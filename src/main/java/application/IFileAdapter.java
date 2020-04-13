package application;

import java.io.IOException;
import java.util.ArrayList;

public interface IFileAdapter {
    void writeToFile(ArrayList batchToWrite) throws IOException;
    void resetFile() throws IOException;
}
