package com.pairs.netty.rpcServer;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * Created by admin on 2017/1/25.
 */
public class MarshallingDecoder {
    private  Unmarshaller unmarshaller;

    public MarshallingDecoder() {
        try {
            this.unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object decode(ByteBuf in){
        int objectSize=in.readInt();
        ByteBuf buf=in.slice(in.readerIndex(),objectSize);
        ByteInput input=new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object object=unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex()+objectSize);
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                unmarshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
