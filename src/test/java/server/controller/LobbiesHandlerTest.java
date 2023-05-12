package server.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import server.model.User;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbiesHandlerTest {

    LobbiesHandler lobbyTester = new LobbiesHandler();
    User mattia = new User("Mattia");

    LobbiesHandlerTest() throws RemoteException {
    }

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
/*
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    @DisplayName("Lobby creation test")
    void createLobby(int numOfPlayers) throws RemoteException {
        lobbyTester.createUser("Mattia");
        assertTrue(lobbyTester.createLobby("Mattia", numOfPlayers));
        assertTrue(lobbyTester.getLobbies().stream().anyMatch(lobby -> lobby.getUsers().contains("Mattia") && lobby.getGameSize() == numOfPlayers));
    }*/

    @ParameterizedTest
    @ValueSource(ints = {1, 5, -3})
    @DisplayName("Lobby creation with invalid game size")
    void createLobby_InvalidGameSizes(int numOfPlayers) {
        lobbyTester.createUser("Mattia");
        assertFalse(lobbyTester.createLobby("Mattia", numOfPlayers));
    }
}