/**
 * Package containing the management view and its controller.
 */
package board.management;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import board.Board;
import board.Frame;
import board.match.MatchingView;
import board.objects.AdTable;

/**
 * Class defining the management view of the application.
 *
 */
@SuppressWarnings("serial")
public class ManagementView extends JPanel implements Observer {

  private Frame frame;
  private AdTable adTable;

  /**
   * Two-argument constructor.
   * 
   * @param board the model of the application.
   * @param frame the container frame of the application.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  public ManagementView(Board board, Frame frame) throws FileNotFoundException {
    super(new BorderLayout());
    frame.getContentPane().setPreferredSize(((new Dimension(600, 600))));
    frame.pack();

    this.frame = frame;

    adTable = new AdTable(board, true);
    JPanel jPanelNorth = new JPanel(new FlowLayout());
    JPanel jPanelCenter = new JPanel(new FlowLayout());
    JPanel jPanelSouth = new JPanel(new FlowLayout());

    jPanelCenter.add(new JScrollPane(adTable));

    ManagementViewController mngViewController = new ManagementViewController(
      board, adTable
    );

    JButton jButtonAdd = new JButton("Add");
    jButtonAdd.addActionListener(mngViewController);
    jPanelNorth.add(jButtonAdd);

    JButton jButtonSave = new JButton("Save");
    jButtonSave.addActionListener(mngViewController);
    jPanelNorth.add(jButtonSave);

    JButton jButtonRemove = new JButton("Remove");
    jButtonRemove.addActionListener(mngViewController);
    jPanelNorth.add(jButtonRemove);

    JButton jButtonGetAdMatches = new JButton("Match your ads");
    jButtonGetAdMatches.addActionListener(mngViewController);
    jPanelSouth.add(jButtonGetAdMatches);

    add(jPanelNorth, BorderLayout.NORTH);
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
    String action = (String)arg;
    switch (action) {
      case "Add":
        adTable.addAd();
        break;
      case "Remove":
        adTable.removeSelectedAd();
        break;
      case "Match your ads":
        Board board = (Board)o;
        try {
          MatchingView matchView = new MatchingView(board, frame);
          board.addObserver(matchView);
          frame.setBoardFrame(matchView, "Board Matching View");
        } catch (FileNotFoundException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(),
          "Runtime exception", JOptionPane.WARNING_MESSAGE);
        }
        break;
    }
  }
}
