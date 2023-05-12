package server;

import client.Server;

public class ServerImpl implements Server {
    @Override
    public String sendMessage(String clientMessage) {
        return "Client Message".equals(clientMessage) ? "Server Message" : null;
    }
}
