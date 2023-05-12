package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    boolean createUser(String newUsername) throws RemoteException;

    boolean searchUser(String username) throws RemoteException;

    boolean joinLobby(String user, int lobbyID) throws RemoteException;
}
