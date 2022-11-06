package dungeon;

import static dungeon.ItemType.DIAMOND;
import static dungeon.ItemType.RUBY;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Junit tests for {@link PlayerLocationDescription} class.
 */
public class PlayerLocationDescriptionTest {
  PlayerLocationDescription playerLocationDescription;

  @Before
  public void setUp() throws Exception {
    Cell cell1 = new CellImpl(1, 1,1);
    Cell cell2 = new CellImpl(2, 1,2);
    Cell cell3 = new CellImpl(3, 2,1);
    Cell cell4 = new CellImpl(4, 1,5);
    cell1.setWest(cell4);
    cell1.setEast(cell2);
    cell1.setSouth(cell3);
    cell1.addItems(RUBY, 10);
    cell1.addItems(DIAMOND, 10);
    playerLocationDescription = new PlayerLocationDescription(cell1);
  }

  @Test
  public void testToString() {
    String expected = "-------Location Description-------\n"
            + "Cave Id: 1\n"
            + "Treasure List: \n"
            + "DIAMOND\n"
            + "Location Type: CAVE\n"
            + "Available Directions: East, South, West";
    assertEquals(expected, playerLocationDescription.toString());
  }
}
