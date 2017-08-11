package com.pairs.netty.hanlderDome;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

/**
 * Created on 2017年08月02日14:57
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
public class HanlderDncoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bt=new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bt);
        String type=new String(bt);
        list.add(type);
    }
}
