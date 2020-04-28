package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class DeleteQuestion extends HttpServlet
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
	long Ques_Id = 0;
		
	String Quesn = null;
	Quesn = req.getParameter("SelectQuestion");
	
	if(Quesn != null)
	{
	  try
	  {
	  	Ques_Id = Long.parseLong(Quesn);
	  }
	  catch(NumberFormatException ex)
	  {
	  	Ques_Id = 0;
	  }
	}
				
	if(Ques_Id != 0)
	{
	  Connection con = null;	
	  
	  try
	  {
		ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
		Statement smt=con.createStatement();
		smt.executeUpdate("Delete from Questions where Ques_Id = " + Ques_Id);
		smt.executeUpdate("Commit");
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
	res.sendRedirect("/examples/servlets/Admin/Question.jsp");
  }
		
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}