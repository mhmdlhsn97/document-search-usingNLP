/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queryUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mohamad
 */
public class tfidfSim {


    public static Map<String, Map<String, Double>> 
        calculateTFIDF
        (Map<String,
                Map<String, Integer>> termFrequencies) {
        Map<String, Map<String, Double>> tfidfData = new HashMap<>();

        // Step 1: Calculate the document frequency (DF) for each term
        Map<String, Integer> documentFrequency = new HashMap<>();
        int totalDocuments = termFrequencies.size();

        for (Map<String, Integer> wordMap : termFrequencies.values()) {
            for (String word : wordMap.keySet()) {
                documentFrequency.put(word, documentFrequency.getOrDefault(word, 0) + 1);
            }
        }

        // Step 2: Calculate TF-IDF for each term in each document
        for (Map.Entry<String, Map<String, Integer>> documentEntry : termFrequencies.entrySet()) {
            String document = documentEntry.getKey();
            Map<String, Integer> wordMap = documentEntry.getValue();
            Map<String, Double> tfidfMap = new HashMap<>();

            for (Map.Entry<String, Integer> wordEntry : wordMap.entrySet()) {
                String word = wordEntry.getKey();
                int termFrequency = wordEntry.getValue();
                int df = documentFrequency.getOrDefault(word, 0);

                // Calculate TF-IDF value for the term in the document
                double tfidf = (double) termFrequency / wordMap.size() * Math.log((double) totalDocuments / (df + 1));
                tfidfMap.put(word, tfidf);
            }

            tfidfData.put(document, tfidfMap);
        }

        return tfidfData;
    }
}
