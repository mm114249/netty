package com.pairs.netty.bytebuf;

/**
 * Created by hupeng on 2017/7/26.
 */
public class BFModel {
    private String namge;
    private int age;

    public BFModel(String namge, int age) {
        this.namge = namge;
        this.age = age;
    }

    public String getNamge() {
        return namge;
    }

    public void setNamge(String namge) {
        this.namge = namge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
