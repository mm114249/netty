package com.pairs.netty.codec.msgpack;

import com.pairs.netty.model.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * msg 编码器
 * Created by admin on 2017/1/24.
 */
public class MsgpackEncoder extends MessageToByteEncoder<UserInfo> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, UserInfo o, ByteBuf byteBuf) throws Exception {
        MessagePack messagePack=new MessagePack();
        try{
            byte[] message=messagePack.write(o);
            byteBuf.writeBytes(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
