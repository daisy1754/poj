import java.util.Scanner;

/**
 * 1006
 * Works fine for sample inputs, but seems wrong answer ;(
 **/
public class Biorhythms {
    private static final int PHYSICAL_PEAK_CYCLE_LENGTH = 23;
    private static final int EMOTIONAL_PEAK_CYCLE_LENGTH = 28;
    private static final int MENTAL_PEAK_CYCLE_LENGTH = 33;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNo = 1;
        while (true) {
            int firstPhysicalPeak = scanner.nextInt();
            int firstEmotionalPeak = scanner.nextInt();
            int firstMentalPeak = scanner.nextInt();
            int day = scanner.nextInt();
            if (firstPhysicalPeak == -1 && firstEmotionalPeak == -1 &&
                    firstMentalPeak == -1 && day == -1) {
                return;
            }

            int dayToMeet = calcDayWhereThreePeakMeets(
                    firstPhysicalPeak, firstEmotionalPeak, firstMentalPeak);
            while (dayToMeet <= day) {
                dayToMeet = calcDayWhereThreePeakMeets(dayToMeet + PHYSICAL_PEAK_CYCLE_LENGTH, dayToMeet, dayToMeet);
            }
            System.out.println("Case " + caseNo
                    + ": the next triple peak occurs in " + (dayToMeet - day)
                    + " days.");
            caseNo++;
        }
    }

    private static int calcDayWhereThreePeakMeets(
            int firstPhysicalPeak, int firstEmotionalPeak, int firstMentalPeak) {
        int physicalPeakDay = firstPhysicalPeak;
        int emotionalPeakDay = firstEmotionalPeak;
        int mentalPeakDay = firstMentalPeak;
        while (true) {
            int dayWherePhysicalAndEmotionalPeak =
                    calcDayWherePhysicalAndEmotionalPeak(physicalPeakDay, emotionalPeakDay);
            while (mentalPeakDay < dayWherePhysicalAndEmotionalPeak) {
                mentalPeakDay += MENTAL_PEAK_CYCLE_LENGTH;
            }
            if (mentalPeakDay == dayWherePhysicalAndEmotionalPeak) {
                return mentalPeakDay;
            }
            physicalPeakDay = dayWherePhysicalAndEmotionalPeak + PHYSICAL_PEAK_CYCLE_LENGTH;
            emotionalPeakDay = dayWherePhysicalAndEmotionalPeak;
        }
    }

    private static int calcDayWherePhysicalAndEmotionalPeak(
            int physicalPeak, int emotionalPeak) {
        while (physicalPeak != emotionalPeak) {
            if (physicalPeak < emotionalPeak) {
                physicalPeak += PHYSICAL_PEAK_CYCLE_LENGTH;
            } else {
                emotionalPeak += EMOTIONAL_PEAK_CYCLE_LENGTH;
            }
        }
        return physicalPeak;
    }
}
