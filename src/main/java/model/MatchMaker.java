package model;

import java.util.ArrayList;

public class MatchMaker {
    private final ArrayList<Game> games = new ArrayList<>();
    private final ArrayList<Lobby> lobbies = new ArrayList<>();
    private final ArrayList<User> availableUsers = new ArrayList<>();

    public boolean addUser(User toBeAddedUser) throws MatchMakingException {
        for (User user : availableUsers) {
            if (user.equals(toBeAddedUser))
                throw new MatchMakingException("User already exists!");
        }
        return availableUsers.add(toBeAddedUser);
    }

    public boolean removeUser(User toBeRemovedUser) {
        return availableUsers.remove(toBeRemovedUser);
    }

    public void createLobby(User firstUser, int gameSize) throws MatchMakingException {
        if (!availableUsers.contains(firstUser)) throw new MatchMakingException("User isn't available");
        else if (gameSize > 4 || gameSize < 2) throw new MatchMakingException("Game size is invalid");
        else {
            Lobby newLobby = new Lobby(firstUser, gameSize);
            lobbies.add(newLobby);
            availableUsers.remove(firstUser);
        }
    }

    public boolean addLobby(Lobby toBeAdded) {
        return lobbies.add(toBeAdded);
    }

    public boolean removeLobby(Lobby toBeRemoved) {
        return lobbies.remove(toBeRemoved);
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public ArrayList<User> getAvailableUsers() {
        return availableUsers;
    }

    public ArrayList<Game> getGames() {
        return games;
    }
}