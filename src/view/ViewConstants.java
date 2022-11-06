package view;

import dungeon.Direction;

/**
 * This class represents all the UI constants being used in the application which includes
 *    string messages, dropdown values etc.
 */
public class ViewConstants {
  public static final int CELL_WH = 70;
  public static final String[] DISTANCE_CHOICE = {"1", "2", "3", "4", "5"};
  public static final String[] CHOICES = { "Yes", "No"};
  public static final Direction[] DIRECTION_CHOICE = {
    dungeon.Direction.NORTH,
    dungeon.Direction.SOUTH,
    dungeon.Direction.EAST,
    dungeon.Direction.WEST};
  public static final String SELECT_DIRECTION = "Select Direction";
  public static final String SELECT_DISTANCE = "Select Distance";
  public static final String SHOOT_ARROW_HEADING = "Shoot Arrow";
  public static final String NO_ARROWS_AVAILABLE = "No Arrows Available";
  public static final String COLLECT_TREASURE_HEADING = "Collect Treasure";
  public static final String NO_ITEM_AVAILABLE = "No Available Items at this location";
  public static final String GENERIC_INFO_MSG = "Any info will be displayed here!";
  public static final String PLAYER_INVENTORY_DET_MSG = "Player Inventory Details";
  public static final String GAME_TITLE = "Dungeons and Dragons";
  public static final String FELL_INTO_PIT = "You fell into a pit! Game over.";
  public static final String EATEN_BY_OTYUGH = "You are Eaten by Otyugh! Game over.";
  public static final String WIN_MSG = "You Won!!";
  public static final String PLAYER_MOVED_MSG = "Player moved in ";
  public static final String COLLECT_TREASURE_MSG = ",<br/>You can collect treasure here!";
  public static final String TREASURE_STOLEN_MSG = "<br/> A thief stole your collected Treasures";
  public static final String INVALID_GAME_STATE_MSG = "Invalid Game state.";
  public static final String GAME_ALREADY_WON_MSG = "Game already Won! Start a new Game.";
  public static final String PLAYER_DEAD_MSG = "Player already dead.";
  public static final String TREASURE_COLLECTED_MSG = "<br/>Treasure Collected!";
  public static final String ARROW_SHOT_MSG = "<br/>Arrow shot into Darkness!";
  public static final String CANNOT_SHOOT_MSG = "Cannot Shoot in this direction!";
  public static final String PIT_NEARBY_MSG = "<br/> Warning! Pit nearby!";
  public static final String LIGHT_SMELL_MSG = "<br/> Warning! Light smell detected.";
  public static final String STRONG_SMELL_MSG = "<br/> Warning! Strong smell detected.";

}
