package com.pairs.netty.bytebuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hupeng on 2017/7/26.
 */
public class BFClientHanlder extends SimpleChannelInboundHandler<BFModel> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BFModel bfModel) throws Exception {
        System.out.println(bfModel);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
