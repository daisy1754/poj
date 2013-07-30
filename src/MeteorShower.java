import java.awt.*;
import java.util.*;

/**
 * 3669
 * Time limit exceeded... ;<
 **/
public class MeteorShower {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver = new Solver(scanner);
        int solution = solver.solve();
        System.out.println(solution);
    }

    static private class Solver {
        private static final int X_LIMIT = 301;
        private static final int Y_LIMIT = 301;

        private int numberOfMeteors;
        private Map<Integer, Set<Point>> meteors =
                new HashMap<Integer, Set<Point>>();
        private Set<Point> unsafePoints = new HashSet<Point>();
        private Set<Point> destroyedPoints = new HashSet<Point>();
        // Point where Bessie can exist at the certain time.
        private Set<Point> existablePoints = new HashSet<Point>();
        private Set<Point> reachablePoints = new HashSet<Point>();

        public Solver(Scanner scanner) {
            numberOfMeteors = scanner.nextInt();
            for (int i = 0; i < numberOfMeteors; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int t = scanner.nextInt();

                if (!meteors.containsKey(t)) {
                    meteors.put(t, new HashSet<Point>());
                }
                Point p = new Point(x, y);
                meteors.get(t).add(p);
                unsafePoints.add(p);
                unsafePoints.addAll(nonDestroyedNeighbors(p));
            }
        }

        public int solve() {
            Point origin = new Point(0, 0);
            existablePoints.add(origin);
            reachablePoints.add(origin);
            int time = 0;
            while (!existablePoints.isEmpty()) {
                executeMeteor(time);

                Set<Point> nextExistablePoints = new HashSet<Point>();
                for (Point currentPoint: existablePoints) {
                    if (checkNeighbors(currentPoint, nextExistablePoints)) {
                        return time + 1;
                    }
                }
                existablePoints = nextExistablePoints;
                if (existablePoints.size() == 0) {
                    return -1;
                }
                reachablePoints.addAll(existablePoints);
                time++;
            }
            return -1;
        }

        private Collection<Point> nonDestroyedNeighbors(Point p) {
            Set<Point> nonDestroyedNeighbors = new HashSet<Point>();
            int[] deltaXs = {1, -1, 0, 0};
            int[] deltaYs = {0, 0, 1, -1};
            for (int i = 0; i < 4; i++) {
                Point neighbor = new Point(p.x + deltaXs[i], p.y + deltaYs[i]);
                if (getPointState(neighbor) != State.DESTROYED) {
                    nonDestroyedNeighbors.add(neighbor);
                }
            }
            return nonDestroyedNeighbors;
        }

        private void executeMeteor(int time) {
            Set<Point> meteorsForTime = meteors.get(time);
            if (meteorsForTime == null) {
                return;
            }

            for (Point p: meteorsForTime) {
                existablePoints.remove(p);
                existablePoints.removeAll(nonDestroyedNeighbors(p));

                destroyedPoints.add(p);
                destroyedPoints.addAll(nonDestroyedNeighbors(p));
            }
        }

        private boolean checkNeighbors(Point p, Set<Point> nextExistablePoints) {
            Collection<Point> nonDestroyedNeighbors = nonDestroyedNeighbors(p);
            for (Point neighbor: nonDestroyedNeighbors) {
                State state = getPointState(neighbor);
                switch (state) {
                    case SAFE:
                        return true;
                    case AVAILABLE:
                        if (!reachablePoints.contains(neighbor)) {
                            nextExistablePoints.add(neighbor);
                        }
                        break;
                }
            }
            return false;
        }

        private State getPointState(Point p) {
            if (p.x < 0 || p.y < 0) {
                return State.DESTROYED;
            }
            if (p.x > X_LIMIT || p.y > Y_LIMIT) {
                return State.SAFE;
            }
            if (destroyedPoints.contains(p)) {
                return State.DESTROYED;
            }
            return unsafePoints.contains(p) ? State.AVAILABLE : State.SAFE;
        }

        private enum State {
            SAFE, DESTROYED, AVAILABLE
        }
    }
}
