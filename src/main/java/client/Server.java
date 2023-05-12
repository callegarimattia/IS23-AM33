package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    String sendMessage(String clientMessage) throws RemoteException;
}
