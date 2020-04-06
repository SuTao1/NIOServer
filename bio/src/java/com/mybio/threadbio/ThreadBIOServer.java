package com.mybio.threadbio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程阻塞
 */
public class ThreadBIOServer {

    static byte[] BYTE = new byte[1024];

    public static void main(String[] args) {

        try {
            // 创建socket
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                // 阻塞等待客户连接
                System.out.println("阻塞——等待客户连接");
                Socket socket = serverSocket.accept();
                // 每有一个客户创建连接，便开启一个线程并启动，将 “ 等待客户输入流 ” 的阻塞交给子线程处理。避免影响主线程监听新客户的连接
                new Thread(new ThreadIO(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ThreadIO implements Runnable {

        private Socket socket;

        private ThreadIO(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                int length = is.read(BYTE);
                System.out.println("客户端输入的内容 : " + new String(BYTE, 0, length));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
