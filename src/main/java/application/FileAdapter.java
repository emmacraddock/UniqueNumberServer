package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileAdapter implements IFileAdapter {
    private String fileName;

    public FileAdapter(String fileName) { this.fileName = fileName; }

    public void writeToFile(ArrayList batchToWrite) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(this.fileName, true))) {
            batchToWrite.forEach((line) -> {
                try {
                    bufferedWriter.append(line + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void resetFile() throws IOException {

        File logFile = new File(this.fileName);
        Files.deleteIfExists(logFile.toPath());
        logFile.createNewFile();
    }
}
