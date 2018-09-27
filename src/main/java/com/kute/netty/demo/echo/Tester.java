package com.kute.netty.demo.echo;

/**
 * created by kute on 2018/07/28 18:27
 */
public class Tester {

    public static void main(String[] args) {

        String host = "localhost";
        int port = 5007;
        boolean ssl = true;

        // 1. start server
        new Thread(() -> {
            try {
                new EchoServer(ssl, port).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 2. start client
        new Thread(() -> {
            try {
                new EchoClient(host, port, ssl).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



    }
}
