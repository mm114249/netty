package com.pairs.netty.fileServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by admin on 2017/1/24.
 */
public class FileServer {

    private int port=7082;

    private void bind(){
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        ServerBootstrap boot=new ServerBootstrap();
        boot.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChildrenChannelHanler());
        try {
            ChannelFuture future=boot.bind("127.0.0.1",port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    private class ChildrenChannelHanler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            channel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
            channel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
            channel.pipeline().addLast("http-encoder",new HttpResponseEncoder());
            channel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
            channel.pipeline().addLast(new HttpFileServerHandler());
        }

    }

    public static void main(String[] args) {
        new FileServer().bind();
    }
}
