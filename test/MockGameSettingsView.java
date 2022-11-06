import controller.DungeonControllerV2;
import view.GameSettings;

/**
 * This class represents a mock view for the setting UI of the application
 *    and facilitates in controller-UI interaction testing.
 */
public class MockGameSettingsView implements GameSettings {

  private final StringBuilder log;
  private int numRows;
  private int numCol;
  private int interconnectivity;
  private int numMonster;
  private boolean isWrapping;
  private double treasurePercent;
  private int numPit;
  private int numThief;

  /**
   * This contractor creates an instance of the mock settings view.
   * @param log appendable log.
   */
  public MockGameSettingsView(StringBuilder log) {
    log.append("Rendered Game Settings view\n");
    this.log = log;

  }

  /**
   * This contractor creates an instance of the mock settings view.
   * @param log appendable log.
   * @param numRows number of rows.
   * @param numCol  number of columns.
   * @param interconnectivity interconnectivity.
   * @param numMonster  number of monster.
   * @param isWrapping  is wrapping or not.
   * @param treasurePercent   treasure percentage.
   * @param numPit  number of pits.
   * @param numThief  number of thieves.
   */
  public MockGameSettingsView(StringBuilder log, int numRows, int numCol, int interconnectivity,
                              int numMonster, boolean isWrapping, double treasurePercent,
                              int numPit, int numThief) {
    log.append("Rendered Game Settings view\n");
    this.log = log;
    this.numRows = numRows;
    this.numCol = numCol;
    this.interconnectivity = interconnectivity;
    this.numMonster = numMonster;
    this.isWrapping = isWrapping;
    this.treasurePercent = treasurePercent;
    this.numPit = numPit;
    this.numThief = numThief;
  }

  @Override
  public void addClickListeners(DungeonControllerV2 listener) {
    log.append("Added event listener to game settings UI\n");
  }

  @Override
  public void makeVisible(boolean visibility) {
    if (visibility) {
      log.append("Setting window displayed");
    } else {
      log.append("Setting window Hidden");
    }
  }

  @Override
  public int getNumRows() {
    log.append(String.format("Num Rows: %s\n", numRows));
    return numRows;
  }

  @Override
  public int getNumCol() {
    log.append(String.format("Num Columns: %s\n", numCol));
    return numCol;
  }

  @Override
  public int getInterconnectivity() {
    log.append(String.format("Interconnectivity: %s\n", interconnectivity));
    return interconnectivity;
  }

  @Override
  public double getTreasurePercent() {
    log.append(String.format("Treasure percentage: %s\n", treasurePercent));
    return treasurePercent;
  }

  @Override
  public int getNumMonster() {
    log.append(String.format("Num monster: %s", numMonster));
    return numMonster;
  }

  @Override
  public boolean isWrapping() {
    log.append(String.format("Is wrapping: %s\n", isWrapping));
    return isWrapping;
  }

  @Override
  public int getNumPit() {
    log.append(String.format("Num pit: %s\n", numPit));
    return numPit;
  }

  @Override
  public int getNumThief() {
    log.append(String.format("Num thief: %s\n", numThief));
    return numThief;
  }
}
