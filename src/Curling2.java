import java.util.Arrays;
import java.util.Scanner;

/**
 * 3009
 **/
public class Curling2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();

            if (width == 0 && height == 0) {
                return;
            }

            Board board = new Board(width, height, scanner);
            System.out.println(board.calcMinNumMove());

        }
    }

    private static class Board {
        int width, height;
        int startX, startY;
        int endX, endY;
        int[][] stage;

        private static final int DIRECTION_UP = 0;
        private static final int DIRECTION_RIGHT = 1;
        private static final int DIRECTION_DOWN = 2;
        private static final int DIRECTION_LEFT = 3;

        private static final int TYPE_NONE = 0;
        private static final int TYPE_OBSTACLE = 1;

        public Board(int width, int height, Scanner scanner) {
            this.width = width;
            this.height = height;
            stage = new int[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int cell = scanner.nextInt();
                    if (cell == 2) {
                        startX = j;
                        startY = i;
                        stage[i][j] = TYPE_NONE;
                    } else if (cell == 3) {
                        endX = j;
                        endY = i;
                        stage[i][j] = TYPE_NONE;
                    } else {
                        stage[i][j] = cell;
                    }
                }
            }
//            for (int i = 0; i < stage.length; i++)
//              System.out.println(Arrays.toString(stage[i]));
        }

        public int calcMinNumMove() {
            int answer = getMinNumMoveRecursion(startX, startY, 0);
            if (answer <= 10) {
                return answer;
            } else {
                return -1;
            }
        }

        private int getMinNumMoveRecursion(
                int currentX, int currentY, int currentNumOfMove) {
            if (currentX == endX && currentY == endY) {
                return currentNumOfMove;
            }

            currentNumOfMove++;
            if (currentNumOfMove > 10){
                return 100;
            }

            int scoreWithTop = calcScoreWhenMoveTo(currentX, currentY, DIRECTION_UP, 0, 1, currentNumOfMove);
            int scoreWithRight = calcScoreWhenMoveTo(currentX, currentY, DIRECTION_RIGHT, -1, 0, currentNumOfMove);
            int scoreWithBottom = calcScoreWhenMoveTo(currentX, currentY, DIRECTION_DOWN, 0, -1, currentNumOfMove);
            int scoreWithLeft = calcScoreWhenMoveTo(currentX, currentY, DIRECTION_LEFT, 1, 0, currentNumOfMove);

            return Math.min(Math.min(scoreWithTop, scoreWithRight), Math.min(scoreWithBottom, scoreWithLeft));
        }

        private int calcScoreWhenMoveTo(int fromX, int fromY, int direction, int deltaX, int deltaY, int currentNumOfMove) {
//            System.out.println("try (" + fromX + ", " + fromY + ") " + direction + "|"+ canReachToGoal(fromX, fromY, direction));
            if (canReachToGoal(fromX, fromY, direction)) {
                return currentNumOfMove;
            }

            Point closestObstacle = getClosestObstacle(fromX, fromY, direction);

            if (closestObstacle == null) {
                return 100;
            }
            if (Math.abs(closestObstacle.x - fromX) < 2 && Math.abs(closestObstacle.y - fromY) < 2 ) {
//                Point p = getClosestObstacle(4, 0, DIRECTION_LEFT);
//                System.out.println(p.x + ", " + p.y);
//                System.out.println(closestObstacle.x + "|"+ closestObstacle.y + "|" + fromX + "|"+ fromY + "|"+ direction + "|" +currentNumOfMove);
                return 100;
            }

            stage[closestObstacle.y][closestObstacle.x] = TYPE_NONE;
            int score = getMinNumMoveRecursion(closestObstacle.x + deltaX, closestObstacle.y + deltaY, currentNumOfMove);
            stage[closestObstacle.y][closestObstacle.x] = TYPE_OBSTACLE;
            return score;
        }

        private Point getClosestObstacle(int fromX, int fromY, int direction) {
            int deltaX = 0, deltaY = 0;
            int x = fromX, y = fromY;
            switch (direction) {
                case DIRECTION_UP:
                    deltaY = -1;
                    break;
                case DIRECTION_DOWN:
                    deltaY = 1;
                    break;
                case DIRECTION_RIGHT:
                    deltaX = 1;
                    break;
                case DIRECTION_LEFT:
                    deltaX = -1;
                    break;
            }
            while (0 <= x && x < width && 0 <= y && y < height) {
                x += deltaX;
                y += deltaY;
                if (x < 0 || width <= x || y < 0 || height <= y) {
                    return null;
                } else if (stage[y][x] == TYPE_OBSTACLE) {
                    return new Point(x, y);
                }
            }
            return  null;
        }

        private boolean canReachToGoal(int currentX, int currentY, int direction) {
            int deltaX = 0, deltaY = 0;
            int x = currentX, y = currentY;
            switch (direction) {
                case DIRECTION_UP:
                    deltaY = -1;
                    break;
                case DIRECTION_DOWN:
                    deltaY = 1;
                    break;
                case DIRECTION_RIGHT:
                    deltaX = 1;
                    break;
                case DIRECTION_LEFT:
                    deltaX = -1;
                    break;
            }
            while (0 <= x && x < width && 0 <= y && y < height) {
                x += deltaX;
                y += deltaY;
                if (x == endX && y == endY) {
                    return true;
                }

                if (x < 0 || width <= x || y < 0 || height <= y) {
                    return false;
                } else if (stage[y][x] == TYPE_OBSTACLE) {
                    return false;
                }
            }
            return false;
        }
    }

    private static class Point {
        public int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
