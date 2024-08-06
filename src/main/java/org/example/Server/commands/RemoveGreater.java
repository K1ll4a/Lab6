package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

import java.sql.SQLException;

public class RemoveGreater extends Command {
    private final CollectionRuler collectionRuler;

    public RemoveGreater(CollectionRuler collectionRuler) {
        super("RemoveGreater", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass, String login, String password) {
        if (arguments[1].isEmpty()) {
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " {element}");
        }
        if (!collectionRuler.collectionIsEmptu()) {
            try {
                var deletable = collectionRuler.getFirtsMclassToRemove();
                var userID = collectionRuler.getUserid(login);
                var deletableId = deletable.getId();
                var checkinguserID = collectionRuler.isCorrectID(deletableId);
                if (userID == checkinguserID) {
                    int initialSize = collectionRuler.getCollection().size();
                    collectionRuler.getCollection().removeIf(m -> m.compareTo(mclass) > 0);
                    int finalSize = collectionRuler.getCollection().size();
                    return new Response("Удалено элементов: " + (initialSize - finalSize));
                } else {
                    return new Response("Mclass не удален! Возможные причины: вы попытались удалить чужой mclass");
                }

            } catch (SQLException e) {
                return new Response("Ошибка удаления mclass в базе данных");
            }
        } else {
            return new Response("Невозможно удалить элемент , так как коллекция пуста");
        }

    }
}
