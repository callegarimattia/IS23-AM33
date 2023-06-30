package server.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LobbiesHandlerTest {

    LobbiesHandler lobbyTester;

    {
        try {
            lobbyTester = new LobbiesHandlerImpl();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    void init() {

    }

    @AfterEach
    void teardown() {
        lobbyTester.getWaitingLobbies().clear();
        lobbyTester.getUsers().clear();
    }

    @Test
    @DisplayName("Create User")
    void createUser() {
        lobbyTester.createUser("Mattia", null,null);
        assertTrue(lobbyTester.isUserPresent("Mattia"));
    }

    @Test
    @DisplayName("Create User - Same name exception")
    void createUser_SameName() {
        lobbyTester.createUser("Mattia",null,null);
        assertEquals("0",lobbyTester.createUser("Mattia",null,null).get(0));
    }

    @Test
    @DisplayName("Username Search tests")
    void searchUser() {
        lobbyTester.createUser("Mattia", null,null);
        assertTrue(lobbyTester.isUserPresent("Mattia"));
        lobbyTester.getUsers().clear();
        assertFalse(lobbyTester.isUserPresent("Mattia"));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Lobby creation test")
    void createLobby(int numOfPlayers) {
        lobbyTester.createUser("Mattia", null,null);
        List<Integer> ans = lobbyTester.createLobby(numOfPlayers,null,null);
        assertFalse(ans.get(0) >= 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -3})
    @DisplayName("Lobby creation with invalid game size")
    void createLobby_InvalidGameSizes(int numOfPlayers) {
        lobbyTester.createUser("Mattia",null,null);
        assertEquals(-1,lobbyTester.createLobby(numOfPlayers,null,null).get(0));
    }
}