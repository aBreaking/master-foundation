package com.abreaking.master.nio;


import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

/**
 * Java NIO中的Buffer用于和NIO通道进行交互。如你所知，数据是从通道读入缓冲区，从缓冲区写入到通道中的。
 *
 * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
 * from : http://topjava.cn/article/1389525250915569664
 * @author liwei
 * @date 2021/5/19
 */
public class BufferMaster {

    @Test
    public void testByteBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        for (int i = 1; i <= 8; i++) { //因为一个int 占4个字节，所以最多只能Put 8个数字
            byteBuffer.putInt(i);
        }
        System.out.println(byteBuffer.toString());
        System.out.println(byteBuffer.position());
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get(i));
        }
    }

    @Test
    public void testByteBuffer02(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put("abc".getBytes());
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println((char)byteBuffer.get(i));
        }
    }

    @Test
    public void testDoubleBuffer(){
        double[] doubles = {1.0,2.0,3.0,4.0};
        DoubleBuffer doubleBuffer = DoubleBuffer.wrap(doubles);
        System.out.println(doubleBuffer);
        for (int i = 0; i < doubleBuffer.limit(); i++) {
            System.out.println(doubleBuffer.get(i));
        }
    }

    /**
     * Buffer的capacity,position和limit
     * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
     *
     * 为了理解Buffer的工作原理，需要熟悉它的三个属性：
     *
     * capacity
     * position
     * limit
     * position和limit的含义取决于Buffer处在读模式还是写模式。不管Buffer处在什么模式，capacity的含义总是一样的。
     *
     * 这里有一个关于capacity，position和limit在读写模式中的说明，详细的解释在插图后面。
     *
     * 202105041820103361.png
     *
     * capacity
     * 作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
     *
     * position
     * 当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
     *
     * 当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
     *
     * limit
     * 在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
     *
     * 当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
     */
    @Test
    public void testFlip(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        String abc = "abc";
        String[] split = abc.split("");
        System.out.println(byteBuffer);
        for (int i = 0; i < split.length; i++) {
            byteBuffer.put(split[i].getBytes());
            System.out.println(byteBuffer);
        }
        System.out.println(flipToString(byteBuffer));
    }

    static String flipToString(ByteBuffer byteBuffer){
        byteBuffer.flip();
        char[] chars = new char[byteBuffer.limit()];
        for (int i = 0; byteBuffer.hasRemaining(); i++) {
            chars[i] = (char) byteBuffer.get();
        }
        return new String(chars);
    }
}
