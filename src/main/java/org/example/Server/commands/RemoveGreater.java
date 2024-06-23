package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

public class RemoveGreater extends Command {
    private final CollectionRuler collectionRuler;

    public RemoveGreater(CollectionRuler collectionRuler) {
        super("RemoveGreater", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass) {
        try {
            if (arguments[1].isEmpty()) {
                return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " {element}");
            }
            Mclass a = mclass;
            if (a == null) {
                return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " {element}");
            }
            int initialSize = collectionRuler.getCollection().size();
            collectionRuler.getCollection().removeIf(m -> m.compareTo(a) > 0);
            int finalSize = collectionRuler.getCollection().size();
            return new Response("Удалено элементов: " + (initialSize - finalSize));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}