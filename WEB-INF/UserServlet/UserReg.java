package ORS.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class UserReg extends HttpServlet
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
	Connection con = null;
	String message = "Unknown Error";
	boolean status = false;
				
	String Fname=null,Mname=null,Lname=null;
	String Gender=null,Address=null,Email=null,Birthdate = null;
	String Qualification=null,Branch=null,Login=null,Passwd=null;
	int Experience=0;
	long Telephone=0;
	
	Fname=req.getParameter("fname");
	Mname=req.getParameter("mname");
	Lname=req.getParameter("lname");
	Gender=req.getParameter("gender");
	Address=req.getParameter("address");
	Email= req.getParameter("email");
	Birthdate=req.getParameter("Bdate");
	Qualification= req.getParameter("Qualification");
	Branch= req.getParameter("Branch");
	Login = req.getParameter("login");
	Passwd=req.getParameter("passwd");
	
	if(Fname != null && Mname != null && Lname != null && Gender != null && Address != null)
	{
	  if(Birthdate != null && Email != null && Qualification != null && Branch != null)
	  {
  	  	if(Login != null && Passwd != null)
  	  	{
  	  	    status = true;	
  	  	}
  	  	else
  	  	 message = "Empty Parameter fields";
	  }
  	  else
  	   message = "Empty Parameter fields";
	}
  	else
  	 message = "Empty Parameter fields";
	
	if(status == true)
	{
	  try
	  {
        Telephone = Long.parseLong(req.getParameter("telephone"));
	    Experience = Integer.parseInt(req.getParameter("Experience"));
	  }
	  catch(NumberFormatException nex)
	  {
	  	message = "Illegal NumberFormat entry";
	  	status = false;
	  }
	  
	  if(Experience < 1 || Experience > 5)
	  {
	  	message = "Invalid Experience entry";
	  	status = false;
	  }
	  else if(Address.length() > 500)
	  {
	  	message = "Address length voilated";
        status = false;
	  }
	}	
	
	if(status == true)
	{
	  Statement smt = null;
	  boolean repeat = false;
	  int Attempt=0;
      int count = 0;
	  
	  try
	  {
		count = 0;
		ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
	    smt=con.createStatement();

		ResultSet rs = smt.executeQuery("Select User_Id from Users " + 
		                                "where Login = '" + Login + "'");
		while(rs.next())
		   count++;
        
        System.out.println("Count = " + count);
		     		
		if(count == 0)
		  repeat = false;
		else if(count > 0)        		  
		  repeat = true;
	  }
	  catch(Exception e)
	  {
	    out.println("Error in User Entry : " + e.getMessage());
	    e.getStackTrace();
	  }
						                           
      if(repeat == false)
      {
	    try
	    {
	      String query = "insert into Users values(User_Id.nextval"
	            		 + ",'"
			             + Fname
			             + "','"
			             + Mname
			             + "','"
			             + Lname
			             + "','"
			             + Login
			             + "','"			 
			             + Passwd
			             + "','"
			             + Gender
			             + "','"
			             + Telephone
			             + "','"
			             + Address
			             + "','"
			             + Email
			             + "','"
			             + Birthdate
			             + "','"
			             + Qualification
			             + "','"
			             + Branch
			             + "',"
			             + Experience + ",0,0,0)";			 
			 
		  smt.executeUpdate(query);
		  smt.close();		     		  
		}		
		catch(Exception exp)
		{
		  exp.printStackTrace();
		  out.println("Error in User Entry : " + exp.getMessage());
		  
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
 		res.sendRedirect("/examples/servlets/User/UserLoginPage.html");        	
      }
	  else if (repeat == true)
	  {
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>UserReg ErrorPage");
	    out.println("</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=ivory>");
	    out.println("<CENTER><h1> Please insert another Login Id !!</h1></CENTER>");
        out.println("<P>&nbsp;</P> " + "<P>");
	    out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/UserRegistrationPage.html\">");	 
        out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	    out.println("</CENTER></FORM>");
	    out.println("</body>");
	    out.println("<html>");
	  }
	}
	else
	{
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>UserReg ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> " + message + ", Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/UserRegistrationPage.html\">");
	  out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER>");
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