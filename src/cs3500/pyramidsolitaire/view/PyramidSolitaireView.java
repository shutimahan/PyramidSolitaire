package cs3500.pyramidsolitaire.view;

import java.io.IOException;

/**
 *  Interface for the View class of the game. Renders the game into text.
 */
public interface PyramidSolitaireView {
  /**
   * Renders a model in some manner (e.g as test, or as graphics, etc.).
   * @throws IOException if the rendering fails for some reason.
   */
  void render() throws IOException;

}
