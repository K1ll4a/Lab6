package org.example.global.tools;

import java.util.Scanner;
public class MyConsole implements Console {
    private static final String P = "$ ";
    private static Scanner fileScanner = null;
    private static Scanner defScanner = new Scanner(System.in);
    
    @Override
    public void println(Object obj){
        System.out.println(obj);
    }
    
    @Override
    public void print(Object obj){
        System.out.print(obj);
    }
   
    @Override
    public String readln() {
        if (fileScanner != null){
            return fileScanner.nextLine();
        }
        else {
            return defScanner.nextLine();
        }
    }

    @Override
    public void promt() {
        print(P);
    }

    @Override
    public String getPromt() {
        return P;
    }


    @Override
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner!=null?fileScanner:defScanner).hasNextLine();
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        this.fileScanner = scanner;
    }

    @Override
    public void selectConsoleScanner() {
        this.fileScanner = null;
    }

    @Override
    public void printError(Object obj) {
        System.out.println(obj);
    }

    

    
}