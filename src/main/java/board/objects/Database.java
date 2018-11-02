/**
 * Package containing the fundamental objects of the Board application.
 */
package board.objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Abstract class representing a general database for the Board application.
 */
@SuppressWarnings("serial")
public abstract class Database extends java.io.File {

  /**
   * One-argument constructor.
   * 
   * @param filePath file path of the database file.
   */
  public Database(String filePath) {
    super(filePath);
    try {
      createNewFile();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null,
      e.getMessage(), "Unable to create the database",
      JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  /**
   * Generic abstract Iterator.
   *
   * @param <T> object to be returned by getNext().
   */
  public abstract class Iterator<T> {

    private Scanner scanner;

    /**
     * Constructor.
     * 
     * @throws FileNotFoundException if the file does not exist,
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    public Iterator() throws FileNotFoundException {
      scanner = new Scanner(new BufferedReader(new FileReader(Database.this)));
    }

    /**
     * Checks if the database has another line ready to be read.
     * 
     * @return true if another line is present.
     */
    public boolean hasNext() {	
      return scanner.hasNextLine();
    }

    /**
     * Gets next line of the database.
     * 
     * @return string containing next line of the database.
     */
    public String getNextLine() {
      return scanner.nextLine();
    }

    /**
     * Disposes the Scanner object before closing.
     * 
     * @throws Throwable the Exception raised by this method.
     */
    @Override
    protected void finalize() throws Throwable {
      scanner.close();
      super.finalize();
    }

    /**
     * Gets the next object in the database.
     * 
     * @return the next object in the database.
     * @throws IllegalArgumentException if the object that's being constructed
     * is given wrong arguments.
     */
    public abstract T getNext() throws IllegalArgumentException;
  }

  /**
   * Registers a new objects in this database.
   * 
   * @param obj object to be registered.
   * @throws IllegalArgumentException if the object that's being constructed is given wrong arguments.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  public abstract void registerEntry(Object obj)
  throws IllegalArgumentException, FileNotFoundException, IOException;

  /**
   * String representation of this database.
   * 
   * @return fileString string representation of this database.
   */
  @Override
  public String toString() {
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(this));
      String fileString = new String();
      String lineTmp = fileString = bufferedReader.readLine();
      while((lineTmp = bufferedReader.readLine()) != null) {
        fileString += lineTmp;
      }
      bufferedReader.close();
      return fileString == null ? new String() : fileString;
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Checks if two Database objects are equal.
   * 
   * @param obj object to be compared with "this".
   * @return true if equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (!(obj instanceof UserDatabase)) return false;
    UserDatabase userDatabase = (UserDatabase)obj;
    return Objects.equals(this.toString(), userDatabase.toString());
  }

  /**
   * Computes the hash code of the Database object.
   * 
   * @return hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.toString());
  }

  /**
   * Checks if the database contains a given object.
   * 
   * @param obj object that needs to be checked.
   * @return true if the user is contained.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   * @throws IllegalArgumentException if the object that's being constructed is given wrong arguments.
   */
  protected abstract boolean containsEntry(Object obj)
  throws FileNotFoundException, IllegalArgumentException;

  /**
   * Appends a new object to the database.
   * 
   * @param obj object that needs to be added.
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  protected void addEntry(Object obj) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this, true));
    bufferedWriter.write(obj.toString() + "\n");
    bufferedWriter.close();
  }
}
