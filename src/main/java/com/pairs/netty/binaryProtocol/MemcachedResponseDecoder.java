package com.pairs.netty.binaryProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Created by hupeng on 2017/3/2.
 */
public class MemcachedResponseDecoder extends ByteToMessageDecoder {

    private enum State {  //2
        Header,
        Body
    }

    private State state = State.Header;
    private int totalBodySize;
    private byte magic;
    private byte opCode;
    private short keyLength;
    private byte extraLength;
    private short status;
    private int id;
    private long cas;


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        in.markReaderIndex();
        byte magic=in.readByte();
        in.resetReaderIndex();
        if(magic!=0x01){
            return;
        }

        if(in.readableBytes()<SimpleRequest.BASE_LENGTH){
            return;
        }
        simpleDecoder(in,list);



//        in.markReaderIndex();
//        byte magic=in.readByte();
//        in.resetReaderIndex();
//        if(magic==0x01){
//            simpleDecoder(in,list);
//        }else{
//            memcachedDecoder(in,list);
//        }

    }


    private void simpleDecoder(ByteBuf byteBuf, List<Object> list) {
        byteBuf.skipBytes(1);
        int id = byteBuf.readInt();
        int length = byteBuf.readInt();
        byte[] bodys = new byte[length];
        byteBuf.readBytes(bodys);
        String body = new String(bodys, CharsetUtil.UTF_8);

        SimpleRequest request = new SimpleRequest();

        request.setId(id);
        request.setBody(body);

        list.add(request);
    }

    private void memcachedDecoder(ByteBuf in, List<Object> list) {
        switch (state) {
            case Header:
                if (in.readableBytes() < 24) {
                    return;
                }
                magic = in.readByte();
                opCode = in.readByte();
                keyLength = in.readShort();
                extraLength = in.readByte();
                in.skipBytes(1);
                status = in.readShort();
                totalBodySize = in.readInt();
                id = in.readInt();
                cas = in.readLong();
                state = State.Body;
            case Body:
                if (in.readableBytes() < totalBodySize) {
                    return;
                }

                int flags = 0;
                int expires = 0;
                int actualBodySize = totalBodySize;

                if (extraLength > 0) {  //7
                    flags = in.readInt();
                    actualBodySize -= 4;
                }
                if (extraLength > 4) {  //8
                    expires = in.readInt();
                    actualBodySize -= 4;
                }

                String key = "";
                if (keyLength > 0) {  //9
                    ByteBuf keyBytes = in.readBytes(keyLength);
                    key = keyBytes.toString(CharsetUtil.UTF_8);
                    actualBodySize -= keyLength;
                }
                ByteBuf body = in.readBytes(actualBodySize);  //10
                String data = body.toString(CharsetUtil.UTF_8);

                MemcachedRequest re = new MemcachedRequest(Opcode.GET, "aaa");
                list.add(re);
                state = State.Header;
        }

    }
}
