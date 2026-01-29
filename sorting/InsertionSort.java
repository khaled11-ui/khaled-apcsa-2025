package sorting;

public class InsertionSort implements Sorter {
    public void sort(int[] input) {
        int steps = 0;
        int n = input.length;
        for (int i = 1; i < n; ++i) {
            int key = input[i];
            int j = i - 1;
            while (j >= 0 && input[j] > key) {
                input[j + 1] = input[j];
                j = j - 1;
                steps++; 
            }
            input[j + 1] = key;
            steps++; 
        }
        System.out.println("Insertion Sort steps: " + steps);
    }
}