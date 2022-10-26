import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

class access extends Thread {
    public void run () {
        System.out.println("Command for the game: ");
        System.out.println("play: start a new game");
        System.out.println("draw: you draw a card");
        System.out.println("check: check who wins");
        System.out.println("exit: exit the game");
        System.out.println("Please input only above commands");
        try {
            Socket soc = new Socket("localhost", 1234);

            Scanner scn=new Scanner(System.in);
            String cmd;

            ObjectOutputStream outToServer=new ObjectOutputStream(soc.getOutputStream());
            ObjectInputStream inFromServer=new ObjectInputStream(soc.getInputStream());

            System.out.println("Enter the start money: ");
            String coin_temp = scn.nextLine();
            outToServer.writeObject(coin_temp);

            while(true) {
                System.out.println("Enter a command: (play, draw, check, exit)");
                cmd = scn.nextLine();
                if(cmd.equals("play")) {
                    outToServer.writeObject("play");
                    System.out.println(inFromServer.readObject());
                    System.out.println("Enter the bet: ");
                    String bet_temp = scn.nextLine();
                    outToServer.writeObject(bet_temp);
                    System.out.println(inFromServer.readObject());
                    System.out.println(inFromServer.readObject());
                }
                else if(cmd.equals("draw")) {
                    outToServer.writeObject("draw");
                    System.out.println(inFromServer.readObject());
                }
                else if(cmd.equals("check")) {
                    outToServer.writeObject("check");
                    System.out.println("If Dealer's cardSum is smaller than 16, Dealer draw a card");
                    System.out.println(inFromServer.readObject());
                    System.out.println(inFromServer.readObject());
                    String temp = (String)inFromServer.readObject();
                    if(temp.equals("0") || temp.equals("1")) {
                        System.out.println(inFromServer.readObject());
                        break;
                    }
                    else {
                        System.out.println(inFromServer.readObject());
                    }
                }
                else if(cmd.equals("quit")) {
                    outToServer.writeObject("quit");
                    System.out.println(inFromServer.readObject());
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
public class Client {
    public static void main (String[] args) {
        new access().start();
    }
}
