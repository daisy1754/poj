import java.awt.*;
import java.util.*;

/**
 * 3669
 * Improved (from the performance perspective) of {@link MeteorShower}.
 */
public class MeteorShower2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver = new Solver(scanner);
        int solution = solver.solve();
        System.out.println(solution);
    }

    static private class Solver {
        private static final int X_LIMIT = 302;
        private static final int Y_LIMIT = 302;
        private static final int DEFAULT_VALUE = -1;

        private int numberOfMeteors;
        private int[][] stage = new int[X_LIMIT][Y_LIMIT];

        public Solver(Scanner scanner) {
            for (int i = 0; i < X_LIMIT; i++) {
                Arrays.fill(stage[i], DEFAULT_VALUE);
            }
            numberOfMeteors = scanner.nextInt();
            for (int i = 0; i < numberOfMeteors; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int t = scanner.nextInt();
                update(stage, x, y, t);
                int[] deltaXs = new int[] {1, -1, 0, 0};
                int[] deltaYs = new int[] {0, 0, 1, -1};
                for (int j= 0; j < 4; j++) {
                    update(stage, x + deltaXs[j], y + deltaYs[j], t);
                }
            }
        }

        public int solve() {
            Queue<Point> points = new LinkedList<Point>();
            int turn = 0;
            int pointsInScore = 1;
            int pointsInNextScore = 0;
            points.add(new Point(0, 0));
            while (!points.isEmpty()) {
                Point p = points.poll();
                pointsInScore--;
                if (stage[p.x][p.y] < 0) {
                    return turn;
                } else if (stage[p.x][p.y] <= turn) {
                    // Do nothing (dead).
                } else {
                    int[] deltaXs = new int[] {1, -1, 0, 0};
                    int[] deltaYs = new int[] {0, 0, 1, -1};
                    for (int i= 0; i < 4; i++) {
                        if (existable(p.x + deltaXs[i], p.y + deltaYs[i], turn)) {
                            pointsInNextScore++;
                            points.add(new Point(p.x + deltaXs[i], p.y + deltaYs[i]));
                        }
                    }
                }
                // Prepend backward-search
                stage[p.x][p.y] = 0;
                if (pointsInScore == 0) {
                    turn++;
                    pointsInScore = pointsInNextScore;
                    pointsInNextScore = 0;
                }
            }
            return -1;
        }

        private void update(int[][] arr, int x, int y, int t) {
            if (x < 0 || y < 0)
                return;
            if (arr[x][y] == DEFAULT_VALUE || arr[x][y] > t) {
                arr[x][y] = t;
            }
        }

        private boolean existable(int x, int y, int turn) {
            if (x < 0 || y < 0) {
                return false;
            }
            return stage[x][y] == DEFAULT_VALUE || stage[x][y] > turn;
        }
    }
}
