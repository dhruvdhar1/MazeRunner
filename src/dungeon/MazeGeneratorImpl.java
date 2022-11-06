package dungeon;

import randomizer.Randomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This package-private class represents a maze of a dungeon and implements
 * all the operations of the maze.
 */
class MazeGeneratorImpl implements MazeGenerator {

  private static final int MIN_DISTANCE = 5;
  private static final int TREASURE_ID_START_INDEX = 4;
  private final Map<Integer, Integer> disjointSet;
  private final Map<Integer, Cell> dungeon;
  private final List<Edge> possiblePaths;
  private final List<Edge> leftOverEdges;
  private final List<Edge> wrappingEdges;
  private final double treasurePercent;
  private final Randomizer randomizer;
  private final int interconnectivity;
  private final boolean isWrapping;
  private final int numMonster;
  private final int numThief;
  private final int numPit;
  private final int numColumns;
  private final int numRows;
  private int maxAttempts;
  private int treasureId;
  private Cell entryPoint;
  private Cell exitPoint;

  /**
   * This constructor creates an instance of the MazeGenerator class.
   * @param numRows number of rows in the resulting maze.
   * @param numColumns  number of columns in the resulting maze.
   * @param interconnectivity interconnectivity of resulting maze.
   * @param isWrapping  whether maze needs to be a wrapping maze.
   * @param treasurePercent percentage of caves with treasure
   * @param randomizer  instance of randomizer class.
   */
  public MazeGeneratorImpl(int numRows, int numColumns, int interconnectivity, boolean isWrapping,
                           double treasurePercent, int numMonster, int numPit,
                           int numThief, Randomizer randomizer) {
    this.interconnectivity = interconnectivity;
    this.treasurePercent = treasurePercent;
    this.isWrapping = isWrapping;
    this.randomizer = randomizer;
    this.numColumns = numColumns;
    this.numRows = numRows;
    this.numMonster = numMonster;
    this.numThief = numThief;
    this.numPit = numPit;
    this.possiblePaths = new ArrayList<>();
    this.wrappingEdges = new ArrayList<>();
    this.leftOverEdges = new ArrayList<>();
    this.disjointSet = new HashMap<>();
    this.dungeon = new HashMap<>();
    entryPoint = null;
    exitPoint = null;
    treasureId = TREASURE_ID_START_INDEX;
  }

  /**
   * Generate wrapping edge pair for the given grid.
   * @param numRows number of rows in the grid.
   * @param numColumns  number of columns in the grid.
   */
  private void generateWrappingGraphEdges(int numRows, int numColumns) {
    int gridSize = numRows * numColumns;
    for (int i = 1; i <= gridSize; i += numColumns) {
      wrappingEdges.add(new Edge(i, i + numColumns - 1));
    }
    for (int i = 1; i <= numColumns; i++) {
      wrappingEdges.add(new Edge(i, gridSize - numColumns + i));
    }
    Collections.shuffle(wrappingEdges, randomizer.getRandom());
  }

  /**
   * Generate all possible non-wrapping edge pair for the given grid.
   * @param numRows number of rows in the grid.
   * @param numColumns  number of columns in the grid.
   */
  private void generateGraphEdges(int numRows, int numColumns) {
    int gridSize = numRows * numColumns;
    for (int i = 1; i <= gridSize; i++) {
      if (i + numColumns <= gridSize) {
        possiblePaths.add(new Edge(i, (i + numColumns)));
      }
      if (i % numColumns == 0) {
        continue;
      }
      possiblePaths.add(new Edge(i, i + 1));
    }
    if (isWrapping) {
      generateWrappingGraphEdges(numRows, numColumns);
    }
  }

  /**
   * Performs union operation on set of nodes of a graph.
   * @param src source node id.
   * @param dest  target node id.
   */
  private void union(int src, int dest) {
    int srcParent = find(src);
    int destParent = find(dest);
    disjointSet.put(srcParent, destParent);
  }

  /**
   * Performs find operation on the disjoint set of a graph.
   * @param node  node to be searched.
   */
  private int find(int node) {
    if (disjointSet.get(node).equals(node)) {
      return node;
    } else {
      return find(disjointSet.get(node));
    }
  }

  /**
   * Get a random treasure to be added to a Cave.
   * @param randomizer  instance of randomizer class.
   * @return  treasure to be added.
   * @throws IllegalArgumentException if passed randomizer object is null.
   */
  private ItemType getRandomTreasure(Randomizer randomizer) {
    if (null == randomizer) {
      throw new IllegalArgumentException("Invalid parameter passed");
    }
    int treasureNum = randomizer.getRandomNum(3) + 1;
    return ItemType.getTreasureUsingIndex(treasureNum);
  }

