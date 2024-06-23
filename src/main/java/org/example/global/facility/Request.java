package org.example.global.facility;

import java.io.Serializable;

import org.example.global.facility.Mclass;

public class Request implements Serializable {
    private static final long serialVersionUID = 5760575944040770153L;
    private String commandMassage;
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

    public Request(String commandMassage, Mclass mclass){
        this.commandMassage=commandMassage;
        this.mclass=mclass;
    }

    public Mclass getMclass(){
        return mclass;
    }
}