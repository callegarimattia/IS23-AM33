package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {

    //  interfaccia usata dal client
    int createUser(String newUsername, VirtualViewRMI virtualView) throws RemoteException;

    boolean joinLobby(String user, int lobbyID) throws RemoteException;

    boolean leaveLobby(String user) throws RemoteException;

    int createLobby(String firstUser, int gameSize) throws RemoteException;

}
