package com.mybio.nio;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NIOClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            while (true) {
                OutputStream os = socket.getOutputStream();
                Scanner scanner = new Scanner(System.in);
                System.out.print("请输入内容 : ");
                String content = scanner.next();
                if ("exit".equalsIgnoreCase(content)) {
                    break;
                } else {
                    os.write(content.getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
