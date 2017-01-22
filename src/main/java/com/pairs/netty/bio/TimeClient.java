package com.pairs.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hupeng on 2017/1/18.
 */
public class TimeClient {
    private Socket socket=null;
    private PrintWriter out=null;
    private BufferedReader in=null;


    private void init(){
        try {
            socket=new Socket("127.0.0.1",TimerServerUtil.port);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void read(String code){
        out.println(code);
        String body=null;
        while (true){
            try {
                body= in.readLine();
                System.out.println(body);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if(body!=null){
                break;
            }
        }
        if(out!=null){
            out.close();
        }

        if(in!=null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        TimeClient client=new TimeClient();
        client.init();
        client.read("QUERY TIME ORDER");
    }

}
