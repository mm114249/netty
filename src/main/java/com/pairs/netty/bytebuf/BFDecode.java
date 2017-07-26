package com.pairs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by hupeng on 2017/7/26.
 */
public class BFDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("in decode function");
        if(byteBuf.readableBytes()<2){
            return;
        }

        byteBuf.markReaderIndex();

        byte[] mgArray=new byte[2];
        byteBuf.readBytes(mgArray);
        String magic=new String(mgArray);

        if(!magic.equals("$_")){
            byteBuf.resetReaderIndex();
            return;
        }


        byte[] nameArray=new byte[4];
        byteBuf.readBytes(nameArray);
        String name=new String(nameArray);
        int age=byteBuf.readInt();
        BFModel bfModel=new BFModel(name,age);
        list.add(bfModel);
    }
}
