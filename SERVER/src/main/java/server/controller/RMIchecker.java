package server.controller;

import server.model.User;

import java.rmi.RemoteException;

public class RMIchecker implements Runnable{
    LobbiesHandler lobbiesHandler;
    @Override
    public void run() {   //  must be shut down maybe
        while (true){
            for(User user : lobbiesHandler.getUsers()){
                try {
                    user.getMyClient().checkAlive();
                } catch (RemoteException e) {
                    System.out.println("RMI connection from user " + user.getUserName() + " lost");
                    lobbiesHandler.removeUser(user.getUserName());
                }
            }
            try {
                wait(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
