package view;

import static view.ViewConstants.CELL_WH;
import static view.ViewConstants.COLLECT_TREASURE_HEADING;
import static view.ViewConstants.DIRECTION_CHOICE;
import static view.ViewConstants.DISTANCE_CHOICE;
import static view.ViewConstants.GAME_TITLE;
import static view.ViewConstants.GENERIC_INFO_MSG;
import static view.ViewConstants.NO_ARROWS_AVAILABLE;
import static view.ViewConstants.NO_ITEM_AVAILABLE;
import static view.ViewConstants.PLAYER_INVENTORY_DET_MSG;
import static view.ViewConstants.SELECT_DIRECTION;
import static view.ViewConstants.SELECT_DISTANCE;
import static view.ViewConstants.SHOOT_ARROW_HEADING;

import controller.DungeonControllerV2;
import dungeon.Direction;
import dungeon.ItemType;
import dungeon.PlayerLocationDescription;
import dungeon.ReadOnlyDungeon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * This interface represents the overall view of the Dungeon Game and implements
 * the various operations that can be performed on the view.
 */
public class DungeonViewImpl extends JFrame implements DungeonView {

  private Map<Integer, PlayerLocationDescription> visitedNodes;
  private int numRows;
  private int numCols;
  private final ReadOnlyDungeon readOnlyModel;
  private final Container rootContainer;
  private final BorderLayout rootLayout;
  private final JPanel gamePanel;
  private final JButton moveNorthBtn;
  private final JButton moveSouthBtn;
  private final JButton moveEastBtn;
  private final JButton moveWestBtn;
  private final JButton collectBtn;
  private final JButton shootBtn;
  private boolean isSPressed;
  private BorderLayout gameLayout;
  private MazeView mazeView;
  private JLabel infoLabel;
  private JMenuItem restart;
  private JMenuItem newGame;
  private JMenuItem quit;

  /**
   * This constructor creates an instance of the Dungeon view and expects a
   *    read-only model as a parameter.
   * @param readOnlyDungeonModel  read-only model of the game.
   * @throws IllegalArgumentException if null model passed.
   */
  public DungeonViewImpl(ReadOnlyDungeon readOnlyDungeonModel) {
    super(GAME_TITLE);
    if (readOnlyDungeonModel == null) {
      throw new IllegalArgumentException("Null model passed");
    }
    this.visitedNodes = new HashMap<>();
    this.readOnlyModel = readOnlyDungeonModel;
    this.gamePanel = new JPanel();
    rootContainer = new Container();
    rootLayout = new BorderLayout();
    this.isSPressed = false;
    collectBtn = new JButton("Col Treasure");
    shootBtn = new JButton("Shoot Arrow");
    moveNorthBtn = new JButton("North");
    moveNorthBtn.setPreferredSize(new Dimension(20, 10));
    moveSouthBtn = new JButton("South");
    moveEastBtn = new JButton("East");
    moveWestBtn = new JButton("West");
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setContentPane(rootContainer);
    this.setMaximumSize(new Dimension(1024, 800));
  }

  /**
   * This method renders the side panel of the game UI.
   * @return  side view panel.
   */
  private JPanel getSidePane() {
    try {
      JPanel sidePane = new JPanel();
      sidePane.setBackground(new Color(13, 108, 154));
      sidePane.setLayout(new BorderLayout());
      JLabel header = new JLabel(PLAYER_INVENTORY_DET_MSG);
      header.setPreferredSize(new Dimension(200, 50));
      header.setHorizontalAlignment(SwingConstants.CENTER);
      sidePane.add(header, BorderLayout.NORTH);
      header.setForeground(Color.WHITE);
      JPanel treasureList = new JPanel();
      treasureList.setBackground(new Color(94, 182, 27));
      Map<ItemType, Integer> playerBag =
              deserializeTreasureQuantity(readOnlyModel.getPlayerDescription());
      treasureList.setLayout(new GridLayout(5, 3));
      treasureList.setPreferredSize(new Dimension(200, 50));
      for (ItemType item : playerBag.keySet()) {
        String pathToImg = new StringBuilder().append(FileConstants.IMAGE_PATH).append("/")
                .append(FileConstants.ITEM_TO_FILE_ICON_MAPPING.get(item)).toString();
        BufferedImage myPicture = ImageIO.read(getClass().getResourceAsStream(pathToImg));
        treasureList.add(new JLabel(new ImageIcon(myPicture)));
        treasureList.add(new JLabel(item.toString()));
        treasureList.add(new JLabel(playerBag.get(item).toString()));
      }
      for (int i = 0; i < (5 - playerBag.size()); i++) {
        treasureList.add(new JLabel());
        treasureList.add(new JLabel());
        treasureList.add(new JLabel());
      }
      sidePane.add(treasureList, BorderLayout.CENTER);
      return sidePane;
    } catch (IOException ex) {
      System.out.println("Error occurred while reading file");
    }
    return null;
  }

