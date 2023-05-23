package client;


import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI

        Scanner in = new Scanner(System.in);
        Client client;
        System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI)");
        String str = in.next();
        if (str.equals("T") || str.equals("t")) {
            client = new ClientTCP();
            //  devo fare new connection nel costruttore perche devo farla prima di altre cose che faccio nel costruttore
        } else {
            client = new ClientRMI();
            client.newConnection("localhost", 1099);  // dovranno essere presi da arg/json/CL
        }




        //  CLI

        boolean inGame = false;
        System.out.println("List of commands: \n-1: exit game\n0: create user\n1: ask list of lobbies\n2: new lobby\n3: join lobby\n4: leave lobby");
        while (!inGame){
            String x = in.next();
            switch (x){
                default:
                    System.out.println("invalid command, try again");
                    break;
                case "-1":  // close app
                    client.shutDown();
                    break;
                case "0":  // create user
                    client.createUser();
                    break;
                case "1":  // lobbies list request
                    client.lobbyListRequest();
                    break;
                case "2":  // create lobby
                    client.createLobby(666); // parametro sieze viene in realta chiesto nel metodo ma non volevo rompere RMI
                    break;
                case "3":  // join lobby
                    client.joinLobby(666); // stessa cosa
                    break;
                case "4":  // leave lobby
                    client.leaveLobby();
                    break;
            }
        }


    }
}
