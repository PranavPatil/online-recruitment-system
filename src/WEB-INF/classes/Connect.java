package ORS.ConnPool;

import java.sql.*;
import java.io.*;
import java.util.*;

public class Connect
{
 ArrayList str = new ArrayList();
 String cat=null;
 int size,id=0;
 
 public Connect()
 { 	
 } 
 public void setQuery(String query)
 {
       Connection conn;	  
       Statement smt;       
       PrintWriter out = null;
       ResultSet rs = null;
       String temp = null;
       
    try
    {
	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	  conn=DriverManager.getConnection("jdbc:odbc:ORS","","");	    

	  System.out.println("Hello " + query);
	  		 
	  smt=conn.createStatement();
	  rs=smt.executeQuery(query);
	  if(rs==null)
	  {
		System.out.println("Query Error");
	  }
	  while(rs.next())
	  {
	  	temp = rs.getString(1);
	  	str.add(temp);	  	
	  }

	  rs.close();
	  smt.close();	
	}
	catch(Exception e) 
	{
		System.err.println("1"+e.getMessage());
		e.printStackTrace(out);
	}
 }
 
 public int getSize()
 {
	  size = str.size();
	  System.out.println("Size = " + size); 	
	  return (this.size);
 }	

 public void setId(int id)
 {
 	this.id = id;
 }
 
 public String getCat()
 {
 	  System.out.println("id = " + id);
	  cat = (String)str.get(id);
	  System.out.println(cat); 	
	  return (this.cat);
 }
 
 public void setClear(String clear)	
 {
    System.out.println("ArrayList Cleared !!" + size);
	str.clear();
 }
 
 public ResultSet setRSQuery(String query)
 {
    Connection conn;	  
    Statement smt;       
    PrintWriter out = null;
    ResultSet rs = null;
    String temp = null;
       
    try
    {
	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	  conn=DriverManager.getConnection("jdbc:odbc:ORS","","");	    
		       
	  System.out.println("Hello " + query);
	  		 
	  smt=conn.createStatement();
	  rs=smt.executeQuery(query);
	}
	catch(Exception e) 
	{
		System.err.println("1"+e.getMessage());
		e.printStackTrace(out);
	}
	return(rs);
 }
 
}
