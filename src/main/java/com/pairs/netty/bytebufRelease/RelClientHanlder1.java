package com.pairs.netty.bytebufRelease;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by hupeng on 2017/7/26.
 */
public class RelClientHanlder1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            System.out.println(1111);
        }
       // ctx.fireChannelRead(msg);
    }

    
}
