package model;

import java.util.HashSet;
import java.util.Set;

public class LobbiesHandler {
    private final Set<Lobby> lobbies = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    public void createUser(String newUsername) throws LobbiesHandlerException {
        for (User user : users) {
            if (user.getUserName().equals(newUsername)) throw new LobbiesHandlerException("Username already taken");
        }
        User newUser = new User(newUsername);
        users.add(newUser);
    }

    public User searchUser(String username) throws LobbiesHandlerException {
        for (User user : users)
            if (user.getUserName().equals(username)) return user;
        throw new LobbiesHandlerException("User not found!");
    }

    public void removeUser(String toBeRemovedUsername) {
        users.removeIf(user -> user.getUserName().equals(toBeRemovedUsername));
    }

    public void createLobby(User firstUser, int gameSize) throws LobbiesHandlerException {
        if (!users.contains(firstUser)) throw new LobbiesHandlerException("User doesn't exist");
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

    private void removeLobby(int lobbyID) {
        lobbies.removeIf(lobby -> lobby.getID() == lobbyID);
    }

    private void joinLobby(User joiningUser, Lobby toBeJoinedlobby) throws LobbiesHandlerException {
        if (!users.contains(joiningUser)) throw new LobbiesHandlerException("User doesn't exist.");
        if (joiningUser.isInLobby()) throw new LobbiesHandlerException("User is already in a lobby.");
        if (joiningUser.isInGame()) throw new LobbiesHandlerException("User is in an active game.");
        if (!lobbies.contains(toBeJoinedlobby)) throw new LobbiesHandlerException("Lobby doesn't exist.");
        if (toBeJoinedlobby.isFull()) throw new LobbiesHandlerException("Lobby is full");
        if (toBeJoinedlobby.isInGame()) throw new LobbiesHandlerException("Game already started in lobby.");

        toBeJoinedlobby.add(joiningUser);
    }

    public Set<Lobby> getLobbies() {
        return lobbies;
    }

    public Set<User> getUsers() {
        return users;
    }
}