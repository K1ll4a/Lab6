package org.example.Client.tools;

import org.example.global.tools.Console;
import org.example.global.facility.*;


import org.example.global.facility.Mclass.*;

public class Ask {

    public static class AskBreak extends Exception{}
    
        
    

    public static Mclass askMclass(Console console) throws AskBreak{
        String name = askMclassName(console);
        Coordinates coordinates = askCoordinates(console);
        Double price = askPrice(console);
        Float manufactureCost = askManufactureCost(console);
        UnitOfMeasure unitOfMeasure = askUnitOfMeasure(console);
        Organization manufacturer = askOrganization(console);
        Mclass mclass = new Mclass(name, price,coordinates, manufactureCost,unitOfMeasure, manufacturer);
        return mclass;
    }

    public static String askMclassName(Console console) throws AskBreak{
        String name;
        while (true){
            console.print("Введите имя продукта: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                name = line;
                break; // Add break statement to exit the loop
            }
        }
        return name; // Add return statement after the try-catch block
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak{
        float x;
        while (true){
            console.print("Введите координату x: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                try{
                    x = Float.parseFloat(line);
                    break; // Add break statement to exit the loop
                }catch (NumberFormatException e){
                    console.printError("Координата x должна быть числом");
                    
                }catch(IllegalArgumentException e){
                    console.printError("Координата x  не валидна");
                }
            }
        }
        Float y;
        while (true) {
            console.print("Введите координату y: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if(!line.isEmpty()){
                try{
                    y = Float.parseFloat(line);
                    break; // Add break statement to exit the loop
                }catch (NumberFormatException e){
                    console.printError("Координата y должна быть числом");
                }catch(IllegalArgumentException e){
                    console.printError("Координата y не валидна");
                }
            }
        }
        return new Coordinates(x, y); // Add return statement after the try-catch block
        
    }

    public static Double askPrice(Console console) throws AskBreak{
        Double price;
        while (true){
            console.print("Введите цену продукта: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                try{
                    price = Double.parseDouble(line);
                    if(price>0){
                        break; // Add break statement to exit the loop
                    }else{
                        throw new IllegalArgumentException();
                    }
                }catch (NumberFormatException e){
                    console.printError("Цена должна быть числом");
                }catch(IllegalArgumentException e){
                    console.printError("Цена не валидна");
                }
            }
        }
        return price; 
    }
    
    public static Float askManufactureCost(Console console) throws AskBreak{
        Float manufactureCost;
        while (true){
            console.print("Введите стоимость производства продукта: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                try{
                    manufactureCost = Float.parseFloat(line);
                    if(manufactureCost>0){
                        break; // Add break statement to exit the loop
                    }else{
                        throw new IllegalArgumentException();
                    }
                }catch (NumberFormatException e){
                    console.printError("Стоимость производства должна быть числом");
                }catch(IllegalArgumentException e){
                    console.printError("Стоимость производства не валидна");
                }
            }
        }
        return manufactureCost; 
    }

    public static UnitOfMeasure askUnitOfMeasure(Console console) throws AskBreak{
        UnitOfMeasure unitOfMeasure;
        while (true){
            console.print("Введите единицу измерения продукта (KILOGRAMS, PCS, LITERS): ");
            String line = console.readln().trim().toUpperCase();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                try{
                    unitOfMeasure = UnitOfMeasure.valueOf(line);
                    break; // Add break statement to exit the loop
                }catch (IllegalArgumentException e){
                    console.printError("Единица измерения не валидна");
                }
            }
        }
        return unitOfMeasure; 
    }

    public static Organization askOrganization(Console console) throws AskBreak{
        String OrgName = askOrganizationName(console);
        String fullName = askOrganizationFullName(console);
        OrganizationType type = askOrganizationType(console);
        Organization manufacturer = new Organization(OrgName, fullName, type);
        return manufacturer;
    }

    public static String askOrganizationName(Console console) throws AskBreak{
        String OrgName;
        while (true){
            console.print("Введите имя организации: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                OrgName = line;
                break; // Add break statement to exit the loop
            }
        }
        return OrgName; 
    }

    public static String askOrganizationFullName(Console console) throws AskBreak{
        String fullName;
        while (true){
            console.print("Введите полное имя организации: ");
            String line = console.readln().trim();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                fullName = line;
                break; // Add break statement to exit the loop
            }
        }
        return fullName; 
    }

    public static OrganizationType askOrganizationType(Console console) throws AskBreak{
        OrganizationType type;
        while (true){
            console.print("Введите тип организации (COMMERCIAL, PUBLIC, GOVERNMENT, TRUST): ");
            String line = console.readln().trim().toUpperCase();
            if(line.equals("exit")) throw new AskBreak();
            if (!line.isEmpty()){
                try{
                    type = OrganizationType.valueOf(line);
                    break; // Add break statement to exit the loop
                }catch (IllegalArgumentException e){
                    console.printError("Тип организации не валиден");
                }
            }
        }
        return type; 
    }
}
