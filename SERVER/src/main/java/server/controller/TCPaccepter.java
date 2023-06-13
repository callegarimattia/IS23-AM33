package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// accetta nuove connessioni e crea per ognuna di esse un thread che le gestisce

class TCPaccepter implements Runnable {
    private final ServerSocket serverSocket;
    private final LobbiesHandler lobbiesHandler;

    public TCPaccepter(ServerSocket socket, LobbiesHandler lobbiesHandler) {
        this.serverSocket = socket;
        this.lobbiesHandler = lobbiesHandler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("Received client connection");
                Runnable r = new TCPclientParser(socket, lobbiesHandler);
                Thread th = new Thread(r);
                th.start();
            } catch (final IOException e) {
                break;
            }
        }
        try {
            serverSocket.close();
            System.out.println("closing all sockets");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
