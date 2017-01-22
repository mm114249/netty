package com.pairs.netty.nio;

import com.pairs.netty.bio.TimerServer;
import com.pairs.netty.bio.TimerServerUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeServer {

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel;
    private Integer port = 7060;

    private void init() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.out.println("异常TimerServer.init()->");
            e.printStackTrace();
        }
    }


    private void run() {
        while (true) {
            try {
                int num = selector.select(1000);
                if (num == 0) {
                    continue;
                }
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> it = keySet.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    handerInput(selectionKey);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handerInput(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
            SocketChannel sc=ssc.accept();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            read(selectionKey);
        }
    }


    private void read(SelectionKey selectionKey){
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
        try {
            int byteSize=socketChannel.read(buffer);
            if(byteSize>0){
                buffer.flip();
                byte[] bt=new byte[buffer.remaining()];
                buffer.get(bt);
                String body=new String(bt);
                String respons="QUERY TIME ORDER".equalsIgnoreCase(body)? TimerServerUtil.getCurrentTime():"BAD ORDER";
                write(socketChannel,respons);

            }else if(byteSize<0){
                selectionKey.cancel();
                socketChannel.close();
            }else {

            }

        } catch (IOException e) {
            e.printStackTrace();
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void write(SocketChannel socketChannel,String response){
        if(response!=null&&response.length()>0&&!"".equals(response)){
            byte[] bytes=response.getBytes();
            ByteBuffer byteBuffer=ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            try {
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        TimeServer ts = new TimeServer();
        ts.init();
        ts.run();

    }

}
