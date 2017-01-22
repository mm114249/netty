package com.pairs.netty.EchoServer;

import com.pairs.netty.bio.TimerServerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * Created by hupeng on 2017/1/19.
 */
public class ServerHanlder extends ChannelHandlerAdapter {

    private int count;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf= (ByteBuf) msg;
//        byte[] bt=new byte[buf.readableBytes()];
//        buf.readBytes(bt);
//        String body=new String(bt,"utf-8").substring(0,bt.length- System.getProperty("line.separator").length());
        String body= (String) msg;
        System.out.println("recever client msg is "+body+"。current count is "+ ++count);
        String response="QUERY TIME ORDER".equalsIgnoreCase(body)? TimerServerUtil.getCurrentTime():"BAD ORDER";
        response+=System.getProperty("line.separator");
        ByteBuf responseBuf=Unpooled.copiedBuffer(response.getBytes());
        ctx.write(responseBuf);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
