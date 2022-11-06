package controller;

import dungeon.Direction;
import dungeon.Dungeon;
import dungeon.ItemType;
import dungeon.PlayerLocationDescription;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents the implementation for the dungeon game's controller. Provide user with
 *    choices and executes operations using the model based on the user choices.
 */
public class DungeonControllerImpl implements DungeonController {

  private final Scanner input;
  private final Appendable out;

  /**
   * This constructor creates an instance of the {@link DungeonControllerImpl} class.
   * @param input the source to read from
   * @param out the target to print to
   */
  public DungeonControllerImpl(Readable input, Appendable out) {
    this.input = new Scanner(input);
    this.out = out;
  }

  private Direction getDirectionFromUserInput(String userInput) {
    if (userInput.equalsIgnoreCase("n")) {
      return Direction.NORTH;
    } else if (userInput.equalsIgnoreCase("s")) {
      return Direction.SOUTH;
    } else if (userInput.equalsIgnoreCase("e")) {
      return Direction.EAST;
    } else if (userInput.equalsIgnoreCase("w")) {
      return Direction.WEST;
    }
    return null;
  }

  private void movePlayer(Dungeon model) throws IOException {
    String ch = null;
    boolean moved = false;
    while (!moved) {
      try {
        out.append("Enter Direction ");
        getDirectionDescription(model.getPlayerLocationDescription());
        ch = input.next();
        model.movePlayer(getDirectionFromUserInput(ch));
        moved = Boolean.TRUE;
      } catch (IllegalArgumentException ex) {
        out.append("Invalid Direction '").append(ch).append("' provided. ");
      }
    }
  }

  private void collectTreasure(Dungeon model) throws IOException {
    Map<Integer, ItemType> list = model.getPlayerLocationDescription().getAvailableItems();
    String id = null;
    boolean treasureCollected = false;
    out.append("Available treasures: \n");
    for (int key : list.keySet()) {
      out.append("Item Id: ").append(new StringBuilder().append(key))
              .append(" Item Name: ").append(list.get(key).toString()).append("\n");
    }
    while (!treasureCollected) {
      try {
        if (list.size() > 0) {
          out.append("Enter item id: ");
          id = input.next();
          int parsed = Integer.parseInt(id);
          model.collectTreasure(parsed);
          out.append(new StringBuilder().append(list.get(parsed)).append(" collected\n"));
        } else {
          out.append("Nothing to collect at this location.\n");
        }
        treasureCollected = Boolean.TRUE;
      } catch (IllegalArgumentException ex) {
        out.append("Invalid treasure id '").append(id).append("' provided. ");
      }
    }
  }

  private void printLocationDetail(Dungeon model) throws IOException {
    PlayerLocationDescription desc = model.getPlayerLocationDescription();
    out.append("Cell id: ").append(new StringBuilder().append(desc.getCellId())).append("\n");
    out.append("You are in a ").append(desc.getLocation().toString()).append("\n");
    if (desc.getAvailableItems().size() > 0) {
      out.append("You can collect items at this location.\n");
    }
    out.append("Doors lead to ");
    getDirectionDescription(desc);
    out.append(":\n");
    if (model.strongSmellDetected()) {
      out.append("Warning! A strong pungent smell detected.\n");
    } else if (model.lightSmellDetected()) {
      out.append("Warning! A light pungent smell detected.\n");
    }
  }

  private void shootArrow(Dungeon model) throws IOException {
    PlayerLocationDescription desc = model.getPlayerLocationDescription();
    int dist = -1;
    boolean arrowShot = false;
    while (!arrowShot) {
      try {
        out.append("Enter Distance (1-5): ");
        dist = Integer.parseInt(input.next());
        out.append("Enter direction ");
        getDirectionDescription(desc);
        String ch = input.next();
        model.shootArrow(getDirectionFromUserInput(ch), dist);
        out.append("Arrow Shot!\n");
        arrowShot = Boolean.TRUE;
      } catch (IllegalArgumentException ex) {
        out.append("\nInvalid distance or direction provided. ");
      } catch (IllegalStateException ex) {
        out.append("\nCannot shoot, No arrows available.\n");
        break;
      }
    }
  }

  private void getPlayerDescription(Dungeon model) throws IOException {
    Map<Integer, ItemType> playerDescription = model.getPlayerDescription();
    if (playerDescription.isEmpty()) {
      out.append("No available items.\n");
    } else {
      out.append("Item id\t|\tItem Name\n");
      out.append("-----------------------\n");
      for (int key : playerDescription.keySet()) {
        out.append(new StringBuilder().append(key)).append("\t\t|\t")
                .append(playerDescription.get(key).toString()).append("\n");
      }
    }
  }

  private void getDirectionDescription(PlayerLocationDescription desc) throws IOException {
    out.append(desc.canMoveNorth() ? "(n/" : "(");
    out.append(desc.canMoveSouth() ? "s/" : "");
    out.append(desc.canMoveWest() ? "w/" : "");
    out.append(desc.canMoveEast() ? "e)" : ") : ");
  }

  private int executeAction(int choice, Dungeon model) throws IOException {
    switch (choice) {
      case 1:
        movePlayer(model);
        break;
      case 2:
        collectTreasure(model);
        break;
      case 3:
        shootArrow(model);
        break;
      case 4:
        getPlayerDescription(model);
        break;
      case 5:
        return 0;
      default:
        return -1;
    }
    return 1;
  }

  private void runGame(Dungeon model) throws IOException {
    boolean invalidInput = false;
    while (!model.isGameOver() && !model.isPlayerWinner()) {
      try {
        if (!invalidInput) {
          printLocationDetail(model);
          out.append("Choose Action : \n 1. Move\n 2. Collect Treasure/Arrows\n "
                  + "3. Shoot arrow.\n 4. Show collected items\n 5. Quit\n");
        }
        String choice = input.next();
        int parsedOption = Integer.parseInt(choice);
        int executed = executeAction(parsedOption, model);
        if (executed == -1) {
          throw new IllegalArgumentException("invalid input");
        } else if (executed == 0) {
          out.append("Game Quit");
          return;
        }
        if (model.isPlayerWinner()) {
          out.append("Destination Reached, You win!!");
          return;
        } else if (model.isGameOver()) {
          out.append("You have been eaten by the monster. Game over!");
          return;
        }
        invalidInput = false;
      } catch (IllegalArgumentException ex) {
        invalidInput = true;
        out.append("Invalid input. Try again\n");
      } catch (NoSuchElementException ex) {
        out.append("Reached end of input");
        break;
      }
    }
  }


  @Override
  public void play(Dungeon model) {
    try {
      if (null == model) {
        out.append("Invalid model passed!");
        return;
      }
      model.enterDungeon();
      runGame(model);
      System.out.println();

    } catch (IOException ex) {
      throw new IllegalStateException("An error occurred. Please try again");
    }
  }
}
