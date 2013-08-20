import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Tried to solve 1416, passed on test, but wrong answer...
 * Check every possible splits (< 2^6)
 *
 * 1416
 **/
public class ShreddingCompany {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver;
        while ((solver = newSolverWithInput(scanner)) != null) {
            System.out.println(solver.solve());
        }
    }

    public static Solver newSolverWithInput(Scanner scanner) {
        int target = scanner.nextInt();
        int source = scanner.nextInt();
        if (target == 0 && source == 0) {
            return null;
        }
        return new Solver(target, source);
    }

    private static class Solver {
        private static int[] tens = {1, 10, 100, 1000, 10000, 100000, 1000000};
        private final int target;
        private final int source;
        private final int orderOfSource;

        public Solver(int target, int source) {
            this.target = target;
            this.source = source;
            for (int order = 1, n = 1;; order++) {
                n *= 10;
                if (n > source) {
                    orderOfSource = order;
                    break;
                }
            }
        }

        public String solve() {
            int candidate = -1;
            int answer = -1;
            for (int cutFlags = 0; cutFlags < (1 << (orderOfSource - 1)); cutFlags++) {
                int sum = calcSum(cutFlags);
                if (candidate == sum) {
                    return "rejected";
                } else if (candidate < sum && sum <= target) {
                    candidate = sum;
                    answer = cutFlags;
                }
            }

            if (candidate > 0) {
                return candidate + " " + parseFlagsToString(answer);
            } else {
                return "error";
            }
        }

        private List<Integer> cutNum(int cutFlags) {
            List<Integer> list = new ArrayList<Integer>(6);
            int start = 0;
            int end = 1;
            while (end < orderOfSource) {
                if (((cutFlags >> (end - 1)) & 1) == 1) {
                    list.add(cut(start, end));
                    start = end;
                }
                end++;
            }
            list.add(cut(start, orderOfSource));
            return list;
        }

        private int calcSum(int cutFlags) {
            int sum = 0;
            for (int n: cutNum(cutFlags)) {
                sum += n;
            }
            return sum;
        }

        private String parseFlagsToString(int cutFlags) {
            StringBuilder builder = new StringBuilder();
            List<Integer> nums = cutNum(cutFlags);
            for (int i = nums.size() - 1; i >= 0; i--) {
                builder.append(Integer.toString(nums.get(i)));
                if (i != 0) {
                    builder.append(' ');
                }
            }
            return builder.toString();
        }

        private int cut(int start, int end) {
            return (source % tens[end]) / tens[start];
        }
    }
}
