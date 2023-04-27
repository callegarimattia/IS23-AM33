package model.gameLogic.listenerStuff;

import java.util.EventListener;

public interface ModelUpdateListener extends EventListener {
    void OnUpdate(ModelUpdateEvent evt);

    void OnLobbyUpdated(LobbiesUpdateEvent evt);
}
