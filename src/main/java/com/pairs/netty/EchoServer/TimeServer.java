package com.pairs.netty.EchoServer;

import com.pairs.netty.codec.marshalling.MarshallingCodecFactory;
import com.pairs.netty.codec.msgpack.MsgpackDecoder;
import com.pairs.netty.codec.msgpack.MsgpackEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;


/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeServer {

    private int port=7081;

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
//            channel.pipeline().addLast(new LineBasedFrameDecoder(1024)); //使用半包解码器
//            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer("$_".getBytes())));//使用分隔符解码器
//            channel.pipeline().addLast(new FixedLengthFrameDecoder(16));
//            channel.pipeline().addLast(new StringDecoder());
//            channel.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
//            channel.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
//            channel.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
//            channel.pipeline().addLast("msgpack encoder",new MsgpackEncoder());


            channel.pipeline().addLast(MarshallingCodecFactory.buildMarshallingDecoder());
            channel.pipeline().addLast(MarshallingCodecFactory.buildMarshallingEncoder());
            channel.pipeline().addLast(new ServerHanlder());
        }

    }

    public static void main(String[] args) {
        TimeServer timeServer=new TimeServer();
        timeServer.bind();
    }



}
