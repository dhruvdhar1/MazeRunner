package view;

import static view.FileConstants.BLANK_IMG_PATH;
import static view.FileConstants.IMAGE_PATH;
import static view.ViewConstants.CELL_WH;

import dungeon.ItemType;
import dungeon.PlayerLocationDescription;
import dungeon.ReadOnlyDungeon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This package-private class represents the view that
 * renders the maze of the game. this view includes the cells, treasure in the cells,
 * player, otyugh, pits etc.
 */
class MazeView extends JPanel {

  private final ReadOnlyDungeon readOnlyDungeon;
  private final int numRows;
  private final int numCol;
  private int currRow;
  private int currCol;
  private final Map<Integer, PlayerLocationDescription> visitedNodes;
  private static final String STRONG_STENCH = "strong";
  private static final String LIGHT_STENCH = "light";

  /**
   * This constructor creates an instance of the MazeView class and initializes
   * the required parameters necessary for the maze rendering.
   *
   * @param readOnlyDungeon read-only model.
   * @param numRows         number of rows in the dungeon.
   * @param numCol          number of columns in the dungeon.
   * @param visitedNodes    Map of all the cells visited by the player.
   */
  MazeView(ReadOnlyDungeon readOnlyDungeon, int numRows, int numCol,
                  Map<Integer, PlayerLocationDescription> visitedNodes) {
    this.readOnlyDungeon = readOnlyDungeon;
    this.numRows = numRows;
    this.numCol = numCol;
    this.currRow = 0;
    this.currCol = 0;
    this.visitedNodes = visitedNodes;
    this.setLayout(new GridLayout(numRows, numCol));
    this.setPreferredSize(new Dimension(CELL_WH * numRows, CELL_WH * numCol));
    this.setBackground(new Color(3, 36, 51));
  }

