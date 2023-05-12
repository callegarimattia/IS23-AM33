package server.controller;

import client.Client;
import server.exceptions.LobbiesHandlerException;
import server.model.Lobby;
import server.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LobbiesHandlerInterface extends Remote {

    void createUser(String newUsername) throws LobbiesHandlerException, RemoteException;

    void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException, RemoteException;

    void joinLobby(User joiningUser, int lobbyID) throws LobbiesHandlerException, RemoteException;

    void leaveLobby(User leavingUser) throws LobbiesHandlerException, RemoteException;

    public boolean setUsername(String newUsername, int userID) throws RemoteException;

    public int joinServer(Client newClient) throws RemoteException;
}
