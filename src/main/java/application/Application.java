package application;

import application.client.ClientHandlerFactory;
import application.client.IClientHandlerFactory;
import application.connection.ConnectionFactory;
import application.connection.IConnectionFactory;
import application.logging.INumberLogger;
import application.logging.NumberLogger;

public class Application {
    public static volatile boolean isStopped = false;
    public static final String LOG_FILE = "numbers.log";
    public static final int MAXIMUM_CONCURRENCY = 5;

    public static void main(String[] args) {

        INumberLogger logger = new NumberLogger();
        IFileAdapter fileAdapter = new FileAdapter(LOG_FILE);
        IQueueWorker queueWorker = new QueueWorker(fileAdapter);
        IClientHandlerFactory clientHandlerFactory = new ClientHandlerFactory(queueWorker, logger);
        IConnectionFactory connectionFactory = new ConnectionFactory(clientHandlerFactory, MAXIMUM_CONCURRENCY);

        Server server = new Server(queueWorker, connectionFactory, logger);
        server.connectToServer();

    }
}
