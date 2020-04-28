package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import ORS.ConnPool.*;

public class AdminLogout extends HttpServlet
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
	
	HttpSession session=req.getSession(false);
	
	session.invalidate();
	
	/*if(flag==true)
	{
	  HttpSession session=req.getSession(true);
	  session.setAttribute("User_Id",AdminId);
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
      out.print("<CENTER><h1> Error in Admin Logout !! </h1></CENTER>");
      out.println("<P>&nbsp;</P> " + "<P>");
      out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/MainPage.html\">");
      out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
      out.println("</CENTER></FORM>");
      out.println("</BODY>");
      out.println("</HTML>");
	}*/
	
	res.sendRedirect("/examples/servlets/Admin/MainPage.html");
  }

  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    doPost(req,res);
  }
}	