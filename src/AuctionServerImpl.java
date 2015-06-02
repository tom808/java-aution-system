import java.io.*;
import java.util.*;
import java.rmi.*;
import java.net.*;
import java.rmi.server.*;

public class AuctionServerImpl extends UnicastRemoteObject implements AuctionServer {
  
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */

  
/**
 *  Auction System Implementation.
 * 
 *  Contains all methods for accepting auction clients, registering bids and providing a list
 *  of rooms and corresponding items. Also passed all transactions and changes to a DatabaseAccessor.
 *
 */
  
  
    
  private static AuctionServerImpl auctionServerImpl;
  private ArrayList<AuctionClient> clients;
  private ArrayList<Room> rooms;
  private ArrayList<Item> items;
  private DatabaseAccessor myDatabase;
  
  public static void main(String[] args) {
  
  
  
  try {
        
 AuctionServerImpl myServer = AuctionServerImpl.getAuctionServerImpl();
        
        Naming.rebind("AS", myServer);
    
        System.out.println("Auction Server bound to registry");
  
  
      }

  catch(Exception ex) {

 System.out.println("FAILED");
 ex.printStackTrace();
        System.exit(1);
 
     }
  
   
  
  }
  
    
/**
 *  Get Auction Server Implementation.
 * 
 *  Returns the Auction Server object. If one does not exist then it is created.
 * 
 *  @return AuctionServerImpl
 */
  
  
  public static synchronized AuctionServerImpl getAuctionServerImpl() {
      if (auctionServerImpl == null) {
          
          try {
          auctionServerImpl = new AuctionServerImpl();
      }
          catch (RemoteException x) {
              System.err.println(x);
          }
        
  }
      return auctionServerImpl;
  
  }
  
  /**
 *  Clone.
 * 
 *  Overrides clone method in order to prevent any additional instances of the object being
 *  created.
 * 
 *  @throws CloneNotSupportedException
 *
 */
  
  
  
  public Object clone() throws CloneNotSupportedException {
      
      throw new CloneNotSupportedException();
      
      
  }
  
  private AuctionServerImpl() throws RemoteException {
  
    /*Private constructor for AuctionServer. Ensures that only this object can
     *call the constructor using the getAuctionServerImpl method.
     *Used to impliment Singleton pattern.
     */
    
    
  clients = new ArrayList<AuctionClient>();
  myDatabase = new DatabaseAccessorImpl();
  items = myDatabase.getItemsForSale();
  rooms = new ArrayList<Room>();
  
  for (Item theItem : items) {
      
      rooms.add(new Room(theItem));
  }

  }
  
  
    /**
 *  Register User.
 * 
 *  Uses the database interface to add a new user to the database. The server is not concerned with
 *  how it is written just that the arguments are passed to storage.
 *  
 *  @param username String Clients chosen username
 *  @param password String Clients chosen password
 *  @param email String Clients chosen email
 *  @throws RemoteException
 */
  
  
  public void registerUser(String username, String password, String email) throws RemoteException {
         
      myDatabase.insertUser(username, password, email);
      
      
  }
  
 /**
 *  Connect.
 * 
 *  Tests if a user with valid user name and password exists and then returns whether the connection
 *  is successful or not
 *  
 *  @param client AuctionClient
 *  @param passWord String
 *  @throws RemoteException
 */
  
  public boolean connect(AuctionClient client,String passWord) throws RemoteException {
      
      
     if(myDatabase.logInUser(client.getName(),passWord)) {
         clients.add(client);
         return true;
     }
     else {
         return false;
     }
     
      
  }
  
  
 /**
 *  Add Client to Item.
 * 
 *  Searches through the rooms to find the one with the respective item and then
 *  adds the client to that item. Also replays a message to the client using the
 *  dispatchMessage() method about this.
 *  
 *  @param client  AuctionClient
 *  @param itemID int
 */
  
  public void addClientToItem(AuctionClient client,int itemID) throws RemoteException{
  
    
     
      
      for (Room room : rooms) {           
                    
        if (room.getItem().getID() == itemID) {
            
            
              room.addAuctionClient(client);
              
             
              
              
              client.dispatchMessage("You are now bidding on " + room.getItem().getName()
                      + "price currently is " + room.getItem().getPrice());
              
              System.out.println(client.getName() + " has been added to room bidding on " + room.getItem().getName());
              
              break;
        }
        
        
        
        
      }
  
  }
  
  
 /**
 *  Get Items.
 * 
 *  Returns an ArrayList of all items in the system currently available for bidding on.
 * 
 *  @return ArrayList<Item>
 *  @throws RemoteException
 */
  
  public ArrayList<Item> getItems() throws RemoteException {
      
      return items;
      
  }
  
  
 /**
 *  Send Bid.
 * 
 *  Takes the client, itemID and an amount and sends the bid amount (parsed to double) to the appropriate room
 *  for the room to make the bid via the addBid() method.
 * 
 *  @param myClient AuctionClient
 *  @param itemID int
 *  @param amount String
 * 
 *  @throws RemoteException
 */
  
  public void sendBidToServer(AuctionClient myClient, int itemID, String amount) throws RemoteException {

      
      
      
      Room bidRoom = null; //get a reference to the room to be used
      
      String message = ""; //create a message to send back

       try{
   
            double amt = Double.parseDouble(amount); //check the string is valid amount

            
            //get the room based on the ID of the item
            
            for (Room room : rooms) {

          if (room.getItem().getID() == itemID) {

              bidRoom = room;
              break;
              
          }
            }
          
              message = bidRoom.addBid(amt,myClient); //make the bid
              myDatabase.recordTransaction(bidRoom.getItem().getID(),message);
              
              for (AuctionClient theClients : bidRoom.getBiddingClients()) {

                  try {
                  
                  theClients.dispatchMessage(message);

                  }
                  
                  catch (Exception ex) {
                      
                      System.out.println("Client unreachable");
                      
                  }
              }
              
                
            
            
            
            System.out.println(message);
            
            }
       
       
        catch (NumberFormatException ex) {
              
            
            try {
                myClient.dispatchMessage("Not a valid amount try again");
            }
            
            catch(Exception x) {
                
                System.out.print("Client unreachable");
                
            }
            
       }    
      
  
   }

}
  
