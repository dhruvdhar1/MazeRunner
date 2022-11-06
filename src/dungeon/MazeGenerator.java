package dungeon;

import java.util.List;
import java.util.Map;

/**
 * This package-private interface represents a maze of a dungeon and exposes all the operations
 * that can be performed on the maze.
 */
interface MazeGenerator {

  /**
   * Generate a maze for the dungeon using Kruskal's Algorithm.
   */
  void generateDungeon();

  /**
   * Get the starting point of the maze.
   * @return  starting point of type Cell.
   */
  Cell getEntryPoint();

  /**
   * Get the ending point of the maze.
   * @return  ending point of type Cell.
   */
  Cell getExitPoint();

  /**
   * Get the Adjacency List representation of the maze in (int) --> [int, int...] form.
   * @return adjacency list.
   */
  Map<Integer, List<Integer>> getAdjacencyList();
}
