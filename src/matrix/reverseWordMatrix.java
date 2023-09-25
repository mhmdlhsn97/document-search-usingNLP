/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package matrix;

import java.util.HashMap;
import java.util.Map;
import static projectUtils.FileUtils.readFileAsString;
import projectUtils.matrixUtils;
import static projectUtils.matrixUtils.computeDocFreq;
import static projectUtils.matrixUtils.parseStringToWordMatrix;
//import static projectUtils.matrixUtils.parseStringToWordMatrix;
import static projectUtils.matrixUtils.saveWordMatrixToString;
import static projectUtils.matrixUtils.serializeWordMatrix;

/**
 *
 * @author Mohamad
 */
public class reverseWordMatrix {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String, Map<String, Integer>> wordMatrix = new HashMap<>();

        String x = readFileAsString("C:\\Users\\Mohamad\\Documents\\NetBeansProjects\\stopList\\src\\matrix\\wordMatrix2.txt");

        wordMatrix = matrixUtils.deserializeWordMatrix(x);
        wordMatrix = computeDocFreq(wordMatrix);
        String mm1 = saveWordMatrixToString(wordMatrix);
        System.out.print(mm1);
        if (mm1.compareTo(x)==0) {
            System.out.println("good ");
        } else {
            System.out.println("bad");
        }
    }

}
