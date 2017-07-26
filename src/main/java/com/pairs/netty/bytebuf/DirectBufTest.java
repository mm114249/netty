package com.pairs.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;
import org.junit.Test;

/**
 * Created on 2017年07月26日14:38
 * <p>
 * Title:[]
 * </p >
 * <p>
 * Description :[]
 * </p >
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public class DirectBufTest {

    @Test
    public void test(){
        for(int i=0;i<400000;i++){
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
            ReferenceCountUtil.release(byteBuf);
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
