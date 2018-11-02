/**
 * Package containing the fundamental objects of the Board application.
 */
package board;

import static org.junit.Assert.*;
import org.junit.Test;

import board.objects.Ad.AdType;
import board.objects.Ad;

public class AdTest {

  public Ad a = new Ad("marco", AdType.BUY, "vespa", "alfa,beta", 20, 330);

  @Test
  public void equalsTest() {
    Ad b = new Ad("marco", AdType.BUY, "vespa", "alfa,beta", 20, 330);
    assertTrue(a.equals(b));
  }

  @Test
  public void getUsernameTest() {
    assertEquals("marco", a.getUsername());
  }

  @Test
  public void getDescriptionTest() {
    assertEquals("vespa", a.getDescription());
  }

  @Test
  public void getKeywordsTest() {
    assertArrayEquals(new String[] {"alfa","beta"}, a.getKeywords());
  }

  @Test
  public void getTypeTest() {
    assertEquals(AdType.BUY, a.getType());
  }

  @Test
  public void getPriceTest() {
    assertEquals(330, a.getPrice(), 0.00001);
  }

  @Test
  public void hasExpiredTest() {
    assertEquals(false, a.hasExpired());
  }

  @Test
  public void getRemainingDaysTest() {
    assertEquals(20, a.getRemainingDays());
  }
}
