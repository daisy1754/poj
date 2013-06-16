import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 1132
 **/
public class Border {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfBitmaps = scanner.nextInt();
        for (int bmIndex = 1; bmIndex <= numberOfBitmaps; bmIndex++) {
            boolean bitmap[][] = new boolean[32][32];
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++)
                    bitmap[i][j] = false;
            }
            int currentX = scanner.nextInt();
            int currentY = scanner.nextInt();
            String commands = scanner.next(Pattern.compile("[ENWS]+\\."));
            for (int chIndex = 0; chIndex < commands.length() - 1; chIndex++) {
                switch (commands.charAt(chIndex)) {
                    case 'E':
                        bitmap[currentY - 1][currentX] = true;
                        currentX++;
                        break;
                    case 'W':
                        bitmap[currentY][currentX - 1] = true;
                        currentX--;
                        break;
                    case 'N':
                        bitmap[currentY][currentX] = true;
                        currentY++;
                        break;
                    case 'S':
                        bitmap[currentY - 1][currentX - 1] = true;
                        currentY--;
                        break;
                }
            }
            System.out.println("Bitmap #" + bmIndex);
            for (int row = 31; row >= 0; row--) {
                for (int col = 0; col < 32; col++) {
                    System.out.print(bitmap[row][col] ? 'X' : '.');
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
