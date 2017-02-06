package com.pairs.netty.protocol.adapter;

import com.pairs.netty.protocol.MessageType;
import com.pairs.netty.protocol.model.Header;
import com.pairs.netty.protocol.model.NettyMessage;
import com.sun.org.apache.xpath.internal.SourceTree;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by hupeng on 2017/2/6.
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage= (NettyMessage) msg;
        if(nettyMessage.getHeader()!=null&&nettyMessage.getHeader().getType()== MessageType.HEARTBEAT_REQ.value()){
            System.out.println("server receive heart beat message : -->"+nettyMessage);
            NettyMessage respon=buildHeartBeat();
            System.out.println("server send hear beat respon message to client : -->"+respon);
            ctx.writeAndFlush(respon);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeartBeat(){
        NettyMessage nettyMessage=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        nettyMessage.setHeader(header);
        return nettyMessage;
    }
}
