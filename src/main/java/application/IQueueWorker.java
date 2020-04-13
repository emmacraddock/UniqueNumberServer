package application;

import java.io.IOException;

public interface IQueueWorker {
    public void startProcessing() throws IOException;
    public void addToProcessingQueue(String input);
    public void finishProcessing();
}
