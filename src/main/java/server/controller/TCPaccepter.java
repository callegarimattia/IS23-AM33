package server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class TCPaccepter implements Runnable {
    private ServerSocket serverSocket = null;
    private LobbiesHandler lobbiesHandler;

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
                TCPclientParser TCPclientHandler = new TCPclientParser(socket, lobbiesHandler);
                TCPclientHandler.run();
            } catch (final IOException e) {
                break;
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
