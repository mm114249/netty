package com.pairs.netty.idle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Created by hupeng on 2017/5/10.
 */
public class IdleClient {

    public  Bootstrap bootstrap = new Bootstrap();
    public  EventLoopGroup group=new NioEventLoopGroup();
    public  AtomicInteger statusCount = new AtomicInteger(0);
    public  AtomicInteger reconnectCount=new AtomicInteger(0);
    public static int port = 7072;
    protected ConnectListener connectListener = new ConnectListener(this);

    public void initBootstrap() {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new IdleDecoder());
                        socketChannel.pipeline().addLast(new IdleStateHandler(4, 4, 10));
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                IdleModel model = (IdleModel) msg;
                                System.out.println(model);
                                if (model.getStatus() == 1) {
                                    statusCount.decrementAndGet();
                                }
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                ctx.channel().eventLoop().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        doConnnect();
                                    }
                                }, 2, TimeUnit.SECONDS);
                            }


                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

                                if (evt.getClass().isAssignableFrom(IdleStateEvent.class)) {
                                    IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                                    if (idleStateEvent.state() == IdleState.READER_IDLE) {
                                        int count = statusCount.incrementAndGet();
                                        if (count > 3) {
                                            ctx.close();
                                            statusCount.set(0);
                                        }

                                    } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                                        System.out.println("客服端开始读空闲");
                                        IdleModel idleModel = new IdleModel(1, "客户端ping消息");
                                        ctx.channel().writeAndFlush(idleModel);
                                    }
                                }


                            }
                        });
                        socketChannel.pipeline().addLast(new IdleEncoder());

                    }
                });
    }


    public void doConnnect() {
        System.out.println("---服务器连接开始---");
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", port);
        channelFuture.addListener(connectListener);
    }


    public static void main(String[] args) {
        IdleClient client = new IdleClient();
        client.initBootstrap();
        client.doConnnect();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
