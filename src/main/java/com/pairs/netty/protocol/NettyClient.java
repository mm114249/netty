package com.pairs.netty.protocol;

import com.pairs.netty.protocol.adapter.HeartBeatReqHandler;
import com.pairs.netty.protocol.adapter.LoginAuthReqHandler;
import com.pairs.netty.protocol.codec.NettyMessageDecoder;
import com.pairs.netty.protocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hupeng on 2017/2/6.
 */
public class NettyClient {
    private ScheduledExecutorService executor= Executors.newScheduledThreadPool(1);
    EventLoopGroup group=new NioEventLoopGroup();

    private void connect(){
        Bootstrap b=new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                        socketChannel.pipeline().addLast(new NettyMessageEncoder());
                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(50));
                        socketChannel.pipeline().addLast(new LoginAuthReqHandler());
                        socketChannel.pipeline().addLast(new HeartBeatReqHandler());

                    }
                });

        try {

            ChannelFuture future=b.connect( new InetSocketAddress(NettyConstant.REMOTEIP, NettyConstant.PORT),
                    new InetSocketAddress(NettyConstant.LOCALIP,NettyConstant.LOCAL_PORT)).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connect();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new NettyClient().connect();
    }
}
