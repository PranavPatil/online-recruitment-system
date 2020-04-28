package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class CreateTest extends HttpServlet
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
		ResultSet rs=null;
		
		
		 int Cat_Id=0;
		 int zero=0;
		 int iTimed=1;
		 int Iscroll=1;
		 int TIME=0;
		 int iTest_Type=0;
		 		 
		 String Cat_Desc=null;
		 String CATEGORY=req.getParameter("categories");    
		 String NAME=req.getParameter("TestName");       
		 String DESCRIPTION=req.getParameter("TestDesc");
		 String TEST_TYPE=req.getParameter("ran");     
		 String TIMED=req.getParameter("TimeBound");
		 if(TIMED.equalsIgnoreCase("No"))
		 {
		 	iTimed=0;
		 	TIME=0;
		 }          
		 else
		 {
		 	TIME=Integer.parseInt(req.getParameter("TestTime"));          
		 }
		 int TOTAL_QUES=Integer.parseInt(req.getParameter("Total_ques"));
		 String SCORLLING=req.getParameter("Scrolling");    
		 int PASS_SCORE=Integer.parseInt(req.getParameter("MinPassScore"));
		 String QUES_TYPE=req.getParameter("Ques_type");    
		 
		 
		 
		 System.out.println("Category is=="+CATEGORY);
		 QUES_TYPE="MC";
		 
		/* if(TIMED.equalsIgnoreCase("No"))
		 {
		 	iTimed=0;
		 	TIME=0;
		 }*/
		 
		 if(TEST_TYPE.equalsIgnoreCase("Random"))
		 {
		 	QUES_TYPE="Null";
		 	iTest_Type=0;
		 }
		 else if(TEST_TYPE.equalsIgnoreCase("Sequential"))
		 {
		 	iTest_Type=1;
		 }
		 
		 if(SCORLLING.equalsIgnoreCase("No"))
		 {
		 	Iscroll=0;
		 }
		 
		 if(QUES_TYPE.equalsIgnoreCase("Multiple Choice"))
		 {
		 	QUES_TYPE="MC";
		 }
		 else if(QUES_TYPE.equalsIgnoreCase("True/False"))
		 {
		 	QUES_TYPE="TF";
		 }
		 else if(QUES_TYPE.equalsIgnoreCase("Multiple Answer"))
		 {
		 	QUES_TYPE="MA";
		 }
		 else
		 {
		 	QUES_TYPE="null";
		 }

				
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
		
			
			//create a statement
			
			 Statement smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 String query1 = "select * from category where Name='"
			 +CATEGORY
			 +"'";
			 
			 rs=smt.executeQuery(query1);
			 rs.next();
			 if(rs==null)
			 {
				  System.out.println("Error!!  No Category was found!");
			 }
			 
			 Cat_Id=Integer.parseInt(rs.getString(1));
			 System.out.println("Category_iddd!"+Cat_Id);
			 
			 String query = "insert into test values(test_Id.nextval"
			 +",'"
			 +NAME
			 +"',"
			 +Iscroll
			 +","
			 +PASS_SCORE
			 +",'"
			 +DESCRIPTION
			 +"','"
			 +QUES_TYPE
			 +"',"
			 +TIME
			 +","
			 +iTimed
			 +","
			 +iTest_Type
			 +","
			 +TOTAL_QUES
			 +")";
	
		  smt.executeUpdate(query);
		  
		  String query2="insert into TEST_CATEGORY values(test_Id.currval,"
		  +Cat_Id
		  +","
		  +zero
		  +")";
		
		  smt.executeUpdate(query2);
		  res.sendRedirect("http://localhost:8080/examples/servlets/Admin/PreTest.jsp");
		}
		catch(Exception e)
		{
			
			e.printStackTrace(out);
		    out.println(e.getMessage());
		
		}
	
		out.println("Test Name :- "+  NAME);       
		out.println("<br>");
 		out.println("Test Discription :- "+  DESCRIPTION);
		out.println("<br>");
		out.println("Test Type :- "+  TEST_TYPE);     
		out.println("<br>");
		out.println("Test Timed or not     :- "+  TIMED);          
		out.println("<br>");
		out.println("Test Time :- "+  TIME);          
		out.println("<br>");
		out.println("Total no of questions :- "+  TOTAL_QUES);
		out.println("<br>");
		out.println("Scrolling :- "+  SCORLLING);    
		out.println("<br>");
		out.println("Passing Score :- "+  PASS_SCORE);
		out.println("<br>");
		out.println("Question Type :- "+  QUES_TYPE);    
		out.println("<br>");
		out.println("Category :- "+  CATEGORY);    
		out.println("<br>");

	/*	out.println("The First Name is :- " + CatName);
		out.println("<br>");
		out.println("The Last Name is :- " + CatDesc);
		out.println("<br>");
		out.println("The Password is :- " + val);
		out.println("<br>");
		out.println("The Mail ID is :- " + MailID);*/
		/*out.println("<br>");
		out.println("</body>");
		out.println("<html>");*/
		
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	   doPost( req,  res);
	}
}