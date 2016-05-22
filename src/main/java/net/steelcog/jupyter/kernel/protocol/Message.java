package net.steelcog.jupyter.kernel.protocol;

import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
public interface Message {
    public static enum MessageType {

    }

    @Value.Immutable
    public static interface Header {
        public String msgId();
        public String username();
        public String session();
        public String date();
        public MessageType msgType();
        public String version();
    }

    public Header header();
    public Map<String, Header> parentHeader();
    public Map<String, ?> metadata();
    //public Content content();
}
