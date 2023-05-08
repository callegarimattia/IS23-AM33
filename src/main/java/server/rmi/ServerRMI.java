package server.rmi;

import client.ClientRMI;
import server.listenerStuff.ListenerModel;
import server.model.lobbies.LobbiesHandlerException;
import server.model.lobbies.Lobby;
import server.model.lobbies.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ServerRMI extends Remote {

    void createUser(String newUsername) throws LobbiesHandlerException, RemoteException;

    void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException, RemoteException;

    void joinLobby(User joiningUser, Lobby toBeJoinedlobby) throws LobbiesHandlerException, RemoteException;

    void leaveLobby(User leavingUser) throws LobbiesHandlerException, RemoteException;
    public void setListener(ListenerModel myListener);

    public void removeListener(ListenerModel myListener);
}
