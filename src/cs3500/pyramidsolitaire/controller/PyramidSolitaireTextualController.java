package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;


/**
 * Represents an implementation of the PyramidSolitaireController that reads the inputs of a Readable object
 * and processes valid commands by returning it in textual view as an Appendable object.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private final Readable rd;
  private final Appendable ap;
  private boolean isGameOver;

  /**
   * Constructor for the textual controller class.
   *
   * @param rd readable input given by commands(listed in the README) and numbers
   * @param ap appendable output
   * @throws IllegalArgumentException if rd or ap is null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
          throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Null parameter");
    } else {
      this.rd = rd;
      this.ap = ap;
      this.isGameOver = true;
    }
  }

  /**
   * Method to play game.
   * Is the main method for processing game states according to given commands.
   *
   * @param model   The type of pyramid solitaire to be played
   * @param deck    The deck of cards to be used in the pyramid
   * @param shuffle True if you would like to shuffle the card deck
   * @param numRows The number of rows in the pyramid
   * @param numDraw The number of visible draw cards in the pyramid
   */
  @Override
  public <Card> void playGame(PyramidSolitaireModel<Card> model, List<Card> deck, boolean shuffle,
                              int numRows, int numDraw) {
    Scanner scan = new Scanner(this.rd);
    if (model == null) {
      throw new IllegalArgumentException("Null model unallowed");
    } else {
      try {
        model.startGame(deck, shuffle, numRows, numDraw);
        this.appendOutModel(model);
      } catch (NullPointerException | IllegalArgumentException n) {
        throw new IllegalStateException("Illegal Argument or IO");
      }
    }
    try {
      while (scan.hasNextLine() && isGameOver && !model.isGameOver()) {
        String currLine = scan.nextLine();
        Scanner scan1 = new Scanner(currLine);
        boolean inputOk = false;
        while (!inputOk && scan1.hasNext()) {
          try {
            switch (scan1.next()) {
              case "q":
              case "Q":
                quitGame(model);
                inputOk = true;
                break;
              case "rm1":
                String isNum1 = retryHelper(scan1);
                String isNum2 = retryHelper(scan1);
                if (isNum1.equals("q") || isNum2.equals("q")) {
                  quitGame(model);
                } else {
                  model.remove(Integer.parseInt(isNum1),
                          Integer.parseInt(isNum2));
                  this.appendOutModel(model);
                }
                inputOk = true;
                break;
              case "rm2":
                isNum1 = retryHelper(scan1);
                isNum2 = retryHelper(scan1);
                String isNum3 = retryHelper(scan1);
                String isNum4 = retryHelper(scan1);
                if (isNum1.equals("q") ||
                        isNum2.equals("q") ||
                        isNum3.equals("q") ||
                        isNum4.equals("q")) {
                  quitGame(model);
                } else {
                  model.remove(Integer.parseInt(isNum1),
                          Integer.parseInt(isNum2),
                          Integer.parseInt(isNum3),
                          Integer.parseInt(isNum4));
                  this.appendOutModel(model);
                }
                inputOk = true;
                break;
              case "rmwd":
                isNum1 = retryHelper(scan1);
                isNum2 = retryHelper(scan1);
                isNum3 = retryHelper(scan1);
                if (isNum1.equals("q") || isNum2.equals("q") || isNum3.equals("q")) {
                  quitGame(model);
                } else {
                  model.removeUsingDraw(Integer.parseInt(isNum1),
                          Integer.parseInt(isNum2),
                          Integer.parseInt(isNum3));
                  this.appendOutModel(model);
                }
                inputOk = true;
                break;
              case "dd":
                isNum1 = retryHelper(scan1);
                if (isNum1.equals("q")) {
                  quitGame(model);
                } else {
                  model.discardDraw(Integer.parseInt(isNum1));
                  this.appendOutModel(model);
                }
                inputOk = true;
                break;
              default:
                this.ap.append("Invalid move. Try another move.\n");
                break;
            }
          } catch (IllegalArgumentException n) {
            this.ap.append("Invalid move\n");
          }
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Readable input has IO exception");
    }
  }

  // quitting the game
  private <Card> void quitGame(PyramidSolitaireModel<Card> model) throws IOException {
    this.isGameOver = false;
    this.ap.append("Game quit!\nState of game when quit:\n");
    this.appendOutModel(model);
    this.ap.append(String.format("Score: %d\n", model.getScore()));
  }

  private String retryHelper(Scanner scan1) throws IOException {
    while (scan1.hasNext()) {
      if (scan1.hasNextInt()) {
        int num1 = scan1.nextInt();
        num1 = num1 - 1;
        if (num1 >= 0) {
          return Integer.toString(num1);
        } else {
          this.ap.append("Invalid input.\n");
        }
      } else if (scan1.hasNext("q") || scan1.hasNext("Q")) {
        return "q";
      } else {
        this.ap.append("Invalid input.\n");
        scan1.next();
      }
    }
    return "";
  }

  private <Card> void appendOutModel(PyramidSolitaireModel<Card> model) {
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model);
    try {
      this.ap.append(String.format("%s\n", view.toString()));
      if (!model.isGameOver() && this.isGameOver) {
        this.ap.append(String.format("Score: %d\n", model.getScore()));
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Exception.");
    }
  }

}
