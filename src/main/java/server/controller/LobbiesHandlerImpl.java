package server.controller;

import client.Client;
import server.Server;
import server.exceptions.LobbiesHandlerException;
import server.listenerStuff.LobbiesUpdateEvent;
import server.model.Lobby;
import server.model.User;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

public class LobbiesHandlerImpl implements LobbiesHandler, Server {  // Controller per Lobby
    private final Set<Lobby> waitingLobbies = new HashSet<>();
    private final Set<Lobby> inGameLobbies = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    /**
     * Creates and then adds a new user to the users pool with given username.
     * It checks whether the username is already present and throws an exception if true.
     *
     * @param newUsername
     * @throws LobbiesHandlerException
     */
    public boolean createUser(String newUsername) {
        synchronized (users) {
            for (User user : users) {
                if (user.getUserName().equals(newUsername)) return false;
            }
            User newUser = new User(newUsername);
            users.add(newUser);
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
    public User searchUser(String username) {  // da cancellare credo
        for (User user : users)
            if (user.getUserName().equals(username)) return user;
        return null;
    }

    public void removeUser(String toBeRemovedUsername) {
        synchronized (users) {
            for (User user : users)
                if (user.getUserName().equals(toBeRemovedUsername) && user.isInLobby())
                    users.remove(user);
            // bisogna anche rimuoverlo dalla lobby nel caso sia in una lobby e chiudere connessione
        }
    }

    /**
     * Given an user and a gameSize it creates a lobby with given gameSize.
     * If user isn't in the user pool, the game size is invalid or user is already in a lobby or game,
     * it throws an exception
     *
     * @param firstUser
     * @param gameSize
     * @throws LobbiesHandlerException
     */
    public synchronized void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException {
        if (!users.contains(firstUser)) throw new LobbiesHandlerException("User doesn't exist");
        if (firstUser.isInLobby() || firstUser.isInGame())
            throw new LobbiesHandlerException("User can't create a lobby right now!");
        if (gameSize > 4 || gameSize < 2) throw new LobbiesHandlerException("Game size is invalid");

        Lobby newLobby = new Lobby(firstUser, gameSize);
        waitingLobbies.add(newLobby);

        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbiesCopy());
        OnLobbyUpdate(evt);

    }

    private Set<Lobby> waitingLobbiesCopy() {
        Set<Lobby> copy = new HashSet<>();
        for (Lobby lobby : this.waitingLobbies) {
            copy.add(new Lobby(lobby));
        }
        return copy;
    }

    private Lobby searchLobby(int lobbyId) throws LobbiesHandlerException {  // da togliere
        for (Lobby lobby : waitingLobbies) {
            if (lobby.getID() == lobbyId) return lobby;
        }
        throw new LobbiesHandlerException("Lobby not found!");
    }

    private void removeLobby(Lobby toBeRemovedLobby, User user) {  // solo il creatore può e solo quando non è startata
        synchronized (waitingLobbies) {
            if (user.equals(toBeRemovedLobby.getUsers().get(0))) {
                for (User us : toBeRemovedLobby.getUsers())
                    us.setInLobby(false);
                waitingLobbies.removeIf(lobby -> lobby.equals(toBeRemovedLobby));
            }
        }
    }

    /**
     * Let a valid user, which is not in a lobby or game, join a not full lobby which hasn't started its game yet.
     * If one of the conditions isn't met it throws an exception.
     *
     * @param joiningUser
     * @param lobbyID
     * @throws LobbiesHandlerException
     */
    public synchronized void joinLobby(User joiningUser, int lobbyID) throws LobbiesHandlerException {
        if (!users.contains(joiningUser)) throw new LobbiesHandlerException("User doesn't exist.");
        if (joiningUser.isInLobby()) throw new LobbiesHandlerException("User is already in a lobby.");
        if (joiningUser.isInGame()) throw new LobbiesHandlerException("User is in an active game.");

        Lobby toBeJoinedLobby = null;
        for (Lobby lobby : waitingLobbies)
            if (lobby.getID() == lobbyID)
                toBeJoinedLobby = lobby;
        if (inGameLobbies.contains(toBeJoinedLobby)) throw new LobbiesHandlerException("Lobby already started");
        if (!waitingLobbies.contains(toBeJoinedLobby)) throw new LobbiesHandlerException("Lobby doesn't exist.");
        if (toBeJoinedLobby.isFull()) throw new LobbiesHandlerException("Lobby is full");

        if (toBeJoinedLobby.add(joiningUser)) startGame(toBeJoinedLobby);
        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbiesCopy());
        OnLobbyUpdate(evt);
    }

    private void OnLobbyUpdate(LobbiesUpdateEvent evt) {   // per ora solo RMI
        for (User user : users)
            try {
                user.getMyClient().LobbiesUpdate(evt);
            } catch (RemoteException e) {
                System.out.println("remote method invocation failed");
            }
    }

    /**
     * Lets a user leave a lobby.
     * If user is invalid, or isn't in a lobby, or is in a game, then it throws an exception.
     *
     * @param leavingUser
     * @throws LobbiesHandlerException
     */
    public synchronized void leaveLobby(User leavingUser) throws LobbiesHandlerException {
        if (!users.contains(leavingUser)) throw new LobbiesHandlerException("User doesn't exist.");
        if (!leavingUser.isInLobby()) throw new LobbiesHandlerException("User isn't in a lobby");
        if (leavingUser.isInGame()) throw new LobbiesHandlerException("Can't leave the lobby while in game!");

        for (Lobby lobby : waitingLobbies) {
            if (lobby.getUsers().contains(leavingUser))
                lobby.remove(leavingUser);
        }
        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbiesCopy());
        OnLobbyUpdate(evt);
    }

    private synchronized void startGame(Lobby toBeStartedLobby) throws LobbiesHandlerException {
        if (!toBeStartedLobby.isFull()) throw new LobbiesHandlerException("Lobby isn't full!");
        inGameLobbies.add(toBeStartedLobby);
        waitingLobbies.remove(toBeStartedLobby);
        toBeStartedLobby.initGame();  //creates game and game controller
        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbiesCopy());
        OnLobbyUpdate(evt);
    }


    public void joinServer(Client newClient) {
        User newUser = new User(newClient);
        users.add(newUser);
    }

    @Override
    public boolean setUsername(String newUsername, Client client) {
        for (Lobby lobby : inGameLobbies)
            for (User user : lobby.getUsers())
                if (user.getUserName().equals(newUsername))
                    return false;
        for (Lobby lobby : waitingLobbies)
            for (User user : lobby.getUsers())
                if (user.getUserName().equals(newUsername))
                    return false;
        for (User user : users)
            if (user.getMyClient() == client) {
                user.setUserName(newUsername);
                return true;
            }
        return false;
    }


    public Set<User> getUsers() {  // delete
        return users;
    }

    public Set<Lobby> getLobbies() {  // delete
        return waitingLobbies;
    }

}