  /**
   * This method deserializes the player bag data received from the read-only model.
   * @param playerBag player bag data
   * @return  deserialized data.
   */
  private Map<ItemType, Integer> deserializeTreasureQuantity(Map<Integer, ItemType> playerBag) {
    Map<ItemType, Integer> deserializedBag = new HashMap<>();
    for (int key : playerBag.keySet()) {
      ItemType item = playerBag.get(key);
      if (deserializedBag.containsKey(item)) {
        int quantity = deserializedBag.get(item);
        deserializedBag.put(item, quantity + 1);
      } else {
        deserializedBag.put(item, 1);
      }
    }
    return deserializedBag;
  }

  /**
   * This method renders the bottom panel of the game UI containing the game control buttons.
   * @return  side view panel.
   */
  private JPanel getControlPane() {
    JPanel footer = new JPanel();
    footer.setLayout(new GridLayout(2, 1));
    footer.setBackground(new Color(94, 182, 27));
    infoLabel = new JLabel(GENERIC_INFO_MSG);
    infoLabel.setPreferredSize(new Dimension(-1, 50));
    infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
    footer.add(infoLabel);
    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(1, 5));
    controlPanel.setBackground(new Color(94, 182, 27));
    controlPanel.add("Right", moveEastBtn);
    controlPanel.add("Left", moveWestBtn);
    controlPanel.add("Up", moveNorthBtn);
    controlPanel.add("Down", moveSouthBtn);
    controlPanel.add("Collect", collectBtn);
    controlPanel.add("Shoot", shootBtn);
    footer.add(controlPanel);
    return footer;
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void makeVisible(int numRows, int numCol) {
    this.numCols = numCol;
    this.numRows = numRows;
    renderComponent();
    setVisible(true);
    pack();
  }

  /**
   * This helper method helps in moving the player to a particular direction
   * based on user click on a neighbouring cell.
   */
  private void directionHelper(DungeonControllerV2 listener, int moveX, int moveY,
                               int playerLocationX, int playerLocationY) {
    if (moveX - playerLocationX == 1 && moveY == playerLocationY) {
      listener.movePlayer(Direction.EAST);
    } else if (moveX - playerLocationX == -1 && moveY == playerLocationY) {
      listener.movePlayer(Direction.WEST);
    } else if (moveY - playerLocationY == 1 && moveX == playerLocationX) {
      listener.movePlayer(Direction.SOUTH);
    } else if (moveY - playerLocationY == -1 && moveX == playerLocationX) {
      listener.movePlayer(Direction.NORTH);
    }
  }

