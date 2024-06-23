package org.example.Server.commands;


import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;


public class AddIfMin extends Command{
    private final CollectionRuler collectionRuler;

    public AddIfMin(CollectionRuler collectionRuler){
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply (String[] arguments , Mclass mclass){
        try{
            if (!arguments[1].isEmpty()){
                return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
            }

            Mclass a = mclass;
            var minPrice = minPrice();
            if(a.getPrice() < minPrice){
                if(a != null){
                    collectionRuler.add(a);
                    
                }else{
                    return new Response("Элемент не добавлен в коллекцию");
                }
            }else{
                return new Response("Элемент не добавлен в коллекцию,цена не минимальная (" + a.getPrice() + " > " + minPrice + ")");
            }
            collectionRuler.update();
            return new Response("Элемент успешно добавлен в коллекцию");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Double minPrice(){
        return collectionRuler.getCollection().stream().map(Mclass::getPrice).min(Double::compareTo).orElse(Double.MAX_VALUE);
    }
    
}
