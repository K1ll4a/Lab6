package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

import java.util.Comparator;
import java.util.Optional;

public class MinByManufactureCost extends Command {
    private final CollectionRuler collectionRuler;
    public MinByManufactureCost(CollectionRuler collectionRuler){
        super("MinByManufactureCost","вывести любой объект из коллекции, значение поля manufactureCost которого является минимальным");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments , Mclass mclass,String login , String password){
        if(!arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }
        Optional<Mclass> minManufactureCostMclass = collectionRuler.getCollection().stream()
                .min(Comparator.comparing(Mclass::getManufactureCost));
        if (minManufactureCostMclass.isPresent()) {
            return new Response("Объект с минимальным значением manufactureCost: " + minManufactureCostMclass.get());
        } else {
            return new Response("Коллекция пуста");
        }
    }
}