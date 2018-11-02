/**
 * Package containing the login view and its controller.
 */
package board.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import board.Board;

/**
 * Class defining the view controller of the LoginView class.
 *
 */
public class LoginViewController implements ActionListener {

  private Board board;
  private LoginView loginView;

  /**
   * Two-argument constructor.
   * 
   * @param board the model of the application.
   * @param loginView the login view of the application.
   */
  public LoginViewController(Board board, LoginView loginView) {
    this.board = board;
    this.loginView = loginView;
  }

  /**
   * Method that gets called every time an event triggers this listener.
   * 
   * @param actionEvent the event that triggered this listener.
   */
  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    String action = ((JButton)actionEvent.getSource()).getText();
    switch (action) {
      case "Login":
        board.setUsername(loginView.getUsername());
        board.setPassword(loginView.getPassword());
        board.loginUser();
        break;
      case "Register":
        board.setUsername(loginView.getUsername());
        board.setPassword(loginView.getPassword());
        board.registerUser();
        break;
    }
  }
}