  @Override
  public void addClickListener(DungeonControllerV2 listener) {
    if (null == listener) {
      throw new IllegalArgumentException("Invalid controller passed!");
    }
    mazeView.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int x = e.getX() / CELL_WH;
        int y = e.getY() / CELL_WH;
        int playerLocationX = mazeView.getCurrentCol();
        int playerLocationY = mazeView.getCurrentRow();
        directionHelper(listener, x, y, playerLocationX, playerLocationY);
      }
    });

    MouseAdapter moveDirectionNorth = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        listener.movePlayer(Direction.NORTH);
      }
    };
    moveNorthBtn.addMouseListener(moveDirectionNorth);

    MouseAdapter moveDirectionSouth = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        listener.movePlayer(Direction.SOUTH);
      }
    };
    moveSouthBtn.addMouseListener(moveDirectionSouth);

    MouseAdapter moveDirectionEast = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        listener.movePlayer(Direction.EAST);
      }
    };
    moveEastBtn.addMouseListener(moveDirectionEast);

    MouseAdapter moveDirectionWest = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        listener.movePlayer(Direction.WEST);
      }
    };
    moveWestBtn.addMouseListener(moveDirectionWest);

    restart.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        visitedNodes = new HashMap<>();
        dispose();
        listener.restartGame();
      }
    });
    quit.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        dispose();
        listener.quitGame();
      }
    });

    newGame.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        dispose();
        listener.initiateNewGame();
      }
    });
    addShootHandler(listener);
    addKeyboardShortcuts(listener);
    addCollectTreasureHandler(listener);
  }


  /**
   * Add event handler for the shoot button.
   * @param listener  controller instance.
   */
  private void addShootHandler(DungeonControllerV2 listener) {
    MouseAdapter shootArrow = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int numArrows = getNumArrows();
        try {
          BufferedImage icon = ImageIO.read(getClass().getResourceAsStream(String.format("%s/%s",
                  FileConstants.IMAGE_PATH,
                  FileConstants.BLACK_ARROW)));
          if (numArrows > 0) {
            Direction[] direction = { Direction.NORTH };
            final int[] distance = {1};
            JPanel form = new JPanel(new GridLayout(2, 2));
            JComboBox<Direction> directionDropDown = new JComboBox(DIRECTION_CHOICE);
            JComboBox<String> distanceDropDown = new JComboBox(DISTANCE_CHOICE);
            directionDropDown.addActionListener(e12 ->
                    direction[0] = (Direction) directionDropDown.getSelectedItem());
            distanceDropDown.addActionListener(e1 ->
                    distance[0] = Integer.parseInt(distanceDropDown.getSelectedItem().toString()));
            form.add(new JLabel(SELECT_DIRECTION));
            form.add(directionDropDown);
            form.add(new JLabel(SELECT_DISTANCE));
            form.add(distanceDropDown);
            int res = JOptionPane.showConfirmDialog(null, form,
                    SHOOT_ARROW_HEADING, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon));
            if (res == 0) {
              if (null != direction[0] && 0 != distance[0]) {
                listener.shootArrow(direction[0], distance[0]);
                resetPlayerBagUiAfterArrowShot();
              }
            }
          } else {
            JOptionPane.showConfirmDialog(null, NO_ARROWS_AVAILABLE,
                    SHOOT_ARROW_HEADING, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon));
          }
          gamePanel.requestFocusInWindow();
        } catch (IOException ex) {
          System.out.println("Error occurred while reading file!");
          ex.printStackTrace();
        }

      }
    };
    shootBtn.addMouseListener(shootArrow);
  }

  private void enterDistanceDialogue(Direction d, DungeonControllerV2 listener) {
    try {
      BufferedImage icon = ImageIO.read(getClass().getResourceAsStream(String.format("%s/%s",
              FileConstants.IMAGE_PATH,
              FileConstants.BLACK_ARROW)));
      int numArrows = getNumArrows();
      if (numArrows > 0) {
        final int[] distance = {1};
        JPanel form = new JPanel(new GridLayout(1, 2));
        JComboBox<String> distanceDropDown = new JComboBox(DISTANCE_CHOICE);

        distanceDropDown.addActionListener(e1 ->
                distance[0] = Integer.parseInt(distanceDropDown.getSelectedItem().toString()));
        form.add(new JLabel(SELECT_DISTANCE));
        form.add(distanceDropDown);
        int res = JOptionPane.showConfirmDialog(null, form,
                SHOOT_ARROW_HEADING, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon));
        if (res == 0) {
          if (null != d && 0 != distance[0]) {
            listener.shootArrow(d, distance[0]);
            isSPressed = false;
            resetPlayerBagUiAfterArrowShot();
          }
        }
      } else {
        JOptionPane.showConfirmDialog(null, NO_ARROWS_AVAILABLE,
                SHOOT_ARROW_HEADING, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon));
      }
    } catch (IOException ex) {
      System.out.println("Error occurred while reading file!");
      ex.printStackTrace();
    }
  }


  /**
   * Rerender the side panel after shooting an arrow to update the side panel.
   */
  private void resetPlayerBagUiAfterArrowShot() {
    gamePanel.remove(gameLayout.getLayoutComponent(BorderLayout.WEST));
    gamePanel.add(getSidePane(), BorderLayout.WEST);
    gamePanel.revalidate();
  }

  /**
   * Add event handler for the collect button.
   * @param listener  controller instance.
   */
  private void addCollectTreasureHandler(DungeonControllerV2 listener) {
    MouseAdapter collectTreasure = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        try {
          renderCollectTreasureDialogue(listener);
        } catch (IOException ex) {
          System.out.println("Error occurred while reading file!");
          ex.printStackTrace();
        }
      }
    };
    collectBtn.addMouseListener(collectTreasure);
  }

  /**
   * Renders the collect dialogue box upon click on the collect button.
   * @param listener  controller instance.
   */
  private void renderCollectTreasureDialogue(DungeonControllerV2 listener) throws IOException {
    Map<Integer, ItemType> availableItems = readOnlyModel.getPlayerLocationDescription()
            .getAvailableItems();
    if (availableItems.size() > 0) {
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(availableItems.size(), 3));
      Set<Integer> selectedItems = new HashSet<>();
      for (int id : availableItems.keySet()) {
        String imgPath = new StringBuilder().append(FileConstants.IMAGE_PATH)
                .append("/").append(FileConstants.ITEM_TO_FILE_ICON_MAPPING.get(
                        availableItems.get(id))).toString();
        BufferedImage myPicture = ImageIO.read(getClass().getResourceAsStream(imgPath));
        panel.add(new JLabel(new ImageIcon(myPicture)));
        panel.add(new JLabel(" " + availableItems.get(id).toString()));
        JCheckBox checkBox = new JCheckBox();
        panel.add(checkBox);
        checkBox.addItemListener(new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == 1) {
              selectedItems.add(id);
            } else {
              selectedItems.remove(id);
            }
          }
        });
      }
      BufferedImage icon = ImageIO.read(getClass().getResourceAsStream(String.format("%s/%s",
              FileConstants.IMAGE_PATH,
              FileConstants.ITEM_TO_FILE_ICON_MAPPING.get(ItemType.SAPPHIRE))));
      int res = JOptionPane.showConfirmDialog(null, panel,
              COLLECT_TREASURE_HEADING, JOptionPane.DEFAULT_OPTION,
              JOptionPane.INFORMATION_MESSAGE, new ImageIcon(icon));
      if (res == 0) {
        listener.collectTreasure(selectedItems);
        reRenderSidePanel();
      }
    } else {
      JOptionPane.showMessageDialog(null,
              NO_ITEM_AVAILABLE,
              COLLECT_TREASURE_HEADING, JOptionPane.ERROR_MESSAGE);
    }
    gamePanel.requestFocusInWindow();
  }

  @Override
  public void reRenderSidePanel() {
    gamePanel.remove(gameLayout.getLayoutComponent(BorderLayout.WEST));
    gamePanel.add(getSidePane(), BorderLayout.WEST);
    gamePanel.revalidate();
  }

  /**
   * Adds key listeners for the game actions.
   * @param listener  controller instance.
   */
  private void addKeyboardShortcuts(DungeonControllerV2 listener) {
    addMoveKeyboardHandler(listener);
    addShootKeyboardHandler();
    addCollectTreasureKeyboardHandler(listener);
  }

  /**
   * Adds the key listener for moving the player in a direction.
   * @param listener  controller instance.
   */
  private void addMoveKeyboardHandler(DungeonControllerV2 listener) {
    gamePanel.registerKeyboardAction(e -> {
      if (isSPressed) {
        enterDistanceDialogue(Direction.EAST, listener);
      } else {
        listener.movePlayer(Direction.EAST);
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0 ),
         JComponent.WHEN_IN_FOCUSED_WINDOW );
    gamePanel.registerKeyboardAction(e -> {
      if (isSPressed) {
        enterDistanceDialogue(Direction.WEST, listener);
      } else {
        listener.movePlayer(Direction.WEST);
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0 ),
         JComponent.WHEN_IN_FOCUSED_WINDOW );
    gamePanel.registerKeyboardAction(e -> {
      if (isSPressed) {
        enterDistanceDialogue(Direction.NORTH, listener);
      } else {
        listener.movePlayer(Direction.NORTH);
      }
    }, KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 ),
            JComponent.WHEN_IN_FOCUSED_WINDOW );
    gamePanel.registerKeyboardAction(e -> {
      if (isSPressed) {
        enterDistanceDialogue(Direction.SOUTH, listener);
      } else {
        listener.movePlayer(Direction.SOUTH);
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0 ),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  /**
   * Adds the key listener for collecting treasure from a cell.
   * @param listener  controller instance.
   */
  private void addCollectTreasureKeyboardHandler(DungeonControllerV2 listener) {
    gamePanel.registerKeyboardAction(e -> {
      try {
        renderCollectTreasureDialogue(listener);
      } catch (IOException ex) {
        System.out.println("Error occurred while reading file!");
        ex.printStackTrace();
      }
    },
        KeyStroke.getKeyStroke(KeyEvent.VK_C, 0),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  /**
   * Adds the key listener for shooting an arrow in a direction.
   */
  private void addShootKeyboardHandler() {
    gamePanel.setFocusable(true);
    gamePanel.requestFocusInWindow();
    gamePanel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_S) {
          isSPressed = true;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_S) {
          isSPressed = false;
        }
      }
    });
  }

  /**
   * Get the number of arrows present in the player bag.
   * @return number of Arrows.
   */
  private int getNumArrows() {
    Map<Integer, ItemType> playerBag = readOnlyModel.getPlayerDescription();
    Collection<ItemType> items = playerBag.values();
    int i = 0;
    for (ItemType item : items) {
      if (item.equals(ItemType.ARROW)) {
        i++;
      }
    }
    return i;
  }

  /**
   * Renders the complete UI for the game.
   */
  private void renderComponent() {
    mazeView = new MazeView(readOnlyModel, numRows, numCols, visitedNodes);
    rootContainer.setLayout(rootLayout);
    restart = new JMenuItem("Restart");
    newGame = new JMenuItem("New Game");
    quit = new JMenuItem("Quit");
    JMenu menu = new JMenu("Options");
    menu.add(newGame);
    menu.add(restart);
    menu.add(quit);
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.add(menu);
    jMenuBar.setBackground(new Color(13, 108, 154));
    jMenuBar.setOpaque(true);
    rootContainer.add(jMenuBar, BorderLayout.NORTH);
    rootContainer.add(renderGameLayout(), BorderLayout.CENTER);
  }

  /**
   * render the layout of the game.
   * @return  game panel.
   */
  private JPanel renderGameLayout() {
    JScrollPane scrollPane = new JScrollPane(mazeView);
    scrollPane.setPreferredSize(new Dimension(700,800));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    gameLayout = new BorderLayout();
    gamePanel.setLayout(gameLayout);
    gamePanel.add(getSidePane(), BorderLayout.WEST);
    gamePanel.add(scrollPane, BorderLayout.CENTER);
    gamePanel.add(getControlPane(), BorderLayout.SOUTH);
    return gamePanel;
  }

  @Override
  public void setInfoMessage(String message) {
    infoLabel.setText(String.format("<html> %s </html>", message));
  }
}












