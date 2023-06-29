package client;

import client.clientModel.ClientDataStructure;
import common.GameServerRMI;
import common.ServerRMI;
import common.VirtualViewRMI;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class ClientRMI extends UnicastRemoteObject implements Client, VirtualViewRMI {
    ServerRMI server;
    String serverIP = null;
    CLI cli;
    Scanner scanner = new Scanner(System.in);
    private GameServerRMI gameServer= null;
    private final ClientDataStructure data = new ClientDataStructure();

    public ClientRMI(CLI cli) throws RemoteException {
        this.cli = cli;
        newConnection("localhost", 1099);
    }

    @Override
    public ClientDataStructure getData() {
        return data;
    }

    @Override
    public void newConnection(String serverIP, int port) {
        try {
            server = (ServerRMI) Naming.lookup("rmi://" + serverIP + "/ServerRMI");
        } catch (NotBoundException | MalformedURLException| RemoteException e) {
            System.out.print("server isn't online yet, closing application...");
            exit(-1);
        }
        Runnable r = new RMIchecker(server, cli);
        Thread th = new Thread(r);
        th.start();
        this.serverIP = serverIP;
    }

    private void connectGame(){
        int ID = data.getMyLobbyID();
        String binder = "GameServer" + ID;
        try {
            gameServer = (GameServerRMI) Naming.lookup("rmi://" + serverIP + "/"+ binder);
        } catch (NotBoundException | MalformedURLException| RemoteException e) {
            System.out.print("game RMI not found...");
            exit(-1);
        }

     /*   try {
            gameServer = (GameServerRMI) Naming.lookup("rmi://" + serverIP + "prova");
        } catch (NotBoundException | MalformedURLException| RemoteException e) {
            System.out.print("game RMI not found...");
            exit(-1);
        }  */
    }


    @Override
    public void shutDown() { // -1
        try {
            List<String> mess = server.shutDownClient(this);
            String ans = mess.get(0);
            String user = null;
            if(ans.equals("2"))
                user = mess.get(1);
            data.ansClientClosingApp(ans, user);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createUser(String username) {  // 0
        try {
            List<String> ans = server.createUser(username,this, null);
            data.ansCreateUser(ans);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void LobbiesUpdate(JSONObject obj) throws RemoteException {  // 99
        try {
            JSONObject answer =  server.lobbyListRequest(this, null);
            List<Integer> lobbiesIDs = (List<Integer>) answer.get("IDs");
            List<Integer> lobbiesCurrentSize = (List<Integer>) answer.get("CurrentSizes");
            List<Integer> lobbiesMaxSizes = (List<Integer>) answer.get("MaxSizes");
            List<Long> lobbiesIDs2 = new ArrayList<>(), lobbiesCurrentSize2 = new ArrayList<>(), lobbiesMaxSizes2 = new ArrayList<>();
            if(lobbiesIDs != null)
                for (int i=0;i<lobbiesIDs.size();++i) {
                    lobbiesIDs2.add(lobbiesIDs.get(i).longValue());
                    lobbiesCurrentSize2.add(lobbiesCurrentSize.get(i).longValue());
                    lobbiesMaxSizes2.add(lobbiesMaxSizes.get(i).longValue());
                }
            answer.put("IDs", lobbiesIDs2);
            answer.put("CurrentSizes", lobbiesCurrentSize2);
            answer.put("MaxSizes", lobbiesMaxSizes2);
            data.onLobbyUpdate(answer);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void lobbyListRequest() {  // 1
        try {
            JSONObject answer =  server.lobbyListRequest(this, null);
            LobbiesUpdate(answer);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createLobby(int gameSize) {  // 2
        try {
            List<Integer> ans = server.createLobby(gameSize,this,null);
            List<Long> longs = ans.stream()
                    .mapToLong(Integer::longValue)
                    .boxed().collect(Collectors.toList());
            data.ansNewLobbyCreation(longs);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void joinLobby(int ID) {   // 3
        try {
            data.setMyLobbyID(ID);
            int ans = server.joinLobby(ID,this,null);
            data.ansJoinLobbyRequest(ans, ID);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void leaveLobby() {  // 4
        try {
            String ans = server.leaveLobby(this, null);
            data.ansLeaveLobbyRequest(ans);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) {  //  5
        JSONObject obj = new JSONObject();

        List<Long> columns2 = columns.stream()
                .mapToLong(Integer::longValue)
                .boxed().toList();
        List<Long> rows2 = rows.stream()
                .mapToLong(Integer::longValue)
                .boxed().toList();

        obj.put("columns", columns2);
        obj.put("rows", rows2);
        obj.put("myColumn", myColumn);
        obj.put("name", data.getMyUsername());
        try {
            JSONObject ans = gameServer.pickAndInsert(obj);
            data.ansPickAndInsert(ans);   // sara da modificare come al solito con i long
        } catch (RemoteException e) {
            System.out.println("you are not in game yet");
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void sendChatMessage(String userName, String message, int visibility) {

    }

    @Override
    public void sendChatMessage(String text, String recipient) {


        System.out.println("you are not in game yet");
    }

    @Override
    public void GameUpdate(JSONObject obj) throws RemoteException {
        List<Integer> cols = (List<Integer>) obj.get("cols");
        List<Integer> rows = (List<Integer>) obj.get("rows");
        List<Long> cols2 = cols.stream()
                .mapToLong(Integer::longValue)
                .boxed().toList();
        List<Long> rows2 = rows.stream()
                .mapToLong(Integer::longValue)
                .boxed().toList();
        obj.put("cols",cols2);
        obj.put("rows",rows2);
        data.onGameUpdate(obj);
    }



    @Override
    public boolean checkAlive() throws RemoteException {
        return true;
    }

    @Override
    public void StartGame(JSONObject obj) throws RemoteException {
        List<List<Integer>> intMainBoard = (List<List<Integer>>) obj.get("mainBoard");
        List<List<Long>> longs = new ArrayList<>();
        for (int i=0 ; i < intMainBoard.size() ; i++) {
            List<Long> lis = new ArrayList<>();
            for (int j=0;j<intMainBoard.size();j++)
              lis.add(intMainBoard.get(i).get(j).longValue());
            longs.add(lis);
        }
        obj.put("mainBoard", longs);
        data.startGame(obj);
    }

    @Override
    public void PersonalStartGame(JSONObject obj) throws RemoteException {
        List<Integer> coordinatesList = (List<Integer>) obj.get("coordinates");
        List<Integer> intValues = (List<Integer>) obj.get("values");
        List<Long> coord = new ArrayList<>(), val = new ArrayList<>();
        for (int i=0 ; i < coordinatesList.size() ; i++)
            coord.add(coordinatesList.get(i).longValue());
        for (int i=0 ; i < intValues.size() ; i++)
            val.add(intValues.get(i).longValue());
        obj.put("coordinates", coord);
        obj.put("values",val);
        data.personalStartGame(obj);

        this.connectGame();

    }




}
