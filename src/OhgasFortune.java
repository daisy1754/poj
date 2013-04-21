import java.util.Scanner;

/**
 * 2683
 **/
public class OhgasFortune {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int numOfTestcase = scanner.nextInt();

        while (numOfTestcase-- > 0) {
            DepositManager manager
                    = new DepositManager(scanner.nextInt(), scanner.nextInt());
            System.out.println(manager.calcFinalDepositAmount());
        }
    }

    static class DepositManager {
        private final int initialDeposit;
        private final int year;

        public DepositManager(int initialDeposit, int year) {
            this.initialDeposit = initialDeposit;
            this.year = year;
        }

        public int calcFinalDepositAmount() {
            int numOfManagementPlans = scanner.nextInt();
            int maxFinalDeposit = initialDeposit;
            for (int i = 0; i < numOfManagementPlans; i++) {
                boolean isSimpleInterest = scanner.nextInt() == 0;
                double interestRate = scanner.nextDouble();
                int annualFee = scanner.nextInt();
                int finalDeposit = calcDeposit(
                        isSimpleInterest, interestRate, annualFee);
                maxFinalDeposit = maxFinalDeposit > finalDeposit ?
                        maxFinalDeposit : finalDeposit;
            }
            return maxFinalDeposit;
        }

        private int calcDeposit(
                boolean isSimpleInterest, double interestRate, int annualFee) {
            if (isSimpleInterest) {
                return calcDepositForSimpleInterest(interestRate, annualFee);
            } else {
                return calcDepositForCompoundInterest(interestRate, annualFee);
            }
        }

        private int calcDepositForSimpleInterest(
                double interestRate, int annualFee) {
            int deposit = initialDeposit;
            int interests = 0;
            for (int y = 0; y < year; y++) {
                interests += (int) (deposit * interestRate);
                deposit -= annualFee;
            }
            return deposit + interests;
        }

        private int calcDepositForCompoundInterest(
                double interestRate, int annualFee) {
            int deposit = initialDeposit;
            for (int y = 0; y < year; y++) {
                int interest = (int) (deposit * interestRate);
                deposit = deposit + interest - annualFee;
            }
            return deposit;
        }
    }
}
