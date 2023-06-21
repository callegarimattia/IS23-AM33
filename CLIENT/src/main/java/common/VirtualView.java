package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface VirtualView extends Remote {

    //  ha solo i metodi che chiama il server

    public void GameUpdate(List<String> players /* ...TBD...*/) throws RemoteException;

    public void LobbiesUpdate(List<String> players /* ...TBD...*/) throws RemoteException;

}
