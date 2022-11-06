package dungeon;

import java.util.HashMap;
import java.util.Map;

/**
 * This package-private class represents a cell in a dungeon and implements
 * the functionality of a cell.
 */
class CellImpl implements Cell {
  private static final int SINGLE_HIT_DAMAGE = 50;
  private final int caveId;
  private final Map<Integer, ItemType> inventoryList;
  private final Map<CellTraps, Integer> cellTraps;
  private final int rowLocation;
  private final int columnLocation;
  private Cell north;
  private Cell west;
  private Cell east;
  private Cell south;

  /**
   * This constructor returns an instance of the cell class.
   * @param caveId  id of the cave.
   * @param rowLocation row location in the grid.
   * @param columnLocation  column location in the grid.
   */
  public CellImpl(int caveId, int rowLocation, int columnLocation) {
    this.caveId = caveId;
    this.rowLocation = rowLocation;
    this.columnLocation = columnLocation;
    this.inventoryList = new HashMap<>();
    this.cellTraps = new HashMap<>();
    this.north = null;
    this.south = null;
    this.east = null;
    this.west = null;
  }

  /**
   * This copy-constructor returns a deep copy of the provided object.
   * @param cell  Object to copy.
   */
  public CellImpl(Cell cell) {
    this.east = cell.getEast();
    this.west = cell.getWest();
    this.south = cell.getSouth();
    this.north = cell.getNorth();
    this.cellTraps = new HashMap<>();
    this.caveId = cell.getCaveId();
    this.inventoryList = cell.getItems();
    this.rowLocation = cell.getRowLocation();
    this.columnLocation = cell.getColumnLocation();

  }

  @Override
  public ItemType removeItem(int treasureId) {
    if (inventoryList.containsKey(treasureId)) {
      return inventoryList.remove(treasureId);
    }
    throw new IllegalArgumentException("Treasure with given treasureId does not exist.");
  }

  @Override
  public LocationType getLocationType() {
    int north = isNeighbourPresent(this.north);
    int south = isNeighbourPresent(this.south);
    int east = isNeighbourPresent(this.east);
    int west = isNeighbourPresent(this.west);
    int sum = (north + east + south + west);
    if (sum == 2) {
      return LocationType.TUNNEL;
    } else {
      return LocationType.CAVE;
    }
  }

  /**
   * Checks if neighbour is present in a particular direction.
   * @param neighbour neighbour to be checked.
   * @return  1 if neighbour is present else 0.
   */
  private int isNeighbourPresent(Cell neighbour) {
    return (neighbour == null) ? 0 : 1;
  }

  @Override
  public int getCaveId() {
    return caveId;
  }

  @Override
  public void addItems(ItemType treasure, int treasureId) {
    if (null != treasure) {
      if (this.getLocationType().equals(LocationType.TUNNEL) && !treasure.equals(ItemType.ARROW)) {
        throw new IllegalArgumentException("Cannot add treasure to a Tunnel");
      }
      inventoryList.put(treasureId, treasure);
    }
  }

  @Override
  public Map<Integer, ItemType> getItems() {
    return inventoryList;
  }

  @Override
  public int getRowLocation() {
    return rowLocation;
  }

  @Override
  public int getColumnLocation() {
    return columnLocation;
  }

  @Override
  public void setNorth(Cell north) {
    if (null == north) {
      throw new IllegalArgumentException("null parameter provided");
    }
    this.north = north;
  }

  @Override
  public void setWest(Cell west) {
    if (null == west) {
      throw new IllegalArgumentException("null parameter provided");
    }
    this.west = west;
  }

  @Override
  public void setEast(Cell east) {
    if (null == east) {
      throw new IllegalArgumentException("null parameter provided");
    }
    this.east = east;
  }

  @Override
  public void setSouth(Cell south) {
    if (null == south) {
      throw new IllegalArgumentException("null parameter provided");
    }
    this.south = south;
  }

  @Override
  public Cell getNorth() {
    return north;
  }

  @Override
  public Cell getWest() {
    return west;
  }

  @Override
  public Cell getEast() {
    return east;
  }

  @Override
  public Cell getSouth() {
    return south;
  }

  @Override
  public Cell getDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return north;
      case WEST:
        return west;
      case EAST:
        return east;
      case SOUTH:
        return south;
      default:
        return null;
    }
  }

  @Override
  public void putMonster() {
    if (this.getLocationType().equals(LocationType.TUNNEL)) {
      throw new IllegalArgumentException("Monsters can be added to Caves only.");
    }
    cellTraps.put(CellTraps.MONSTER , 100);
  }

  @Override
  public void putPit() {
    cellTraps.put(CellTraps.PIT , 1);
  }

  @Override
  public void putThief() {
    cellTraps.put(CellTraps.THIEF , 1);
  }

  @Override
  public boolean hasMonster() {
    return cellTraps.containsKey(CellTraps.MONSTER);
  }

  @Override
  public boolean hasPit() {
    return cellTraps.containsKey(CellTraps.PIT);
  }

  @Override
  public boolean hasThief() {
    return cellTraps.containsKey(CellTraps.THIEF);
  }

  @Override
  public void hitMonster() {
    if (cellTraps.size() > 0) {
      int monsterHealth = cellTraps.get(CellTraps.MONSTER);
      monsterHealth -= SINGLE_HIT_DAMAGE;
      if (monsterHealth == 0) {
        cellTraps.remove(CellTraps.MONSTER);
      } else {
        cellTraps.put(CellTraps.MONSTER, monsterHealth);
      }
    }
  }

  @Override
  public Integer getMonsterHealth() {
    if (cellTraps.size() > 0) {
      return cellTraps.get(CellTraps.MONSTER);
    }
    return null;
  }
}
