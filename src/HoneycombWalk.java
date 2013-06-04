import java.util.Scanner;

/**
 * 3036
 **/
public class HoneycombWalk {
    // 0 -> (2, 0), 1 -> (1, 1), 2 -> (1, -1), 3 -> (-2, 0), 4 -> (-1, 1), 5 -> (-1, -1)
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfTestCase = scanner.nextInt();
        for (int i = 0; i < numOfTestCase; i++) {
            int numOfTotalMove = scanner.nextInt();
            int total = 0;
            int[] numOfMoves = new int[6];
            for (numOfMoves[0] = 0; numOfMoves[0] <= numOfTotalMove / 2; numOfMoves[0]++) {
                for (numOfMoves[1] = 0; numOfMoves[1] <= numOfTotalMove / 2; numOfMoves[1]++) {
                    for (numOfMoves[2] = 0; numOfMoves[2] <= numOfTotalMove / 2; numOfMoves[2]++) {
                        total += solve(numOfTotalMove, numOfMoves);
                    }
                }
            }
            System.out.println(total);
        }
    }

    private static int solve(int numOfTotalMoves, int[] numOfMoves) {
        // for X: 2 * num3 + num4 + num5 should be 2 * num0 + num1 + num2
        // for Y: num5 - num4 should be num1 - num2
        // totalMove: num0 .. + num5 should be numOfTotalMove

        int numOfRemainingMoves = numOfTotalMoves - (numOfMoves[0] + numOfMoves[1] + numOfMoves[2]);
        if (numOfRemainingMoves <= 0) {
            return 0;
        }
        int deltaY = numOfMoves[1] - numOfMoves[2];

        int deltaX = 2 * numOfMoves[0] + numOfMoves[1] + numOfMoves[2];
        numOfMoves[3] = deltaX - numOfRemainingMoves;
        numOfRemainingMoves -= numOfMoves[3];
        if (numOfMoves[3] < 0 || numOfRemainingMoves < 0) {
            return 0;
        }
        if ((deltaY + numOfRemainingMoves) % 2 != 0) {
            return 0;
        }
        numOfMoves[4] = (deltaY + numOfRemainingMoves) / 2;
        numOfMoves[5] = numOfRemainingMoves - numOfMoves[4];
        if (numOfMoves[4] < 0 || numOfMoves[5] < 0) {
            return 0;
        }
        return calcNumOfPermutation(numOfTotalMoves, numOfMoves);
    }

    private static int calcNumOfPermutation(int total, int[] nums) {
        long ans = factorial(total);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                ans /= factorial(nums[i]);
            }
        }
        return (int) ans;
    }

    static long[] factorials = new long[15];
    private static long factorial(int n) {
        if (factorials[n] == 0) {
            factorials[n] = 1;
            for (int i = 1; i <= n; i++) {
                factorials[n] *= i;
            }
        }
        return factorials[n];
    }
}
