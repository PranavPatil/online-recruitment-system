package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class AdminReg extends HttpServlet
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
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();

		Connection con=null;
		Statement smt = null;				
		String Fname;
		String Lname;
		String Designation;
		String Email;
		String Login;
		String Passwd;
		boolean repeat = false;		
		int Attempt=0;
		int count = 0;		
		
		Fname=req.getParameter("Fname");
		Lname=req.getParameter("Lname");
		Designation = req.getParameter("Designation");
		Email= req.getParameter("Email");
		Login= req.getParameter("Login");
		Passwd=req.getParameter("Passwd");		
				
		try
		{
			count = 0;
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
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
			System.out.println("Problem in User Entry ! ");
			System.err.println(e.getStackTrace());
		}
						                           
        if(repeat == false)
        {
		  try
		  {
		  	 String query = "insert into Admin values(admin_Id.nextval"
			 +",'"
			 +Fname
			 +"','"
			 +Lname
			 +"','"
			 +Login
			 +"','"
			 +Passwd
			 +"','"
			 +Email
			 +"','"
			 +Designation
			 +"')";
			 			 
		     smt.executeUpdate(query);
		     smt.close();		     		  
		  }		
		  catch(Exception e)
		  {
			System.out.println("Problem in Admin Entry ! ");
			System.err.println(e.getMessage());
		  }		
		  res.sendRedirect("http://localhost:8080/examples/servlets/Admin/AdminOption.jsp");
		}
		else if (repeat == true)
		{
		  out.println("<html>");
		  out.println("<head>");
		  out.println("<title>");
		  out.println("</title>");
		  out.println("</head>");
		  out.println("<body bgcolor=ivory>");
		  out.println("<h1> Please insert another Login Id !!</h1>");
		  out.println("The Name is :- " + Fname + " " + Lname);
		  out.println("<br>");
		  out.println("The Login is :- " + Login);
		  out.println("<br>");
		  out.println("The Mail ID is :- " + Email);
		  out.println("<br>");
          out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/Admin/AdminOption.jsp\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
		  out.println("</body>");
		  out.println("<html>");
		}		  
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}