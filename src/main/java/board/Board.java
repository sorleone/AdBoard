/**
 * Root package containing the executable class,
 * the container frame of the application and the model.
 */
package board;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JOptionPane;

import board.objects.Ad;
import board.objects.AdDatabase;
import board.objects.User;
import board.objects.UserDatabase;

/**
 * Wrapper class to create a unified model
 *
 */
public class Board extends Observable {
  
  private UserDatabase userDatabase;
  private AdDatabase adDatabase;

  /**
   * Path containing the user database.
   */
  public static final String USR_FILE_PATH = "../UsrDatabase";

  /**
   * Path containing the ad database.
   */
  public static final String AD_FILE_PATH = "../AdDatabase";
  
  /**
   * Constructor.
   */
  public Board() 
  throws IOException, IllegalArgumentException, FileNotFoundException {
    userDatabase = new UserDatabase(USR_FILE_PATH);
    adDatabase = new AdDatabase(AD_FILE_PATH);
  }

  /**
   * Gets the username of the current Board user.
   * 
   * @return the username.
   */
  public String getUsername() {
    return userDatabase.getUsername();
  }

  /**
   * Sets the username of the current user of the Board.
   * 
   * @param username the username.
   */
  public void setUsername(String username) {
    userDatabase.setUsername(username);
  }

  /**
   * Sets the password of the current Board user.
   * 
   * @param password the password.
   */
  public void setPassword(String password) {
    userDatabase.setPassword(password);
  }

  /**
   * Saves a new ad into the Board.
   * 
   * @param ad the ad to be saved.
   */
  public void saveAd(Ad ad) {
    try {
      adDatabase.registerEntry(ad);
    } catch (IOException |IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(),
      "Invalid ad registration", JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Removes an ad from the Board.
   * 
   * @param ad the ad to be removed.
   */
  public void removeAd(Ad ad) {
    try {
      adDatabase.removeAd(ad);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(),
      "Invalid ad removal", JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Authenticates the user that's attempting to use the Board.
   */
  public void loginUser() {
    try {
      userDatabase.login();
      signalObservers("Login");
    } catch (FileNotFoundException | IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null,
      e.getMessage(), "Invalid login",
      JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Registers the user that's attempting to use the Board.
   */
  public void registerUser() {
    try {
      userDatabase.registerEntry(new User(
        userDatabase.getUsername(),
        userDatabase.getPassword()
      ));
      signalObservers("Register");
    } catch (IllegalArgumentException | IOException e) {
      JOptionPane.showMessageDialog(null,
      e.getMessage(), "Invalid registration",
      JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Iterator for the users of the Board.
   *
   */
  public class UserIterator extends UserDatabase.Iterator {

    /**
     * Constructor of the UserIterator
     * 
     * @throws FileNotFoundException if the file does not exist,
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    public UserIterator() throws FileNotFoundException {
      userDatabase.super();
    }
  }

  /**
   * Iterator for the ads contained in the Board.
   *
   */
  public class AdIterator extends AdDatabase.Iterator {

    /**
     * Constructor of the AdIterator
     * 
     * @throws FileNotFoundException if the file does not exist,
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    public AdIterator() throws FileNotFoundException {
      adDatabase.super();
    }
  }

  /*
   * Wrapper function to notify the observers of the model in a handy way
   */
  public void signalObservers(String action) {
    setChanged();
    notifyObservers(action);
  }

  /**
   * Wrapper function to notify the observers of the model in a handy way
   */
  public void signalObservers() {
    setChanged();
    notifyObservers();
  }
}
