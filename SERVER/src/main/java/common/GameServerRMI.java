package common;
import org.json.simple.JSONObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameServerRMI extends Remote {
    JSONObject pickAndInsert(List<Integer> rows, List<Integer> columns, int myColumn) throws RemoteException;
    JSONObject sendChatMessage(String text, String recipient) throws RemoteException;

}


