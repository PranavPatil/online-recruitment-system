package ORS.ConnPool;

import java.sql.*;
import java.util.*;

public class ConnectionPool
{
	
  private String driver=null;
  private String url=null;
  private int size=0;
  private String username=null;
  private String password=null;
  private Vector pool=null;

  public ConnectionPool()
  {	
  }
	
  public void setDriver(String value)
  {
    if(value!=null)
    {
      driver=value;
    }
  }
   
  public String getDriver()
  {
    return driver;
  } 
  
  public void setUrl(String value)
  {
    if(value!=null)
    {
      url=value;
    }
  }
   
  public String getUrl()
  {
    return url;
  } 
   
  public void setSize(int value)
  {
    if(value!=0)
    {
      size=value;
    }
  }
   
  public int getSize()
  {
     return size;
  }
   
   
  public void setUserName(String value)
  {
    if(value!=null)
    {
      username=value;
    }
  }
   
  public String getUserName()
  {
    return username;
  }  

  public void setPassword(String value)
  {
    if(value!=null)
    {
      password=value;
    }
  }
   
  public String getPassword()
  {
    return password;
  } 	

  private Connection createConnection()throws Exception
  {	
 	Connection con=null;
 	con=DriverManager.getConnection(url,username,password);
 	return con;
  }
 	
  public synchronized void initializePool()throws Exception
  {
 	if(driver==null)
 	{
 	  throw new Exception("no Driver name specified");
 	}
 	
 	if(url==null)
 	{
 	 throw new Exception("no Url name specified");
 	}
 	
 	if(size<0)
 	{
 	 throw new Exception("Pool size is less than 1");
 	}
 	
 	try
 	{
 	  Class.forName(driver);
 	  
 	  for(int x=0;x<size;x++)
 	  {
 	    System.err.println("Opening JDBC Connection "+x);
 	    Connection con=createConnection();
 	      	        
 	    if(con!=null)
 	    {
 	      PooledConnection pcon=new PooledConnection(con);
 	      addConnection(pcon);
 	    }
 	  }
 	}
 	catch(SQLException e)
 	{
 	  System.err.println(e.getMessage());
 	}
 	catch(ClassNotFoundException e)
 	{
 	  System.err.println(e.getMessage());
 	}
 	catch(Exception e)
 	{
 	  System.err.println(e.getMessage());
 	}
  }
 
  private void addConnection(PooledConnection value)
  {
	if(pool==null)
	{
	  pool=new Vector(size);
	  pool.addElement(value);	
	}
  }
     
  public synchronized void releaseConnection(Connection con)
  {
    for(int x=0;x<pool.size();x++)
    {
      PooledConnection pcon=(PooledConnection)pool.elementAt(x);
           	  
      if(pcon.getConnection()==con)
      {
        System.err.println("Releasing Connection "+x);
        pcon.setInUse(false);
        break;
      }
    }	
  }
     
  public synchronized Connection getConnection()throws Exception
  {
    PooledConnection pcon=null;
    
    for(int x=0;x<pool.size();x++)
    {
      pcon=(PooledConnection)pool.elementAt(x);
      
      if(pcon.getInUse()==false)
      {
        pcon.setInUse(true);
        return pcon.getConnection();		   
      }
    }	
     
    try
    {
      Connection con=createConnection();
      pcon=new PooledConnection(con);
      pcon.setInUse(true);
      pool.addElement(pcon);
    }
    catch(Exception e)
    {
      System.err.println(e.getMessage());
    }
    
    return pcon.getConnection();
  }
  
  public synchronized void emptyPool()
  {
  	PooledConnection pcon=null;
  	
    for(int x=0;x<pool.size();x++)
    {
      System.err.println("Closing Jdbc Connection "+x);
      pcon=(PooledConnection)pool.elementAt(x);
      
      if(pcon.getInUse()==false)
      {
     	pcon.close();	
      }
      else
      {
     	try
     	{
 		  java.lang.Thread.sleep(30000);
 		  pcon.close();    			  	
        }
        catch(Exception e)
       	{
       	  System.err.println(e.getMessage());	
       	}
      }
    }
  }
}

