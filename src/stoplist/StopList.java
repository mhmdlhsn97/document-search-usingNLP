/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package stoplist;

import static projectUtils.FileUtils.writeTextToFile;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mohamad
 */
public class StopList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String[] inputFile = {"C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\input.txt",
//        "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\input2.txt",
//        "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\input3.txt"};

 String[] inputFile = {"C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\iput4.txt",
        "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\iput4_1.txt",
        "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\iput4_2.txt"};
 
 
        String[] outputFile = {"C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\output1.txt",
            "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\output2.txt",
            "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\output3.txt"};

        String stoplistFile = "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\stoplist.txt";
        // a try catch for general error handeling
        try {
            //create a set of string to add stoplist word to ir
            Set<String> stoplist = loadStoplist(stoplistFile);
            //use function created to remnove words
            for (int i=0;i<inputFile.length;i++ ) {
                String filteredText = removeStoplistWords(inputFile[i], stoplist);
                //write remaining words
                writeTextToFile(outputFile[i], filteredText);
                System.out.println("Stoplist removal completed successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    // function that read the stoplist file and convert it to a set of strings
    public static Set<String> loadStoplist(String stoplistFile) throws IOException {
        //use a hash since it's faster
        Set<String> stoplist = new HashSet<>();
        //read file and use try to close resources after use or if an error happened throw exception
        try (BufferedReader reader = new BufferedReader(new FileReader(stoplistFile))) {
            String line;
            //stops when line is null
            while ((line = reader.readLine()) != null) {
                stoplist.add(line.trim().toLowerCase());
            }
        }
        return stoplist;
    }

    private static String removeStoplistWords(String inputFile, Set<String> stoplist) throws IOException {
        //using stringbuilder for ease of use
        StringBuilder filteredTextBuilder = new StringBuilder();
        //read file and use try to close resources after use or if an error happened throw exception
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // split line into an array of words whenever there is a space or more
                String[] words = line.split("\\s+");
                //loop threw words
                for (String word : words) {
                    // make word lowercase as toldin lecture
                    String lowercaseWord = word.toLowerCase();
                    //if the word isn't present in the stop list we append it to the text builder
                    if (!stoplist.contains(lowercaseWord)) {
                        filteredTextBuilder.append(word).append(" ");
                    }
                }
                filteredTextBuilder.append("\n");
            }
        }
        //retrun a string from the text build
        return filteredTextBuilder.toString();
    }

}
