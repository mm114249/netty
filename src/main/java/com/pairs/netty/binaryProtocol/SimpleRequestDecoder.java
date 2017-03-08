package com.pairs.netty.binaryProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Created by hupeng on 2017/3/7.
 */
public class SimpleRequestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        byte magic=byteBuf.readByte();
        if(magic!=0x01){
            byteBuf.resetReaderIndex();
            return;
        }



        int id=byteBuf.readInt();
        int length=byteBuf.readInt();

        byte[] bodys=new byte[length];
        byteBuf.readBytes(bodys);
        String body=new String(bodys, CharsetUtil.UTF_8);

        SimpleRequest request=new SimpleRequest();

        request.setId(id);
        request.setBody(body);

        list.add(request);
    }

}
