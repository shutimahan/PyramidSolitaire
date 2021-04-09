package cs3500.pyramidsolitaire.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a public enum Gametype that houses only 3 different possible gametypes: Basic,
 * relaxed, and Tripeaks. It will offer only a single factory method create() that returns the
 * instance of the desired gametype.
 */
public final class PyramidSolitaireCreator {

  /**
   * Accepts a type of pyramid solitaire and returns a PyramidSolitaireModel of the desired type
   * Will return a basic game by default
   *
   * @param type enum of BASIC, RELAXED, or TRIPEAKS.
   * @return the PyramidSolitaireModel of one of 3 types
   */
  public static PyramidSolitaireModel create(GameType type) {
    switch (type) {
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case TRIPEAKS:
        return new TripeakPyramidSolitaire();
      case BASIC:
      default:
        return new BasicPyramidSolitaire();
    }
  }


  /**
   * Public enum for the 3 different types of pyramid solitaire
   */
  public enum GameType {
    BASIC("basic"), RELAXED("relaxed"), TRIPEAKS("tripeaks");
    private static final Map<String, GameType> lookup = new HashMap<>();

    static {
      for (GameType gameType : GameType.values()) {
        lookup.put(gameType.getType(), gameType);
      }
    }

    final String type;

    GameType(String type) {
      this.type = type;
    }

    public static GameType get(String url) {
      return lookup.get(url);
    }

    public String getType() {
      return type;
    }
  }
}

