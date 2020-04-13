package application.logging;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberLogger extends TimerTask implements INumberLogger {
    private static AtomicInteger totalUnique = new AtomicInteger();
    private static AtomicInteger duplicateValues = new AtomicInteger();
    private static AtomicInteger uniqueValues = new AtomicInteger();

    public void start() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0, 10000);
    }

    public void run() {
        System.out.println(String.format("%s - Received: %d new unique numbers, %d new duplicates, Unique total: %d",
                LocalDateTime.now().toString(), this.uniqueValues.get(), this.duplicateValues.get(),  this.totalUnique.get()));
        this.uniqueValues.set(0);
        this.duplicateValues.set(0);
    }

    public void incrementCurrentDupes() {
        this.duplicateValues.getAndIncrement();
    }

    public void incrementUnique() {
        this.uniqueValues.getAndIncrement();
        this.totalUnique.getAndIncrement();
    }
}
