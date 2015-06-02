import java.util.*;
import java.rmi.*;

public interface AuctionClient extends Remote {
    
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */

    
/**
 * Auction Client Interface
 *
 */
    
    public void dispatchMessage( String theMessage) throws RemoteException;
    
    public String getName() throws RemoteException;
    
    public void setName(String name) throws RemoteException;
    
}