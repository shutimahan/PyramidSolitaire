package cs3500.pyramidsolitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents a implementation of a Basic model of PyramidSolitaireModel, and maintains the state of
 * the card, pyramid, deck, and draw pile as the user interacts with the game.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel {

  protected List<Card> deck; // standard deck with no joker
  protected List<Card> drawDeck = new ArrayList<Card>();
  protected Card[][] pyramid; // the cards in the pyramid
  protected int numRows;
  protected int numDraw;
  protected int index = 0;


  /**
   * Constructor for BasicPyramidSolitaire.
   */
  public BasicPyramidSolitaire() {
    this.numDraw = -1;
    this.numRows = -1;
    List<Card> deck = new ArrayList<>();
    List<Card> drawDeck = new ArrayList<>();
  }


  /**
   * Method to produce a standard deck of 52 cards with no joker included.
   * There are 13 cards for each of the 4 suites.
   *
   * @return tempDeck returns a list of 52 cards
   */
  @Override
  public List getDeck() {
    List tempDeck = new ArrayList();
    for (int i = 0; i < 52; i++) {
      tempDeck.add(new Card(i));
    }
    return tempDeck;
  }


  @Override
  public void startGame(List deck, boolean shouldShuffle, int numRows, int numDraw) {
    if (deck == null) {
      throw new IllegalStateException("Null deck");
    } else if (deck.size() != 52 || !noDupes(deck)) {
      throw new IllegalArgumentException("Invalid deck");
    } else if (shouldShuffle) {
      this.deck = deck;
      Collections.shuffle(this.deck);
    } else {
      this.deck = deck;
    }
    if (numRows < 1 || numRows > 9) {
      throw new IllegalArgumentException("Number of rows should be between 1-9 inclusive");
    }
    if (numDraw < 0) {
      throw new IllegalArgumentException("There must be at least one draw card");
    }
    this.numRows = numRows;
    this.numDraw = numDraw;
    this.pyramid = new Card[numRows][numRows];
    index = 0;
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col <= row; col++) {
        initializePyramid(row, col);
      }
    }
    this.drawDeck = this.initialDrawCards();

  }

  // Use the given row and col to create a card at the location from the deck using index
  protected void initializePyramid(int row, int col) {
    this.pyramid[row][col] = this.deck.get(index);
    index++;
  }

  // Check if there are no duplicates in the deck
  protected boolean noDupes(List<Card> deck) {
    Set<Card> dupes = new HashSet<Card>();
    dupes.addAll(deck);
    return dupes.size() == 52;
  }

  // Create the draw deck for the draw cards
  protected List initialDrawCards() {
    List updateList = new ArrayList();
    if (numDraw <= 0) {
      throw new IllegalArgumentException("Game hasn't started");
    } else {
      for (int i = 0; i < getNumDraw(); i++) {
        if (index < this.deck.size()) {
          updateList.add(this.deck.get(index));
        }
        index++;
      }
    }
    return updateList;
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    if (getNumRows() == -1) {
      throw new IllegalStateException("Cannot remove 1 card because game hasn't started");
    } else if (getCardAt(row, card).getValue() != 13) {
      throw new IllegalArgumentException("Card must be King to remove a single card");
    } else if (!isUncovered(row, card)) {
      throw new IllegalArgumentException("Card is covered and therefore cannot be removed");
    } else {
      this.pyramid[row][card] = null;
    }
  }

  /**
   * Use the given two card positions to execute a pairwise move
   *
   * @param row1  row of first card position where 0 is the top of the pyramid
   * @param card1 card of first card position where 0 is the far left
   * @param row2  row of second card position
   * @param card2 card of second card position
   * @throws IllegalArgumentException if move is invalid
   * @throws IllegalStateException    if game hasn't started yet
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    if (getNumRows() == -1) {
      throw new IllegalStateException("Cannot remove 2 cards since game hasn't started yet");
    } else if (this.getCardAt(row1, card1) == null || this.getCardAt(row2, card2) == null) {
      throw new IllegalArgumentException("Null card(s)");
    } else if (!sumsTo13(this.getCardAt(row1, card1), (this.getCardAt(row2, card2)))) {
      throw new IllegalArgumentException("Doesn't sum to 13");
    } else if (!isBothUncovered(row1, card1, row2, card2)) {
      throw new IllegalArgumentException("Card is covered and cannot be removed");
    } else {
      this.pyramid[row1][card1] = null;
      this.pyramid[row2][card2] = null;
    }
  }

  // Determines if any of the two cards are covered
  // Only returns true if BOTH of the cards are uncovered
  protected boolean isBothUncovered(int row1, int card1, int row2, int card2) {
    return (isUncovered(row1, card1) && isUncovered(row2, card2));
  }

  /**
   * Checks if the two cards sum to 13
   *
   * @param c1  first card
   * @param c2 second card
   * @return True if they sum to 13
   */
  protected boolean sumsTo13(Card c1, Card c2) {
    if (c1 == null && c2 == null) {
      return false;
    } else if (c1 == null) {
      return c2.getValue() == 13;
    } else if (c2 == null) {
      return c1.getValue() == 13;
    } else {
      return (c1.getValue() + c2.getValue() == 13);
    }
  }

  /**
   * Determines whether the given card is uncovered.
   *
   * @param row  the number of row the card is at
   * @param col the number of column the card is at
   * @return true if the card is completely uncovered
   */
  protected boolean isUncovered(int row, int col) {
    return (row + 1 == this.getNumRows()) ||
            (getCardAt(row + 1, col) == null
                    && getCardAt(row + 1, col + 1) == null);
  }


  @Override
  public void removeUsingDraw(int drawIndex, int row, int col) throws IllegalStateException {
    if (getNumRows() == -1) {
      throw new IllegalStateException("Cannot remove using draw since game hasn't started");
    } else if (drawIndex >= getNumDraw()) {
      throw new IllegalArgumentException("Index out of bounds");
    } else if (getCardAt(row, col) == null) {
      throw new IllegalArgumentException("Null card");
    } else if (this.drawDeck.get(drawIndex) == null) {
      throw new IllegalArgumentException("Null draw card");
    } else if (!sumsTo13(getCardAt(row, col), this.drawDeck.get(drawIndex))) {
      throw new IllegalArgumentException("Doesn't add up to 13");
    } else if (!isUncovered(row, col)) {
      throw new IllegalArgumentException("Card is not uncovered");
    } else if (drawIndex < 0 || drawIndex >= drawDeck.size()) {
      throw new IllegalArgumentException("Invalid drawIndex");
    } else {
      this.pyramid[row][col] = null;
      removeCardFromPile(drawIndex);
    }

  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    if (getNumRows() == -1) {
      throw new IllegalStateException("Cannot discardDraw since game hasn't started");
    } else if (drawDeck.size() == 0) {
      throw new IllegalArgumentException("No cards left in the deck for the draw cards");
    } else if (drawIndex < 0 || drawIndex >= drawDeck.size()) {
      throw new IllegalArgumentException("Invalid drawIndex");
    } else {
      removeCardFromPile(drawIndex);
    }
  }

  // Method to remove a card from the drawDeck then immediately replacing it if possible
  private void removeCardFromPile(int drawIndex) {
    this.drawDeck.remove(drawIndex);
    if (index < deck.size()) {
      this.drawDeck.add(this.deck.get(index));
    }
    index++;
  }

  @Override
  public int getNumRows() {
    return this.numRows;
  }

  @Override
  public int getNumDraw() {
    return this.numDraw;
  }

  @Override
  public int getRowWidth(int row) {
    int result;
    if (this.numRows == -1) {
      throw new IllegalStateException("Cannot get the width of the row since the game has not started");
    } else if (row < 0 || row > 10) {
      throw new IllegalArgumentException("Invalid number of rows");
    } else {
      result = row += 1;
    }
    return result;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (getNumRows() == -1) {
      throw new IllegalArgumentException("Unable to decide if game is over since it hasn't started");
    } else {
      return (this.summation() == 0 || this.getDrawCards().size() == 0 && this.movePossible());
    }
  }

  // determines if there are any moves left for taking
  protected boolean movePossible() {
    List<Card> numbers = new ArrayList<>();
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        if (isUncovered(row, col)) {
          numbers.add(this.pyramid[row][col]);
        }
      }
    }
    for (int i = 0; i < numbers.size(); i++) {
      for (int j = i + 1; j < numbers.size(); j++) {
        if (sumsTo13(numbers.get(i), numbers.get(j))) {
          return false;
        }
      }
    }
    return true;
  }

  private int summation() {
    int sum = 0;
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        if (this.pyramid[row][col] != null) {
          sum += this.pyramid[row][col].getValue();
        }
      }
    }
    return sum;
  }


  /**
   * Returns the current score which is the sum of the values of the cards remaining in the pyramid
   *
   * @return the score
   * @throws IllegalStateException if game hasn't started yet
   */
  @Override
  public int getScore() throws IllegalStateException {
    if (this.getNumRows() == -1) {
      throw new IllegalStateException("Can't get score since game hasn't started yet");
    }
    return summation();
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    if (this.getNumRows() == -1) {
      throw new IllegalStateException("No card to retrieve since game hasn't started");
    } else if (card >= this.getRowWidth(row) || card < 0) {
      throw new IllegalArgumentException("Invalid card index");
    } else if (row >= this.getNumRows() || row < 0) {
      throw new IllegalArgumentException("Invalid row index");
    } else if (this.pyramid[row][card] == null) {
      return null;
    }
    return new Card(this.pyramid[row][card].getValue(),
            this.pyramid[row][card].getSuite());
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    if (this.getNumRows() == -1) {
      throw new IllegalStateException("Cannot get the draw cards since game hasn't started");
    }
    return new ArrayList<Card>(this.drawDeck);
  }
}