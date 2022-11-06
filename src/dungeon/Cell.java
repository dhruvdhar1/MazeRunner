package dungeon;

import java.util.Map;

/**
 * This package-private interface represents a cell in the dungeon and exposes the operations
 * that can be performed on a cell. This cell can have 1-4 neighbours
 * inclusive. A cell with 2 neighbours is considered a 'Tunnel'
 * and a cell with 1,3,4 neighbours is considered a 'Cave'.
 */
interface Cell {

  /**
   * Get the row number of Cell.
   * @return row in which the cell resides.
   */
  int getRowLocation();

  /**getTreasures
   * Get the column number of Cell.
   * @return column in which the cell resides.
   */
  int getColumnLocation();

  /**
   * removes treasure with given treasureId from a cave.
   * @param treasureId id of the treasure that needs to be removed.
   * @return removed treasure object.
   */
  ItemType removeItem(int treasureId);

  /**
   * Get the type of cell (Cave/ Tunnel).
   * @return type of cell.
   */
  LocationType getLocationType();

  /**
   * Set the north neighbour of the current cell.
   * @param north cell to be connected.
   * @throws IllegalArgumentException if null parameter provided.
   */
  void setNorth(Cell north);

  /**
   * Set the west neighbour of the current cell.
   * @param west cell to be connected.
   * @throws IllegalArgumentException if null parameter provided.
   */
  void setWest(Cell west);

  /**
   * Set the east neighbour of the current cell.
   * @param east cell to be connected.
   * @throws IllegalArgumentException if null parameter provided.
   */
  void setEast(Cell east);

  /**
   * Set the south neighbour of the current cell.
   * @param south cell to be connected.
   * @throws IllegalArgumentException if null parameter provided.
   */
  void setSouth(Cell south);

  /**
   * Get the associated id of the cave.
   * @return  cave id.
   */
  int getCaveId();

  /**
   * Get the north neighbour of the current cell.
   * @return  north neighbour.
   */
  Cell getNorth();

  /**
   * Get the west neighbour of the current cell.
   * @return  west neighbour.
   */
  Cell getWest();

  /**
   * Get the east neighbour of the current cell.
   * @return  east neighbour.
   */
  Cell getEast();

  /**
   * Get the south neighbour of the current cell.
   * @return  south neighbour.
   */
  Cell getSouth();

  /**
   * Get the neighbour in a particular direction.
   * @param direction neighbour's direction.
   * @return  neighbouring cell.
   */
  Cell getDirection(Direction direction);

  /**
   * Add treasure to this cell's list of treasures.
   * @param treasure  treasure to be added.
   * @throws IllegalArgumentException If trying to add treasure to a 'Tunnel' cell type.
   */
  void addItems(ItemType treasure, int treasureId);

  /**
   * Get all the treasures present in this cell.
   * @return  map of treasure id and the treasure itself.
   */
  Map<Integer, ItemType> getItems();

  /**
   * Add a monster to a cell by calling this method.
   */
  void putMonster();

  /**
   * Add a pit to the cell by calling this method.
   */
  void putPit();

  /**
   * Add a thief to the cell by calling this method.
   */
  void putThief();

  /**
   * Informs the caller method whether this cell has a monster.
   * @return  true if cell has monster, else false.
   */
  boolean hasMonster();

  /**
   * Informs the caller method whether this cell has a pit.
   * @return  true if cell has a pit, else false.
   */
  boolean hasPit();

  /**
   * Informs the caller method whether this cell has a thief.
   * @return  true if cell has thief, else false.
   */
  boolean hasThief();

  /**
   * Attack a monster in this cave by calling this method. Calling this method inflicts
   *    a damage of 50 points from total health.
   */
  void hitMonster();

  /**
   * Get the health of monster present in the cell.
   * @return  monster health, null if no monster present in the cell.
   */
  Integer getMonsterHealth();
}
