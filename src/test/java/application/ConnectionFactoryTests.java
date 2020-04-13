package application;

import application.client.ClientHandlerFactory;
import application.client.IClientHandlerFactory;
import application.connection.ConnectionFactory;
import application.connection.IConnection;
import application.connection.IConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionFactoryTests {
    @Test
    public void connectionFactory_returns_connection() throws IOException {

        IClientHandlerFactory clientHandlerFactory = Mockito.mock(ClientHandlerFactory.class);
        IConnectionFactory connectionFactory = new ConnectionFactory(clientHandlerFactory, 5);

        IConnection conn = connectionFactory.getConnection(new ServerSocket(9999));

        Assert.assertNotNull(conn);
    }
}
