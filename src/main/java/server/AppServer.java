package server;

import server.controller.LobbiesHandler;
import server.controller.LobbiesHandlerImpl;

public class AppServer {
    public static void main(String[] args) {

        LobbiesHandler lobbiesHandler = new LobbiesHandlerImpl();
        lobbiesHandler.startServer();
    }
}
