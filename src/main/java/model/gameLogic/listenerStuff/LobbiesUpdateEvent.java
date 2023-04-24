package model.gameLogic.listenerStuff;

import java.util.EventObject;
import java.util.List;

public class LobbiesUpdateEvent extends EventObject {
    private final List<Integer> lobbyID;
    private final List<Integer> currPlayers;
    private final List<Integer> maxPlayers;

    public LobbiesUpdateEvent(Object source, List<Integer> lobbyID, List<Integer> currPlayers, List<Integer> maxPlayers) {
        super(source);
        this.lobbyID = lobbyID;
        this.currPlayers = currPlayers;
        this.maxPlayers = maxPlayers;
    }
}
