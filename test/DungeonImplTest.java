import static dungeon.ItemType.ARROW;
import static dungeon.ItemType.DIAMOND;
import static dungeon.ItemType.RUBY;
import static dungeon.ItemType.SAPPHIRE;
import static dungeon.LocationType.CAVE;
import static dungeon.LocationType.TUNNEL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.ItemType;
import dungeon.PlayerLocationDescription;
import org.junit.Before;
import org.junit.Test;
import randomizer.Randomizer;
import randomizer.RandomizerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Junit tests for {@link DungeonImpl} class.
 */
public class DungeonImplTest {

  Dungeon dungeon;

  @Before
  public void setup() {
    Randomizer randomizer = new RandomizerImpl(123);
    dungeon = new DungeonImpl(10, 5, 5,
            2, true, 1, randomizer);
  }

  @Test
  public void enterDungeonTest() {
    dungeon.enterDungeon();
    assertEquals(1, dungeon.getPlayerLocationDescription().getCellId());
    assertEquals(CAVE, dungeon.getPlayerLocationDescription().getLocation());
    assertNotNull(dungeon.getAdjacencyList());
  }

  @Test(expected = IllegalStateException.class)
  public void enterDungeonNegativeTest() {
    dungeon.getPlayerLocationDescription();
  }

  @Test
  public void checkDestinationReachedTest1() {
    dungeon.enterDungeon();
    assertFalse(dungeon.isGameOver());
  }

