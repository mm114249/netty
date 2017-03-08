package com.pairs.netty.binaryProtocol;

/**
 * Created by hupeng on 2017/3/7.
 */
public class SimpleRequest {

    private byte magic=0x01;
    private int id;
    private int length;
    private String body;
    public static int BASE_LENGTH=9;

    public SimpleRequest(){

    }

    public SimpleRequest(byte magic){
        this.magic=magic;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getMagic() {
        return magic;
    }

    public void setMagic(byte magic) {
        this.magic = magic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
