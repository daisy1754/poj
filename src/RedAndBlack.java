import java.util.Arrays;
import java.util.Scanner;

/**
 * 1979
 **/
public class RedAndBlack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            w = scanner.nextInt();
            h = scanner.nextInt();
            if (w == 0 && h == 0) {
                break;
            }

            int startX = 0, startY = 0;
            isBlack = new boolean[h][w];
            scanner.nextLine();
            for (int index = 0; index < h; index++) {
                char[] line = scanner.nextLine().toCharArray();
                for (int j = 0; j < line.length; j++) {
                    switch (line[j]) {
                        case '.':
                            isBlack[index][j] = true;
                            break;
                        case '#':
                            isBlack[index][j] = false;
                            break;
                        case '@':
                            isBlack[index][j] = true;
                            startX = j;
                            startY = index;
                            break;
                    }
                }
            }
            visited = new boolean[h][w];
            for (int i = 0; i < visited.length; i++) {
                Arrays.fill(visited[i], false);
            }
            numOfVisited = 0;
            move(startX, startY);
            System.out.println(numOfVisited);
        }
    }

    static int w, h;
    static boolean[][] visited;
    static boolean[][] isBlack;
    static int numOfVisited;

    static private void move(int x, int y) {
        if (y < 0 || x < 0 || h <= y || w <= x || visited[y][x] || !isBlack[y][x]) {
            return;
        }
        numOfVisited++;
        visited[y][x] = true;

        move(x, y - 1);
        move(x, y + 1);
        move(x - 1, y);
        move(x + 1, y);
    }
}
