package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

public class CountLessManufactureCost extends Command {
    private final CollectionRuler collectionRuler;
    public CountLessManufactureCost(CollectionRuler collectionRuler){
        super("CountLessThanManufactureCost","вывести количество элементов, значение поля manufactureCost которых меньше заданного");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments , Mclass mclass,String login,String password){
        if(arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " 'manufactureCost'");
        }
        double manufactureCost = Double.parseDouble(arguments[1]);
        long count = collectionRuler.getCollection().stream()
                .filter(m -> m.getManufactureCost() < manufactureCost)
                .count();
        return new Response("Количество элементов с manufactureCost меньше " + manufactureCost + ": " + count);
    }
}