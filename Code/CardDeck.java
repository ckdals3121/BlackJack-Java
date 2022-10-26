import java.util.LinkedList;
import java.util.List;
import java.util.Random;
public class CardDeck {
    private String[] Patterns = {"spade", "heart", "diamond", "club"};
    private int Card_num = 13;

    private List<Card> cards;

    public CardDeck() {
        cards = generateCards();
    }

    private List<Card> generateCards() {
        List<Card> cards = new LinkedList<>();

        for(String pattern : Patterns) {
            for(int i=1; i<=Card_num; i++) {
                String num = this.numberToNum(i);
                int score = numberToScore(i);
                Card card = new Card(num, pattern, score);
                cards.add(card);
            }
        }
        return cards;
    }

    private String numberToNum(int number) {
        if(number == 1) {
            return "A";
        }
        else if(number == 11) {
            return "J";
        }
        else if(number == 12) {
            return "Q";
        }
        else if(number == 13) {
            return "K";
        }

        return String.valueOf(number);
    }

    private int numberToScore(int number) {
        if(number >= 11) return 10;
        if(number == 1) return 11;
        else return number;
    }

    public Card draw(){
        Card selectedCard = getRandomCard();
        cards.remove(selectedCard);
        return selectedCard;
    }

    private Card getRandomCard() {
        int size = cards.size();
        Random random = new Random();
        int select = random.nextInt(size);
        return cards.get(select);
    }

}
