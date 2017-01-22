package com.pairs.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hupeng on 2017/1/18.
 */
public class TimerServer {

    private void init(){
        ServerSocket server=null;
        Socket socket=null;
        try {
            server=  new ServerSocket(TimerServerUtil.port);
            while (true){
                socket=server.accept();
                new Thread(new TimeServerHanlder(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("服务器init()->");
            e.printStackTrace();
        }finally {
            if(server!=null){
                try {
                    server.close();
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

    }


    public static void main(String[] args) {
        new TimerServer().init();
    }
}
