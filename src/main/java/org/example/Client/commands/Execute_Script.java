package org.example.Client.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

import org.example.Server.commands.Command;
import org.example.Server.rulers.CollectionRuler;
import org.example.global.facility.Mclass;
import org.example.global.facility.Request;
import org.example.global.facility.Mclass.*;
import org.example.Client.managers.SocketClient;
import org.example.global.facility.Response;



public class Execute_Script {
    private static Stack<File> st = new Stack<>();


    public static void execute(String command , SocketChannel socketChannel , String login , String password) throws Exception {
        File file = new File(command.split( " ")[1]);
        if (!file.canRead()) {
            throw new Exception("У вас недостаточно прав для чтения этого файла");
        }
        if (st.isEmpty()) {
            st.add(file);
        }

        st.add(file);
        String path = command.split( " ")[1];
        var br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        String[] org = new String[9];
        while ((line = br.readLine()) != null) {
            String mainCommand = line.split(" ")[0];
            if (mainCommand.equals("add")||mainCommand.equals("add_if_min")||mainCommand.equals("update_by_id")) {

                for (int n = 0; n < 9; n++) {
                    if ((line = br.readLine()) != null) {
                        org[n] = line;
                    }
                }
                SocketClient.sendRequest(new Request(mainCommand,new Mclass(org[0], Double.parseDouble(org[1]) , new Coordinates(Float.parseFloat(org[2]),Float.parseFloat(org[3])),
                        UnitOfMeasure.valueOf(org[5]),Float.parseFloat(org[4]), new Organization(org[6],org[7],OrganizationType.valueOf(org[8]))), login, password),socketChannel);
            }else {
                if (line.contains("execute_script")) {
                    File file_new = new File(line.split(" ")[1]);
                    if (!file_new.canRead()) {
                        throw new Exception("У вас недостаточно прав для чтения этого файла");
                    }
                    if (st.contains(file_new)) {
                        System.out.println("Рекурсия файла " + file.getName() + " была пропущена");
                    } else {
                        execute(line,socketChannel,login,password);
                    }
                } else {
                    SocketClient.sendRequest(new Request(line,null,login,password),socketChannel);
                }
            }
        }
        st.pop();
    }

    public String getName() {
        return "execute_script file_name";
    }

}