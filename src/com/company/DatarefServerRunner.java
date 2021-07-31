package com.company;

import java.util.concurrent.SynchronousQueue;

public class DatarefServerRunner {
    public static void main(String[] args) {
        DatarefServer server = new DatarefServer(new SynchronousQueue<>());
        server.start();
//        try {
//            server.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
