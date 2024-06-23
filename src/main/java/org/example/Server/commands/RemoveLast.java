package org.example.Server.commands;

import org.example.global.facility.Mclass;
import org.example.global.facility.Response;
import org.example.Server.rulers.CollectionRuler;

public class RemoveLast  extends  Command{
    private final CollectionRuler collectionRuler;

    public RemoveLast(CollectionRuler collectionRuler){
        super("remove_last","удалить последний элемент из коллекции");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass){
        if (!arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }
        collectionRuler.removeLast();
        return new Response("Последний элемент успешно удален");
    }
}
