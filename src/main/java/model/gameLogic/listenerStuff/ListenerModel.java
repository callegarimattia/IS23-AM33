package model.gameLogic.listenerStuff;

public class ListenerModel implements ModelUpdateListener {

    // lista/array di 4 socket max

    @Override
    public void OnGameUpdate(GameUpdateEvent evt) {
        // manda messaggio via TCP/RMI alla lista ascoltatori
    }

    @Override
    public void OnLobbyUpdated(LobbiesUpdateEvent evt) {

    }
}
