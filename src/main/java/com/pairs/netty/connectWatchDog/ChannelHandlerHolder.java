package com.pairs.netty.connectWatchDog;

import io.netty.channel.ChannelHandler;

/**
 * Created on 2017年07月20日 11:32
 * <P>
 * Title:[]
 * </p>
 * <p>
 * Description :[]
 * </p>
 * Company:武汉灵达科技有限公司
 *
 * @author [hupeng]
 * @version 1.0
 **/
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();

}
