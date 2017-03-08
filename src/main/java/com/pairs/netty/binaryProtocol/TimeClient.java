package com.pairs.netty.binaryProtocol;

import com.pairs.netty.codec.marshalling.MarshallingCodecFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClient {

    private int port=7081;


    private void connect(){
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));//使用半包读解码器
                        socketChannel.pipeline().addLast(new MemcachedRequestEncoder());
                        socketChannel.pipeline().addLast(new SimplerRequestEncoder());
//                        socketChannel.pipeline().addLast(new TimeClientHandler());
                        socketChannel.pipeline().addLast(new TimeClientSimpleHandler());
                    }
                });
        try {
            ChannelFuture channelFuture=bootstrap.connect("127.0.0.1",port).sync();
            System.out.println(121212);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new TimeClient().connect();

    }
}
