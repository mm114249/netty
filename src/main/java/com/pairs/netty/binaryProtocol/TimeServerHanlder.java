package com.pairs.netty.binaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeServerHanlder extends ChannelHandlerAdapter {

    private static int index=0;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MemcachedRequest request= (MemcachedRequest) msg;
        index++;
        System.out.println(index);
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
