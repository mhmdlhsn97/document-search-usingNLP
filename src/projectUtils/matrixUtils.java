/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import static projectUtils.FileUtils.getFileName;
import static projectUtils.FileUtils.readFileAsString;

/**
 *
 * @author Mohamad
 */
public class matrixUtils {

    // Function to save the word matrix to a string
    public static String saveWordMatrixToString(Map<String, Map<String, Integer>> wordMatrix) {
        StringBuilder content = new StringBuilder();
        content.append("Word Matrix:\n");
        content.append("-----------------------------------------------------\n");

        // Collect the set of all file names
        Set<String> allFileNames = new HashSet<>();
        for (Map<String, Integer> fileOccurrences : wordMatrix.values()) {
            allFileNames.addAll(fileOccurrences.keySet());
        }

        // Write the header row with file names
        content.append(String.format("%-20s", "Word"));
        for (String inputFile : allFileNames) {
            content.append(String.format("%-12s", getFileName(inputFile)));
        }
        content.append("\n");
        content.append("-----------------------------------------------------\n");

        // Write the content of the word matrix
        for (Map.Entry<String, Map<String, Integer>> entry : wordMatrix.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> fileOccurrences = entry.getValue();
            content.append(String.format("%-20s", word));
            for (String inputFile : allFileNames) {
                int occurrences = fileOccurrences.getOrDefault(inputFile, 0);
                content.append(String.format("%-12d", occurrences));
            }
            content.append("\n");
        }

        content.append("-----------------------------------------------------\n");
        return content.toString();
    }

    // Function to parse the string and create the word matrix
    public static Map<String, Map<String, Integer>> parseStringToWordMatrix(String savedContent) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        String[] lines = savedContent.split("\n");

        // Extract header row to get file names
        String[] header = lines[2].trim().split("\\s+"); // Use "\\s+" to split based on spaces

        // Process the content rows starting from the fourth line
        for (int i = 4; i < lines.length - 1; i++) {
            String[] row = lines[i].trim().split("\\s+");
            if (row.length > 0) {
                String word = row[0];
                Map<String, Integer> fileOccurrences = new HashMap<>();

                for (int j = 1; j < row.length; j++) {
                    int occurrences = Integer.parseInt(row[j]);
                    String inputFile = header[j].trim();
                    fileOccurrences.put(inputFile, occurrences);
                }

                wordMatrix.put(word, fileOccurrences);
            }
        }

