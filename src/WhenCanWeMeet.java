import java.util.*;

/**
 * 2028
 **/
public class WhenCanWeMeet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int numOfCommittee = scanner.nextInt();
            int quorum = scanner.nextInt();
            if (numOfCommittee == 0 && quorum == 0) {
                return;
            }
            MeetingScheduler scheduler = new MeetingScheduler(quorum);
            for (int i = 0; i < numOfCommittee; i++) {
                int numOfAvailableDays = scanner.nextInt();
                int[] availableDays = new int[numOfAvailableDays];
                for (int j = 0; j < numOfAvailableDays; j++) {
                    availableDays[j] = scanner.nextInt();
                }
                scheduler.addCommitteeSchedule(availableDays);
            }
            System.out.println(scheduler.calcBestDay());
        }

    }

    static class MeetingScheduler {
        private int quorum;
        private Map<Integer, Integer> numOfAvailableCommittee;

        public MeetingScheduler(int quorum) {
            this.quorum = quorum;
            numOfAvailableCommittee = new HashMap<Integer, Integer>();
        }

        public void addCommitteeSchedule(int[] schedule) {
            for (int i = 0; i < schedule.length; i++) {
                int day = schedule[i];
                if (numOfAvailableCommittee.containsKey(day)) {
                    numOfAvailableCommittee.put(
                            day, numOfAvailableCommittee.get(day) + 1);
                } else {
                    numOfAvailableCommittee.put(day, 1);
                }
            }
        }

        public int calcBestDay() {
            int bestDay = 0;
            int maxAttendance = 0;
            Iterator<Map.Entry<Integer, Integer>> it
                    = numOfAvailableCommittee.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getValue() > maxAttendance) {
                    bestDay = entry.getKey();
                    maxAttendance = entry.getValue();
                }
            }
            if (maxAttendance >= quorum) {
                return bestDay;
            } else {
                return 0;
            }
        }
    }
}
