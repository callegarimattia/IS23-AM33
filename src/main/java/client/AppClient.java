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




        boolean inGame = false;
        System.out.println("List of commands: \n-1: exit game\n0: create user\n1: ask list of lobbies\n2: ...");
        while (!inGame){
            String x = in.next();
            switch (x){
                default:
                    System.out.println("invalid command, try again");
                    break;
                case "-1":
                    client.shutDown();
                    break;
                case "0":
                    if(client.getUserName() == null){
                        System.out.println("insert userName: ");
                        String st = in.next();
                        client.createUser(st);
                    }
                    else {
                        System.out.println("invalid command, username already setted");
                    }

                    break;
                case "1":

                    break;
            }
        }

        //  nuovo ciclo con comandi di gioco
    }
}
