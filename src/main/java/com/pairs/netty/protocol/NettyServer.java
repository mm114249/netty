package com.pairs.netty.protocol;

import com.pairs.netty.protocol.adapter.HeartBeatRespHandler;
import com.pairs.netty.protocol.adapter.LoginAuthRespHandler;
import com.pairs.netty.protocol.codec.NettyMessageDecoder;
import com.pairs.netty.protocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by hupeng on 2017/2/6.
 */
public class NettyServer {
    public void bind(){
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup wokerGroup=new NioEventLoopGroup();
        ServerBootstrap b=new ServerBootstrap();
        b.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1000)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                        socketChannel.pipeline().addLast(new NettyMessageEncoder());
                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(50));
                        socketChannel.pipeline().addLast(new LoginAuthRespHandler());
                        socketChannel.pipeline().addLast(new HeartBeatRespHandler());
                    }
                });

        try {
             ChannelFuture future=b.bind(NettyConstant.REMOTEIP,NettyConstant.PORT).sync();
             future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new NettyServer().bind();
    }
}
