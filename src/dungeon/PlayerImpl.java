package dungeon;

import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This package-private class represents a player navigating his way through the
 * dungeon and implements all the operations that can be performed
 * on the player.
 */
class PlayerImpl implements Player {
  private static final int INJURED_MONSTER_HEALTH = 50;
  private static final int MONSTER_FULL_HEALTH = 100;
  private static final int TOSS_NUM_OUTCOMES = 2;
  private static final int MAX_NEIGHBOURS = 4;
  private Map<Integer, ItemType> collectedTreasure;
  private final Randomizer randomizer;
  private Cell currentLocation;
  private Cell entryCave;
  private boolean alive;

  /**
   * This constructor creates an instance of the PlayerImpl class.
   */
  public PlayerImpl(Randomizer randomizer) {
    entryCave = null;
    currentLocation = null;
    this.randomizer = randomizer;
    this.collectedTreasure = new HashMap<>();
    alive = Boolean.TRUE;
    get3Arrows();
  }

  /**
   * Add 3 arrows to player bag at the time of instantiation.
   */
  private void get3Arrows() {
    collectedTreasure.put(1, ItemType.ARROW);
    collectedTreasure.put(2, ItemType.ARROW);
    collectedTreasure.put(3, ItemType.ARROW);
  }

  @Override
  public Map<Integer, ItemType> getPlayerDescription() {
    return collectedTreasure;
  }

  @Override
  public PlayerLocationDescription getPlayerLocationDescription() {
    return new PlayerLocationDescription(currentLocation);
  }

  @Override
  public void movePlayer(Direction directionToMove) {
    if (!alive) {
      throw new IllegalStateException("Player is dead.");
    }
    Cell newLocation = currentLocation.getDirection(directionToMove);
    if (null == newLocation) {
      throw new IllegalArgumentException("Cannot move in this direction.");
    }
    currentLocation = newLocation;
    encounterPit();
    encounterThief();
    if (currentLocation.hasMonster()) {
      confrontMonster();
    }
  }

  /**
   * Perform the required action when a player moves into a cave with monster.
   */
  private void confrontMonster() {
    if (MONSTER_FULL_HEALTH == currentLocation.getMonsterHealth()) {
      alive = Boolean.FALSE;
    } else if (INJURED_MONSTER_HEALTH == currentLocation.getMonsterHealth()) {
      int coinToss = randomizer.getRandomNum(TOSS_NUM_OUTCOMES);
      alive = (coinToss == 1) ? Boolean.TRUE : Boolean.FALSE;
    }
  }

  private void encounterPit() {
    if (currentLocation.hasPit()) {
      alive = Boolean.FALSE;
    }
  }

  private void encounterThief() {
    if (currentLocation.hasThief()) {
      collectedTreasure = new HashMap<>();
    }
  }

  @Override
  public void collectTreasure(int treasureId) {
    if (!alive) {
      throw new IllegalStateException("Player is dead.");
    }
    if (currentLocation.getItems().size() == 0) {
      throw new IllegalArgumentException("No treasures present here.");
    }
    if (currentLocation.getItems().containsKey(treasureId)) {
      ItemType removedTreasure = currentLocation.removeItem(treasureId);
      collectedTreasure.put(treasureId, removedTreasure);
    } else {
      throw new IllegalArgumentException("Invalid treasure id provided.");
    }
  }

  @Override
  public void setEntryPoint(Cell entryPoint) {
    if (null == entryPoint) {
      throw new IllegalArgumentException("null parameter provided");
    }
    this.entryCave = entryPoint;
  }

  @Override
  public Cell getCurrentLocation() {
    return currentLocation;
  }

  @Override
  public void enterMaze() {
    currentLocation = entryCave;
  }

  /**
   * Check if player has arrows before shooting arrow.
   * @throws IllegalStateException  if player has no arrows.
   */
  private void useArrow() {
    for (int key: collectedTreasure.keySet()) {
      if (collectedTreasure.get(key).equals(ItemType.ARROW)) {
        collectedTreasure.remove(key);
        return;
      }
    }
    throw new IllegalStateException("No available arrows!");
  }

