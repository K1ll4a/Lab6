package org.example.Server.commands;

import org.example.global.facility.Response;
import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import java.sql.SQLException;

public class Clear  extends  Command{
    private final CollectionRuler collectionRuler;

    public  Clear(CollectionRuler collectionRuler){
        super("clear","очистить коллекцию");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments,Mclass mclass,String login,String password){
        if(!arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }

        try{
            if(!(collectionRuler.getCollection().size() == 0)){
                var userID = collectionRuler.getUserid(login);
                collectionRuler.removeAll(userID);
                return  new Response("Коллекция очищена");
            }else {
                return new Response("Коллекция пуста");
            }
        }catch (SQLException e){
            return new Response("Ошибка в базе данных во время очищения коллекции пользователя");
        }
    }
}