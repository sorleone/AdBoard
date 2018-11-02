/**
 * Package containing the login view and its controller.
 */
package board.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import board.Board;
import board.Frame;
import board.management.ManagementView;

/**
 * Class defining the login view of the application.
 *
 */
@SuppressWarnings("serial")
public class LoginView extends JPanel implements Observer {

  private JTextField usernameField;
  private JPasswordField passwordField;
  private Frame frame;

  /**
   * Two-argument constructor.
   * 
   * @param board the model of the application.
   * @param frame the container frame of the application.
   */
  public LoginView(Board board, Frame frame) {

    super(new BorderLayout());
    frame.getContentPane().setPreferredSize(new Dimension(420, 220));
    frame.pack();
    this.frame = frame;
    
    LoginViewController loginViewController = new LoginViewController(board, this);
    
    JPanel jPanelNorth = new JPanel(new FlowLayout());
    usernameField = new JTextField(10);
    JLabel jLabelUsername = new JLabel("Username: ");
    jPanelNorth.add(jLabelUsername);
    jPanelNorth.add(usernameField);
    add(jPanelNorth, BorderLayout.NORTH);
    
    JPanel jPanelCenter = new JPanel(new FlowLayout());
    passwordField = new JPasswordField(10);
    JLabel jLabelPassword = new JLabel("Password: ");
    jPanelCenter.add(jLabelPassword);
    jPanelCenter.add(passwordField);	
    add(jPanelCenter, BorderLayout.CENTER);
    
    JPanel jPanelSouth = new JPanel(new FlowLayout());
    JButton jButtonLogin = new JButton("Login");
    JButton jButtonRegister = new JButton("Register");
    jButtonLogin.addActionListener(loginViewController);
    jButtonRegister.addActionListener(loginViewController);
    jPanelSouth.add(jButtonLogin);
    jPanelSouth.add(jButtonRegister);
    add(jPanelSouth, BorderLayout.SOUTH);
  }

  /**
   * Gets the username used by user to login into the application.
   * 
   * @return the username.
   */
  public String getUsername() {
    return usernameField.getText();
  }

  /**
   * Gets the password used by user to login into the application.
   * 
   * @return the password.
   */
  public String getPassword() {
    return new String(passwordField.getPassword());
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
    if(Objects.equals(action, "Login") || Objects.equals(action, "Register")) {
      Board board = (Board)o;
      try {
        ManagementView managementView = new ManagementView(board, frame);
        board.addObserver(managementView);
        frame.setBoardFrame(managementView,"Board Management View");
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(),
        "Runtime exception", JOptionPane.WARNING_MESSAGE);
      }
    }
  }
}
