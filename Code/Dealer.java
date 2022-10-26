import java.util.ArrayList;
import java.util.List;
public class Dealer {
    private List<Card> cards;

    public Dealer() {
        cards = new ArrayList<>();
    }

    public StringBuilder receiveCard(Card card) {
        this.cards.add(card);
        StringBuilder sb = this.showCards();

        return sb;
    }

    public int getScoreSum() {
        int sum = 0;
        for(Card card : cards) {
            sum += card.getScore();
        }
        return sum;
    }

    public StringBuilder showCards() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dealer's list of cards: ");
        sb.append("\n");

        for(Card card : cards) {
            sb.append(card.toString());
            sb.append("\n");
        }
        return sb;
    }
}
