package sorting;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int size = 100; 
        int[] testInput = randomArray(size);

        
        TestSuite.run(testInput, 1);
    }

    public static int[] randomArray(int length) {
        Random rand = new Random();
        int[] a = new int[length];
        for (int i = 0; i < length; i++) {
            a[i] = rand.nextInt(10000);
        }
        return a;
    }
}