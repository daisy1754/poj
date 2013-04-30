import java.util.Arrays;
import java.util.Scanner;

/**
 * 2684
 **/
public class PolygonalLineSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int numOfLines = scanner.nextInt();
            if (numOfLines == 0)
                return;
            PolygonalLine template = new PolygonalLine(scanner);
            for (int i = 1; i <= numOfLines; i++) {
                PolygonalLine line = new PolygonalLine(scanner);
                if (template.isSameShape(line)) {
                    System.out.println(i);
                }
            }
            System.out.println("+++++");
        }
    }

    static class PolygonalLine {
        int[] x;
        int[] y;

        PolygonalLine(Scanner scanner) {
            int numOfPoints = scanner.nextInt();
            x = new int[numOfPoints];
            y = new int[numOfPoints];
            for (int i = 0; i < numOfPoints; i++) {
                x[i] = scanner.nextInt();
                y[i] = scanner.nextInt();
            }
        }

        PolygonalLine(int[] x, int[] y) {
            this.x = x;
            this.y = y;
        }

        public boolean isSameShape(PolygonalLine anotherLine) {
            if (x.length != anotherLine.x.length) {
                return false;
            }

            int x0Diff = anotherLine.x[0] - x[0];
            int y0Diff = anotherLine.y[0] - y[0];
            int xLastDiff = anotherLine.x[0] - x[x.length - 1];
            int yLastDiff = anotherLine.y[0] - y[y.length - 1];

            if (createTranslated(x0Diff, y0Diff).isExactlySameAs(anotherLine)
                    || createTranslated(xLastDiff, yLastDiff).isExactlySameInReverseOrder(anotherLine)) {
                return true;
            }

            for (int i = 0; i < 3; i++) {
                rotate90ClockWise();
                x0Diff = anotherLine.x[0] - x[0];
                y0Diff = anotherLine.y[0] - y[0];
                xLastDiff = anotherLine.x[0] - x[x.length - 1];
                yLastDiff = anotherLine.y[0] - y[y.length - 1];
                if (createTranslated(x0Diff, y0Diff).isExactlySameAs(anotherLine)
                        || createTranslated(xLastDiff, yLastDiff).isExactlySameInReverseOrder(anotherLine)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isExactlySameAs(PolygonalLine anotherLine) {
            for (int i = 0; i < x.length; i++) {
                if (x[i] != anotherLine.x[i])
                    return false;
                if (y[i] != anotherLine.y[i])
                    return false;
            }
            return true;
        }

        private boolean isExactlySameInReverseOrder(PolygonalLine anotherLine) {
            for (int i = 0; i < x.length; i++) {
                if (x[x.length - 1 - i] != anotherLine.x[i])
                    return false;
                if (y[y.length - 1 - i] != anotherLine.y[i])
                    return false;
            }
            return true;
        }

        public PolygonalLine createTranslated(int xDiff, int yDiff) {
            int[] newX = new int[x.length], newY = new int[x.length];
            for (int i = 0; i < x.length; i++) {
                newX[i] = x[i] + xDiff;
                newY[i] = y[i] + yDiff;
            }
            return new PolygonalLine(newX, newY);
        }

        public PolygonalLine rotate90ClockWise() {
            int[] newX = new int[x.length], newY = new int[x.length];
            for (int i = 0; i < x.length; i++) {
                newX[i] = y[i];
                newY[i] = -x[i];
            }
            x = newX;
            y = newY;
            return this;
        }

        public void print() {
            for (int i = 0; i < x.length; i++) {
                System.err.print("(" + x[i] + ", " + y[i] + ')');
            }
            System.err.println();
        }
    }
}
