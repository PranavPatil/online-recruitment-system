package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_Category extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt,smt1;
	  String CatName=null;
	  String Cat_Desc=null;
	  int Cat_Id=0;
	  	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		CatName=req.getParameter("SelectCategory");
		System.out.println("Cat_name-->>"+CatName);
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 smt=con.createStatement();
			 
			 String query1 = "select Category_Id,Description from category where Name='"
			 +CatName
			 +"'";
			 
			 ResultSet rs=smt.executeQuery(query1);
		 
			 if(rs==null)
			 {
				  System.out.println("Error!!  No such Category was found!");
			 }
			 while(rs.next())
			 {   
				Cat_Id=Integer.parseInt(rs.getString("Category_Id"));
	   		    Cat_Desc=(String)rs.getString("Description");
			 }
			 System.out.println("Cat_id-->"+Cat_Id);
			 
			 smt1=con.createStatement();
			 String query2 = "Select ques_id,type from questions where Category_Id="
			 +Cat_Id;
			 ResultSet rs1=smt1.executeQuery(query2);
			 
			 if(rs1==null)
			 {
			 	System.out.println("Error!! No Questions were found!!");
			 }
			 int i=0,j=0;
			 while(rs1.next())
			 {
			 	 int temp=Integer.parseInt(rs1.getString("QUES_ID"));
			 	 j++;
			 	 if(rs1.getString("TYPE").equalsIgnoreCase("MC"))
			 	 {
			 	 	smt1.executeUpdate("delete from MC_Answers where QUES_ID="+temp);
			 	 	i++;
			 	 }
			 	 else if(rs1.getString("TYPE").equalsIgnoreCase("TF"))
			 	 {
			 	 	smt1.executeUpdate("delete from TF_Answers where QUES_ID="+temp);
			 	 	i++;
			 	 }
			 	 else if(rs1.getString("TYPE").equalsIgnoreCase("MA"))
			 	 {
			 	 	smt1.executeUpdate("delete from MA_Answers where QUES_ID="+temp);
			 	 	i++;
			 	 }
			 }
			 System.out.println("j-->"+j);
			 System.out.println("Total questions Deleted==>"+i);
			 rs1.close();
			 
			 smt=con.createStatement();
			 String query = "delete from questions where Category_Id="
			 +Cat_Id;
			 smt.executeUpdate(query2);
			 System.out.println("Questions Deleted!!");
					 
			 smt=con.createStatement();
			 String query3 = "delete from Category where Name='"
			 +CatName
			 +"'";
			 smt.executeUpdate(query3);
			 System.out.println("Category Deleted-!!");
			 
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