  @Test
  public void checkDestinationReachedTest2() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(6, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(11, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(16, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(17, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(12, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(7, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(8, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(13, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(18, dungeon.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void overPlayTest() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    assertTrue(dungeon.isGameOver());
    dungeon.movePlayer(Direction.NORTH);
  }

  @Test
  public void create6x8MazeTest() {
    Randomizer randomizer = new RandomizerImpl(42);
    Dungeon dungeon1 = new DungeonImpl(30, 6, 8,
            2, true, 1, randomizer);
    String expectedMaze = "{1=[41, 9], 2=[3, 10], 3=[2], 4=[44, 12], 5=[45, 13], 6=[46, 7], "
            + "7=[47, 8, 15, 6], 8=[48, 7], 9=[1, 10, 16], 10=[2, 11, 9], 11=[12, 10], "
            + "12=[4, 13, 20, 11], 13=[5, 12], 14=[15, 22], 15=[7, 14], 16=[9, 24], "
            + "17=[18, 25, 24], 18=[19, 26, 17], 19=[18], 20=[12, 28], 21=[22, 29], "
            + "22=[14, 23, 21], 23=[24, 22], 24=[16, 17, 23], 25=[17, 26, 32], "
            + "26=[18, 27, 34, 25], "
            + "27=[28, 26], 28=[20, 27], 29=[21, 37], 30=[31], 31=[39, 30], 32=[25, 40], "
            + "33=[34, 40], "
            + "34=[26, 35, 42, 33], 35=[36, 43, 34], 36=[44, 35], 37=[29, 38, 45], 38=[39, 37], "
            + "39=[31, 40, 38], 40=[32, 33, 39], 41=[42, 1, 48], 42=[34, 43, 41], 43=[35, 42], "
            + "44=[36, 45, 4], 45=[37, 46, 5, 44], 46=[47, 6, 45], 47=[48, 7, 46], 48=[41, 8, 47]}";
    assertNotNull(dungeon1.getAdjacencyList());
    assertEquals(expectedMaze, dungeon1.getAdjacencyList().toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMoveTest() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.WEST);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMoveTest2() {
    dungeon.enterDungeon();
    dungeon.movePlayer(null);
  }

  @Test
  public void wrappingCaveTest() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(2, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.NORTH);
    PlayerLocationDescription desc = dungeon.getPlayerLocationDescription();
    assertEquals(22, desc.getCellId());
  }

  @Test(expected = IllegalStateException.class)
  public void movePlayerBeforeGameStart() {
    dungeon.movePlayer(Direction.SOUTH);
  }

  @Test(expected = IllegalStateException.class)
  public void collectTreasureBeforeGameStart() {
    dungeon.collectTreasure(1);
  }

  @Test
  public void allDirectionMoveTest() {
    dungeon.enterDungeon();
    assertEquals(1, dungeon.getPlayerLocationDescription().getCellId());
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(1, dungeon.getPlayerLocationDescription().getCellId());
  }

  @Test
  public void treasureAvailableTest() {
    dungeon.enterDungeon();
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.SOUTH);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertTrue(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertTrue(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.NORTH);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.NORTH);
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.WEST);
    assertTrue(dungeon.treasureAvailable());
  }

  @Test
  public void collectTreasureTest() {
    dungeon.enterDungeon();
    assertEquals(3, dungeon.getPlayerDescription().size());
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.collectTreasure(5);
    dungeon.collectTreasure(6);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.WEST);
    dungeon.collectTreasure(4);
    List<ItemType> expected = new ArrayList<>();
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(DIAMOND);
    expected.add(SAPPHIRE);
    expected.add(RUBY);
    Map<Integer, ItemType> actual = dungeon.getPlayerDescription();
    Set<Integer> keySet = actual.keySet();
    assertEquals(expected.size(), keySet.size());
    for (int key: keySet) {
      assertTrue(expected.contains(actual.get(key)));
    }
  }

  @Test
  public void collectTreasureTest2() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(5, dungeon.getPlayerLocationDescription()
            .getAvailableItems().size());
    dungeon.collectTreasure(5);
    assertEquals(4, dungeon.getPlayerLocationDescription()
            .getAvailableItems().size());
    dungeon.collectTreasure(6);
    assertEquals(3, dungeon.getPlayerLocationDescription()
            .getAvailableItems().size());
    dungeon.collectTreasure(7);
    assertEquals(2, dungeon.getPlayerLocationDescription()
            .getAvailableItems().size());
    dungeon.collectTreasure(8);
    assertEquals(1, dungeon.getPlayerLocationDescription()
            .getAvailableItems().size());
    dungeon.collectTreasure(9);
    assertEquals(0, dungeon.getPlayerLocationDescription()
            .getAvailableItems().size());
  }

  @Test
  public void testExpectedTreasure() {
    Map<Integer, ItemType> expected = new HashMap<>();
    expected.put(5, DIAMOND);
    expected.put(7, DIAMOND);
    expected.put(6, SAPPHIRE);
    expected.put(8, ARROW);
    expected.put(9, ARROW);
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(expected, dungeon.getPlayerLocationDescription()
            .getAvailableItems());
  }

  @Test
  public void testExpectedTreasureToString() {
    String expected = "{5=DIAMOND, 6=SAPPHIRE, 7=DIAMOND, 8=ARROW, 9=ARROW}";
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(expected, dungeon.getPlayerLocationDescription()
            .getAvailableItems().toString());
  }

  @Test
  public void testExpectedTreasure2() {
    Map<Integer, ItemType> expected = new HashMap<>();
    expected.put(4, RUBY);
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.WEST);
    assertEquals(expected, dungeon.getPlayerLocationDescription()
            .getAvailableItems());
  }

  @Test(expected = IllegalArgumentException.class)
  public void collectTreasureNegativeTest2() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.collectTreasure(9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void collectTreasureNegativeTest() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.collectTreasure(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTreasurePercentTest() {
    Dungeon dungeon = new DungeonImpl(-1, 5, 5, 4, true, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTreasurePercentTest2() {
    Dungeon dungeon = new DungeonImpl(101, 5, 5, 4, true, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDungeonCreation() {
    Dungeon dungeon = new DungeonImpl(10, 102, 23, 4, true, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDungeonCreation2() {
    Dungeon dungeon = new DungeonImpl(10, 12, 123, 4, true, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidDungeonCreation3() {
    Dungeon dungeon = new DungeonImpl(10, 112, 123, 4, true, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterconnectivityTest() {
    Dungeon dungeon = new DungeonImpl(23, 5, 5, 17, true, 0);
    dungeon.enterDungeon();
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterconnectivityTest2() {
    Dungeon dungeon = new DungeonImpl(23, 5, 5, -17, true, 0);
    dungeon.enterDungeon();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMinSize() {
    Dungeon dungeon = new DungeonImpl(20, 2, 3, 2, true, 0);
  }

  @Test
  public void getLocationDescriptionTest() {
    dungeon.enterDungeon();
    assertEquals(1, dungeon.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveEast());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveSouth());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveNorth());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveWest());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(6, dungeon.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveEast());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveSouth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveNorth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveWest());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(7, dungeon.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveEast());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveSouth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveNorth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveWest());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(8, dungeon.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveEast());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveSouth());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveNorth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveWest());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(13, dungeon.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveEast());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveSouth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveNorth());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveWest());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(18, dungeon.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveEast());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveSouth());
    assertTrue(dungeon.getPlayerLocationDescription().canMoveNorth());
    assertFalse(dungeon.getPlayerLocationDescription().canMoveWest());
  }

  @Test
  public void testWrapping1() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 1, randomizer);
    dungeon2.enterDungeon();
    assertEquals(1, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(6, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(11, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(16, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.WEST);
    assertEquals(20, dungeon2.getPlayerLocationDescription().getCellId());
  }

  @Test
  public void testWrapping2() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 1, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(6, dungeon2.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveWest());
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(16, dungeon2.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon2.getPlayerLocationDescription().canMoveWest());
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(24, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(4, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.WEST);
    assertEquals(3, dungeon2.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveNorth());
  }

  @Test
  public void testWrapping3() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 1, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(16, dungeon2.getPlayerLocationDescription().getCellId());
    dungeon2.movePlayer(Direction.WEST);
    assertEquals(20, dungeon2.getPlayerLocationDescription().getCellId());
  }

  @Test
  public void testNonWrapping() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, false, 1, randomizer);
    dungeon2.enterDungeon();
    assertEquals(1, dungeon2.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveWest());
    dungeon2.movePlayer(Direction.SOUTH);
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveWest());
    dungeon2.movePlayer(Direction.SOUTH);
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveWest());
    dungeon2.movePlayer(Direction.SOUTH);
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveWest()); //16
    dungeon2.movePlayer(Direction.SOUTH);
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveWest()); //21
    dungeon2.movePlayer(Direction.EAST);
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveSouth()); //22
    dungeon2.movePlayer(Direction.EAST);
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveSouth()); //23

