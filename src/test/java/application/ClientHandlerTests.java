package application;

import application.client.ClientHandler;
import application.logging.INumberLogger;
import application.logging.NumberLogger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientHandlerTests {
    @After
    public void cleanUp() {
        ClientHandler.UNIQUE_VALUE_STORE.clear();
    }

    @Test
    public void given_invalid_input_connectionIsClosed() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("bad input :( \nHost: localhost".getBytes());
        INumberLogger mockLogger = Mockito.mock(NumberLogger.class);
        QueueWorker mockQueueWorker = Mockito.mock(QueueWorker.class);
        Socket mockSocket = Mockito.mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ClientHandler handler = new ClientHandler(mockSocket, mockQueueWorker, mockLogger);
        handler.run();

        verify(mockSocket).close();
        verify(mockQueueWorker, never()).addToProcessingQueue(any());
        Assert.assertEquals(0, handler.UNIQUE_VALUE_STORE.size());
    }

    @Test
    public void given_unique_value_input_is_processed() throws IOException { // better name
        ByteArrayInputStream inputStream = new ByteArrayInputStream("123456789\nHost: localhost".getBytes());
        QueueWorker mockQueueWorker = Mockito.mock(QueueWorker.class);
        INumberLogger mockLogger = Mockito.mock(NumberLogger.class);
        Socket mockSocket = Mockito.mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ClientHandler handler = new ClientHandler(mockSocket, mockQueueWorker, mockLogger);
        handler.run();

        verify(mockSocket).close();
        verify(mockQueueWorker).addToProcessingQueue(any());
        verify(mockQueueWorker).finishProcessing();
        Assert.assertEquals(1, handler.UNIQUE_VALUE_STORE.size());
    }

    @Test
    public void given_duplicate_value_processing_is_skipped() throws IOException {
        String duplicateInput = "123456789";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                String.format("%s\nHost: localhost", duplicateInput).getBytes());
        QueueWorker mockQueueWorker = Mockito.mock(QueueWorker.class);
        INumberLogger mockLogger = Mockito.mock(NumberLogger.class);
        Socket mockSocket = Mockito.mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ClientHandler handler = new ClientHandler(mockSocket, mockQueueWorker, mockLogger);
        handler.UNIQUE_VALUE_STORE.add(duplicateInput);
        handler.run();

        verify(mockSocket).close();
        verify(mockQueueWorker, never()).addToProcessingQueue(any());
        Assert.assertEquals(1, handler.UNIQUE_VALUE_STORE.size());
    }

    @Test
    public void given_terminate_command_connectionIsClosed() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("terminate\nHost: localhost".getBytes());
        QueueWorker mockQueueWorker = Mockito.mock(QueueWorker.class);
        NumberLogger mockLogger = Mockito.mock(NumberLogger.class);
        Socket mockSocket = Mockito.mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ClientHandler handler = new ClientHandler(mockSocket, mockQueueWorker, mockLogger);
        handler.run();

        verify(mockSocket).close();
        verify(mockQueueWorker, never()).addToProcessingQueue(any());
        Assert.assertEquals(0, handler.UNIQUE_VALUE_STORE.size());
        Assert.assertTrue(Application.isStopped);
    }
}
