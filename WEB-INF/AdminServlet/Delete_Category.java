package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_Category extends HttpServlet
{
  public void init(ServletConfig conf)throws ServletException
  {
    super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    int Cat_Id = 0;
    res.setContentType("text/html");
 	PrintWriter out=res.getWriter();
		
	String str= null;		
	str = req.getParameter("SelectCategory");
		
	if(str != null)
	{
	  try
	  {
	    Cat_Id = Integer.parseInt(str);		
	  }
	  catch(NumberFormatException e)
	  {
	  	Cat_Id = 0;
	  }
	}
		System.out.println("Category_Id = " + Cat_Id);

    if(Cat_Id != 0)
    {
      Connection con = null;	
    	
	  try
	  {
	    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();
        Statement smt=con.createStatement();

	    String Query1 = "Select Post_Id from Post";
  	    ResultSet rs1=smt.executeQuery(Query1);
          
        while(rs1.next())
        {
          try
          {
            int Post_Id = 0;
            Post_Id = rs1.getInt(1);
            System.out.println("Post = " + Post_Id);
            
            Statement smt1 = con.createStatement();
            
            String Query2 = "Delete Table(select CatEntry from Post where Post_Id = "
                            + Post_Id + ")N where N.category_Id =" + Cat_Id;
            
            smt1.execute(Query2);
            smt1.close();
          }
          catch(SQLException ex)
          {
            System.out.print("err" + ex.getMessage());
          }
        }
					 
	    String query3 = "Delete from Category where Category_Id = "
		                  + Cat_Id;
			                
		smt.executeUpdate(query3);
		smt.executeUpdate("Commit");
		System.out.println("Category Deleted-!!");
		  
		smt.close();
	  }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	    out.println("Error : " + e.getMessage());

	    try
	    {
          Statement stmt=con.createStatement();
 	      stmt.execute("Rollback");
 	      stmt.close();
 	    }
 	    catch(Exception ex)
 	    {
 	      out.println("Data Restoration Failed : " + ex.getMessage());
 	    } 
	  }      
    }
	
	res.sendRedirect("/examples/servlets/Admin/Category.jsp");
  }
		
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}	