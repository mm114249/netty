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
public class InBoundHandlerFirst extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String type= (String) msg;
        if("1".equals(type)){
            ctx.fireChannelRead(msg);
        }else{
            ctx.fireChannelInactive();
        }
    }
}
