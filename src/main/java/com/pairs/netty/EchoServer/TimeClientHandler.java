package com.pairs.netty.EchoServer;

import com.pairs.netty.model.MarshallingModel;
import com.pairs.netty.model.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private int count;


    public TimeClientHandler() {
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String body="QUERY TIME ORDER"+System.getProperty("line.separator");
//        String body="QUERY TIME ORDER"+"$_";
        byte[] bytes=body.getBytes();
        ByteBuf byteBuf=null;
        for(int i=0;i<100;i++){
            byteBuf= Unpooled.copiedBuffer(bytes);
            byteBuf.writableBytes();
            ctx.writeAndFlush(byteBuf);
        }

//        int loop = 100;
//        UserInfo[] userInfos=getUserInfos(loop);
//        for(UserInfo userInfo:userInfos){
//            ctx.writeAndFlush(userInfo);
//        }

//        MarshallingModel[] marshallingModels=getMarshallingModel(loop);
//        for(MarshallingModel marshallingModel:marshallingModels){
//            ctx.writeAndFlush(marshallingModel);
//        }


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf readBuf= (ByteBuf) msg;
//        byte[] bytes=new byte[readBuf.readableBytes()];
//        readBuf.readBytes(bytes);
//        String body=new String(bytes,"utf-8");
//        String body= (String) msg;
//        System.out.println("receive server count "+ ++count);
//        System.out.println(body);

        System.out.println(msg);
        ctx.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private UserInfo[] getUserInfos(int length) {
        UserInfo[] userInfos = new UserInfo[length];
        for (int i = 0; i < length; i++) {
            UserInfo userInfo = new UserInfo(i, new Date());
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

    private MarshallingModel[] getMarshallingModel(int length) {
        MarshallingModel[] models = new MarshallingModel[length];
        for (int i = 0; i < length; i++) {
            MarshallingModel model = new MarshallingModel(i);
            models[i] = model;
        }

        return models;
    }
}
