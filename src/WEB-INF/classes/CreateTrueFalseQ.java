package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class CreateTrueFalseQ extends HttpServlet
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
		ResultSet rs;
		String txtQuestion;
		String radio;
		String select;
		String Ans=null;
		int CatId=0;
		String Type="TF";

		
		txtQuestion=req.getParameter("txtQuestion");
		radio=req.getParameter("radio");
		select=req.getParameter("select");
				
		if(radio.equalsIgnoreCase("True"))
		{
			Ans="True";
		}
		else
		{
			Ans="False";
		}
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
			
			rs=smt.executeQuery("Select category_id from category where Name='"
			+select
			+"'");
			rs.next();
			String Temp=rs.getString(1);
			CatId=Integer.parseInt(Temp);
		  	
		  	String query1 = "insert into Questions values(ques_Id.nextval"
			 +",'"
			 +txtQuestion
			 +"',"
			 +CatId
			 +",'"
			 +Type
			 +"')";
			 
			 String query2 = "insert into TF_Answers values(TF_ANS_ID.nextval"
			 +",ques_id.currval,'"
			 +Ans
			 +"')";
			 
		    smt.executeUpdate(query1);
		    smt.executeUpdate(query2);
		  
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		

		/*out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hello! </h1>");

		out.println("The question is :- " + txtQuestion);
		out.println("The Answer is :- " + Ans);
		out.println("<br>");
		out.println("The Desc is :- " + txtDesc);
		out.println("The Radio is :- " + radio);
		out.println("<br>");

		out.println("</body>");
		out.println("<html>");*/
		res.sendRedirect("http://localhost:8080/examples/servlets/Admin/PreQuestion.jsp");
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}