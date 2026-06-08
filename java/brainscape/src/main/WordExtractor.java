package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;


public class WordExtractor {

    private String directoryPath = "./files";

    // Regex pattern to check if a string is strictly a number
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

    public void extract() {

        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        // Change to your folder path
        File folder = new File(this.directoryPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (listOfFiles == null) {
            System.out.println("Invalid directory path.");
            return;
        }

        // Map to track: Word -> List of Filenames
        Map<String, List<String>> wordMap = new HashMap<>();

        for (File file : listOfFiles) {
            extractWordsFromFile(file, wordMap);
        }

        displayDuplicates(wordMap);
    }

    private void extractWordsFromFile(File file, Map<String, List<String>> wordMap) {
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                // If the current line is a number
                if (NUMBER_PATTERN.matcher(line).matches()) {
                    // The very next line that isn't empty is our "Word Definition"
                    while (sc.hasNextLine()) {
                        String nextLine = sc.nextLine().trim();
                        if (!nextLine.isEmpty()) {
                            String word = nextLine;
                            wordMap.computeIfAbsent(word, k -> new ArrayList<>()).add(file.getName());
                            break; // Move back to looking for the next number
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not read file: " + file.getName());
        }
    }

    private void displayDuplicates(Map<String, List<String>> wordMap) {
        System.out.println("--- Duplicate Word Analysis ---");
        boolean found = false;

        for (var entry : wordMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                found = true;
                System.out.printf("Word: [%s]%n Found %d times in: %s%n%n",
                        entry.getKey(), entry.getValue().size(), entry.getValue());
            }
        }

        if (!found) {
            System.out.println("No duplicate word definitions found.");
        }
    }
}