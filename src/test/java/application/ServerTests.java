package application;

import application.connection.ConnectionFactory;
import application.connection.IConnectionFactory;
import application.logging.INumberLogger;
import application.logging.NumberLogger;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.verify;

public class ServerTests {

    @Test
    public void connect_to_server_starts_server_processes() throws IOException {
        IQueueWorker queueWorker = Mockito.mock(QueueWorker.class);
        INumberLogger numberLogger = Mockito.mock(NumberLogger.class);
        IConnectionFactory connectionFactory = Mockito.mock(ConnectionFactory.class, RETURNS_DEEP_STUBS);

        Server server = new Server(queueWorker, connectionFactory, numberLogger);
        server.connectToServer();

        verify(numberLogger).start();
        verify(queueWorker).startProcessing();
        verify(connectionFactory).getConnection(any(ServerSocket.class));
    }


}

