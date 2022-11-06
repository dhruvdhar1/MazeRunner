import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import controller.DungeonController;
import controller.DungeonControllerImpl;
import dungeon.Dungeon;
import dungeon.DungeonImpl;
import dungeon.MockDungeonModel;
import org.junit.Before;
import org.junit.Test;
import randomizer.Randomizer;
import randomizer.RandomizerImpl;

import java.io.StringReader;

/**
 * JUnit tests for {@link DungeonControllerImpl} class.
 */
public class DungeonControllerTest {

  Dungeon dungeon;
  DungeonController controller;

  @Before
  public void setUp() {
    Randomizer randomizer = new RandomizerImpl(12312);
    dungeon = new DungeonImpl(20, 5, 5,
            2, true, 3, randomizer);
  }

  @Test
  public void testControllerOutputNotNull() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("3 1 s 3 1 s 3 1 s 4 5");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : "
            + "Arrow Shot!\nCell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : "
            + "Arrow Shot!\nCell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : "
            + "Arrow Shot!\nCell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "No available items.\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Game Quit";
    assertEquals(expected, out.toString());
    assertNotNull(out);
  }

  @Test
  public void testMoveInWrongDirection() {
    //pass north if there is no path to north.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 e");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Invalid Direction 'e' provided. "
            + "Enter Direction (n/s/w/) : Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testGiveInvalidInputForMove() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 1 2 2 2 f g s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Invalid Direction '1' provided. "
            + "Enter Direction (n/s/w/) : "
            + "Invalid Direction '2' provided. Enter Direction (n/s/w/) : Invalid Direction '2' "
            + "provided. Enter Direction (n/s/w/) : Invalid Direction '2' provided. "
            + "Enter Direction "
            + "(n/s/w/) : Invalid Direction 'f' provided. Enter Direction (n/s/w/) : "
            + "Invalid Direction 'g' provided. Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testGiveInvalidMenuOption1() {
    //pass 6/'e' for menu input
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("6");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Invalid input. Try again\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testGiveInvalidMenuOption2() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("w");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Invalid input. Try again\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testMove1() {
    //move in a direction, then verify the player's new location
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    assertEquals(6, dungeon.getPlayerLocationDescription().getCellId());
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testMove2() {
    //ove in a direction, then verify the player's new location
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 s 1 e 1 s 1 w 1 w");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    assertEquals(16, dungeon.getPlayerLocationDescription().getCellId());
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 12\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 13\n"
            + "You are in a TUNNEL\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (s/w/) : Cell id: 18\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/w/e)Cell id: 17\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 16\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testCollectNegative1() {
    //pass invalid treasure id
    //assert that nothing is collected.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("2 6 2 4 w t 5 s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n" +  "You are in a CAVE\n"
            +  "You can collect items at this location.\n"
            +  "Doors lead to (n/s/w/) : :\n"
            +  "Warning! A light pungent smell detected.\n"
            +  "Choose Action : \n"
            +  " 1. Move\n"
            +  " 2. Collect Treasure/Arrows\n"
            +  " 3. Shoot arrow.\n"
            +  " 4. Show collected items\n"
            +  " 5. Quit\n"
            +  "Available treasures: \n"
            +  "Item Id: 16 Item Name: SAPPHIRE\n"
            +  "Item Id: 17 Item Name: DIAMOND\n"
            +  "Item Id: 18 Item Name: RUBY\n"
            +  "Item Id: 19 Item Name: SAPPHIRE\n"
            +  "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: Invalid treasure id '6' provided. Enter item id: Invalid treasure id "
            +  "'2' provided. Enter item id: Invalid treasure id '4' provided. Enter item id: "
            +  "Invalid treasure id 'w' provided. Enter item id: Invalid treasure id 't' provided. "
            +  "Enter item id: Invalid treasure id '5' provided. Enter item id: Invalid treasure "
            +  "id 's' provided. Enter item id: Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testCollectNegative2() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("2 rrt 9 gg _ * 5");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 16 Item Name: SAPPHIRE\n"
            + "Item Id: 17 Item Name: DIAMOND\n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: Invalid treasure id 'rrt' provided. Enter item id: Invalid "
            + "treasure id '9' provided. Enter item id: Invalid treasure id 'gg' provided. "
            + "Enter item id: Invalid treasure id '_' provided. Enter item id: Invalid treasure "
            + "id '*' provided. Enter item id: Invalid treasure id '5' provided. Enter item id: "
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testCollectTreasure() {
    //collect treasure
    //assert that treasure is collected.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("2 16 2 17 2 20 4");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 16 Item Name: SAPPHIRE\n"
            + "Item Id: 17 Item Name: DIAMOND\n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: SAPPHIRE collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 17 Item Name: DIAMOND\n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: DIAMOND collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: RUBY collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Item id\t|\tItem Name\n"
            + "-----------------------\n"
            + "16\t\t|\tSAPPHIRE\n"
            + "1\t\t|\tARROW\n"
            + "17\t\t|\tDIAMOND\n"
            + "2\t\t|\tARROW\n"
            + "3\t\t|\tARROW\n"
            + "20\t\t|\tRUBY\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
    String expectedModelOut = "-------Location Description-------\nCave Id: 1\n"
            + "Treasure List: \n"
            + "RUBY\n"
            + "SAPPHIRE\n"
            + "Location Type: CAVE\n"
            + "Available Directions: North, South, West";
    assertEquals(expectedModelOut, dungeon.getPlayerLocationDescription().toString());
  }

  @Test
  public void testDetectLightPungentSmell() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 12\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertTrue(dungeon.lightSmellDetected());
    assertEquals(expected, out.toString());
  }

  @Test
  public void testDetectStrongPungentSmell1() {
    //detect strong smell when immediate neighbour has a monster
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
    assertTrue(dungeon.strongSmellDetected());
  }

  @Test
  public void testDetectStrongPungentSmell2() {
    //detect strong smell when 2 distant neighbours have monsters.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertTrue(dungeon.strongSmellDetected());
    assertEquals(expected, out.toString());
  }

  @Test
  public void testGameOverByMonsterAttack() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 n 1 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/) : You have been eaten by the monster. Game over!";
    assertEquals(expected, out.toString());
    assertTrue(dungeon.isGameOver());
    assertFalse(dungeon.isPlayerWinner());
  }

  @Test
  public void testGameOverByPlayerWin() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 n 3 1 n 3 1 n 1 n 1 w 2 21 2 22 2 23 1 "
            +  "e 2 30 1 n 1 n 1 n 3 1 e 3 1 e 1 e 1 n 3 1 e  3 1 e 1 e");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/) : Arrow Shot!\n"
            + "Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/) : Arrow Shot!\n"
            + "Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/) : Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 21 Item Name: ARROW\n"
            + "Item Id: 22 Item Name: ARROW\n"
            + "Item Id: 23 Item Name: ARROW\n"
            + "Item Id: 24 Item Name: ARROW\n"
            + "Item Id: 25 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 22 Item Name: ARROW\n"
            + "Item Id: 23 Item Name: ARROW\n"
            + "Item Id: 24 Item Name: ARROW\n"
            + "Item Id: 25 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 23 Item Name: ARROW\n"
            + "Item Id: 24 Item Name: ARROW\n"
            + "Item Id: 25 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (s/w/e)Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 7 Item Name: SAPPHIRE\n"
            + "Item Id: 8 Item Name: RUBY\n"
            + "Item Id: 9 Item Name: RUBY\n"
            + "Item Id: 10 Item Name: SAPPHIRE\n"
            + "Item Id: 11 Item Name: RUBY\n"
            + "Item Id: 30 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 17\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 12\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)Arrow Shot!\n"
            + "Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)Arrow Shot!\n"
            + "Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 8\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/w/) : Cell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/e)Arrow Shot!\n"
            + "Cell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/e)Arrow Shot!\n"
            + "Cell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/e)Destination Reached, You win!!";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testCrossingAnInjuredMonster1() {
    //player survives.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 n 3 1 n 3 1 n 1 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n" +
            "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n" 
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/) : Arrow Shot!\n"
            + "Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/) : Arrow Shot!\n"
            + "Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/) : Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testCrossingAnInjuredMonster2() {
    //player dies.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 e 1 n 3 1 e 1 e");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =   "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 8\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/w/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/w/) : You have been eaten by the monster. Game over!";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testArrowFreeTravelInTunnel() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 3 1 e 3 1 e 1 e 1 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected = "Cell id: 1\n" + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)"
            + "Arrow Shot!\nCell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)"
            + "Arrow Shot!\nCell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 8\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/w/) : Cell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void shootArrowTest1() {
    //shoot arrow in one direction 2 times, check if monster died.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 3 1 e 3 1 e");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    assertTrue(dungeon.lightSmellDetected());
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)"
            + "Arrow Shot!\nCell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)"
            + "Arrow Shot!\nCell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void shootArrowInInvalidDirection() {
    //shoot in north when north == null
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 3 1 s 3 1 w");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/e)\n"
            + "Invalid distance or direction provided. Enter Distance (1-5): "
            + "Enter direction (n/e)\n"
            + "Invalid distance or direction provided. Enter Distance (1-5): \n"
            + "Invalid distance or direction provided. Enter Distance (1-5): Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void shootArrowNegativeTest1() {
    //enter distance <= 0
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 3 -1 w 0 w");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/e)\n"
            + "Invalid distance or direction provided. Enter Distance (1-5): "
            + "Enter direction (n/e)\n"
            + "Invalid distance or direction provided. Enter Distance (1-5): Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void shootArrowNegativeTest2() {
    //enter distance > 5
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 3 6 w 10 w");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/e)\n"
            + "Invalid distance or direction provided. Enter Distance (1-5): "
            + "Enter direction (n/e)\n"
            + "Invalid distance or direction provided. Enter Distance (1-5): Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void shootArrowWhenNotAvailable() {
    //shoot arrow if available quantity = 0
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("3 1 s 3 1 s 3 1 s 3 1 s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : "
            + "Arrow Shot!\nCell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : "
            + "Arrow Shot!\nCell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : "
            + "Arrow Shot!\nCell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/) : \n"
            + "Cannot shoot, No arrows available.\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void showCollectedItemsTest1() {
    //check that 3 arrows are available at start
    //collect more items and assert.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("2 16 2 17 2 19 4");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 16 Item Name: SAPPHIRE\n"
            + "Item Id: 17 Item Name: DIAMOND\n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: SAPPHIRE collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 17 Item Name: DIAMOND\n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: DIAMOND collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: SAPPHIRE collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Item id\t|\tItem Name\n"
            + "-----------------------\n"
            + "16\t\t|\tSAPPHIRE\n"
            + "1\t\t|\tARROW\n"
            + "17\t\t|\tDIAMOND\n"
            + "2\t\t|\tARROW\n"
            + "3\t\t|\tARROW\n"
            + "19\t\t|\tSAPPHIRE\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testQuit() {
    //test quit.
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("2 16 5");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 16 Item Name: SAPPHIRE\n"
            + "Item Id: 17 Item Name: DIAMOND\n"
            + "Item Id: 18 Item Name: RUBY\n"
            + "Item Id: 19 Item Name: SAPPHIRE\n"
            + "Item Id: 20 Item Name: RUBY\n"
            + "Enter item id: SAPPHIRE collected\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Game Quit";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testKilledByMonster() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 e 1 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 8\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/w/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/w/) : You have been eaten by the monster. Game over!";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testReachedDestination() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("1 s 1 e 1 n 3 1 n 3 1 n 1 n 1 w 2 21 2 22 2 23 1 "
            +  "e 2 30 1 n 1 n 1 n 3 1 e 3 1 e 1 e 1 n 3 1 e  3 1 e 1 e");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
    String expected =  "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 6\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/) : "
            + "Arrow Shot!\nCell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/) : "
            + "Arrow Shot!\nCell id: 2\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/s/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/) : Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 21 Item Name: ARROW\n"
            + "Item Id: 22 Item Name: ARROW\n"
            + "Item Id: 23 Item Name: ARROW\n"
            + "Item Id: 24 Item Name: ARROW\n"
            + "Item Id: 25 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 22 Item Name: ARROW\n"
            + "Item Id: 23 Item Name: ARROW\n"
            + "Item Id: 24 Item Name: ARROW\n"
            + "Item Id: 25 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 23 Item Name: ARROW\n"
            + "Item Id: 24 Item Name: ARROW\n"
            + "Item Id: 25 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 21\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (s/w/e)Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Available treasures: \n"
            + "Item Id: 7 Item Name: SAPPHIRE\n"
            + "Item Id: 8 Item Name: RUBY\n"
            + "Item Id: 9 Item Name: RUBY\n"
            + "Item Id: 10 Item Name: SAPPHIRE\n"
            + "Item Id: 11 Item Name: RUBY\n"
            + "Item Id: 30 Item Name: ARROW\n"
            + "Enter item id: ARROW collected\n"
            + "Cell id: 22\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to (n/s/w/) : :\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/) : Cell id: 17\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 12\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)"
            + "Arrow Shot!\nCell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/w/e)"
            + "Arrow Shot!\nCell id: 7\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/w/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/s/w/e)Cell id: 8\n"
            + "You are in a TUNNEL\n"
            + "Doors lead to (n/w/) : :\n"
            + "Warning! A light pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction (n/w/) : Cell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/e)"
            + "Arrow Shot!\nCell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Warning! A strong pungent smell detected.\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Distance (1-5): Enter direction (n/s/e)Arrow Shot!\n"
            + "Cell id: 3\n"
            + "You are in a CAVE\n"
            + "Doors lead to (n/s/e):\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit"
            + "\nEnter Direction (n/s/e)Destination Reached, You win!!";
    assertEquals(expected, out.toString());
    assertTrue(dungeon.isPlayerWinner());
  }

  @Test
  public void testMoveWithMockModel() {
    StringBuilder out = new StringBuilder();
    StringReader input = new StringReader("1 s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(out, 1));
    String expected =  "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Enter Direction Get the player location description\n"
            + "() : Move player in direction: SOUTH Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testMoveWithMockModel2() {
    StringBuilder out = new StringBuilder();
    StringBuilder modelOut = new StringBuilder();
    StringReader input = new StringReader("1 s");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(modelOut, 3));
    String expected = "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Get the player location description\n"
            + "Move player in direction: SOUTH Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n";
    assertEquals(expected, modelOut.toString());
  }

  @Test
  public void testCollectWithMockModel() {
    StringBuilder out = new StringBuilder();
    StringReader input = new StringReader("2 11");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(out, 1));
    String expected =  "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Get the player location description\n"
            + "Available treasures: \n"
            + "Item Id: 11 Item Name: RUBY\n"
            + "Enter item id: Collecting Treasure with treasureId: 11RUBY collected\n"
            + " Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testCollectWithMockModel2() {
    StringBuilder out = new StringBuilder();
    StringBuilder modelOut = new StringBuilder();
    StringReader input = new StringReader("2 11");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(modelOut, 3));
    String expected = "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Get the player location description\n"
            + "Collecting Treasure with treasureId: 11 Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n";
    assertEquals(expected, modelOut.toString());
  }

  @Test
  public void testShootWithMockModel() {
    StringBuilder out = new StringBuilder();
    StringReader input = new StringReader("3 3 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(out, 3));
    String expected =  "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Get the player location description\n"
            + "Enter Distance (1-5): Enter direction () : Shoot Arrow in direction: "
            + "NORTH, at distance: 3"
            + "Arrow Shot!\n Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShootWithMockModel2() {
    StringBuilder out = new StringBuilder();
    StringBuilder modelOut = new StringBuilder();
    StringReader input = new StringReader("3 3 n");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(modelOut, 3));
    String expected =  "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Get the player location description\n"
            + "Shoot Arrow in direction: NORTH, at distance: 3 Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n";
    assertEquals(expected, modelOut.toString());
  }

  @Test
  public void testShowCollectedItemsWithMockModel() {
    StringBuilder out = new StringBuilder();
    StringReader input = new StringReader("4");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(out, 3));
    String expected = "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Get the player description\n"
            + "Item id\t|\tItem Name\n"
            + "-----------------------\n"
            + "3\t\t|\tARROW\n"
            + " Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Cell id: 1\n"
            + "You are in a CAVE\n"
            + "You can collect items at this location.\n"
            + "Doors lead to () : :\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Choose Action : \n"
            + " 1. Move\n"
            + " 2. Collect Treasure/Arrows\n"
            + " 3. Shoot arrow.\n"
            + " 4. Show collected items\n"
            + " 5. Quit\n"
            + "Reached end of input";
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowCollectedItemsWithMockModel2() {
    StringBuilder out = new StringBuilder();
    StringBuilder modelOut = new StringBuilder();
    StringReader input = new StringReader("4");
    controller = new DungeonControllerImpl(input, out);
    controller.play(new MockDungeonModel(modelOut, 3));
    String expected =  "Player enters the dungeon\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n"
            + "Get the player description\n"
            + " Check if player has won the game\n"
            + " Check if game is over\n"
            + " Check if game is over\n"
            + " Check if player has won the game\n"
            + "Get the player location description\n"
            + "Check if strong smell is detected\n"
            + " Check if light smell is detected\n";
    assertEquals(expected, modelOut.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void failingAppendableTest() {
    Appendable out = new FailingAppendable();
    StringReader input = new StringReader("4");
    controller = new DungeonControllerImpl(input, out);
    controller.play(dungeon);
  }

  @Test
  public void invalidModelTest() {
    Appendable out = new StringBuilder();
    StringReader input = new StringReader("4");
    controller = new DungeonControllerImpl(input, out);
    controller.play(null);
    assertEquals("Invalid model passed!", out.toString());
  }
}
