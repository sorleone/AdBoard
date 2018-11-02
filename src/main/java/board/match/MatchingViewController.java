/**
 * Package containing the matching view and its controller.
 */
package board.match;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import board.Board;

/**
 * Class defining the view controller of the MatchingView class.
 *
 */
public class MatchingViewController implements ActionListener {

  private Board board;

  /**
   * One-argument constructor.
   * 
   * @param board the model of the application.
   */
  public MatchingViewController(Board board) {
    this.board = board;
  }

  /**
   * Method that gets called every time an event triggers this listener.
   * 
   * @param actionEvent the event that triggered this listener.
   */
  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    board.signalObservers("Back");
  }
}
