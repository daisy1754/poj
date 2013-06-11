import java.util.Scanner;

/**
 * 1012
 **/
public class Joseph {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // hard-coded value calculated by checkAnswer()
        int[] memo = new int[] {0, 2, 7, 5, 30, 169, 441, 1872, 7632, 1740,
                93313, 459901, 1358657, 2504881};
        while (true) {
            int k = scanner.nextInt();
            if (k == 0) {
                return;
            }
            if (memo[k] != 0) {
                System.out.println(memo[k]);
                continue;
            }

            for (int move = 2; move < Integer.MAX_VALUE; move++) {
                if (checkAnswer(k, move)) {
                    System.out.println(move);
                    memo[k] = move;
                    break;
                }
            }
        }
    }

    private static boolean checkAnswer(int numOfGoodGuys, int move) {
        int numOfPeople = 2 * numOfGoodGuys;
        int currentPosition = -1;
        while (numOfPeople > numOfGoodGuys) {
            int kill = (currentPosition + move) % numOfPeople;
            if (kill < numOfGoodGuys) {
                return false;
            }
            currentPosition = kill - 1;
            numOfPeople--;
        }
        return true;
    }
}
