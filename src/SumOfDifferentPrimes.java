import java.util.*;

/**
 * 3132
 **/
public class SumOfDifferentPrimes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            if (n == 0 && k == 0){
                return;
            }
            List<Integer> primesLessThanN = calcPrimesLessThan(n);
            System.out.println(calc(n, k, primesLessThanN));
        }
    }

    // Simple implementation of Eratosthenes's Sieve using Array.
    private static List<Integer> calcPrimesLessThan(int n) {
        boolean[] mayPrime = new boolean[n + 2];
        Arrays.fill(mayPrime, true);
        List<Integer> primes = new ArrayList<Integer>();
        primes.add(2);
        int i = 3;
        double rootOfN = Math.sqrt(n);
        while (i <= rootOfN) {
            primes.add(i);
            for (int j = 1; i * j <= n; j++) {
                mayPrime[i * j] = false;
            }
            while (!mayPrime[i] && i <= rootOfN) {
                // only considering odd number.
                i += 2;
            }
        }
        for (; i <= n; i += 2) {
            if (mayPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    // numOfUniqueSets[n][i][s]: number of unique sets that consists of n
    // elements whose sum is s. Minimum index of n number is i.
    static int[][][] numOfUniqueSets;
    private static int calc(int sum, int k, List<Integer> list) {
        numOfUniqueSets = new int[k + 1][list.size()][sum + 1];

        // calc cases for num of elements are 1
        int index = 0;
        for (int s = 2; s <= sum; s++) {
            while (index < list.size() - 1 && list.get(index) < s) {
                index++;
            }
            numOfUniqueSets[1][index][s] = (list.get(index) == s) ? 1 : 0;
        }

        // n < 14, i < numOfPrimesLessThan(1120), s < 1120, j < 14
        // 14 * 1120 * 14 * 187 = 41M
        // seems doable...
        for (int n = 2; n <= k; n++) {
            for (int i = list.size() - 1; i >= 0; i--) {
                int primeAtI = list.get(i);
                for (int s = n * primeAtI; s <= sum; s++) {
                    for (int j = i + 1; j < list.size(); j++) {
                        numOfUniqueSets[n][i][s] += numOfUniqueSets[n - 1][j][s - primeAtI];
                    }
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < list.size(); i++) {
            ans += numOfUniqueSets[k][i][sum];
        }
        return ans;
    }
}
