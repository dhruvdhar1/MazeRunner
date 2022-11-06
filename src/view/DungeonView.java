package view;

import controller.DungeonControllerV2;

/**
 * This interface represents the overall view of the Dungeon Game and exposes
 * the various operations that can be performed on the view.
 */
public interface DungeonView {
  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible(int numRows, int numCol);

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   * @throws IllegalArgumentException if null controller instance passed.
   */
  void addClickListener(DungeonControllerV2 listener);

  /**
   * Set the info message for the user using this method.
   *
   * @param message message to be displayed.
   */
  void setInfoMessage(String message);

  /**
   * Re-render the side panel of the window by calling this method.
   */
  void reRenderSidePanel();
}
