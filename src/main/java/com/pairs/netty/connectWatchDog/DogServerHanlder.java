package com.pairs.netty.connectWatchDog;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created on 2017年07月20日 11:14
 * <P>
 * Title:[]
 * </p>
 * <p>
 * Description :[]
 * </p>
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public class DogServerHanlder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 已连接上服务器端");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt.getClass().isAssignableFrom(IdleStateEvent.class)){
            IdleStateEvent event= (IdleStateEvent) evt;
            if(event.state()== IdleState.READER_IDLE){
                ctx.channel().close();
            }
        }
    }
}
