package application.connection;

import java.net.ServerSocket;

public interface IConnectionFactory {
    IConnection getConnection(ServerSocket serverSocket);
}
