import java.util.Scanner;

/**
 * 1666
 **/
public class CandySharingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver;
        while ((solver = newSolverWithScannedInput(scanner)) != null) {
            System.out.println(solver.resultAsString());
        }
    }

    private static Solver newSolverWithScannedInput(Scanner scanner) {
        int numberOfStudents = scanner.nextInt();
        if (numberOfStudents == 0) {
            return null;
        }

        int[] numberOfCandies = new int[numberOfStudents];
        for (int i = 0; i < numberOfStudents; i++) {
            numberOfCandies[i] = scanner.nextInt();
        }
        return new Solver(numberOfStudents, numberOfCandies);
    }

    private static class Solver {
        private final int numberOfStudents;
        private final int[] numberOfCandies;

        private Solver(int numberOfStudents, int[] numberOfCandies) {
            this.numberOfStudents = numberOfStudents;
            this.numberOfCandies = numberOfCandies;
        }

        public String resultAsString() {
            int numberOfRound = 0;
            while (!isFinished()) {
                doRound();
                numberOfRound++;
            }
            return Integer.toString(numberOfRound) + ' ' + Integer.toString(numberOfCandies[0]);
        }

        private boolean isFinished() {
            for (int i = 0; i < numberOfStudents; i++) {
                if (numberOfCandies[0] != numberOfCandies[i]) {
                    return false;
                }
            }
            return true;
        }

        private void doRound() {
            // pass candies
            int[] buf = new int[numberOfStudents];
            for (int i = 0; i < numberOfStudents; i++) {
                buf[i] = numberOfCandies[i] / 2;
            }
            for (int i = 0; i < numberOfStudents; i++) {
                numberOfCandies[i] = numberOfCandies[i] / 2
                        + (i == 0 ? buf[buf.length - 1] :buf[i - 1]);
            }
            for (int i = 0; i < numberOfStudents; i++) {
                if (numberOfCandies[i] % 2 == 1) {
                    numberOfCandies[i]++;
                }
            }
        }
    }
}
