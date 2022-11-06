package dungeon;

/**
 * This package-private class represents an edge between 2 nodes of a graph.
 */
class Edge {
  private final int from;
  private final int to;

  /**
   * This constructor creates an instance of the Edge class.
   * @param from source point
   * @param to target point
   */
  Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }

  /**
   * Get the id of the source node.
   * @return  source node id
   */
  int getFrom() {
    return from;
  }

  /**
   * Get the id of the destination node.
   * @return  destination node id
   */
  int getTo() {
    return to;
  }
}
