package org.example.Server.commands;


import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

import java.sql.SQLException;


public class AddIfMin extends Command{
    private final CollectionRuler collectionRuler;

    public AddIfMin(CollectionRuler collectionRuler){
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply (String[] arguments , Mclass mclass,String login,String password){
        try{
            if (!arguments[1].isEmpty()){
                return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
            }
            mclass.setLogin(login);
            mclass.setUser_id(collectionRuler.getUserid(login));
            var minPrice = minPrice();
            if(mclass.getPrice() < minPrice){
                if(mclass != null && mclass.validate()){
                    collectionRuler.addToCollection(mclass,login);
                }else{
                    return new Response("Элемент не добавлен в коллекцию");
                }
            }else{
                return new Response("Элемент не добавлен в коллекцию,цена не минимальная (" + mclass.getPrice() + " > " + minPrice + ")");
            }
            return new Response("Элемент успешно добавлен в коллекцию");
        } catch (SQLException e){
            return  new Response("Ошибка при добавлении");
        }catch (Exception e){
            return new Response("Непредвиденная ошибка при добавлении");
        }
    }

    private Double minPrice(){
        return collectionRuler.getCollection().stream().map(Mclass::getPrice).min(Double::compareTo).orElse(Double.MAX_VALUE);
    }
    
}
