import java.util.Scanner;

/**
 * 2030
 * Works fine for sample inputs, but seems wrong answer ;(
 **/
public class SecretNumberFinder {
    private static int[][] stage;
    private static boolean[][] isContained;
    private static int width;
    private static int height;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            width = scanner.nextInt();
            height = scanner.nextInt();
            if (width == 0 && height == 0) {
                return;
            }
            stage= new int[height][width];
            isContained = new boolean[height][width];
            //scanner.nextLine();
            for (int y = 0; y < height; y++) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    char ch = line.charAt(x);
                    if ('0' <= ch && ch <= '9') {
                        stage[y][x] = ch - '0';
                    } else {
                        stage[y][x] = -1;
                    }
                }
            }

//            for (int i = 0; i < height; i++) {
//                System.out.println(Arrays.toString(stage[i]));
//            }

            String maxScore = null;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (!isContained[y][x] && stage[y][x] > 0) {
//                        System.out.println("Start search: " + x + ", " + y);
                        maxScore = max(maxScore, calcScore(x, y, ""));
//                        System.out.println(maxScore);
                    }
                }
            }
            System.out.println(maxScore);
        }
    }

    private static String calcScore(int startX, int startY, String subStr) {
        isContained[startY][startY] = true;
        String rightScore = null, downScore = null;
        if (startX + 1 < width && stage[startY][startX + 1] >= 0) {
            rightScore = calcScore(startX + 1, startY, subStr + String.valueOf(stage[startY][startX]));
        }
        if (startY + 1 < height && stage[startY + 1][startX] >= 0) {
            downScore = calcScore(startX, startY + 1, subStr + String.valueOf(stage[startY][startX]));
        }

        if (rightScore == null && downScore == null) {
            return subStr + String.valueOf(stage[startY][startX]);
        }

        return max(rightScore, downScore);
    }

    private static String max(String num1, String num2) {
        if (num1 == null) {
            return num2;
        } else if (num2 == null) {
            return num1;
        }

        if (num1.length() < num2.length()) {
            return num2;
        } else if (num1.length() > num2.length()) {
            return num1;
        } else {
            for (int i = 0; i < num1.length(); i++) {
                int digit1 = num1.charAt(i) - '0';
                int digit2 = num2.charAt(i) - '0';
                if (digit1 < digit2) {
                    return num2;
                } else if (digit1 > digit2) {
                    return num1;
                }
            }
        }
        return num1;
    }
}
