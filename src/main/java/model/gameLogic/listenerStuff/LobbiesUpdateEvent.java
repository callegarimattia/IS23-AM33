package model.gameLogic.listenerStuff;

import java.util.EventObject;
import java.util.List;

public class LobbiesUpdateEvent extends EventObject {
    private List<Integer> lobbyID;
    private List<Integer> currPlayers;
    private List<Integer> maxPlayers;

    public LobbiesUpdateEvent(Object source, List<Integer> lobbyID, List<Integer> currPlayers, List<Integer> maxPlayers) {
        super(source);
        this.lobbyID = lobbyID;
        this.currPlayers = currPlayers;
        this.maxPlayers = maxPlayers;
    }
}
