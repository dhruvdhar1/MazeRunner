package dungeon;

import java.util.Map;

/**
 * This package-private interface represents a player navigating his way through the
 * dungeon and the interface offers all the operations that can be performed
 * on the player.
 */
interface Player {

  /**
   * Get the description of the player that includes all the treasures
   * that the player has collected till now.
   * @return  player description.
   */
  Map<Integer, ItemType> getPlayerDescription();

  /**
   * Get the description of player's location that includes the current cave's location,
   * the directions navigable from current location, type of cell and list of
   * all the treasures available at the current location.
   * @return  description of player's location.
   */
  PlayerLocationDescription getPlayerLocationDescription();

  /**
   * Moves the player in a particular direction.
   * @param directionToMove direction to move the player in (North/ South/ East/ West)
   * @throws IllegalArgumentException if trying to make Illegal move.
   * @throws IllegalStateException  if trying to move after the player is dead.
   */
  void movePlayer(Direction directionToMove);

  /**
   * Collect treasure from the current location.
   * @param treasureId  id of treasure that needs to be collected.
   * @throws IllegalArgumentException if the provided treasureId is invalid.
   * @throws IllegalStateException  if trying to collect treasure after the player is dead.
   */
  void collectTreasure(int treasureId);

  /**
   * Provide the player with the dungeon's entry point.
   * @param entryPoint  entry point of the dungeon.
   * @throws IllegalArgumentException if null parameter provided.
   */
  void setEntryPoint(Cell entryPoint);

  /**
   * Get the player's current location.
   * @return  player's current location.
   */
  Cell getCurrentLocation();

  /**
   * Enter the maze and set the entry point as the current location.
   */
  void enterMaze();

  /**
   * Shoot an arrow for a specified distance in a particular direction.
   * @param direction direction to shoot the arrow in.
   * @param distance  distance travelled by arrow.
   * @throws IllegalArgumentException if provided direction is invalid.
   * @throws IllegalArgumentException if provided invalid distance.
   * @throws IllegalStateException  if trying to shoot after the player is dead.
   */
  void shootArrow(int distance, Direction direction);

  /**
   * Inform whether the player is alive or eaten by monster.
   * @return  true if alive, else false.
   */
  boolean isPlayerAlive();

  /**
   * Inform the user when player detects a light pungent smell at his current location.
   * @return  true if light smell detected, else false.
   */
  boolean getLightSmell();

  /**
   * Inform the user when player detects a strong pungent smell at his current location.
   * @return  true if strong smell detected, else false.
   */
  boolean getStrongSmell();

  /**
   * Informs the player if there is a pit near to his current location.
   *    This info can be used by the player to avoid the pit.
   * @return  true is, a pit is present nearby, else false.
   */
  boolean isPitNearby();
}
