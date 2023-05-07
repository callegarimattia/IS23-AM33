package server.listenerStuff;

import java.util.EventListener;

public interface ModelUpdateListener extends EventListener {
    void OnGameUpdate(GameUpdateEvent evt);

    void OnLobbyUpdate(LobbiesUpdateEvent evt);
}
