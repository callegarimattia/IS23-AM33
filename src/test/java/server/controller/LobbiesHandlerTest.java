package server.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LobbiesHandlerTest {

    LobbiesHandler lobbyTester = new LobbiesHandlerImpl();

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
        lobbyTester.createUser("Mattia");
        assertTrue(lobbyTester.searchUser("Mattia"));
    }

    @Test
    @DisplayName("Create User - Same name exception")
    void createUser_SameName() {
        lobbyTester.createUser("Mattia");
        assertFalse(lobbyTester.createUser("Mattia"));
    }

    @Test
    @DisplayName("Username Search tests")
    void searchUser() {
        lobbyTester.createUser("Mattia");
        assertTrue(lobbyTester.searchUser("Mattia"));
        lobbyTester.getUsers().clear();
        assertFalse(lobbyTester.searchUser("Mattia"));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Lobby creation test")
    void createLobby(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        int lobbyID = lobbyTester.createLobby("Mattia", numOfPlayers);
        assertTrue(lobbyID >= 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -3})
    @DisplayName("Lobby creation with invalid game size")
    void createLobby_InvalidGameSizes(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        assertEquals(lobbyTester.createLobby("Mattia", numOfPlayers), -3);
    }
}