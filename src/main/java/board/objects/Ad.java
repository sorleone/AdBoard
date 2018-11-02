/**
 * Package containing the fundamental objects of the Board application.
 */
package board.objects;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class representing an ad. 
 *
 */
public class Ad {

  /**
   * Enumerator designed to represent the type of an ad.
   */
  public enum AdType {
    BUY, SELL;
    
    /**
     * Method that allows to parse an AdType from a given String.
     * 
     * @param string String to be parsed into an AdType.
     * @return the AdType enum parsed from the input String.
     */
    public static AdType parse(String string)  {
      if(Objects.equals(string, "BUY"))
        return AdType.BUY;
      else 
        return AdType.SELL;
    }
  };

  private AdType type;
  private String username, description;
  private String[] keywords;
  private LocalDateTime expirationDate;
  private double price;

  /**
   * Six-argument constructor.
   * 
   * @param username owner of the Ad.
   * @param type type of the Ad.
   * @param description description of the Ad.
   * @param keywords Keywords of the Ad.
   * @param days number of days that the Ad is expected to last. 
   * @param price price of the object that the Ad is about.
   */
  public Ad(
    String username,
    AdType type,
    String description,
    String keywords,
    long days,
    double price
  ) {
    this.username = username;
    this.type = type;
    this.description = description;
    this.keywords = keywords.trim().split("\\s*,\\s*");
    this.expirationDate = LocalDateTime.now().plusDays(days + 1);
    this.price = price;
  }

  /**
   * Gets the username of the owner of this ad.
   * 
   * @return the username of the owner of this ad.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the type of this ad.
   * 
   * @return the type of this ad.
   */
  public AdType getType() {
    return type;
  }

  /**
   * Gets the description of this ad.
   * 
   * @return the description of this ad.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the keywords of this ad.
   * 
   * @return the keywords of this ad.
   */
  public String[] getKeywords() {
    return keywords;
  }

  /**
   * Gets the price of this ad.
   * 
   * @return the price of the object contained in this ad.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Checks if this ad has expired.
   * 
   * @return true if expired.
   */
  public boolean hasExpired() {
    return this.expirationDate.compareTo(LocalDateTime.now()) < 0 ? true : false;
  }

  /**
   * Gets the remaining active days of this ad.
   * 
   * @return the remaining days.
   */
  public long getRemainingDays() {
    return ChronoUnit.DAYS.between(LocalDateTime.now(), this.expirationDate);
  }

  /**
   * Gives a string representation of this ad.
   * 
   * @return the string representation of this ad.
   */
  @Override
  public String toString() {
    return "Owner: " + username +
           "\nType: " + type +
           "\nDescription: " + description +
           "\nKeywords: " + Arrays.toString(keywords) +
           "\nExpiration: " + expirationDate +
           "\nPrice: " + price;
  }

  /**
   * Checks for equality two ads.
   * 
   * @param obj the other ad to be checked for equality.
   * @return true if equals.
   */
  @Override
  public boolean equals(Object obj) {
    if(obj == this) return true;
    if(!(obj instanceof Ad)) return false;
    Ad ad = (Ad)obj;
    return Objects.equals(this.username, ad.username) &&
         Objects.equals(this.type, ad.type) && 
         Objects.equals(this.description, ad.description) &&
         Arrays.deepEquals(this.keywords, ad.keywords) &&
         Objects.equals(this.price, ad.price);
  }

  /**
   * Computes the hash code of this ad.
   * 
   * @return the hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(username, type, description, keywords, price);
  }
}
