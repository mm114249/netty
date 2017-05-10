package com.pairs.netty.idle;

import com.pairs.netty.codec.msgpack.MsgpackDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by hupeng on 2017/5/9.
 */
public class IdleServer {

    private int port=7072;

    private void bind(){
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup work=new NioEventLoopGroup();

        ServerBootstrap starp=new ServerBootstrap();
        starp.group(boss,work).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    private int READ_TIME_OUT=4;
                    private int WRITE_TIME_OUT=5;
                    private int IDLE_TIME_OUT=10;

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new IdleDecoder());
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                IdleModel model= (IdleModel) msg;
                                System.out.println(model);
                                if(model.getStatus()==1){
                                    IdleModel idleModel=new IdleModel(1,"服务器端pong消息");
                                   // ctx.channel().writeAndFlush(idleModel);
                                }
                            }


                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                              //  ctx.close();
                            }
                        });
                        socketChannel.pipeline().addLast(new IdleEncoder());
                    }
                });
        ChannelFuture f = null;
        try {
            f = starp.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new IdleServer().bind();
    }

}
