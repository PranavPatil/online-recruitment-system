package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class UserLogin extends HttpServlet
{
  ConnectionPool pool=null; 
	  	  
  public void init(ServletConfig conf)
  throws ServletException
  {
	super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    Statement smt;
	String User=null;
	ResultSet rs=null;
	String path = "C:/Log.dat";
	 
	Log file = new Log(path);             	 		 
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	HttpSession session=req.getSession(true);
	 
	boolean flag=false;
   	User=null;
    String Passwd=null;
	int UserId = 0;
	User=req.getParameter("Usr_Name");
	Passwd=req.getParameter("Usr_Passwd");	
	Connection con=null;
    file.writeLog(0,"Authenticating User");        	 	 
    	 
	try
	{	 	
	  pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	  con=pool.getConnection();
	  smt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	  rs=smt.executeQuery("select FName,Password,User_Id from Users");
	  
	  if(rs==null)
	  {
		  System.out.println("Query Error");
	  }

	  System.out.println("input = " + User);
	  System.out.println("input = " + Passwd);
	  
	  while(rs.next())
	  {   
	    System.out.println("name = " + rs.getString("FName"));
	    System.out.println("name = " + rs.getString("Password"));
	    System.out.println("name = " + rs.getInt("User_Id"));
	      
		UserId=rs.getInt("User_Id");
		
	  	if(Passwd.equals(rs.getString("PASSWORD")) && User.equals(rs.getString("FNAME")))
	   	{
	   	  flag=true;
	   	  System.out.println("OKKKKKKKKK !");
	   	  break;
	   	}	
	  }
	
	 if(flag==true)
	 {
	   System.out.println("Logged  :" +User);
	   System.out.println("User_Id :"+UserId);
	   session.setAttribute("User_Id",UserId);    
	   
	   System.out.println("Session Id 0 = " + session.getId());	 
       System.out.println("Session Creation 0 = " + session.getCreationTime());	 	 	      
     
       Enumeration names = session.getAttributeNames();
       while (names.hasMoreElements()) {
            String name = (String) names.nextElement(); 
            String value = session.getAttribute(name).toString();
            System.out.println(name + " *= " + value);
        }
           
  	   file.writeLog(UserId,"User Logged in");   
	   res.sendRedirect("http://localhost:8080/examples/servlets/User/PostView.jsp");
	 }
	 else
	 {
	   System.out.println("Login Failed  :" +User);
	   file.writeLog(UserId,"User Login Failed");    
       out.println("<HTML>");
       out.println("<HEAD>");
       out.println("<TITLE>Prompt</TITLE>");
       out.println("</HEAD>");
       out.println("<BODY bgcolor=\"ivory\">");	   		
       out.print("<h1> Invalid Password, Login denied !! </h1>");	   		
       out.print("<h1> Please check your password again !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
	   		       "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/User/UserLoginPage.html\">" +				 
				   "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
		           "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");	   			  
	 }				
	}
	catch(Exception e)
	{
	  	System.err.println(e.getMessage());
	  	file.writeLog(0,"Error in User Query");
	}                        
	
  }

  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	 doGet(req,res);
  }
}	