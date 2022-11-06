package dungeon;

import java.util.List;
import java.util.Map;

/**
 * A Mock of the Dungeon model to simulate that controller is working as expected.
 */
public class MockDungeonModel implements Dungeon {

  private StringBuilder log;
  private final int mockedData;

  public MockDungeonModel(StringBuilder log, int mockedData) {
    this.log = log;
    this.mockedData = mockedData;
  }

  @Override
  public void enterDungeon() {
    log.append("Player enters the dungeon\n");
  }

  @Override
  public Map<Integer, ItemType> getPlayerDescription() {
    log.append("Get the player description\n");
    return Map.ofEntries(
            Map.entry(mockedData, ItemType.ARROW)
    );
  }

  @Override
  public PlayerLocationDescription getPlayerLocationDescription() {
    log.append("Get the player location description\n");
    Cell cell = new CellImpl(1, 2, 3);
    cell.addItems(ItemType.RUBY, 11);
    return new PlayerLocationDescription(cell);
  }

  @Override
  public void movePlayer(Direction directionToMove) {
    log.append(new StringBuilder("Move player in direction: ").append(directionToMove));
  }

  @Override
  public void collectTreasure(int treasureId) {
    log.append(new StringBuilder("Collecting Treasure with treasureId: ").append(treasureId));
  }

  @Override
  public boolean treasureAvailable() {
    log.append("Check if treasure is available\n");
    return false;
  }

  @Override
  public Map<Integer, List<Integer>> getAdjacencyList() {
    log.append("Get the adjacency list of the maze\n");
    return null;
  }

  @Override
  public boolean isGameOver() {
    log.append(" Check if game is over\n");
    return false;
  }

  @Override
  public boolean isPlayerWinner() {
    log.append(" Check if player has won the game\n");
    return false;
  }

  @Override
  public boolean lightSmellDetected() {
    log.append(" Check if light smell is detected\n");
    return false;
  }

  @Override
  public boolean strongSmellDetected() {
    log.append("Check if strong smell is detected\n");
    return false;
  }

  @Override
  public boolean pitDetected() {
    log.append("Pit detected nearby\n");
    return false;
  }

  @Override
  public boolean hasPit() {
    return false;
  }

  @Override
  public boolean hasThief() {
    return false;
  }

  @Override
  public void shootArrow(Direction direction, int distance) {
    log.append(new StringBuilder("Shoot Arrow in direction: ")
            .append(direction).append(", at distance: ").append(distance));
  }
}
