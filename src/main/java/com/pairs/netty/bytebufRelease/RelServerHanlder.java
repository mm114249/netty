package com.pairs.netty.bytebufRelease;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created on 2017年07月27日11:26
 * <p>
 * Title:[]
 * </p >
 * <p>
 * Description :[]
 * </p >
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public class RelServerHanlder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<1;i++){
            ByteBuf byteBuf = ctx.alloc().directBuffer();
            byteBuf.writeBytes("abcd".getBytes());
            ctx.writeAndFlush(byteBuf);
        }
    }
}
