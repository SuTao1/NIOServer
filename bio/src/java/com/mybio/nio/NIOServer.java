package com.mybio.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NIOServer {

    private static List<SocketChannel> CLIENT_CONNECTION = new ArrayList<SocketChannel>();
    private static ByteBuffer BF = ByteBuffer.allocate(1024);
    public static void main(String[] args) {
        try {
            // 服务器打开连接
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(8080));
            // 设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            while (true) {
                // 监听连接
                System.out.println("开始监听连接");
                SocketChannel socket = serverSocketChannel.accept();
                Thread.sleep(5000);
                if (null == socket) {
                    System.out.println("没有新连接, 当前连接个数 : " + CLIENT_CONNECTION.size());
                } else {
                    socket.configureBlocking(false);
                    CLIENT_CONNECTION.add(socket);
                    System.out.println("新连接！！, 当前连接个数 : " + CLIENT_CONNECTION.size());
                }
                clientHandler();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clientHandler() {
        for (SocketChannel socketChannel : CLIENT_CONNECTION) {
            try {
                int length = socketChannel.read(BF);
                if (length == -1) {
                    CLIENT_CONNECTION.remove(socketChannel);
                } else if (length > 0) {
                    System.out.println("客户端--内容 : " + BF);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
