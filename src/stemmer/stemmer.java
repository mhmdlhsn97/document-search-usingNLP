/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stemmer;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import opennlp.tools.stemmer.PorterStemmer;
import stoplist.StopList;
import static projectUtils.FileUtils.writeTextToFile;

public class stemmer {

    public static void main(String[] args) {
        try {
            String[] inputFile = {"C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\output1.txt",
                "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\output2.txt",
                "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\output3.txt"}; // Replace with the output file from the previous program
            String[] outputFile = {"C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stemmer\\stemmed_output1.txt",
                "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stemmer\\stemmed_output2.txt",
                "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stemmer\\stemmed_output3.txt"}; // Replace with the desired output file path

            for (int i = 0; i < inputFile.length; i++) {
                List<String> words = readWordsFromFile(inputFile[i]);

                String stemmedWords = "";
                PorterStemmer stem = new PorterStemmer();

                for (String word : words) {
                    System.out.print(word + " -> ");
                    //remove punctiation
                    word = word.replaceAll("\\p{Punct}", "");
                    //stem word and add it to array
                    stemmedWords += (stem.stem(word)) + " ";
                    System.out.println(stem.stem(word));
                }
                writeTextToFile(outputFile[i], stemmedWords);
            }
        } catch (IOException e) {
            System.err.println("IO err " + e);
        }

    }

    public static List<String> readWordsFromFile(String filePath) {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into words based on whitespace
                String[] lineWords = line.trim().split("\\s+");

                // Add each word to the list
                for (String word : lineWords) {
                    words.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }
}
