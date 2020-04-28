package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;



public class CreateCategory extends HttpServlet
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
		String CatName;
	    String CatDesc;
		int val=0;
		
		CatName=req.getParameter("CatName");
		CatDesc=req.getParameter("CatDesc");

		Connection con=null;
		
		
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			
			//create a statement
			Statement smt=con.createStatement();
			
			String query = "insert into category values(Category_Id.nextval"
			 +",'"
			 +CatName
			 +"','"
			 +CatDesc
			 +"')";
			 
			 
		  smt.executeUpdate(query);
		  
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
			System.out.println("OK");
		}
		
		

		/*out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hello! </h1>");

		out.println("The First Name is :- " + CatName);
		out.println("<br>");
		out.println("The Last Name is :- " + CatDesc);
		out.println("<br>");
		out.println("The Password is :- " + val);
		//out.println("<br>");
		//out.println("The Mail ID is :- " + MailID);
		out.println("<br>");

		out.println("</body>");
		out.println("<html>");*/
		res.sendRedirect("http://localhost:8080/examples/servlets/Admin/Category.jsp");
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}