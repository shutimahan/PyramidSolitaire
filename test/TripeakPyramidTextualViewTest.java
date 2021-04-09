import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;
import cs3500.pyramidsolitaire.model.TripeakPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static junit.framework.TestCase.assertEquals;

/**
 * Test class for the methods in view for Tripeak Solitaire
 */
public class TripeakPyramidTextualViewTest extends PyramidSolitaireTextualViewTest {

  private BasicPyramidSolitaire basic1 = new TripeakPyramidSolitaire();
  private BasicPyramidSolitaire initialized1 = new TripeakPyramidSolitaire();
  private BasicPyramidSolitaire miniPyramid = new TripeakPyramidSolitaire();
  private BasicPyramidSolitaire shuffledPyramid = new TripeakPyramidSolitaire();

  private PyramidSolitaireTextualView view;
  private PyramidSolitaireTextualView miniView;


  /**
   * Initializes the objects for testing purposes.
   */
  @Before
  public void initializeGames() {
    List<Card> orderedDeck = new ArrayList<>();

    initialized1.startGame(initialized1.getDeck(), false, 7, 3);
    view = new PyramidSolitaireTextualView(this.initialized1);
    PyramidSolitaireTextualView viewBasic = new PyramidSolitaireTextualView(this.basic1);

    miniPyramid.startGame(miniPyramid.getDeck(), false, 3, 10);
    miniView = new PyramidSolitaireTextualView(this.miniPyramid);

    shuffledPyramid.startGame(shuffledPyramid.getDeck(), true, 7, 3);
    PyramidSolitaireTextualView viewShuffled = new PyramidSolitaireTextualView(shuffledPyramid);

  }


  @Test
  public void testToString() {
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦", this.view.toString());
    initializeGames();
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦", this.view.toString());

    assertEquals("    A♣  2♣  3♣\n" +
            "  4♣  5♣  6♣  7♣\n" +
            "8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦", miniView.toString());
  }


  @Test
  public void testRemoveUsingDrawVisual() {
    assertEquals("    A♣  2♣  3♣\n" +
            "  4♣  5♣  6♣  7♣\n" +
            "8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦", miniView.toString());
    miniPyramid.removeUsingDraw(1, 2, 4);
    assertEquals("    A♣  2♣  3♣\n" +
            "  4♣  5♣  6♣  7♣\n" +
            "8♣  9♣  10♣ J♣\n" +
            "Draw: K♣, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦", this.miniView.toString());
  }

  @Test
  public void testRemove2Visual() {
    initialized1.remove(6, 5, 6, 8);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣      6♣  7♣      9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦", this.view.toString());
    initialized1.remove(6, 1, 6, 12);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠      2♣  3♣  4♣      6♣  7♣      9♣  10♣ J♣\n" +
            "Draw: K♣, A♦, 2♦", this.view.toString());

  }

  @Test
  public void testRemove1Visual() {
    initialized1.remove(6, 0);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "    A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦", view.toString());
  }

  @Test
  public void testWin() {
    assertEquals("    A♣  2♣  3♣\n" +
            "  4♣  5♣  6♣  7♣\n" +
            "8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦", miniView.toString());
    miniPyramid.removeUsingDraw(1, 2, 4);
    miniPyramid.removeUsingDraw(1, 2, 3);
    miniPyramid.removeUsingDraw(1, 2, 2);
    miniPyramid.removeUsingDraw(1, 2, 1);
    miniPyramid.removeUsingDraw(1, 2, 0);
    miniPyramid.removeUsingDraw(1, 1, 3);
    miniPyramid.removeUsingDraw(1, 1, 2);
    miniPyramid.removeUsingDraw(1, 1, 1);
    miniPyramid.removeUsingDraw(1, 1, 0);
    miniPyramid.removeUsingDraw(1, 0, 2);
    miniPyramid.removeUsingDraw(1, 0, 1);
    miniPyramid.removeUsingDraw(1, 0, 0);
    assertEquals("You win!", miniView.toString());

  }

}