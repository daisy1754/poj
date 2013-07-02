import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1028
 **/
public class WebNavigation {
    private static final String COMMAND_BACK = "BACK";
    private static final String COMMAND_VISIT = "VISIT";
    private static final String COMMAND_FORWARD = "FORWARD";
    private static final String COMMAND_QUIT = "QUIT";
    private static final String OUTPUT_IGNORED = "Ignored";

    public static void main(String[] args) {
        List<String> history = new ArrayList<String>();
        history.add("http://www.acm.org/");
        int index = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String[] inputs = scanner.nextLine().split("\\s");
            String command = inputs[0];
            String output = OUTPUT_IGNORED;
            if (command.equals(COMMAND_VISIT)) {
                history = history.subList(0, index + 1);
                history.add(inputs[1]);
                index++;
                output = inputs[1];
            } else if (command.equals(COMMAND_FORWARD)) {
                if (index + 1 < history.size()) {
                    index++;
                    output = history.get(index);
                }
            } else if (command.equals(COMMAND_BACK)) {
                if (index - 1 >= 0) {
                    index--;
                    output = history.get(index);
                }
            } else if (command.equals(COMMAND_QUIT)) {
                return;
            }
            System.out.println(output);
        }
    }
}
