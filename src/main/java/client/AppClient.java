package client;


import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI

        Scanner in = new Scanner(System.in);
        Client client = null;
        System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI)");
        String str = in.next();
        if (str.equals("T") || str.equals("t")) {
            client = new ClientTCP();
            //  devo fare new connection nel costruttore perche devo farla prima di altre cose che faccio nel costruttore
        } else {
            client = new ClientRMI();
            client.newConnection("localhost", 1099);  // dovranno essere presi da arg/json/CL
        }

        System.out.println("insert username:");
        str = in.next();
        client.createUser(str);


        while (true){

        }
    }
}
