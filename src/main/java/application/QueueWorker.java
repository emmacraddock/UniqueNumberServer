package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueWorker extends TimerTask implements IQueueWorker {

    private IFileAdapter fileAdapter;
    LinkedBlockingQueue processingQueue = new LinkedBlockingQueue();

    public QueueWorker(IFileAdapter fileAdapter) {
        this.fileAdapter = fileAdapter;
    }

    public void startProcessing() throws IOException {
        this.fileAdapter.resetFile();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0, 1000);
    }

    public void addToProcessingQueue(String input) {
        processingQueue.add(input);
    }

    public void finishProcessing() {
        processQueue();
    }

    public void run() {
        ArrayList batch = new ArrayList();
        processingQueue.drainTo(batch);
        try {
            this.fileAdapter.writeToFile(batch);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processQueue() {
        ArrayList batch = new ArrayList();
        processingQueue.drainTo(batch);
        try {
            this.fileAdapter.writeToFile(batch);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
