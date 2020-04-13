package application;

import application.client.ClientHandler;
import application.client.ClientHandlerFactory;
import application.connection.Connection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConnectionTests {

    private Connection connection;

    @Before
    public void setup() throws IOException {

        ServerSocket serverSocket = new ServerSocket(4444);

        ClientHandlerFactory clientHandlerFactory = mock(ClientHandlerFactory.class);
        when(clientHandlerFactory.getClientHandler(any())).thenReturn(mock(ClientHandler.class, RETURNS_DEEP_STUBS));
        this.connection = new Connection(serverSocket, clientHandlerFactory, 5){
            @Override
            public Socket getSocket() {
                return mock(Socket.class);
            }
        };

    }

    @Test
    public void connection_limits_input_to_five() throws IOException {
        int expectedThreadPoolSize = 5;

        // queue up 10 tasks
        for(int i = 0; i < 10; i++) {
            this.connection.listenForConnection();
        }

        // assert pools size is capped at 5
        Assert.assertEquals(expectedThreadPoolSize, connection.threadPool.getMaximumPoolSize());
        Assert.assertEquals(expectedThreadPoolSize, connection.threadPool.getPoolSize());

        // clean up threadPool
        connection.threadPool.shutdownNow();
        Assert.assertTrue(connection.threadPool.isShutdown());

    }
}
