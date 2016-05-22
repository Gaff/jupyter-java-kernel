package net.steelcog.jupyter.kernel.protocol.classic;

import java.util.Map;

public class Message {
    public static enum MessageType {

    }

    // The message header contains a pair of unique identifiers for the
    // originating session and the actual message id, in addition to the
    // username for the process that generated the message.  This is useful in
    // collaborative settings where multiple users may be interacting with the
    // same kernel simultaneously, so that frontends can label the various
    // messages in a meaningful way.
    public static class Header {
        String msgId;
        String username;
        String session;
        String date;
        MessageType msgType;
        String version;
    }

    // In a chain of messages, the header from the parent is copied so that
    // clients can track where messages come from.
    Map<String, Header> parentHeader;
    Map<String, ?> metadata;
    Content content;
}
