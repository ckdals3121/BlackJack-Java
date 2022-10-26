import java.io.*;
import java.net.*;

public class BJThread extends Thread {
    private Socket soc;
    private int id;
    private int coin;
    private int start_coin;
    private int bet;
    public BJThread (Socket soc, int id) {
        this.soc = soc;
        this.id = id;
    }
    public void run () {
        String cmd;
        try {
            ObjectOutputStream outToClient=new ObjectOutputStream(soc.getOutputStream());
            ObjectInputStream inFromClient=new ObjectInputStream(soc.getInputStream());

            Dealer dealer = new Dealer();
            Gamer gamer = new Gamer();
            Rule rule = new Rule();
            CardDeck cardDeck = new CardDeck();

            coin = Integer.parseInt((String) inFromClient.readObject());
            start_coin = coin;
            while(true) {
                cmd = (String) inFromClient.readObject();
                if (cmd.equals("play")) {
                    dealer = new Dealer();
                    gamer = new Gamer();
                    rule = new Rule();
                    cardDeck = new CardDeck();
                    StringBuilder sb = new StringBuilder();
                    sb.append("========= Blackjack =========\n");
                    sb.append("You have " + coin + " coins.\n");
                    sb.append("=============================\n");
                    outToClient.writeObject(sb.toString());
                    bet = Integer.parseInt((String) inFromClient.readObject());
                    StringBuilder sb_gamer = new StringBuilder();
                    StringBuilder sb_dealer = new StringBuilder();
                    for (int i = 0; i < 2; i++) {
                        Card card = cardDeck.draw();
                        sb_gamer = gamer.receiveCard(card);

                        Card card2 = cardDeck.draw();
                        sb_dealer = dealer.receiveCard(card2);
                    }
                    outToClient.writeObject(sb_gamer.toString());
                    outToClient.writeObject(sb_dealer.toString());
                }
                else if (cmd.equals("draw")) {
                    Card card = cardDeck.draw();
                    StringBuilder sb = gamer.receiveCard(card);
                    outToClient.writeObject(sb.toString());
                }
                else if (cmd.equals("check")) {
                    while(dealer.getScoreSum() <= 16) {
                        Card card = cardDeck.draw();
                        dealer.receiveCard(card);
                    }
                    StringBuilder sb = dealer.showCards();
                    outToClient.writeObject(sb.toString());
                    String winner = rule.getWinner(dealer, gamer);
                    sb = new StringBuilder();
                    sb.append("Winner: " + winner+ "\n");
                    outToClient.writeObject(sb.toString());
                    if(winner.equals("Gamer")) {
                        if(gamer.getScoreSum() == 21)
                            coin += bet * 2;
                        else
                            coin += bet * 1.5;
                    }
                    else {
                        coin -= bet;
                    }

                    if(coin <= 0) {
                        outToClient.writeObject("0");
                        StringBuilder sb_lose = new StringBuilder();
                        sb_lose.append("You have 0 coin. You lose.\n");
                        outToClient.writeObject(sb_lose.toString());
                        break;
                    }
                    else if(coin >= start_coin*2) {
                        outToClient.writeObject("1");
                        StringBuilder sb_win = new StringBuilder();
                        sb.append("You have " + coin + " coins. You win.\n");
                        outToClient.writeObject(sb.toString());
                        break;
                    }
                    else {
                        outToClient.writeObject("2");
                        StringBuilder sb_continue = new StringBuilder();
                        sb.append("You have " + coin + " coins.\n");
                        outToClient.writeObject(sb.toString());
                    }

                }
                else if (cmd.equals("quit")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("You have " + coin + " coins.\n");
                    sb.append("==============================\n");
                    sb.append("Thank you for playing!\n");
                    outToClient.writeObject(sb.toString());
                    break;
                }
            }
            soc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
