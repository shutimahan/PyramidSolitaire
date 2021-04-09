package cs3500.pyramidsolitaire.view;

import java.io.IOException;
import java.util.List;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;


/**
 * Represents the formatted string version of the game board
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable out;

  // constructor
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  // constructor
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    out = null;
  }

  @Override
  public void render() throws IOException {
    out.append(this.toString());
  }


  /**
   * Generates the board as a string
   *
   * @return the board as a string
   */
  @Override
  public String toString() {
    String board = "";
    if (model.getNumRows() == -1) {
      return board;
    } else if (model.isGameOver() && model.getScore() == 0) {
      return "You win! :D";
    } else if (model.isGameOver()) {
      return "Game over. Score: " + model.getScore();
    } else {
      for (int row = 0; row < model.getNumRows(); row++) {
        StringBuilder rowBuilder = new StringBuilder();
        for (int i = row + 1; i < model.getNumRows(); i++) {
          rowBuilder.append("  ");
        }
        for (int col = 0; col < model.getRowWidth(row); col++) {
          if (model.getCardAt(row, col) == null) {
            rowBuilder.append("   ");
          } else {
            rowBuilder.append(this.model.getCardAt(row, col).toString());
          }
          if (col < model.getRowWidth(row)) {
            rowBuilder.append(" ");
            if (model.getCardAt(row, col) != null
                    && this.model.getCardAt(row, col).toString().length() == 2) {
              rowBuilder.append(" ");
            }
          }
        }
        int len = rowBuilder.length();
        for (; len > 0; len--) {
          if (!Character.isWhitespace(rowBuilder.charAt(len - 1))) {
            break;
          }
        }
        board += rowBuilder.substring(0, len);
        board += "\n";
      }
      board += String.format("Draw: %s", listToString(model.getDrawCards())).trim();
      return board;
    }
  }

  private String listToString(List entry) {
    String drawList = "";
    for (int i = 0; i < entry.size(); i++) {
      if (i != entry.size() - 1) {
        drawList += entry.get(i) + ", ";
      } else {
        drawList += entry.get(i);
      }
    }
    return drawList.trim();
  }

}