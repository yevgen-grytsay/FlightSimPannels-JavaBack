package com.company;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue();

        DatarefServer datarefServer = new DatarefServer(q);
        datarefServer.start();

        WebSocketServer webSocketServer = new WebSocketServer(3001, true, q);
        webSocketServer.start();

    }
}
//