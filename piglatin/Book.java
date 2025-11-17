package piglatin;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

class Book {
    private String title;
    private ArrayList<String> text = new ArrayList<String>();

    Book() {
        // Empty book - no code needed here.
    }

    // Helper to debug code
    // Prints out a range of lines from a book
    public void printlines(int start, int length) {
        System.out.println("Lines " + start + " to " + (start + length) + " of book: " + title);
        for (int i = start; i < start + length; i++) {
            if (i < text.size()) {
                System.out.println(i + ": " + text.get(i));
            } else {
                System.out.println(i + ": line not in book.");
            }
        }
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getLine(int lineNumber) {
        return text.get(lineNumber);
    }

    int getLineCount() {
        return text.size();
    }

    void appendLine(String line) {
        text.add(line);
    }

    public void readFromString(String title, String string) {
        // load a book from an input string.
        this.title = title;
        if (string == null || string.isEmpty()) {
            return;
        }

        // Split on newline characters and add each line to the book
        String[] lines = string.split("\r?\n");
        for (String line : lines) {
            text.add(line);
        }
    }

    public void readFromUrl(String title, String url) {
        // load a book from a URL.
        this.title = title;
        // clear previous contents
        text.clear();
        

        java.net.URL u = null;
        java.net.HttpURLConnection conn = null;
        try {
            u = new java.net.URL(url);
            conn = (java.net.HttpURLConnection) u.openConnection();
            // Use a browser-like user agent to avoid some servers rejecting the default Java UA
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; PigLatinTranslator/1.0)");
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);

            int code = conn.getResponseCode();
            if (code >= 200 && code < 300) {
                try (java.io.BufferedReader br = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.add(line);
                    }
                }
            } else {
                System.err.println("Failed to fetch URL: " + url + " -> HTTP " + code);
            }
        } catch (Exception ex) {
            // Print error but do not throw; caller can check getLineCount()
            System.err.println("Error reading URL: " + url + " -> " + ex.getMessage());
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    void writeToFile(String name) {
        // Write the contents of the book to a file (one line per entry).
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(name))) {
            for (String line : text) {
                writer.write(line == null ? "" : line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPage(String translatedPage) {
        // Add a translated page/line to the book
        text.add(translatedPage);
    }

    public String getPage(int i) {
        return (i >= 0 && i < text.size()) ? text.get(i) : null;
    }

    public int size() {
        return text.size();
    }
}
