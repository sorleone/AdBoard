/**
 * Package containing the management view and its controller.
 */
package board.management;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import board.Board;
import board.objects.AdTable;

/**
 * Class defining the view controller of the ManagementView class.
 *
 */
public class ManagementViewController implements ActionListener {

  private Board board;
  private AdTable adTable;

  /**
   * Two-argument constructor.
   * 
   * @param board the model of the application.
   * @param adTable the AdTable used in the ManagementView.
   */
  public ManagementViewController(Board board, AdTable adTable) {
    this.board = board;
    this.adTable = adTable;
  }

  /**
   * Method that gets called every time an event triggers this listener.
   *
   * @param actionEvent the event that triggered this listener.
   */
  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String action = ((JButton)actionEvent.getSource()).getText();
    switch(action) {
      case "Add":
        board.signalObservers("Add");
        break;
      case "Save":
        try {
          board.saveAd(adTable.getSelectedAd());
        } catch (IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(),
          "Invalid ad", JOptionPane.WARNING_MESSAGE);
        }
        break;
      case "Remove":
        try {
          board.removeAd(adTable.getSelectedAd());
          board.signalObservers("Remove");
        } catch (IllegalArgumentException e) {
          if(Objects.equals(e.getMessage(), "No ad selected")) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
            "Invalid ad", JOptionPane.WARNING_MESSAGE);
          } else board.signalObservers("Remove");
        }
        break;
      case "Match your ads":
        board.signalObservers("Match your ads");
        break;
    }
  }
}