        return wordMatrix;
    }

    public static Map<String, Map<String, Integer>> rebuildMatrixFromFile(String filePath) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean contentStarted = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("-----------------------------------------------------")) {
                    contentStarted = !contentStarted;
                    continue;
                }

                if (contentStarted) {
                    String[] parts = line.split("\\s+");
                    String word = parts[0];
                    Map<String, Integer> fileOccurrences = new HashMap<>();

                    for (int i = 1; i < parts.length; i += 2) {
                        String fileName = parts[i];
                        int occurrences = Integer.parseInt(parts[i + 1]);
                        fileOccurrences.put(fileName, occurrences);
                    }

                    wordMatrix.put(word, fileOccurrences);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordMatrix;
    }

    public static Map<String, Map<String, Integer>> rebuildMatrixFromString(String matrixString) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        // Split the matrixString by lines
        String[] lines = matrixString.split("\n");
        boolean contentStarted = false;

        for (String line : lines) {
            if (line.startsWith("-----------------------------------------------------")) {
                contentStarted = !contentStarted;
                continue;
            }

            if (contentStarted) {
                String[] parts = line.split("\\s+");
                String word = parts[0];
                Map<String, Integer> fileOccurrences = new HashMap<>();

                for (int i = 1; i < parts.length; i += 2) {
                    String fileName = parts[i];
                    if (i + 1 < parts.length) {
                        int occurrences = Integer.parseInt(parts[i + 1]);
                        fileOccurrences.put(fileName, occurrences);
                    }
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

    public static boolean compareWordMatrices(Map<String, Map<String, Integer>> matrix1, Map<String, Map<String, Integer>> matrix2) {
        if (matrix1.size() != matrix2.size()) {
            return false;
        }

        for (Map.Entry<String, Map<String, Integer>> entry1 : matrix1.entrySet()) {
            String word1 = entry1.getKey();
            Map<String, Integer> fileOccurrences1 = entry1.getValue();

            if (!matrix2.containsKey(word1)) {
                return false;
            }

            Map<String, Integer> fileOccurrences2 = matrix2.get(word1);
            if (!fileOccurrences1.equals(fileOccurrences2)) {
                return false;
            }
        }

        return true;
    }

    public static Map<String, Map<String, Integer>> readMatrixFromFile(String filePath) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();
        String x = readFileAsString(filePath);

        wordMatrix = parseStringToWordMatrix(x);
        return wordMatrix;
    }

//    
//    public static String serializeWordMatrix(Map<String, Map<String, Integer>> wordMatrix) {
//        StringBuilder serialized = new StringBuilder();
//        
//        for (Map.Entry<String, Map<String, Integer>> entry : wordMatrix.entrySet()) {
//            String word = entry.getKey();
//            Map<String, Integer> fileOccurrences = entry.getValue();
//            
//            serialized.append(word).append("=");
//            for (Map.Entry<String, Integer> fileEntry : fileOccurrences.entrySet()) {
//                String fileName = fileEntry.getKey();
//                int occurrences = fileEntry.getValue();
//                serialized.append(fileName).append(":").append(occurrences).append(",");
//            }
//            serialized.deleteCharAt(serialized.length() - 1); // Remove the last comma
//            serialized.append("\n");
//        }
//        
//        return serialized.toString();
//    }
//    
//    public static Map<String, Map<String, Integer>> deserializeWordMatrix(String serialized) {
//        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();
//        
//        String[] lines = serialized.split("\n");
//        for (String line : lines) {
//            String[] parts = line.split("=");
//            String word = parts[0];
//            String[] fileData = parts[1].split(",");
//            Map<String, Integer> fileOccurrences = new HashMap<>();
//            
//            for (String fileEntry : fileData) {
//                String[] fileParts = fileEntry.split(":");
//                String fileName = fileParts[0];
//                int occurrences = Integer.parseInt(fileParts[1]);
//                fileOccurrences.put(fileName, occurrences);
//            }
//            
//            wordMatrix.put(word, fileOccurrences);
//        }
//        
//        return wordMatrix;
//    }
//    
//    public static Map<String, Map<String, Integer>> deserializeWordMatrix2(String serialized) {
//    Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();
//
//    String[] lines = serialized.split("\n");
//    for (String line : lines) {
//        String[] parts = line.split("=");
//        String word = parts[0];
//        String[] fileData = parts[1].split(",");
//        Map<String, Integer> fileOccurrences = new HashMap<>();
//
//        for (String fileEntry : fileData) {
//            String[] fileParts = fileEntry.split("\\|");  // Change the separator
//            String fileName = fileParts[0];
//            int occurrences = Integer.parseInt(fileParts[1]);
//            fileOccurrences.put(fileName, occurrences);
//        }
//
//        wordMatrix.put(word, fileOccurrences);
//    }
//
//    return wordMatrix;
//}
//    
    public static String serializeWordMatrix(Map<String, Map<String, Integer>> wordMatrix) {
        StringBuilder serialized = new StringBuilder();

        Set<String> allFileNames = new HashSet<>();
        for (Map<String, Integer> fileOccurrences : wordMatrix.values()) {
            allFileNames.addAll(fileOccurrences.keySet());
        }

        for (Map.Entry<String, Map<String, Integer>> entry : wordMatrix.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> fileOccurrences = entry.getValue();

            serialized.append(word).append("=");

            for (String f : allFileNames) {
                if (fileOccurrences.containsKey(f)) {
                    int occurrences = fileOccurrences.get(f);
                    serialized.append(f).append("|").append(occurrences).append(",");
                }else{
                    serialized.append(f).append("|").append("0,");
                }
            }

            serialized.deleteCharAt(serialized.length() - 1); // Remove the last comma
            serialized.append("\n");
        }

        return serialized.toString();
    }

    public static Map<String, Map<String, Integer>> deserializeWordMatrix(String serialized) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        String[] lines = serialized.split("\n");
        for (String line : lines) {
            String[] parts = line.split("=");
            String word = parts[0];
            String[] fileData = parts[1].split(",");
            Map<String, Integer> fileOccurrences = new HashMap<>();

            for (String fileEntry : fileData) {
                String[] fileParts = fileEntry.split("\\|");  // Change the separator
                String fileName = fileParts[0];
                int occurrences = Integer.parseInt(fileParts[1]);
                fileOccurrences.put(fileName, occurrences);
            }

            wordMatrix.put(word, fileOccurrences);
        }

        return wordMatrix;
    }

    //take word matrix and process line by line the document frequency of terms
    public static Map<String, Map<String, Integer>> computeDocFreq(Map<String, Map<String, Integer>> input) {

        Map<String, Map<String, Integer>> output = new ConcurrentHashMap<>();
        output = input;
        Iterator<String> word = output.keySet().iterator();
        while (word.hasNext()) {
            String wordKey = word.next();
            Map<String, Integer> docFCalc = output.get(wordKey);
            Iterator<String> file = docFCalc.keySet().iterator();
            int cache = 0;
            while (file.hasNext()) {
                String fileKey = file.next();
                if (docFCalc.get(fileKey) > 0) {
                    cache++;
                }
            }
            docFCalc.put("docf", cache);
            output.replace(wordKey, docFCalc);
        }
        return output;
    }

    public static Map<String, Map<String, Integer>> reverseMap(Map<String, Map<String, Integer>> originalMap) {
        Map<String, Map<String, Integer>> reversedMap = new HashMap<>();

        // Iterate through the original map
        for (Map.Entry<String, Map<String, Integer>> entry : originalMap.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> fileCountMap = entry.getValue();

            // Iterate through the inner map (fileCountMap)
            for (Map.Entry<String, Integer> fileEntry : fileCountMap.entrySet()) {
                String filename = fileEntry.getKey();
                int wordOccurrences = fileEntry.getValue();

                // Add the word and its count to the reversed map
                reversedMap.computeIfAbsent(filename, k -> new HashMap<>())
                        .put(word, wordOccurrences);
            }
        }

        return reversedMap;
    }

    public static Map<String, Map<String, Double>> reverseMapd(Map<String, Map<String, Double>> originalMap) {
        Map<String, Map<String, Double>> reversedMap = new HashMap<>();

        // Iterate through the original map
        for (Map.Entry<String, Map<String, Double>> entry : originalMap.entrySet()) {
            String word = entry.getKey();
            Map<String, Double> fileCountMap = entry.getValue();

            // Iterate through the inner map (fileCountMap)
            for (Map.Entry<String, Double> fileEntry : fileCountMap.entrySet()) {
                String filename = fileEntry.getKey();
                double wordOccurrences = fileEntry.getValue();

                // Add the word and its count to the reversed map
                reversedMap.computeIfAbsent(filename, k -> new HashMap<>())
                        .put(word, wordOccurrences);
            }
        }

        return reversedMap;
    }
}
