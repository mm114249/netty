package com.pairs.netty.idle;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by hupeng on 2017/5/10.
 */
public class CloseListener implements ChannelFutureListener {

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if(channelFuture.isDone()){
            System.out.println("通道关闭:"+channelFuture.channel().isActive());
        }
    }
}
