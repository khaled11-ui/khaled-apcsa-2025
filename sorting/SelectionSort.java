package sorting;

public class SelectionSort implements Sorter {
    public void sort(int[] input) {
        int steps = 0;
        int n = input.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                steps++; 
                if (input[j] < input[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = input[minIdx];
            input[minIdx] = input[i];
            input[i] = temp;
            steps++; 
        }
        System.out.println("Selection Sort steps: " + steps);
    }
}