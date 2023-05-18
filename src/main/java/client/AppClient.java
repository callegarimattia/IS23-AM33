package client;


public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI
        VirtualView client = null;
        if (args.length < 2) System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI)");
        if (args[0].equals("T")) {
            client = new ClientTCP();   // tbd

        } else {
            ClientRMI clientTest = new ClientRMI();
            clientTest.newConnection("localhost", 1099);
        }

        //  ciclo while che chiede comandi all utente

        // da qui è uguale sia per TCP che per RMIclient.
        //
        //...
    }
}
