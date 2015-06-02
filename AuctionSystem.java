import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.naming.*;
import java.rmi.*;
import java.util.Random;

/**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */

/**
 * GUI for the client AuctionSystem.                            
 * <p>
 * Provides a client facing interface for the auction system.
 * Allows the client to register a new account, log in and once
 * logged in select and item to bid on.
 * 
 * Generates a new AuctionClient class and a reference to the
 * AuctionServer using RMI.
 *
 */



/**
 * AuctionSystem Constructor.
 * 
 * Provides the interface with which a client is able to
 * register and login with.
 *
 */

public class AuctionSystem extends JFrame {

 private Panel topPanel,bottomPanel;
 private AuctionServer auctionServer;    
 private AuctionClient myClient;
    private Container container;
  private JTextArea receivedList;
    private JComboBox itemList;
    private JButton sendButton,connectButton,registerButton,enterAuction;
    private JTextField messageText,userNameText,passWordText,emailText,regUserText,regPassText;
    private ArrayList<Item> auctionItems;    
 private Item currentItem;
    private JLabel userNameLabel,passWordLabel,emailLabel,regUserNameLabel,regPassWordLabel;
        
        
 public AuctionSystem() {
               
                
                setTitle("Auction System");
                userNameLabel = new JLabel("Enter New User Name:");
                passWordLabel = new JLabel("Enter New Password:");
                regUserNameLabel = new JLabel("User Name:");
                regPassWordLabel = new JLabel("Password");
                emailLabel = new JLabel("Enter New email:");
                userNameText = new JTextField(20);
                passWordText = new JTextField(20);
                emailText = new JTextField(20);
                regUserText = new JTextField(20);
                regPassText = new JTextField(20);
                registerButton = new JButton("Register");
                connectButton = new JButton("Login");

                container = this.getContentPane();
                container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
                
                topPanel = new Panel();
                bottomPanel = new Panel();
                
                topPanel.add(userNameLabel);
                topPanel.add(userNameText);
                topPanel.add(passWordLabel);
                topPanel.add(passWordText);
                topPanel.add(emailLabel);
                topPanel.add(emailText);
                topPanel.add(registerButton);
            
                bottomPanel.add(regUserNameLabel);
                bottomPanel.add(regUserText);
                bottomPanel.add(regPassWordLabel);
                bottomPanel.add(regPassText);
                
                bottomPanel.add(connectButton);
                container.add(topPanel,BorderLayout.NORTH);
                container.add(bottomPanel,BorderLayout.CENTER);
                registerButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
      register(userNameText.getText(),passWordText.getText(),emailText.getText());
      userNameText.setText("");
      passWordText.setText("");
      emailText.setText("");
                    }});
                
                connectButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                     if (!regUserText.getText().isEmpty()){
                      connectToServer(regUserText.getText(),regPassText.getText());
                            }
                   }});
                
                
                this.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
      setVisible(false);
         dispose();
         System.exit(0);
      }});
                
                
                receivedList = new JTextArea(20,30);
                receivedList.setEditable(false);
                
                pack();
        
 }
        
        
 /**
 *                            
 * Method to display the bidding interface once a successful
 * login attempt has been made
 * 
 */
  

 public void displayAuctionInterface() {
                
        container.remove(bottomPanel);  
        container.remove(topPanel);
          
        messageText = new JTextField(10);

   sendButton = new JButton("Send");
   enterAuction = new JButton("Start Bid");
        sendButton.setEnabled(false);
   connectButton = new JButton("Connect...");
        
        bottomPanel = new Panel();
   bottomPanel.add(new JLabel("Enter Bid Amount:"));
        bottomPanel.add(messageText);
   bottomPanel.add(sendButton);
   bottomPanel.add(enterAuction);

        container.add(bottomPanel, BorderLayout.SOUTH);
        
   sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    sendBid(messageText.getText());
     }});
     
    enterAuction.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
        enterAuction(auctionItems.get(itemList.getSelectedIndex()));
                }});
                
        pack();

 } 
    
 /**
 *                            
 * Takes the input provided from the registration text boxes and
 * sends this information to the auction server.
 * 
 * If successful the client becomes registered on the server
 * database and can then proceed to login.
 *
 * 
 * @param username String username for client registering
 * @param pass String password for client registering
 * @param email String email address for client registering 
 * 
 */
        
    
    public void register(String username, String pass, String email){
                
  String bindName = "AS";

    try{
       System.out.println("Looking up " + bindName);
       auctionServer = (AuctionServer)Naming.lookup(bindName);
       myClient = new AuctionClientImpl(receivedList);

             auctionServer.registerUser(username,pass,email);
             myClient.setName(username);   
            }
        catch (Exception ex) {
             System.out.println("Registration Failed!");
            }
            
            JOptionPane.showMessageDialog(this,"Registered as " + username,"Auction System",JOptionPane.OK_OPTION);
                
    }
    
    
 /**
 *                            
 * Creates connection between client and server.
 * 
 * If successful the client is acknowledged by the server has currently
 * bidding. The methods for generating the bidding display and retrieving
 * the list of auction items is also called.
 *
 * 
 * @param clientName String username for client attempting login
 * @param clientPass String password for client attempting login
 * 
 */
    
 public void connectToServer(String clientName, String clientPass){
                
           /*
              Server name changed to AS for simplicity. In this example we are
              assuming there is only one auction server on the local machine
              to connect to.
           */  
       
            
  boolean loggedIn = false;     
  String bindName = "AS";

  try{

                        
      System.out.println("Looking up " + bindName);
      auctionServer = (AuctionServer)Naming.lookup(bindName);

      myClient = new AuctionClientImpl(receivedList,clientName);
      loggedIn = auctionServer.connect(myClient,clientPass);
    }
    
    catch(Exception e){
      JOptionPane.showMessageDialog(this,"Failed to find server: " + bindName,"Auction Server",JOptionPane.ERROR_MESSAGE);
  } 
                
                if(loggedIn)
                {
                 displayAuctionInterface();
                 getAuctionItems();
                }
                
                else {
                    JOptionPane.showMessageDialog(this,"Failed to login" + bindName,"Auction Server",JOptionPane.ERROR_MESSAGE);
                    
                }
 }
       
    
