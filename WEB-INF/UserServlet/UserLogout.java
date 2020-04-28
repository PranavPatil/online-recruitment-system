package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class UserLogout extends HttpServlet
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
    
    long User_Id = 0;
	String path = "C:/Log.dat";	
	Log file = new Log(path);
	
	HttpSession session=req.getSession(false);

    session.invalidate();
              
	/*if(flag==true)
	{
	   
	}
	else
	{
       out.println("<HTML>");
       out.println("<HEAD>");
       out.println("<TITLE>UserLogin ErrorPage</TITLE>");
       out.println("</HEAD>");
       out.println("<BODY bgcolor=\"ivory\">");
       out.println("<CENTER><h1> Error in User Logout !! </h1></CENTER>");
       out.println("<P>&nbsp;</P> " + "<P>");
	   out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/MainPage.html\">");
	   out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	   out.println("</CENTER></FORM>");
       out.println("</BODY>");
       out.println("</HTML>");
	}*/
	
	res.sendRedirect("/examples/servlets/User/UserLoginPage.html");
  }

  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	 doGet(req,res);
  }
}	