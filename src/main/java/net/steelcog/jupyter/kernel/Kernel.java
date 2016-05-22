package net.steelcog.jupyter.kernel;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.immutables.value.Value;
import org.zeromq.ZMQ;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

/**
 * Created on 22-May-16.
 */
public class Kernel implements Runnable {
    @Value.Immutable
    public static interface ConnectionInfo {
        public String Signature_scheme();
        public String transport();
        public int stdinPort();
        public int controlPort();
        public int IOPubPort();
        public int HBPort();
        public int shellPort();
        public String key();
        public String IP();
    }

    private class Sockets {
        private final ZMQ.Socket shellSocket;
        private final ZMQ.Socket controlSocket;
        private final ZMQ.Socket stdinSocket;
        private final ZMQ.Socket iopubSocket;
        private final ZMQ.Socket hbSocket;

        public Sockets(ConnectionInfo connectionInfo) {
            ZMQ.Context context = ZMQ.context(1); //Do we need to keep this?

            shellSocket = context.socket(ZMQ.ROUTER);
            controlSocket = context.socket(ZMQ.ROUTER);
            stdinSocket = context.socket(ZMQ.ROUTER);
            iopubSocket = context.socket(ZMQ.PUB);
            hbSocket = context.socket(ZMQ.REP);

            Function<Integer, String> bindHelper = s -> String.format("%s://%s:%d", connectionInfo.transport(), connectionInfo.IP(), s);

            shellSocket.bind(bindHelper.apply(connectionInfo.shellPort()));
            controlSocket.bind(bindHelper.apply(connectionInfo.controlPort()));
            stdinSocket.bind(bindHelper.apply(connectionInfo.stdinPort()));
            iopubSocket.bind(bindHelper.apply(connectionInfo.IOPubPort()));
            hbSocket.bind(bindHelper.apply(connectionInfo.HBPort()));

            /*
        // Message signing key
        sg.Key = []byte(conn_info.Key)

        */
        }
    }

    private class HeartBeat extends Thread {
        public void run() {
            ZMQ.proxy(sockets.hbSocket, sockets.hbSocket, null);
        }
    }

    private final ConnectionInfo connectionInfo;
    private final Sockets sockets;
    private HeartBeat heartBeat;

    public static Kernel buildKernel(ConnectionInfo ci) {
        return new Kernel(ci);
    }

    public static Kernel buildKernel(File file) throws IOException {
        ObjectMapper om = new ObjectMapper();
        ConnectionInfo connectionInfo = om.readValue(file, ConnectionInfo.class);
        return buildKernel(connectionInfo);
    }

    private Kernel(ConnectionInfo ci) {
        this.connectionInfo = ci;
        sockets = new Sockets(connectionInfo);
    }

    @Override
    public void run() {
        heartBeat = new HeartBeat();
        heartBeat.run();
        try {

        } finally {
            heartBeat.interrupt();
        }
    }

}
