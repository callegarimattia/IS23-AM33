package server.controller;

import server.model.User;

import java.rmi.RemoteException;

public class RMIchecker implements Runnable{
    LobbiesHandler lobbiesHandler;

    public RMIchecker(LobbiesHandler lobbiesHandler) {
        this.lobbiesHandler = lobbiesHandler;
    }

    @Override
    public void run() {   //  must be shut down maybe
        while (true){
            doJob();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void doJob(){
        if(lobbiesHandler.getUsers()!=null){
            for(User user : lobbiesHandler.getUsers()){
                if(user.getMyClient()!=null)
                    try {
                        user.getMyClient().checkAlive();
                    } catch (RemoteException e) {
                        lobbiesHandler.disconnectedUser(user.getUserName());
                        return;
                    }
            }
        }
    }
}
