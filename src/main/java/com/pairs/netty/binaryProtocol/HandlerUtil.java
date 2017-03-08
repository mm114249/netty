package com.pairs.netty.binaryProtocol;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hupeng on 2017/3/8.
 */
public class HandlerUtil  {

    private static Map<String,ChannelHandlerContext> channels=new HashMap<String,ChannelHandlerContext>();

    public static ChannelHandlerContext get(String key){

        ChannelHandlerContext context=null;
        if(channels.containsKey(key)){
            context=channels.get(key);
        }
        return context;
    }

    public static void put(String key,ChannelHandlerContext context){
        channels.put(key,context);
    }

}
