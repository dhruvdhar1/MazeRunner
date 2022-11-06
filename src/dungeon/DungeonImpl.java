package dungeon;

import randomizer.Randomizer;
import randomizer.RandomizerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a dungeon of size (N x M) that can be traversed by a player
 * and implements the functionality exposed by the dungeon.
 */
public class DungeonImpl implements Dungeon {

  private final double treasurePercent;
  private final int numRows;
  private final int numColumns;
  private final int numMonster;
  private final int numPit;
  private final int numThief;
  private final int interconnectivity;
  private final boolean isWrapping;
  private final Player player;
  private final Randomizer randomizer;
  private Cell exitPoint;
  private Map<Integer, List<Integer>> adjList;
  private boolean gameStarted;

  /**
   * This constructor is used to create a randomized instance of a dungeon.
   * @param treasurePercent percentage of caves that should contain treasure.
   * @param numRows number of rows in the dungeon.
   * @param numColumns  number of columns in the dungeon.
   * @param interconnectivity the interconnectivity between nodes.
   * @param isWrapping  whether dungeon should be wrapping or not.
   * @param numMonster  number of monsters to add in the dungeon.
   * @throws IllegalArgumentException if {@code numRows <= 2}
   * @throws IllegalArgumentException if {@code numColumns <= 2}
   * @throws IllegalArgumentException if {@code interconnectivity < 0
   *                                          || interconnectivity > noLeftOverEdges}
   * @throws IllegalArgumentException if {@code numMonster < 1}
   * @throws IllegalArgumentException if {@code treasurePercent < 0 || treasurePercent > 100}
   */
  public DungeonImpl(double treasurePercent, int numRows, int numColumns,
                     int interconnectivity, boolean isWrapping, int numMonster) {
    validateInput(treasurePercent, numRows, numColumns, interconnectivity,
            numMonster, 0, 0);
    this.treasurePercent = treasurePercent;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.numMonster = numMonster;
    this.numPit = 0;
    this.numThief = 0;
    this.interconnectivity = interconnectivity;
    this.isWrapping = isWrapping;
    this.randomizer = new RandomizerImpl();
    this.player = new PlayerImpl(this.randomizer);
    this.adjList = null;
    this.exitPoint = null;
    gameStarted = Boolean.FALSE;
    createDungeon();
  }

  /**
   * This constructor is used to create a randomized instance of a dungeon.
   * @param treasurePercent percentage of caves that should contain treasure.
   * @param numRows number of rows in the dungeon.
   * @param numColumns  number of columns in the dungeon.
   * @param interconnectivity the interconnectivity between nodes.
   * @param isWrapping  whether dungeon should be wrapping or not.
   * @param numMonster  number of monsters to add in the dungeon.
   * @param numPit  number of pits to add in the dungeon.
   * @throws IllegalArgumentException if {@code numRows <= 2}
   * @throws IllegalArgumentException if {@code numColumns <= 2}
   * @throws IllegalArgumentException if {@code interconnectivity < 0
   *                                          || interconnectivity > noLeftOverEdges}
   * @throws IllegalArgumentException if {@code numMonster < 1}
   * @throws IllegalArgumentException if {@code treasurePercent < 0 || treasurePercent > 100}
   */
  public DungeonImpl(double treasurePercent, int numRows, int numColumns,
                     int interconnectivity, boolean isWrapping, int numMonster,
                     int numPit, int numThief) {
    validateInput(treasurePercent, numRows, numColumns, interconnectivity,
            numMonster, numPit, numThief);
    this.treasurePercent = treasurePercent;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.numMonster = numMonster;
    this.numPit = numPit;
    this.numThief = numThief;
    this.interconnectivity = interconnectivity;
    this.isWrapping = isWrapping;
    this.randomizer = new RandomizerImpl();
    this.player = new PlayerImpl(this.randomizer);
    this.adjList = null;
    this.exitPoint = null;
    gameStarted = Boolean.FALSE;
    createDungeon();
  }

  /**
   * This constructor is used to create a pre-seeded instance of a dungeon.
   * @param treasurePercent percentage of caves that should contain treasure.
   * @param numRows number of rows in the dungeon.
   * @param numColumns  number of columns in the dungeon.
   * @param interconnectivity the interconnectivity between nodes.
   * @param isWrapping  whether dungeon should be wrapping or not.
   * @param numMonster  number of monsters to add in the dungeon.
   * @throws IllegalArgumentException if {@code numRows <= 2}
   * @throws IllegalArgumentException if {@code numColumns <= 2}
   * @throws IllegalArgumentException if {@code interconnectivity < 0
   *                                          || interconnectivity > noLeftOverEdges}
   * @throws IllegalArgumentException if {@code numMonster < 1}
   * @throws IllegalArgumentException if {@code treasurePercent < 0 || treasurePercent > 100}
   */
  public DungeonImpl(double treasurePercent, int numRows, int numColumns, int interconnectivity,
                     boolean isWrapping, int numMonster, Randomizer randomizer) {
    validateInput(treasurePercent, numRows, numColumns, interconnectivity,
            numMonster, 0, 0);
    this.treasurePercent = treasurePercent;
    this.numRows = numRows;
    this.numColumns = numColumns;
    this.numMonster = numMonster;
    this.numPit = 0;
    this.numThief = 0;
    this.interconnectivity = interconnectivity;
    this.isWrapping = isWrapping;
    this.randomizer = randomizer;
    this.player = new PlayerImpl(this.randomizer);
    this.adjList = null;
    this.exitPoint = null;
    gameStarted = Boolean.FALSE;
    createDungeon();
  }

