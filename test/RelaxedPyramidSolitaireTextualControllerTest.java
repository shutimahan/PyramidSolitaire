import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.RelaxedPyramidSolitaire;

import static org.junit.Assert.assertEquals;


/**
 * Test class for the methods in controller for Relaxed Solitaire
 */
public class RelaxedPyramidSolitaireTextualControllerTest {
  private StringBuilder out;
  private String readableEntry;
  private Reader in;

  private BasicPyramidSolitaire initialized1;


  /**
   * Sets up a working deck and run commands for testing purposes
   */
  @Before
  public void setUp() {
    readableEntry = "";
    for (int i = 0; i < 51; i++) {
      readableEntry += "dd 1 \n";
    }
    initialized1 = new RelaxedPyramidSolitaire();
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
    in = new StringReader("rm1 8 7 5 \n rm1 7 5 \n q");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "Invalid move. Try another move.\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦      A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 172\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦      A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 172\n", out.toString());
  }

  // expect illegal state as the rm1 command will not register as valid
  @Test
  public void testOverColIndex() {
    in = new StringReader("rm1 7 9 5 \n rm2 7 4 7 6\n rm1 7 5 \n q");
    out = new StringBuilder();
    PyramidSolitaireController control4 = new PyramidSolitaireTextualController(in, out);
    control4.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "Invalid move. Try another move.\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦      K♦      2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 172\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦              2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 159\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦              2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 159\n", out.toString());
  }

  // tests with a valid command with invalid characters after
  @Test
  public void testValidMoveWithInvalids() {
    in = new StringReader("rm2 7 4 d f r t 7 f g h -1 6");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦      K♦      2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 172\n", out.toString());
  }


  // test invalid command line later, followed by Q
  // in2 = new StringReader("rm2 7 4 f 7 h 6 \n rm1 7 5 \n 2 2 \n dd 2 \n q Q");
  @Test
  public void testPlay() throws IOException {
    in = new StringReader("rm2 7 4 f 7 h 6 \n rm1 7 5 \n 2 2 \n dd 2 \n q Q");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid input.\n" +
            "Invalid input.\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦      K♦      2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 172\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦              2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 159\n" +
            "Invalid move. Try another move.\n" +
            "Invalid move. Try another move.\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦              2♥\n" +
            "Draw: 3♥, 5♥, 6♥\n" +
            "Score: 159\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦              2♥\n" +
            "Draw: 3♥, 5♥, 6♥\n" +
            "Score: 159\n", out.toString());
  }

  // test Q
  // in3 = new StringReader("q 7 4 7 6");
  @Test
  public void testGameQuit() throws IOException {
    in = new StringReader("q 7 4 7 6");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n", out.toString());
  }


  // test empty line
  @Test
  public void testEmptyCommands() throws IOException {
    in = new StringReader("");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n", out.toString());
  }


  @Test
  public void testRemoveWithDraw() throws IOException {
    in = new StringReader("rmwd 1 7 2 \n rm1 q");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦      J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 175\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦      J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 175\n", out.toString());
  }

  @Test
  public void testRemoveWithDrawFlawed() throws IOException {
    in = new StringReader("rmwd 0 7 2");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid input.\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n", out.toString());
  }

  @Test
  public void testDiscardDrawFlawed() throws IOException {
    in = new StringReader("dd 7 0 \n dd 1");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "Invalid move. Try another move.\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 185\n", out.toString());
  }

  @Test
  public void testDiscardDrawFlawedSameLine() throws IOException {
    in = new StringReader("dd 7 dd 1 \n");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "Invalid move. Play Again. Something about your command is rubbish\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 185\n", out.toString());
  }



  // test discard then remove
  @Test
  public void testDiscardThenRemove() throws IOException {
    in = new StringReader("dd 1 \n rmwd 1 7 1");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 185\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "    10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 5♥, 6♥, 7♥\n" +
            "Score: 176\n", out.toString());
  }

  // test remove draw with a q embedded
  @Test
  public void testQuitInMiddle() throws IOException {
    in = new StringReader("dd 1 \n rmwd 1 7 q 1");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 7, 3);
    assertEquals("            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 185\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 185\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "            A♣\n" +
            "          2♣  3♣\n" +
            "        4♣  5♣  6♣\n" +
            "      7♣  8♣  9♣  10♣\n" +
            "    J♣  Q♣  K♣  A♦  2♦\n" +
            "  3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 185\n", out.toString());
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

  // tests the completed game if no available move left
  @Test
  public void testGameOverLoss() {
    in = new StringReader(readableEntry);
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 1, 3);
    assertEquals("A♣\n" +
            "Draw: 2♣, 3♣, 4♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 3♣, 4♣, 5♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 4♣, 5♣, 6♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 5♣, 6♣, 7♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 6♣, 7♣, 8♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 7♣, 8♣, 9♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 8♣, 9♣, 10♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 9♣, 10♣, J♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 10♣, J♣, Q♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: J♣, Q♣, K♣\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: Q♣, K♣, A♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: K♣, A♦, 2♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: A♦, 2♦, 3♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 2♦, 3♦, 4♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 3♦, 4♦, 5♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 4♦, 5♦, 6♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 5♦, 6♦, 7♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 6♦, 7♦, 8♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 7♦, 8♦, 9♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 8♦, 9♦, 10♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 9♦, 10♦, J♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 10♦, J♦, Q♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: J♦, Q♦, K♦\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: Q♦, K♦, A♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: K♦, A♥, 2♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: A♥, 2♥, 3♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 2♥, 3♥, 4♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 3♥, 4♥, 5♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 4♥, 5♥, 6♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 5♥, 6♥, 7♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 6♥, 7♥, 8♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 7♥, 8♥, 9♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 8♥, 9♥, 10♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 9♥, 10♥, J♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 10♥, J♥, Q♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: J♥, Q♥, K♥\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: Q♥, K♥, A♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: K♥, A♠, 2♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: A♠, 2♠, 3♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 2♠, 3♠, 4♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 3♠, 4♠, 5♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 4♠, 5♠, 6♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 5♠, 6♠, 7♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 6♠, 7♠, 8♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 7♠, 8♠, 9♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 8♠, 9♠, 10♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 9♠, 10♠, J♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: 10♠, J♠, Q♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: J♠, Q♠, K♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: Q♠, K♠\n" +
            "Score: 1\n" +
            "A♣\n" +
            "Draw: K♠\n" +
            "Score: 1\n" +
            "Game over. Score: 1\n", out.toString());
  }

  // tests the removal of an edge card
  @Test
  public void testEdge() {
    in = new StringReader("rm2 6 3 6 6 \n");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 6, 3);
    assertEquals("          A♣\n" +
            "        2♣  3♣\n" +
            "      4♣  5♣  6♣\n" +
            "    7♣  8♣  9♣  10♣\n" +
            "  J♣  Q♣  K♣  A♦  2♦\n" +
            "3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "Draw: 9♦, 10♦, J♦\n" +
            "Score: 127\n" +
            "          A♣\n" +
            "        2♣  3♣\n" +
            "      4♣  5♣  6♣\n" +
            "    7♣  8♣  9♣  10♣\n" +
            "  J♣  Q♣  K♣  A♦  2♦\n" +
            "3♦  4♦      6♦  7♦\n" +
            "Draw: 9♦, 10♦, J♦\n" +
            "Score: 114\n", out.toString());
  }

  // tests the removal of an edge card
  @Test
  public void testRelaxedMovement() {
    in = new StringReader("rm2 8 1 8 8 \n rm2 7 1 8 2");
    out = new StringBuilder();
    PyramidSolitaireController control1 = new PyramidSolitaireTextualController(in, out);
    control1.playGame(initialized1, initialized1.getDeck(), false, 8, 3);
    assertEquals("              A♣\n" +
            "            2♣  3♣\n" +
            "          4♣  5♣  6♣\n" +
            "        7♣  8♣  9♣  10♣\n" +
            "      J♣  Q♣  K♣  A♦  2♦\n" +
            "    3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n" +
            "Draw: J♥, Q♥, K♥\n" +
            "Score: 237\n" +
            "              A♣\n" +
            "            2♣  3♣\n" +
            "          4♣  5♣  6♣\n" +
            "        7♣  8♣  9♣  10♣\n" +
            "      J♣  Q♣  K♣  A♦  2♦\n" +
            "    3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "  9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "    4♥  5♥  6♥  7♥  8♥  9♥\n" +
            "Draw: J♥, Q♥, K♥\n" +
            "Score: 224\n" +
            "              A♣\n" +
            "            2♣  3♣\n" +
            "          4♣  5♣  6♣\n" +
            "        7♣  8♣  9♣  10♣\n" +
            "      J♣  Q♣  K♣  A♦  2♦\n" +
            "    3♦  4♦  5♦  6♦  7♦  8♦\n" +
            "      10♦ J♦  Q♦  K♦  A♥  2♥\n" +
            "        5♥  6♥  7♥  8♥  9♥\n" +
            "Draw: J♥, Q♥, K♥\n" +
            "Score: 211\n", out.toString());
  }

}