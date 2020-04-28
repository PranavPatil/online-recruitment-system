package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class EditPost extends HttpServlet
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
		
		String fname;
		String lname;
		String email;
		String login;
		String passwd;
	
				
		StringBuffer date=new StringBuffer("ini");	//Becoz Date in Java has format(mm-dd-yy)
												//and of sql is(dd-mm-yy)
		int Attempt=0;
		
		fname=req.getParameter("fname");
		lname=req.getParameter("Surname");
		email= req.getParameter("email");
		login =req.getParameter("login");
		passwd=req.getParameter("passwd");
		
		
		System.out.println("Login--"+login);
					
		try
		{
		 	 //get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 smt=con.createStatement();
			 
			 String query = "update Admin set FNAME='"
			 +fname
			 +"',LNAME='"
			 +lname
			 +"',EMAIL='"
			 +email
			 +"',PASSWORD='"
			 +passwd
			 +"' "
			 +"where login='"
			 +login
			 +"'";
			 smt.executeUpdate(query); 
		
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }                        
		 
		
		res.sendRedirect("http://localhost:8080/examples/servlets/Admin/AdminOption.jsp");

		/*out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hello! </h1>");

		out.println("The First Date is :- " + date);
		out.println("<br>");
		out.println("The Last Name is :- " + fname);
		out.println("<br>");
		out.println("The Login is :- " + login);
		out.println("<br>");
		out.println("The Mail ID is :- " + email);
		out.println("<br>");

		out.println("</body>");
		out.println("<html>");*/
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}