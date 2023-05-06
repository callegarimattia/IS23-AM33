package model.gameLogic.listenerStuff;

import java.util.EventListener;

public interface ModelUpdateListener extends EventListener {
    void OnGameUpdate(GameUpdateEvent evt);

    void OnLobbyUpdated(LobbiesUpdateEvent evt);
}
