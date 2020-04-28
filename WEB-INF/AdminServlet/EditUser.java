package ORS.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class EditUser extends HttpServlet
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
	boolean status = false;
	String message = "Unknown Error";
	
	int Experience=0;
	long User_Id = 0,Telephone=0;
	String Fname=null,Mname=null,Lname=null;
	String Gender=null,Address=null,Email=null,Birthdate = null;
	String Qualification=null,Branch=null,Passwd=null;
	
	Fname=req.getParameter("fname");
	Mname=req.getParameter("mname");
	Lname=req.getParameter("lname");
	Gender=req.getParameter("gender");
	Address=req.getParameter("address");
	Email= req.getParameter("email");
	Birthdate=req.getParameter("Bdate");
	Qualification= req.getParameter("Qualification");
	Branch= req.getParameter("Branch");
	Passwd=req.getParameter("passwd");
	
	if(Fname != null && Mname != null && Lname != null && Gender != null && Address != null)
	{
	  if(Birthdate != null && Email != null && Qualification != null && Branch != null)
	  {
  	  	if( Passwd != null)
  	  	  status = true;
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
        User_Id=Long.parseLong(req.getParameter("User_Id"));
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
	  else if(User_Id < 1)
	  {
	  	message = "Invalid User";
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
	  Connection con = null;
	  Statement smt = null;
	  
	  try
	  {
		ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
	    smt=con.createStatement();

	    String query = "Update Users set FNAME='"
	                   + Fname
			           + "',MNAME='"
			           + Mname
			           + "',LNAME='"
			           + Lname
			           + "',PASSWORD='"			 
			           + Passwd
			           + "',GENDER='"
			           + Gender
			           + "',TELEPHONE="
			           + Telephone
			           + ",ADDRESS='"
			           + Address
			           + "',EMAIL='"
			           + Email
			           + "',DATE_OF_BIRTH='"
			           + Birthdate
			           + "',QUALIFICATION='"
			           + Qualification
			           + "',BRANCH='"
			           + Branch
			           + "',EXPERIENCE="
			           + Experience 
                       + " Where User_Id="
                       + User_Id;
		
		System.out.println("query = " + query);
		smt.executeUpdate(query);
		smt.close();		     		  
	  }		
	  catch(Exception exp)
	  {
	    exp.printStackTrace();
	    out.println("Error in User Update : " + exp.getMessage());
		  
	    try
        {
          Statement stmt = con.createStatement();
          stmt.executeUpdate("Rollback");
          stmt.close();        	
        }
        catch(Exception ee)
        {
          out.println("Error : System restoration failed" + ee.getMessage());
          ee.printStackTrace();
        }
	  } 
 	  res.sendRedirect("/examples/servlets/User/PostView.jsp");        	
	}
	else
	{
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>Update User ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> " + message + ", Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/PostView.jsp\">");
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