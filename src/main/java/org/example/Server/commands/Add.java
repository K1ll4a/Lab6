package org.example.Server.commands;

import jdk.jshell.ImportSnippet;
import org.example.global.facility.Response;
import org.example.global.facility.Mclass;
import org.example.Server.rulers.DatabaseRuler;
import org.example.Server.rulers.CollectionRuler;

import java.sql.SQLException;







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

    public Response apply(String[] arguments , Mclass mclass,String login,String password) {
        try {
            if (!arguments[1].isEmpty()) {
                //console.println("Неправильное количество аргументов!");
                //console.println("Использование: '" + getName() + "'");
                return new Response("Неправильное количество аргументов!\n" + "Использование: '" + getName() + "'");
            }
            mclass.setLogin(login);
            mclass.setUser_id(collectionRuler.getUserid(login));
            if (mclass != null && mclass.validate()) {
                collectionRuler.addToCollection(mclass, login);
                return new Response("Mclass добавлен.");
            } else {
                return new Response("Поля Mclass не валидны! Mclass не создан!");
            }

        } catch (SQLException e) {
            return new Response("Ошибка при добавлении");
        } catch (Exception e) {
            return new Response("Непредвиденная ошибка при добавлении");
        }
    }
}