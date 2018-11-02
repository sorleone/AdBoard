/**
 * Root package containing the executable class,
 * the container frame of the application and the model.
 */
package board;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;

import board.login.LoginView;

/**
 * Class defining the container frame of the application.
 *
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {

  private Container container;

  /**
   * One-argument of the constructor.
   * 
   * @param board the model of the application.
   */
  public Frame(Board board) {
    this.container = getContentPane();
    LoginView loginView = new LoginView(board, this);
    board.addObserver(loginView);
    setBoardFrame(loginView, "Board Login View");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Switches the content of the Frame.
   * 
   * @param comp component to be inserted in the Frame.
   * @param title title of the new view.
   */
  public void setBoardFrame(Component comp, String title) {
    this.getContentPane().removeAll();
    container.setLayout(new BorderLayout());
    container.add(comp, BorderLayout.CENTER);
    this.setTitle(title);
    this.setVisible(true);
  }
}
