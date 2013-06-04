import java.util.Scanner;

/**
 * 1458
 **/
public class CommonSubsequence {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] strs = line.split("\\s+");
            System.out.println(solve(strs[0].toCharArray(), strs[1].toCharArray()));
        }
    }

    static int[][] table;
    private static int solve(char[] char1, char[] char2) {
        table = new int[char1.length][char2.length];
        for (int r = 0; r < char1.length; r++) {
            for (int c = 0; c < char2.length; c++) {
                updateTable(r, c, char1[r] == char2[c] ? 1 : 0);
            }
        }
        return table[char1.length - 1][char2.length - 1];
    }

    private static void updateTable(int row, int column, int delta) {
        int above = row == 0 ? 0 : table[row - 1][column];
        int left = column == 0 ? 0 : table[row][column - 1];
        int leftAbove = (row == 0 || column == 0) ? 0 : table[row - 1][column - 1];
        table[row][column] = Math.max(Math.max(above, left), leftAbove + delta);
    }
}
