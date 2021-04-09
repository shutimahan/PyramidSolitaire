import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.TripeakPyramidSolitaire;

import static org.junit.Assert.assertEquals;


/**
 * Test class for the methods in controller for Tripeak Solitaire
 */
public class TripeaksPyramidSolitaireTextualControllerTest {
  private StringBuilder out;
  private Reader in;

  private BasicPyramidSolitaire initialized1;


  /**
   * Sets up a working deck and run commands for testing purposes
   */
  @Before
  public void setUp() {
    String readableEntry = "";
    for (int i = 0; i < 51; i++) {
      readableEntry += "dd 1 \n";
    }
    initialized1 = new TripeakPyramidSolitaire();
  }

  // tests if the input commands are null
  @Test(expected = IllegalArgumentException.class)
  public void testReadableNull() {
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(null, out);
  }

  // tests if the output is a null and not appendable
  @Test(expected = IllegalArgumentException.class)
  public void testAppendableNull() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, null);
  }

  // tests if the model is null
  @Test(expected = IllegalArgumentException.class)
  public void testModelNull() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(null, initialized1.getDeck(), false, 7, 3);
  }


  // tests if the rows is invalid
  @Test(expected = IllegalStateException.class)
  public void testModelInvalid() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, -1, 3);
  }

  // tests if the draw is invalid
  @Test(expected = IllegalStateException.class)
  public void testDrawsInvalid() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, 1, -1);
  }

  @Test(expected = IllegalStateException.class)
  public void testRowsInvalid() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, 10, 3);
  }

  // tests if the deck is null
  @Test(expected = IllegalStateException.class)
  public void testDeckNull() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, null, false, 7, 3);
  }

  @Test
  public void testOverRowIndex() {
    in = new StringReader("rm1 8 7 1 \n rm1 7 1 \n q");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "Invalid move. Try another move.\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "    A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 429\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "    A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 429\n", out.toString());
  }

  // expect illegal state as the rm1 command will not register as valid
  @Test
  public void testOverColIndex() {
    in = new StringReader("rm1 7 9 1 \n rm2 7 7 7 8\n rm1 7 1 \n q");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "Invalid move. Try another move.\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣          8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 429\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "    A♣  2♣  3♣  4♣  5♣          8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 416\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "    A♣  2♣  3♣  4♣  5♣          8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 416\n", out.toString());
  }

  // tests with a valid command with invalid characters after
  @Test
  public void testValidMoveWithInvalids() {
    in = new StringReader("rm2 7 2 d f r t 7 f g h -1 13");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠      2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 429\n", out.toString());
  }


  @Test
  public void testPlay() throws IOException {
    in = new StringReader("rm2 7 7 f 7 jkl random dd 8");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣          8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 429\n", out.toString());
  }

  // test Q
  // in3 = new StringReader("q 7 4 7 6");
  @Test
  public void testGameQuit() throws IOException {
    in = new StringReader("q 7 4 7 6");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n", out.toString());
  }


  // test empty line
  @Test
  public void testEmptyCommands() throws IOException {
    in = new StringReader("");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n", out.toString());
  }


  @Test
  public void testRemoveWithDrawWithQuitSameLine() throws IOException {
    in = new StringReader("rmwd 2 7 13 \n dd 1 q");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣\n" +
            "Draw: K♣, 2♦, 3♦\n" +
            "Score: 430\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣\n" +
            "Draw: 2♦, 3♦, 4♦\n" +
            "Score: 430\n", out.toString());
  }

  @Test
  public void testRemoveWithDrawFlawed() throws IOException {
    in = new StringReader("rmwd 0 7 2");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid input.\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n", out.toString());
  }

  @Test
  public void testDiscardDrawFlawed() throws IOException {
    in = new StringReader("dd 7 0 \n dd 1");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "Invalid move. Try another move.\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: A♦, 2♦, 3♦\n" +
            "Score: 442\n", out.toString());
  }

  @Test
  public void testDiscardDrawFlawedSameLine() throws IOException {
    in = new StringReader("dd 7 dd 1 \n");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: A♦, 2♦, 3♦\n" +
            "Score: 442\n", out.toString());
  }



  // test discard then remove
  @Test
  public void testDiscardThenRemove() throws IOException {
    in = new StringReader("dd 1 \n rmwd 2 7 12");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: A♦, 2♦, 3♦\n" +
            "Score: 442\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣     Q♣\n" +
            "Draw: A♦, 3♦, 4♦\n" +
            "Score: 431\n", out.toString());
  }

  // test remove draw with a q embedded
  @Test
  public void testQuitInMiddle() throws IOException {
    in = new StringReader("dd 1 \n rmwd 1 7 q 1");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 442\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: A♦, 2♦, 3♦\n" +
            "Score: 442\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣          2♣          3♣\n" +
            "          4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "        10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "      6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "  A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "K♠  A♣  2♣  3♣  4♣  5♣  6♣  7♣  8♣  9♣  10♣ J♣  Q♣\n" +
            "Draw: A♦, 2♦, 3♦\n" +
            "Score: 442\n", out.toString());
  }

  // tests the game over win screen when winning
  @Test
  public void testGameOverWin() {
    in = new StringReader("rmwd 11 1 1 \n");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 1, 13);
    assertEquals("A♣\n" +
            "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣, A♦\n" +
            "Score: 1\n" +
            "You win!\n", out.toString());
  }

  // tests the removal of an edge card
  @Test
  public void testEdge() {
    in = new StringReader("rm2 6 1 6 12 \n");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 6, 3);
    assertEquals("          A♣          2♣          3♣\n" +
            "        4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "      10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "    6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "A♠  2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠\n" +
            "Draw: K♠, A♣, 2♣\n" +
            "Score: 351\n" +
            "          A♣          2♣          3♣\n" +
            "        4♣  5♣      6♣  7♣      8♣  9♣\n" +
            "      10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦\n" +
            "    6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥\n" +
            "    2♠  3♠  4♠  5♠  6♠  7♠  8♠  9♠  10♠ J♠\n" +
            "Draw: K♠, A♣, 2♣\n" +
            "Score: 338\n", out.toString());
  }

}