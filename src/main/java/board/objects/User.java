/**
 * Package containing the fundamental objects of the Board application.
 */
package board.objects;

import java.util.Objects;

/**
 * Class representing a user of the board application.
 *
 */
public class User {

  private String username, password;

  /**
   * Two-argument constructor.
   * 
   * @param username the username of this User object.
   * @param password password of this User object.
   * @throws IllegalArgumentException if username or password contain whitespaces, are empty or null.
   */
  public User(String username, String password) throws IllegalArgumentException {

    boolean validCredentials =
      username == null ||
      password == null ||
      username.isEmpty() ||
      password.isEmpty() ||
      containsPattern("\\s", username) ||
      containsPattern("\\s", password);

    if(validCredentials) {
      throw new IllegalArgumentException("Username and password can not contain whitespaces");
    } else {
      this.username = username;
      this.password = password;
    }
  }

  /**
   * Gets the username of this User.
   * 
   * @return the username this User.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the password of this User.
   * 
   * @return the password of this User.
   */
  public String getPassword() {
    return password;
  }

  /**
   * String representation of this User.
   * 
   * @return a representation of this User.
   */
  @Override
  public String toString() {
    return "Username: " + username + "\nPassword: " + password;
  }

  /**
   * Checks if two User objects are equal.
   * 
   * @param obj Object to be checked for equality.
   * @return true if equal.
   */
  @Override
  public boolean equals(Object obj) {		
    if(obj == this) return true;
    if(!(obj instanceof User)) return false;		
    User user = (User)obj;
    return Objects.equals(this.username, user.username);
  }
  
  /**
   * Compute the hash value this User.
   * 
   * @return hash code of this User.
   */
  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  private static boolean containsPattern(String pattern, String string) {
    return java.util.regex.Pattern.compile(pattern).matcher(string).find();
  }
}
