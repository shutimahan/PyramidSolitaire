package cs3500.pyramidsolitaire.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;


/**
 * Represents an implementation of the PyramidSolitaireModel with a relaxed ruleset.
 * Basically identical to the basic/default version where the only difference is when removing 2 cards from the pyramid,
 * this version will allow the move if one of the cards is partially covered by the other card being removed.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  //constructor
  public RelaxedPyramidSolitaire() {
    super();
  }

  // Method to determine if either cards are covering each other or are partially covered.
  // Returns true if the card is uncovered or covered by card being removed
  @Override
  protected boolean isBothUncovered(int row1, int card1, int row2, int card2) {
    return (isUncovered(row1, card1) && isUncovered(row2, card2))
            // card 1 covers card 2, but card 1 is uncovered
            || (row2 < getNumRows() && oneCoversAnother(row1, card1, row2, card2))
            // card 2 covers card 1, but card 2 is uncovered
            || (row1 < getNumRows() && oneCoversAnother(row2, card2, row1, card1));
  }

  // Returns true if the other given card is the only thing that covers this card
  private boolean oneCoversAnother(int row1, int card1, int row2, int card2) {
    return isUncovered(row1, card1)
            && (getCardAt(row1, card1).equals(getCardAt(row2 + 1, card2))
            && getCardAt(row2 + 1, card2 + 1) == null
            || getCardAt(row1, card1).equals(getCardAt(row2 + 1, card2 + 1))
            && getCardAt(row2 + 1, card2) == null);
  }

  // Determines if there are any moves left for taking
  @Override
  protected boolean movePossible() {
    List<Card> fulls = new ArrayList<>();
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        if (isPartialCover(row, col) && row + 1 < numRows) {
          if (getCardAt(row + 1, col) == null && sumsTo13(getCardAt(row, col),
                  getCardAt(row + 1, col + 1))) {
            return false;
          } else if (getCardAt(row + 1, col + 1) == null && sumsTo13(getCardAt(row, col),
                  getCardAt(row + 1, col))) {
            return false;
          }
        } else if (isUncovered(row, col)) {
          fulls.add(pyramid[row][col]);
        }
      }
    }
    for (int i = 0; i < fulls.size(); i++) {
      for (int j = i + 1; j < fulls.size(); j++) {
        if (sumsTo13(fulls.get(i), fulls.get(j))) {
          return false;
        }
      }
    }

    return true;
  }

  private boolean isPartialCover(int row, int col) {
    return row + 1 < numRows && ((getCardAt(row + 1, col) == null
            && getCardAt(row + 1, col + 1) != null)
            || (getCardAt(row + 1, col) != null
            && getCardAt(row + 1, col + 1) == null));
  }


}
