package common;
import org.json.simple.JSONObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualViewRMI extends Remote {

    //  ha solo i metodi che chiama il server
    void BoardUpdate(JSONObject data) throws RemoteException;

    void GameUpdate(JSONObject data) throws RemoteException;

    void LobbiesUpdate(JSONObject obj) throws RemoteException;

    boolean checkAlive() throws RemoteException;

    void StartGame(JSONObject data) throws RemoteException;

    void PersonalStartGame(JSONObject data) throws RemoteException;

    void sendChatMessage(JSONObject object) throws Exception;

    void endGameMessage(JSONObject data) throws RemoteException;
}
