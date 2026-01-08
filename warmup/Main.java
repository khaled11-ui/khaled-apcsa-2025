package warmup;

public class Main {

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= 10; i++) {
            if( i % 3 == 0 || i % 5 == 0 ) {
                sum += i;
            }

    }    System.out.println("Sum of multiples of 3 or 5 between 1 and 1000: " + sum);
    }
}
