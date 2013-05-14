import java.util.Scanner;

/**
 * 1980
 **/
public class UnitFractionPartition {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int p = scanner.nextInt(),
                q = scanner.nextInt(),
                a = scanner.nextInt(),
                n = scanner.nextInt();
            if (p == 0 && q == 0 && a == 0 && n == 0) {
                return;
            }
            PartitionCalculator calculator = new PartitionCalculator(p, q, a, n);

            System.out.println(calculator.solve());
        }
    }

    private static class PartitionCalculator {
        private int p, q, a, n;
        private int numOfParticion = 0;

        public PartitionCalculator(int p, int q, int a, int n) {
            this.p = p; this.q = q; this.a = a; this.n = n;
        }

        private int solve() {
            solve(1, 0, 1, 1, 0);
            return numOfParticion;
        }

        private void solve(int currentDenominator, int sumNumerator,
                          int sumDenominator, int product, int numOfUnits) {

            if (product > a || numOfUnits > n) {
                return;
            }

            if (sumNumerator * q == sumDenominator * p) {
                numOfParticion++;
                return;
            }

            if (numOfUnits == n) {
                return;
            }
            for (int denominator = currentDenominator;
                 denominator * product <= a;
                 denominator++) {
                int newSumNumerator = denominator * sumNumerator + sumDenominator;
                int newDenominator = denominator * sumDenominator;
                solve(denominator, newSumNumerator, newDenominator, product * denominator, numOfUnits + 1);
            }
        }

    }
}
