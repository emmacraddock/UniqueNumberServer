package application;

import java.io.IOException;

public interface IQueueWorker {
    void startProcessing() throws IOException;
    void addToProcessingQueue(String input);
    void finishProcessing();
}
