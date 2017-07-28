package com.pairs.netty.bytebuf;

import org.junit.Test;

import java.util.Random;

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

    private ThreadLocal<String> t=new ThreadLocal<String>();
    private class MyRunable implements Runnable{
        @Override
        public void run() {
            Random r=new Random();
            String code=String.valueOf(r.nextInt(100));
            t.set(code);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t.get());
        }
    }


    public static void main(String[] args) {
        ThreadLocalTest t=new ThreadLocalTest();
        t.run();
    }


    public void run(){
        MyRunable r=new MyRunable();
        new Thread(r).start();
        new Thread(r).start();
    }

}
