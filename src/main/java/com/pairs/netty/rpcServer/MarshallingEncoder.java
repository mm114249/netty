package com.pairs.netty.rpcServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.Marshalling;

import java.io.IOException;

/**
 * Created by admin on 2017/1/25.
 */
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER=new byte[4];
    private Marshaller marshaller;

    public MarshallingEncoder(){
        try {
            marshaller= MarshallingCodecFactory.buildMarshalling();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void encode(Object msg, ByteBuf out) throws IOException {
        int lengthPos=out.writerIndex();
        out.writeBytes(LENGTH_PLACEHOLDER);
        ChannelBufferByteOutput output=new ChannelBufferByteOutput(out);
        marshaller.start(output);
        marshaller.writeObject(msg);
        marshaller.flush();
        out.setInt(lengthPos,out.writerIndex()-lengthPos-4);
        marshaller.close();
    }



}
