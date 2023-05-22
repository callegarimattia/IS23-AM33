package server.controller;

import server.Server;
import server.exceptions.LobbiesHandlerException;
import server.listenerStuff.LobbiesUpdateEvent;
import server.model.Lobby;
import server.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class LobbiesHandlerImpl implements LobbiesHandler, Server {  // Controller per Lobby
    private final Set<Lobby> waitingLobbies = new HashSet<>();
    private final Set<Lobby> inGameLobbies = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    private final int TCPport = 2345;  // sarebbe meglio prenderla da arg/json

    /**
     * Creates and then adds a new user to the users pool with given username.
     * It checks whether the username is already present and throws an exception if true.
     *
     * @param newUsername
     * @throws LobbiesHandlerException
     */
    @Override
    public boolean createUser(String newUsername) {
        synchronized (users) {
            for (User user : users) {
                if (user.getUserName().equals(newUsername)) return false;
            }
            User newUser = new User(newUsername);
            users.add(newUser);
            System.out.println("SERVER: NEW USERNAME ADDED ('" + newUsername + "')");
            return true;
        }
    }

    /**
     * Search the given username in the pool of users.
     * If found returns the corresponding user. Otherwise it returns null.
     *
     * @param username of user to be searched
     * @return user or null if not present
     */
    @Override
    public boolean searchUser(String username) {  // da cancellare credo
        for (User user : users)
            if (user.getUserName().equals(username)) return true;
        return false;
    }

    public void removeUser(String toBeRemovedUsername) {
        synchronized (this) {
            for (User user : users)
                if (user.getUserName().equals(toBeRemovedUsername))
                    users.remove(user);
            for(Lobby lobby : waitingLobbies)
                for (User user : lobby.getUsers())
                    if (user.getUserName().equals(toBeRemovedUsername)){
                        lobby.removeUser(user);
                        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
                        OnLobbyUpdate(evt);
                    }

            for(Lobby lobby : inGameLobbies)
                for (User user : lobby.getUsers())
                    if (user.getUserName().equals(toBeRemovedUsername) && user.isInLobby()){
                        System.out.println("al momento niente");
                        //  update speciale di fine partita e chiusura di tutti i (4 possibilmente) thread parser
                    }
        }
    }



    /**
     * Given an user and a gameSize it creates a lobby with given gameSize.
     * If user isn't in the user pool, the game size is invalid or user is already in a lobby or game,
     * it throws an exception
     *
     * @param username
     * @param gameSize
     */
    @Override
    public synchronized int createLobby(String username, int gameSize) {
        User firstUser = null;
        for (User user : users) {
            if (user.getUserName().equals(username)) firstUser = user;
        }
        if (firstUser == null) return -1;

        if (firstUser.isInLobby() ||
                firstUser.isInGame()) return -2;

        if (gameSize > 4 || gameSize < 2) return -3;

        Lobby newLobby = new Lobby(gameSize);
        waitingLobbies.add(newLobby);
        System.out.println("SERVER: CREATED NEW LOBBY ID(" + newLobby.getID() + ") WITH GAME SIZE " + gameSize);
        joinLobby(username, newLobby.getID());

        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
        OnLobbyUpdate(evt);
        return newLobby.getID();
    }

    @Override
    public boolean searchLobby(int lobbyId) {
        for (Lobby lobby : waitingLobbies) {
            if (lobby.getID() == lobbyId) return true;
        }
        return false;
    }

    public void removeLobby(String userName) {
        synchronized (inGameLobbies) {
            for(Lobby lobby : inGameLobbies)
                for(User user: lobby.getUsers())
                    if(user.getUserName().equals(userName)){
                        for(User us : lobby.getUsers())
                            users.remove(us);
                        inGameLobbies.remove(lobby);
                    }
        }
    }

    /**
     * Let a valid user, which is not in a lobby or game, join a not full lobby which hasn't started its game yet.
     * If one of the conditions isn't met it throws an exception.
     *
     * @param username
     * @param lobbyID
     * @throws LobbiesHandlerException
     */
    @Override
    public synchronized boolean joinLobby(String username, int lobbyID) {
        User joiningUser = null;
        for (User user : users) {
            if (user.getUserName().equals(username)) joiningUser = user;
        }
        if (joiningUser == null) return false;

        if (joiningUser.isInLobby()
                || joiningUser.isInGame()) return false;

        for (Lobby lobby : waitingLobbies)
            if (lobby.getID() == lobbyID) {
                if (lobby.isFull()) return false;
                else {
                    LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
                    OnLobbyUpdate(evt);
                    System.out.println("SERVER: LOBBY ID(" + lobbyID + ") JOINED BY ('" + username + "')");
                    return lobby.add(joiningUser);
                }
            }
        return false;
    }

    private void OnLobbyUpdate(LobbiesUpdateEvent evt) {   // per ora solo RMI
        for (User user : users)
            try {
                if (user.getMyClient() != null)
                    user.getMyClient().LobbiesUpdate(evt);
            } catch (RemoteException e) {
                System.out.println("remote method invocation failed");
            }
    }

    /**
     * Lets a user leave a lobby.
     * If user is invalid, or isn't in a lobby, or is in a game, then it throws an exception.
     *
     * @param username
     * @throws LobbiesHandlerException
     */
    @Override
    public synchronized boolean leaveLobby(String username) {
        User leavingUser = null;
        for (User user : users) {
            if (user.getUserName().equals(username)) leavingUser = user;
        }
        if (leavingUser == null) return false;

        if (!users.contains(leavingUser)
                || !leavingUser.isInLobby()
                || leavingUser.isInGame()) return false;

        int lobbyID = -1;
        for (Lobby lobby : waitingLobbies) {
            if (lobby.getUsers().contains(leavingUser)) {
                lobby.removeUser(leavingUser);
                lobbyID = lobby.getID();
            }
        }
        if (lobbyID == -1) return false;

        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
        OnLobbyUpdate(evt);
        System.out.println("SERVER: LOBBY ID(" + lobbyID + ") LEAVED BY ('" + username + "')");
        return true;
    }

    private synchronized boolean startGame(int toBeStartedLobbyID) {
        Lobby toBeStartedLobby = null;
        for (Lobby lobby : waitingLobbies)
            if (lobby.getID() == toBeStartedLobbyID) toBeStartedLobby = lobby;
        if (toBeStartedLobby == null) return false;
        if (!toBeStartedLobby.isFull()) return false;
        inGameLobbies.add(toBeStartedLobby);
        waitingLobbies.remove(toBeStartedLobby);
        toBeStartedLobby.initGame();  //creates game and game controller
        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
        OnLobbyUpdate(evt);
        System.out.println("SERVER: LOBBY " + toBeStartedLobbyID + " GAME STARTED");
        return true;
    }

    @Override
    public Set<User> getUsers() {  // delete
        return users;
    }

    @Override
    public Set<Lobby> getWaitingLobbies() {  // delete
        return waitingLobbies;
    }

    private void startTCP() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(TCPport);
        } catch (final IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        Runnable TCPaccepter = new TCPaccepter(serverSocket, this);
        Thread th = new Thread(TCPaccepter);
        th.start();
        System.out.println("------------------- TCP SERVER ONLINE -------------------");
    }

    private void startRMI(){
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1099);
            Server stub = (Server) UnicastRemoteObject
                    .exportObject((Server) this, 0);
            registry.rebind("Server", stub);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------- RMI SERVER ONLINE -------------------");
    }

    @Override
    public void startServer() {
        startTCP();
        startRMI();
    }

    @Override
    public void refresh() {   // debug purpose onlly
        System.out.println("REFRESHED: ");
        System.out.println("ALL USERS:  \n");
        for(User us: users){
            System.out.println("username:  " + us.getUserName());
            System.out.println("is in lobby:  " + us.isInLobby());
            System.out.println("is in game:  " + us.isInGame());
            System.out.println();
        }

        System.out.println("\nIN GAME LOBBIES: \n\n" );
        for(Lobby lobby : inGameLobbies){
            System.out.println("ID:  " + lobby.getID());
            System.out.println("size:  " + lobby.getGameSize());
            System.out.println("isFull:  " + lobby.isFull());
            System.out.println("users: \n\n");
            for(User us : lobby.getUsers()){
                System.out.println("username:  " + us.getUserName());
                System.out.println("is in lobby:  " + us.isInLobby());
                System.out.println("is in game:  " + us.isInGame());
                System.out.println();
            }
        }

        System.out.println("\nPRE GAME LOBBIES: \n" );
        for(Lobby lobby : waitingLobbies){
            System.out.println("ID:  " + lobby.getID());
            System.out.println("Current size:  " + lobby.getUsers().size());
            System.out.println("To be reached size:  " + lobby.getGameSize());
            System.out.println("isFull:  " + lobby.isFull());
            System.out.println("users: \n");
            for(User us : lobby.getUsers()){
                System.out.println("username:  " + us.getUserName());
                System.out.println("is in lobby:  " + us.isInLobby());
                System.out.println("is in game:  " + us.isInGame());
                System.out.println();
            }
        }

    }

    @Override
    public void addTCPparserToUser(String newUserUsername, TCPclientParser parser) {
        for (User user: users)
            if(user.getUserName().equals(newUserUsername))
                user.setMyParser(parser);
    }


}


