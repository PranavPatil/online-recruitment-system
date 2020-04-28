package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_Post extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt;
	  String PostName=null;
	  	  	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		PostName=req.getParameter("SelectPost");
		System.out.println("Post_name-->>"+PostName);
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 smt=con.createStatement();
			 
			 String query = "delete from Post where PostName='"
			 +PostName
			 +"'";
			 smt.executeUpdate(query);
			 System.out.println("Post Deleted-!!");
			 
			 smt.close();
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }                        
	}
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}	