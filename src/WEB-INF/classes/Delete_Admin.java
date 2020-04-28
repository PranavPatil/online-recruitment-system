package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_Admin extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt;
	  String UserName=null;
	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int Admin_Id = 0;
		String str = null;		
				
        try
    	{
		  str=req.getParameter("SelectAdmin");
  	  	  System.out.println("Admin_name-->>"+str);
          Admin_Id = Integer.parseInt(str);    
          System.out.println("Admin_Id = " + Admin_Id); 	
    	}
    	catch (NumberFormatException exp)
    	{
      	  System.out.println("Problem in Admin_Id");
          System.err.println(exp.getMessage());
        }			
            		
		try
		{
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			con=pool.getConnection();
			smt=con.createStatement();
			
			 String query = "delete from Admin where Admin_Id = " + Admin_Id;
			 
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