package common;
import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface VirtualViewRMI extends Remote {

    //  ha solo i metodi che chiama il server

    public void GameUpdate(List<String> players /* ...TBD...*/) throws RemoteException;

    public void LobbiesUpdate(JSONObject data) throws RemoteException;

    public boolean checkAlive() throws RemoteException;

}
