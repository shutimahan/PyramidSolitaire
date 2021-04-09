package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.io.StringReader;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;


/**
 * Represents the game of Pyramid Solitaire
 * Maintains a state of the main method that hosts the game once initialization occurs
 */
public final class PyramidSolitaire {

  /**
   * The main method of PyramidSolitaire that accepts a command line argument in the form of arguments
   * and initializes a game of either Basic, Relaxed, or Tripeaks solitaire.
   * There is an option to define the number of rows in the pyramid and the number of visible
   * draw cards through R and D as succeeding integers to the game type.
   */
  public static void main(String[] args) {
    Appendable out = System.out;
    Readable in = new InputStreamReader(System.in);
    int defaultR = 7;
    int defaultD = 3;

    if (args.length > 0) {
      String s = args[0];
      PyramidSolitaireModel model =
              PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.get(s));
      if (args.length > 2) {
        try {
          defaultR = Integer.parseInt(args[1]);
          defaultD = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
          System.err.println("Invalid! One of the inputs is not an integer:" + args[1] + ", " + args[2]);
        }
      }
      PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
      control.playGame(model, model.getDeck(), false, defaultR, defaultD);
      //System.out.println(out);

    }
  }
}