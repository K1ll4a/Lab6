package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

public class Save  extends  Command{
    private final CollectionRuler collectionRuler;

    public Save(CollectionRuler collectionRuler){
        super("save","сохранить коллекцию в файл");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass,String login , String password){
        if(!arguments[1].isEmpty()){
            console.println("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
            console.println("Использование: " + getName() + " '");
            return new Response("");
        }

        console.println("Коллекция сохранена");
        return new Response("");
    }
}
