package com.pairs.netty.binaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by hupeng on 2017/3/7.
 */
public class TimeClientSimpleHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String id=ctx.channel().id().asLongText();
        HandlerUtil.put(id,ctx);

        byte param=0x02;
        SimpleRequest request=new SimpleRequest();
        request.setId(1);
        //request.setBody("aaa");
        ctx.writeAndFlush(request);


        Thread.sleep(20000);

        request=new SimpleRequest();

        request.setId(1);
        request.setBody("aaa");
        ctx.writeAndFlush(request);

//        SimpleRequest request=new SimpleRequest();
//        request.setId(1);
//        request.setBody("aaa");
//
//        for(int i=0;i<1;i++){
//            ctx.writeAndFlush(request);
//        }
    }
}
