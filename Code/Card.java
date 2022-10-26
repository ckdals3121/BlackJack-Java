public class Card {
    private String pattern;
    private String num;
    private int score;

    public Card(String pattern, String num, int score) {
        this.pattern = pattern;
        this.num = num;
        this.score = score;
    }
    public int getScore() {
        return this.score;
    }

    @Override
    public String toString() {
        return "Card{" +
                "pattern='" + pattern + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
