import controller.DungeonControllerV2;
import dungeon.Direction;
import view.DungeonView;

/**
 * This class represents a mock view for the Dungeon UI of the application
 *    and facilitates in controller-UI interaction testing.
 */
public class MockDungeonView implements DungeonView {

  private final StringBuilder log;

  /**
   * This constructor creates an instance of the mock dungeon view.
   * @param log appendable log.
   */
  public MockDungeonView(StringBuilder log) {
    this.log = log;
  }


  @Override
  public void refresh() {
    log.append("Refresh Called");
  }

  @Override
  public void makeVisible(int numRows, int numCol) {
    log.append("Making View Visible");
  }

  @Override
  public void addClickListener(DungeonControllerV2 listener) {
    log.append("Adding onClick listeners");
    listener.shootArrow(Direction.NORTH, 1);
    listener.movePlayer(Direction.NORTH);
    listener.movePlayer(Direction.SOUTH);
    listener.movePlayer(Direction.EAST);
    listener.movePlayer(Direction.WEST);
  }

  @Override
  public void setInfoMessage(String message) {
    log.append(String.format("Appending this info message: %s ", message));
  }

  @Override
  public void reRenderSidePanel() {
    log.append("Re-rendering side panel");
  }
}
