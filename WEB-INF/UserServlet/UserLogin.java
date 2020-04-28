package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class UserLogin extends HttpServlet
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
    
	Connection con=null;
	String User=null,Passwd=null;
	String path = "C:/Log.dat";
	boolean flag=false;
	long User_Id = 0;

	Log file = new Log(path);
	User=req.getParameter("Usr_Name");
	Passwd=req.getParameter("Usr_Passwd");
    
	try
	{	 	
	  ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	  con=pool.getConnection();
	  Statement smt=con.createStatement();
	  ResultSet rs=smt.executeQuery("select Login,Password,User_Id from Users");
	  
	  while(rs.next())
	  {
		User_Id=rs.getLong("User_Id");
		
	  	if(Passwd.equals(rs.getString("Password")) && User.equals(rs.getString("Login")))
	   	{
	   	  flag=true;
	   	  System.out.println("You are Logged In !");
	   	  break;
	   	}
	   	
	    System.out.println("name = " + User_Id);	
	  }
	}
	catch(Exception e)
	{
	  	out.println("Error in User Login : " + e.getMessage());
	  	e.printStackTrace();
	  	file.writeLog(0,"Error in User Query");
	  	flag = false;
	}

	if(flag==true)
	{
	   HttpSession session=req.getSession(true);
	   System.out.println("Logged  :" +User);
	   System.out.println("User_Id :"+User_Id);
	   session.setAttribute("User_Id",User_Id);
	   
       Enumeration names = session.getAttributeNames();
       while (names.hasMoreElements())
       {
         String name = (String) names.nextElement(); 
         String value = session.getAttribute(name).toString();
         System.out.println(name + " *= " + value);
       }
       
  	   file.writeLog(User_Id,"User Logged in");
	   res.sendRedirect("/examples/servlets/User/PostView.jsp");
	}
	else
	{
	   System.out.println("Login Failed  :" +User);
	   file.writeLog(User_Id,"User Login Failed");
       out.println("<HTML>");
       out.println("<HEAD>");
       out.println("<TITLE>UserLogin ErrorPage</TITLE>");
       out.println("</HEAD>");
       out.println("<BODY bgcolor=\"ivory\">");
       out.println("<CENTER><h1> Invalid Password, Login denied !! </h1>");
       out.println("<h1> Please check your password again !! </h1></CENTER>");
       out.println("<P>&nbsp;</P> " + "<P>");
	   out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/UserLoginPage.html\">");
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