package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class DeleteQuestion extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt,smt1;
	  String QuesName=null;
	  ResultSet rs=null;
	  int ques_id;
	  String type;
	  int flag=0;
	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		QuesName=req.getParameter("SelectQuestion");
				
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 
			 smt=con.createStatement();
			 String query1 = "select * from questions where question='"
			 +QuesName
			 +"'";
			 
			 rs=smt.executeQuery(query1);
			 
			 if(rs==null)
			 {
				  System.out.println("Error!!  No Questions were found!");
			 }
			 while(rs.next())
			 {   
				ques_id=Integer.parseInt(rs.getString("ques_id"));
				type=rs.getString("type");
			 }
			 
			 if(type.equalsIgnoreCase("MC"))
			 {
			 	smt1=con.createStatement();
			 	String query3 = "delete from MC_Answers where ques_id="
			 	+ques_id;
			 	smt.executeUpdate(query3);
			 	smt1.close();
			 	flag=1;				//indicates that answers of specified Q
			 						//has been deleted
			 }
			 else if(type.equalsIgnoreCase("TF"))
			 {
			 	smt1=con.createStatement();
			 	String query3 = "delete from TF_Answers where ques_id="
			 	+ques_id;
			 	smt.executeUpdate(query3);
			 	smt1.close();
			 	flag=1;
			 }
			 else
			 {
			 	smt1=con.createStatement();
			 	String query3 = "delete from MA_Answers where ques_id="
			 	+ques_id;
			 	smt.executeUpdate(query3);
			 	smt1.close();
			 	flag=1;
			 }
			 
			 if(flag==1)
			 {
			 	smt1=con.createStatement();
			 	String query = "delete from Questions where question='"
				+QuesName
			 	+"'";			 
			 	smt1.executeUpdate(query);
			 	smt1.close();
			 }
			 
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     } 
	     res.sendRedirect("http://localhost:8080/examples/servlets/Admin/PreQuestion.jsp");                      
	}
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}