package client;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.Socket;

public class TCPserverParser implements Runnable {
    private final Socket socket;

    public TCPserverParser(Socket socket) {
        this.socket = socket;
    }



    // ascolta tutti i messaggi del server (anche le risposte ai metodi)
    @Override
    public void run() {


        JSONObject obj = new JSONObject();
        boolean bool = true;  // sara messo a false con messaggio speciale di fine partita o simili
        while (bool) {
            ObjectInputStream in;
            try {
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String str = null;   //  mi da codice duplicato ma staranno poi su 2 eseguibili diversi
            try {
                str =  in.readObject().toString();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }


            JSONParser parser = new JSONParser();
            try {
                obj = (JSONObject) parser.parse(str);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }


            long x = (long)obj.get("type");
            switch ((int)x) {
                default:  // 0

                    break;
                case 1: // create user answer


                    break;
                case 2:


                    break;
                case 99:
                    //  messaggio con punteggi e vincitori
                    //  bool = false;
                    //  mando messaggi finali
                    bool = false;
                    break;
                }
            }

        System.out.println("input socket closing");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }



}
