package com.abreaking.master.nio;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
 * 仅用单个线程来处理多个Channels的好处是，只需要更少的线程来处理通道。事实上，可以只用一个线程处理所有的通道。对于操作系统来说，线程之间上下文切换的开销很大，而且每个线程都要占用系统的一些资源（如内存）。因此，使用的线程越少越好。
 *
 * 但是，需要记住，现代的操作系统和CPU在多任务方面表现的越来越好，所以多线程的开销随着时间的推移，变得越来越小了。实际上，如果一个CPU有多个内核，不使用多任务可能是在浪费CPU能力。不管怎么说，关于那种设计的讨论应该放在另一篇不同的文章中。在这里，只要知道使用Selector能够处理多个通道就足够了。
 *
 * form: {@see http://topjava.cn/article/1389525261179031552}
 * @author liwei
 * @date 2021/5/19
 */
public class SelectorMaster {

    @Test
    public void test_server() throws IOException, InterruptedException {
        Selector selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_ACCEPT);

        while (true){
            int select = selector.select();
            if (select==0) continue;

            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> iterator = set.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if (key.isAcceptable()){
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_WRITE);
                } else if (key.isReadable()){

                }else  if (key.isWritable()){
                    SocketChannel selectableChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(32);
                    byteBuffer.put("hello world!!!".getBytes());
                    byteBuffer.flip();
                    selectableChannel.write(byteBuffer);
                    selectableChannel.close();
                }else if (key.isConnectable()){

                }
                iterator.remove();
            }
            Thread.sleep(1000);
        }

    }

    @Test
    public void test_client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(9999));
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("from server data is :"+new String(byteBuffer.array()));
    }
}
