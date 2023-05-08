package server;

import server.controller.LobbiesHandler;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 */
public class AppServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        LobbiesHandler lobbiesHandler = new LobbiesHandler();



        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("LobbiesHandler", lobbiesHandler);
        System.out.println("Server bound and ready");
        // il thread main termina ma vi sono i thread RMI ancora attivi ---> il server è online


    }
}
