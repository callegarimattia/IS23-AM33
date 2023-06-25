package client;

import java.util.Objects;

public class Lobby {
    private final int gameSize;
    private final int lobbyId;
    private final int numCurrPlayers;

    public Lobby(int gameSize, int lobbyId, int numCurrPlayers) {
        this.gameSize = gameSize;
        this.lobbyId = lobbyId;
        this.numCurrPlayers = numCurrPlayers;
    }

    public int getGameSize() {
        return gameSize;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public int getNumCurrPlayers() {
        return numCurrPlayers;
    }

    @Override
    public String toString() {
        return "Lobby #" + lobbyId
                + " (" + numCurrPlayers + "/" + gameSize + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return lobbyId == lobby.lobbyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lobbyId);
    }
}
