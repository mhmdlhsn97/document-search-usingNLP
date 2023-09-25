/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static projectUtils.FileUtils.getFileName;

/**
 *
 * @author Mohamad
 */
public class matrixUtils1 {

    public static String saveWordMatrixToString(Map<String, Map<String, Integer>> wordMatrix) {
        StringBuilder content = new StringBuilder();
        content.append("Word Matrix:\n");
        content.append("-----------------------------------------------------\n");

        // Write the header row with file names
        content.append(String.format("%-20s", "Word"));
        for (String inputFile : wordMatrix.values().iterator().next().keySet()) {
            content.append(String.format("%-12s", getFileName(inputFile)));
        }
        content.append("\n");

        content.append("-----------------------------------------------------\n");

        // Write the content of the word matrix
        for (Map.Entry<String, Map<String, Integer>> entry : wordMatrix.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> fileOccurrences = entry.getValue();
            content.append(String.format("%-20s", word));
            for (String inputFile : wordMatrix.values().iterator().next().keySet()) {
                int occurrences = fileOccurrences.getOrDefault(inputFile, 0);
                content.append(String.format("%-12d", occurrences));
            }
            content.append("\n");
        }

        content.append("-----------------------------------------------------\n");
        return content.toString();
    }

    // Function to parse the string and create the word matrix
//    public static Map<String, Map<String, Integer>> parseStringToWordMatrix(String savedContent) {
//        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();
//
//        String[] lines = savedContent.split("\n");
//        int numRows = lines.length;
//
//        // Skip the header rows
//        int startRow = 2;
//
//        // Read the header row to get file names
//        String[] header = lines[startRow].split("\\s+");
//        int numCols = header.length - 1; // Exclude the "Word" column
//
//        // Process the content rows
//        for (int i = startRow + 1; i < numRows - 1; i++) {
//            String[] row = lines[i].split("\\s+");
//            String word = row[0];
//            Map<String, Integer> fileOccurrences = new HashMap<>();
//
//            for (int j = 1; j <= numCols; j++) {
//                String inputFile = header[j];
//                int occurrences = Integer.parseInt(row[j]);
//                fileOccurrences.put(inputFile, occurrences);
//            }
//
//            wordMatrix.put(word, fileOccurrences);
//        }
//
//        return wordMatrix;
//    }

     public static Map<String, Map<String, Integer>> parseStringToWordMatrix(String savedContent) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        String[] lines = savedContent.split("\n");
        int numRows = lines.length;

        // Skip the header rows
        int startRow = 2;

        // Read the header row to get file names
        String[] header = lines[startRow].split("\\s+");
        int numCols = header.length - 1; // Exclude the "Word" column

        // Process the content rows
        for (int i = startRow + 1; i < numRows - 1; i++) {
            String[] row = lines[i].split("\\s+");
            if (row.length > 0) {
                String word = row[0];
                Map<String, Integer> fileOccurrences = new HashMap<>();

                for (int j = 1; j <= numCols && j < row.length; j++) {
                    String inputFile = header[j];
                    int occurrences = Integer.parseInt(row[j]);
                    fileOccurrences.put(inputFile, occurrences);
                }

                wordMatrix.put(word, fileOccurrences);
            }
        }

        return wordMatrix;
    }
    
    
    
    // Function to save the word matrix to a text file
    public static void saveWordMatrixToFile(Map<String, Map<String, Integer>> wordMatrix, String outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Word Matrix:\n");
            writer.write("-----------------------------------------------------\n");

            // Write the header row with file names
            writer.write(String.format("%-20s", "Word"));
            for (String inputFile : wordMatrix.values().iterator().next().keySet()) {
                writer.write(String.format("%-12s", getFileName(inputFile)));
            }
            writer.write("\n");

            writer.write("-----------------------------------------------------\n");

            // Write the content of the word matrix
            for (Map.Entry<String, Map<String, Integer>> entry : wordMatrix.entrySet()) {
                String word = entry.getKey();
                Map<String, Integer> fileOccurrences = entry.getValue();
                writer.write(String.format("%-20s", word));
                for (String inputFile : wordMatrix.values().iterator().next().keySet()) {
                    int occurrences = fileOccurrences.getOrDefault(inputFile, 0);
                    writer.write(String.format("%-12d", occurrences));
                }
                writer.write("\n");
            }

            writer.write("-----------------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
