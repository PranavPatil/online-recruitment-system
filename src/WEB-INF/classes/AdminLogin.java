package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class AdminLogin extends HttpServlet
{
  ConnectionPool pool=null;    
  Connection con=null;
  PrintWriter out;
  Statement smt;
  String Admin=null;
  ResultSet rs=null;
	  
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
   	Admin=null;
    String Passwd=null;
	String AdminId=null;
	Admin=req.getParameter("Admin_Name");
	Passwd=req.getParameter("Admin_Passwd");	
	
	try
	{		
		pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
		
		smt=con.createStatement();
		rs=smt.executeQuery("select Admin_Id,Login,Password from Admin");
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
		  System.out.println("Logged  :" +Admin);
		  System.out.println("Admin_Id :"+AdminId);
		  res.sendRedirect("http://localhost:8080/examples/servlets/Admin/AdminHomeFrame.html");
		}
		else
		{
		  System.out.println("Login Failed  :" +Admin);
          out.println("<HTML>");
          out.println("<HEAD>");
          out.println("<TITLE>Prompt</TITLE>");
          out.println("</HEAD>");
          out.println("<BODY bgcolor=\"ivory\">");	   		
          out.print("<h1> Invalid Password, Login denied !! </h1>");	   		
          out.print("<h1> Please check your password again !! </h1>");
          out.println("<P>&nbsp;</P> " + "<P>" +
	   		          "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/Admin/AdminLoginPage.html\">" +				 
				      "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
		              "</FORM>");          
          out.println("</BODY>");
          out.println("</HTML>");	   			  
		}				
	}
	catch(Exception e)
	{
	  System.err.println(e.getMessage());
	}
	                        
	try
	{		
	  rs.close();
	  smt.close();		
	}
	catch(Exception e)
	{
	  System.out.println(e.getMessage());
	}
  }

  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    doPost(req,res);
  }
}	