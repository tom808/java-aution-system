import java.io.*;
import java.util.*;
import java.sql.*;
import java.rmi.*;
import java.net.*;
import java.rmi.server.*;

public class DatabaseAccessorImpl implements DatabaseAccessor {
  
      
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */
    
    
    
/**
 * DataBase Accessor Implementation                            
 * 
 * Contains all the methods associated with interating with a database. In this instance the database
 * is a preconstructed mySQL DB on the host system.
 *
 */
  
  
  /**
 * Get Connection.                            
 * 
 * Takes a list of parameters which are used to connected to a local MySQL database. The database driver (JDBC)
 * is com.mysql.jdbc.Driver found locally. This connection script returns an Object of type connection to by used
 * throught this class.
 * 
 * @return Connection
 *
 */
    
    private String[] parameters;
    
    public DatabaseAccessorImpl() {
        
        parameters =  new String[5];
        
        BufferedReader br = null;
 
            try {
 
                String sCurrentLine;
                int index;
 
  br = new BufferedReader(new FileReader("config.ini"));
 
                
                
  for (int i = 0; i <5; i++) {
                    sCurrentLine = br.readLine();
                    index = sCurrentLine.indexOf("=");           
                    parameters[i] = sCurrentLine.substring(index+1);
                    
  }
 
  } catch (IOException e) {
                    e.printStackTrace();
                }
            
            
    }
    
  
    public Connection getConnection() {
    
    Connection conn = null;
  
    // set up the connection data
    String url = parameters[0];
    String db = parameters[1];
    String username = parameters[2];
    String pass = parameters[3];
    String driver = parameters[4];
  
  
    try{
     // Load the mysql driver
        Class.forName(driver);
     // Get a connection to the database
        conn = DriverManager.getConnection(url+db,username,pass);
        
        }
    
     catch (Exception e){
            e.printStackTrace();
        }return conn;
        
    }
    
    
    /**
 * Insert User.                            
 * 
 * Takes in the necessary fields for a user and runs an inster query into the database.
 * 
 *  @param userName String
 *  @param passWord String
 *  @param email String
 *
 */
    
    public void insertUser(String userName, String passWord, String email) {
        
        Connection con = getConnection();
        
    try{
         Statement st = con.createStatement();
         int val = st.executeUpdate("INSERT users VALUES('"+ userName +"','"+ passWord +"','" + email +"')");
         System.out.println("1 row affected");
     
         con.close();
       }
   
    catch (SQLException s){
            System.out.println("SQL statement failed.");
       }
        
        
    }
    
    
    /**
 * Record Transaction.                           
 * 
 * Takes an item ID to record the transaction made against it.
 * 
 * @param itemNum String
 * @param transaction String
 * 
 */
  
    public void recordTransaction(int itemNum,String transaction) {
    
    Connection con = getConnection();
  
    
     try{
     Statement st = con.createStatement();
     // val is how many rows are affected by the Update
     int val = st.executeUpdate("INSERT transactions VALUES('"+ itemNum +"','"+ transaction +"')");
     System.out.println("1 row affected");
     
     con.close();
    }
   
    catch (SQLException s){
     System.out.println("SQL statement failed.");
    }

    
 /*   
 * Get Items for Sale                         
 * 
 * Queries the database and returns all items which are for sale. Then generates a unique
 * Item object for all this data and stores it to an ArrayList. This list is then returned
 * to the caller.
 * 
 * @returns ArrayList<Item>
 * 
 */
    
    }
    
    public ArrayList<Item> getItemsForSale() {
    
        ArrayList<Item> items = new ArrayList<Item>();
        
        Connection conn = getConnection();
     
     try{
     String selectSQL = "select * from items";
     Statement stmt = conn.createStatement();
     

     ResultSet result = stmt.executeQuery(selectSQL);
     
                   
      while(result.next()){
   
                        items.add(new Item(result.getInt("itemID"),result.getString("name"),result.getDouble("startPrice")));
                                                   

   }
                    
                    conn.close();
    }
   
    catch (SQLException s){
     System.out.println("SQL statement failed.");
    }
     
    return items;
               
    }
    
    
 /* Login User.                           
 * 
 * Takes a username and password then queries the database for the corresponding username. This 
 * is checked against the supplied password and returns true if there is a match.
 * 
 * @param userName String
 * @param passWord String
 * @returns boolean
 * 
 */
    
    public boolean logInUser(String userName, String passWord){
     
        
    
     Connection conn = getConnection();
     
     try{

     String selectSQL = "select password from users where user_name = '" + userName + "'";
     
     Statement stmt = conn.createStatement();

     ResultSet result = stmt.executeQuery(selectSQL);//query does not work
     
     result.next();
     
     
     if (result.getString("password").equals(passWord)) {
         
     
         return true;
         
        }
     }
     
     catch (SQLException ex) {
         System.err.println(ex);
         System.out.println("Query failed!");
         
     }
  
     return false;
     
    }

}
    
    
