package client;
import client.clientModel.ClientDataStructure;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class TCPserverParser implements Runnable {
    private final CLI cli;
    private ClientDataStructure data = new ClientDataStructure();
    private final Socket socket;
    private final Client client;  // devo poi usare l interfaccia ma per ora ho i metodi solo su TCP


    public TCPserverParser(Socket socket, Client client, CLI cli) {
        this.socket = socket;
        this.client = client;
        this.cli = cli;;
    }


    @Override
    public void run() {
        JSONObject obj;
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {

            String str = null;   //  mi da codice duplicato ma staranno poi su 2 eseguibili diversi
            try {
                str = in.readObject().toString();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            JSONParser parser = new JSONParser();
            try {
                obj = (JSONObject) parser.parse(str);
            } catch (Exception e) {
                System.out.println("server went offline, press any key to close app");
                cli.shutDown();
                break;
            }

            String answer = null;
            long x = (long) obj.get("type");
            switch ((int) x) {
                default:
                    System.out.println("default branch, invalid type message");
                    break;
                case -1:
                    answer = obj.get("answer").toString();
                    String disconnectedPlayer = obj.get("disconnectedPlayer").toString();
                    data.ansClientClosingApp(answer,disconnectedPlayer);
                    break;
                case 0:
                    List<String> ans = (List<String>) obj.get("data");
                    data.ansCreateUser(ans);
                    break;
                case 1:
                    List<Long> dataList = (List<Long>) obj.get("data");
                    data.ansLobbyListRequest(dataList);
                    break;
                case 2:
                    data.ansNewLobbyCreation(obj);
                    break;
                case 3:
                    data.ansJoinLobbyRequest(obj);
                    break;
                case 4:
                    data.ansLeaveLobbyRequest(obj);
                    break;
                case 5:
                    data.ansPickAndInsert(obj);
                    break;
                case 6:
                    data.ansChatMessage(obj);
                    break;

                case 777:
                    data.startGame(obj);
                    break;
                case 778:
                    data.personalStartGame(obj);
                    break;
                case 99:
                    List<Long> lobbiesList = (List<Long>) obj.get("data");
                    data.onLobbyUpdate(lobbiesList);
                    break;
                case 100:
                    data.onGameUpdate(obj);
                    break;
                case 101:
                    data.recivedChatMessage(obj);
                    break;
                case 999:
                    //  messaggio con punteggi e vincitori
                    //  bool = false;
                    //  mando messaggi finali
                    break;
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}
