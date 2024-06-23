package org.example.Server;


import org.example.Server.managers.SocketServer;
import org.example.Server.rulers.CollectionRuler;
import org.example.Server.rulers.CommandRuler;
import org.example.Server.tools.Parser;
import org.example.global.tools.MyConsole;
import org.example.global.tools.Console;
import org.example.Server.commands.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Console console = new MyConsole();
        if (System.getenv("DATANAME") == null) {
            System.out.println("Введите имя загружаемого файла, как переменную окружения");
            System.exit(1);
        }

        Parser parser = new Parser(System.getenv("DATANAME"), console);
        CollectionRuler collectionRuler = new CollectionRuler(parser);
        if (!collectionRuler.init()) {
            System.out.println("Ошибка инициализации коллекции");
            System.exit(1);
        }
        CommandRuler commandRuler = new CommandRuler(){{
            register("info",new Info(collectionRuler));
            register("help",new Help(this));
            register("show",new Show(collectionRuler));
            register("add",new Add(collectionRuler));
            register("update",new UpdateById(collectionRuler));
            register("remove_by_id",new RemoveById(collectionRuler));
            register("clear",new Clear(collectionRuler));
            register("save",new Save(collectionRuler));
            register("exit",new Exit());
            register("add_if_min",new AddIfMin(collectionRuler));
            register("RemoveLast",new RemoveLast(collectionRuler));
            register("RemoveGreater",new RemoveGreater(collectionRuler));
            register("count_greater_than_unit_of_measure",new CountLessManufactureCost(collectionRuler));
            register("MaxByManufactureCost",new MaxByManufactureCost(collectionRuler));
            register("MinByManufactureCost",new MinByManufactureCost(collectionRuler));
        }};

        System.out.println("Сервер запускается...");
        System.out.println("Сервер запущен и слушает на порту 8080");

        new SocketServer("localhost", 8080, commandRuler).start();

        //System.out.println("Сервер запущен и слушает на порту 8080");
    }
}