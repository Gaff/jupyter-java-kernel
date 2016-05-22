package net.steelcog.jupyter.kernel.protocol;

import org.immutables.value.Value;

import java.util.Map;

@Value.Immutable
public interface Message {
    public static enum MessageType {
        //lower case because it helps map the JSON
        execute_request,
        execute_reply,
        inspect_request,
        inspect_reply,
        complete_request,
        complete_reply,
        history_request,
        history_reply,
        is_complete_request,
        is_complete_reply,
        connect_request,
        connect_reply,
        comm_info_request,
        comm_info_reply,
        kernel_info_request,
        kernel_info_reply,
        shutdown_request,
        shutdown_reply,
        display_data,
        execute_input,
        execute_result,
        error,
        status,
        clear_output,
        input_request,
        input_reply,
        comm_open,
        comm_msg,
        comm_close
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
    public Header parentHeader();
    public Map<String, ?> metadata();
    public Map<String, ?> content();
}
