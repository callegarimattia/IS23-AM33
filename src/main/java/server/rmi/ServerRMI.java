package server.rmi;

import client.ClientRMI;
import server.exceptions.LobbiesHandlerException;
import server.listenerStuff.ListenerModel;
import server.model.Lobby;
import server.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {

    void createUser(String newUsername) throws LobbiesHandlerException, RemoteException;

    void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException, RemoteException;

    void joinLobby(User joiningUser, Lobby toBeJoinedlobby) throws LobbiesHandlerException, RemoteException;

    void leaveLobby(User leavingUser) throws LobbiesHandlerException, RemoteException;

    public void removeListener(ListenerModel myListener);

    public void joinServer(ListenerModel newListener, String newUsername, ClientRMI newClient);

    public boolean setUsername(String newUsername);
}
