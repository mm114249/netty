package com.pairs.netty.protocol.adapter;

import com.pairs.netty.protocol.MessageType;
import com.pairs.netty.protocol.model.Header;
import com.pairs.netty.protocol.model.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hupeng on 2017/2/6.
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private Map<String,Boolean> nodeCheck=new ConcurrentHashMap<String,Boolean>();

    private String[] whitekList={"127.0.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage nettyMessage= (NettyMessage) msg;
        if(nettyMessage.getHeader()!=null && nettyMessage.getHeader().getType()== MessageType.LOGIN_REQ.value()){
            String nodeIndex=ctx.channel().remoteAddress().toString();
            NettyMessage loginResp=null;
            if(nodeCheck.containsKey(nodeIndex)){
                loginResp=buildResponse((byte)-1);
            }else {
                InetSocketAddress address= (InetSocketAddress) ctx.channel().remoteAddress();
                String ip=address.getAddress().getHostAddress();
                boolean isOk=false;
                for(String WIP:whitekList){
                    if(WIP.equals(ip)){
                        isOk=true;
                        break;
                    }
                }
                loginResp=isOk?buildResponse((byte) 1):buildResponse((byte)-1);

                if(isOk){
                    nodeCheck.put(nodeIndex,true);
                }

                System.out.println("the login response is :" +loginResp+" body ["+loginResp.getBody()+"]");
                ctx.writeAndFlush(loginResp);
            }

        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result){
        NettyMessage nettyMessage=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        nettyMessage.setHeader(header);
        nettyMessage.setBody(result);
        return  nettyMessage;
    }
}
