package application.logging;

public interface INumberLogger {
    void start();

    void incrementCurrentDupes();

    void incrementUnique();
}
