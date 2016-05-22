package net.steelcog.jupyter.kernel.protocol.classic;

public class ExecuteContent implements Content {

    String code;
    boolean silent;
    boolean storeHistory;
    boolean allowStdin;
    boolean stopOnError;

}
