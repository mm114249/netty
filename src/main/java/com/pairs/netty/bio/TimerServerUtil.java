package com.pairs.netty.bio;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hupeng on 2017/1/18.
 */
public class TimerServerUtil {

    public static final Integer port=7071;//服务器监听端口

    public static SimpleDateFormat SECOND_FORMAT=null;

    static {
        SECOND_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 活动当前时间
     * @return
     */
    public static String getCurrentTime(){
        return SECOND_FORMAT.format(new Date());
    }

}
