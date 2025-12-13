package piglatin;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title = "";
    private final List<String> lines = new ArrayList<>();

    public Book() {}

    public Book(String title) { this.title = title; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public void readFromUrl(String title, String urlStr) {
        setTitle(title);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.trim().length() > 0) {
                    lines.add(inputLine);
                }
            }
        } catch (IOException e) {
            // Ignore and leave lines empty — caller will fall back to local sample
            System.out.println("Failed to load remote URL: " + e.getMessage());
        }
    }

    public void readFromString(String title, String text) {
        setTitle(title);
        lines.clear();
        String[] parts = text.split("\\r?\\n");
        for (String p : parts) {
            lines.add(p);
        }
    }

    public void appendLine(String line) { lines.add(line); }

    public int getLineCount() { return lines.size(); }
    public String getLine(int index) { return (index >= 0 && index < lines.size()) ? lines.get(index) : ""; }

    public void printlines(int start, int end) {
        for (int i = start; i <= end && i < getLineCount(); i++) {
            System.out.println(lines.get(i));
        }
    }

    public void writeToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String line : lines) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + e.getMessage());
        }
    }
}
