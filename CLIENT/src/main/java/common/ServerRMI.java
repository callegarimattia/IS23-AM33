package common;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerRMI extends Remote {

    //  interfaccia usata dal client
    List<String> createUser(String newUsername, VirtualViewRMI virtualView, Object obj) throws RemoteException;

    int joinLobby(int ID, VirtualViewRMI virtualView, Object obj) throws RemoteException;

    String leaveLobby(VirtualViewRMI virtualView, Object obj) throws RemoteException;

    List<Integer> createLobby(int gameSize, VirtualViewRMI virtualView, Object obj) throws RemoteException;

    JSONObject lobbyListRequest(VirtualViewRMI virtualView, Object obj)throws RemoteException;

    List<String> shutDownClient(VirtualViewRMI virtualView)throws RemoteException;

    void checkAlive()throws RemoteException;
}
