package application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileAdapterTests {

    static final String TEST_FILE = "test.txt";

    @After
    public void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    public void writeToFile_appends_input_to_file_on_newline() throws IOException {

        ArrayList linesToWrite = new ArrayList() {{
            add("hello");
            add("world!");
            add("fizz");
            add("buzz");
        }};


        IFileAdapter fileAdapter = new FileAdapter(TEST_FILE);
        fileAdapter.writeToFile(linesToWrite);

        ArrayList lines = readFile();

        Assert.assertEquals(lines, linesToWrite);
        Assert.assertEquals(lines.size(), linesToWrite.size());
    }

    @Test
    public void log_file_is_reset_if_exists() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE, true))) {
            writer.append("hello world\n");
        }

        IFileAdapter fileAdapter = new FileAdapter(TEST_FILE);
        fileAdapter.resetFile();

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));

        Assert.assertEquals(0, lines.size());
        Assert.assertTrue(Files.exists(Paths.get(TEST_FILE)));
    }


    @Test
    public void log_file_is_created_if_does_not_exists() throws IOException {
        IFileAdapter fileAdapter = new FileAdapter(TEST_FILE);
        fileAdapter.resetFile();

        Assert.assertTrue(Files.exists(Paths.get(TEST_FILE)));
    }

    private ArrayList readFile() throws IOException {
        ArrayList lines = new ArrayList();
        for (String line : Files.readAllLines(Paths.get(TEST_FILE))) {
            lines.add(line);
        }
        return lines;
    }
}
