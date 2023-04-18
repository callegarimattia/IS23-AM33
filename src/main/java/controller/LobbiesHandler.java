package controller;

import model.lobbies.LobbiesHandlerException;
import model.lobbies.Lobby;
import model.lobbies.LobbyInterface;
import model.lobbies.User;

import java.util.HashSet;
import java.util.Set;

public class LobbiesHandler implements LobbyInterface {
    private final Set<Lobby> lobbies = new HashSet<>();

    private final Set<Lobby> inGameLobbies = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    /**
     * Creates and then adds a new user to the users pool with given username.
     * It checks whether the username is already present and throws an exception if true.
     *
     * @param newUsername
     * @throws LobbiesHandlerException
     */
    public void createUser(String newUsername) throws LobbiesHandlerException {
        for (User user : users) {
            if (user.getUserName().equals(newUsername)) throw new LobbiesHandlerException("Username already taken");
        }
        User newUser = new User(newUsername);
        users.add(newUser);
    }

    /**
     * Search the given username in the pool of users.
     * If found returns the corresponding user. Otherwise it throws an exception.
     *
     * @param username
     * @return user
     * @throws LobbiesHandlerException
     */
    public User searchUser(String username) throws LobbiesHandlerException {
        for (User user : users)
            if (user.getUserName().equals(username)) return user;
        throw new LobbiesHandlerException("User not found!");
    }

    public void removeUser(String toBeRemovedUsername) {
        users.removeIf(user -> user.getUserName().equals(toBeRemovedUsername));
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
    public void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException {
        if (!users.contains(firstUser)) throw new LobbiesHandlerException("User doesn't exist");
        if (firstUser.isInLobby() || firstUser.isInGame())
            throw new LobbiesHandlerException("User can't create a lobby right now!");
        if (gameSize > 4 || gameSize < 2) throw new LobbiesHandlerException("Game size is invalid");

        Lobby newLobby = new Lobby(firstUser, gameSize);
        lobbies.add(newLobby);
    }

    private Lobby searchLobby(int lobbyId) throws LobbiesHandlerException {
        for (Lobby lobby : lobbies) {
            if (lobby.getID() == lobbyId) return lobby;
        }
        throw new LobbiesHandlerException("Lobby not found!");
    }

    private void removeLobby(Lobby toBeRemovedLobby) {
        lobbies.removeIf(lobby -> lobby.equals(toBeRemovedLobby));
    }

    /**
     * Let a valid user, which is not in a lobby or game, join a not full lobby which hasn't started its game..
     * If one of the conditions isn't met it throws an exception
     *
     * @param joiningUser
     * @param toBeJoinedLobby
     * @throws LobbiesHandlerException
     */
    public void joinLobby(User joiningUser, Lobby toBeJoinedLobby) throws LobbiesHandlerException {
        if (!users.contains(joiningUser)) throw new LobbiesHandlerException("User doesn't exist.");
        if (joiningUser.isInLobby()) throw new LobbiesHandlerException("User is already in a lobby.");
        if (joiningUser.isInGame()) throw new LobbiesHandlerException("User is in an active game.");
        if (!lobbies.contains(toBeJoinedLobby)) throw new LobbiesHandlerException("Lobby doesn't exist.");
        if (toBeJoinedLobby.isFull()) throw new LobbiesHandlerException("Lobby is full");

        toBeJoinedLobby.add(joiningUser);
    }

    /**
     * Lets a user leave a lobby.
     * If user is invalid, or isn't in a lobby, or is in a game, then it throws an exception.
     *
     * @param leavingUser
     * @throws LobbiesHandlerException
     */
    public void leaveLobby(User leavingUser) throws LobbiesHandlerException {
        if (!users.contains(leavingUser)) throw new LobbiesHandlerException("User doesn't exist.");
        if (!leavingUser.isInLobby()) throw new LobbiesHandlerException("User isn't in a lobby");
        if (leavingUser.isInGame()) throw new LobbiesHandlerException("Can't leave the lobby while in game!");

        for (Lobby lobby : lobbies) {
            if (lobby.getUsers().contains(leavingUser))
                lobby.remove(leavingUser);
        }
    }

    public void restoreLobby(Lobby toBeAddedLobby) {
        lobbies.add(toBeAddedLobby);
    }

    public void startLobby(Lobby toBeStartedLobby) {
        if (toBeStartedLobby.isFull()) {
            lobbies.remove(toBeStartedLobby);
            inGameLobbies.add(toBeStartedLobby);
        }
    }

    public Set<Lobby> getLobbies() {
        return lobbies;
    }

    public Set<User> getUsers() {
        return users;
    }
}