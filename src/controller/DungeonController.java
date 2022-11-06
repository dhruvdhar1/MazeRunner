package controller;

import dungeon.Dungeon;

/**
 * This interface represents the controller of the Dungeon. It handles user actions by executing
 *    it using the model.
 */
public interface DungeonController {

  /**
   * This method uses the model reference to execute the operations of the game. Provides a menu
   *  based control to the user to play the game.
   * @param model Instance of the dungeon model.
   */
  void play(Dungeon model);
}
