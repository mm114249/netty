package com.pairs.netty.hanlderDome;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created on 2017年07月07日 15:46
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
public class ServerStringEncoder extends StringEncoder {
    public ServerStringEncoder() {
        super();
    }

    public ServerStringEncoder(Charset charset) {
        super(charset);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        System.out.println("encoder handler");
        super.encode(ctx, msg, out);
    }
}
