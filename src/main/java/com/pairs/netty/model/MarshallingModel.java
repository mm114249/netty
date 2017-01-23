package com.pairs.netty.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/1/25.
 */
public class MarshallingModel implements Serializable {

    private static final long serialVersionUID = 955565834494047834L;
    private Integer id;
    private String name;

    public MarshallingModel(int id){
        this.id=id;
        this.name="ABCD"+id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MarshallingModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
