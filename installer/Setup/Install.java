/*
   @Version 1.01 2005-09-21
   @author Pranav Patil
*/

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Install
{  
  public static void main(String[] args)
  {  
    boolean Entry = true;
	String Path = null;

    Win32RegKey key = new Win32RegKey(
       Win32RegKey.HKEY_LOCAL_MACHINE,
       "SOFTWARE\\javasoft\\Java Development Kit");
                        
    
    Win32 win = new Win32();
    Object value = key.getValue("CurrentVersion");
                        
    try
 	{
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  	    	
    }	
    catch(Exception ex)
    {
  	  System.out.println("Error : " + ex);
  	}
      
    if(value != null && value.equals("1.5"))
    {
      Entry = true;

      key.setPath("SOFTWARE\\Apache Software Foundation\\Tomcat\\5.0");
      value = key.getValue("Version");

      if(value != null)
      {
        Entry = true;
        Path = value.toString();
        System.out.println("Setup detected Apache Tomcat 5.0 and Configured it as WebServer");
      }
      else if(value == null)
      {
        System.out.println("Apache Tomcat 5.0 not installed ");
        System.out.println("Searching for Apache Tomcat 4.1 ");
        key.setPath("SOFTWARE\\Apache Group\\Tomcat\\4.1");
        value = key.getValue("");
        
        if(value == null)
        {
          System.out.println("Apache Tomcat 4.1 not installed ");	
          System.out.println("Searching for Apache Tomcat 4.0 ");	          
          key.setPath("SOFTWARE\\Apache\\Apache Tomcat 4.0");
          value = key.getValue("");

          if(value == null)
          {
            Entry = false;
            System.out.println("Apache Tomcat 4.0 not installed ");
            //Toolkit.getDefaultToolkit().beep();
            win.Beep(16);
            JOptionPane.showMessageDialog(null, "Please install Apache Tomcat 5.0 \\ 4.1 \\ 4.0 ","Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
          }
          else
          {
            Entry = true;
            Path = value.toString();            
            System.out.println("Setup detected Apache Tomcat 4.0 and Configured it as WebServer ");
          }
        }
        else
        {
          Entry = true;
          Path = value.toString();            
          System.out.println("Setup detected Apache Tomcat 4.1 and Configured it as WebServer ");
        }
      }
    }
    else
    {
      Entry = false;
      //Toolkit.getDefaultToolkit().beep();
      win.Beep(16);
      JOptionPane.showMessageDialog(null, "Java Development Kit 1.5 not installed ","Error",JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }

    if(Entry == true)
    {
      key.setPath("SOFTWARE\\ODBC\\ODBCINST.INI\\ODBC Drivers");
      value = key.getValue("Microsoft ODBC for Oracle");

      if(value != null && value.equals("Installed"))
      {
        Entry = true;
        System.out.println("Setup detected Microsoft ODBC Driver ");                  	
      }
      else
      {
        Entry = false;
        //Toolkit.getDefaultToolkit().beep();
        win.Beep(16);
        JOptionPane.showMessageDialog(null, "Microsoft ODBC Driver for Oracle not installed ","Error",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
    }

    if(Entry == true)
    {
      key.setPath("SOFTWARE\\ORACLE\\Home0");
      value = key.getValue("ORACLE_HOME_NAME");
		
      if(value == null)
      {
        Entry = false;
        //Toolkit.getDefaultToolkit().beep();
        win.Beep(16);
        JOptionPane.showMessageDialog(null, "Oracle 8i not Installed ","Error",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
      else
      {
        Entry = true;
        System.out.println("Setup detected Oracle in " + value);
      }
    }
         
    if(Entry == true)
    {
      value = key.getValue("ORACLE_SID");      	
         
      if(value == null)
      {
    	Entry = false;
        //Toolkit.getDefaultToolkit().beep();
        win.Beep(16);
        JOptionPane.showMessageDialog(null, "Oracle 8i Database Not Initialized ","Error",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
      else
      {
        Entry = true;
        System.out.println("Oracle 8i Database Initialized with SID as " + value);
      }
    }

    if(Entry == true)
    {
      new Installer(Path);
    }
  }
}