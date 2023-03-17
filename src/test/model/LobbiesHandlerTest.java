package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LobbiesHandlerTest {
    LobbiesHandler tester;
    User mattia = new User("Mattia");

    @BeforeEach
    void init() {
        tester = new LobbiesHandler();
    }

    @AfterEach
    void teardown() {
        tester.getLobbies().clear();
        tester.getUsers().clear();
        assertTrue(tester.getUsers().isEmpty());
        assertTrue(tester.getLobbies().isEmpty());
    }

    @Test
    void createUser() {
        tester.createUser("Mattia");
        assertTrue(tester.getUsers().contains(mattia));
    }

    @Test
    void createUser_SameName() {
        tester.createUser("Mattia");
        assertThrows(LobbiesHandlerException.class, () -> tester.createUser("Mattia"));
    }

    @Test
    void searchUser() {
        tester.createUser("Mattia");
        assertEquals(mattia, tester.searchUser("Mattia"));

    }

    @Test
    void searchUser_NotPresent() {
        assertThrows(LobbiesHandlerException.class, () -> tester.searchUser("Mattia"));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void createLobby(int numOfPlayers) {
        tester.createUser("Mattia");
        tester.createLobby(mattia, numOfPlayers);
        assertTrue(tester.getLobbies().stream().anyMatch(lobby -> lobby.getUsers().contains(mattia) && lobby.getGameSize() == numOfPlayers));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -12})
    void createLobby_InvalidGameSizes(int numOfPlayers) {
        tester.createUser("Mattia");
        assertThrows(LobbiesHandlerException.class, () -> tester.createLobby(mattia, numOfPlayers));
    }
}