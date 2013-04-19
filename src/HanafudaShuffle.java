import java.util.Arrays;
import java.util.Scanner;

/**
 * 1978
 **/
public class HanafudaShuffle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int numOfCards = scanner.nextInt();
            int numOfOperations = scanner.nextInt();
            if (numOfCards == 0 && numOfOperations == 0) {
                return;
            }
            Deck deck = new Deck(numOfCards);
            for (int i = 0; i < numOfOperations; i++) {
                int p = scanner.nextInt();
                int c = scanner.nextInt();
                deck.hanafudaShuffle(p - 1, c);
            }
            System.out.println(deck.getTopCardNumber());
        }
    }

    static class Deck {
        private int[] cards;

        public Deck(int numOfCard) {
            cards = new int[numOfCard];
            for (int i = 0; i < numOfCard; i++) {
                cards[i] = numOfCard - i;
            }
        }

        public Deck hanafudaShuffle(int startIndex, int numOfCard) {
            int[] cardsToMove = Arrays.copyOfRange(
                    cards, startIndex, startIndex + numOfCard);
            // Shift
            for (int i = startIndex - 1; i >= 0; i--) {
                cards[i + numOfCard] = cards[i];
            }
            // Put cardsToMove on top
            for (int i = 0; i < numOfCard; i++) {
                cards[i] = cardsToMove[i];
            }
            return this;
        }

        public int getTopCardNumber() {
            return cards[0];
        }
    }
}
