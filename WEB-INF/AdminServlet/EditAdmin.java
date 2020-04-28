package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import ORS.ConnPool.*;

public class EditAdmin extends HttpServlet
{ 
  public void init(ServletConfig conf)
  throws ServletException
  {
	super.init(conf);
  }
	
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
 	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	
    String Fname = null,Lname = null,Email = null;
    String Login = null,Passwd = null;
	StringBuffer date=new StringBuffer("ini");
	int Attempt=0;

	Fname=req.getParameter("Fname");
	Lname=req.getParameter("Lname");
	Email= req.getParameter("Email");
	Login =req.getParameter("Login");
	Passwd=req.getParameter("Passwd");
			
	System.out.println("Login--"+Login);
				
	if(Fname != null && Lname != null && Email != null && Login != null && Passwd != null)
	{
	  Statement smt = null;
	  
		try
		{
			ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			Connection con=pool.getConnection();
  		    smt=con.createStatement();
			 
			 String query = "update Admin set FNAME='"
			 +Fname
			 +"',LNAME='"
			 +Lname
			 +"',EMAIL='"
			 +Email
			 +"',PASSWORD='"
			 +Passwd
			 +"' "
			 +"where login='"
			 +Login
			 +"'";
			 smt.executeUpdate(query); 
		
		 }
		 catch(Exception e)
		 {
		 	out.println("Error in Admin Update : " + e.getMessage());
		 	e.printStackTrace();
	     }                        
		 		
		res.sendRedirect("/examples/servlets/Admin/AdminOption.jsp");
	}
	else
	{
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>Admin ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> Empty Parameter fields, Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/AdminOption.jsp\">");
	  out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	  out.write("</CENTER></FORM>");
      out.write("</BODY>");
      out.write("</HTML>");
	}
  }
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}