package com.pairs.netty.idle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by hupeng on 2017/5/10.
 */
public class IdleEncoder extends MessageToByteEncoder<IdleModel> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IdleModel idleModel, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(idleModel.getStatus());
        byteBuf.writeBytes(idleModel.getBody().getBytes());
    }
}
