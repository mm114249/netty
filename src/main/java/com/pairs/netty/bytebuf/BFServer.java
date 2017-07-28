package com.pairs.netty.bytebuf;

import com.pairs.netty.EchoServer.ServerHanlder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * Created by hupeng on 2017/1/19.
 */
public class BFServer {

    private int port=7076;

    private void bind(){
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        ServerBootstrap boot=new ServerBootstrap();
        boot.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChildrenChannelHanler());
        try {
            ChannelFuture future=boot.bind(port).sync();
            System.out.println("server start...");
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
            channel.pipeline().addLast(new BFServerHanlder());
           // channel.pipeline().addLast(new BFEncode());
        }

    }

    public static void main(String[] args) {
        BFServer timeServer=new BFServer();
        timeServer.bind();
    }

}
