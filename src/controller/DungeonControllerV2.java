package controller;

import dungeon.Direction;

import java.util.Set;


/**
 * This interface represents the controller that acts as the
 * liaison between the view and the model and exposes the operations to be
 * performed between the two.
 */
public interface DungeonControllerV2 {

  /**
   * Render the game by calling this method.
   */
  void play();

  /**
   * Start the dungeon game by calling this method.
   *
   * @param numRows           number of rows in the dungeon
   * @param numCol            number of columns in the dungeon
   * @param interconnectivity interconnectivity in the dungeon
   * @param numMonster        number of monsters in the dungeon
   * @param isWrapping        wrapping dungeon or not
   * @param treasurePercent   percentage of treasure in the dungeon
   * @param numPit            number of pits in the dungeon
   * @param numThief          number of thieves in the dungeon
   */
  void startGame(int numRows, int numCol, int interconnectivity, int numMonster,
                 boolean isWrapping, double treasurePercent, int numPit, int numThief);

  /**
   * Moves the player in a specific direction. Ignores user command
   * if provided with invalid direction.
   *
   * @param d direction to move the player to.
   */
  void movePlayer(Direction d);

  /**
   * Collects treasures from a cell.
   *
   * @param itemsToCollect list of item Ids, which need to be collected.
   */
  void collectTreasure(Set<Integer> itemsToCollect);

  /**
   * Shoot an arrow in the user specified direction for a specific distance.
   *
   * @param d    direction to shoot the arrow in.
   * @param dist distance that should be travelled by the arrow.
   */
  void shootArrow(Direction d, int dist);

  /**
   * Initiate a new game by recreating a new model with new parameters
   * and a corresponding view.
   */
  void initiateNewGame();

  /**
   * Resets the model and view state with the existing parameters.
   */
  void restartGame();

  /**
   * Terminates the game by calling this method.
   */
  void quitGame();
}
