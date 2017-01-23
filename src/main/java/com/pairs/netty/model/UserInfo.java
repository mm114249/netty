package com.pairs.netty.model;

import org.msgpack.annotation.Message;

import java.util.Date;

/**
 * Created by admin on 2017/1/24.
 */
@Message
public class UserInfo {

    private Integer id;
    private Date bothday;

    public UserInfo() {
    }

    public UserInfo(Integer id, Date bothday) {
        this.id = id;
        this.bothday = bothday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBothday() {
        return bothday;
    }

    public void setBothday(Date bothday) {
        this.bothday = bothday;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", bothday=" + bothday +
                '}';
    }
}
