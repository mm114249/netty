package com.pairs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Created by hupeng on 2017/1/19.
 */
public class BFServerHanlder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Scanner scanner = new Scanner(System.in);
                    String str=scanner.nextLine();
                    System.out.println("输入命令:"+str);
//                    BFModel bfModel=new BFModel("aaaa",11);
//                    ctx.channel().writeAndFlush(bfModel);
                    ByteBuf bt= Unpooled.buffer();
                    bt.writeBytes("aaa".getBytes());
                    ctx.channel().writeAndFlush(bt);
                }
            }
        }).start();

    }






}
