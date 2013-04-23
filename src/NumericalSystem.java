import java.util.Arrays;
import java.util.Scanner;

/**
 * 2685
 **/
public class NumericalSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfSpecs = Integer.parseInt(scanner.nextLine());
        while (numOfSpecs-- > 0) {
            String line = scanner.nextLine();
            Adder adder = new Adder(line.split(" "));
            System.out.println(adder.calcResult());
        }
    }

    static class Adder {
        int[] num1 = new int[4], num2 = new int[4];
        int m = 3;
        int c = 2;
        int x = 1;
        int i = 0;

        public Adder(String[] inputs) {
            Arrays.fill(num1, 0);
            Arrays.fill(num2, 0);
            parse(inputs[0], num1);
            parse(inputs[1], num2);
        }

        private void parse(String str, int[] orders) {
            char[] chars = str.toCharArray();
            for (int index = 0; index < chars.length; index++) {
                switch (chars[index]) {
                    case 'm':
                        orders[m] = getOrder(chars, index);
                        break;
                    case 'c':
                        orders[c] = getOrder(chars,index);
                        break;
                    case 'x':
                        orders[x] = getOrder(chars, index);
                        break;
                    case 'i':
                        orders[i] = getOrder(chars, index);
                        break;
                }
            }
        }

        private int getOrder(char[] chars, int index) {
            if (index == 0)
                return 1;
            if (chars[index - 1] < '0' || '9' < chars[index - 1])
                return 1;
            else
                return chars[index - 1] - '0';
        }

        private int encode(int[] nums) {
            int encoded = 0;
            int order = 1;
            for (int i = 0; i < 4; i++) {
                encoded += nums[i] * order;
                order *= 10;
            }
            return encoded;
        }

        private String decode(int n) {
            StringBuilder builder = new StringBuilder();
            int order = 1000;
            while (order > 0) {
                int digit = n / order;
                if (digit != 0) {
                    if (digit != 1) {
                        builder.append(digit);
                    }
                    switch (order) {
                        case 1000:
                            builder.append('m');
                            break;
                        case 100:
                            builder.append('c');
                            break;
                        case 10:
                            builder.append('x');
                            break;
                        case 1:
                            builder.append('i');
                            break;
                    }
                }
                n %= order;
                order /= 10;
            }

            return builder.toString();
        }

        public String calcResult() {
            int n1 = encode(num1), n2 = encode(num2);
            int answer = n1 + n2;
            return decode(answer);
        }
    }
}
