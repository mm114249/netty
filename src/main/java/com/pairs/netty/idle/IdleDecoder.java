package com.pairs.netty.idle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hupeng on 2017/5/10.
 */
public class IdleDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<4){
            return;
        }

        int status=byteBuf.readInt();
        byte[] msg=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msg);
        String body=new String(msg);
        IdleModel model=new IdleModel(status,body);
        list.add(model);
    }
}
