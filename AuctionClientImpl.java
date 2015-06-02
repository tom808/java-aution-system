import java.util.*;
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import javax.swing.*;


public class AuctionClientImpl extends UnicastRemoteObject implements AuctionClient {

    
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */
    
    
    
/**
 * Client Implementation for Auction System.                            
 * 
 * Contains all the methods associated with the auction client.
 * 
 *
 */
        
        private String name; //Client's unique name
        private JTextArea theSink; //Displays messages from the server.
        
        
 
        /**
         * Set Client Name.
         * 
         * @param name String
         * 
         * @throws RemoteException
         * 
         */
        
        public void setName(String name) throws RemoteException{
            
            this.name = name;
            
        }
        
        
        /**
         * @throws RemoteException
         * @return String client name
         * 
         */
        
        public String getName() throws RemoteException{
            
            return name;
            
            
        }
    
  

 /**
         * Constructor for AuctionCLientImpl.
         * 
         * 
         * 
         * @param theSuppliedSink JTextArea
         * @throws RemoteException
         * 
         */
        
        public AuctionClientImpl(JTextArea theSuppliedSink) throws RemoteException {

  // simply store the reference
  theSink = theSuppliedSink;

 }
        
 /*
  * @param theSuppliedSink JTextArea
  * @param name String
  * @throws RemoteException
  * 
  */

        
 public AuctionClientImpl(JTextArea theSuppliedSink, String name) throws RemoteException {

  // simply store the reference
  theSink = theSuppliedSink;
                this.name = name;

 }
        
        
        /**
         * 
         * @param theMessage String 
         * @throws RemoteException
         * 
         */

 public void dispatchMessage(String theMessage) throws RemoteException {

                
  theSink.append("\n" + theMessage);

 }
        

}