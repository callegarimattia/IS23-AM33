package client;


import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) throws Exception {  // T for TCP, R for RMI

        Scanner in = new Scanner(System.in);
        Client client;
        System.out.println("Usage: T (or R) IPserver (T = TCP, R = RMI)");
        String str = in.next();

        while (!(str.equals("T") || str.equals("t") || str.equals("R") || str.equals("r"))){
            System.out.print("invalid input, try again: ");
            str = in.next();
        }

        if (str.equals("T") || str.equals("t")) {
            client = new ClientTCP();
            //  devo fare new connection nel costruttore perche devo farla prima di altre cose che faccio nel costruttore
        } else {
            client = new ClientRMI();
            client.newConnection("localhost", 1099);  // dovranno essere presi da arg/json/CL
        }

        //  CLI or GUI

        System.out.println("CLI or GUI ? (C/G): ");
        str = in.next();

        while (!(str.equals("C") || str.equals("c") || str.equals("G") || str.equals("g"))){
            System.out.print("invalid input, try again: ");
            str = in.next();
        }

        if (str.equals("C") || str.equals("c")) {  // CLI
            Runnable r = new CLI(client);
            Thread th = new Thread(r);
            th.start();
        } else {  // GUI

        }






    }
}
