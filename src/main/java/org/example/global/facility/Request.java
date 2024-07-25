package org.example.global.facility;


import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String commandMassage;
    private String password;
    private String login;
    private Mclass mclass;
    public Request(String commandMassage){
        this.commandMassage = commandMassage;
    }

    public String getCommandMassage(){
        return commandMassage;
    }

    @Override
    public String toString(){
        return commandMassage;
    }

    public Request(String commandMassage, Mclass mclass , String login , String password){
        this.commandMassage=commandMassage;
        this.mclass = mclass;
        this.login = login;
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public String getLogin(){
        return login;
    }

    public Mclass getMclass(){
        return mclass;
    }
}