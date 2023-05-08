package client;

import java.rmi.RemoteException;

public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI
        Client client = null;
        if(args[0]=="T"){
            client = new ClientTCP(args[1]);

        }

        else  {
            client = new ClientRMI_Impl(args[1]);
            ((ClientRMI_Impl) client).joinServer("localhost");   // in futuro non sarà local host
        }



    }
}
