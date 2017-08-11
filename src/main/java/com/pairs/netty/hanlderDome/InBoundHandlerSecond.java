package com.pairs.netty.hanlderDome;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created on 2017年08月02日15:00
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
public class InBoundHandlerSecond extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("invactive");
    }
}
