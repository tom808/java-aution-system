import java.util.*;
import java.sql.*;


public interface DatabaseAccessor{
  
  
      
 /**
 * @author      Thomas Egan 09178371 
 * @since       5-12-2012
 */
    
    
    
/**
 * DataBase Accessor Interface
 *
 */
  
    public Connection getConnection();
  
    public void recordTransaction(int itemNum,String transaction);
    
    public ArrayList<Item> getItemsForSale();
    
    public void insertUser(String userName, String passWord, String email);
    
    public boolean logInUser(String userName, String passWord);
}
