package org.example.Server.commands;
import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

public class Show  extends  Command{
    private final CollectionRuler collectionRuler;
    public Show(CollectionRuler collectionRuler){
        super("show","вывести все элементы коллекции");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass){
        if(!arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }
       return new Response(collectionRuler.toString());
    }
}
