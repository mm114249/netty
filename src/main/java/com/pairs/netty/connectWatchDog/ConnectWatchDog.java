package com.pairs.netty.connectWatchDog;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2017年07月20日 11:18
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
@ChannelHandler.Sharable
public abstract  class ConnectWatchDog extends ChannelInboundHandlerAdapter implements TimerTask,ChannelHandlerHolder  {

    private Bootstrap bootstrap;
    private Timer timer;
    private String host;
    private Integer port;
    private boolean connected=true;
    private int attemqts;

    public ConnectWatchDog(Bootstrap bootstrap, Timer timer, String host, Integer port) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                for (ChannelHandler handler : handlers()) {
                    channel.pipeline().addLast(handler);
                }
            }
        });
        bootstrap.connect(host,port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("重连成功");
                }else{
                    System.out.println("重连失败");
                    channelFuture.channel().pipeline().fireChannelInactive();
                }
            }
        });


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链路已连接");
        ctx.fireChannelActive();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链路断开,开始重连");
        timer.newTimeout(this,2, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }
}
