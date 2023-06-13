package com.example.gui.client.listenerStuff;

import com.example.gui.client.clientModel.Lobby;

import java.util.EventObject;
import java.util.Set;

public class LobbiesUpdateEvent extends EventObject {
    private final Set<Lobby> waitingLobbies;

    public LobbiesUpdateEvent(Object source, Set<Lobby> waitingLobbies) {
        super(source);
        this.waitingLobbies = waitingLobbies;
    }

    public Set<Lobby> getWaitingLobbies() {
        return waitingLobbies;
    }
}

