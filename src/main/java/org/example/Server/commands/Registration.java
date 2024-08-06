package org.example.Server.commands;

import org.example.global.facility.Response;
import org.example.global.facility.Mclass;
import org.example.Server.rulers.DatabaseRuler;

import java.sql.SQLException;

import static org.example.Server.tools.PasswordHashing.*;

public class Registration extends Command {
    private  DatabaseRuler databaseRuler;
    public Registration(DatabaseRuler databaseRuler){
        super("registration" , "Добавить пользователя");
        this.databaseRuler = databaseRuler;
    }

    @Override
    public Response apply(String[] arguments , Mclass mclass , String login ,String password) throws SQLException{
        try {
            String salt = generateSalt();
            databaseRuler.insertUser(login,hashPasswordWithSalt(password,salt),salt);
            return new Response("Вы успешно авторизовались" , true);
        }catch (SQLException e){
            return new Response("Ошибка! Не удалось добавить пользователя");
        }
    }
}
