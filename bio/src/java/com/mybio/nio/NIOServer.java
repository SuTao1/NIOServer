package com.mybio.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * NIO —— 单线程 非阻塞
 */
public class NIOServer {

    private static List<SocketChannel> CLIENT_LIST = new ArrayList<>();
    private static ByteBuffer BYTE = ByteBuffer.allocate(1024);

    public static void main(String[] args) {

        try {
            // 开启socket服务
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(8080));
            while (true) {
                // 关闭第一个阻塞点 —— 监听客户端的阻塞
                serverSocketChannel.configureBlocking(false);
                Thread.sleep(5000);
                // 监听连接
                SocketChannel socket = serverSocketChannel.accept();
                if (null != socket) {
                    // 关闭第二个阻塞点 —— 等待客户端输入流的阻塞
                    socket.configureBlocking(false);
                    CLIENT_LIST.add(socket);
                    System.out.println("新用户加入!! 当前连接数量 : " + CLIENT_LIST.size());
                } else {
                    System.out.println("没有监听到连接，当前连接数 : " + CLIENT_LIST.size());
                }
                clientHandler();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void clientHandler() {
        for (SocketChannel socketChannel : CLIENT_LIST) {
            try {
                int length = socketChannel.read(BYTE);
                if (length == -1) {
                    CLIENT_LIST.remove(socketChannel);
                } else if (length > 0) {
                    BYTE.flip();
                    System.out.println("客户发送的内容 : " + new String(BYTE.array()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
