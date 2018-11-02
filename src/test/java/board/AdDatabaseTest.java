/**
 * Package containing the fundamental objects of the Board application.
 */
package board;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import board.objects.AdDatabase;
import board.objects.Ad;

public class AdDatabaseTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();	
  
  @Test
  public void clearDatabaseTest() throws IOException, IllegalArgumentException {	
    AdDatabase adDatabase = new AdDatabase(System.getProperty("user.home") + "/Desktop" + "/AdDB");
    adDatabase.clearDatabase();
    adDatabase.registerEntry(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
    adDatabase.clearDatabase();
    adDatabase.registerEntry(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
  }

  @Test
  public void registerEntryTest() throws IOException, IllegalArgumentException {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("Ad already contained");
    
    AdDatabase adDatabase = new AdDatabase(System.getProperty("user.home") + "/Desktop" + "/AdDB");
    adDatabase.clearDatabase();
    adDatabase.registerEntry(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
    adDatabase.registerEntry(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
  }

  @Test
  public void removeAdTest() throws IOException, IllegalArgumentException {
    AdDatabase adDatabase = new AdDatabase(System.getProperty("user.home") + "/Desktop" + "/AdDB");
    adDatabase.clearDatabase();
    adDatabase.registerEntry(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
    adDatabase.removeAd(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
    adDatabase.registerEntry(new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300));
  }

  @Test
  public void containsEntryTest() throws IOException, IllegalArgumentException {
    AdDatabase adDatabase = new AdDatabase(System.getProperty("user.home") + "/Desktop" + "/AdDB");
    adDatabase.clearDatabase();
    Ad ad = new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", 60, 300);		
    adDatabase.registerEntry(ad);
    assertTrue(adDatabase.containsEntry(ad));
  }

  @Test
  public void removeExpiredAdsTest() throws IOException, IllegalArgumentException {
    AdDatabase adDatabase = new AdDatabase(System.getProperty("user.home") + "/Desktop" + "/AdDB");
    adDatabase.clearDatabase();
    Ad ad = new Ad("marco", Ad.AdType.BUY, "adssad", "asdsa,asds,fad", -10, 300);
    adDatabase.registerEntry(ad);
    AdDatabase newAdDatabase = new AdDatabase(System.getProperty("user.home") + "/Desktop" + "/AdDB");
    assertFalse(newAdDatabase.containsEntry(ad));
  }
}
