import java.util.Arrays;
import java.util.Scanner;

/**
 * 3006
 **/
public class DirichletsTheorem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int a = scanner.nextInt(),
                d = scanner.nextInt(),
                n = scanner.nextInt();
            if (a == 0 && d == 0 && n == 0) {
                return;
            }
            int numOfPrime = 0;
            int num = a;
            while (true) {
                if (isPrime(num)) {
                    numOfPrime++;
                }
                if (numOfPrime == n) {
                    System.out.println(num);
                    break;
                }
                num += d;
            }
        }
    }

    static boolean isPrime(int num) {
        if (num == 1) {
            return false;
        } else if (num == 2 || num == 3) {
            return true;
        }

        boolean[] isChecked = new boolean[(int) Math.ceil(Math.sqrt(num)) + 2];
        Arrays.fill(isChecked, false);
        int smalledUnchecked = 2;
        while (smalledUnchecked < isChecked.length) {
            if (num % smalledUnchecked == 0) {
                return false;
            }
            for (int i = 1; i <= (isChecked.length - 1) / smalledUnchecked; i++) {
                isChecked[i * smalledUnchecked] = true;
            }
            while (isChecked[smalledUnchecked]) {
                smalledUnchecked++;
                if (smalledUnchecked >= isChecked.length) {
                    return true;
                }
            }
        }
        return true;
    }
}