  /**
   * Initializes the nodes of the graph by generating edges,
   * instantiating cell nodes and setting an entry point.
   */
  private void init() {
    generateGraphEdges(numRows, numColumns); //generates maximum spanning tree edges.
    int caveId = 1;
    for (int i = 1; i <= numRows; i++) {
      for (int j = 1; j <= numColumns; j++) {
        Cell cell = new CellImpl(caveId, i, j);
        dungeon.put(caveId, cell);
        disjointSet.put(caveId, caveId);
        caveId++;
      }
    }
    Collections.shuffle(possiblePaths, randomizer.getRandom());
  }

  /**
   * Connects wrapping edges of a graph with each other.
   */
  private void connectWrappingEdges() {
    int edgesToPick = randomizer.getRandomNum(wrappingEdges.size());
    for (int i = 0; i < edgesToPick; i++) {
      Edge randomWrappingEdge = wrappingEdges.get(0);
      Cell source = dungeon.get(randomWrappingEdge.getFrom());
      Cell target = dungeon.get(randomWrappingEdge.getTo());
      if (source.getRowLocation() == target.getRowLocation()) {
        if (target.getColumnLocation() - source.getColumnLocation() > 0) {
          source.setWest(target);
          target.setEast(source);
        } else {
          source.setEast(target);
          target.setWest(source);
        }
      }

      if (source.getColumnLocation() == target.getColumnLocation()) {
        if (target.getRowLocation() - source.getRowLocation() > 0) {
          source.setNorth(target);
          target.setSouth(source);
        } else {
          source.setSouth(target);
          target.setNorth(source);
        }
      }
      wrappingEdges.remove(randomWrappingEdge);
    }
  }

  /**
   * Generates a minimum spanning tree using Kruskal's algorithm.
   */
  private void generateMst() {
    while (possiblePaths.size() > 0) {
      Edge randomEdge;
      if (null != possiblePaths.get(0)) {
        randomEdge = possiblePaths.get(0);
        int parent1 = find(randomEdge.getFrom());
        int parent2 = find(randomEdge.getTo());
        //if modes are in different sets, then connect them.
        if (parent1 == parent2) {
          leftOverEdges.add(randomEdge);
          possiblePaths.remove(randomEdge);
          continue;
        }
      } else {
        continue;
      }
      //perform union operation on connected nodes.
      union(randomEdge.getTo(), randomEdge.getFrom());
      Cell source = dungeon.get(randomEdge.getFrom());
      Cell target = dungeon.get(randomEdge.getTo());
      if (null != source && null != target) {
        connectCaves(source, target);
        possiblePaths.remove(randomEdge);
        Collections.shuffle(possiblePaths, randomizer.getRandom());
      }
    }
  }

  /**
   * Applies the user provided interconnectivity to the generated
   * minimum spanning tree.
   * @throws IllegalStateException  if user provided interconnectivity
   *                                is > no. of leftover edges.
   */
  private void applyInterConnectivity() {
    if (interconnectivity > leftOverEdges.size()) {
      throw new IllegalArgumentException("Invalid interconnectivity");
    }
    Edge randomLeftOverEdge;
    int originalSize = leftOverEdges.size();
    while (leftOverEdges.size() > (originalSize - interconnectivity)) {
      if (null != leftOverEdges.get(0)) {
        randomLeftOverEdge = leftOverEdges.get(0);
        Cell source = dungeon.get(randomLeftOverEdge.getFrom());
        Cell target = dungeon.get(randomLeftOverEdge.getTo());
        if (null != source && null != target) {
          connectCaves(source, target);
          leftOverEdges.remove(randomLeftOverEdge);
          Collections.shuffle(leftOverEdges, randomizer.getRandom());
        }
      }
    }
  }

  /**
   * Connects non-wrapping edges of a graph with each other.
   * @param source source node.
   * @param target destination node.
   */
  private void connectCaves(Cell source, Cell target) {
    //connect caves that are in the same row.
    if (source.getRowLocation() == target.getRowLocation()) {
      if (target.getColumnLocation() - source.getColumnLocation() > 0) {
        source.setEast(target);
        target.setWest(source);
      } else {
        source.setWest(target);
        target.setEast(source);
      }
    }

    //connect caves that are in the same column.
    if (source.getColumnLocation() == target.getColumnLocation()) {
      if (target.getRowLocation() - source.getRowLocation() > 0) {
        source.setSouth(target);
        target.setNorth(source);
      } else {
        source.setNorth(target);
        target.setSouth(source);
      }
    }
  }

