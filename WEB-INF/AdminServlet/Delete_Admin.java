package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_Admin extends HttpServlet
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
		int Admin_Id = 0;
		String str = null;		
				
        try
    	{
		  str=req.getParameter("SelectAdmin");
  	  	  System.out.println("Admin_name-->>"+str);
          Admin_Id = Integer.parseInt(str);    
          System.out.println("Admin_Id = " + Admin_Id); 	
    	}
    	catch (NumberFormatException exp)
    	{
      	  System.out.println("Problem in Admin_Id");
          System.err.println(exp.getMessage());
        }			
            		
		if(Admin_Id != 1)
		{
		  try
		  {
			ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			Connection con=pool.getConnection();
			Statement smt=con.createStatement();
			
		    String query = "delete from Admin where Admin_Id = " + Admin_Id;
			 
			 smt.executeUpdate(query);
		  }
		  catch(Exception e)
		  {
		 	System.err.println(e.getMessage());
	      } 
	      res.sendRedirect("/examples/servlets/Admin/AdminOption.jsp");			
		}
		else
		{
          out.println("<HTML>");
          out.println("<HEAD>");
          out.println("<META NAME=\"GENERATOR\" Content=\"Microsoft Visual Studio 6.0\">");
          out.println("<TITLE>Delete Admin ErrorPage</TITLE>");
          out.println("</HEAD>");
          out.println("<BODY bgcolor=\"ivory\">");
          out.println("<CENTER><P>");
	      out.println("<h1>Super Administrator Cannot Be Deleted.</h1><P></P></CENTER>");
          out.println("<P>&nbsp;</P> " + "<P>");
	   	  out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/AdminOption.jsp\">");
		  out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
		  out.println("</CENTER></FORM>");
          out.println("</BODY>");
          out.println("</HTML>");	      
		}
	}
		
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}