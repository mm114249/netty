package com.pairs.netty.binaryProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by hupeng on 2017/3/2.
 */
public class MemcachedRequestEncoder extends MessageToByteEncoder<MemcachedRequest> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MemcachedRequest msg, ByteBuf out) throws Exception {
        byte[] key = msg.key().getBytes(CharsetUtil.UTF_8);
        byte[] body=msg.body().getBytes();
        int bodysize=key.length+body.length+(msg.hasExtras()?8:0);
        out.writeByte(msg.magic());
        out.writeByte(msg.opCode());
        out.writeShort(key.length);
        int extraSize=msg.hasExtras()?0x08:0x0;
        out.writeByte(extraSize);
        out.writeByte(0);
        out.writeShort(0);

        out.writeInt(bodysize);
        out.writeInt(msg.id());
        out.writeLong(msg.cas());

        if(msg.hasExtras()){
            out.writeInt(msg.flags());
            out.writeInt(msg.expires());
        }

        out.writeBytes(key);
        out.writeBytes(body);

    }
}

