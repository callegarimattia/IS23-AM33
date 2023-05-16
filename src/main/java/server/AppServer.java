package server;

import server.controller.LobbiesHandler;
import server.controller.LobbiesHandlerImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AppServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        LobbiesHandler lobbiesHandler = new LobbiesHandlerImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
        Server stub = (Server) UnicastRemoteObject
                .exportObject((Server) lobbiesHandler, 0);
        registry.rebind("Server", stub);
        System.out.println("------------------- RMI SERVER ONLINE -------------------");
    }
}
