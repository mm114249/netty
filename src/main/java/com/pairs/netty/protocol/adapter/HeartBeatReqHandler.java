package com.pairs.netty.protocol.adapter;

import com.pairs.netty.protocol.MessageType;
import com.pairs.netty.protocol.model.Header;
import com.pairs.netty.protocol.model.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

/**
 * Created by hupeng on 2017/2/6.
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        NettyMessage nettyMessage= (NettyMessage) msg;
        if(nettyMessage.getHeader()!=null&& nettyMessage.getHeader().getType()== MessageType.LOGIN_RESP.value()){
            //握手成功,主动发送心跳消息
            heartBeat=ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else if(nettyMessage.getHeader()!=null && nettyMessage.getHeader().getType()==MessageType.HEARTBEAT_RESP.value()){
            System.out.println("client receive server heart beat message : -->"+nettyMessage);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(heartBeat!=null){
            heartBeat.cancel(true);
            heartBeat=null;
        }
        ctx.fireExceptionCaught(cause);
    }


    private class HeartBeatTask implements Runnable{
        private final ChannelHandlerContext ctx;

        private HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage nettyMessage=buildHeatBeat();
            System.out.println("client send heart beat message to server :-->"+nettyMessage);
            ctx.writeAndFlush(nettyMessage);
        }

        private NettyMessage buildHeatBeat(){
            NettyMessage nettyMessage=new NettyMessage();
            Header header=new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            nettyMessage.setHeader(header);
            return nettyMessage;
        }
    }

}
