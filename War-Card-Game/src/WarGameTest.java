import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarGameTest {

    public static void main(String[] args) {
        DeckOfCards dealer = new DeckOfCards();//dealer deck
        dealer.setDealerDeck(); // initialize dealer deck
        DeckOfCards player1Deck = new DeckOfCards();
        DeckOfCards player2Deck = new DeckOfCards();

        dealer.dealCards(player1Deck,player2Deck); // deal cards to 2 players
        Object [] options = {"Start new game","Get stats","Quit"}; // option buttons for the dialog pane
        int result = JOptionPane.showOptionDialog(null,"Choose an option","War game",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,null);
        if(result == JOptionPane.YES_OPTION)//if the user choose start new game
            warGame(player1Deck,player2Deck,true);//game procedure
        else if(result == JOptionPane.NO_OPTION) // if user choose to get stats
            stats(Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the number of games you want to simulate","War game",JOptionPane.PLAIN_MESSAGE)));

    }

    /**
     * Execute War game procedure
     * @param p1Deck player 1 deck
     * @param p2Deck player 2 deck
     * @param showMassages show massage in dialog box or not
     * @return total moves in the game
     */
    private static int warGame(DeckOfCards p1Deck,DeckOfCards p2Deck, boolean showMassages){
        int moves ; // total moves in game
        boolean war = false;
        StringBuilder msg; // for building the massage
        for (moves = 0;!p1Deck.isEmpty() && !p2Deck.isEmpty(); moves++,war = false) {
            msg = new StringBuilder();
            msg.append("Move number: ").append(moves).append("\n").append(FacedUpCards(p1Deck, p2Deck));
            if(p1Deck.topCardIsBigger(p2Deck)){
                msg.append("\tPlayer 1 wins the draw !\n");
                p1Deck.addCards(p1Deck.popTopCard(),p2Deck.popTopCard());//adding the top cards to the winner deck
            }
            else if (p2Deck.topCardIsBigger(p1Deck)){
                msg.append("\tPlayer 2 wins the draw !\n");
                p2Deck.addCards(p2Deck.popTopCard(),p1Deck.popTopCard());
            }
            else {
                msg.append("\tWAR!!!\n");
                war = true;
            }
            if (showMassages)
                boxMsg(msg);//show box dialog with data of the current move
            if(war)
                moves+= warMove(p1Deck,p2Deck,showMassages);
        }
        if (showMassages)
            winnerMassage(p1Deck);//show box dialog with winner massage
        return moves;
    }

    /**
     * Execute War move procedure
     * @param p1Deck deck of player1
     * @param p2Deck deck of player 2
     * @param showMassages show massages or not
     * @return total moves of the war
     */
    private static int warMove(DeckOfCards p1Deck, DeckOfCards p2Deck, boolean showMassages ){
        DeckOfCards p1WarDeck = new DeckOfCards();// deck for storing all cards that were popped during the war by player 1
        DeckOfCards p2WarDeck = new DeckOfCards();
        boolean warEnd = false;
        StringBuilder msg = new StringBuilder();
        int moves = 0;
        while (!warEnd){
            msg = new StringBuilder();
            /*if the player remains with less then 3 cards he losses immediately */
            if(p1Deck.hasLessThenThreeCards()){
                p1Deck.clearDeck();
                if (showMassages)
                    boxMsg(msg.append("\tplayer 1 runs out of cards...\n"));
                return moves;
            }
            else if(p2Deck.hasLessThenThreeCards()){
                p2Deck.clearDeck();
                if (showMassages)
                    boxMsg(msg.append("\tplayer 2 runs out of cards...\n"));
                return moves;
            }
            else {
                p1WarDeck.addCards(p1Deck.popTopCard(),p1Deck.popTopCard());//adding the faced down cards
                p2WarDeck.addCards(p2Deck.popTopCard(),p2Deck.popTopCard());
                msg.append("Player 1 face down cards: XXX  XXX\nPlayer 2 face down cards: XXX  XXX\n").append(FacedUpCards(p1Deck, p2Deck));
                if(p1Deck.topCardIsEqual(p2Deck)){//if war again
                    msg.append("\tWar again!!!\n");
                    if (showMassages)
                        JOptionPane.showMessageDialog(null,msg,"War Game",JOptionPane.PLAIN_MESSAGE);
                    p1WarDeck.addCards(p1Deck.popTopCard());//adding cards to war decks
                    p2WarDeck.addCards(p2Deck.popTopCard());
                }
                else//if war finish
                    warEnd = true;
                moves++;
            }
        }
        if (p1Deck.topCardIsBigger(p2Deck)) {
            msg.append("\tPlayer 1 wins the war!!\n");
            p1Deck.addAllCards(p1WarDeck);
            p1Deck.addCards(p1Deck.popTopCard(),p2Deck.popTopCard());
            p1Deck.addAllCards(p2WarDeck);
        }
        else {
            msg.append("\tPlayer 2 wins the war!!\n");
            p2Deck.addAllCards(p2WarDeck);
            p2Deck.addCards(p2Deck.popTopCard(),p1Deck.popTopCard());
            p2Deck.addAllCards(p1WarDeck);
        }
        if (showMassages)
            JOptionPane.showMessageDialog(null,msg,"War Game",JOptionPane.PLAIN_MESSAGE);
        return moves;
    }
    /*return string of the cards the player faced up*/
    private static String FacedUpCards(DeckOfCards p1 , DeckOfCards p2){
        return  String.format("Player 1 turns up: %s\nPlayer 2 turns up: %s\n",p1.getTopCard(),p2.getTopCard());
    }

    /**
     * this will run war games and print statistical result in a box dialog
     * @param games the number of games to run
     */
    private static void stats(int games){
        DeckOfCards dealer = new DeckOfCards();
        DeckOfCards player1Deck = new DeckOfCards();
        DeckOfCards player2Deck = new DeckOfCards();
        int p1Won = 0 ,p2Won = 0 ;
        List <Integer> movesList = new ArrayList<>();//store number of moves of every game
        for (int i=0; i<games ; i++){// run games
            dealer.setDealerDeck();
            dealer.dealCards(player1Deck,player2Deck);
            int moves = warGame(player1Deck,player2Deck,false);
            p1Won += player1Deck.isEmpty() ? 0 : 1;
            p2Won += player1Deck.isEmpty() ? 1 : 0;
            movesList.add(moves);
        }
        Long sum = 0L; // to calculate the average
        for (Integer moves : movesList)
            sum += moves;
        String msg = String.format("\n\nSimulating %d games to get some statistical data\nPlayer 1 won %d games\nPlayer 2 won %d games\nMin moves:%d\nMax moves:%d\nAverage moves:%d\n",
                games,p1Won,p2Won, Collections.min(movesList),Collections.max(movesList),sum/movesList.size());
        JOptionPane.showMessageDialog(null,msg,"War game stats",JOptionPane.PLAIN_MESSAGE);

    }

    /**
     * print a winner massage in a box dialog
     * @param p1 deck of player 1
     */
    private static void winnerMassage(DeckOfCards p1){
        StringBuilder msg = new StringBuilder(String.format("\nPlayer %d runs out of cards...\n\nPlayer %d is the winner of the game!!!\n",p1.isEmpty()? 1 :2,p1.isEmpty() ? 2 :1));
        boxMsg(msg);
    }

    /**
     * prints a massage in a box dialog
     * @param msg the massage
     */
    private static void boxMsg(Object msg){
        Object[] options = {"Next move","Quit"};
        int result = JOptionPane.showOptionDialog(null,msg,"War Game",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,null);
        if (result == JOptionPane.NO_OPTION)
            System.exit(0);
    }
}