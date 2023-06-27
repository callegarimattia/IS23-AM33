package server;

import server.controller.LobbiesHandler;
import server.controller.LobbiesHandlerImpl;

import java.rmi.RemoteException;
import java.util.Scanner;

public class AppServer {
    public static void main(String[] args) {

        LobbiesHandler lobbiesHandler = null;
        try {
            lobbiesHandler = new LobbiesHandlerImpl();
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        lobbiesHandler.startServer();

        // debug purpose only:

        while (true){
            System.out.println("\npress r for model refresh:");
            Scanner in = new Scanner(System.in);
            String str = in.next();
            if(str.equals("r")){
                lobbiesHandler.refresh();
            }

        }
    }
}
