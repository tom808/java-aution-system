import java.io.*;
import java.util.*;
import java.sql.*;
import java.rmi.*;
import java.net.*;
import java.rmi.server.*;

public interface AuctionServer extends Remote {
  
  
      
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */
    
    
    
/**
 * Auction Server Interface
 *
 */
    
  public void addClientToItem(AuctionClient client,int itemId) throws RemoteException;
  
  public boolean connect(AuctionClient client,String passWord) throws RemoteException;
  
  public void registerUser(String username, String password, String email) throws RemoteException;
  
  public ArrayList<Item> getItems() throws RemoteException;
  
  public void sendBidToServer(AuctionClient myClient, int itemID, String amount) throws RemoteException;
}
