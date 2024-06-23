package org.example.Server.commands;

import org.example.global.facility.Response;
import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;

public class Clear  extends  Command{
    private final CollectionRuler collectionRuler;

    public  Clear(CollectionRuler collectionRuler){
        super("clear","очистить коллекцию");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments,Mclass mclass){
        if(!arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }

        if(!(collectionRuler.getCollection().isEmpty())){
            collectionRuler.removeAll();
            return new Response("Коллекция успешно очищена");
    }else{
            return new Response("Коллекция пуста");
        }
}}