package model.gameLogic.listenerStuff;

import java.util.EventListener;

public interface ModelUpdateListener extends EventListener {
    public void OnUpdate(ModelUpdateEvent evt);

    public void OnLobbyUpdated(LobbiesUpdateEvent evt);
}
