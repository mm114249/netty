package com.pairs.netty.idle;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by hupeng on 2017/5/10.
 */
public class ConnectListener implements ChannelFutureListener {

    private IdleClient client;
    public ConnectListener(IdleClient client) {
        this.client = client;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if(!channelFuture.isSuccess()){
            int count=client.reconnectCount.incrementAndGet();
            if(count>3){
                System.exit(-2);
            }
            System.out.println("连接失败开始重新连接");
            channelFuture.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    client.doConnnect();
                }
            },3l, TimeUnit.SECONDS);
        }else if(channelFuture.isSuccess()) {
            ChannelFuture closeFuture=channelFuture.channel().closeFuture();
            closeFuture.addListener(new CloseListener());
        }
    }
}
