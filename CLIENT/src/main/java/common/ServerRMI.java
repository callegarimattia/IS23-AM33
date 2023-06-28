package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerRMI extends Remote {

    //  interfaccia usata dal client
    List<String> createUser(String newUsername, VirtualViewRMI virtualView) throws RemoteException;

    boolean joinLobby(String user, int lobbyID) throws RemoteException;

    boolean leaveLobby(String user) throws RemoteException;

    int createLobby(String firstUser, int gameSize) throws RemoteException;

    List<Integer> lobbyListRequest(VirtualViewRMI virtualView)throws RemoteException;

    List<String> shutDownClient(VirtualViewRMI virtualView)throws RemoteException;

    void checkAlive()throws RemoteException;
}
