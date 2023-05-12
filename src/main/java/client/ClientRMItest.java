package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMItest {
    Registry registry;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            Server server = (Server) registry
                    .lookup("Server");
            String responseMessage = server.sendMessage("Client Message");
            String expectedMessage = "Server Message";
            System.out.println(expectedMessage.equals(responseMessage));
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
