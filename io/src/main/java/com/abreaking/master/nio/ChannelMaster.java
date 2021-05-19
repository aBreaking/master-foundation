package com.abreaking.master.nio;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.channels.*;
import java.util.RandomAccess;

/**
 * Java NIO的通道类似流，但又有些不同：
 *
 * 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。
 * 通道可以异步地读写。
 * 通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。
 * @author liwei
 * @date 2021/5/19
 */
public class ChannelMaster {

    @Test
    public void testtransfer() throws IOException {
        RandomAccessFile from = new RandomAccessFile("D:\\workspace\\master-foundation\\io\\src\\main\\resources\\mbb-target.log", "rw");
        RandomAccessFile to = new RandomAccessFile("D:\\workspace\\master-foundation\\io\\src\\main\\resources\\mbb-target-new.log", "rw");
        FileChannel fromChannel = from.getChannel();
        FileChannel toChannel = to.getChannel();
        fromChannel.transferTo(0,fromChannel.size(),toChannel);
    }

    /**
     * 使用FileChannel简单的往文件中写数据
     * @throws IOException
     */
    @Test
    public void testFileChannel() throws IOException {

        String targetFilePath = "D:\\workspace\\master-foundation\\io\\src\\main\\resources\\my-target.log";
        File file = new File(targetFilePath);
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String str2Write = "hello world";
        byteBuffer.put(str2Write.getBytes());
        byteBuffer.flip();
        int write = fileChannel.write(byteBuffer);
        System.out.println(write);
        fileChannel.close();
        fileOutputStream.close();

    }

    @Test
    public void testServerSocketChannel() throws IOException, InterruptedException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(9999));
        channel.configureBlocking(false); //ServerSocketChannel可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null。 因此，需要检查返回的SocketChannel是否是null.如
        while (true){
            SocketChannel socketChannel = channel.accept();
            if (socketChannel!=null){
                ByteBuffer byteBuffer = ByteBuffer.allocate(8);
                socketChannel.read(byteBuffer);
                byteBuffer.flip();
                System.out.println(new String(byteBuffer.array()));

                byteBuffer.clear();

                byteBuffer.put("hello".getBytes());
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    socketChannel.write(byteBuffer);
                }
                System.out.println(socketChannel.getLocalAddress()+" has coming");
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void testSocketChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(9999));
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);

        byteBuffer.put("abc".getBytes());
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            socketChannel.write(byteBuffer);
        }
        byteBuffer.clear();

        socketChannel.read(byteBuffer);
        System.out.println(BufferMaster.flipToString(byteBuffer));

    }



}
