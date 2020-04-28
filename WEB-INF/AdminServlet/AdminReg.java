package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import ORS.ConnPool.*;

public class AdminReg extends HttpServlet
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
	
	String Fname = null,Lname = null,Designation = null,Email = null;
	String Login = null,Passwd = null;
	boolean repeat = false;
	int Attempt=0,count = 0;
	
	Fname=req.getParameter("Fname");
	Lname=req.getParameter("Lname");
	Designation = req.getParameter("Designation");
	Email= req.getParameter("Email");
	Login= req.getParameter("Login");
	Passwd=req.getParameter("Passwd");		
			
	if(Fname != null && Lname != null && Designation != null && Email != null && Login != null && Passwd != null)
	{
	  Statement smt = null;
	  
	  try
	  {
        count = 0;
	    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();
	    smt=con.createStatement();

	    ResultSet rs = smt.executeQuery("select Admin_Id from Admin " + 
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
		out.println("Error in Admin Entry : " + e.getMessage());
		e.getStackTrace();
	  }
						                           
      if(repeat == false)
      {
	    try
	    {
	      String query = "insert into Admin values(admin_Id.nextval"
		                  + ",'"
			              + Fname
			              + "','"
			              + Lname
			              + "','"
			              + Login
			              + "','"
			              + Passwd
			              + "','"
			              + Email
			              + "','"
			              + Designation
			              + "')";
			 			 
		  smt.executeUpdate(query);
		  smt.close();		     		  
		}		
		catch(SQLException e)
		{
		  out.println("Error in Admin Entry : " + e.getMessage());
		  e.getStackTrace();
		}		
		  res.sendRedirect("/examples/servlets/Admin/AdminOption.jsp");
	  }
	  else if (repeat == true)
	  {
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>AdminReg ErrorPage");
	    out.println("</title>");
	    out.println("</head>");
	    out.println("<body bgcolor=ivory>");
	    out.println("<CENTER><h1> Please insert another Login Id !!</h1></CENTER>");
        out.println("<P>&nbsp;</P> " + "<P>");
	    out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/AdminOption.jsp\">");
	    out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	    out.println("</CENTER></FORM></body>");
	    out.println("<html>");
	  }		  		
	}
	else
	{
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>AdminReg ErrorPage</TITLE>");
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