import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 3007
 **/
public class OrganizeTrain2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfLines = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numOfLines; i++) {
            String line = scanner.nextLine();
            System.out.println(calcNumOfUniqueTrains(line));
        }
    }

    private static int calcNumOfUniqueTrains(String train) {
        char[] trainChars = train.toCharArray();
        char[] newTrainChars = new char[trainChars.length];
        Set<String> knownTrains = new HashSet<String>();
        for (int divideAt = 1; divideAt < trainChars.length; divideAt++) {
            for (int flag = 1; flag <=8; flag++) {
                boolean isFIFO = flag % 2 == 0;
                boolean isReverseFront = (flag >> 1) % 2 == 0;
                boolean isReverseRear = (flag >> 2) % 2 == 0;
                for (int i = 0; i < divideAt; i++) {
                    newTrainChars[isFIFO ? i : (trainChars.length - divideAt) + i]
                            = isReverseFront ? trainChars[divideAt - 1 - i] : trainChars[i];
                }
                for (int i = divideAt; i < trainChars.length; i++) {
                    newTrainChars[isFIFO ? i : i - divideAt]
                            = isReverseRear ? trainChars[trainChars.length + divideAt - 1 - i] : trainChars[i];
                }
//                System.out.println(divideAt +"|" + isFIFO +"|"+ isReverseFront +"|" +isReverseRear +"|"+ new String(newTrainChars));
                knownTrains.add(new String(newTrainChars));
            }
        }
        return knownTrains.size();
    }
}
