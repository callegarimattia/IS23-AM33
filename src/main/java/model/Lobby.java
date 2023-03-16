package model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final ArrayList<User> waitingUsers = new ArrayList<>();
    private final int gameSize;

    public Lobby(User firstUser, int gameSize) {
        this.waitingUsers.add(firstUser);
        this.gameSize = gameSize;
    }

    public List<User> getUsers() {
        return waitingUsers;
    }

    public boolean isReady() {
        return waitingUsers.size() == gameSize;
    }

    public void join(User newUser) {
        waitingUsers.add(newUser);
    }

    public void leave(User oldUser) {
        waitingUsers.remove(oldUser);
    }
}
