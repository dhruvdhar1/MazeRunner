package dungeon;

/**
 * This interface represents a dungeon (N x M) dungeon and exposes all the operations that
 *    can be performed on the dungeon and the player present in the dungeon.
 */
public interface Dungeon extends ReadOnlyDungeon {

  /**
   * Starts the game by generating the maze and places the player in the maze.
   */
  void enterDungeon();

  /**
   * moves the player in a particular direction from its current location.
   * @param directionToMove Direction in which player should move(N, S, E, W).
   * @throws IllegalArgumentException if trying to move in a direction that has no
   *                                  links to other cave.
   * @throws IllegalArgumentException if trying to move after destination reached.
   * @throws IllegalArgumentException if null parameter is provided.
   * @throws IllegalStateException if trying to invoke before the player enters the maze.
   */
  void movePlayer(Direction directionToMove);

  /**
   * Collect treasure for the player that is present at the current location.
   * @param treasureId  id of the treasure that needs to be collected.
   * @throws IllegalArgumentException If provided invalid treasureId.
   * @throws IllegalArgumentException If trying to collect treasure when none exists.
   * @throws IllegalStateException if trying to invoke before the player enters the maze.
   */
  void collectTreasure(int treasureId);

  /**
   * Shoot an arrow for a specified distance in a particular direction.
   * @param direction direction to shoot the arrow in.
   * @param distance  distance travelled by arrow.
   * @throws IllegalArgumentException if provided direction is invalid.
   * @throws IllegalArgumentException if provided invalid distance.
   */
  void shootArrow(Direction direction, int distance);

}
