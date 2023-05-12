package server;


import server.controller.LobbiesHandler;
import server.controller.LobbiesHandlerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Hello world!
 */
public class ServerImpl {
    public static void main(String[] args) {

        LobbiesHandler lobbiesHandler = new LobbiesHandlerImpl();

        try {
            Server stub = (Server) UnicastRemoteObject
                    .exportObject((Server) lobbiesHandler, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Server", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("----------- SERVER ONLINE -----------");
    }
}
