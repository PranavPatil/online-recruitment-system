package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class DeleteTest extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt,smt1;
	  String TestName=null;
	  int Test_id=0;
	  ResultSet rs=null,rs1=null;
	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		TestName=req.getParameter("SelectTest");
		
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 smt=con.createStatement();
			 String query1="select test_id from test where name='"
			 +TestName
			 +"'";
			 rs=smt.executeQuery(query1);
			 rs.next();
			 Test_id=Integer.parseInt(rs.getString("test_id"));
			 System.out.println("Test_id found-->"+Test_id);
			 
			 String query2="delete from test_category where test_id="
			 +Test_id;
			 smt.executeUpdate(query2);
			 System.out.println("Test_id deleted-->!!");
			 
			 String query3 = "delete from Test where Test_id="
			 +Test_id;
			 smt.executeUpdate(query3);
			 System.out.println("Test Deleted!!!!!!!!!!!");
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }
	     res.sendRedirect("http://localhost:8080/examples/servlets/Admin/PreTest.jsp");
	}
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}