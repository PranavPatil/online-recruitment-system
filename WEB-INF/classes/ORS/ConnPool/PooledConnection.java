package ORS.ConnPool;

import java.sql.*;

public class PooledConnection
{
	
  private Connection connection=null;
  private boolean inuse=false;

  public PooledConnection(Connection value)
  {
	if(value!=null)
	{
	  connection=value;
	}
  }	

  public Connection getConnection()
  {
	return connection;	
  }
  
  public void setInUse(boolean value)
  {
	inuse=value;	
  }
	
  public boolean getInUse()
  {
	return inuse;	
  }
	
  public void close()		
  {
	try
	{
	  connection.close();
	}
	catch(SQLException e)
	{
	  System.err.println(e.getMessage());
	}	
  }
}