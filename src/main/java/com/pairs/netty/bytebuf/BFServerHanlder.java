package com.pairs.netty.bytebuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
        BFModel bfModel=new BFModel("aaaa",11);
        ctx.channel().writeAndFlush(bfModel);
    }






}
