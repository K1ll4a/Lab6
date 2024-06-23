package org.example.Server.commands;
import org.example.Server.rulers.CollectionRuler;
import org.example.global.exeptions.NotFoundExeption;
import org.example.global.facility.Mclass;
import org.example.global.facility.Response;

public class UpdateById  extends Command{
    private final CollectionRuler collectionRuler;

    public UpdateById(CollectionRuler collectionRuler){
        super("update","обновить значение элемента коллекции, id которого равен заданному");
        this.collectionRuler = collectionRuler;
    }

    @Override
    public Response apply(String[] arguments, Mclass mclass){
        if (arguments[1].isEmpty()){
            return  new Response("Неправильное количество аргументов!\n" + "Использование: " + getName() + " '");

        }
        try{
            long deletableId = Long.parseLong(arguments[1]);
            var deletable = collectionRuler.byId(deletableId);
            if (deletable == null) throw new NotFoundExeption();
            collectionRuler.remove(deletable);
            Mclass a = mclass;
            a.setId(deletableId);
            if(a.validate()){
                collectionRuler.add(a);
                return new Response("Элемент успешно обновлен");
            }else{
                return new Response("Элемент не обновлен");
            }
        }catch (NotFoundExeption e){
            return new Response("Элемент с таким id не найден");
        }
    }
}
