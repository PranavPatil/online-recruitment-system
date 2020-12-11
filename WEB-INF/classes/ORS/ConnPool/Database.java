package ORS.ConnPool;

import java.sql.*;

public class Database
{
 private Connection conn = null;

 public Database(ConnectionPool pool) throws Exception
 {
   conn = pool.getConnection();
   conn.setAutoCommit(false);
 }
 
 public ResultSet RetriveDb(String Query) throws SQLException
 {
    Statement stmt = null;
    ResultSet rs = null;
       
    if(Query != null)
    {
      stmt=conn.createStatement();
      rs=stmt.executeQuery(Query);
    }

	return(rs);
 }
 
 public void ExecuteDb(String Query) throws SQLException
 {
   Statement stmt = null;
       
   try
   {
     if(Query != null)
     {
       stmt = conn.createStatement();
       stmt.execute(Query);
       stmt.close();
       conn.commit();
     }
   }
   catch(SQLException sqlex)
   {
   	 conn.rollback();
   	 throw sqlex;
   }
 }
 
 public static boolean isColumn(String ColName, ResultSet rs)
 {
 	try
 	{
 	  rs.findColumn(ColName);
 	  return true;
 	}
 	catch(SQLException sex)
 	{
 	  return false;
 	}
 }

 protected void finalize() throws Throwable
 {
   conn.close();
 }
}