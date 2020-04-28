package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class EditAdmin extends HttpServlet
{ 
   ConnectionPool pool=null;
   Statement smt;
   Connection con=null;
   
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
		
		String Fname;
		String Lname;
		String Email;
		String Login;
		String Passwd;
		StringBuffer date=new StringBuffer("ini");
		int Attempt=0;
		
		Fname=req.getParameter("Fname");
		Lname=req.getParameter("Lname");
		Email= req.getParameter("Email");
		Login =req.getParameter("Login");
		Passwd=req.getParameter("Passwd");
				
		System.out.println("Login--"+Login);
					
		try
		{
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			con=pool.getConnection();
  		    smt=con.createStatement();
			 
			 String query = "update Admin set FNAME='"
			 +Fname
			 +"',LNAME='"
			 +Lname
			 +"',EMAIL='"
			 +Email
			 +"',PASSWORD='"
			 +Passwd
			 +"' "
			 +"where login='"
			 +Login
			 +"'";
			 smt.executeUpdate(query); 
		
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }                        
		 		
		res.sendRedirect("http://localhost:8080/examples/servlets/Admin/AdminOption.jsp");
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}