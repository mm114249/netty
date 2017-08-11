package com.pairs.netty.connectWatchDog;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2017年07月20日 11:42
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
public class DogClient {

    private static final EventExecutorGroup EVENT_EXECUTORS=new DefaultEventExecutorGroup(1,new DefaultThreadFactory("client.executor"));

    public static void main(String[] args) {
        new DogClient().test();
    }

    private void test(){
        Bootstrap bootstrap = new Bootstrap();
        HashedWheelTimer timer = new HashedWheelTimer();
         final ConnectWatchDog dog = new ConnectWatchDog(bootstrap, timer, "192.168.10.191", 7076) {
            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        new DogClientHandler()
                };
            }
        };

        bootstrap.group(new NioEventLoopGroup(1))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel socketChannel) throws Exception {
                        for (ChannelHandler channelHandler : dog.handlers()) {
                            socketChannel.pipeline().addLast(EVENT_EXECUTORS,channelHandler);
                        }
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("192.168.10.191", 7076);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("连接服务器成功");
                    } else {
                        System.out.println("连接服务器失败");
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
