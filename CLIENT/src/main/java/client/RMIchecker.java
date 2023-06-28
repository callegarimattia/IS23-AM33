package client;
import common.ServerRMI;

import java.rmi.RemoteException;

public class RMIchecker implements Runnable{
    ServerRMI server;
    CLI cli;

    public RMIchecker(ServerRMI server, CLI cli) {
        this.server = server;
        this.cli = cli;
    }

    @Override
    public void run() {
        while (true) {
            try {
                server.checkAlive();
            } catch (RemoteException e) {
                System.out.print("server went offline, press any key to close the app: ");
                cli.shutDown();
                return;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
