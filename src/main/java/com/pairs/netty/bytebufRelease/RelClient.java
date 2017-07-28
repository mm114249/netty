package com.pairs.netty.bytebufRelease;

import com.pairs.netty.bytebuf.BFClientHanlder;
import com.pairs.netty.bytebuf.BFDecode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by hupeng on 2017/1/19.
 */
public class RelClient {
    private int port = 7076;

    private void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new RelClientHanlder());
                        socketChannel.pipeline().addLast(new RelClientHanlder1());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new RelClient().connect();
    }
}
