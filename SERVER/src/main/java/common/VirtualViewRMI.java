package common;
import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface VirtualViewRMI extends Remote {

    //  ha solo i metodi che chiama il server
    void BoardUpdate(JSONObject data) throws RemoteException;
    void GameUpdate(JSONObject data) throws RemoteException;

    void LobbiesUpdate(JSONObject data) throws RemoteException;

    void StartGame(JSONObject data) throws RemoteException;

    void PersonalStartGame(JSONObject data) throws RemoteException;

    boolean checkAlive() throws RemoteException;

    void sendChatMessage(JSONObject data) throws RemoteException;
    void endGameMessage(JSONObject data) throws RemoteException;

}
