package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPserverParser implements Runnable {
    private Socket socket = null;

    public TCPserverParser(Socket socket) {
        this.socket = socket;
    }



    @Override
    public void run() {

        try {
            final ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //  da sostituire con qualcosa che aspetta e legge i json dal server
        ////////////
        /*
        while (true) {
            TBD
        }
        */
        //////////////


    }



}
