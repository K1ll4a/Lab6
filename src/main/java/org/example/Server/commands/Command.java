package org.example.Server.commands;

import org.example.global.tools.MyConsole;
import org.example.global.tools.Console;

public abstract class Command implements Executable {
    private final String name;
    private final String description;

    public Console console = new MyConsole();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return"Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    @Override
    public int hashCode(){
        return name.hashCode() + description.hashCode();
    }
    
}
