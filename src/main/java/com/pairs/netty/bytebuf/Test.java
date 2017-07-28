package com.pairs.netty.bytebuf;

import java.io.Console;
import java.util.Scanner;

/**
 * Created on 2017年07月28日16:23
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
public class Test {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Scanner scanner = new Scanner(System.in);
                    String str=scanner.nextLine();
                    System.out.println("输入的是"+str);
                }
            }
        }).start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
