package com.company;

import java.io.IOException;

public class EchoServerRunner {
    public static void main(String[] args) {
        var server = new EchoServer();
        server.start();
//        try {
//            server.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
