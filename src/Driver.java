import controller.DungeonControllerImpl;
import controller.DungeonControllerV2;
import controller.DungeonControllerV2Impl;
import dungeon.Dungeon;
import dungeon.DungeonImpl;

import java.io.InputStreamReader;

/**
 * This driver class is used to execute the program.
 */
public class Driver {

  /**
   * This method is used for program execution.
   * @param args  program arguments.
   */
  public static void main(String[] args) {
    if (args.length < 6) {
      DungeonControllerV2 controller = new DungeonControllerV2Impl();
      controller.play();

    } else {
      int row = Integer.parseInt(args[0]);
      int col = Integer.parseInt(args[1]);
      int interconnectivity = Integer.parseInt(args[2]);
      int numMonster = Integer.parseInt(args[3]);
      int treasurePercentage = Integer.parseInt(args[4]);
      String wrap = args[5];
      boolean isWrapping = wrap.equals("wrap");
      Dungeon dungeon = new DungeonImpl(treasurePercentage, row, col, interconnectivity, isWrapping,
              numMonster);
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;
      new DungeonControllerImpl(input, output).play(dungeon);
    }
  }
}