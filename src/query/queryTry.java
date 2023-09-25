package query;

import java.io.IOException;
import java.util.*;
import opennlp.tools.stemmer.PorterStemmer;
import static projectUtils.FileUtils.readFileAsString;
import projectUtils.matrixUtils;
import static projectUtils.matrixUtils.reverseMap;
import static stoplist.StopList.loadStoplist;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Mohamad
 */

public class queryTry {

    private Set<String> stoplist;

    private PorterStemmer stemmer;
    private Map<String, Map<String, Integer>> wordMatrix;

    public queryTry(Map<String, Map<String, Integer>> wordMatrix) throws IOException {

        this.stoplist = loadStoplist("C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\stoplist.txt");
        this.stemmer = new PorterStemmer();
        this.wordMatrix = wordMatrix;
    }

    public static List<Map<String, Double>> calculateTFIDF(List<Map<String, Double>> documents) {
        int totalDocuments = documents.size();
        Map<String, Integer> termDoc = new HashMap<>();
        for (Map<String, Double> document : documents) {
            for (String term : document.keySet()) {
                if (termDoc.containsKey(term)) {
                    termDoc.replace(term, termDoc.get(term) + 1);

                } else {
                    termDoc.putIfAbsent(term, 1);
                }
            }
        }
        // Step 1: Calculate IDF (Inverse Document Frequency)
        Map<String, Double> idfVector = new HashMap<>();
        for (Map<String, Double> document : documents) {
            for (String term : document.keySet()) {
                // System.out.print(term+" "+Math.log10( totalDocuments  / termDoc.get(term)));
                idfVector.put(term, Math.log(totalDocuments / ( termDoc.get(term))) +1);
            }
        }

        // Step 2: Calculate TF-IDF for each term in each document
        List<Map<String, Double>> tfidfVectors = new ArrayList<>();
        for (Map<String, Double> document : documents) {
            Map<String, Double> tfidfVector = new HashMap<>();
            for (Map.Entry<String, Double> termEntry : document.entrySet()) {
                String term = termEntry.getKey();
                double tf =  termEntry.getValue();
                double idf = idfVector.getOrDefault(term, 0.0);
                double tfidf = tf * idf;
                tfidfVector.put(term, tfidf);
                        
            }
            tfidfVectors.add(tfidfVector);
        }

        return tfidfVectors;
    }

    public static List<Map<String, Double>> createDocumentVectorsI(
            Map<String, Map<String, Integer>> tfidfMatrix) {

        List<Map<String, Double>> documentVectors = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> tfidfVector : tfidfMatrix.entrySet()) {
            Map<String, Double> temp = new HashMap<>();
            for (Map.Entry<String, Integer> vals : tfidfVector.getValue().entrySet()) {

                temp.put(vals.getKey(), (double) vals.getValue());

            }
            documentVectors.add(temp);
        }

        return documentVectors;
    }

    public static void printDocumentVectors(List<Map<String, Double>> documentVectors) {
        int docIndex = 0;
        for (Map<String, Double> documentVector : documentVectors) {
            System.out.println("Document Vector " + docIndex + ":");
            for (Map.Entry<String, Double> entry : documentVector.entrySet()) {
                String word = entry.getKey();
                double tfidfValue = entry.getValue();
                System.out.println(word + ": " + tfidfValue);
            }
            docIndex++;
        }
    }

    public static void main(String[] args) throws Exception {
        String wordMatrixFile = "C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\matrix\\wordMatrix2.txt";
        String x = readFileAsString(wordMatrixFile);
        Map<String, Map<String, Integer>> wordMatrix = matrixUtils.deserializeWordMatrix(x);
        Map<String, Map<String, Integer>> fileWordMap = reverseMap(wordMatrix);

        List<Map<String, Double>> f = createDocumentVectorsI(fileWordMap);
        List<Map<String, Double>> xd = calculateTFIDF(f);

        printDocumentVectors(f);

        System.out.print("Please input the query: ");
        String input = new Scanner(System.in).nextLine();
        // Step 1: Tokenize the query into words
        String[] words = input.split("\\s+");
        Set<String> stoplist = loadStoplist("C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\stoplist\\stoplist.txt");
        // Step 2: Remove stop words and stem the remaining words
        List<String> processedWords = new ArrayList<>();
        PorterStemmer stemmer = new PorterStemmer();
        for (String word : words) {
            if (!stoplist.contains(word.toLowerCase())) {
                String stemmedWord = stemmer.stem(word.toLowerCase());
                processedWords.add(stemmedWord);
            }
        }

        System.out.println(processedWords.toString());
        Map<String, Double> ff = new HashMap<>();

        for (String word : processedWords) {
            ff.putIfAbsent(word, 1.0);
            if (ff.containsKey(word)) {
                ff.replace(word, ff.get(word) + 1);
            }
        }
        List<Map<String, Double>> falase = new ArrayList<>();
        falase.add(ff);
        falase = calculateTFIDF(falase);
        ff = falase.get(0);
//        f.add(ff);
//
        printDocumentVectors(falase);
       // printDocumentVectors(xd);
        //ff = calculateCosineSimilarity(ff, xd);

        List<Double> xf = calculateCosineSimilarities(ff, xd);
        for (int i = 0; i < xf.size(); i++) {
            System.out.println("Cosine Similarity with Document " + (i + 1) + ": " + xf.get(i));
        }

    }

    public static double cosineSimilarity(Map<String, Double> queryVector, Map<String, Double> documentVector) {
        double dotProduct = 0.0;
        double queryMagnitude = 0.0;
        double documentMagnitude = 0.0;

        for (Map.Entry<String, Double> entry : queryVector.entrySet()) {
            String term = entry.getKey();
            double queryTermWeight = entry.getValue();
            double documentTermWeight = documentVector.getOrDefault(term, 0.0);

            dotProduct += queryTermWeight * documentTermWeight;
            queryMagnitude += queryTermWeight * queryTermWeight;
            documentMagnitude += documentTermWeight * documentTermWeight;
        }

        if (queryMagnitude == 0.0 || documentMagnitude == 0.0) {
            return 0.0; // Handle zero magnitude case
        }

        return dotProduct / (Math.sqrt(queryMagnitude) * Math.sqrt(documentMagnitude));
    }

    public static List<Double> calculateCosineSimilarities(Map<String, Double> queryVector, List<Map<String, Double>> documentVectors) {
        List<Double> similarities = new ArrayList<>();
        Map<String, Double> _queryVector = queryVector;
        for (Map<String, Double> documentVector : documentVectors) {
            for (Map.Entry<String, Double> wordVector : documentVector.entrySet()) {
                if (!_queryVector.containsKey(wordVector.getKey())) {
                    _queryVector.put(wordVector.getKey(), 0.0);
                }
            }
        }

        for (Map<String, Double> documentVector : documentVectors) {
            double similarity = cosineSimilarity(_queryVector, documentVector);
            similarities.add(similarity);
        }

        return similarities;
    }
}
