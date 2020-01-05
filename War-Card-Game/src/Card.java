public class Card implements Comparable<Card>{

    public  enum Face  {Deuce, Three , Four , Five, Six, Seven, Eight, Nine,
             Ten, Jack, Queen, King, Ace}
    public  enum Suit {Hearts, Diamonds, Clubs, Spades}

    private final int faceValue;
    private final Face face;
    private final Suit suit;
    /*constructor*/
    public Card(int faceValue, Face face, Suit suit) {
        this.faceValue = faceValue;
        this.face = face;
        this.suit = suit;
    }
    public Face getFace(){
        return face;
    }
    public Suit getSuit(){
        return suit;
    }
    @Override
    public String toString() {
        return face +" of " + suit;
    }

    /*compere 2 cards by their face value*/
    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.faceValue, other.faceValue);
    }
}