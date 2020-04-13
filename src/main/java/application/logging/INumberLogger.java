package application.logging;

public interface INumberLogger {
    public void start();

    public void incrementCurrentDupes();

    public void incrementUnique();
}
