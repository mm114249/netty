package com.pairs.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by hupeng on 2017/1/18.
 */
public class TimeServerHanlder implements Runnable {

    private Socket socket;

    public TimeServerHanlder(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(socket.getInputStream());
            in=new BufferedReader(inputStreamReader);
            out=new PrintWriter(socket.getOutputStream(),true);
            String body="";
            while (true){
                body=in.readLine();
                if(null==body){
                    break;
                }
                String respons="QUERY TIME ORDER".equalsIgnoreCase(body)?"当前时间是："+TimerServerUtil.getCurrentTime():"BAD ORDER";
                out.println(respons);
            }
        } catch (IOException e) {
            System.out.println("服务器run()->");
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(out!=null){
                out.close();
            }

            if(this.socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
