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
public class cosineSim {

    public static Map<String, Double> calculateCosineSimilarity(
            Map<String, Double> queryVector,
            Map<String, Map<String, Double>> documentVectors) {

        Map<String, Double> cosineSimilarities = new HashMap<>();

        // Calculate the magnitude of the query vector
        double queryMagnitude = calculateMagnitude(queryVector);

        // Calculate the cosine similarity between the query and each document
        for (Map.Entry<String, Map<String, Double>> entry : documentVectors.entrySet()) {
            String documentName = entry.getKey();
            Map<String, Double> documentVector = entry.getValue();

            double dotProduct = calculateDotProduct(queryVector, documentVector);
            double documentMagnitude = calculateMagnitude(documentVector);

            // Avoid division by zero
            if (queryMagnitude == 0 || documentMagnitude == 0) {
                cosineSimilarities.put(documentName, 0.0);
            } else {
                double cosineSimilarity = dotProduct / (queryMagnitude * documentMagnitude);
                cosineSimilarities.put(documentName, cosineSimilarity);
            }
        }

        return cosineSimilarities;
    }

    private static double calculateMagnitude(Map<String, Double> vector) {
        double sumOfSquares = 0.0;
        for (double value : vector.values()) {
            sumOfSquares += value * value;
        }
        return Math.sqrt(sumOfSquares);
    }

    private static double calculateDotProduct(
            Map<String, Double> vector1, Map<String, Double> vector2) {
        double dotProduct = 0.0;
        for (String term : vector1.keySet()) {
            if (vector2.containsKey(term)) {
                dotProduct += vector1.get(term) * vector2.get(term);
            }
        }
        return dotProduct;
    }

}
