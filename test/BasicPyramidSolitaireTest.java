import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.Card;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the methods in Basic Solitaire
 */
public class
BasicPyramidSolitaireTest {

  private BasicPyramidSolitaire basic1 = new BasicPyramidSolitaire();
  private BasicPyramidSolitaire initialized1 = new BasicPyramidSolitaire();
  private BasicPyramidSolitaire initialized2 = new BasicPyramidSolitaire();
  private BasicPyramidSolitaire miniGame = new BasicPyramidSolitaire();
  private List<Card> invalidDeck;
  private List<Card> orderedDeck;
  private List<Card> orderedDeck1;


  /**
   * Initializes the objects
   */
  @Before
  public void initializeGames() {
    invalidDeck = new ArrayList<>();
    orderedDeck = new ArrayList<>();
    orderedDeck1 = new ArrayList<>();
    orderedDeck = initialized1.getDeck();
    initialized1.startGame(orderedDeck, false, 7, 3);


    for (int i = 0; i < 10; i++) {
      invalidDeck.add(new Card(12));
    }

    for (int i = 0; i < 52; i++) {
      orderedDeck1.add(new Card(5 + ((i % 2))));
    }

    miniGame.startGame(orderedDeck, false, 2, 10);


  }

  @Test
  public void testGetNumRows() {
    assertEquals(7, initialized1.getNumRows());
    assertEquals(7, initialized1.getNumRows());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRows() {
    initialized2.startGame(orderedDeck, false, 0, 3);
  }

  @Test
  public void testGetDeck() {
    assertEquals(52, basic1.getDeck().size());
    assertEquals(52, initialized1.getDeck().size());
  }

  @Test(expected = IllegalStateException.class)
  public void testNullDeck() {
    basic1.startGame(null, false, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFraudDeckNotBigEnough() {
    basic1.startGame(invalidDeck, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFraudDeckHasDupes() {
    for (int i = 0; i < 52; i++) {
      invalidDeck.add(new Card(12));
    }

    basic1.startGame(invalidDeck, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDeckDupe() {
    basic1.startGame(orderedDeck1, true, 4, 2);
    basic1.startGame(orderedDeck1, false, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndexOutRow() {
    basic1.startGame(orderedDeck, false, -10, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIndexOutDraw() {
    basic1.startGame(orderedDeck, false, 1, -2);
  }

  @Test
  public void testGetCardAt() {
    assertEquals(1, initialized1.getCardAt(0, 0).getValue());
    assertEquals(2, initialized1.getCardAt(1, 0).getValue());
    assertEquals(3, initialized1.getCardAt(1, 1).getValue());
    assertEquals(9, initialized1.getCardAt(6, 0).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardAtOutOfBounds() {
    assertEquals(1, initialized1.getCardAt(7, 10).getValue());
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveBeforeGameStart() {
    basic1.remove(0, 0);
  }

  @Test
  public void testRemove1() {
    assertEquals(13, initialized1.getCardAt(6, 4).getValue());
    initialized1.remove(6, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonKing() {
    assertEquals(12, initialized1.getCardAt(6, 3).getValue());
    initialized1.remove(6, 3);
    assertEquals(12, initialized1.getCardAt(6, 4).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCoveredKing() {
    assertEquals(13, initialized1.getCardAt(4, 2).getValue());
    initialized1.remove(4, 2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNon13Sum() {
    initialized1.remove(6, 3, 6, 2);
    assertEquals(12, this.initialized1.getCardAt(6, 3).getValue());
    assertEquals(11, this.initialized1.getCardAt(6, 2).getValue());
    assertEquals(13, this.initialized1.getCardAt(6, 4).getValue());
    assertEquals(1, this.initialized1.getCardAt(6, 5).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCardAndEmpty() {
    initialized1.remove(6, 3, 6, 5);
    initialized1.remove(6, 4, 6, 5); // Second card is empty.
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCoveredCard() {
    assertEquals(3, initialized1.getCardAt(5, 0).getValue());
    assertEquals(10, initialized1.getCardAt(6, 1).getValue());
    initialized1.remove(5, 0, 6, 1);
  }

  @Test
  public void testRemove2() {
    assertEquals(12, this.initialized1.getCardAt(6, 3).getValue());
    assertEquals(1, this.initialized1.getCardAt(6, 5).getValue());
    initialized1.remove(6, 3, 6, 5);
  }

  @Test
  public void testRemoveUsingDraw() {
    assertEquals(10, this.initialized1.getCardAt(6, 1).getValue());
    assertEquals("[3♥, 4♥, 5♥]", this.initialized1.getDrawCards().toString());
    initialized1.removeUsingDraw(0, 6, 1);
    assertEquals("[4♥, 5♥, 6♥]", this.initialized1.getDrawCards().toString());

  }


  @Test(expected = IllegalArgumentException.class)
  public void testRemoveUsingOutIndex() {
    assertEquals(10, this.initialized1.getCardAt(6, 1).getValue());
    assertEquals("[3♥, 4♥, 5♥]", this.initialized1.getDrawCards().toString());
    initialized1.removeUsingDraw(3, 6, 1);
  }

  @Test
  public void testDiscardDraw() {
    assertEquals("[3♥, 4♥, 5♥]", this.initialized1.getDrawCards().toString());
    initialized1.discardDraw(0);
    assertEquals("[4♥, 5♥, 6♥]", this.initialized1.getDrawCards().toString());
  }


  @Test
  public void testGetNum() {
    assertEquals(3, initialized1.getRowWidth(2));
    assertEquals(7, initialized1.getNumRows());
    assertEquals(3, initialized1.getNumDraw());
    assertEquals(-1, basic1.getNumDraw());
    assertEquals(-1, basic1.getNumRows());
  }


  @Test(expected = IllegalStateException.class)
  public void testBeforeGameStart() {
    basic1.getRowWidth(3);
    basic1.getRowWidth(-1);
    basic1.getRowWidth(10);
    basic1.isGameOver();
  }

  @Test
  public void testIsGameOverEmptyPyramid() {
    assertEquals(false, this.initialized1.isGameOver());

    miniGame.removeUsingDraw(6, 1, 1);
    miniGame.removeUsingDraw(6, 1, 0);
    miniGame.removeUsingDraw(6, 0, 0);
    assertEquals(0, miniGame.getScore());
    assertEquals(true, miniGame.isGameOver());

  }

  @Test
  public void testIsGameOverEmptyDraw() {
    for (int i = 0; i < 48; i++) {
      miniGame.discardDraw(0);
    }
    assertEquals("[K♠]", miniGame.getDrawCards().toString());
    assertEquals(false, miniGame.isGameOver());
    miniGame.discardDraw(0);
    assertEquals(true, miniGame.isGameOver());
    assertEquals(6, miniGame.getScore());
  }

  @Test
  public void testGetScore() {
    assertEquals(185, initialized1.getScore());
    initialized1.remove(6, 3, 6, 5);
    assertEquals(172, initialized1.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetScoreBeforeStart() {
    assertEquals(0, basic1.getScore());
  }

}