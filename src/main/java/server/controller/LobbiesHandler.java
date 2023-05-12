package server.controller;

import client.Client;
import server.exceptions.LobbiesHandlerException;
import server.model.User;

import java.rmi.RemoteException;

public interface LobbiesHandler {

    boolean createUser(String newUsername);

    void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException, RemoteException;

    void joinLobby(User joiningUser, int lobbyID) throws LobbiesHandlerException, RemoteException;

    void leaveLobby(User leavingUser) throws LobbiesHandlerException, RemoteException;

    public boolean setUsername(String newUsername, Client client) throws RemoteException;

    public void joinServer(Client newClient) throws RemoteException;
}
