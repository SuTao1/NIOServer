package com.mybio.nio;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class NIOClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 8080);
                OutputStream os = socket.getOutputStream();
                Scanner scanner = new Scanner(System.in);
        ) {
            String content = scanner.next();
            os.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
