import java.util.*;

/**
 * 2688
 **/
public class CleaningRobot {
    static int width, height;
    static int numOfDirtyTiles;
    static boolean[][] obstacle;
    static int[] pointsX, pointsY;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            width = scanner.nextInt();
            height = scanner.nextInt();
            if (width == 0 && height == 0) {
                return;
            }
            obstacle = new boolean[height][width];
            pointsX = new int[11];
            pointsY = new int[11];
            numOfDirtyTiles = 0;
            scanner.nextLine();
            for (int i = 0; i < height; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < line.length(); j++){
                    obstacle[i][j] = false;
                    switch (line.charAt(j)) {
                        case 'x':
                            obstacle[i][j] = true;
                            break;
                        case 'o':
                            pointsX[0] = j;
                            pointsY[0] = i;
                            break;
                        case '*':
                            pointsX[numOfDirtyTiles + 1] = j;
                            pointsY[numOfDirtyTiles + 1] = i;
                            numOfDirtyTiles++;
                            break;
                    }
                }
            }
            int [][] distances = new int[numOfDirtyTiles + 1][numOfDirtyTiles + 1];
            if (!calcDistances(distances)) {
                System.out.println(-1);
            } else {
//                for (int i = 0; i < numOfDirtyTiles + 1; i++) {
//                    System.out.println(Arrays.toString(distances[i]));
//                }
                System.out.println(calcShortestPathDistance(distances));
            }
        }
    }

    /**
     * @return if all distances can be calculated, i.e., all points are reachable.
     */
    static boolean calcDistances(int[][] distances) {
        for (int i = 0; i <= numOfDirtyTiles; i++) {
            for (int j = 0; j <= numOfDirtyTiles; j++) {
                distances[i][j] = calcDistance(i, j);
                if (distances[i][j] < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static int calcDistance(int index1, int index2) {
        int startX = pointsX[index1];
        int startY = pointsY[index1];
        int endX = pointsX[index2];
        int endY = pointsY[index2];

        // Dijkstra
        int[][] distances = new int[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }
        PriorityQueue<WeightedPoint> undecidedPoints =  new PriorityQueue<WeightedPoint>();
        Set<Point> decidedPoint = new HashSet<Point>();
        undecidedPoints.add(new WeightedPoint(startX, startY, 0));
        while (!undecidedPoints.isEmpty()) {
            WeightedPoint p = undecidedPoints.poll();
            if (decidedPoint.contains(p)) {
                continue;
            } else if (p.x == endX && p.y == endY) {
                return p.weight;
            }
            int[] deltaX = {1, -1, 0, 0};
            int[] deltaY = {0, 0, 1, -1};
            for (int i = 0; i < 4; i++) {
                int x = p.x + deltaX[i];
                int y = p.y + deltaY[i];
                if (0 <= x && x < width && 0 <= y && y < height
                        && !obstacle[y][x] && distances[y][x] > p.weight + 1) {
                    // update
                    distances[y][x] = p.weight + 1;
                    undecidedPoints.add(new WeightedPoint(x, y, p.weight + 1));
                }
            }
            decidedPoint.add(p);
        }
//        System.out.println(endX + "|" + endY);
        return -1;
    }

    static int shortestPathDistance = Integer.MAX_VALUE;
    static int calcShortestPathDistance(int[][] distances) {
        shortestPathDistance = Integer.MAX_VALUE;
        int[] visitOrder = new int[numOfDirtyTiles + 1];
        visitOrder[0] = 0;
        List<Integer> list = new LinkedList<Integer>();
        for (int i = 1; i <= numOfDirtyTiles; i++) {
            list.add(i);
        }
        calcPermutationAndScore(distances, visitOrder, 1, list, 0);
        return shortestPathDistance;
    }

    static void calcPermutationAndScore(int[][] distances, int[] visitOrder,
            int currentIndex, List<Integer> unvisitedTileIds, int currentDistance) {
        if (currentDistance >= shortestPathDistance) {
            return;
        }
        int numOfUnvisitedTiles = unvisitedTileIds.size();
        if (numOfUnvisitedTiles == 0) {
            shortestPathDistance = Math.min(shortestPathDistance, currentDistance);
        }
        for (int i = 0; i < numOfUnvisitedTiles; i++) {
            int nextVisitTile = unvisitedTileIds.get(i);
            unvisitedTileIds.remove(i);
            visitOrder[currentIndex] = nextVisitTile;
            calcPermutationAndScore(distances, visitOrder, currentIndex + 1,
                    unvisitedTileIds, currentDistance + distances[visitOrder[currentIndex - 1]][nextVisitTile]);
            // restore
            unvisitedTileIds.add(i, nextVisitTile);
        }
    }

    static class WeightedPoint extends Point implements Comparable<WeightedPoint> {
        int weight;
        WeightedPoint(int x, int y, int weight) {
            super(x, y);
            this.weight = weight;
        }

        @Override
        public int compareTo(WeightedPoint weightedPoint) {
            return weight - weightedPoint.weight;
        }
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            return ((Point) o).x == x && ((Point) o).y == y;
        }

        @Override
        public int hashCode() {
            return x * 1000 + y;
        }
    }
}
