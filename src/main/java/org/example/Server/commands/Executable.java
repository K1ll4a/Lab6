package org.example.Server.commands;

import org.example.global.facility.Response;
import org.example.global.facility.Mclass;

import java.sql.SQLException;

public interface Executable {
    Response apply(String[] arguments , Mclass mclass,String login,String password) throws SQLException;
}
