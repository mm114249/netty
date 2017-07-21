package com.pairs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import org.junit.Test;

/**
 * Created on 2017年07月11日 9:16
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
public class ByteBufReadIndex {

    @Test
    public void test1(){
        ByteBuf byteBuf= Unpooled.buffer(4);
        byteBuf.writeInt(10);
        System.out.println(byteBuf.getByte(3));

        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
    }

    @Test
    public void clear(){
        ByteBuf byteBuf= Unpooled.buffer(10);
        byteBuf.writeInt(1);

        System.out.println(byteBuf.readerIndex()+"_"+byteBuf.writerIndex());
        byteBuf.clear();
        System.out.println(byteBuf.readerIndex()+"_"+byteBuf.writerIndex());
        byteBuf.writeLong(1);
        System.out.println(byteBuf.readerIndex()+"_"+byteBuf.writerIndex());
    }

    @Test
    public void ByteProcess(){
        ByteBuf byteBuf= Unpooled.buffer(10);
        byteBuf.writeBytes("\n\r".getBytes());
        byteBuf.writeInt(1);
        System.out.println(byteBuf.readerIndex()+"_"+byteBuf.writerIndex());
        try {
            System.out.println(byteBuf.forEachByte(ByteProcessor.FIND_NON_CRLF));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void slice(){
        ByteBuf byteBuf=Unpooled.copiedBuffer("aabbccddeeff", CharsetUtil.UTF_8);
        ByteBuf buf = byteBuf.slice(0, 4);
        byte[] b=new byte[buf.readableBytes()];
        buf.readBytes(b);
        System.out.println(new String(b));
    }

    @Test
    public void byteBufUtil(){
        ByteBuf buffer = Unpooled.buffer(10);
        buffer.writeBytes("abcderf".getBytes());
        String hexDump = ByteBufUtil.hexDump(buffer);
        System.out.println(hexDump);
    }


}
