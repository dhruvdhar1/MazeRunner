package dungeon;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a dungeon (N x M) dungeon and exposes all the operations that
 * can be performed on the dungeon and the player present in the dungeon.
 */
public interface ReadOnlyDungeon {

  /**
   * Provides description of the player that includes the list of treasures that the player
   * has collected.
   *
   * @return a map of treasureId and the collected treasure.
   */
  Map<Integer, ItemType> getPlayerDescription();

  /**
   * Provides description of player's location that includes the current location,
   * treasures present at the location and the directions(N, S, E, W) in which player
   * can move.
   *
   * @return PlayerLocationDescription object.
   * @throws IllegalStateException if trying to invoke before the player enters the maze.
   */
  PlayerLocationDescription getPlayerLocationDescription();

  /**
   * Informs whether treasure is available at the current location.
   *
   * @return true if available, else false.
   * @throws IllegalStateException if trying to invoke before the player enters the maze.
   */
  boolean treasureAvailable();

  /**
   * Provides the adjacency list of the maze that is generated.
   *
   * @return Adjacency list.
   */
  Map<Integer, List<Integer>> getAdjacencyList();

  /**
   * Inform the user if the game has ended. A game is ended either when the player
   * reaches the end or if the user is eaten by a monster.
   *
   * @return true if game ended, else false.
   */
  boolean isGameOver();

  /**
   * Inform the user if the player has won the game or not.
   *
   * @return true if won, else false.
   */
  boolean isPlayerWinner();

  /**
   * Inform the user when player detects a light pungent smell at his current location.
   *
   * @return true if light smell detected, else false.
   */
  boolean lightSmellDetected();

  /*** Inform the user when player detects a strong pungent smell at his current location.
   * @return true if strong smell detected, else false.
   */
  boolean strongSmellDetected();

  /**
   * Inform the user when player detects a pit near his current location.
   *
   * @return true if light smell detected, else false.
   */
  boolean pitDetected();

  /**
   * Inform if the current cell contains a pit. This info is used
   * by the view to render pit image.
   *
   * @return    true if location has a pit else false.
   */
  boolean hasPit();

  /**
   * Inform if the current cell contains a thief. This info is used by the
   * view to render thief image on the screen.
   *
   * @return    true if location has a thief else false.
   */
  boolean hasThief();

}

