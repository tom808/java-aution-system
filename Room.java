
import java.util.ArrayList;

public class Room {

/**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */
    
    
    
/**
 * Room                           
 * 
 * Class for instantiating an auction room. An aution room is used to hold information
 * regarding clients and items. The room is used to encapsulte the details about what clients
 * are bidding on what items
 *
 */
  
  
  private double highBid;
  private ArrayList<AuctionClient> biddingClients;
  private AuctionClient highestBidder;
  private Item biddingItem;
  
  
  
   /**
   * Constructor for Room.
   * 
   * @param bidItem Item
   */
  
  public Room(Item bidItem) {
      
      biddingItem = bidItem;
      biddingClients = new ArrayList<AuctionClient>();
      highBid = biddingItem.getPrice();
      
  }
  
  
 /**
 * Add Bid.                           
 * 
 * Take the bid amount and client and checks to see if it is greated that the highest current bid.
 * Is successful it updates this information and relays a String to the caller.
 * 
 * @param bid double
 * @param client AuctionClient
 * @return String
 *
 */
  
  public String addBid(double bid,AuctionClient client) {
      
      
      try {
          
          
          String clientName = client.getName();
      
      if (bid > highBid) {
          highBid = bid;
          biddingItem.setPrice(highBid);
          highestBidder = client;
          return clientName + " has bid " + highBid + " for " + biddingItem.getName();
        }
      
      return clientName + " bid not high enough!";
      
      }
      
      catch (Exception ex) {
          
          return "No client name. No bid made!";
      }
  }
  
  
   /**
 * Add AuctionClient
 * 
 * @param client AuctionClient
 *
 */
  
  public void addAuctionClient(AuctionClient client) {
      
      biddingClients.add(client);
      
  }
  
  
   /**
 * Get Item
 * 
 * @return Item
 * 
 */
  
  public Item getItem() {
      
      return biddingItem;
      
  }
  
  
 /**
 * Get Bidding Clients
 * 
 * Returns all clients in the bidding room.
 * 
 * @return ArrayList<AuctionClient>
 * 
 */
  
  
  
  public ArrayList<AuctionClient> getBiddingClients() {
      
      return biddingClients;
      
  }

}