  /**
   * Creates a dungeon for the game and informs the player of maze's entry location.
   */
  private void createDungeon() {
    MazeGenerator maze = new MazeGeneratorImpl(numRows, numColumns, interconnectivity,
            isWrapping, treasurePercent, numMonster, numPit, numThief, randomizer);
    maze.generateDungeon();
    this.adjList = maze.getAdjacencyList();
    exitPoint = maze.getExitPoint();
    player.setEntryPoint(maze.getEntryPoint());
  }

  @Override
  public void enterDungeon() {
    gameStarted = Boolean.TRUE;
    player.enterMaze();
  }

  @Override
  public Map<Integer, ItemType> getPlayerDescription() {
    Map<Integer, ItemType> treasureListCopy = new HashMap<>();
    Map<Integer, ItemType> playerDesc =  player.getPlayerDescription();
    Set<Integer> keySet = playerDesc.keySet();
    for (int key: keySet) {
      ItemType treasureCopy = playerDesc.get(key);
      treasureListCopy.put(key, treasureCopy);
    }
    return treasureListCopy; //returning defensive copy.
  }

  @Override
  public PlayerLocationDescription getPlayerLocationDescription() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }
    return new PlayerLocationDescription(player
            .getPlayerLocationDescription()); //returning defensive copy.
  }

  @Override
  public void collectTreasure(int treasureId) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }
    if (treasureId < 0) {
      throw new IllegalArgumentException("Invalid treasure id provided.");
    }
    player.collectTreasure(treasureId);
  }

  @Override
  public boolean treasureAvailable() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }
    return player.getCurrentLocation().getItems().values().size() > 0;
  }

  @Override
  public Map<Integer, List<Integer>> getAdjacencyList() {
    Map<Integer, List<Integer>> adjListCopy = new HashMap<>();
    if (null != adjList) {
      for (int i = 1; i <= adjList.size(); i++) {
        List<Integer> neighbours = new ArrayList<>(adjList.get(i));
        adjListCopy.put(i, neighbours);
      }
      return adjListCopy; //returning defensive copy.
    }
    return null;
  }

  @Override
  public void movePlayer(Direction directionToMove) {
    if (!gameStarted) {
      throw new IllegalStateException("Player has not entered dungeon yet.");
    }
    if (null == directionToMove) {
      throw new IllegalArgumentException("null direction provided");
    }
    if (isGameOver()) {
      throw new IllegalStateException("Game is over");
    }
    player.movePlayer(directionToMove);
  }

  /**
   * Validates user provided input.
   * @param treasurePercent percentage of caves that should contain treasure.
   * @param numRows number of rows in the dungeon.
   * @param numColumns  number of columns in the dungeon.
   * @param interconnectivity the interconnectivity between nodes.
   * @throws IllegalArgumentException if {@code numRows <= 2}
   * @throws IllegalArgumentException if {@code numColumns <= 2}
   * @throws IllegalArgumentException if {@code interconnectivity < 0
   *                                          || interconnectivity > noLeftOverEdges}
   * @throws IllegalArgumentException if {@code numMonster < 0}
   * @throws IllegalArgumentException if {@code numPit < 0}
   * @throws IllegalArgumentException if {@code numThief < 0}
   * @throws IllegalArgumentException if {@code treasurePercent < 0 || treasurePercent > 100}
   */
  private void validateInput(double treasurePercent, int numRows, int numColumns,
                             int interconnectivity, int numMonster, int numPit, int numThief) {
    if (treasurePercent < 0 || treasurePercent > 100) {
      throw new IllegalArgumentException("Invalid treasure percentage value provided.");
    }
    if (numColumns <= 3 && numRows <= 3) {
      throw new IllegalArgumentException("number of rows and columns should be > 4");
    }
    if (numColumns > 100 || numRows > 100) {
      throw new IllegalArgumentException("number of rows and columns should be <= 100");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be negative");
    }
    if (numMonster < 1) {
      throw new IllegalArgumentException("Number of monsters cannot be less than 1");
    }
    if (numPit < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be negative");
    }
    if (numThief < 0) {
      throw new IllegalArgumentException("Interconnectivity cannot be negative");
    }
  }

  @Override
  public boolean isGameOver() {
    return !player.isPlayerAlive()
            || player.getCurrentLocation().getCaveId() == exitPoint.getCaveId();
  }

  @Override
  public boolean isPlayerWinner() {
    return player.isPlayerAlive()
            && player.getCurrentLocation().getCaveId() == exitPoint.getCaveId();
  }

  @Override
  public boolean lightSmellDetected() {
    return player.getLightSmell();
  }

  @Override
  public boolean pitDetected() {
    return player.isPitNearby();
  }

  @Override
  public boolean hasPit() {
    return player.getCurrentLocation().hasPit();
  }

  @Override
  public boolean hasThief() {
    return player.getCurrentLocation().hasThief();
  }

  @Override
  public boolean strongSmellDetected() {
    return player.getStrongSmell();
  }

  @Override
  public void shootArrow(Direction direction, int distance) {
    if (null == direction || null == player.getCurrentLocation().getDirection(direction)) {
      throw new IllegalArgumentException("Invalid or null direction");
    } else if (distance <= 0 || distance > 5) {
      throw new IllegalArgumentException("Invalid distance");
    }
    player.shootArrow(distance, direction);
  }
}
