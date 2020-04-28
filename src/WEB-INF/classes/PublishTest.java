package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class PublishTest extends HttpServlet
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
		
		 String TestName;
		 int Test_Id=0;
		 		 		 
		 TestName=req.getParameter("SelectTest");
		 System.out.println("TestName--!"+TestName);

		if(TestName==null)
		{
			out.println("Test Name :- ");       
			out.println("<br>");
	 		out.println("Not Valid !!");
		}		
		else
		{
			try
			{
					//get a refrence to the connectionPool from the global
					// servlet context 
					
					pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
					
					//GET A CONNECTION FROM A CONNECTION POOL
					con=pool.getConnection();
					
					
					//create a statement
					
					Statement smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					String query1 = "select Test_id from Test where Name='"
					+TestName
					+"'";
					rs=smt.executeQuery(query1);
					rs.next();
					if(rs==null)
					{
					  System.out.println("Error!!  No Category was found!");
					}
									
					Test_Id=Integer.parseInt(rs.getString(1));
					System.out.println("Test_iddd!"+Test_Id);
					
					String query2 = "Update Test_category set PUBLISH=1 where Test_id="
					+Test_Id;
					
					
					smt.executeUpdate(query2);
					System.out.println("Test Published!");
			}
			catch(Exception e)
			{
				
				e.printStackTrace(out);
			    out.println(e.getMessage());
			
			}
			res.sendRedirect("http://localhost:8080/examples/servlets/Admin/PreTest.jsp");
		}
	
		/*out.println("Test Name :- "+  NAME);       
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
		out.println("<br>");*/

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