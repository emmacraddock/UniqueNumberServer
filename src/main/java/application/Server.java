package application;

import application.connection.IConnection;
import application.connection.IConnectionFactory;
import application.logging.INumberLogger;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static ServerSocket serverSocket = null;
    private final int PORT = 4000;

    private IQueueWorker queueWorker;
    private IConnectionFactory connectionFactory;
    private INumberLogger logger;

    public Server(IQueueWorker queueWorker, IConnectionFactory connectionFactory, INumberLogger logger) {
        this.queueWorker = queueWorker;
        this.connectionFactory = connectionFactory;
        this.logger = logger;
    }

    public void connectToServer() {
        try {

            this.serverSocket = new ServerSocket(this.PORT);

            this.logger.start();
            this.queueWorker.startProcessing();

            IConnection connection = this.connectionFactory.getConnection(serverSocket);
            connection.startConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
