package server;

import client.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerTest {
    public static void main(String[] args) {
        Server server = new ServerImpl();
        try {
            Server stub = (Server) UnicastRemoteObject
                    .exportObject((Server) server, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Server", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
