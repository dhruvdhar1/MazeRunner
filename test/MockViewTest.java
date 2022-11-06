import static org.junit.Assert.assertEquals;

import controller.DungeonControllerV2;
import controller.DungeonControllerV2Impl;
import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.MockDungeonModel;
import org.junit.Before;
import org.junit.Test;
import view.GameSettings;

import java.util.Set;

/**
 * This test class tests the controller-view interaction using a mock view.
 */
public class MockViewTest {

  MockDungeonView dungeonView;
  MockGameSettingsView gameSettingsView;
  DungeonControllerV2 controllerV2;
  Dungeon model;
  StringBuilder viewOut;

  @Before
  public void setUp() throws Exception {
    viewOut = new StringBuilder();
    dungeonView = new MockDungeonView(viewOut);
    model = new MockDungeonModel(viewOut, 1234);
    gameSettingsView = new MockGameSettingsView(viewOut);
  }

  @Test
  public void testViewInstantiation() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.play();
    System.out.println(viewOut);
    String expected = "Rendered Game Settings view\n"
            + "Added event listener to game settings UI\n"
            + "Setting window displayed";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testShootN1() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.shootArrow(Direction.NORTH, 1);
    String expected = "Rendered Game Settings view\n"
            + "Shoot Arrow in direction: NORTH, at distance: 1 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Arrow shot into Darkness! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testShootS1() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.shootArrow(Direction.SOUTH, 1);
    String expected = "Rendered Game Settings view\n"
            + "Shoot Arrow in direction: SOUTH, at distance: 1 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Arrow shot into Darkness! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testShootE1() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.shootArrow(Direction.EAST, 1);
    String expected = "Rendered Game Settings view\n"
            + "Shoot Arrow in direction: EAST, at distance: 1 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Arrow shot into Darkness! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testShootN3() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.shootArrow(Direction.NORTH, 3);
    String expected = "Rendered Game Settings view\n"
            + "Shoot Arrow in direction: NORTH, at distance: 3 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Arrow shot into Darkness! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testShootS3() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.shootArrow(Direction.SOUTH, 3);
    String expected = "Rendered Game Settings view\n"
            + "Shoot Arrow in direction: SOUTH, at distance: 3 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Arrow shot into Darkness! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testShootW1() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.shootArrow(Direction.WEST, 1);
    String expected = "Rendered Game Settings view\n"
            + "Shoot Arrow in direction: WEST, at distance: 1 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Arrow shot into Darkness! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testCollectTreasure() {
    Set<Integer> itemsToCollect = Set.of(11);
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.collectTreasure(itemsToCollect);
    String expected = "Rendered Game Settings view\n"
            + "Collecting Treasure with treasureId: 11 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Treasure Collected! Refresh Called";

    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testCollectTreasure2() {
    Set<Integer> itemsToCollect = Set.of(13);
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.collectTreasure(itemsToCollect);
    String expected = "Rendered Game Settings view\n"
            + "Collecting Treasure with treasureId: 13 Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Appending this info message: <br/>Treasure Collected! Refresh Called";

    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testGameRestart() {
    GameSettings gameSettingsView2 = new MockGameSettingsView(viewOut, 5,
            5, 3,
            4, true, 10, 2, 2);
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView2, model);
    controllerV2.restartGame();
    String expected = "Rendered Game Settings view\n"
            + "Rendered Game Settings view\n"
            + "Num Rows: 5\n"
            + "Num Columns: 5\n"
            + "Num monster: 4Interconnectivity: 3\n"
            + "Is wrapping: true\n"
            + "Treasure percentage: 10.0\n"
            + "Num pit: 2\n"
            + "Num thief: 2\n"
            + "Setting window Hidden";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testNewGame() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.initiateNewGame();
    String expected = "Rendered Game Settings view\n"
            + "Setting window displayed";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testPlayerMovementN() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.movePlayer(Direction.NORTH);
    String expected = "Rendered Game Settings view\n"
            + "Move player in direction: NORTH Check if game is over\n"
            + " Check if game is over\n"
            + "Get the player location description\n"
            + " Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Pit detected nearby\n"
            + "Appending this info message: Player moved in north,<br/>You can collect treasure "
            + "here! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testPlayerMovementS() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.movePlayer(Direction.SOUTH);
    String expected = "Rendered Game Settings view\n"
            + "Move player in direction: SOUTH Check if game is over\n"
            + " Check if game is over\n"
            + "Get the player location description\n"
            + " Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Pit detected nearby\n"
            + "Appending this info message: Player moved in south,<br/>You can collect treasure "
            + "here! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testPlayerMovementE() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.movePlayer(Direction.EAST);
    String expected = "Rendered Game Settings view\n"
            + "Move player in direction: EAST Check if game is over\n"
            + " Check if game is over\n"
            + "Get the player location description\n"
            + " Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Pit detected nearby\n"
            + "Appending this info message: Player moved in east,"
            + "<br/>You can collect treasure here! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }

  @Test
  public void testPlayerMovementW() {
    controllerV2 = new DungeonControllerV2Impl(dungeonView, gameSettingsView, model);
    controllerV2.movePlayer(Direction.WEST);
    String expected = "Rendered Game Settings view\n"
            + "Move player in direction: WEST Check if game is over\n"
            + " Check if game is over\n"
            + "Get the player location description\n"
            + " Check if light smell is detected\n"
            + "Check if strong smell is detected\n"
            + "Pit detected nearby\n"
            + "Appending this info message: Player moved in west,"
            + "<br/>You can collect treasure here! Refresh Called";
    assertEquals(expected, viewOut.toString());
  }
}
