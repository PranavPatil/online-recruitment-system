package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_User extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt,smt1;
	  String UserName=null;
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
		int User_Id = 0;
		String str = null;
				
		try
		{
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			con=pool.getConnection();
			smt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
            try
    	    {
      		  str = req.getParameter("SelectUser");
  	  		  System.out.println("Ques_name-->>"+str);
              User_Id = Integer.parseInt(str);    
              System.out.println("User_Id = " + User_Id); 	
    		}
    		catch (NumberFormatException exp)
    		{
      		  System.out.println("Problem in User_Id");
              System.err.println(exp.getMessage());
            }			
			
       	 	smt.executeUpdate("delete Table(select TestData from Result where User_Id = " + 
       	 	                    User_Id + ")N");
       	 	                    
       	    smt.executeUpdate("delete from Result where User_Id = " + User_Id);       						
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     } 
	     
	     try
	     {
 	        smt.executeUpdate("delete Table(select TestEntry from TestQuestions where User_Id = " +
	                           User_Id + ")N"); 	     	
       	    smt.executeUpdate("delete from TestQuestions where User_Id = " + User_Id);       						
	     }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     } 

	     
	     try
	     {
	     	String query = "delete from Users where User_Id= " + User_Id;			 
			smt.executeUpdate(query);	     	
	     }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     } 	     

	     res.sendRedirect("http://localhost:8080/examples/servlets/Admin/UserUpdate.jsp");
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}