  /**
   * Marks the exit node in the maze and ensures a minimum of 5 hops between
   * the start and the exit cave. Also, ensures that the exit point is a maze.
   */
  private void markExitCave() {
    Map<Integer, Integer> distance = new HashMap<>();
    for (int i = 1; i <= (numRows * numColumns); i++) {
      distance.put(i, -1);
    }
    //perform bfs to find the shortest path to every node from current location.
    bfs(entryPoint, distance);
    int maxDist = -1;
    int mostDistantNode = -1;

    //Set the most distant CAVE as the exit point.
    for (int k = 1; k <= distance.size(); k++) {
      int dist = distance.get(k);
      if (dist > maxDist && dungeon.get(k).getLocationType().equals(LocationType.CAVE)) {
        maxDist = dist;
        mostDistantNode = k;
      }
    }
    if (maxDist >= MIN_DISTANCE) {
      exitPoint = dungeon.get(mostDistantNode);
      exitPoint.putMonster();
      return;
    } else {
      if (maxAttempts < 5) {
        maxAttempts++;
        generateDungeon();
      }
    }
    if (null == exitPoint) {
      throw new IllegalArgumentException("Dungeon is too small. Cannot generate an Exit point.");
    }
  }

  /**
   * Performs a Breadth First search to find a cave that is mre than 5 hops away from the starting
   * node.
   * @param source starting point of maze, relative to which distance has to be measured.
   * @throws IllegalArgumentException if the provided distance map is null.
   */
  private void bfs(Cell source, Map<Integer, Integer> distance) {
    if (null == distance) {
      throw new IllegalArgumentException("Invalid parameter passed");
    }
    distance.put(source.getCaveId(), 0);
    Queue<Cell> q = new LinkedList<>();
    q.add(source);
    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        Cell vertex = q.remove();
        List<Cell> neighbours = new ArrayList<>();
        neighbours.add(vertex.getEast());
        neighbours.add(vertex.getSouth());
        neighbours.add(vertex.getWest());
        neighbours.add(vertex.getNorth());
        for (Cell neighbour: neighbours) {
          if (null != neighbour) {
            int dist = distance.get(neighbour.getCaveId());
            if (dist == -1) {
              int newDist = distance.get(vertex.getCaveId()) + 1;
              distance.put(neighbour.getCaveId(), newDist);
              q.add(neighbour);
            }
          }
        }
      }
    }
  }

  /**
   * Generates the maze for the dungeon using Kruskal's algorithm.
   */
  @Override
  public void generateDungeon() {
    init(); //step-1: initializes the graph nodes.
    generateMst(); //step-2: generates a minimum spanning tree.
    applyInterConnectivity(); //step-3: apply the user provided interconnectivity
    if (isWrapping) {
      connectWrappingEdges(); //step-4: connect wrapping edges.
    }
    markEntryCave();
    markExitCave(); //step-5: mark exit caves and add 1 monster.
    addTreasure(); //step-6: add treasure to cave.

    addMonster(); //step-7: add monster to caves.
    addArrows(); //step-8: add arrows to the caves.
    addPit(); //step-9 add pits in the caves.
    addThief(); //step-10 add thief in the caves.
  }

  /**
   * Marks the entry cave in the maze and that a cave is selected as the entry point.
   * the start and the exit cave.
   */
  private void markEntryCave() {
    for (int i = 1; i <= (numRows * numColumns); i++) {
      Cell cell = dungeon.get(i);
      if (cell.getLocationType().equals(LocationType.CAVE)) {
        entryPoint = cell;
        break;
      }
    }
  }

  /**
   * Adds treasure to a user specified percentage of caves randomly.
   */
  private void addTreasure() {
    int numCaves = 0;
    int treasureId = 1;
    List<Integer> caveIds = new ArrayList<>();
    //get the list of all the caves in the maze.
    for (int i = 1; i <= (numRows * numColumns); i++) {
      Cell cell = dungeon.get(i);
      if (cell.getLocationType().equals(LocationType.CAVE)) {
        caveIds.add(i);
        numCaves++;
      }
    }
    int numCaveWithTreasure = (int) Math.ceil((numCaves * treasurePercent) / 100);
    //randomly adds x amount of treasure to cave.
    for (int j = 0; j < numCaveWithTreasure; j++) {
      Collections.shuffle(caveIds, randomizer.getRandom());
      Cell cell = dungeon.get(caveIds.get(0));
      caveIds.remove(0);
      int numTreasure = randomizer.getRandomNum(MIN_DISTANCE) + 1;
      for (int k = 0; k < numTreasure; k++) {
        ItemType treasure = getRandomTreasure(randomizer);
        if (null != treasure) {
          cell.addItems(treasure, this.treasureId++);
        }
      }
    }
  }

  /**
   * Randomly add arrows to the user specified percentage of cells of the dungeon.
   */
  private void addArrows() {
    List<Integer> cellIds = new ArrayList<>();
    for (int i = 1; i <= (numRows * numColumns); i++) {
      cellIds.add(i);
    }
    int numCellsWithArrows = (int) Math.ceil((numRows * numColumns * treasurePercent) / 100);
    for (int j = 0; j < numCellsWithArrows; j++) {
      Collections.shuffle(cellIds, randomizer.getRandom());
      Cell cell = dungeon.get(cellIds.get(0));
      cellIds.remove(0);
      int numArrows = randomizer.getRandomNum(MIN_DISTANCE) + 1;
      for (int k = 0; k < numArrows; k++) {
        cell.addItems(ItemType.ARROW, this.treasureId++);
      }
    }
  }

  private List<Integer> getCaveList() {
    int numCaves = 0;
    List<Integer> caveIds = new ArrayList<>();
    //get the list of all the caves in the maze.
    for (int i = 1; i <= (numRows * numColumns); i++) {
      Cell cell = dungeon.get(i);
      if (cell.getLocationType().equals(LocationType.CAVE)
              && cell.getCaveId() != entryPoint.getCaveId()
              && cell.getCaveId() != exitPoint.getCaveId()) {
        caveIds.add(i);
        numCaves++;
      }
    }
    if (numMonster > numCaves) {
      throw new IllegalArgumentException("Trying to add too many monsters");
    }
    return caveIds;
  }

  /**
   * Add user specified 'n' monsters to randomly selected caves in the dungeon.
   */
  private void addMonster() {
    List<Integer> caveIds = getCaveList();
    for (int j = 0; j < numMonster - 1; j++) {
      Collections.shuffle(caveIds, randomizer.getRandom());
      Cell cell = dungeon.get(caveIds.get(0));
      cell.putMonster();
      caveIds.remove(0);
    }
  }

  /**
   * Add user specified 'n' pits to randomly selected caves in the dungeon.
   */
  private void addPit() {
    List<Integer> caveIds = getCaveList();
    Collections.shuffle(caveIds, randomizer.getRandom());
    Collections.shuffle(caveIds, randomizer.getRandom());
    for (int j = 0; j < numPit; j++) {
      Collections.shuffle(caveIds, randomizer.getRandom());
      Cell cell = dungeon.get(caveIds.get(0));
      cell.putPit();
      caveIds.remove(0);
    }
  }

  /**
   * Add user specified 'n' thief to randomly selected caves in the dungeon.
   */
  private void addThief() {
    List<Integer> caveIds = getCaveList();
    for (int j = 0; j < numThief; j++) {
      Collections.shuffle(caveIds, randomizer.getRandom());
      Cell cell = dungeon.get(caveIds.get(0));
      cell.putThief();
      caveIds.remove(0);
    }
  }

  @Override
  public Cell getEntryPoint() {
    return entryPoint;
  }

  @Override
  public Cell getExitPoint() {
    return exitPoint;
  }

  @Override
  public Map<Integer, List<Integer>> getAdjacencyList() {
    Map<Integer, List<Integer>> adjList = new HashMap<>();
    for (Cell c : dungeon.values()) {
      List<Integer> linkedCaves = new ArrayList<>();
      if (null != c.getNorth()) {
        linkedCaves.add(c.getNorth().getCaveId());
      }
      if (null != c.getEast()) {
        linkedCaves.add(c.getEast().getCaveId());
      }
      if (null != c.getSouth()) {
        linkedCaves.add(c.getSouth().getCaveId());
      }
      if (null != c.getWest()) {
        linkedCaves.add(c.getWest().getCaveId());
      }
      adjList.put(c.getCaveId(), linkedCaves);
    }
    return adjList;
  }

}
