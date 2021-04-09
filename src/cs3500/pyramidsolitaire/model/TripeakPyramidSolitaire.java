package cs3500.pyramidsolitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;

/**
 * Represents a implementation of a PyramidSolitaireModel that has 3 different peaks
 * Identical to the basic/default version of the game except the difference in shape.
 */
public class TripeakPyramidSolitaire extends BasicPyramidSolitaire {


  // constructor
  public TripeakPyramidSolitaire() {
    super();
  }

  // The valid deck for tripeaks is a double deck with 104 cards
  @Override
  public List getDeck() {
    List<Card> tempDeck = new ArrayList<Card>();
    for (int d = 0; d < 2; d++) {
      for (int i = 0; i < 52; i++) {
        tempDeck.add(new Card(i));
      }
    }
    return tempDeck;
  }

  @Override
  public void startGame(List deck, boolean shouldShuffle, int numRows, int numDraw) {
    if (deck == null) {
      throw new IllegalStateException("Null deck");
    } else if (deck.size() != 104 || !onlyTwoIdentical(deck)) {
      throw new IllegalArgumentException("Invalid deck");
    } else if (shouldShuffle) {
      this.deck = deck;
      Collections.shuffle(this.deck);
    } else {
      this.deck = deck;
    }
    if (numRows < 1 || numRows > 8) {
      throw new IllegalArgumentException("Invalid number of rows");
    }
    if (numDraw < 0) {
      throw new IllegalArgumentException("Invalid number of cards in the draw pile");
    }
    this.numRows = numRows;
    this.numDraw = numDraw;
    this.pyramid = new Card[getNumRows()][getRowWidth(numRows)];

    index = 0;
    makeTopHalf();
    makeBottomHalf();

    this.drawDeck = this.initialDrawCards();
  }

  // Determines if there is exactly 2 copies of any unique card to a total of 104 cards
  private boolean onlyTwoIdentical(List<Card> deck) {
    List<Card> dupeList = new ArrayList<>();
    for (int i = 0; i < deck.size(); i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        if (deck.get(i).equals(deck.get(j))) {
          dupeList.add(deck.get(i));
        }
      }
    }
    return noDupes(dupeList);
  }


  // Method to make the top half of the pyramid
  private void makeTopHalf() {
    for (int row = 0; row < (getNumRows() / 2); row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        // the 3 peaks of the pyramid
        if (row == 0 && col % (getNumRows() / 2) == 0) {
          initializePyramid(row, col);
        } else if (row > 0 && col > 0 && this.pyramid[row - 1][col - 1] != null) { // fills in cards
          // supporting peaks on one side
          initializePyramid(row, col);
        } else if (row > 0 && this.pyramid[row - 1][col] != null) {
          initializePyramid(row, col);
        } else { //not part of pyramid if not within conditions above
          this.pyramid[row][col] = null;
        }
      }
    }
  }

  // Method to make the top half of the pyramid
  private void makeBottomHalf() {
    for (int row = getNumRows() / 2; row < getNumRows(); row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        initializePyramid(row, col);
      }
    }
  }

  @Override
  public int getRowWidth(int row) {
    int result;
    if (this.numRows == -1) {
      throw new IllegalStateException("Cannot get the width of the row since the game hasn't started");
    } else if (row < 0 || row > 8) {
      throw new IllegalArgumentException("Invalid number of rows");
    } else {
      result = row + (getNumRows() + (1 - (getNumRows() % 2)));
    }
    return result;
  }

}
