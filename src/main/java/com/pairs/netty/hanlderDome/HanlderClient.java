package com.pairs.netty.hanlderDome;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Created on 2017年07月03日 10:10
 * <P>
 * Title:[]
 * </p>
 * <p>
 * Description :[]
 * </p>
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public class HanlderClient {

    private static Channel channel;

    public static void main(String[] args) {

        Bootstrap bt=new Bootstrap();
        EventLoopGroup e=new NioEventLoopGroup();
        bt.group(e)
                .channel(NioSocketChannel.class)
                .remoteAddress("127.0.0.1",7070)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                    }
                });
        ChannelFuture channelFuture = bt.connect();
        channel = channelFuture.channel();
        CountDownLatch c=new CountDownLatch(1);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("连接成功");
                    c.countDown();
                }
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Scanner scanner = new Scanner(System.in);
                    String str=scanner.nextLine();
                    System.out.println("输入命令:"+str);
                    ByteBuf bt= Unpooled.buffer();
                    bt.writeBytes(str.getBytes());
                    channel.writeAndFlush(bt);
                }
            }
        }).start();


    }

}
