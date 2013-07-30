import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 1017
 **/
public class Packets {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numOfBoxes = new int[6];
        while (true) {
            for (int i = 0; i < numOfBoxes.length; i++) {
                numOfBoxes[i] = scanner.nextInt();
            }
            if (Arrays.equals(numOfBoxes, new int[] {0, 0, 0, 0, 0, 0})) {
                return;
            }
            System.out.println(solve(numOfBoxes));
        }
    }

    private static int solve(int[] numOfBoxes) {
        int numOfPackets = 0;
        List<Blanks> blanks = new ArrayList<Blanks>();
        /// 6 * 6
        for (int i = 0; i < numOfBoxes[5]; i++) {
            numOfPackets++;
        }
        // 5 * 5
        for (int i =0; i < numOfBoxes[4]; i++) {
            numOfPackets++;
            blanks.add(new Blanks(11, 0));
        }
        // 4 * 4
        for (int i = 0; i < numOfBoxes[3]; i++) {
            numOfPackets++;
            blanks.add(new Blanks(0, 5));
        }
        // 3 * 3
        numOfPackets += numOfBoxes[2] / 4;
        numOfBoxes[2] %= 4;
        switch (numOfBoxes[2]) {
            case 0:
                break;
            case 1:
                numOfPackets++;
                blanks.add(new Blanks(7, 5));
                break;
            case 2:
                numOfPackets++;
                blanks.add(new Blanks(6, 3));
                break;
            case 3:
                numOfPackets++;
                blanks.add(new Blanks(5, 1));
                break;
        }
        // 2 * 2
        while (numOfBoxes[1] > 0) {
            for (Blanks blank: blanks) {
                if (blank.blankForSize2 < numOfBoxes[1]) {
                    numOfBoxes[1] -= blank.blankForSize2;
                    blank.blankForSize2 = 0;
                } else {
                    blank.blankForSize2 -= numOfBoxes[1];
                    numOfBoxes[1] = 0;
                    break;
                }
            }
            if (numOfBoxes[1] > 0) {
                numOfPackets++;
                blanks.add(new Blanks(0, 9));
            }
        }
        // 1 * 1
        for (Blanks blank: blanks) {
            blank.blankForSize1 += blank.blankForSize2 * 4;
            blank.blankForSize2 = 0;
        }
        while (numOfBoxes[0] > 0) {
            for (Blanks blank: blanks) {
                if (blank.blankForSize1 < numOfBoxes[0]) {
                    numOfBoxes[0] -= blank.blankForSize1;
                    blank.blankForSize1 = 0;
                } else {
                    blank.blankForSize1 -= numOfBoxes[0];
                    numOfBoxes[0] = 0;
                    break;
                }
            }
            if (numOfBoxes[0] > 0) {
                numOfPackets++;
                blanks.add(new Blanks(36, 0));
            }
        }

        return numOfPackets;
    }

    private static class Blanks {
        private int blankForSize1;
        private int blankForSize2;
        public Blanks(int blankForSize1, int blankForSize2) {
            this.blankForSize1 = blankForSize1;
            this.blankForSize2 = blankForSize2;
        }
    }
}
