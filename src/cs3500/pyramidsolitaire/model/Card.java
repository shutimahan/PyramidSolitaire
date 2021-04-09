package cs3500.pyramidsolitaire.model;

import java.util.Objects;

/**
 * Represents the card class
 */
final public class Card {
  private int value;
  private char suite;

  public Card(int i) {
    this.value = (i % 13) + 1;
    this.suite = this.suiteGraphics(i / 13);
  }

  public Card(int value, char suite) {
    this.value = value;
    this.suite = suite;
  }


  private char suiteGraphics(int num) {
    switch (num) {
      case 0:
        return '♣';
      case 1:
        return '♦';
      case 2:
        return '♥';
      case 3:
        return '♠';
      default:
        return ' ';
    }
  }

  public int getValue() {
    return this.value;
  }

  public char getSuite() {
    return this.suite;
  }

  @Override
  public String toString() {
    char suiteCopy = this.suite;
    return pictureCards() + suiteCopy;
  }

  private String pictureCards() {
    switch (this.value) {
      case 10:
        return "10";
      case 11:
        return "J";
      case 12:
        return "Q";
      case 13:
        return "K";
      case 1:
        return "A";
      case 0:
        return " ";
      default:
        return Integer.toString(this.value);
    }
  }

  @Override
  public boolean equals(Object card) {
    if (this == card) {
      return true;
    }
    if (card == null || getClass() != card.getClass()) {
      return false;
    }
    Card card1 = (Card) card;
    return value == ((Card) card).value &&
            this.suite == ((Card) card).suite;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, suite);
  }

}
