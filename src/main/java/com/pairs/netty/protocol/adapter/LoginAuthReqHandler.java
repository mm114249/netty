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
public class LoginAuthReqHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message= (NettyMessage) msg;
        if(message.getHeader()!=null && message.getHeader().getType()==MessageType.LOGIN_RESP.value()){
            byte loginResult= (byte) message.getBody();
            if(loginResult!=(byte)0){
                ctx.close();
            }else {
                System.out.println("Login is ok "+ message);
                ctx.fireChannelRead(msg);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildLoginReq(){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }
}
