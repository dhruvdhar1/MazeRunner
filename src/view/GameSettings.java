package view;

import controller.DungeonControllerV2;

/**
 * This interface represents the view for a form to accept all the user-defined
 *    parameters of the game like rows, columns, interconnectivity etc.
 */
public interface GameSettings {

  /**
   * Set up the controller to handle click events in this view.
   * @param listener the controller
   * @throws IllegalArgumentException If null controller passed.
   */
  void addClickListeners(DungeonControllerV2 listener);

  /**
   * Sets the visibility of the settings UI. Frame is rendered if
   *    visibility is {@code true} else frame is not displayed.
   * @param visibility  UI visibiliyt
   */
  void makeVisible(boolean visibility);

  /**
   * Get the number of rows entered by the user.
   * @return  num rows.
   */
  int getNumRows();

  /**
   * Get the number of columns entered by the user.
   * @return  num columns.
   */
  int getNumCol();

  /**
   * Get the interconnectivity entered by the user.
   * @return  interconnectivity.
   */
  int getInterconnectivity();

  /**
   * Get the treasure percentage entered by the user.
   * @return  treasure percentage.
   */
  double getTreasurePercent();

  /**
   * get the number of monsters entered by the user.
   * @return number of monsters.
   */
  int getNumMonster();

  /**
   * get the wrapping criteria entered by the user.
   * @return wrapping criteria.
   */
  boolean isWrapping();

  /**
   * get the number of pits entered by the user.
   * @return number of pits.
   */
  int getNumPit();

  /**
   * get the number of thieves entered by the user.
   * @return number of thieves.
   */
  int getNumThief();
}
