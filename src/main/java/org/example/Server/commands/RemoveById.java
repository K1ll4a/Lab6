package org.example.Server.commands;

import org.example.Server.rulers.CollectionRuler;
import org.example.global.exeptions.NotFoundExeption;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

public class RemoveById  extends  Command{
    private final CollectionRuler collectionRuler;
    public RemoveById(CollectionRuler collectionRuler){
        super("remove_by_id","удалить элемент из коллекции по его id");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass){
        if(arguments[1].isEmpty()){
            return new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");
        }
        try{
            long deletableId = Long.parseLong(arguments[1]);
            var deletable = collectionRuler.byId(deletableId);
            if (deletable == null) throw new NotFoundExeption();
            collectionRuler.remove(deletable);
            return new Response("Элемент успешно удален");
        }catch (NotFoundExeption e){
            return new Response("Элемент с таким id не найден");
        }
    }

}