  /**
   * This method is used to overlay an image over another.
   *
   * @param starting starting image
   * @param fpath    path of the image that needs to be overlayed.
   * @param offset   overlay offset.
   * @return new overlayed image.
   * @throws IOException if error encountered during reading a file from disk.
   */
  private BufferedImage overlay(BufferedImage starting, String fpath,
                                int offset) throws IOException {
    BufferedImage overlay = ImageIO.read(getClass().getResourceAsStream(fpath));
    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);
    Graphics2D g2d = (Graphics2D) g;
    g2d.scale(0.8, 0.8);
    g2d.drawImage(overlay, offset / 2, offset, null);
    return combined;
  }

  private String getCellPaths(PlayerLocationDescription description) {
    StringBuilder paths = new StringBuilder();
    paths.append(description.canMoveNorth() ? "n" : "");
    paths.append(description.canMoveSouth() ? "s" : "");
    paths.append(description.canMoveEast() ? "e" : "");
    paths.append(description.canMoveWest() ? "w" : "");
    return paths.toString();
  }

  private void drawGrid(Graphics2D g, int rowHt, int rowWid) {
    for (int i = 0; i < numCol; i++) {
      g.drawLine(0, i * rowHt, getWidth(), i * rowHt);
    }
    for (int i = 0; i < numRows; i++) {
      g.drawLine(i * rowWid, 0, i * rowWid, getHeight());
    }
  }

  private BufferedImage addItemIconToCaves(BufferedImage image,
                                           String paths,
                                           int currentCellId, int currLocation) throws IOException {
    int offset = 10;
    Map<Integer, ItemType> collectables = visitedNodes.get(currentCellId)
            .getAvailableItems();
    if (collectables.values().size() > 0) {
      for (ItemType item : collectables.values()) {
        String pathToImg = new StringBuilder().append(IMAGE_PATH).append("/")
                .append(FileConstants.ITEM_TO_FILE_ICON_MAPPING.get(item)).toString();
        image = overlay(image, pathToImg, offset += 5);
      }
    }
    if (currentCellId == currLocation
            && readOnlyDungeon.isGameOver()
            && !readOnlyDungeon.isPlayerWinner()) {
      String pathToImg;
      if (readOnlyDungeon.hasPit()) {

        pathToImg = new StringBuilder().append(IMAGE_PATH).append("/")
                .append(FileConstants.PIT_IMG_PATH).toString();
        image = overlay(image, pathToImg, offset + 5);
      } else {

        pathToImg = new StringBuilder().append(IMAGE_PATH).append("/")
                .append(FileConstants.OTYUGH_IMG_PATH).toString();
      }
      image = overlay(image, pathToImg, offset += 10);
    }
    return image;
  }

  private BufferedImage addStenchToCells(BufferedImage image) throws IOException {
    int offset = 10;
    boolean lightSmell = readOnlyDungeon.lightSmellDetected();
    boolean strongSmell = readOnlyDungeon.strongSmellDetected();
    if (lightSmell) {
      String pathToImg = new StringBuilder().append(IMAGE_PATH).append("/")
              .append(FileConstants.STENCH_FILE_MAPPING.get(LIGHT_STENCH)).toString();
      image = overlay(image, pathToImg, offset += 5);
    }
    if (strongSmell) {
      String pathToImg = new StringBuilder().append(IMAGE_PATH).append("/")
              .append(FileConstants.STENCH_FILE_MAPPING.get(STRONG_STENCH)).toString();
      image = overlay(image, pathToImg, offset + 5);
    }
    String playerImg = new StringBuilder().append(IMAGE_PATH).append("/")
            .append(FileConstants.PLAYER_IMG_PATH).toString();
    if (!(!readOnlyDungeon.isPlayerWinner() && readOnlyDungeon.isGameOver())) {
      image = overlay(image, playerImg, 10);
    }
    return image;
  }

  private BufferedImage addThiefToCave(BufferedImage image) throws IOException {
    int offset = 10;
    boolean hasThief = readOnlyDungeon.hasThief();
    if (hasThief) {
      String pathToImg = new StringBuilder().append(IMAGE_PATH).append("/")
              .append(FileConstants.THIEF_IMG_PATH).toString();
      image = overlay(image, pathToImg, offset += 5);
    }
    return image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    int rowHt = CELL_WH;
    int rowWid = CELL_WH;
    drawGrid(g2d, rowHt, rowWid);
    int cellId = 0;
    Map<String, Integer> dungeonMap = new HashMap<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCol; j++) {
        dungeonMap.put(String.format("%d%d", i, j), ++cellId);
      }
    }
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCol; j++) {
        try {
          int currentCellId = dungeonMap.get(String.format("%d%d", i, j));
          int x = (j * rowWid);
          int y = (i * rowHt);
          int currLocation = readOnlyDungeon.getPlayerLocationDescription().getCellId();
          visitedNodes.put(currLocation, readOnlyDungeon.getPlayerLocationDescription());

          if (visitedNodes.containsKey(currentCellId)) {
            String paths = visitedNodes.containsKey(currentCellId)
                    ? getCellPaths(visitedNodes.get(currentCellId)) : "";
            BufferedImage image = null;
            if (paths.length() != 2) {
              image = ImageIO.read(getClass()
                      .getResourceAsStream(String.format("%s/%s", IMAGE_PATH,
                              FileConstants.CAVE_TO_ICON_MAPPING.get(paths))));
            } else {
              image = ImageIO.read(getClass()
                      .getResourceAsStream(String.format("%s/%s", IMAGE_PATH,
                              FileConstants.TUNNEL_TO_ICON_MAPPING.get(paths))));
            }
            image = addItemIconToCaves(image, paths, currentCellId, currLocation);
            if (currentCellId == currLocation) {
              currRow = i;
              currCol = j;
              image = addStenchToCells(image);
              image = addThiefToCave(image);
            }
            g.drawImage(image, x, y, rowWid - 1, rowHt - 1, null);
          } else {
            BufferedImage image = ImageIO.read(getClass()
                    .getResourceAsStream(String.format("%s/%s", IMAGE_PATH,BLANK_IMG_PATH)));
            g.drawImage(image, x, y, rowWid - 1, rowHt - 1, null);
          }
        } catch (IOException e) {
          System.out.println("Error while reading image file!");
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * This package-private method gets the column index of player's current position.
   * @return  row index
   */
  int getCurrentRow() {
    return currRow;
  }

  /**
   * This package-private method gets the row index of player's current position.
   * @return  row index
   */
  int getCurrentCol() {
    return currCol;
  }
}