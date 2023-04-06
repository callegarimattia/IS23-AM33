package model;

import model.lobbies.GamesHandler;
import model.lobbies.LobbiesHandler;
import model.lobbies.LobbiesHandlerException;
import model.lobbies.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LobbiesHandlerTest {

    GamesHandler gameTester = new GamesHandler();
    LobbiesHandler lobbyTester = new LobbiesHandler();
    User mattia = new User("Mattia");

    @BeforeEach
    void init() {
        lobbyTester.initLobbiesHandler(gameTester);
        gameTester.initGameHandler(lobbyTester);

    }

    @AfterEach
    void teardown() {
        lobbyTester.getLobbies().clear();
        lobbyTester.getUsers().clear();
        assertTrue(lobbyTester.getUsers().isEmpty());
        assertTrue(lobbyTester.getLobbies().isEmpty());
    }

    @Test
    void createUser() {
        lobbyTester.createUser("Mattia");
        assertTrue(lobbyTester.getUsers().contains(mattia));
    }

    @Test
    void createUser_SameName() {
        lobbyTester.createUser("Mattia");
        assertThrows(LobbiesHandlerException.class, () -> lobbyTester.createUser("Mattia"));
    }

    @Test
    void searchUser() {
        lobbyTester.createUser("Mattia");
        assertEquals(mattia, lobbyTester.searchUser("Mattia"));

    }

    @Test
    void searchUser_NotPresent() {
        assertThrows(LobbiesHandlerException.class, () -> lobbyTester.searchUser("Mattia"));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void createLobby(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        lobbyTester.createLobby(mattia, numOfPlayers);
        assertTrue(lobbyTester.getLobbies().stream().anyMatch(lobby -> lobby.getUsers().contains(mattia) && lobby.getGameSize() == numOfPlayers));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -12})
    void createLobby_InvalidGameSizes(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        assertThrows(LobbiesHandlerException.class, () -> lobbyTester.createLobby(mattia, numOfPlayers));
    }
}