    assertEquals(23, dungeon2.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveSouth());
    dungeon2.movePlayer(Direction.NORTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(25, dungeon2.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveSouth());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveEast());
    dungeon2.movePlayer(Direction.NORTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.SOUTH);
    assertEquals(24, dungeon2.getPlayerLocationDescription().getCellId());
    assertFalse(dungeon2.getPlayerLocationDescription().canMoveSouth());
  }

  private int isTreasureAtCurrentLocation(Dungeon dungeon) {
    Map<Integer, ItemType> availableTreasure = dungeon.getPlayerLocationDescription()
            .getAvailableItems();
    for (int key: availableTreasure.keySet()) {
      if (!availableTreasure.get(key).equals(ARROW)) {
        return 1;
      }
    }
    return 0;
  }

  private int traverseHalfNodes() {
    int numCavesWithTreasure = 0;
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //2
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //22
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //21
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //23
    return numCavesWithTreasure;
  }

  private int traverseRestNodes() {
    int numCavesWithTreasure = 0;
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //6
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //7
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //6
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //1
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //11
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //16
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //17
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //12
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //7
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //2
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //3
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //4
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isTreasureAtCurrentLocation(dungeon); //21
    return numCavesWithTreasure;
  }

  @Test
  public void testPercentage() {
    int numCavesWithTreasure = 0;
    dungeon.enterDungeon();
    numCavesWithTreasure = traverseRestNodes() + traverseHalfNodes();
    int numCaves = 16;
    double actual = (double)(numCaves * 10) / 100;
    assertEquals(numCavesWithTreasure, (int)(Math.ceil(actual)));
  }

  @Test
  public void testNoTreasureInTunnel() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.EAST);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation()); //13
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation()); //13
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation()); //13
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation()); //13
    assertFalse(dungeon.treasureAvailable());
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation()); //13
    assertFalse(dungeon.treasureAvailable());
  }

  @Test
  public void testStartingPointCave() {
    for (int i = 0; i < 10; i++) {
      Dungeon dungeonRand = new DungeonImpl(10,
              5,5,2,true, 1);
      dungeonRand.enterDungeon();
      assertEquals(CAVE, dungeonRand.getPlayerLocationDescription().getLocation());
    }
  }

  @Test
  public void testExitPointCave() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(CAVE, dungeon.getPlayerLocationDescription().getLocation());
  }

  @Test
  public void testExitPointCave2() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon2 = new DungeonImpl(10,5,5,
            2,true, 1, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    assertEquals(2, dungeon2.getPlayerLocationDescription().getCellId());
    assertTrue(dungeon2.isGameOver());
    assertEquals(CAVE, dungeon2.getPlayerLocationDescription().getLocation());
  }

  @Test
  public void dungeonMapTest() {
    String expected = "{1=[6], 2=[22, 3, 7], 3=[4, 2], 4=[24, 3], 5=[10], 6=[1, 7, 11, 10], "
            + "7=[2, 8, 12, 6], 8=[9, 13, 7], 9=[10, 14, 8], 10=[5, 6, 15, 9], 11=[6, 16], "
            + "12=[7, 17], 13=[8, 18], 14=[9, 19], 15=[10, 20], 16=[11, 17], 17=[12, 16], "
            + "18=[13, 19, 23], 19=[14, 18], 20=[15, 25], 21=[22], 22=[23, 2, 21], "
            + "23=[18, 24, 22], 24=[4, 23], 25=[20]}";
    assertEquals(expected, dungeon.getAdjacencyList().toString());
  }

  @Test
  public void testMultipleTreasure() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    assertTrue(dungeon.getPlayerLocationDescription().getAvailableItems().size() > 0);
  }

  private int countEdges(Dungeon dungeon) {
    int edges = 0;
    Map<Integer, List<Integer>> adjList = dungeon.getAdjacencyList();
    for (int i = 1; i <= 25; i++) {
      edges += adjList.get(i).size();
    }
    return edges / 2;
  }

  @Test
  public void testInterconnectivity1() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer);
    int d1Edges = countEdges(dungeon1);
    Dungeon dungeon2 = new DungeonImpl(10,5,5,
            3,false, 1, randomizer);
    int d2Edges = countEdges(dungeon2);
    assertEquals(d1Edges + 3, d2Edges);
  }

  @Test
  public void testInterconnectivity2() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer);
    int d1Edges = countEdges(dungeon1);
    Dungeon dungeon2 = new DungeonImpl(10,5,5,
            5,false, 1, randomizer);
    int d2Edges = countEdges(dungeon2);
    assertEquals(d1Edges + 5, d2Edges); //dungeon1 has interconnectivity of 0 and dungeon2 has 5.
  }

  @Test
  public void testNavigationToEveryOtherNode() {
    Randomizer randomizer = new RandomizerImpl(45724315);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            2,false, 1, randomizer);
    dungeon1.enterDungeon();
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(6, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(11, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(16, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    assertEquals(17, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.WEST);
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(21, dungeon1.getPlayerLocationDescription().getCellId());

    dungeon1.movePlayer(Direction.EAST);
    assertEquals(22, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    assertEquals(23, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(18, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    assertEquals(19, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    assertEquals(20, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(25, dungeon1.getPlayerLocationDescription().getCellId());

    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(20, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(15, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.WEST);
    assertEquals(14, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(10, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(5, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.WEST);
    assertEquals(9, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(4, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.WEST);
    assertEquals(3, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.WEST);
    assertEquals(8, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.WEST);
    assertEquals(7, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(12, dungeon1.getPlayerLocationDescription().getCellId());
    dungeon1.movePlayer(Direction.EAST);
    assertEquals(13, dungeon1.getPlayerLocationDescription().getCellId());
  }

  private int isNeighbourPresent(boolean neighbour) {
    return (neighbour) ? 1 : 0;
  }

  @Test
  public void testCellType() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    int n = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveNorth());
    int s = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveSouth());
    int e = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveEast());
    int w = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveWest());
    assertEquals(2, (n + e + s + w));
  }

  @Test
  public void testCellType2() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    int n = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveNorth());
    int s = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveSouth());
    int e = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveEast());
    int w = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveWest());
    assertEquals(2, (n + e + s + w));
  }

  @Test
  public void testCellType3() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    assertEquals(TUNNEL, dungeon.getPlayerLocationDescription().getLocation());
    int n = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveNorth());
    int s = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveSouth());
    int e = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveEast());
    int w = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveWest());
    assertEquals(2, (n + e + s + w));
  }

  @Test
  public void testCellType4() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.EAST);
    assertEquals(CAVE, dungeon.getPlayerLocationDescription().getLocation());
    int n = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveNorth());
    int s = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveSouth());
    int e = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveEast());
    int w = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveWest());
    assertEquals(3, (n + e + s + w));
  }

  @Test
  public void testCellType5() {
    dungeon.enterDungeon();
    assertEquals(CAVE, dungeon.getPlayerLocationDescription().getLocation());
    int n = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveNorth());
    int s = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveSouth());
    int e = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveEast());
    int w = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveWest());
    assertEquals(1, (n + e + s + w));
  }

  @Test
  public void testCellType6() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    assertEquals(CAVE, dungeon.getPlayerLocationDescription().getLocation());
    int n = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveNorth());
    int s = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveSouth());
    int e = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveEast());
    int w = isNeighbourPresent(dungeon.getPlayerLocationDescription().canMoveWest());
    assertEquals(4, (n + e + s + w));
  }

  @Test
  public void mstEdgeTest() {
    Dungeon dungeon1 = new DungeonImpl(10,5,
            5,0,false, 1);
    int d1Edges = countEdges(dungeon1);
    assertEquals(24, d1Edges); //MST no of edges = (m * n - 1)
  }

  @Test
  public void testRandomEntry() {
    Randomizer randomizer = new RandomizerImpl(98);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer);
    dungeon1.enterDungeon();
    Randomizer randomizer2 = new RandomizerImpl(87987);
    Dungeon dungeon2 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer2);
    dungeon2.enterDungeon();
    assertNotEquals(dungeon1.getPlayerLocationDescription().getCellId(),
            dungeon2.getPlayerLocationDescription().getCellId());
  }

  @Test
  public void testRandomEntry2() {
    Randomizer randomizer = new RandomizerImpl(98765);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer);
    dungeon1.enterDungeon();
    Randomizer randomizer2 = new RandomizerImpl(46456);
    Dungeon dungeon2 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer2);
    dungeon2.enterDungeon();
    assertNotEquals(dungeon1.getPlayerLocationDescription().getCellId(),
            dungeon2.getPlayerLocationDescription().getCellId());
  }

  @Test
  public void testMazeRandomness() {
    Dungeon dungeon1 = new DungeonImpl(10,5,
            5,2,false, 1);
    dungeon1.enterDungeon();
    Dungeon dungeon2 = new DungeonImpl(10,5,
            5,2,false, 1);
    dungeon2.enterDungeon();
    assertNotEquals(dungeon1.getAdjacencyList().toString(), dungeon2.getAdjacencyList().toString());
  }

  @Test
  public void testValidPathToEveryOtherNode() {
    Randomizer randomizer = new RandomizerImpl(98765);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            0,false, 1, randomizer);
    Map<Integer, List<Integer>> adjList = dungeon1.getAdjacencyList();
    dungeon1.enterDungeon();
    //asserts valid path between 2(entry and other nodes), path length > 0, if valid path exists
    assertTrue(bfs(2, 1, 25, adjList) > 0);
    assertTrue(bfs(2, 3, 25, adjList) > 0);
    assertTrue(bfs(2, 4, 25, adjList) > 0);
    assertTrue(bfs(2, 5, 25, adjList) > 0);
    assertTrue(bfs(2, 6, 25, adjList) > 0);
    assertTrue(bfs(2, 7, 25, adjList) > 0);
    assertTrue(bfs(2, 8, 25, adjList) > 0);
    assertTrue(bfs(2, 9, 25, adjList) > 0);
    assertTrue(bfs(2, 10, 25, adjList) > 0);
    assertTrue(bfs(2, 11, 25, adjList) > 0);
    assertTrue(bfs(2, 12, 25, adjList) > 0);
    assertTrue(bfs(2, 13, 25, adjList) > 0);
    assertTrue(bfs(2, 14, 25, adjList) > 0);
    assertTrue(bfs(2, 15, 25, adjList) > 0);
    assertTrue(bfs(2, 16, 25, adjList) > 0);
    assertTrue(bfs(2, 17, 25, adjList) > 0);
    assertTrue(bfs(2, 18, 25, adjList) > 0);
    assertTrue(bfs(2, 19, 25, adjList) > 0);
    assertTrue(bfs(2, 20, 25, adjList) > 0);
    assertTrue(bfs(2, 21, 25, adjList) > 0);
    assertTrue(bfs(2, 22, 25, adjList) > 0);
    assertTrue(bfs(2, 23, 25, adjList) > 0);
    assertTrue(bfs(2, 24, 25, adjList) > 0);
    assertTrue(bfs(2, 25, 25, adjList) > 0);
  }

  @Test
  public void testShortestPath() {
    Map<Integer, List<Integer>> adjList = dungeon.getAdjacencyList();
    int dist = bfs(2, 25, 25, adjList);
    assertTrue(dist >= 5);
  }

  @Test
  public void testShortestPath2() {
    Randomizer randomizer = new RandomizerImpl(4527);
    Dungeon dungeon1 = new DungeonImpl(10,5,
            5,0,false, 1, randomizer);
    Map<Integer, List<Integer>> adjList = dungeon1.getAdjacencyList();
    int dist = bfs(5, 6, 25, adjList);
    assertTrue(dist >= 5);
  }

  @Test
  public void maximumInterconnectivityTest() {
    Randomizer randomizer = new RandomizerImpl(4527);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            16,true, 1, randomizer);
    String expected = "{1=[2, 6, 5], 2=[3, 7, 1], 3=[4, 8, 2], 4=[5, 9, 3], 5=[1, 10, 4], "
            + "6=[1, 7, 11], 7=[2, 8, 12, 6], 8=[3, 9, 13, 7], 9=[4, 10, 14, 8], 10=[5, 15, 9], "
            + "11=[6, 12, 16], 12=[7, 13, 17, 11], 13=[8, 14, 18, 12], 14=[9, 15, 19, 13], "
            + "15=[10, 20, 14], 16=[11, 17, 21], 17=[12, 18, 22, 16], 18=[13, 19, 23, 17], "
            + "19=[14, 20, 24, 18], 20=[15, 25, 19], 21=[16, 22], 22=[17, 23, 21], "
            + "23=[18, 24, 22], 24=[19, 25, 23], 25=[20, 24]}";
    assertEquals(expected, dungeon1.getAdjacencyList().toString());

  }

  @Test
  public void zeroInterconnectivityTest() {
    Randomizer randomizer = new RandomizerImpl(4527);
    Dungeon dungeon1 = new DungeonImpl(10,5,5,
            0,true, 1, randomizer);
    String expected = "{1=[21, 2, 6, 5], 2=[7, 1], 3=[4, 8], 4=[5, 3], 5=[25, 1, 4], 6=[1, 10], "
            + "7=[2, 8, 12], 8=[3, 13, 7], 9=[10, 14], 10=[6, 9], 11=[12, 15], 12=[7, 11], "
            + "13=[8, 14, 18], 14=[9, 13], 15=[11, 20], 16=[17, 21], 17=[18, 22, 16], "
            + "18=[13, 19, 23, 17], 19=[20, 18], 20=[15, 25, 19], 21=[16, 1, 25], 22=[17], "
            + "23=[18], 24=[25], 25=[20, 21, 5, 24]}";
    assertEquals(expected, dungeon1.getAdjacencyList().toString());

  }

  private int bfs(int entry, int exit, int gridSize, Map<Integer, List<Integer>> adjList) {
    //reference: https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
    Map<Integer, Integer> dist = new HashMap<>();
    for (int i = 1; i <= gridSize; i++) {
      dist.put(i, -1);
    }
    dist.put(entry, 0);
    LinkedList<Integer> queue = new LinkedList<>();
    queue.add(entry);
    while (queue.size() != 0) {
      entry = queue.poll();
      Iterator<Integer> i = adjList.get(entry).listIterator();
      while (i.hasNext()) {
        int n = i.next();
        if (dist.get(n) == -1) {
          int newDist = dist.get(entry) + 1;
          dist.put(n, newDist);
          queue.add(n);
        }
      }
    }
    return dist.get(exit);
  }

  //test: monster at end cave
  @Test
  public void monsterAtEndCave() {
    dungeon.enterDungeon();
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.EAST);
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    assertTrue(dungeon.isGameOver()); //validates that player reaches end cave.
    assertFalse(dungeon.isPlayerWinner()); //proves that end cave had a monster.
  }

  //test: no monster at start
  @Test
  public void noMonsterAtStartTest() {
    dungeon.enterDungeon();
    assertFalse(dungeon.isGameOver());
    assertFalse(dungeon.isPlayerWinner());
  }

  //test: no monster in tunnel
  @Test
  public void noMonsterInTunnelTest() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.SOUTH); //11
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.SOUTH); //16
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.EAST); //17
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.NORTH); //12
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.NORTH); //7
    dungeon2.movePlayer(Direction.NORTH); //2
    dungeon2.movePlayer(Direction.EAST);  //3
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.EAST); //4
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.NORTH); //24
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
  }

  @Test
  public void noMonsterInTunnelTest2() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.EAST); //7
    dungeon2.movePlayer(Direction.EAST); //8
    dungeon2.shootArrow(Direction.EAST, 1); //injure monster at cave #9
    dungeon2.shootArrow(Direction.EAST, 1); //kill monster at cave #9
    dungeon2.movePlayer(Direction.EAST); //9
    dungeon2.movePlayer(Direction.SOUTH); //14
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.SOUTH); //19
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.NORTH); //10
    dungeon2.movePlayer(Direction.NORTH); //10
    dungeon2.movePlayer(Direction.EAST); //10
    dungeon2.movePlayer(Direction.SOUTH); //15
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
    dungeon2.movePlayer(Direction.SOUTH); //20
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
  }

  @Test
  public void noMonsterInTunnelTest3() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.EAST); //7
    dungeon2.movePlayer(Direction.EAST); //8
    dungeon2.movePlayer(Direction.SOUTH); //13
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertFalse(dungeon2.isGameOver());
  }

  //test: provide very high monster count
  @Test(expected = IllegalArgumentException.class)
  public void provideInvalidMonsterCount() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 12, randomizer);
    dungeon2.enterDungeon();
  }

  @Test(expected = IllegalArgumentException.class)
  public void provideInvalidMonsterCount2() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, -2, randomizer);
    dungeon2.enterDungeon();
  }

  //test: test light pungent smell bfs(source, end) == 2
  @Test
  public void testLightPungentSmell() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    Map<Integer, List<Integer>> adjList = dungeon2.getAdjacencyList();
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.EAST); //7
    assertTrue(dungeon2.lightSmellDetected());
    assertEquals(2, bfs(7, 9, 25, adjList));
  }

  //test: test strong pungent smell for both scenarios.
  @Test
  public void testStrongPungentSmell1() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.WEST); //10
    dungeon2.movePlayer(Direction.SOUTH); //15
    assertTrue(dungeon2.strongSmellDetected()); //2 caves at distance 2 have monsters.
  }

  @Test
  public void testStrongPungentSmell2() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.WEST); //10
    assertTrue(dungeon2.strongSmellDetected()); //immediate neighbour has a monster.
  }

  //test: encounter a monster, test if player dies.
  @Test
  public void playerDeathTest() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.WEST); //10
    assertTrue(dungeon2.strongSmellDetected()); //immediate neighbour has a monster.
    dungeon2.movePlayer(Direction.NORTH); //5, cave has monster, player dies
    assertTrue(dungeon2.isGameOver());
    assertFalse(dungeon2.isPlayerWinner());
  }

  //test: shoot monster with an arrow once, test if player survives(survives)
  @Test
  public void shootMonsterOnceTest1() {
    Randomizer randomizer = new RandomizerImpl(123);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.movePlayer(Direction.WEST); //10
    assertTrue(dungeon2.strongSmellDetected()); //immediate neighbour has a monster.
    dungeon2.shootArrow(Direction.NORTH, 1);
    dungeon2.movePlayer(Direction.NORTH); //5,
    assertFalse(dungeon2.isGameOver()); //player could not survive an injured monster.
  }

  //test: shoot monster with an arrow once, test if player survives(dies)
  @Test
  public void shootMonsterOnceTest2() {
    Randomizer randomizer = new RandomizerImpl(1321);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.EAST); //6
    dungeon2.movePlayer(Direction.SOUTH); //6
    dungeon2.shootArrow(Direction.EAST, 1);
    dungeon2.movePlayer(Direction.EAST);
    assertFalse(dungeon2.isGameOver()); // player survies an injured monster
  }

  //test: check at start if player has 3 arrows.
  @Test
  public void checkPlayerBagAtStart() {
    Randomizer randomizer = new RandomizerImpl(1321);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 4, randomizer);
    dungeon2.enterDungeon();
    List<ItemType> expected = new ArrayList<>();
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(ARROW);
    Map<Integer, ItemType> actual = dungeon.getPlayerDescription();
    Set<Integer> keySet = actual.keySet();
    assertEquals(expected.size(), keySet.size());
    for (int key: keySet) {
      assertTrue(expected.contains(actual.get(key)));
    }
  }

  //test: test if percent of cells with arrow === treasurePercent
  private int isArrowPresentAtCurrentLocation(Dungeon dungeon) {
    Map<Integer, ItemType> availableTreasure = dungeon.getPlayerLocationDescription()
            .getAvailableItems();
    for (int key: availableTreasure.keySet()) {
      if (availableTreasure.get(key).equals(ARROW)) {
        return 1;
      }
    }
    return 0;
  }

  private int traverseHalfNodesForArrow() {
    int numCavesWithTreasure = 0;
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //2
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //22
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //21
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //23
    return numCavesWithTreasure;
  }

  private int traverseRestNodesForArrow() {
    int numCavesWithTreasure = 0;
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //6
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //7
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //6
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //1
    dungeon.movePlayer(Direction.SOUTH);
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //11
    dungeon.movePlayer(Direction.SOUTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //16
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //17
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //12
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //7
    dungeon.movePlayer(Direction.NORTH);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //2
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //3
    dungeon.movePlayer(Direction.EAST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //4
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.WEST);
    dungeon.movePlayer(Direction.NORTH);
    dungeon.movePlayer(Direction.WEST);
    numCavesWithTreasure += isArrowPresentAtCurrentLocation(dungeon); //21
    return numCavesWithTreasure;
  }

  @Test
  public void testArrowPercentage() {
    int numCavesWithTreasure = 0;
    dungeon.enterDungeon();
    numCavesWithTreasure = traverseRestNodesForArrow() + traverseHalfNodesForArrow();
    int numCells = 25;
    double actual = (double)(numCells * 10) / 100;
    assertEquals(numCavesWithTreasure, (int)(Math.ceil(actual)));
  }

  //test: test if arrows can be present in both caves and tunnels.
  @Test
  public void arrowAtCaveAndTunnelTest() {
    Randomizer randomizer = new RandomizerImpl(7070);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, false, 1, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(CAVE));
    assertTrue(dungeon2.getPlayerLocationDescription().toString().contains("ARROW"));
    dungeon2.movePlayer(Direction.NORTH);
    dungeon2.movePlayer(Direction.NORTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.NORTH);
    assertTrue(dungeon2.getPlayerLocationDescription().getLocation().equals(TUNNEL));
    assertTrue(dungeon2.getPlayerLocationDescription().toString().contains("ARROW"));
  }

  //test: shoot arrow at distance 1
  @Test
  public void killMonsterAtDistance1() {
    Randomizer randomizer = new RandomizerImpl(7070);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 5, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    assertTrue(dungeon2.strongSmellDetected());
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.movePlayer(Direction.SOUTH); //monster killed
    assertFalse(dungeon2.isGameOver());
  }

  //test: shoot arrow at distance 2
  @Test
  public void killMonsterAtDistance2() {
    Randomizer randomizer = new RandomizerImpl(311231);
    Dungeon dungeon2 = new DungeonImpl(10, 5, 5,
            2, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);

    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    assertTrue(dungeon2.lightSmellDetected()); //indication of monster
    dungeon2.shootArrow(Direction.EAST, 2);
    dungeon2.shootArrow(Direction.EAST, 2);
    assertFalse(dungeon2.lightSmellDetected()); //no smell because monster killed
  }

  //test: shoot arrow at distance 3
  @Test
  public void killMonsterAtDistanceWithTunnels() {
    Randomizer randomizer = new RandomizerImpl(242352);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    assertFalse(dungeon2.isGameOver()); // 3 tunnels in between were not counted in the distance.
  }

  @Test
  public void killMonsterAtDistance3() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.shootArrow(Direction.EAST, 3);
    dungeon2.shootArrow(Direction.EAST, 3);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    assertFalse(dungeon2.isGameOver()); // monster was shot from 3 caves away.
  }

  @Test
  public void overShotArrowTest() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    assertTrue(dungeon2.strongSmellDetected()); //multiple monsters 2 hops away.
    dungeon2.shootArrow(Direction.EAST, 3);
    dungeon2.shootArrow(Direction.EAST, 3);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    assertTrue(dungeon2.isGameOver()); // over shot arrow missed the monster in the way.
  }

  //test: enter distance <= 0
  @Test(expected = IllegalArgumentException.class)
  public void arrowDistanceNegativeTest() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.shootArrow(Direction.SOUTH, -3);
  }

  //test: enter distance > 5
  @Test(expected = IllegalArgumentException.class)
  public void arrowDistanceNegativeTest2() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.shootArrow(Direction.SOUTH, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void arrowDistanceNegativeTest3() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.shootArrow(Direction.EAST, 1); //invalid direction, no way to the east.
  }

  @Test(expected = IllegalStateException.class)
  public void shootArrowAfterPlayerDead() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.SOUTH); //monster at this location ate player.
    dungeon2.shootArrow(Direction.SOUTH, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void moveAfterPlayerDeadTest() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.SOUTH); //monster at this location ate player.
    dungeon2.movePlayer(Direction.SOUTH);
  }

  //test: shoot arrow when none available.
  @Test(expected = IllegalStateException.class)
  public void noAvailableArrowTest() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.shootArrow(Direction.SOUTH, 1); //shot all the initially provided arrows.
    dungeon2.shootArrow(Direction.SOUTH, 1);
  }


  //test: if arrow passes through the tunnel freely.
  @Test
  public void arrowMovingFreelyInTunnelTest() {
    Randomizer randomizer = new RandomizerImpl(87653);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.NORTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.shootArrow(Direction.SOUTH, 1); //shooting arrow through a bent tunnel.
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    assertFalse(dungeon2.isGameOver()); // monster killed
  }

  //test: collect treasure after player dead.
  @Test(expected = IllegalStateException.class)
  public void collectTreasureAfterPlayerDeath() {
    Randomizer randomizer = new RandomizerImpl(515353);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.NORTH);
    assertTrue(dungeon2.isGameOver());
    dungeon2.collectTreasure(20);
  }

  //MEGA Test: entire game play.
  @Test
  public void playGame() {
    Randomizer randomizer = new RandomizerImpl(515353);
    Dungeon dungeon2 = new DungeonImpl(10, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.collectTreasure(12); //collecting arrows
    dungeon2.collectTreasure(13); //collecting arrows
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    assertTrue(dungeon2.treasureAvailable());
    dungeon2.collectTreasure(4); //collecting treasure
    dungeon2.collectTreasure(6); //collecting treasure
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.EAST);
    assertTrue(dungeon2.lightSmellDetected());
    dungeon2.shootArrow(Direction.EAST, 1); //1 arrow shot to injure monster.
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.NORTH);
    assertFalse(dungeon2.isGameOver()); //player survives an injured monster.
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.shootArrow(Direction.SOUTH, 1);
    dungeon2.shootArrow(Direction.SOUTH, 1); //kill monster
    dungeon2.movePlayer(Direction.SOUTH);
    assertTrue(dungeon2.isGameOver());
    assertTrue(dungeon2.isPlayerWinner());
  }

  //test: both arrows and treasure can be found in a cave.
  @Test
  public void arrowAndTreasureInSameCaveTest() {
    Randomizer randomizer = new RandomizerImpl(515353);
    Dungeon dungeon2 = new DungeonImpl(40, 6, 6,
            5, true, 3, randomizer);
    dungeon2.enterDungeon();
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.EAST);
    dungeon2.movePlayer(Direction.SOUTH);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    dungeon2.movePlayer(Direction.WEST);
    List<ItemType> expected = new ArrayList<>();
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(ARROW);
    expected.add(RUBY);
    Map<Integer, ItemType> actual = dungeon2.getPlayerLocationDescription().getAvailableItems();
    Set<Integer> keySet = actual.keySet();
    assertEquals(expected.size(), keySet.size());
    for (int key: keySet) {
      assertTrue(expected.contains(actual.get(key)));
    }
  }
}

