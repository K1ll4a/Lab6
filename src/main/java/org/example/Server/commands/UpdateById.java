package org.example.Server.commands;
import org.example.Server.rulers.CollectionRuler;
import org.example.Server.rulers.DatabaseRuler;
import org.example.global.exeptions.NotFoundExeption;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;


import java.sql.SQLException;

public class UpdateById  extends Command {
    private final CollectionRuler collectionRuler;
    private final DatabaseRuler databaseRuler;

    public UpdateById(CollectionRuler collectionRuler, DatabaseRuler databaseRuler) {
        super("update", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionRuler = collectionRuler;
        this.databaseRuler = databaseRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass, String login, String password) {
        if (arguments[1].isEmpty()) {
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");

        }
        try {
            long deletableId = Long.parseLong(arguments[1]);
            var deletable = collectionRuler.byId(deletableId);
            if (deletable == null) throw new NotFoundExeption();
            var userID = collectionRuler.getUserid(login);
            var checkingUsertID = collectionRuler.isCorrectID(deletableId);
            if (userID == checkingUsertID && mclass != null && mclass.validate()) {
                collectionRuler.updateMclassDB(mclass, deletableId);
                collectionRuler.updateCollection(deletable, mclass, deletableId, login);
                return new Response("Mclass обновлен");
            } else {
                return new Response("Mclass не обновлён! Возможные причины: вы попытались обновить чужой mclass" +
                        "или поля обновлённого mclass невалидны");
            }
        } catch (NotFoundExeption e) {
            return new Response("Продукта с таким ID не существует в коллекции");
        } catch (SQLException e) {
            return new Response("Ошибка обновления mclass в базе данных");
        }
    }
}
