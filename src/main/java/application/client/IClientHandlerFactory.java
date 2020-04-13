package application.client;

import java.net.Socket;

public interface IClientHandlerFactory {
    IClientHandler getClientHandler(Socket connectionSocket);
}
