package org.example.Server.commands;

import org.example.global.facility.Response;
import org.example.global.facility.Mclass;

import org.example.Server.rulers.CollectionRuler;







/**
 * добавление элемента в коллекцию
 */

public class Add extends Command{
    private final CollectionRuler collectionRuler;

    public Add( CollectionRuler collectionRuler){
        super("add", "добавить новый элемент в коллекцию");
        this.collectionRuler=collectionRuler;
    }

    /**
     * метод выполняет команду
     *
     * @return возвращает сообщение о  успешности выполнения команды
     */

    public Response apply(String[] arguments , Mclass mclass){
        try{
            if(!arguments[1].isEmpty()){
                //console.println("Неправильное количество аргументов!");
                //console.println("Использование: '" + getName() + "'");
                return new Response("Неправильное количество аргументов!\n" + "Использование: '" + getName() + "'" );
            }

            Mclass a =  mclass;
            if(a!= null&&a.validate()){
                collectionRuler.add(a);

                return new Response("Mclass добавлен!");
            }else{

                return new Response("Поля Mclass не валидны! Mclass не создан!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}