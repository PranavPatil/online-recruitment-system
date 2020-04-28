/*
   @Version 1.01 2005-09-21
   @author Pranav Patil
*/

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Uninstall
{  
   public static void main(String[] args)
   {  
   
      Win32RegKey key = new Win32RegKey(
         Win32RegKey.HKEY_LOCAL_MACHINE,
         "SOFTWARE\\SSCL\\ORS");

      Object value = key.getValue("Path");
      String Path = null;
                        
      if(value != null)
      {
        Path = value.toString();
        System.out.println("System Path : " + Path);
      }
      else
        Path = null;
      
      // To uninstall all files
      
      Copy files = new Copy();
      
      if(Path != null)
      {
        files.setPath(Path);
       
        String str = Path + "\\WEB-INF\\classes\\ORS";
        String[] tem = new String[] { str };      
        files.Delete(tem);

        str = Path + "\\servlets\\Admin";
        tem = new String[] { str };        
        files.Delete(tem);
      
        str = Path + "\\servlets\\gifs";
        tem = new String[] { str };            
        files.Delete(tem);
      
        str = Path + "\\servlets\\User";
        tem = new String[] { str };            
        files.Delete(tem);
            
        boolean exist = files.Exists("RESTORE");
            
        if(exist == true)
        {
          str = Path + "\\WEB-INF\\web.xml";
          tem = new String[] { str };            
          files.Delete(tem);
      
          boolean val = false;
          val = files.rename("RESTORE","web.xml");      	
        }
        System.out.println("System files deleted Successfully !!");
      }
      else
      {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null, "Setup can't find the specified path.","Error",JOptionPane.ERROR_MESSAGE);        
        System.exit(0);
      }
                        
      //Delete the Registry Entries
      
      key.setPath("SOFTWARE\\ODBC\\ODBC.INI\\ODBC Data Sources");
      value = key.getValue("ORS");
                        
      if(value != null)
      {
        boolean ok = false;
        Database db = new Database();                    //Drop all the Database
        ok = db.isConnected();
          
        if(ok == true)
        {
          ok = db.Query("Uninstall.txt");
          
          if(ok == true)
            System.out.println("Database uninstalled Successfully !!");
        }
        else
        {
          String str = "Oracle not available.\nDatabase Uninstallation failed.";
          Toolkit.getDefaultToolkit().beep();
          JOptionPane.showMessageDialog(null, str);
          System.exit(0);
        }       
        key.delValue("ORS");
      }

      key.setPath("SOFTWARE\\ODBC\\ODBC.INI\\ORS");
      value = key.isKey();
                        
      if(value != null)
      {
      	key.delKey();
      }
      
      key.setPath("SOFTWARE\\SSCL\\ORS");
      value = key.isKey();
                        
      if(value != null)
      {
      	key.delKey();
      }

      key.setPath("SOFTWARE\\SSCL");
      value = key.isKey();

      if(value != null)
      {
      	key.delKey();
      }
   }
}
