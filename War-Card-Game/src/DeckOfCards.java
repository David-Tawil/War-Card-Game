import java.util.ArrayList;
import java.util.Collections;

/**
 * this class represent deck of cards
 */
public class DeckOfCards {
    private static final int cardsInDeck = 52;/*always deck has exactly 52 cards*/
    private ArrayList<Card> deck = new ArrayList<>(cardsInDeck);/*storing the cards in Array List*/

    /**
     * empty constructor to create empty deck
     */
    public DeckOfCards(){

    }

    /**
     * set a deck to be the dealer deck
     */
    public void setDealerDeck(){
        deck.clear();/*be sure the deck is empty at first*/
        /*filling deck with all cards*/
        for(Card.Suit suit: Card.Suit.values())
            for (Card.Face face : Card.Face.values())
                deck.add(new Card(face.ordinal(),face,suit));
        Collections.shuffle(deck);/*shuffle deck*/
    }

    /**
     * deals cards of this deck to 2 other decks
     * @param player1Deck player 1 deck
     * @param player2Deck player 2 deck
     */
    public void dealCards(DeckOfCards player1Deck , DeckOfCards player2Deck){
        player1Deck.clearDeck();
        player2Deck.clearDeck();
        for (int i = 0; !this.isEmpty();i++){
            if(i%2 == 0)
                player1Deck.addCards(this.popTopCard());
            else
                player2Deck.addCards(this.popTopCard());
        }
    }

    /**
     * add cards to the deck - unlimited card objects can be placed as parameter
     * @param cards cards to add
     */
    public void addCards(Card ... cards){
        Collections.addAll(deck, cards);
    }

    /**
     * adding all cards in other deck to this deck
     * @param other deck of cards
     */
    public void addAllCards(DeckOfCards other){
        this.deck.addAll(other.deck);
    }

    /**
     * pop top card from deck and return it too
     * @return top card of the deck
     */
    public Card popTopCard(){
       return deck.remove(0);
    }

    /**
     * only return top card in deck but it still stays in the deck
     * @return top card of the deck
     */
    public Card getTopCard(){
        return deck.get(0);
    }

    /**
     *     check if a deck is empty
     * @return true if deck is empty otherwise false
     */
    public boolean isEmpty(){
        return deck.isEmpty();
    }

    /**
     *     removing all cards from deck so the deck will be empty
     */
    public void clearDeck(){
        this.deck.clear();
    }

    /**
     * check if top card is bigger then top card of other deck
     * @param other deck of cards
     * @return true if the top card of this deck is bigger then the top card of the other deck otherwise false
     */
    public boolean topCardIsBigger(DeckOfCards other){
        return  this.getTopCard().compareTo(other.getTopCard()) > 0;
    }

    /**
     *    check if top card is equal to top card of other deck
     * @param other deck of cards
     * @return true if the top card of this deck is equal to the top card of the other deck otherwise false
     */
    public boolean topCardIsEqual(DeckOfCards other){
        return this.getTopCard().compareTo(other.getTopCard()) == 0;
    }

    /**
     *    checks if this deck has less then 3 cards
     * @return true if the deck has less then 3 cards otherwise false
     */
    public boolean hasLessThenThreeCards() {
        return deck.size() < 3;
    }
}