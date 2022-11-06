package controller;

import static view.ViewConstants.ARROW_SHOT_MSG;
import static view.ViewConstants.CANNOT_SHOOT_MSG;
import static view.ViewConstants.COLLECT_TREASURE_MSG;
import static view.ViewConstants.EATEN_BY_OTYUGH;
import static view.ViewConstants.FELL_INTO_PIT;
import static view.ViewConstants.GAME_ALREADY_WON_MSG;
import static view.ViewConstants.INVALID_GAME_STATE_MSG;
import static view.ViewConstants.LIGHT_SMELL_MSG;
import static view.ViewConstants.PIT_NEARBY_MSG;
import static view.ViewConstants.PLAYER_DEAD_MSG;
import static view.ViewConstants.PLAYER_MOVED_MSG;
import static view.ViewConstants.STRONG_SMELL_MSG;
import static view.ViewConstants.TREASURE_COLLECTED_MSG;
import static view.ViewConstants.TREASURE_STOLEN_MSG;
import static view.ViewConstants.WIN_MSG;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import view.DungeonView;
import view.DungeonViewImpl;
import view.GameSettings;
import view.GameSettingsImpl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * This class represents the controller that acts as the
 * liaison between the view and the model and implements the operations to be
 * performed between the two.
 */
public class DungeonControllerV2Impl implements DungeonControllerV2 {

  private DungeonView dungeonView;
  private Dungeon model;
  private final GameSettings settingsUI;

  /**
   * This constructor creates an instance of the controller.
   */
  public DungeonControllerV2Impl() {
    this.model = null;
    this.dungeonView = null;
    settingsUI = new GameSettingsImpl();
  }

  /**
   * This constructor creates an instance of the controller and expects dungeonView,
   *      gameSettings view and model as parameters.
   * @throws IllegalArgumentException if null parameter passed
   */
  public DungeonControllerV2Impl(DungeonView dungeonView,
                                 GameSettings gameSettingsView,
                                 Dungeon model) {
    if (null == dungeonView || null == gameSettingsView || null == model) {
      throw new IllegalArgumentException("NUll value passed");
    }
    this.model = model ;
    this.dungeonView = dungeonView;
    settingsUI = gameSettingsView;
  }

  /**
   * Append info messages, informing the player about the monsters smell.
   * @return  info message.
   */
  private String appendSmellInfo() {
    if (model.lightSmellDetected()) {
      return LIGHT_SMELL_MSG;
    } else if (model.strongSmellDetected()) {
      return STRONG_SMELL_MSG;
    }
    return "";
  }

  /**
   * Append info messages, informing the player about a nearby pit.
   * @return  info message.
   */
  private String appendPitInfo() {
    if (model.pitDetected()) {
      return PIT_NEARBY_MSG;
    }
    return "";
  }

  @Override
  public void play() {
    settingsUI.addClickListeners(this);
    settingsUI.makeVisible(true);
  }

  @Override
  public void movePlayer(Direction d) {
    try {
      StringBuilder infoMessage = new StringBuilder();
      model.movePlayer(d);
      if (model.isGameOver() && !model.isPlayerWinner()) {
        if (model.hasPit()) {
          infoMessage.append(FELL_INTO_PIT);
        } else {
          infoMessage.append(EATEN_BY_OTYUGH);
        }
      } else if (model.isGameOver() && model.isPlayerWinner()) {
        infoMessage.append(WIN_MSG);
      } else {
        infoMessage.append(PLAYER_MOVED_MSG).append(d.toString().toLowerCase());
        if (model.getPlayerLocationDescription()
                .getAvailableItems().size() > 0) {
          infoMessage.append(COLLECT_TREASURE_MSG);
        }
        infoMessage.append(appendSmellInfo());
        infoMessage.append(appendPitInfo());
        if (model.hasThief()) {
          infoMessage.append(TREASURE_STOLEN_MSG);
          dungeonView.reRenderSidePanel();
        }
      }
      dungeonView.setInfoMessage(infoMessage.toString());
      dungeonView.refresh();
    } catch (IllegalArgumentException ex) {
      dungeonView.setInfoMessage(String.format("Invalid direction. Cannot move to %s",
              d.toString().toLowerCase()));
      System.out.println(INVALID_GAME_STATE_MSG);
    } catch (IllegalStateException ex) {
      if (model.isPlayerWinner()) {
        dungeonView.setInfoMessage(GAME_ALREADY_WON_MSG);
      } else {
        dungeonView.setInfoMessage(PLAYER_DEAD_MSG);
      }
      System.out.println("Invalid Game state. Trying to perform invalid action.");
    }
  }

  @Override
  public void collectTreasure(Set<Integer> itemsToCollect) {
    HttpURLConnection conn = new URL("").openConnection();
    StringBuilder infoMessage = new StringBuilder();
    for (int itemId: itemsToCollect) {
      model.collectTreasure(itemId);
    }
    infoMessage.append(TREASURE_COLLECTED_MSG).append(appendSmellInfo());
    dungeonView.setInfoMessage(infoMessage.toString());
    dungeonView.refresh();
  }

  @Override
  public void shootArrow(Direction d, int dist) {
    StringBuilder infoMessage = new StringBuilder();
    try {
      model.shootArrow(d, dist);
      infoMessage.append(ARROW_SHOT_MSG).append(appendSmellInfo());
    } catch (IllegalArgumentException ex) {
      infoMessage.append(CANNOT_SHOOT_MSG).append(appendSmellInfo());
    }
    dungeonView.setInfoMessage(infoMessage.toString());
    dungeonView.refresh();
  }

  @Override
  public void startGame(int numRows, int numCol, int interconnectivity, int numMonster,
                        boolean isWrapping, double treasurePercent, int numPit, int numThief) {
    try {
      this.model = new DungeonImpl(treasurePercent, numRows, numCol,
              interconnectivity, isWrapping, numMonster, numPit, numThief);
      model.enterDungeon();
      dungeonView = new DungeonViewImpl(model);
      settingsUI.makeVisible(false);
      dungeonView.makeVisible(numRows, numCol);
      dungeonView.addClickListener(this);
    } catch (IllegalArgumentException ex) {
      System.out.println("Invalid values provided");
    }
  }

  @Override
  public void initiateNewGame() {
    settingsUI.makeVisible(true);
  }

  @Override
  public void restartGame() {
    int numRows = settingsUI.getNumRows();
    int numCol = settingsUI.getNumCol();
    int numMonster = settingsUI.getNumMonster();
    int interconnectivity = settingsUI.getInterconnectivity();
    boolean isWrapping = settingsUI.isWrapping();
    double treasurePercent = settingsUI.getTreasurePercent();
    int numPit = settingsUI.getNumPit();
    int numThief = settingsUI.getNumThief();
    startGame(numRows, numCol, interconnectivity, numMonster, isWrapping,
            treasurePercent, numPit, numThief);
  }

  @Override
  public void quitGame() {
    System.exit(0);
  }
}
