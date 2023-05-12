package server;

import server.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    boolean createUser(String newUsername) throws RemoteException;

    User searchUser(String username) throws RemoteException;
}
