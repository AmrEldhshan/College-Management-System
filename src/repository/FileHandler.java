package repository;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class FileHandler {
    private String dirPath = "data/";

    private String getFilePath(String fileName) {
        return dirPath + fileName;
    }

    // Method to initialize the file (create file if it doesn't exist)
    public void initializeFile(String fileName) {
        try {
            File file = new File(getFilePath(fileName));
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Create the directory if it doesn't exist
                file.createNewFile(); // Create new file if the file does not exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read data from the file
    public String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // Method to write content to a file (overwrite the existing content)
    public void writeFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(fileName)))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to append content to a file (append to the existing content)
    public void appendToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(fileName), true))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read lines from a file
    public List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}