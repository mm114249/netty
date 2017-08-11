package com.pairs.netty.EchoServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;

import java.util.concurrent.TimeUnit;


/**
 * Created by hupeng on 2017/1/19.
 */
public class ServerHanlder extends ChannelInboundHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //不是用解码器接收消息
//        ByteBuf buf= (ByteBuf) msg;
//        byte[] bt=new byte[buf.readableBytes()];
//        buf.readBytes(bt);
//        String body=new String(bt,"utf-8").substring(0,bt.length- System.getProperty("line.separator").length());


//        String body= (String) msg;
//        System.out.println("recever client msg is "+body+"。current count is "+ ++count);
//        String response="QUERY TIME ORDER".equalsIgnoreCase(body)? TimerServerUtil.getCurrentTime():"BAD ORDER";
        //response+=System.getProperty("line.separator");//使用半包读解码器接收消息
//        response+="$_";//使用分隔符解码器'
        ctx.writeAndFlush(msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel close");
        super.channelInactive(ctx);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel open");
        ctx.channel().eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(ctx.channel().isOpen());
                System.out.println(ctx.channel().isActive());
            }
        },2,2, TimeUnit.SECONDS);
        super.channelActive(ctx);
    }






}
