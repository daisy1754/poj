import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 2029
 **/
public class GetManyPersimmonTrees {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int numOfTrees = scanner.nextInt();
            if (numOfTrees == 0) {
                return;
            }
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            Set<Point> trees = new HashSet<Point>(numOfTrees);
            for (int i = 0; i < numOfTrees; i++) {
                trees.add(new Point(scanner.nextInt(), scanner.nextInt()));
            }
            EstateManager manager = new EstateManager(
                    width, height, trees, scanner.nextInt(), scanner.nextInt());
            System.out.println(manager.calcMaxNumOfTree());
        }
    }

    static class EstateManager {
        private int totalWidth;
        private int totalHeight;
        private Set<Point> trees;
        private int myWidth;
        private int myHeight;

        public EstateManager(
                int width, int height, Set<Point> trees,
                int myWidth, int myHeight) {
            this.totalWidth = width;
            this.totalHeight = height;
            this.trees = trees;
            this.myWidth = myWidth;
            this.myHeight = myHeight;
        }

        public int calcMaxNumOfTree() {
            int maxNum = 0;
            for (int x = 1; x <= totalWidth - myWidth + 1; x++) {
                for (int y = 1; y <= totalHeight - myHeight + 1; y++) {
                    int num = calcNumOfTreeInMyRegion(x, y);
                    maxNum = maxNum > num ? maxNum : num;
                }
            }
            return maxNum;
        }

        private int calcNumOfTreeInMyRegion(int startX, int startY) {
            int num = 0;
            for (Point p: trees) {
                if ((startX <= p.x && p.x < startX + myWidth)
                        && (startY <= p.y && p.y < startY + myHeight)) {
                    num++;
                }
            }
            return num;
        }
    }

    static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