  /**
   * Get the exit direction of the tunnel.
   * @param direction the direction from which arrow enters.
   * @param tunnel the tunnels object reference
   * @return  exit direction.
   */
  private Direction getTunnelOtherSide(Direction direction, Cell tunnel) {
    Direction[] directions = {Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH};
    Map<Direction, Direction> oppDirections = Map.ofEntries(
            Map.entry(Direction.NORTH, Direction.SOUTH),
            Map.entry(Direction.SOUTH, Direction.NORTH),
            Map.entry(Direction.EAST, Direction.WEST),
            Map.entry(Direction.WEST, Direction.EAST)
    );
    for (Direction dir: directions) {
      if (tunnel.getDirection(dir) != null && direction != oppDirections.get(dir)) {
        return dir;
      }
    }
    return null;
  }

  @Override
  public void shootArrow(int distance, Direction direction) {
    if (!alive) {
      throw new IllegalStateException("Player is dead.");
    }
    Cell arrowLocation = new CellImpl(currentLocation);
    int distanceTravelled = 0;
    useArrow();
    while (distanceTravelled < distance) {
      LocationType type = arrowLocation.getLocationType();
      if (type.equals(LocationType.TUNNEL)) {
        direction = getTunnelOtherSide(direction, arrowLocation);
        arrowLocation = arrowLocation.getDirection(direction);
      } else {
        arrowLocation = arrowLocation.getDirection(direction);
      }
      if (null == arrowLocation) {
        break;
      } else if (arrowLocation.getLocationType().equals(LocationType.CAVE)) {
        distanceTravelled++;
      }
    }
    if (null != arrowLocation && arrowLocation.hasMonster()) {
      arrowLocation.hitMonster();
    }
  }

  @Override
  public boolean isPlayerAlive() {
    return alive;
  }

  /**
   * Run a bfs algorithm to scan all the neighbours at a distance of 2 hops
   *    and return the number of caves that contains a monster.
   * @return  number of distant neighbours with a monster.
   */
  private int scanNeighbours() {
    int distantNeighboursWithMonster = 0;
    Queue<Cell> bfs = new LinkedList<>();
    List<Integer> visitedNodes = new ArrayList<>();
    visitedNodes.add(currentLocation.getCaveId());
    bfs.add(currentLocation.getNorth());
    bfs.add(currentLocation.getSouth());
    bfs.add(currentLocation.getEast());
    bfs.add(currentLocation.getWest());
    for (int i = 0; i < MAX_NEIGHBOURS; i++) {
      Cell node = bfs.remove();
      if (null != node) {
        visitedNodes.add(node.getCaveId());
        List<Cell> neighbours = new ArrayList<>();
        neighbours.add(node.getNorth());
        neighbours.add(node.getSouth());
        neighbours.add(node.getWest());
        neighbours.add(node.getEast());
        for (Cell neighbour: neighbours) {
          if (null != neighbour && !visitedNodes.contains(neighbour.getCaveId())) {
            if (!bfs.contains(neighbour)) {
              bfs.add(neighbour);
            }
          }
        }
      }
    }
    for (Cell distantNeighbour: bfs) {
      if (null != distantNeighbour && distantNeighbour.hasMonster()) {
        distantNeighboursWithMonster++;
      }
    }
    return distantNeighboursWithMonster;
  }

  @Override
  public boolean getLightSmell() {
    return scanNeighbours() == 1 && !getStrongSmell();
  }

  @Override
  public boolean isPitNearby() {
    Cell north = currentLocation.getNorth();
    Cell south = currentLocation.getSouth();
    Cell west = currentLocation.getWest();
    Cell east = currentLocation.getEast();
    if (null != north && north.hasPit()) {
      return true;
    } else if (null != south && south.hasPit()) {
      return true;
    } else if (null != west && west.hasPit()) {
      return true;
    }
    return (null != east && east.hasPit());
  }

  /**
   * Check if any of the immediate cells contain a monster.
   * @param location  player's current location.
   * @return  true if immediate neighbours contain a monster.
   */
  private boolean checkNeighbourForMonster(Cell location) {
    Cell north = location.getNorth();
    Cell south = location.getSouth();
    Cell west = location.getWest();
    Cell east = location.getEast();
    if (null != north && north.hasMonster()) {
      return true;
    } else if (null != south && south.hasMonster()) {
      return true;
    } else if (null != west && west.hasMonster()) {
      return true;
    }
    return (null != east && east.hasMonster());
  }

  @Override
  public boolean getStrongSmell() {
    boolean checkImmediateNeighbours = checkNeighbourForMonster(currentLocation);
    int numMonstersAt2Hops = scanNeighbours();
    return checkImmediateNeighbours || (numMonstersAt2Hops > 1);
  }

}
