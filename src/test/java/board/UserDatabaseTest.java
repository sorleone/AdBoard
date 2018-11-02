/**
 * Package containing the fundamental objects of the Board application.
 */
package board;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import board.objects.UserDatabase;
import board.objects.User;

public class UserDatabaseTest {

  final String FILE_PATH = System.getProperty("user.home") + "/Desktop" + "/UsrDatabase";
  
  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  
  @Test
  public void registerEntryTest() throws IllegalArgumentException, IOException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Username already taken");
    
    UserDatabase userDatabase = new UserDatabase(FILE_PATH);
    userDatabase.delete();
    userDatabase.createNewFile();
    userDatabase.registerEntry(new User("Lorenzo", "654984d"));
    userDatabase.registerEntry(new User("Lorenzo", "654j984d"));
  }

  @Test
  public void loginTest() throws IllegalArgumentException, IOException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Invalid username and/or password");
    
    UserDatabase userDatabase = new UserDatabase(FILE_PATH);
    userDatabase.delete();
    userDatabase.createNewFile();
    userDatabase.setUsername("asdf");
    userDatabase.setPassword("12");
    userDatabase.login();
  }

  @Test
  public void loginTest2() throws IllegalArgumentException, IOException {
    UserDatabase userDatabase = new UserDatabase(FILE_PATH);
    userDatabase.delete();
    userDatabase.createNewFile();
    userDatabase.registerEntry(new User("marco", "12"));
    userDatabase.setUsername("marco");
    userDatabase.setPassword("12");
    userDatabase.login();
  }
  
  @Test
  public void containsEntryTest() throws IllegalArgumentException, IOException {
    UserDatabase userDatabase = new UserDatabase(FILE_PATH);
    userDatabase.delete();
    userDatabase.createNewFile();
    userDatabase.registerEntry(new User("ghhs", "hjkg"));
    assertEquals(true, userDatabase.containsEntry(new User("ghhs", "hjkg")));
  }
}
