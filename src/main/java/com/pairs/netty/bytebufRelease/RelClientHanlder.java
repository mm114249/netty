package com.pairs.netty.bytebufRelease;

import com.pairs.netty.bytebuf.BFModel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hupeng on 2017/7/26.
 */
public class RelClientHanlder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = ctx.alloc().heapBuffer();
        if(byteBuf.isReadable()){
            byte[] bt=new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bt);
            System.out.println("hanlder read is "+new String(bt));
        }
        ctx.fireChannelRead(byteBuf);
    }


}
