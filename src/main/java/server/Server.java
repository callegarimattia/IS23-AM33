package server;

import server.model.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Server extends Remote {

    //  interfaccia usata dal client
    boolean createUser(String newUsername) throws RemoteException;

    boolean joinLobby(String user, int lobbyID) throws RemoteException;

    boolean leaveLobby(String user) throws RemoteException;

    int createLobby(String firstUser, int gameSize) throws RemoteException;

}
