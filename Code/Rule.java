public class Rule {
    public String getWinner(Dealer dealer, Gamer gamer) {
        String Winner = "";
        int dealerScore = dealer.getScoreSum();
        int gamerScore = gamer.getScoreSum();

        if(gamerScore > 21 && dealerScore > 21) {
            Winner = "Dealer";
        }
        else if(gamerScore > 21 && dealerScore <= 21) {
            Winner = "Dealer";
        }
        else if(gamerScore <= 21 && dealerScore > 21) {
            Winner = "Gamer";
        }
        else if(dealerScore > gamerScore) {
            Winner = "Dealer";
        }
        else {
            Winner = "Gamer";
        }


        return Winner;

    }
}