import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 1200
 **/
public class CrazySearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextInt();
        Set<String> strSet = new HashSet<String>();
        scanner.useDelimiter("\\n");
        String string = scanner.next();
        for (int i = 0; i < string.length() - n + 1; i++) {
            strSet.add(string.substring(i, i + n));
        }
        System.out.print(strSet.size());
    }
}
