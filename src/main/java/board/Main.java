/**
 * Root package containing the executable class,
 * the container frame of the application and the model.
 */
package board;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Executable class of the Board application
 *
 */
public class Main {
  public static void main(String[] args)
  throws IOException, IllegalArgumentException, FileNotFoundException {
    new Frame(new Board());
  }
}
