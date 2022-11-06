package dungeon;

/**
 * This enum represents all the item types than can be
 * collected from the cells.
 */
public enum ItemType {
  DIAMOND,
  RUBY,
  SAPPHIRE,
  ARROW;

  /**
   * Returns the TreasureType based on an integer.
   * @param index treasureType identifier
   * @return  TreasureType mapped to index.
   */
  public static ItemType getTreasureUsingIndex(int index) {
    switch (index) {
      case 1:
        return DIAMOND;
      case 2:
        return RUBY;
      case 3:
        return SAPPHIRE;
      default:
        return null;
    }
  }
}
