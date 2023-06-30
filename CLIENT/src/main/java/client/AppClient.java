package client;
import gui.GuiApplication;
import javafx.application.Application;

import java.util.Scanner;

import static java.lang.System.exit;

public class AppClient {
    public static void main(String[] args) throws Exception{  // T for TCP, R for RMI

        //  CLI or GUI

        Scanner in = new Scanner(System.in);
        System.out.println("CLI or GUI ? (C/G): ");
        String str = in.next();

        while (!(str.equals("C") || str.equals("c") || str.equals("G") || str.equals("g"))) {
            System.out.print("invalid input, try again: ");
            str = in.next();
        }

        if (str.equals("C") || str.equals("c")) {  // CLI
            Runnable r = new CLI(args[0]);
            Thread th = new Thread(r);
            th.start();
            th.join();
            exit(777);
        } else {  // GUI
            Application.launch(GuiApplication.class, args);
        }

    }
}
