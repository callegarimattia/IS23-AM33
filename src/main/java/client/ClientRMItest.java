package client;

import server.Server;

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
            if (!server.createUser("mattia")) {
                //Just a test... (later we reiterate until a valid username is given)
                System.out.println("User already present!");
                System.exit(1);
            }
            System.out.println(server.searchUser("mattia").toString());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
