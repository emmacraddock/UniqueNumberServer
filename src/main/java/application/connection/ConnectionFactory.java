package application.connection;

import application.client.IClientHandlerFactory;

import java.net.ServerSocket;

public class ConnectionFactory implements IConnectionFactory {
    private IClientHandlerFactory clientHandlerFactory;
    private int MAXIMUM_CONCURRENCY;


    public ConnectionFactory(IClientHandlerFactory clientHandlerFactory, int MAXIMUM_CONCURRENCY) {
        this.clientHandlerFactory = clientHandlerFactory;
        this.MAXIMUM_CONCURRENCY = MAXIMUM_CONCURRENCY;
    }


    public IConnection getConnection(ServerSocket serverSocket) {
        return new Connection(serverSocket, this.clientHandlerFactory, this.MAXIMUM_CONCURRENCY);
    }
}

