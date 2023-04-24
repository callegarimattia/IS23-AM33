package controller;

import model.lobbies.LobbiesHandlerException;
import model.lobbies.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LobbiesHandlerTest {

    LobbiesHandler lobbyTester = new LobbiesHandler();
    User mattia = new User("Mattia");

    @BeforeEach
    void init() {

    }

    @AfterEach
    void teardown() {
        lobbyTester.getLobbies().clear();
        lobbyTester.getUsers().clear();
    }

    @Test
    @DisplayName("Create User")
    void createUser() {
        lobbyTester.createUser("Mattia");
        assertTrue(lobbyTester.getUsers().contains(mattia));
    }

    @Test
    @DisplayName("Create User - Same name exception")
    void createUser_SameName() {
        lobbyTester.createUser("Mattia");
        assertThrows(LobbiesHandlerException.class, () -> lobbyTester.createUser("Mattia"));
    }

    @Test
    @DisplayName("Username Search tests")
    void searchUser() {
        lobbyTester.createUser("Mattia");
        assertEquals(mattia, lobbyTester.searchUser("Mattia"));
        lobbyTester.getUsers().clear();
        assertThrows(LobbiesHandlerException.class, () -> lobbyTester.searchUser("Mattia"));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Lobby creation test")
    void createLobby(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        lobbyTester.createLobby(mattia, numOfPlayers);
        assertTrue(lobbyTester.getLobbies().stream().anyMatch(lobby -> lobby.getUsers().contains(mattia) && lobby.getGameSize() == numOfPlayers));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -3})
    @DisplayName("Lobby creation with invalid game size")
    void createLobby_InvalidGameSizes(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        assertThrows(LobbiesHandlerException.class, () -> lobbyTester.createLobby(mattia, numOfPlayers));
    }
}