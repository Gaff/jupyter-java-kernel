package net.steelcog.jupyter.kernel.protocol;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Comms {
    static final ObjectMapper om = new ObjectMapper();
    static final TypeReference TR = new TypeReference<Map<String, String>>(){};

    public static Message wireMsgToComposedMsg( byte[][] msgparts, byte[] signkey) throws IOException {
        int i = 0;
        for(byte[] msg : msgparts) {
            if(new  String(msg) ==  "<IDS|MSG>" )
                break;
            i++;
        }


        //TODO: Sign message!

        /*
        identities=msgparts[:i]
                // msgparts[i] is the delimiter

                // Validate signature
                if

        len(signkey)

        !=0

        {
            mac:=hmac.New(sha256.New, signkey)
            for _, msgpart:=range msgparts[ i + 2:i + 6]{
            mac.Write(msgpart)
        }
            signature:=make([]byte,hex.DecodedLen(len(msgparts[i + 1])))
            hex.Decode(signature, msgparts[i + 1])
            if !hmac.Equal(mac.Sum(nil), signature) {
            return msg,nil,&InvalidSignatureError {
            }
        }
        }

        */
        Message out = ImmutableMessage.builder()
                .header(om.readValue(msgparts[i+2], Message.Header.class))
                .parentHeader(om.readValue(msgparts[i+3], Message.Header.class))
                .metadata(om.<Map<String, ?>>readValue(msgparts[i + 4], TR))
                .content(om.<Map<String, ?>>readValue(msgparts[i + 5], TR))
                .build();

        return(out);
    }
}
