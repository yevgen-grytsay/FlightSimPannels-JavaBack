package com.company;

public class DatarefServerRunner {
    public static void main(String[] args) {
        var server = new DatarefServer();
        server.start();
//        try {
//            server.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
