import java.io.*;

public class PokerIO {
    
    public static void record(Card [] pHand, Card [] cHand, Card [] board, int bet) throws IOException {
        //Check if file exists
        File hands = new File("handHistory.txt");
        //If file exists just add to the end of the history
        if (hands.exists()) {
            FileWriter fw = new FileWriter("handHistory.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 2; i++) {
                bw.write(pHand[i].getSuit() + "." + pHand[i].getNum() + " ");
            }
            bw.write("\n");
            for (int i = 0; i < 2; i++) {
                bw.write(cHand[i].getSuit() + "." + cHand[i].getNum() + " ");
            }
            bw.write("\n");
            for (int i = 0; i < 5; i++) {
                bw.write(board[i].getSuit() + "." + board[i].getNum() + " ");
            }
            bw.write("\n");
            bw.write(bet + "\n\n");
            bw.flush();
            bw.close();
        }
        //If no file exists create one to start recording hands
        else {
            File history = new File("handHistory.txt");
            history.createNewFile();
            FileWriter fw = new FileWriter(history);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 2; i++) {
                bw.write(pHand[i].getSuit() + "." + pHand[i].getNum() + " ");
            }
            bw.write("\n");
            for (int i = 0; i < 2; i++) {
                bw.write(cHand[i].getSuit() + "." + cHand[i].getNum() + " ");
            }
            bw.write("\n");
            for (int i = 0; i < 5; i++) {
                bw.write(board[i].getSuit() + "." + board[i].getNum() + " ");
            }
            bw.write("\n");
            bw.flush();
            bw.write(bet + "\n\n");
            bw.close();
        }
    }
    
    //Test
    public static void main(String[] args) throws IOException {
        Card card = new Card(5, 0);
        Card [] board = new Card [5];
        Card [] hands = new Card[2];
        for (int i = 0; i < 5; i++) {
            board[i] = card;
        }
        for (int i = 0; i < 2; i++) {
            hands[i] = card;
        }
        record(hands, hands, board, 100);
    }
}
