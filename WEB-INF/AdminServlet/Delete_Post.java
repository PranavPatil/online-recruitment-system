package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_Post extends HttpServlet
{  
  public void init(ServletConfig conf)throws ServletException
  {
	super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	int Post_Id = 0;
	
	try
	{
	  Post_Id = Integer.parseInt(req.getParameter("SelectPost"));
	}
	catch(NumberFormatException ex)
	{
	  Post_Id = 0;
	}
	
	System.out.println("Post_Id-->>"+ Post_Id);

	if(Post_Id != 0)
	{
      Connection con = null;
      Statement smt = null;
 
	  try
	  {
        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();
	    smt=con.createStatement();
	  }
	  catch(Exception e)
	  {
        e.printStackTrace();
        out.println(e.getMessage());
	  }

	  try
	  {
	    smt.executeUpdate("Delete from TestQuestions where Post_Id =" + Post_Id);
	    System.out.println("TestQuestions Deleted !!");
	    smt.executeUpdate("Delete from Result where Post_Id =" + Post_Id);
	    System.out.println("Result Deleted !!");
	    smt.executeUpdate("Delete from Post where Post_Id =" + Post_Id);
	    smt.executeUpdate("Commit");
	    System.out.println("Post Deleted !!");
	    smt.close();
	  }
	  catch(Exception e)
	  {
        e.printStackTrace();
        out.println(e.getMessage());
        
        try
        {
          Statement stmt = con.createStatement();
          stmt.executeUpdate("Rollback");
          stmt.close();        	
        }
        catch(Exception ee)
        {
          out.print("Error : System restoration failed" + ee.getMessage());
        }
	  }
	}
	
	res.sendRedirect("/examples/servlets/Admin/Post.jsp");
  }	
	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}	