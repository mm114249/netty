package com.pairs.netty.bytebuf;

import org.junit.Test;

/**
 * Created on 2017年07月26日11:19
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
public class ThreadLocalTest {


    @Test
    public void test(){
        ThreadLocal<String> threadLocal=new ThreadLocal<>();
        for (int i = 0; i < 2; i++) {
            final int j=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocal.set(""+j);
                    System.out.println(threadLocal.get());
                }
            }).start();
        }

    }


}
