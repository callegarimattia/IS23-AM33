package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchMakerTest {
    MatchMaker testMatchMaker;
    User testUser1;
    User testUser2;
    User testUser3;
    User testUser4;

    @BeforeEach
    void init() {
        testMatchMaker = new MatchMaker();
        testUser1 = new User("Tia");
        testUser2 = new User("Ivan");
        testUser3 = new User("Marco");
    }

    @Test
    void removeUser() {
        testMatchMaker.addUser(testUser1);
        assertTrue(testMatchMaker.getAvailableUsers().contains(testUser1));
        testMatchMaker.removeUser(testUser1);
        assertFalse(testMatchMaker.getAvailableUsers().contains(testUser1));
    }

    @Test
    void removeUser_NotPresent() {
        assertFalse(testMatchMaker.getAvailableUsers().contains(testUser1));
        assertFalse(testMatchMaker.removeUser(testUser1));
    }

    @Test
    void addUser() {
        assertFalse(testMatchMaker.getAvailableUsers().contains(testUser1));
        testMatchMaker.addUser(testUser1);
        assertTrue(testMatchMaker.getAvailableUsers().contains(testUser1));
    }

    @Test
    void addUser_SameName() {
        User mattia = new User("Mattia");
        testMatchMaker.addUser(mattia);
        assertTrue(testMatchMaker.getAvailableUsers().contains(mattia));
        assertThrows(MatchMakingException.class, () -> testMatchMaker.addUser(new User("Mattia")));
    }

    @Test
    void removeLobby() {
        Lobby testLobby = new Lobby(testUser1, 2);
        testMatchMaker.addLobby(testLobby);
        assertTrue(testMatchMaker.getLobbies().contains(testLobby));
        testMatchMaker.removeLobby(testLobby);
        assertFalse(testMatchMaker.getLobbies().contains(testLobby));
    }
}