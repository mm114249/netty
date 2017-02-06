package com.pairs.netty.rpcServer;

import io.netty.channel.ChannelHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by admin on 2017/1/25.
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private Map<String,Boolean> nodeCheck=new ConcurrentHashMap<String,Boolean>();
    private String[] whitekList={"127.0.0.1"};




}
