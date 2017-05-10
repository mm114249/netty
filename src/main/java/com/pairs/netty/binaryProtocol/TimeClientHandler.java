package com.pairs.netty.binaryProtocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by hupeng on 2017/1/19.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private int count;


    public TimeClientHandler() {
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MemcachedRequest request = new MemcachedRequest(Opcode.SET,"delete","abcderf");
        for(int i=0;i<20;i++){
            ctx.writeAndFlush(request);
        }
//        String code="aaa"+System.getProperty("line.separator");
//        ByteBuf b=Unpooled.copiedBuffer(code.getBytes());
//        b.writableBytes();
//        ctx.writeAndFlush(b);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        //  ctx.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private String bytes2HexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase().trim();
    }

    private static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    private static Byte[] HexString2Bytes(String hexstr) {
        Byte[] b = new Byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }


    private static String toStringHex(String s) {
        char[] baKeyword = new char[s.length() / 4];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (char) Integer.parseInt(s.substring(i * 4, i * 4 + 4), 16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new String(baKeyword);
    }


    public static void main(String[] args) {
        String code = "232305FE4C5A5954455441573645313034343531340100291101180E3B0B000279756E64753300000000000079756E647531323300000000000000000000000001BB";
        Byte[] bt = HexString2Bytes(code);
        byte[] bts = new byte[bt.length];
        for (int i = 0; i < bt.length; i++) {
            bts[i] = bt[i].byteValue();
        }

        System.out.println(hexStr2Str(code));
        System.out.println(new String(bts, CharsetUtil.UTF_8));

    }

}
