import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static junit.framework.TestCase.assertEquals;


/**
 * Test class for the methods in view for Basic Solitaire
 */
public class PyramidSolitaireTextualViewTest {

  private BasicPyramidSolitaire basic1 = new BasicPyramidSolitaire();
  private BasicPyramidSolitaire initialized1 = new BasicPyramidSolitaire();
  private BasicPyramidSolitaire miniPyramid = new BasicPyramidSolitaire();

  private PyramidSolitaireTextualView view;
  private PyramidSolitaireTextualView basicView;
  private PyramidSolitaireTextualView miniView;

  /**
   * Initializes the objects
   */
  private void initializeGames() {
    List<Card> orderedDeck = new ArrayList<>();
    for (int i = 0; i < 52; i++) {
      orderedDeck.add(new Card(i));
    }
    initialized1.startGame(orderedDeck, false, 7, 3);
    view = new PyramidSolitaireTextualView(this.initialized1);
    basicView = new PyramidSolitaireTextualView(this.basic1);

    miniPyramid.startGame(basic1.getDeck(), false, 2, 10);
    miniView = new PyramidSolitaireTextualView(this.miniPyramid);
  }


  @Test
  public void testToString() {
    initializeGames();
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
    initializeGames();
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
  public void testRemoveDrawVisual() {
    initializeGames();
    assertEquals("", this.basicView.toString());
    assertEquals(10, initialized1.getCardAt(6, 1).getValue());
    initialized1.removeUsingDraw(0, 6, 1);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦      J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥", view.toString());
    initialized1.removeUsingDraw(0, 6, 0);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "        J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 5♥, 6♥, 7♥", view.toString());

    assertEquals("  A♣\n" +
            "2♣  3♣\n" +
            "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣", miniView.toString());
    miniPyramid.removeUsingDraw(6, 1, 1);
    assertEquals("  A♣\n" +
            "2♣\n" +
            "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, J♣, Q♣, K♣, A♦", miniView.toString());
    miniPyramid.removeUsingDraw(6, 1, 0);
    assertEquals("  A♣\n" +
            "\n" +
            "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, Q♣, K♣, A♦, 2♦", miniView.toString());
    miniPyramid.discardDraw(7);
    assertEquals("  A♣\n" +
            "\n" +
            "Draw: 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, Q♣, A♦, 2♦, 3♦", miniView.toString());

    initializeGames();
    assertEquals(9, initialized1.getCardAt(6, 0).getValue());
    initialized1.removeUsingDraw(1, 6, 0);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "    10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 5♥, 6♥", view.toString());
    initialized1.removeUsingDraw(0, 6, 1);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "        J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 5♥, 6♥, 7♥", view.toString());
  }

  @Test
  public void testGameOverWin() {
    initializeGames();
    miniPyramid.removeUsingDraw(6, 1, 1);
    miniPyramid.removeUsingDraw(6, 1, 0);
    miniPyramid.removeUsingDraw(6, 0, 0);
    assertEquals("You win!", miniView.toString());
  }

  @Test
  public void testGameOverLose() {
    initializeGames();
    for (int i = 0; i < 48; i++) {
      miniPyramid.discardDraw(0);
    }
    Assert.assertEquals("[K♠]", miniPyramid.getDrawCards().toString());
    Assert.assertEquals(false, miniPyramid.isGameOver());
    miniPyramid.discardDraw(0);
    Assert.assertEquals(true, miniPyramid.isGameOver());
    Assert.assertEquals(6, miniPyramid.getScore());
    assertEquals("Game over. Score: 6", miniView.toString());
  }

}
