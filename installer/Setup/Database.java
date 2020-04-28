/*
   @Version 1.01 2005-10-9
   @author Pranav Patil
*/

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
 
// Executes all SQL statements in a file. 

 public class Database extends Thread
 {   
   private DataInputStream din;
   private Connection conn;
   private Statement stat;
   private String Path = null;
   private boolean value = false;
   private boolean stop = false;
   private Temp tem;
   
   public void Query(String path,Temp tp)
   {
     try
     {
       int size = 0;
       String line = null;
       String text = null;
       Path = path;
       tem = tp;
       stop = false;
       
       if(Path.contains("Uninstall"))
       {
       	 text = "Deleting Previous Database...\n";
       }
       else if(Path.contains("Install"))
       {
       	 text = "Creating System Database...\n";
       }
       else if(Path.contains("Entries"))
       {
       	 text = "Creating Default Entries...\n";
       }
       else
         text = "Error";
       
       tem.setText(text);
              
       File f1 = new File(Path);
       System.out.println("Can Read = " + f1.canRead());
       FileInputStream fin = new FileInputStream(f1);
       BufferedInputStream bin = new BufferedInputStream(fin);
       DataInputStream din1 = new DataInputStream(bin);

       while (din1.available() != 0 && !stop)
       {
      	 line = din1.readUTF();
         if(line.equals("/"))
         {
         	size++;
         }            
       }
       
       System.out.println("Size of Dbase = " + size);
       
       tem.setTarget(size);
              
       din = new DataInputStream(new BufferedInputStream(new FileInputStream(f1)));

       conn = getConnection();         
       stat = conn.createStatement();
       
       Thread th = new Thread(this);
       th.start();
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
       tem.setText(ex.toString());
       value = false;
       stop = true;
     }
   }       

   public void run()
   { 
     String line = null;
     StringBuffer buffer = null;
     int current = 0;
     tem.setText(null);

     try
     {
       while ((din.available() != 0) && !stop)
       {
      	 line = din.readUTF();
       	 
         if (line == null || line.length() == 0) 
         {
         }            
         else if(line.equals("/"))
         {
           current = current + 1;
           tem.setValue(current);
           sleep(1);
           
           System.out.print("Executing : " + buffer);
           String str = new String(buffer);
           try
           {
             boolean hasResultSet = stat.execute(str);
            
             if (hasResultSet)
               showResultSet(stat);
               
             SQLException ex = null;
             
             if(ex == null)
               System.out.print("\t:  Done");

              
           }
           catch (SQLException ex)
           {
          	 if(!Path.equals("Uninstall.dat"))
           	 {           	 	
               while (ex != null)
               {
                 ex.printStackTrace();
                 tem.setText(ex.toString());
                 ex = ex.getNextException();
               }
               value = false;
               stop = true;
           	 }
           }
           buffer = null;
           System.out.println();           
         }
         else
         {
           if(buffer == null)
             buffer = new StringBuffer(line);
           else                
             buffer.append(line);
         }
       }
       din.close();       
       stat.close();
       conn.close();       
       value = true;
       stop = true;
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
       tem.setText(ex.toString());
       value = false;
       stop = true;
     }    	
   }  	    
 
   public boolean getStop()
   {
   	 return stop;
   }

   public boolean getValue()
   {
   	 return value;
   }
   
   public static Connection getConnection()
      throws SQLException, IOException
   {  
      Properties props = new Properties();
      FileInputStream in 
         = new FileInputStream("database.properties");
      props.load(in);
      in.close();

      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null)
         System.setProperty("jdbc.drivers", drivers);
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      return
         DriverManager.getConnection(url, username, password);
   }
   
   public boolean isConnected()
   {
   	  boolean bln = false;
      Connection conn = null;
      
      try
      {
        conn = getConnection();
        
        if(conn.isClosed() == false)
        {
          bln = true;
          conn.close();
        }
      }
      catch(Exception ex)
      {
      	bln = false;
      }
      return bln;          	
   }

   public static void showResultSet(Statement stat) 
      throws SQLException
   { 
      ResultSet result = stat.getResultSet();
      ResultSetMetaData metaData = result.getMetaData();
      int columnCount = metaData.getColumnCount();

      for (int i = 1; i <= columnCount; i++)
      {  
         if (i > 1) System.out.print(", ");
         System.out.print(metaData.getColumnLabel(i));
      }
      System.out.println();

      while (result.next())
      {  
         for (int i = 1; i <= columnCount; i++)
         {  
            if (i > 1) System.out.print(", ");
            System.out.print(result.getString(i));
         }
         System.out.println();
      }
      result.close();
   }     	
 }
