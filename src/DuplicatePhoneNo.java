import java.util.*;

/**
 * 1002
 **/
public class DuplicatePhoneNo {
    private static Map<Character, Integer> charToInt;

    public static void main(String[] args) {
        initCharToInt();
        Scanner scanner = new Scanner(System.in);
        int numOfTestCases = scanner.nextInt();
        scanner.nextLine();
        SortedMap<String, Integer> phoneNoCount
                = new TreeMap<String, Integer>();
        for (int i = 0; i < numOfTestCases; i++) {
            String phoneNo = scanner.nextLine();
            String stdPhoneNo = convertToStandardForm(phoneNo);
            if (phoneNoCount.containsKey(stdPhoneNo)) {
                int currentCount = phoneNoCount.get(stdPhoneNo);
                phoneNoCount.put(stdPhoneNo, currentCount + 1);
            } else {
                phoneNoCount.put(stdPhoneNo, 1);
            }
        }
        boolean flag = false;
        for (String key: phoneNoCount.keySet()) {
            if (phoneNoCount.get(key) > 1) {
                flag = true;
                System.out.println(key + " " + phoneNoCount.get(key));
            }
        }
        if (!flag) {
            System.out.println("No duplicates.");
        }
    }

    private static void initCharToInt() {
        charToInt = new HashMap<Character, Integer>();
        charToInt.put('A', 2);
        charToInt.put('B', 2);
        charToInt.put('C', 2);
        charToInt.put('D', 3);
        charToInt.put('E', 3);
        charToInt.put('F', 3);
        charToInt.put('G', 4);
        charToInt.put('H', 4);
        charToInt.put('I', 4);
        charToInt.put('J', 5);
        charToInt.put('K', 5);
        charToInt.put('L', 5);
        charToInt.put('M', 6);
        charToInt.put('N', 6);
        charToInt.put('O', 6);
        charToInt.put('P', 7);
        charToInt.put('R', 7);
        charToInt.put('S', 7);
        charToInt.put('T', 8);
        charToInt.put('U', 8);
        charToInt.put('V', 8);
        charToInt.put('W', 9);
        charToInt.put('X', 9);
        charToInt.put('Y', 9);
        for (int i = 0; i <= 9; i++) {
            charToInt.put((char) (i + '0'), i);
        }
    }

    private static String convertToStandardForm(String no) {
        char[] standardForm = new char[8];
        int index = 0;
        for (int i = 0; i < no.length(); i++) {
            char ch = no.charAt(i);
            if (charToInt.containsKey(ch)) {
                standardForm[index] = (char) (charToInt.get(ch) + '0');
                index++;
                if (index == 3) {
                    standardForm[index] = '-';
                    index++;
                }
            }
        }
        return new String(standardForm);
    }
}
