package matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import projectUtils.FileUtils;
import static projectUtils.FileUtils.getFileName;
import static projectUtils.matrixUtils.reverseMap;
import static projectUtils.matrixUtils.saveWordMatrixToString;
import static projectUtils.matrixUtils.serializeWordMatrix;

public class WordMatrix {

    public static void main(String[] args) {
        // Replace the file paths with the paths to your input text files
        String[] inputFiles = {
            "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stemmer\\stemmed_output1.txt", //"file2.txt"
            "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stemmer\\stemmed_output2.txt",
            "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stemmer\\stemmed_output3.txt"
        // Add more file paths as needed
        };

        // Create the word matrix
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        for (String inputFile : inputFiles) {
            processFile(inputFile, wordMatrix);
        }

        // Print the word matrix as a table
        System.out.println("Word Matrix:");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-20s", "Word");
        for (String inputFile : inputFiles) {
            System.out.printf("%-12s", getFileName(inputFile));
        }
        System.out.println();
        System.out.println("-----------------------------------------------------");

        for (Map.Entry<String, Map<String, Integer>> entry : wordMatrix.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> fileOccurrences = entry.getValue();
            System.out.printf("%-20s", word);
            for (String inputFile : inputFiles) {
                int occurrences = fileOccurrences.getOrDefault(inputFile, 0);
                System.out.printf("%-12d", occurrences);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------");

        String outputFile = "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\matrix\\wordMatrix.txt";
        String out = saveWordMatrixToString(wordMatrix);
        //saveWordMatrixToFile(wordMatrix, outputFile);
        try {
            FileUtils.writeTextToFile(outputFile, out);
        } catch (IOException e) {
            System.out.println("Err Io: " + e);
        }
        String outputFile2 = "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\matrix\\wordMatrix2.txt";
        String out2 = serializeWordMatrix(wordMatrix);
        //saveWordMatrixToFile(wordMatrix, outputFile);
        try {
            FileUtils.writeTextToFile(outputFile2, out2);
        } catch (IOException e) {
            System.out.println("Err Io: " + e);
        }
       
        String outputFile3 = "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\matrix\\wordMatrix3.txt";
        //wordMatrix=reverseMap(wordMatrix);
        String out3 = serializeWordMatrix(wordMatrix);
        //saveWordMatrixToFile(wordMatrix, outputFile);
        try {
            FileUtils.writeTextToFile(outputFile3, out3);
        } catch (IOException e) {
            System.out.println("Err Io: " + e);
        }
        
        
    }
    
    
    
    private static void processFile(String inputFile, Map<String, Map<String, Integer>> wordMatrix) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into words
                String[] words = line.split("\\s+");

                // Update the word matrix for each word in the line
                for (String word : words) {
                    word = word.toLowerCase(); // Convert to lowercase to ignore case sensitivity

                    // Skip empty words (e.g., caused by multiple spaces)
                    if (!word.isEmpty()) {
                        // Get the inner map for the word
                        Map<String, Integer> fileOccurrences = wordMatrix.getOrDefault(word, new HashMap<>());

                        // Increment the count of occurrences for the current file
                        int occurrences = fileOccurrences.getOrDefault(inputFile, 0);
                        fileOccurrences.put(inputFile, occurrences + 1);

                        // Update the inner map for the word in the word matrix
                        wordMatrix.put(word, fileOccurrences);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
