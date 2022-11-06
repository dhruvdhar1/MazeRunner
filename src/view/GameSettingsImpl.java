package view;

import static view.ViewConstants.CHOICES;

import controller.DungeonControllerV2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class represents the view for a form to accept all the user-defined
 * parameters of the game like rows, columns, interconnectivity etc.
 */
public class GameSettingsImpl extends JFrame implements GameSettings {
  private final JButton quit;
  private final JButton start;
  private int numRows;
  private int numCol;
  private int interconnectivity;
  private double treasurePercent;
  private int numMonster;
  private int numPit;
  private int numThief;
  private boolean isWrapping;

  /**
   * This constructor initiates the user interface for the settings view.
   */
  public GameSettingsImpl() {
    this.numCol = 0;
    this.numRows = 0;
    this.interconnectivity = 0;
    this.treasurePercent = 0;
    this.numMonster = 0;
    this.numPit = 0;
    this.numThief = 0;
    this.isWrapping = true;
    quit = new JButton("Quit Game");
    start = new JButton("Start Game");
    setLayout(new BorderLayout());
    setSize(500, 400);
    add(getHeader(), BorderLayout.NORTH);
    add(getForm(), BorderLayout.CENTER);
    add(getFooter(), BorderLayout.SOUTH);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void addClickListeners(DungeonControllerV2 listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Null Controller passed!");
    }
    quit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        listener.quitGame();
      }
    });

    start.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String errorMsg = validateAndGetErrorMsg();
        if (!errorMsg.equals("")) {
          showErrorDialogue(errorMsg);
        } else {
          listener.startGame(numRows, numCol, interconnectivity,
                  numMonster, isWrapping, treasurePercent, numPit, numThief);
        }
      }
    });
  }

  /**
   * Get the settings form containing input fields for numRows,
   * numCol, interconnectivity, treasurePercent, numMonster etc.
   *
   * @return settings form panel.
   */
  private JPanel getForm() {
    JPanel settingsPanel = new JPanel();
    settingsPanel.setLayout(new GridLayout(8, 2));
    settingsPanel.setBorder(new LineBorder(Color.BLACK));
    JTextField numRows = new JTextField();
    attachNumRowHandler(numRows);
    JTextField numCol = new JTextField();
    attachNumColHandler(numCol);
    JTextField numInter = new JTextField();
    attachInterconnectivityHandler(numInter);
    JTextField treasurePercent = new JTextField();
    attachTreasurePercentHandler(treasurePercent);
    JTextField numMonster = new JTextField();
    addNumMonsterHandler(numMonster);

    JTextField numPitField = new JTextField();
    addNumPitHandler(numPitField);
    JTextField numThiefField = new JTextField();
    addNumThiefHandler(numThiefField);

    JComboBox isWrappingField = new JComboBox(CHOICES);
    isWrappingField.addActionListener(e ->
            setIsWrapping(isWrappingField.getSelectedItem().toString()));
    JComponent[] componentList = {
      new JLabel("  Number of Rows *"), numRows,
      new JLabel("  Number of Columns *"), numCol,
      new JLabel("  Treasure Percent"), treasurePercent,
      new JLabel("  Interconnectivity"), numInter,
      new JLabel("  Number of Monsters *"), numMonster,
      new JLabel("  Number of Pits "), numPitField,
      new JLabel("  Number of Thief "), numThiefField,
      new JLabel("  Wrapping Dungeon *"), isWrappingField
    };

    for (int i = 0; i < componentList.length; i += 2) {
      settingsPanel.add(componentList[i]);
      settingsPanel.add(componentList[i + 1]);
    }
    return settingsPanel;
  }

  private void setNumRows(int numRows) {
    this.numRows = numRows;
  }

  private void setNumCol(int numCol) {
    this.numCol = numCol;
  }

  private void setInterconnectivity(int interconnectivity) {
    this.interconnectivity = interconnectivity;
  }

  private void setTreasurePercent(Double treasurePercent) {
    this.treasurePercent = treasurePercent;
  }

  private void setNumMonster(int numMonster) {
    this.numMonster = numMonster;
  }

  private void setNumPit(int numPit) {
    this.numPit = numPit;
  }

  private void setNumThief(int numThief) {
    this.numThief = numThief;
  }

  private void setIsWrapping(String isWrapping) {
    this.isWrapping = isWrapping.equals("Yes");
  }

  private void attachNumRowHandler(JTextField numRows) {
    numRows.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setNumRows(Integer.parseInt(numRows.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setNumRows(Integer.parseInt(numRows.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setNumRows(Integer.parseInt(numRows.getText()));
      }
    });
  }

  private void attachNumColHandler(JTextField numCol) {
    numCol.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setNumCol(Integer.parseInt(numCol.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setNumCol(Integer.parseInt(numCol.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setNumCol(Integer.parseInt(numCol.getText()));
      }
    });
  }

  private void attachInterconnectivityHandler(JTextField interconnectivity) {
    interconnectivity.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setInterconnectivity(Integer.parseInt(interconnectivity.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setInterconnectivity(Integer.parseInt(interconnectivity.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setInterconnectivity(Integer.parseInt(interconnectivity.getText()));
      }
    });
  }

  private void attachTreasurePercentHandler(JTextField treasurePercent) {
    treasurePercent.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setTreasurePercent(Double.parseDouble(treasurePercent.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setTreasurePercent(Double.parseDouble(treasurePercent.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setTreasurePercent(Double.parseDouble(treasurePercent.getText()));
      }
    });
  }

  private void addNumMonsterHandler(JTextField numMonsterField) {
    numMonsterField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setNumMonster(Integer.parseInt(numMonsterField.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setNumMonster(Integer.parseInt(numMonsterField.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setNumMonster(Integer.parseInt(numMonsterField.getText()));
      }
    });
  }

  private void addNumPitHandler(JTextField numPitField) {
    numPitField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setNumPit(Integer.parseInt(numPitField.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setNumPit(Integer.parseInt(numPitField.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setNumPit(Integer.parseInt(numPitField.getText()));
      }
    });
  }

  private void addNumThiefHandler(JTextField numThiefField) {
    numThiefField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        setNumThief(Integer.parseInt(numThiefField.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        setNumThief(Integer.parseInt(numThiefField.getText()));
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        setNumThief(Integer.parseInt(numThiefField.getText()));
      }
    });
  }

  /**
   * get the header of the settings UI.
   *
   * @return settings UI header panel.
   */
  private JPanel getHeader() {
    JPanel header = new JPanel();
    Font headerFont = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 20);
    JLabel headingLabel = new JLabel("Game Settings");
    headingLabel.setFont(headerFont);
    headingLabel.setForeground(Color.WHITE);
    header.add(headingLabel);
    header.setBackground(new Color(13, 108, 154));
    return header;
  }

  /**
   * get the footer of the settings UI.
   *
   * @return settings UI footer panel.
   */
  private JPanel getFooter() {
    JPanel footer = new JPanel();
    footer.setLayout(new FlowLayout());
    footer.add("quit", quit);
    footer.add("start", start);
    return footer;
  }

  /**
   * render error dialogue for invalid input.
   */
  private void showErrorDialogue(String errorMsg) {
    JOptionPane.showMessageDialog(null,
            errorMsg,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Helper method that does input validation and returns appropriate error
   * message if any error.
   *
   * @return error message.
   */
  private String validateAndGetErrorMsg() {
    if (numRows < 5 || numRows > 100) {
      return "number of rows should be between 5 and 100";
    } else if (numCol < 5 || numCol > 100) {
      return "number of column should be between 5 and 100";
    } else if (interconnectivity < 0) {
      return "Interconnectivity cannot be negative";
    } else if (numMonster < 1) {
      return "Number of monsters cannot be less than 1";
    } else if (treasurePercent < 0 || treasurePercent > 100) {
      return "Invalid treasure percentage value provided.";
    } else if (numPit < 0) {
      return "Number of Pits cannot be less than 0";
    } else if (numThief < 0) {
      return "Number of thief cannot be less than 0";
    }
    return "";
  }

  @Override
  public void makeVisible(boolean visibility) {
    setVisible(visibility);
  }

  @Override
  public int getNumRows() {
    return numRows;
  }

  @Override
  public int getNumCol() {
    return numCol;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public double getTreasurePercent() {
    return treasurePercent;
  }

  @Override
  public int getNumMonster() {
    return numMonster;
  }

  @Override
  public boolean isWrapping() {
    return isWrapping;
  }

  @Override
  public int getNumPit() {
    return numPit;
  }

  @Override
  public int getNumThief() {
    return numThief;
  }
}
