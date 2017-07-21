package com.pairs.netty.EchoServer;

import com.pairs.netty.codec.marshalling.MarshallingCodecFactory;
import com.pairs.netty.codec.msgpack.MsgpackDecoder;
import com.pairs.netty.codec.msgpack.MsgpackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClient {

    private int port=7076;

    private void connect(){
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                          socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));//使用半包读解码器
//                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("$_".getBytes())));//使用字符分割解码器
//                        socketChannel.pipeline().addLast(new StringDecoder());
//                        socketChannel.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
//                        socketChannel.pipeline().addLast("msgpack decoder ",new MsgpackDecoder());
//                        socketChannel.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
//                        socketChannel.pipeline().addLast("msgpack encoder ",new MsgpackEncoder());


//                        socketChannel.pipeline().addLast(MarshallingCodecFactory.buildMarshallingDecoder());
//                        socketChannel.pipeline().addLast(MarshallingCodecFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new TimeClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture=bootstrap.connect("127.0.0.1",port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new TimeClient().connect();
    }
}
