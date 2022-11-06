package dungeon;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Junit Tests for {@link CellImpl} class.
 */
public class CellImplTest {
  Cell cell;

  @Before
  public void setUp() throws Exception {
    cell = new CellImpl(1, 2,3);
  }

  @Test
  public void addTreasureTest() {
    Cell northNeighbour = new CellImpl(2, 1,3);
    cell.setNorth(northNeighbour);
    assertEquals(LocationType.CAVE, cell.getLocationType());
    cell.addItems(ItemType.RUBY, 2);
    assertEquals(1, cell.getItems().size());
  }

  @Test
  public void removeTreasureTest() {
    Cell northNeighbour = new CellImpl(2, 1,3);
    cell.setNorth(northNeighbour);
    assertEquals(LocationType.CAVE, cell.getLocationType());
    cell.addItems(ItemType.DIAMOND, 10);
    assertEquals(1, cell.getItems().size());
    cell.removeItem(10);
    assertEquals(0, cell.getItems().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addTreasureNegativeTest() {
    Cell northNeighbour = new CellImpl(2, 1,3);
    cell.setNorth(northNeighbour);
    Cell southNeighbour = new CellImpl(2, 3,3);
    cell.setSouth(southNeighbour);
    assertEquals(LocationType.TUNNEL, cell.getLocationType());
    cell.addItems(ItemType.SAPPHIRE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighbourNegativeTest1() {
    cell.setNorth(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighbourNegativeTest2() {
    cell.setSouth(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighbourNegativeTest3() {
    cell.setEast(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNeighbourNegativeTest4() {
    cell.setWest(null);
  }
}
