package client;

import server.listenerStuff.ListenerModel;

public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI
        ListenerModel myListener = new ListenerModel();
        Client client = null;
        if (args.length < 3) System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI).\n");
        if (args[0] == "T") {
            client = new ClientTCP(args[1]);

        } else {
            client = new ClientRMI_Impl(args[1]);
            ((ClientRMI_Impl) client).joinServer("localhost", myListener);   // in futuro non sarà local host
        }
    }
}
