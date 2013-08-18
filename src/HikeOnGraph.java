import java.util.*;

/**
 * Tried to solve 2415, passed on test, but wrong answer...
 * Width-first search that do any possible move.
 *
 * 2415
 **/
public class HikeOnGraph {
    private static int[][] opponentsIndices = new int[][] {{1, 2}, {0, 2}, {0, 1}};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver;
        while (((solver = newInstanceWithInputs(scanner)) != null)) {
            int answer = solver.solve();
            if (answer > 0) {
                System.out.println(answer);
            } else {
                System.out.println("impossible");
            }
        }
    }
    private static Solver newInstanceWithInputs(Scanner scanner) {
        Solver instance = new Solver();
        int n = scanner.nextInt();
        if (n ==0) {
            return null;
        }
        for (int i = 0; i < 3; i++) {
            // normalize index: starts from zero
            instance.p[i] = scanner.nextInt() - 1;
        }
        instance.colors = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                instance.colors[i][j] = scanner.next().charAt(0);
            }
        }
        return instance;
    }

    private static class Solver {
        private int[] p = new int[3];
        private char[][] colors;
        private PriorityQueue<WeightedState> nextStates = new PriorityQueue<WeightedState>(
                100,
                new Comparator<WeightedState>() {
                    @Override
                    public int compare(WeightedState weightedState, WeightedState weightedState2) {
                        return weightedState.weight - weightedState2.weight;
                    }
                });
        private Set<Integer> knownStates = new HashSet<Integer>();

        private int solve() {
            nextStates.add(new WeightedState(getPointsState(), 0));
            while (!nextStates.isEmpty()) {
                WeightedState state = nextStates.poll();
                restorePoints(state.value);
                knownStates.add(state.value);
                for (int moveTarget = 0; moveTarget < 3; moveTarget++) {
                    int ans = checkForMove(moveTarget, state.weight);
                    if (ans > 0) {
                        return ans;
                    }
                }
            }
            return -1;
        }

        private int checkForMove(int moveTarget, int currentWeight) {
            int currentPos = p[moveTarget];
            int opponentPos0 = p[opponentsIndices[moveTarget][0]];
            int opponentPos1 = p[opponentsIndices[moveTarget][1]];
            char availableColor = colors[opponentPos0][opponentPos1];
            char[] ways = colors[currentPos];
            for (int dest = 0; dest < ways.length; dest++) {
                if (availableColor != ways[dest]) {
                    continue;
                }
                p[moveTarget] = dest;
                if (p[0] == p[1] && p[1] == p[2]) {
                    return currentWeight + 1;
                }
                int currentPointsState = getPointsState();
                if (!knownStates.contains(currentPointsState)) {
                    nextStates.add(new WeightedState(currentPointsState, currentWeight + 1));
                }
            }
            // restore original value
            p[moveTarget] = currentPos;
            return -1;
        }

        private int getPointsState() {
            return p[0] + p[1] * 100 + p[2] * 10000;
        }

        private void restorePoints(int state) {
            p[0] = state % 100;
            p[1] = state / 100 % 100;
            p[2] = state / 10000;
        }
    }

    private static class WeightedState {
        private final int value;
        private final int weight;

        private WeightedState(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}
