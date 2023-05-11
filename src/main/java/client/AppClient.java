package client;


public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI
        Client client = null;
        if (args.length < 3) System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI).\n");
        if (args[0] == "T") {
            client = new ClientTCP();   // tbd

        } else {
            client = new ClientRMI(args[1],args[2]);
        }
    }
}
