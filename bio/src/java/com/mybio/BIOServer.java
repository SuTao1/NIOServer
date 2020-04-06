package com.mybio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    private static byte[] BYTE = new byte[1024];
    private static int ZERO = 0;
    private static int PORT = 8080;
    public static void main(String[] args) {
        Socket socket = null;
        InputStream is = null;
        ServerSocket serverSocket = null;
        try {
            // 实例化serverSocket
            serverSocket = new ServerSocket(PORT);
            while (true) {
                // 阻塞，等待客户端连接
                System.out.println("阻塞——等待客户端连接");
                socket = serverSocket.accept();
                System.out.println("连接成功");
                // 连接成功，获取客户端的流入的输入流
                is = socket.getInputStream();
                int len;
                // 阻塞，等待客户端进行流入
                // 这里阻塞后，服务器将无法被新的连接访问。
                System.out.println("阻塞——等待客户端的流");
                len = is.read(BYTE);
                String result = new String(BYTE, 0, len);
                if ("exit".equalsIgnoreCase(result)) {
                    break;
                } else {
                    System.out.println("客户端的内容 : " + result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != socket) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != serverSocket) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
