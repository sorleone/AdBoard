/**
 * Package containing the fundamental objects of the Board application.
 */
package board;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import board.objects.User;

public class UserTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  
  @Test
  public void newUserTest() throws IllegalArgumentException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Username and password can not contain whitespaces");
    new User("marc o", "1234 56");
  }

  @Test
  public void equalsTest() throws IllegalArgumentException {
    User user = new User("marco", "123456");
    User user2 = new User("marco", "123456");
    assertTrue(user.equals(user2));
  }

  @Test
  public void getUsernameTest() throws IllegalArgumentException {
    User user = new User("marco", "123456");
    assertEquals("marco",user.getUsername());
  }

  @Test
  public void getPasswordTest() throws IllegalArgumentException {
    User user = new User("marco", "123456");
    assertEquals("123456",user.getPassword());
  }
}
