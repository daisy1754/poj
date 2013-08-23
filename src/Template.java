import java.util.Scanner;

/**
 *
 **/
public class Template {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver;
        while ((solver = newSolverWithScannedInput(scanner)) != null) {
            System.out.println(solver.resultAsString());
        }
    }

    private static Solver newSolverWithScannedInput(Scanner scanner) {
        return null;
    }

    private static class Solver {

        public String resultAsString() {
            return "";
        }
    }
}
