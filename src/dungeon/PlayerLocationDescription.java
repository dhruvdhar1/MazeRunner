package dungeon;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a Player's current location's description. This description includes
 * the directions that the player can move in, the id of the current cell, the type of
 * location (Cave/Tunnel) and all the available treasures in that cell (if any).
 */
public class PlayerLocationDescription {

  private final int cellId;
  private final boolean canMoveNorth;
  private final boolean canMoveSouth;
  private final boolean canMoveEast;
  private final boolean canMoveWest;
  private final LocationType location;
  private final Map<Integer, ItemType> availableItems;

  /**
   * This constructor initializes values and returns an instance
   * of the {@code PlayerLocationDescription} class.
   * @param currentLocation player's current location
   */
  public PlayerLocationDescription(Cell currentLocation) {
    if (null == currentLocation) {
      throw new IllegalArgumentException("Cannot pass null object");
    }
    this.cellId = currentLocation.getCaveId();
    this.availableItems = currentLocation.getItems();
    this.location = currentLocation.getLocationType();
    this.canMoveNorth = currentLocation.getNorth() != null;
    this.canMoveSouth = currentLocation.getSouth() != null;
    this.canMoveEast = currentLocation.getEast() != null;
    this.canMoveWest = currentLocation.getWest() != null;
  }

  /**
   * This copy-constructor returns a deep-copy of the provided
   * {@code PlayerLocationDescription} type object.
   * @param locationDescription object to be copied.
   */
  public PlayerLocationDescription(PlayerLocationDescription locationDescription) {
    this.cellId = locationDescription.getCellId();
    this.canMoveNorth = locationDescription.canMoveNorth();
    this.canMoveSouth = locationDescription.canMoveSouth();
    this.canMoveEast = locationDescription.canMoveEast();
    this.canMoveWest = locationDescription.canMoveWest();
    this.location = locationDescription.getLocation();
    this.availableItems = locationDescription.getAvailableItems();
  }

  /**
   * Get a Map of all available Treasures at Player's current location.
   * This method returns a defensive copy.
   * @return  Map of available Treasures.
   */
  public Map<Integer, ItemType> getAvailableItems() {
    Map<Integer, ItemType> treasureListCopy = new HashMap<>();
    Set<Integer> keySet = availableItems.keySet();
    for (int key: keySet) {
      ItemType treasureCopy = availableItems.get(key);
      treasureListCopy.put(key, treasureCopy);
    }
    return treasureListCopy; //return defensive copy.
  }

  /**
   * Get the string representation of Map of Treasures.
   * @return  treasure list as string.
   */
  private String getAvailableTreasureStr() {
    StringBuilder sb = new StringBuilder();
    for (ItemType treasure: availableItems.values()) {
      sb.append("\n").append(treasure.toString());
    }
    return sb.toString();
  }

  /**
   * Inform the user whether the player can move north from his current location.
   * @return  true if player can move north from current location, else false.
   */
  public boolean canMoveNorth() {
    return canMoveNorth;
  }


  /**
   * Inform the user whether the player can move south from his current location.
   * @return  true if player can move south from current location, else false.
   */
  public boolean canMoveSouth() {
    return canMoveSouth;
  }

  /**
   * Inform the user whether the player can move east from his current location.
   * @return  true if player can move east from current location, else false.
   */
  public boolean canMoveEast() {
    return canMoveEast;
  }

  /**
   * Inform the user whether the player can move west from his current location.
   * @return  true if player can move west from current location, else false.
   */
  public boolean canMoveWest() {
    return canMoveWest;
  }

  /**
   * Get the id of the cell.
   * @return  cell id.
   */
  public int getCellId() {
    return cellId;
  }

  /**
   * Get the type of cell (Tunnel/ Cave).
   * @return  location type.
   */
  public LocationType getLocation() {
    return location;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("-------Location Description-------")
            .append("\nCave Id: ").append(cellId)
            .append("\nTreasure List: ").append(getAvailableTreasureStr())
            .append("\nLocation Type: ").append(location)
            .append("\nAvailable Directions: ");
    if (canMoveNorth) {
      stringBuilder.append("North, ");
    } if (canMoveEast) {
      stringBuilder.append("East, ");
    } if (canMoveSouth) {
      stringBuilder.append("South, ");
    } if (canMoveWest) {
      stringBuilder.append("West");
    }
    return stringBuilder.toString();
  }
}
