import java.util.Scanner;

/**
 * 1068
 **/
public class Parencodings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfTestCases = scanner.nextInt();
        for (int i = 0; i < numOfTestCases; i++) {
            int numOfInteger = scanner.nextInt();
            int[] pSequence = new int[numOfInteger];
            for (int j = 0; j < numOfInteger; j++) {
                pSequence[j] = scanner.nextInt();
            }
            int[] directLeftParenthesis = new int[numOfInteger];
            directLeftParenthesis[0] = pSequence[0];
            for (int j = 1; j < numOfInteger; j++) {
                directLeftParenthesis[j] = pSequence[j] - pSequence[j - 1];
            }
            int[] wSequence = calcWSequence(directLeftParenthesis);
            for (int j = 0; j < wSequence.length - 1; j++) {
                System.out.print(wSequence[j] + " ");
            }
            System.out.println(wSequence[wSequence.length - 1]);
        }
    }

    private static int[] calcWSequence(int[] directLeftParenthesis) {
        int[] wSequence = new int[directLeftParenthesis.length];
        for (int i = 0; i < wSequence.length; i++) {
            int wValue = 1;
            int numOfLeftParenthesis = 0;
            for (int j = i; j >= 0; j--) {
                numOfLeftParenthesis += directLeftParenthesis[j];
                if (numOfLeftParenthesis >= wValue) {
                    break;
                }
                wValue++;
            }
            wSequence[i] = wValue;
        }
        return wSequence;
    }
}
