package com.pairs.netty.idle;

/**
 * Created by hupeng on 2017/5/10.
 */
public class IdleModel {

    private int status;//1 心跳消息 2 正式消息
    private String body;

    public IdleModel(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "IdleModel{" +
                "status=" + status +
                ", body='" + body + '\'' +
                '}';
    }



}
