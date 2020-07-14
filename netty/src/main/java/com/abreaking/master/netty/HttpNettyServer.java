package com.abreaking.master.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * netty的简易服务器
 * @author liwei_paas
 * @date 2020/7/13
 */
public class HttpNettyServer {
    private final int port;

    public HttpNettyServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                .group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        System.out.println("init channel:"+channel);
                        channel.pipeline()
                                .addLast("decoder",new HttpRequestDecoder())
                                .addLast("encoder", new HttpResponseEncoder())  // 2
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))    // 3
                                .addLast("handler", new HttpNettyHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
        bootstrap.bind(port).sync();
    }

    public static void  main(String args[]) throws Exception {
        int port = 9999;
        new HttpNettyServer(port).start();
    }
}
