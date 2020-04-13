package application;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class QueueWorkerTests {

    @Test
    public void addToProcessingQueue_adds_to_queue() throws InterruptedException {
        IFileAdapter mockFileAdapter = Mockito.mock(IFileAdapter.class);
        QueueWorker queueWorker = new QueueWorker(mockFileAdapter);
        queueWorker.addToProcessingQueue("123456789");

        Assert.assertEquals(1, queueWorker.processingQueue.size());
        Assert.assertEquals("123456789", queueWorker.processingQueue.take());
    }

    @Test
    public void finishProcessing_process_remaining_work() throws IOException {
        IFileAdapter mockFileAdapter = Mockito.mock(IFileAdapter.class);
        QueueWorker queueWorker = new QueueWorker(mockFileAdapter);
        queueWorker.addToProcessingQueue("111111111");
        queueWorker.addToProcessingQueue("222222222");
        queueWorker.addToProcessingQueue("333333333");

        queueWorker.finishProcessing();

        verify(mockFileAdapter).writeToFile(any());
        Assert.assertEquals(0, queueWorker.processingQueue.size());
    }
}
