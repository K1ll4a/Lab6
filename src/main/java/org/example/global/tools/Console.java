package org.example.global.tools;

import java.util.Scanner;

public interface Console {
    void println(Object obj);
    void print(Object obj);
    String readln();
    void printError(Object obj);
    void promt();
    void printTable(Object obj1,Object obj2);
    boolean isCanReadln();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();

    String getPromt();
    
}
