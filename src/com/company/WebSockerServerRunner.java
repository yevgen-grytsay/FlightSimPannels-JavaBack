package com.company;

import fi.iki.elonen.NanoWSD;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

public class WebSockerServerRunner {

    public static void main(String[] args) throws IOException {
//        final boolean debugMode = args.length >= 2 && args[1].toLowerCase().equals("-d");
        NanoWSD ws = new WebSocketServer(args.length > 0 ? Integer.parseInt(args[0]) : 3000, true, new SynchronousQueue<>());
        ws.start();
        System.out.println("Server started, hit Enter to stop.\n");
        try {
            System.in.read();
        } catch (IOException ignored) {
        }
        ws.stop();
        System.out.println("Server stopped.\n");
    }
}
