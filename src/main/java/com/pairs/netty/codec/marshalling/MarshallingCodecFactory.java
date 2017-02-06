package com.pairs.netty.codec.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by admin on 2017/1/25.
 */
public class MarshallingCodecFactory {

    public static MarshallingDecoder buildMarshallingDecoder(){
        MarshallerFactory marshallerFactory= Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration=new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider=new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        MarshallingDecoder marshallingDecoder=new MarshallingDecoder(provider,1024);
        return marshallingDecoder;
    }

    public static MarshallingEncoder buildMarshallingEncoder(){
        MarshallerFactory marshallerFactory= Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration configuration=new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider=new DefaultMarshallerProvider(marshallerFactory,configuration);
        MarshallingEncoder encoder=new MarshallingEncoder(provider);
        return encoder;
    }
}
