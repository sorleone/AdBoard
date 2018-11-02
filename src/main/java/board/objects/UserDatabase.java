/**
 * Package containing the fundamental objects of the Board application.
 */
package board.objects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * Class representing a database containing the users of the board 
 *
 */
@SuppressWarnings("serial")
public class UserDatabase extends Database {

  private String username, password;

  /**
   * One-argument constructor.
   * 
   * @param filePath the path of the database file.
   */
  public UserDatabase(String filePath) {
    super(filePath);
  }

  /**
   * Gets the username of the current user of this database.
   * 
   * @return the username.
   */
  public String getUsername() {
    return username;
  }
  
  /**
   * Sets the username of the current user of this database.
   * 
   * @param username the username to be set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password of the current user of this database.
   * 
   * @return the password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the current user of this database.
   * 
   * @param password the password to be set.
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Attempts the login procedure for a given user.
   * 
   * @throws IllegalArgumentException if username or password
   * contain whitespaces, are empty or null. If the username and/or password are invalid.
   * @throws FileNotFoundException If the database file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  public void login()
  throws IllegalArgumentException, FileNotFoundException {
    UserDatabase.Iterator iterator = this.new Iterator();
    while(iterator.hasNext()) {
      User user = iterator.getNext();
      boolean validUsername = Objects.equals(username, user.getUsername());
      boolean validPassword = Objects.equals(password, user.getPassword());
      if(validUsername && validPassword) return;
    }
    throw new IllegalArgumentException("Invalid username and/or password");
  }

  /**
   * Registers a new user in this database.
   * 
   * @param user user to be registered.
   * @throws IllegalArgumentException if the username is already taken.
   * If the username or password contain whitespaces, are empty or null.
   * @throws FileNotFoundException if the file does not exist, is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Override
  public void registerEntry(Object user)
  throws IllegalArgumentException, FileNotFoundException, IOException {	
    if(containsEntry(user))
      throw new IllegalArgumentException("Username already taken");
    else
      addEntry(user);
  }

  /**
   * Iterator.
   */
  public class Iterator extends Database.Iterator<User> {
    
    /**
     * Constructor.
     * 
     * @throws FileNotFoundException if the file does not exist,
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    public Iterator()
    throws FileNotFoundException {
      super();
    }

    /**
     * Gets the next User in the database.
     *
     * @throws IllegalArgumentException if username or password
     * contain whitespaces, are empty or null.
     */
    @Override
    public User getNext() 
    throws IllegalArgumentException {
      String username = getNextLine();
      String password = getNextLine();
      return new User(
        username.substring(username.indexOf(" ") + 1),
        password.substring(password.indexOf(" ") + 1)
      );
    }
  }

  /**
   * Checks if the database contains a given User.
   * 
   * @param user object that needs to be checked.
   * @return true if the user is contained.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   * @throws IllegalArgumentException if username or password contain whitespaces, are empty or null.
   */
  @Override
  public boolean containsEntry(Object user)
  throws FileNotFoundException, IllegalArgumentException {
    UserDatabase.Iterator iterator = this.new Iterator();
    while(iterator.hasNext()) {
      if(iterator.getNext().equals(user)) {
        return true;
      }
    }
    return false;
  }
}
