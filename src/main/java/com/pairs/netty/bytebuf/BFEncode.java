package com.pairs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hupeng on 2017/7/26.
 */
public class BFEncode extends MessageToByteEncoder<BFModel> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BFModel bfModel, ByteBuf byteBuf) throws Exception {
        String magic="$_";
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer();
        buf.writeBytes(magic.getBytes());
        buf.writeBytes(bfModel.getNamge().getBytes());
        buf.writeInt(bfModel.getAge());
        channelHandlerContext.writeAndFlush(buf);
    }

}
