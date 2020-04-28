package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import ORS.ConnPool.*;

public class AdminLogin extends HttpServlet
{
  public void init(ServletConfig conf)throws ServletException
  {
    super.init(conf);
  }
		 
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	
	boolean flag=false;
   	String Admin=null;
    String Passwd=null;
	String AdminId=null;
	Admin=req.getParameter("Admin_Name");
	Passwd=req.getParameter("Admin_Passwd");	
	
	try
	{		
		ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		Connection con=pool.getConnection();
		
		Statement smt=con.createStatement();
		ResultSet rs=smt.executeQuery("select Admin_Id,Login,Password from Admin");

		if(rs==null)
		{
		  System.out.println("Query Error");
		}
	 
	 	while(rs.next())
		{   
		  AdminId=rs.getString("Admin_id");
		  
	  	  if(Passwd.equals(rs.getString("PASSWORD")) && Admin.equals(rs.getString("Login")))
	   	  {
	   	    flag=true;
	   	    break;
	   	  }	   	
		}
	
		if(flag==true)
		{
		  HttpSession session=req.getSession(true);
		  session.setAttribute("Admin_Id",AdminId);
		  System.out.println("Logged  :" +Admin);
		  System.out.println("Admin_Id :"+AdminId);
		  res.sendRedirect("/examples/servlets/Admin/AdminHomeFrame.html");
		}
		else
		{
		  System.out.println("Login Failed  :" +Admin);
          out.println("<HTML>");
          out.println("<HEAD>");
          out.println("<TITLE>AdminLogin ErrorPage</TITLE>");
          out.println("</HEAD>");
          out.println("<BODY bgcolor=\"ivory\">");
          out.print("<CENTER><h1> Invalid Password, Login denied !! </h1>");
          out.print("<h1> Please check your password again !! </h1></CENTER>");
          out.println("<P>&nbsp;</P> " + "<P>");
          out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/AdminLoginPage.html\">");
	      out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
          out.println("</CENTER></FORM>");
          out.println("</BODY>");
          out.println("</HTML>");
		}
		
 	    rs.close();
	    smt.close();
	}
	catch(Exception e)
	{
	  System.err.println(e.getMessage());
	}
  }

  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    doPost(req,res);
  }
}	