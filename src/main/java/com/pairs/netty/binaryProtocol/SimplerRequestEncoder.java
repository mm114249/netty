package com.pairs.netty.binaryProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.StringUtil;

/**
 * Created by hupeng on 2017/3/7.
 */
public class SimplerRequestEncoder extends MessageToByteEncoder<SimpleRequest> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SimpleRequest simpleRequest, ByteBuf byteBuf) throws Exception {
//        byteBuf.writeByte(simpleRequest.getMagic());
//        byteBuf.writeInt(simpleRequest.getId());
//
//        if(simpleRequest.getBody()!=null){
//            int length=simpleRequest.getBody().getBytes().length;
//            byteBuf.writeInt(length);
//            byteBuf.writeBytes(simpleRequest.getBody().getBytes());
//        }

        if(simpleRequest.getBody()==null){
            byteBuf.writeByte(simpleRequest.getMagic());
            byteBuf.writeInt(simpleRequest.getId());
        }else {
            int length=simpleRequest.getBody().getBytes().length;
            byteBuf.writeInt(length);
            byteBuf.writeBytes(simpleRequest.getBody().getBytes());
        }

    }
}
