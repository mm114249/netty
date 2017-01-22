package com.pairs.netty.EchoServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private int count;



    public TimeClientHandler(){
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String body="QUERY TIME ORDER"+System.getProperty("line.separator");
        byte[] bytes=body.getBytes();
        ByteBuf byteBuf=null;
        for(int i=0;i<100;i++){
            byteBuf= Unpooled.copiedBuffer(bytes);
            byteBuf.writableBytes();
            ctx.writeAndFlush(byteBuf);
        }


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf readBuf= (ByteBuf) msg;
//        byte[] bytes=new byte[readBuf.readableBytes()];
//        readBuf.readBytes(bytes);
//        String body=new String(bytes,"utf-8");
        String body= (String) msg;
        System.out.println("receive server count "+ ++count);
        System.out.println(body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
