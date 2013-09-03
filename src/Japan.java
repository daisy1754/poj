import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 3067
 *
 * Note: you need to use long to store output, it took me a while to notice that.
 **/
public class Japan {
    public static void main(String[] args) {
        System.setOut(new PrintStream(new BufferedOutputStream(System.out)));
        Scanner scanner = new Scanner(System.in);
        int numOfTestCases = Integer.parseInt(scanner.next());
        for (int i = 0; i < numOfTestCases; i++) {
            Solver solver = new Solver(scanner);
            System.out.println("Test case " + (i + 1) + ": " + solver.resultAsString());
        }
        System.out.flush();
    }

    private static class Solver {
        private int numOfWestCities;
        private List<List<Integer>> highways;

        private Solver(Scanner scanner) {
            int numOfEastCities = Integer.parseInt(scanner.next());
            highways = new ArrayList<List<Integer>>(numOfEastCities);
            for (int i = 0; i < numOfEastCities; i++) {
                highways.add(new ArrayList<Integer>());
            }
            numOfWestCities = Integer.parseInt(scanner.next());
            long numOfHighways = Long.parseLong(scanner.next());
            for (long i = 0; i < numOfHighways; i++) {
                highways.get(Integer.parseInt(scanner.next()) - 1)
                        .add(Integer.parseInt(scanner.next()) - 1);
            }
        }

        public String resultAsString() {
            long answer = 0;
            int[] numOfCrossingSum = new int[numOfWestCities];
            int[] numOfCrossings = new int[numOfWestCities];
            for (int cityId = 0; cityId < highways.size(); cityId++) {
                for (Integer to: highways.get(cityId)) {
                    numOfCrossings[to]++;
                    if (to < numOfCrossingSum.length - 1) {
                        answer += numOfCrossingSum[to];
                    }
                }
                for (int i = numOfCrossings.length - 1, currentSum = 0; i > 0; i--) {
                    currentSum += numOfCrossings[i];
                    numOfCrossingSum[i - 1] += currentSum;
                }
                Arrays.fill(numOfCrossings, 0);
            }
            return Long.toString(answer);
        }
    }
}
