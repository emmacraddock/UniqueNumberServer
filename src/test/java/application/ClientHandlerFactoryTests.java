package application;

import application.client.ClientHandlerFactory;
import application.client.IClientHandler;
import application.client.IClientHandlerFactory;
import application.logging.INumberLogger;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.Socket;

public class ClientHandlerFactoryTests {
    @Test
    public void getClientHandler_returns_ClientHandler() {
        IQueueWorker mockQueueWorker = Mockito.mock(QueueWorker.class);
        INumberLogger mockLogger = Mockito.mock(INumberLogger.class);
        Socket mockSocket = Mockito.mock(Socket.class);

        IClientHandlerFactory factory = new ClientHandlerFactory(mockQueueWorker, mockLogger);
        IClientHandler handler = factory.getClientHandler(mockSocket);

        Assert.assertNotNull(handler);
    }
}
