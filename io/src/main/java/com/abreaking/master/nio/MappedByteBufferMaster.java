package com.abreaking.master.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 内存映射文件(Memory-mapped File)，指的是将一段虚拟内存逐字节映射于一个文件，使得应用程序处理文件如同访问主内存（但在真正使用到这些数据前却不会消耗物理内存，也不会有读写磁盘的操作），这要比直接文件读写快几个数量级。
 * java.nio 包使得内存映射变得非常简单，其中的核心类叫做 MappedByteBuffer，字面意思为映射的字节缓冲区。
 * from https://juejin.im/post/5d53c0e1f265da0390052545
 *
 * 用MappedByteBufferMaster 来读取超大的文件
 * @author liwei_paas 
 * @date 2020/1/14
 */
public class MappedByteBufferMaster {

    private static final String BIG_FILE_LOCATION = "C:\\Users\\MI\\Desktop\\es接口\\esb-esbws.txt";


    /**
     * 简单的 读内容，一次性全读进来
     * @throws Exception
     */
    @Test
    public void simpleMappedByteBufferRead() throws Exception {
        //源文件
        Path pathSource = Paths.get(BIG_FILE_LOCATION);
        FileChannel fileChannelSource = FileChannel.open(pathSource);
        long sourceSize = fileChannelSource.size();
        MappedByteBuffer mbbSource = fileChannelSource.map(FileChannel.MapMode.READ_ONLY, 0, sourceSize);

        //写入文件
        Path pathTarget = Paths.get("D:\\workspace\\master-foundation\\io\\src\\main\\resources\\mbb-target.log");
        FileChannel channelTarget = FileChannel.open(pathTarget, StandardOpenOption.READ, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
        MappedByteBuffer mbbTarget = channelTarget.map(FileChannel.MapMode.READ_WRITE, 0, sourceSize);

        /*for (int i = 0; i < sourceSize; i++) {\
            //从源文件读内容
            byte b = mbbSource.get(i);
            //将读出来的内容写入新文件
            mbbTarget.put(i,b);
        }*/

        //从源文件读内容
        CharBuffer charBuffer = Charset.forName("utf-8").decode(mbbSource);

        //将读出来的内容写入新文件
        ByteBuffer byteBuffer = mbbTarget.put(Charset.forName("UTF-8").encode(charBuffer));

        System.out.println(byteBuffer.toString());
    }

    public static void main(String[] args) throws URISyntaxException {
        CharBuffer charBuffer = CharBuffer.wrap("123");

        URI uriTarget = MappedByteBufferMaster.class.getClassLoader().getResource("mbb-target.log").toURI();
        Path path = Paths.get(uriTarget);

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

            if (mappedByteBuffer != null) {
                mappedByteBuffer.put(Charset.forName("UTF-8").encode(charBuffer));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Reader{


    }
}