/*                         
 * Gets all AutionItems from the AuctionServer.
 * 
 * Populates the auctions systems ArrayList of items with those
 * which are currently available for bidding.
 *
 */     
       
        
        
 public void getAuctionItems() {
            
  try{   
      auctionItems = auctionServer.getItems();            
            itemList = new JComboBox(auctionItems.toArray());                     
            bottomPanel.add(itemList);
              
                                    
            pack();
            
     }
    catch(Exception e){
    
      System.out.println("Failed to get items");
            }
        }
        
        
/**
 *                            
 * Enter into an Auction.
 * 
 * Asks the server to assign the local client to bid for a specific item. Also
 * displays the clients received list in the window as this is the first time 
 * anything will be received from the server (when addClientToItem is remotedly
 * called).
 * 
 * @param anItem Item Item reference to be passed to the server as ID
 * 
 */


 public void enterAuction(Item anItem) {
                
                currentItem = anItem;
              
                
                container.add(receivedList, BorderLayout.CENTER);
                sendButton.setEnabled(true);
                
                pack();
                
                try {

                auctionServer.addClientToItem(myClient,anItem.getID());
                
                }
                
                catch (Exception ex) {
                    System.err.println(ex);
                    System.out.println("Failed to enter auction room!");
                }



 }
        
        
/**                        
 * Send Bid to server for item.
 * 
 * Passes the amount the client would like to bid to the server - as an unformatted
 * String. Is based on the item currently selected for bidding.
 *
 * 
 * @param bidAmount String Amount to bid for item as a String
 * 
 */
        
        public void sendBid(String bidAmount){
            
            try{
          
            
            auctionServer.sendBidToServer(myClient,currentItem.getID(),bidAmount);
            
            }
            
            catch (Exception ex) {
                
                System.out.println("failed to send bid to server");
                
            }
        } 


        
 public static void main(String[] args)  {

  AuctionSystem mySystem = new AuctionSystem();
                mySystem.setSize(300, 500);
  mySystem.setVisible(true);
 }

}