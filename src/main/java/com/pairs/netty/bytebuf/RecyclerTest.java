package com.pairs.netty.bytebuf;

import io.netty.handler.codec.http.HttpClientUpgradeHandler;
import io.netty.util.Recycler;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2017年07月25日 14:28
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
public class RecyclerTest {


    @Test
    public void test(){
        System.setProperty("io.netty.recycler.ratio","1");

        Recycler<RecyclerModel> recycler=new Recycler<RecyclerModel>() {
            @Override
            protected RecyclerModel newObject(Handle<RecyclerModel> handle) {
                Random r=new Random();
                RecyclerModel model=new RecyclerModel(r.nextInt(100),handle);
                return model;
            }
        };

        RecyclerModel model1 = recycler.get();
        System.out.println(model1);
        RecyclerModel model2 = recycler.get();
        System.out.println(model2);
        RecyclerModel model3 = recycler.get();
        System.out.println(model3);

        RecyclerModel model5 = recycler.get();
        System.out.println(model5);
        RecyclerModel model6 = recycler.get();
        System.out.println(model6);
        RecyclerModel model7 = recycler.get();
        System.out.println(model7);

        model1.recycle();model2.recycle();model3.recycle();model5.recycle();model6.recycle();model7.recycle();


//        System.out.println(recycler.get());
//        System.out.println(recycler.get());
//        System.out.println(recycler.get());
//        System.out.println(recycler.get());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test1(){

        for(int i=-1;i<32;i++){
            System.out.println("index is "+i+" ---> "+(i&0));
        }
    }
}
