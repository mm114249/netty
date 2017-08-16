package com.pairs.netty.connectWatchDog;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Scanner;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Scanner scanner = new Scanner(System.in);
                    String str=scanner.nextLine();
                    System.out.println("输入命令:"+str);
                    ByteBuf bt= Unpooled.buffer();
                    bt.writeBytes("aaa".getBytes());
                    ctx.channel().close();
                }
            }
        }).start();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if(evt.getClass().isAssignableFrom(IdleStateEvent.class)){
//            IdleStateEvent event= (IdleStateEvent) evt;
//            if(event.state()== IdleState.READER_IDLE){
//                ctx.channel().close();
//            }
//        }
        super.userEventTriggered(ctx, evt);
    }
}
