package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class AutoDelUser extends HttpServlet
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
    
    Connection con = null;
      
	try
	{
       ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	   con=pool.getConnection();
	   Statement smt=con.createStatement();
	   ResultSet reslt = null;
	   
       reslt = smt.executeQuery("Select User_Id from Result group by User_Id having min(Months_Between(Sysdate,Testdate)) > 12");

       while(reslt.next())
       {
       	 Statement stamt=con.createStatement();
       	 long User_Id = 0;
       	 User_Id = reslt.getLong(1);

         smt.execute("Delete from Result where User_Id = " + User_Id);
         smt.execute("Delete from TestQuestions where User_Id = " + User_Id);
         smt.execute("Delete from Users where User_Id= " + User_Id);
         System.out.println("Users deleted = " + User_Id);
         smt.execute("Commit");
       }
       smt.close();
	}
	catch(Exception e)
	{
	  e.printStackTrace();
	  out.println(e.getMessage());

	  try
      {
        Statement stmt = con.createStatement();
        stmt.execute("Rollback");
        stmt.close();        	
      }
      catch(Exception ee)
      {
        out.print("Error : System restoration failed" + ee.getMessage());
      }
	}
	
    res.sendRedirect("/examples/servlets/Admin/ResultView.jsp");
  }
  
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}