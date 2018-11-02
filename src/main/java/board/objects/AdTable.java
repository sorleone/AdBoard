/**
 * Package containing the fundamental objects of the Board application.
 */
package board.objects;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import board.Board;
import board.objects.Ad.AdType;

/**
 * Class representing an ad table.
 */
@SuppressWarnings("serial")
public class AdTable extends JTable {

  private String username;
  private int tableSize = 0;
  private Board board;
  private int selectedRowIdx = -1;
  private DefaultTableModel defaultTableModel;
  private static final Object[] tableHeaders = {
    "Ad Type", "Description", "Keywords", "Duration (d)", "Price ($)"
  };

  /**
   * Two-argument constructor.
   * 
   * @param board the model of the application.
   * @param getDataFromAdDatabase flag allowing to choose to load
   * the table from the AdDatabase of the board or not.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  public AdTable(Board board, boolean getDataFromAdDatabase)
  throws FileNotFoundException {

    this.username = board.getUsername();
    this.board = board;

    if(getDataFromAdDatabase)
      setModel(new DefaultTableModel(getDataFromBoardAdDatabase(), tableHeaders));
    else
      setModel(new DefaultTableModel(null, tableHeaders));

    defaultTableModel = (DefaultTableModel)this.getModel();
    tableSize = defaultTableModel.getDataVector().size();
    getTableHeader().setReorderingAllowed(false);

    setRowHeight(25);
    setFillsViewportHeight(true);

    TableColumn typeColumn = this.getColumnModel().getColumn(0);
    JComboBox<String> comboBox = new JComboBox<String>();
    comboBox.addItem("BUY");
    comboBox.addItem("SELL");
    typeColumn.setCellEditor(new DefaultCellEditor(comboBox));

    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setToolTipText("Click for combo box");
    typeColumn.setCellRenderer(renderer);      

    this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
          selectedRowIdx = getSelectedRow();
        }
      }
    });
  }

  /**
   * Removes the Ad selected by the mouse from the table.
   */
  public void removeSelectedAd() {
    if(selectedRowIdx != -1 && tableSize > 0) {
      tableSize--;
      defaultTableModel.removeRow(selectedRowIdx);
      defaultTableModel.fireTableDataChanged();
    }
  }

  /**
   * Adds an empty Ad to the table.
   */
  public void addAd() {
    tableSize++;
    defaultTableModel.addRow(new Object[]{"", "", "", "", ""});
    defaultTableModel.fireTableDataChanged();
  }

  /**
   * Gets the ad selected ad by the mouse and makes sure that every field has the right data. 
   * 
   * @throws IllegalArgumentException if the \"Duration\" and \"Price\" fields
   * are not a positive numbers. If there are empty fields in the ad.
   * If there is no ad selected by the mouse.
   */
  public Ad getSelectedAd()
  throws IllegalArgumentException {
    if(selectedRowIdx != -1) {
      Object[][] tableData = getDataFromTable();
      boolean emptyField = !Arrays.stream(tableData[selectedRowIdx]).anyMatch(
        obj -> Objects.toString(obj).length() == 0
      );
      if(emptyField) {
        String duration = Objects.toString(tableData[selectedRowIdx][3]);
        String price = Objects.toString(tableData[selectedRowIdx][4]);
        if(duration.matches("\\d+") && price.matches("^(\\d+)?(\\.)?(\\d+)?$")) {
          return new Ad(
            username,
            AdType.parse(Objects.toString(tableData[selectedRowIdx][0])),
            Objects.toString(tableData[selectedRowIdx][1]),
            Objects.toString(tableData[selectedRowIdx][2]),
            Long.parseLong(duration), Double.parseDouble(price)
          );
        } else {
          throw new IllegalArgumentException("The \"Duration\" and \"Price\" fields must be positive numbers");
        }
      } else {
        throw new IllegalArgumentException("Every field must be filled");
      }
    } else {
      throw new IllegalArgumentException("No ad selected");
    }
  }

  /**
   * Automatically discovers if there are any matches between
   * BUY and SELL ads of various users through their keywords.
   * 
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  public void match() throws FileNotFoundException {
    Board.AdIterator iterator = board.new AdIterator();
    ArrayList<Ad> currentUsrAds = new ArrayList<>();
    ArrayList<Ad> otherUsrAds = new ArrayList<>();
    ArrayList<Ad> matches = new ArrayList<>();
    while(iterator.hasNext()) {
      Ad ad = iterator.getNext();
      if(Objects.equals(ad.getUsername(), username))
        currentUsrAds.add(ad);
      else
        otherUsrAds.add(ad);
    }
    for (Ad currentUsrAd : currentUsrAds) {
      for (Ad otherUsrAd : otherUsrAds) {
        boolean oppositeAdTypes = !Objects.equals(
          currentUsrAd.getType(), otherUsrAd.getType()
        );
        boolean matchingAdKeywords = Objects.deepEquals(
          currentUsrAd.getKeywords(), otherUsrAd.getKeywords()
        );

        if(oppositeAdTypes && matchingAdKeywords && !matches.contains(otherUsrAd)) {
          matches.add(otherUsrAd);
          tableSize++;
          String string = Arrays.toString(otherUsrAd.getKeywords());
          addAd(
            Objects.toString(otherUsrAd.getType()),
            Objects.toString(otherUsrAd.getDescription()),
            string.substring(string.indexOf("[") + 1, string.indexOf("]")),
            Objects.toString(otherUsrAd.getRemainingDays()),
            Objects.toString(otherUsrAd.getPrice())
          );
        }
      }
    }
  }

  private Object[][] getDataFromBoardAdDatabase() throws FileNotFoundException {
    ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
    Board.AdIterator iterator = board.new AdIterator();
    while(iterator.hasNext()) {
      Ad ad = iterator.getNext();
      if(Objects.equals(ad.getUsername(), username)) {
        String string = Arrays.toString(ad.getKeywords());
        arrayList.add(new Object[]{
          ad.getType(), ad.getDescription(),
          string.substring(string.indexOf("[") + 1, string.indexOf("]")),
          ad.getRemainingDays(), ad.getPrice()
        });
      }
    }
    Object[][] tableData = new Object[arrayList.size()][];
    for (int i = 0; i < arrayList.size(); i++) {
      tableData[i] =  arrayList.get(i);
    }
    return tableData;
  }

  private void addAd(
    String type, String description, String keywords, String days, String price
  ) {
    tableSize++;
    defaultTableModel.addRow(new Object[]{
      type, description, keywords, days, price
    });
  }

  private Object[][] getDataFromTable() {
    DefaultTableModel dtm = (DefaultTableModel)this.getModel();
    int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
    Object[][] tableData = new Object[nRow][nCol];
    for (int i = 0 ; i < nRow ; i++) {
      for (int j = 0 ; j < nCol ; j++) {
        tableData[i][j] = dtm.getValueAt(i,j);
      }
    }
    return tableData;
  }
}
