import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable{
 
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */
    
    
    
/**
 * item                           
 * 
 * Class for instantiating a basic auction item. Contains details on ID, name and price.
 *
 */
  
  private int itemID;  
  private String name;
  private double price;
  
 /**
   * Constructor for Item.
   * 
   * @param itemID int
   * @param name String
   * @param price double
   */
  
  
  
  public Item(int itemID, String name,double price){
    this.itemID = itemID;
    this.name = name;
    this.price = price;
  
  }
  
  
    /**
 * Get Name
 * 
 * @return String
 *
 */
  
  public String getName(){

  return name;

  }
  
/**
 * Set Name
 * 
 * @param name String
 */
  
  
  public void setName(String name) {
      
      this.name = name;
  }
  
  
/**
 * Get ID
 * 
 * @return int
 *
 */
  
  public int getID() {
      
      return itemID;
      
  }
  
  /**
 * Set ID
 * 
 * @param iD int
 */
  
  public void setID(int iD) {
      
      this.itemID = iD;
      
  }
  
  /**
 * Set Price
 * 
 * @param price double
 */
  
  public void setPrice(double price) {
      
      this.price = price;
      
  }
  
/**
 * Get Price
 * 
 * @return double
 *
 */
   
  
  public double getPrice() {
      
      return price;
  }
  
  
/**
 * ToString
 * 
 * Overrides Object.toString()
 * 
 * @return String
 *
 */
  
  public String toString() {
      
      return name;
      
  }
  
  
}