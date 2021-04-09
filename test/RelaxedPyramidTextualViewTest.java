import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;
import cs3500.pyramidsolitaire.model.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for the methods in view for Relaxed Solitaire
 */
public class RelaxedPyramidTextualViewTest extends PyramidSolitaireTextualViewTest {

  private BasicPyramidSolitaire basic1 = new RelaxedPyramidSolitaire();
  private BasicPyramidSolitaire initialized1 = new RelaxedPyramidSolitaire();
  private BasicPyramidSolitaire miniPyramid = new RelaxedPyramidSolitaire();

  private PyramidSolitaireTextualView view;
  private PyramidSolitaireTextualView miniView;

  /**
   * Initializes the objects for testing purposes.
   */
  @Before
  public void initializeGames() {
    List<Card> orderedDeck = new ArrayList<>();
    for (int i = 0; i < 52; i++) {
      orderedDeck.add(new Card(i));
    }
    initialized1.startGame(orderedDeck, false, 7, 3);
    view = new PyramidSolitaireTextualView(this.initialized1);
    PyramidSolitaireTextualView viewBasic = new PyramidSolitaireTextualView(this.basic1);

    miniPyramid.startGame(orderedDeck, false, 2, 10);
    miniView = new PyramidSolitaireTextualView(this.miniPyramid);
  }


  @Test
  public void testToString() {
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥", this.view.toString());
    initializeGames();
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥", this.view.toString());

    assertEquals("  A♣\n" +
            "2♣  3♣\n" +
            "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", miniView.toString());
  }


  @Test
  public void testRemove2CardsVisual() {
    initialized1.remove(6, 3, 6, 5);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦      K♦      2♥\n" +
            "Draw: 3♥, 4♥, 5♥", this.view.toString());
  }

  @Test
  public void testRemovePartialCoveredCardVisual() {
    initialized1.startGame(initialized1.getDeck(), false, 8, 3);
    view = new PyramidSolitaireTextualView(this.initialized1);
    initialized1.remove(7, 0, 7, 7);
    initialized1.remove(6, 0, 7, 1);
    assertEquals("              A♣\n" +
            "            2♣  3♣\n" +
            "          4♣  5♣  6♣\n" +
            "        7♣  8♣  9♣  10♣\n" +
            "      J♣  Q♣  K♣  A♦  2♦\n" +
            "    3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "      10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "        5♥  6♥  7♥  8♥  9♥\n" +
            "Draw: J♥, Q♥, K♥", this.view.toString());
  }

  @Test
  public void testGameOverVisual() {
    initialized1.startGame(initialized1.getDeck(), false, 8, 3);
    view = new PyramidSolitaireTextualView(this.initialized1);
    initialized1.remove(7, 0, 7, 7);
    initialized1.remove(7, 2, 7, 5);
    initialized1.remove(7, 3, 7, 4);
    initialized1.remove(6, 4);
    initialized1.discardDraw(0);
    initialized1.removeUsingDraw(2, 6, 3);
    initialized1.removeUsingDraw(2, 6, 2);
    initialized1.discardDraw(0);
    initialized1.discardDraw(0);
    initialized1.removeUsingDraw(1, 7, 6);
    initialized1.remove(6, 0, 7, 1);
    initialized1.remove(5, 0, 6, 1);
    for (int i = 0; i < 10; i++) {
      initialized1.discardDraw(0);
    }
    assertEquals("Game over. Score: 127", this.view.toString());

    assertEquals(true, initialized1.isGameOver());
  }
}
















