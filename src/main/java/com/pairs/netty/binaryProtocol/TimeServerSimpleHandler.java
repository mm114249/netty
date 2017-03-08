package com.pairs.netty.binaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by hupeng on 2017/3/7.
 */
public class TimeServerSimpleHandler extends ChannelHandlerAdapter {

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
