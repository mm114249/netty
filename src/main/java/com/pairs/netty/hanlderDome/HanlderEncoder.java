package com.pairs.netty.hanlderDome;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;

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
public class HanlderEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        byte[] code=s.getBytes();
        byteBuf.writeBytes(code);
        channelHandlerContext.channel().writeAndFlush(byteBuf);
        ReferenceCountUtil.release(byteBuf);
    }
}
