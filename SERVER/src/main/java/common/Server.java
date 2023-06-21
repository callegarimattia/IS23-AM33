package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {

    //  interfaccia usata dal client
    boolean createUser(String newUsername) throws RemoteException;

    boolean joinLobby(String user, int lobbyID) throws RemoteException;

    boolean leaveLobby(String user) throws RemoteException;

    int createLobby(String firstUser, int gameSize) throws RemoteException;

}
