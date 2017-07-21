package com.pairs.netty.hanlderDome;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created on 2017年07月03日 10:10
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
public class HanlderClient implements Runnable  {

    private EventLoopGroup group=null;

    private int index;

    public HanlderClient(){

    }

    public HanlderClient(int index,EventLoopGroup group){
        this.index=index;
        this.group=group;
    }

    public static void main(String[] args) {
        EventLoopGroup group=new NioEventLoopGroup(20);
        for(int i=0;i<20;i++){
            new Thread(new HanlderClient(i,group)).start();
        }

    }



    @Override
    public void run() {
        Bootstrap b=new Bootstrap();
        b.group(group).option(ChannelOption.TCP_NODELAY,true).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));//使用半包读解码器
                        socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(3));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                System.out.println(s);
                            }
                        });
                        socketChannel.pipeline().addLast(new StringEncoder());
                    }
                });
        try {
            ChannelFuture channelFuture = b.connect("127.0.0.1", 7070).sync();
            channelFuture.addListener (new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        String code=String.valueOf(index);
                        if(code.length()==1){
                            code="00"+code;
                        }else if (code.length()==2){
                            code="0"+code;
                        }
                        for(int j=0;j<1;j++){
                            Thread.sleep(200);
                            channelFuture.channel().writeAndFlush(String.valueOf(code));
                        }
                    }
                }
            });
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
