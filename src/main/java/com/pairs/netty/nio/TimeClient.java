package com.pairs.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private Integer port = 7060;
    private boolean stop = true;


    private void init() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void run() {
        doConnect();
        try {
            while (stop) {
                int num = selector.select(1000);
                if (num == 0) {
                    continue;
                }
                Set<SelectionKey> sets = selector.selectedKeys();
                Iterator<SelectionKey> it = sets.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (key.isValid()) {
                        if (key.isConnectable()) {
                            if (channel.finishConnect()) {
                                channel.register(selector, SelectionKey.OP_READ);
                                write(socketChannel);
                            }
                        }

                        if (key.isReadable()) {
                            doRead(channel,key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void doConnect() {
        boolean isConnect = false;
        try {
            isConnect = socketChannel.connect(new InetSocketAddress("127.0.0.1", port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (isConnect) {
                write(socketChannel);
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    private void doRead(SocketChannel channel, SelectionKey key) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int byteSize = channel.read(buffer);
            if (byteSize > 0) {
                buffer.flip();
                byte[] btArray = new byte[buffer.remaining()];
                buffer.get(btArray);
                String body = new String(btArray);
                System.out.println("当前时间为" + body);
                stop = false;
            } else if (byteSize < 0) {
                key.cancel();
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            key.cancel();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void write(SocketChannel channel) {
        String mes = "query time order";
        byte[] bytes = mes.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        try {
            channel.write(byteBuffer);
            byteBuffer.compact();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TimeClient client = new TimeClient();
        client.init();
        client.run();
    }

}
