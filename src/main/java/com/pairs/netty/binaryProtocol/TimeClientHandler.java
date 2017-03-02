package com.pairs.netty.binaryProtocol;

import com.pairs.netty.model.MarshallingModel;
import com.pairs.netty.model.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private int count;


    public TimeClientHandler() {
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MemcachedRequest request=new MemcachedRequest(Opcode.GET,"abcd");
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(msg);
        ctx.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
