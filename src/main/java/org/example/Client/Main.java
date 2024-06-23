package org.example.Client;


import org.example.Client.managers.SocketClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        SocketClient client = new SocketClient("localhost",8080);
        client.start();
    }
}