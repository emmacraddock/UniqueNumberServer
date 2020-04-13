package application.connection;

import application.Application;
import application.client.IClientHandler;
import application.client.IClientHandlerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Connection implements Runnable, IConnection {

    private ServerSocket serverSocket;
    private IClientHandlerFactory clientHandlerFactory;
    private int max_concurrency;

    public ThreadPoolExecutor threadPool;

    public Connection(ServerSocket serverSocket, IClientHandlerFactory clientHandlerFactory, int max_concurrency) {
        this.serverSocket = serverSocket;
        this.clientHandlerFactory = clientHandlerFactory;
        this.max_concurrency = max_concurrency;
        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.max_concurrency);
    }

    public void startConnection() {
        shutdownMonitor();
        new Thread(this::run).start();
    }

    public Socket getSocket() throws IOException {
        return this.serverSocket.accept();
    }

    public void listenForConnection() {
        try {
            Socket connectionSocket = getSocket();
            IClientHandler clientHandler = this.getClient(connectionSocket);
            threadPool.execute(clientHandler);

        } catch (SocketException se) {
            // if the application has been stopped,
            // we can safely ignore this exception
            if (!Application.isStopped) {
                se.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Application.isStopped) {
            listenForConnection();
        }
    }

    public void shutdownMonitor() {
        new Thread(() -> {
            while(!Application.isStopped) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            shutdown();
        }).start();
    }

    private void shutdown()  {
        this.threadPool.shutdown();
        try {
            if(!this.threadPool.isShutdown()){
                this.threadPool.shutdownNow();
            }
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private IClientHandler getClient(Socket connection) {
        return this.clientHandlerFactory.getClientHandler(connection);
    }
}
