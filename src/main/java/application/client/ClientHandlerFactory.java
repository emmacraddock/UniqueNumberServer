package application.client;

import application.logging.INumberLogger;
import application.IQueueWorker;

import java.net.Socket;

public class ClientHandlerFactory implements IClientHandlerFactory {
    private IQueueWorker queueWorker;
    private INumberLogger logger;

    public ClientHandlerFactory(IQueueWorker queueWorker, INumberLogger logger) {
        this.queueWorker = queueWorker;
        this.logger = logger;
    }

    public IClientHandler getClientHandler(Socket connectionSocket) {
        return new ClientHandler(connectionSocket, this.queueWorker, this.logger);

    }
}
