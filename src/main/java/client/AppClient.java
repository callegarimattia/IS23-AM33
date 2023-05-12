package client;


public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI
        VirtualView client = null;
        if (args.length < 2) System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI)");
        if (args[0] == "T") {
            client = new ClientTCP();   // tbd

        } else {
            ClientRMItest clientTest = new ClientRMItest();
            clientTest.newConnection("localhost");
        }
        // da qui è uguale sia per TCP che per RMIclient.
        //
        //...
    }
}
