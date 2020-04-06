package com.mybio.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class BIOClient {

    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket("192.168.1.109", 8080);
            os = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("客戶端，請輸入内容: ");
                String count = scanner.next();
                if ("exit".equalsIgnoreCase(count)) {
                    break;
                } else {
                    os.write(count.getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != socket) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
