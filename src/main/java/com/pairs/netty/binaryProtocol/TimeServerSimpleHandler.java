package com.pairs.netty.binaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by hupeng on 2017/3/7.
 */
public class TimeServerSimpleHandler extends ChannelInboundHandlerAdapter {

    private static int index=0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(!(msg instanceof SimpleRequest)){
            ctx.fireChannelRead(msg);
            return;
        }

        SimpleRequest request= (SimpleRequest) msg;

        index++;
        System.out.println(index);

    }

}
