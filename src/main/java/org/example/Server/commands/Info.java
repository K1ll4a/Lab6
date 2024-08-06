package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

import java.time.LocalDateTime;

public class Info  extends Command{
    private final CollectionRuler collectionRuler;

    public Info(CollectionRuler collectionRuler){
        super("info","вывести информацию о коллекции");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass,String login,String password){
        if(!arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }
        LocalDateTime lastInitTime = collectionRuler.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации не было" : lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionRuler.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "сохранений не было" : lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
        String s="";
        s+="Сведения о коллекции:\n";
        s+="Тип: " + collectionRuler.getCollection().getClass().toString();
        s+= " \nКоличество элементов: " + collectionRuler.getCollection().size();
        s+= " \nДата последнего сохранения: " + lastSaveTimeString;
        s+= " \nДата последней инициализации: " + lastInitTimeString;
        s+="\n";
        return new Response(s);

    }
}
