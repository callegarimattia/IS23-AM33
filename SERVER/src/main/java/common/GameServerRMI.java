package common;
import org.json.simple.JSONObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameServerRMI extends Remote {
    JSONObject pickAndInsert(JSONObject obj) throws RemoteException;
    JSONObject sendChatMessage(JSONObject obj) throws RemoteException;

}


