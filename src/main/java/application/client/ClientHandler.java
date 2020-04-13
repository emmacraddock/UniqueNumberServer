package application.client;

import application.*;
import application.logging.INumberLogger;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable, IClientHandler {
    private Socket clientSocket;
    private IQueueWorker queueWorker;
    private INumberLogger logger;

    public static Set<String> UNIQUE_VALUE_STORE = ConcurrentHashMap.newKeySet();

    public ClientHandler(Socket clientSocket, IQueueWorker queueWorker, INumberLogger logger) {

        this.clientSocket = clientSocket;
        this.queueWorker = queueWorker;
        this.logger = logger;
    }

    public void run() {
        try (InputStream inputToServer = this.clientSocket.getInputStream()) {
            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            while (!Application.isStopped) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!isValidInput(line)) {
                        break;
                    }
                    if (line.toLowerCase().trim().equals("terminate")) {
                        Application.isStopped = true;
                        break;
                    }
                    if (UNIQUE_VALUE_STORE.add(line)) {
                        this.logger.incrementUnique();
                        this.queueWorker.addToProcessingQueue(line);
                    } else {
                        this.logger.incrementCurrentDupes();
                    }
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // process any remaining work
            this.queueWorker.finishProcessing();
        }
    }

    private boolean isValidInput(String input) {
        return Pattern.matches("([0-9]{9}|terminate)$", input);
    }
}

