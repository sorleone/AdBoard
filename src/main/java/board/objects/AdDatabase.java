/**
 * Package containing the fundamental objects of the Board application.
 */
package board.objects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import board.objects.Ad.AdType;

/**
 * Class representing an ad database.
 */
@SuppressWarnings("serial")
public class AdDatabase extends Database {

  /**
   * One-argument constructor.
   * 
   * @param filePath the path of the database file.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  public AdDatabase(String filePath)
  throws FileNotFoundException, IOException {
    super(filePath);
    removeExpiredAds();
  }

  /**
   * Checks if the database contains a given ad.
   * 
   * @param obj object that needs to be checked.
   * @return true if contained.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  @Override
  public boolean containsEntry(Object ad)
  throws FileNotFoundException {
    AdDatabase.Iterator iterator = this.new Iterator();
    while(iterator.hasNext())
      if(iterator.getNext().equals(ad)) return true;
    return false;
  }

  /**
   * Method dedicated to the registration of a new ad
   * 
   * @param ad the ad to be registered.
   * @return true if the registration was positive.
   * @throws IllegalArgumentException if the ad is already contained in the database.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file, or for some other reason cannot be opened for reading.
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  @Override
  public void registerEntry(Object ad)
  throws IllegalArgumentException, FileNotFoundException, IOException {
    if(containsEntry(ad))
      throw new IllegalArgumentException("Ad already contained");
    else
      addEntry(ad);
  }

  /**
   * Iterator.
   */
  public class Iterator extends Database.Iterator<Ad> {

    /**
     * Constructor.
     * 
     * @throws FileNotFoundException if the file does not exist,
     * is a directory rather than a regular file,
     * or for some other reason cannot be opened for reading.
     */
    public Iterator() throws FileNotFoundException {
      super();
    }

    /**
     * Gets the next ad in the database.
     *
     * @return the next ad in the database.
     */
    @Override
    public Ad getNext() {
      String username = getNextLine();
      String type = getNextLine();
      String description = getNextLine();
      String keywords = getNextLine();
      String days = getNextLine();
      String price = getNextLine();
      return new Ad(
        username.substring(username.indexOf(" ") + 1),
        AdType.parse(type.substring(type.indexOf(" ") + 1)),
        description.substring(description.indexOf(" ") + 1),
        keywords.substring(keywords.indexOf("[") + 1, keywords.indexOf("]")),
        ChronoUnit.DAYS.between(
          LocalDateTime.now(),
          LocalDateTime.parse(days.substring(days.indexOf(" ") + 1))
        ),
        Double.parseDouble(price.substring(price.indexOf(" ") + 1))
      );
    }
  }

  /**
   * Removes an ad from the database.
   * 
   * @param adToBeRemoved the ad to be removed.
   * @return true if removed.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   */
  public boolean removeAd(Ad adToBeRemoved)
  throws FileNotFoundException, IOException {
    AdDatabase.Iterator iterator = this.new Iterator();
    ArrayList<Ad> adList = new ArrayList<>();
    while(iterator.hasNext()) {
      adList.add(iterator.getNext());
    }
    clearDatabase();
    boolean removed = adList.remove(adToBeRemoved);
    for (Ad ad : adList) {
      addEntry(ad);
    }
    return removed;
  }

  /**
   * Deletes the content of the database.
   * 
   * @throws FileNotFoundException If the given file object does not denote an existing,
   * writable regular file and a new regular file of that name cannot be created,
   * or if some other error occurs while opening or creating the file.
   */
  public void clearDatabase() throws FileNotFoundException {
    new PrintWriter(this).close();
  }

  /**
   * Removes the expired ads.
   * 
   * @throws IOException if the file exists but is a directory rather than a regular file,
   * does not exist but cannot be created, or cannot be opened for any other reason.
   * @throws FileNotFoundException if the file does not exist,
   * is a directory rather than a regular file,
   * or for some other reason cannot be opened for reading.
   */
  private void removeExpiredAds()
  throws IOException, FileNotFoundException {
    AdDatabase.Iterator iterator = this.new Iterator();
    while(iterator.hasNext()) {
      Ad ad = iterator.getNext();
      if(ad.hasExpired()) {
        removeAd(ad);
      }
    }
  }
}
