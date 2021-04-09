import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;
import cs3500.pyramidsolitaire.model.TripeakPyramidSolitaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test class for the methods in Tripeaks Solitaire
 */
public class TripeakPyramidSolitaireTest {
  private BasicPyramidSolitaire initialized1;
  private BasicPyramidSolitaire preStart;
  private BasicPyramidSolitaire smallGame;
  private List<Card> fraudDeck;
  private List orderedDeck;
  private List<Card> orderedDeck2;


  /**
   * Initializes the objects for testing purposes.
   */
  @Before
  public void initializeGames() {
    fraudDeck = new ArrayList<>();
    orderedDeck = new ArrayList<>();
    orderedDeck2 = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      fraudDeck.add(new Card(12));
    }

    for (int d = 0; d < 2; d++) {
      for (int i = 0; i < 52; i++) {
        orderedDeck.add(new Card(i));
      }
    }

    for (int i = 0; i < 52; i++) {
      orderedDeck2.add(new Card(5 + ((i % 2))));
    }
    preStart = new TripeakPyramidSolitaire();
    initialized1 = new TripeakPyramidSolitaire();
    smallGame = new TripeakPyramidSolitaire();
    initialized1.startGame(orderedDeck, false, 7, 3);
    smallGame.startGame(smallGame.getDeck(), false, 3, 10);
  }

  @Test
  public void getDeck() {
    assertEquals(104, initialized1.getDeck().size());
  }


  @Test(expected = IllegalStateException.class)
  public void testNullDeck() {
    initialized1.startGame(null, false, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFraudDeckNotBigEnough() {
    initialized1.startGame(fraudDeck, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFraudDeckDupe() {
    initialized1.startGame(orderedDeck2, true, 4, 2);
    initialized1.startGame(orderedDeck2, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFraudDeckHasDupesBut104Cards() {
    fraudDeck = new ArrayList<>();
    for (int d = 0; d < 2; d++) {
      for (int i = 0; i < 51; i++) {
        fraudDeck.add(new Card(i));
      }
    }
    fraudDeck.add(new Card(3));
    fraudDeck.add(new Card(3));

    assertEquals(104, fraudDeck.size());
    initialized1.startGame(fraudDeck, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeckHasExtraDupes() {
    fraudDeck = new ArrayList<>();
    for (int d = 0; d < 2; d++) {
      for (int i = 0; i < 52; i++) {
        fraudDeck.add(new Card(i));
      }
    }
    fraudDeck.add(new Card(3));
    fraudDeck.add(new Card(3));

    assertEquals(106, fraudDeck.size());
    initialized1.startGame(fraudDeck, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndexOutRow() {
    initialized1.startGame(orderedDeck, false, -10, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndexOutUpper() {
    initialized1.startGame(orderedDeck, false, 9, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndexOutDraw() {
    initialized1.startGame(orderedDeck, false, 1, -2);
  }

  @Test
  public void testShuffle() {
    initialized1.startGame(orderedDeck, true, 7, 3);
    assertFalse(initialized1.getScore() == 442);
  }

  @Test
  public void testGetCardAt() {
    assertEquals(1, initialized1.getCardAt(0, 0).getValue());
    assertEquals(4, initialized1.getCardAt(1, 0).getValue());
    assertEquals(2, initialized1.getCardAt(0, 3).getValue());
    assertEquals(3, initialized1.getCardAt(0, 6).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtOutOfBounds() {
    assertEquals(1, initialized1.getCardAt(7, 10).getValue());
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveBeforeGameStart() {
    preStart.remove(0, 0);
  }

  @Test
  public void testRemove1() {
    assertEquals(13, initialized1.getCardAt(6, 0).getValue());
    initialized1.remove(6, 0);
  }

  @Test
  public void testRemove2() {
    assertEquals(5, initialized1.getCardAt(6,5).getValue());
    assertEquals(8, initialized1.getCardAt(6,8).getValue());
    initialized1.remove(6, 5, 6, 8);
    assertEquals(null, initialized1.getCardAt(6,5));
    assertEquals(null, initialized1.getCardAt(6,8));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonKing() {
    assertEquals(3, initialized1.getCardAt(6, 3).getValue());
    initialized1.remove(6, 3);
    assertEquals(4, initialized1.getCardAt(6, 4).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCoveredKing() {
    assertEquals(13, initialized1.getCardAt(2, 3).getValue());
    initialized1.remove(2, 3);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNon13Sum() {
    initialized1.remove(6, 3, 6, 2);
    assertEquals(12, initialized1.getCardAt(6, 3).getValue());
    assertEquals(11, initialized1.getCardAt(6, 2).getValue());
    assertEquals(13, initialized1.getCardAt(6, 4).getValue());
    assertEquals(1, initialized1.getCardAt(6, 5).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardAndEmpty() {
    initialized1.remove(6, 3, 6, 5);
    initialized1.remove(6, 4, 6, 5); // Second card is empty.
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCoveredCard() {
    assertEquals(1, initialized1.getCardAt(5, 0).getValue());
    assertEquals(1, initialized1.getCardAt(6, 1).getValue());
    initialized1.remove(5, 0, 6, 1);
  }

  @Test
  public void testRemoveUsingDraw() {
    assertEquals(11, initialized1.getCardAt(6, 11).getValue());
    assertEquals("[K♣, A♦, 2♦]", initialized1.getDrawCards().toString());
    initialized1.removeUsingDraw(2, 6, 11);
    assertEquals("[K♣, A♦, 3♦]", initialized1.getDrawCards().toString());

  }


  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingOutIndex() {
    assertEquals(1, initialized1.getCardAt(6, 1).getValue());
    assertEquals("[K♣, A♦, 2♦]", this.initialized1.getDrawCards().toString());
    initialized1.removeUsingDraw(3, 6, 1);
  }

  @Test
  public void testDiscardDraw() {
    assertEquals("[K♣, A♦, 2♦]", this.initialized1.getDrawCards().toString());
    initialized1.discardDraw(0);
    assertEquals("[A♦, 2♦, 3♦]", this.initialized1.getDrawCards().toString());
  }


  @Test
  public void testGetNum() {
    assertEquals(9, initialized1.getRowWidth(2));
    assertEquals(14, initialized1.getRowWidth(7));
    assertEquals(7, initialized1.getRowWidth(0));
    assertEquals(7, initialized1.getNumRows());
    assertEquals(3, initialized1.getNumDraw());
    assertEquals(-1, preStart.getNumDraw());
    assertEquals(-1, preStart.getNumRows());
  }


  @Test(expected = IllegalStateException.class)
  public void testBeforeGameStart() {
    preStart.getRowWidth(3);
    preStart.getRowWidth(-1);
    preStart.getRowWidth(10);
    preStart.isGameOver();
  }

  @Test
  public void testIsGameOverEmptyPyramid() {
    assertEquals(false, this.initialized1.isGameOver());
    smallGame.removeUsingDraw(1, 2, 4);
    smallGame.removeUsingDraw(1, 2, 3);
    smallGame.removeUsingDraw(1, 2, 2);
    smallGame.removeUsingDraw(1, 2, 1);
    smallGame.removeUsingDraw(1, 2, 0);
    smallGame.removeUsingDraw(1, 1, 3);
    smallGame.removeUsingDraw(1, 1, 2);
    smallGame.removeUsingDraw(1, 1, 1);
    smallGame.removeUsingDraw(1, 1, 0);
    smallGame.removeUsingDraw(1, 0, 2);
    smallGame.removeUsingDraw(1, 0, 1);
    smallGame.removeUsingDraw(1, 0, 0);
    assertEquals(0, smallGame.getScore());
    assertEquals(true, smallGame.isGameOver());

  }

  @Test
  public void testIsGameOverEmptyDraw() {
    for (int i = 0; i < 91; i++) {
      smallGame.discardDraw(0);
    }
    assertEquals("[K♠]", smallGame.getDrawCards().toString());
    assertEquals(false, smallGame.isGameOver());
    smallGame.discardDraw(0);
    assertEquals(true, smallGame.isGameOver());
    assertEquals(78, smallGame.getScore());
  }

  @Test
  public void testGetScore() {
    assertEquals(442, initialized1.getScore());
    initialized1.remove(6, 5, 6, 8);
    assertEquals(429, initialized1.getScore());
  }


  @Test(expected = IllegalStateException.class)
  public void testGetScoreBeforeStart() {
    assertEquals(0, preStart.getScore());
  }



}