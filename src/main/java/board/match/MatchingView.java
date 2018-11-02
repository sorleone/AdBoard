/**
 * Package containing the matching view and its controller.
 */
package board.match;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import board.Board;
import board.Frame;
import board.management.ManagementView;
import board.objects.AdTable;

/**
 * Class defining the matching view of the application.
 *
 */
@SuppressWarnings("serial")
public class MatchingView extends JPanel implements Observer {

  private Frame frame;

  /**
   * Two-argument constructor.
   * 
   * @param board the model of the application.
   * @param frame the container frame of the application.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  public MatchingView(Board board, Frame frame) throws FileNotFoundException {
    super(new BorderLayout());
    frame.getContentPane().setPreferredSize(new Dimension(600, 600));
    frame.pack();

    this.frame = frame;

    JPanel jPanelCenter = new JPanel(new FlowLayout());
    JPanel jPanelSouth = new JPanel(new FlowLayout());
    
    AdTable adTable = new AdTable(board, false);
    adTable.match();

    jPanelCenter.add(new JScrollPane(adTable));

    JButton jButtonBack = new JButton("Back");
    jButtonBack.addActionListener(new MatchingViewController(board));
    jPanelSouth.add(jButtonBack);

    add(jPanelCenter, BorderLayout.CENTER);
    add(jPanelSouth, BorderLayout.SOUTH);
  }

  /**
   * This method is called whenever the observed object is changed.
   * An application calls an Observable object's notifyObservers method
   * to have all the object's observers notified of the change.
   * 
   * @param o the observable object.
   * @param arg an argument passed to the notifyObservers method.
   */
  @Override
  public void update(Observable o, Object arg) {
    if(Objects.equals((String)arg, "Back")) {
      Board board = (Board)o;
      try {
        ManagementView managementView = new ManagementView(board, frame);
        board.addObserver(managementView);
        frame.setBoardFrame(managementView, "Board Management View");
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(),
        "Runtime exception", JOptionPane.WARNING_MESSAGE);
      }
    }
  }
}
