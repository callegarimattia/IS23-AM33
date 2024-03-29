package server.controller;

import common.ServerRMI;
import common.VirtualViewRMI;
import org.json.simple.JSONObject;
import server.listenerStuff.LobbiesUpdateEvent;
import server.model.Lobby;
import server.model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LobbiesHandlerImpl extends UnicastRemoteObject implements LobbiesHandler, ServerRMI, GameEnder {  // Controller per Lobby
    private final Set<Lobby> waitingLobbies = new HashSet<>();
    private final Set<Lobby> inGameLobbies = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    private final int TCPport = 2345;  // sarebbe meglio prenderla da arg/json

    /**
     * <p>Default constructor. Initializes the RMI checker</p>
     *
     * @throws RemoteException when RMI checker throws an error
     */
    public LobbiesHandlerImpl() throws RemoteException {
        Runnable r = new RMIchecker(this);
        Thread t = new Thread(r);
        t.start();
    }

    /**
     * <p>Creates and then adds a new user to the users pool with given username.
     * It checks whether the username is already present and throws an exception if true.</p>
     *
     * @param newUsername username of the user to be created
     */
    @Override
    public List<String> createUser(String newUsername, VirtualViewRMI virtualView, Object parser) {
        List<String> message = new ArrayList<>(2);
        message.add(0, "1");
        synchronized (users) {
            for (User user : users)
                if (user.getUserName().equals(newUsername)) {
                    message.set(0, "0");  // username taken
                    return message;
                }

            for (User user : users)
                if (user.getMyClient() != null && user.getMyClient().equals(virtualView)) {
                    message.set(0, "-1");  // already asscociated
                    return message;
                }


            if(newUsername.equals("all")) {
                message.set(0, "-2");
                return message;
            }

            User newUser = new User(newUsername);
            newUser.setMyClient(virtualView);
            newUser.setMyParser((TCPclientParser) parser);
            users.add(newUser);
            System.out.println("NEW USERNAME ADDED ('" + newUsername + "')");
            message.add(1, newUsername);
            return message;
        }
    }

    /**
     * <p>Search the given username in the pool of users.
     * If found returns the corresponding user. Otherwise it returns null. </p>
     *
     * @param username of user to be searched
     * @return user or null if not present
     */
    @Override
    public boolean isUserPresent(String username) {  // da cancellare credo
        for (User user : users)
            if (user.getUserName().equals(username)) return true;
        return false;
    }

    /**
     * <p>Search username in pool of users</p>
     *
     * @param userName username to be searched
     * @return the user class associated with the given username. null if user is not present
     */
    @Override
    public User searchUser(String userName) {
        for (User user : users)
            if (user.getUserName().equals(userName))
                return user;
        return null;
    }

    /**
     * <p>Removes the user with given username</p>
     *
     * @param toBeRemovedUsername string of the username
     */

    public void removeUser(String toBeRemovedUsername) {  // rimuove uno user da lista users e waiting lobbies
        synchronized (this) {
            User toBeRem = searchUser(toBeRemovedUsername);
            for (User user : users)
                if (user.equals(toBeRem)) {
                    users.remove(user);
                    break;
                }

            for(Lobby lobby : waitingLobbies)
                for (User user : lobby.getUsers())
                    if (user.equals(toBeRem)){
                        lobby.removeUser(user);
                        if(lobby.getUsers().size() == 0){
                            waitingLobbies.remove(lobby);
                            LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
                            OnLobbyUpdate(evt);
                            return;
                        }
                    }
        }
    }

    @Override
    public List<String> shutDownClient(VirtualViewRMI virtualView) {
        List<String> message = new ArrayList<>(2);
        message.set(0, "-1");
        for(User user : users)
            if(user.getMyClient()!=null || user.getMyParser()!=null){
                removeUser(user.getUserName());
                System.out.println("user " + user.getUserName() + " deleted");
                message.set(0, "1");
                message.set(1, user.getUserName());
            }
        return message;
    }

    @Override
    public void checkAlive() throws RemoteException {

    }

    /**
     * <p>Request lobby list</p>
     *
     * @param virtualView
     * @param parser
     * @return
     */

    @Override
    public JSONObject lobbyListRequest(VirtualViewRMI virtualView, Object parser) {
        JSONObject answer = new JSONObject();
        answer.put("type", 1);

        User user = associatedUser(virtualView, parser);
        if (user != null && user.isInGame()) {
            answer.put("answer", "-1");
            return answer;
        }

        if(getWaitingLobbies().size() > 0){
            answer.put("answer", "1");
            List<Integer> lobbiesIDs = new ArrayList<>();
            List<Integer> lobbiesCurrentSize = new ArrayList<>();
            List<Integer> lobbiesMaxSizes = new ArrayList<>();
            Set<Lobby> waitingLobbyList = getWaitingLobbies();
            for(Lobby lobby : waitingLobbyList){
                lobbiesIDs.add(lobby.getID());
                lobbiesCurrentSize.add(lobby.getUsers().size());
                lobbiesMaxSizes.add(lobby.getGameSize());
            }
            answer.put("IDs", lobbiesIDs);
            answer.put("CurrentSizes", lobbiesCurrentSize);
            answer.put("MaxSizes", lobbiesMaxSizes);
        } else {
            answer.put("answer", "0");
        }
        return answer;
    }

    /**
     * Return the associated user with TCP or RMI from reference.
     *
     * @param virtualView
     * @param parser
     * @return
     */

    private User associatedUser(VirtualViewRMI virtualView, Object parser) {
        for (User user : users)
            if (user.getMyParser() != null && user.getMyParser().equals(parser) ||
                    user.getMyClient() != null && user.getMyClient().equals(virtualView))
                return user;
        return null;
    }

    /**
     * <p>Given a user and a gameSize it creates a lobby with given gameSize.
     * If user isn't in the user pool, the game size is invalid or user is already in a lobby or game,
     * returns an error code </p>
     *
     * @param gameSize gameSize
     * @param virtualView virtualView user
     * @param parser TCP util
     * @return message with confirm/error
     */
    @Override
    public synchronized List<Integer> createLobby(int gameSize, VirtualViewRMI virtualView, Object parser) {
        List<Integer> ans = new ArrayList<>(2);
        User firstUser = null;
        firstUser = associatedUser(virtualView, parser);

        if (firstUser == null){
            ans.add(0,-1) ; // bisogna prima creare user
            return ans;
        }

        if (firstUser.isInLobby() || firstUser.isInGame()){
            ans.add(0,-2);   // gia in lobby/game
            return ans;
        }

        if (gameSize > 4 || gameSize < 2){
            ans.add(0,-3);   // invalid game size
            return ans;
        }

        Lobby newLobby = new Lobby(gameSize);
        waitingLobbies.add(newLobby);
        System.out.println("CREATED NEW LOBBY ID(" + newLobby.getID() + ") WITH GAME SIZE " + gameSize);
        joinLobby(newLobby.getID(), firstUser.getMyClient(), firstUser.getMyParser());
        ans.add(0, 1);
        ans.add(1, newLobby.getID());
        return ans;
    }

    /**
     * <p> Search for a lobby given it's ID</p>
     *
     * @param ID id of the lobby to be searched
     * @return lobby class instance corresponding to id or null
     */
    @Override
    public Lobby searchLobby(int ID) {
        for (Lobby lobby : waitingLobbies)
            if (lobby.getID() == ID)
                return lobby;
        for (Lobby lobby : inGameLobbies)
            if (lobby.getID() == ID)
                return lobby;
        return null;
    }

    /**
     * Abort the lobby of given user with username when game ends
     *
     * @param userName string of the user
     */
    public void abortLobby(String userName) {  // cancella la lobby e tutti i suoi user (metodo chiamato quando finisce la partita per qualche motivo)
        synchronized (inGameLobbies) {
            for (Lobby lobby : inGameLobbies)
                for (User user : lobby.getUsers())
                    if (user.getUserName().equals(userName)) {
                        for (User us : lobby.getUsers())
                            users.remove(us);
                        inGameLobbies.remove(lobby);
                        System.out.println("LOBBY " + lobby.getID() + " ABORTED");
                    }
        }
        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
        OnLobbyUpdate(evt);
    }


    /**
     *  <p>Let a valid user, which is not in a lobby or game, join a not full lobby which hasn't started its game yet.
     *  Returns a message with validation or error</p>
     * @param ID
     * @param virtualView
     * @param obj
     * @return
     */
    @Override
    public synchronized int joinLobby(int ID, VirtualViewRMI virtualView, Object obj) {
        User joiningUser = null;
        joiningUser = associatedUser(virtualView, obj);

        if (joiningUser == null) return -2;  // bisogna prima creare lo user

        if (joiningUser.isInLobby() || joiningUser.isInGame()) return 0;  // user è gia in una lobby

        for (Lobby lobby : waitingLobbies)
            if (lobby.getID() == ID) {
                if (lobby.isFull()) return -3;  // full
                else {
                    lobby.add(joiningUser);
                    LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
                    OnLobbyUpdate(evt);
                    System.out.println("LOBBY ID(" + ID + ") JOINED BY ('" + joiningUser.getUserName() + "')");
                    if (lobby.isFull()) // controllo se deve partire il game
                        startGame(lobby.getID());
                    return 1;
                }
            }
        return -1; // lobby doesn't exist
    }

    /**
     * Listener event update for lobbies
     *
     * @param evt
     */
    private void OnLobbyUpdate(LobbiesUpdateEvent evt) {
        JSONObject data = lobbyListRequest(null, null);
        List<Integer> size = (List<Integer>) data.get("IDs");
        if (size != null)
            for (User user : users)
                if (!user.isInGame()) {

                    if (user.getMyClient() != null) {   // RMI:
                        try {
                            user.getMyClient().LobbiesUpdate(data);
                        } catch (RemoteException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    if(user.getMyParser()!= null){    // TCP
                        ObjectOutputStream myOut = user.getMyParser().getOut();
                        data.put("type", 99);
                        try {
                            myOut.writeObject(data);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

    }

    /**
     * Let a user leave it's lobby
     * @param virtualView
     * @param obj
     * @return a message with validation or error
     */
    @Override
    public synchronized String leaveLobby(VirtualViewRMI virtualView, Object obj) {
        User user = null;
        user = associatedUser(virtualView,obj);

        if (user == null) return "-2";     // u are not in a user yet

        if(!user.isInLobby()) return "0";   // u are not in a lobby

        if(user.isInGame()) return "-1";    // user is in an active game, cant leave lobby (shut down app if you want)

        int lobbyID = -1;
        for (Lobby lobby : waitingLobbies) {
            if (lobby.getUsers().contains(user)) {
                lobby.removeUser(user);
                lobbyID = lobby.getID();
                if(lobby.getUsers().size() == 0)
                    waitingLobbies.remove(lobby);
            }
        }
        if (lobbyID == -1) return "999";

        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
        OnLobbyUpdate(evt);
        System.out.println("LOBBY ID(" + lobbyID + ") LEAVED BY ('" + user.getUserName() + "')");
        return "1";
    }

    private synchronized boolean startGame(int toBeStartedLobbyID) {
        Lobby toBeStartedLobby = searchLobby(toBeStartedLobbyID);
        if (toBeStartedLobby == null) return false;
        if (!toBeStartedLobby.isFull()) return false;


        GameHandler gameHandler = toBeStartedLobby.initGame(this);   //creates game, game controller and passes RMI refs & TCP outs from users to players
        for(User user : toBeStartedLobby.getUsers())
            if(user.getMyParser() != null){
                user.getMyParser().setGameHandler(gameHandler);
            }


        inGameLobbies.add(toBeStartedLobby);
        waitingLobbies.remove(toBeStartedLobby);
        LobbiesUpdateEvent evt = new LobbiesUpdateEvent(this, waitingLobbies);
        OnLobbyUpdate(evt);
        System.out.println("LOBBY " + toBeStartedLobbyID + " GAME STARTED");
        return true;
    }

    /**
     * Getter of pool of users
     *
     * @return set of users
     */
    @Override
    public Set<User> getUsers() {  // delete
        return users;
    }

    /**
     * Getter of waiting lobbies
     *
     * @return set of lobbies
     */
    @Override
    public Set<Lobby> getWaitingLobbies() {  // delete
        return waitingLobbies;
    }

    private void startTCP() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(TCPport);
        } catch (final IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        Runnable TCPaccepter = new TCPaccepter(serverSocket, this);
        Thread th = new Thread(TCPaccepter);
        th.start();
        System.out.println("------------------- TCP SERVER ONLINE -------------------");
    }

    private void startRMI(){

        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            registry.bind("ServerRMI", this);
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------------------- RMI SERVER ONLINE -------------------");
    }

    /**
     * start server method
     */
    @Override
    public void startServer() {
        startTCP();
        startRMI();
    }

    @Override
    public void refresh() {   // debug purpose onlly
        System.out.println("REFRESHED: ");
        System.out.println("ALL USERS:  \n");
        for(User us: users){
            System.out.println("username:  " + us.getUserName());
            System.out.println("is in lobby:  " + us.isInLobby());
            System.out.println("is in game:  " + us.isInGame());
            System.out.println();
        }

        System.out.println("IN GAME LOBBIES: \n" );
        for(Lobby lobby : inGameLobbies){
            System.out.println("ID:  " + lobby.getID());
            System.out.println("size:  " + lobby.getGameSize());
            System.out.println("isFull:  " + lobby.isFull());
            System.out.print("users: ");
            for(User us : lobby.getUsers())
                System.out.print(us.getUserName() + " ");
            System.out.println();
            if(lobby.getGameHandler() != null){
                lobby.getGameHandler().refresh();
            }
        }

        System.out.print("\nPRE GAME LOBBIES: ");
        for (Lobby lobby : waitingLobbies) {
            System.out.println("\n\nID:  " + lobby.getID());
            System.out.println("Current size:  " + lobby.getUsers().size());
            System.out.println("To be reached size:  " + lobby.getGameSize());
            System.out.println("isFull:  " + lobby.isFull());
            System.out.print("users: ");
            for (User us : lobby.getUsers())
                System.out.print(us.getUserName() + " ");
        }
        System.out.println("\n");
    }

    /**
     * Link TCP parser to user
     *
     * @param newUserUsername username
     * @param parser          tcp parser instance
     */

    @Override
    public void addTCPparserToUser(String newUserUsername, TCPclientParser parser) {
        for (User user : users)
            if (user.getUserName().equals(newUserUsername))
                user.setMyParser(parser);
    }

    /**
     * Deletes the user who disconnects
     *
     * @param userName username of the disconnected user
     */
    public void disconnectedUser(String userName) {
        User me = searchUser(userName);
        if (me == null) return;
        Lobby myLobby = null;
        if (me.isInGame()) {
            for (Lobby lobby : inGameLobbies)
                for (User user : lobby.getUsers())
                    if (user.equals(me)) {
                        myLobby = lobby;
                        break;
                    }
            myLobby.getGameHandler().abortGame(me.getUserName());  // manda messaggio finale e chiude tutti i 4 (potenzialmente) thread parser
            abortLobby(userName);  // ed elimina anche tuttu i (4) user
            return;
        } else{  // sia che sia in una waitingLobby sia che sia un user e basta
            removeUser(me.getUserName());
        }
        System.out.println("connection with user "+ userName +" closed and user deleted");
    }
}


