package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class EditQuestion extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt;
	  ResultSet rs=null;
	  String Question=null;
	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		out=res.getWriter();
		
		Question=req.getParameter("SelectQuestion");
		//System.out.println("Question-->"+Question);
		//System.out.println("cat"+CatName);
		if(Question.equalsIgnoreCase("pls select category and press Go"));
		{
			out.println("<html><body bgcolor=ivory><form name=form action=http://localhost:8080/examples/servlets/Admin/PreQuestion.jsp>");
			out.println("<center><strong>pls select category and press Go</strong>");
			out.println("<input type=submit value=Retry name=submit>");
			out.println("</form></body></html>");
		}
		
		try
		{
		    //get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 
			 smt=con.createStatement();
			 String query = "select Type from questions where QUESTION='"
			 +Question
			 +"'";
			 
			 rs=smt.executeQuery(query);
			 while(rs.next())
			 {
			 	System.out.println("Type-->"+rs.getString("TYPE"));
			 }
			 
			 if(rs==null)
			 {
				  out.println("<html><body bgcolor=ivory><form name=form action=http://localhost:8080/examples/servlets/Admin/Question.jsp>");
				  out.println("<center>Wrong Value!!");
				  out.println("<input type=submit value=GoBack! name=submit>");
				  out.println("</form></body></html>");
			 }
			 while(rs.next())
			 {   
				String temp=rs.getString("TYPE");
				if(temp.equalsIgnoreCase("MC"))
				{
					forwadMC(req,res);
				}
				else if(temp.equalsIgnoreCase("TF"))
				{
					forwadTF(req,res);
				}
				else if(temp.equalsIgnoreCase("MA"))
				{
					forwadMA(req,res);
				}
			 }
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
	
	protected void forwadMC(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
	 String forwadStr="EditMCQuestion.jsp";
	 System.out.println("Forward to MULTIPLEChoice");
	 RequestDispatcher dispatcher=req.getRequestDispatcher(forwadStr);
	 dispatcher.forward(req,res);
	 
	}
	protected void forwadTF(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
	 String forwadStr="EditMCQuestion.jsp";
	 System.out.println("Forward to True/false");
	 RequestDispatcher dispatcher=req.getRequestDispatcher(forwadStr);
	 dispatcher.forward(req,res);
	 
	}
	 protected void forwadMA(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
	 String forwadStr="EditMCQuestion.jsp";
	 System.out.println("Forward to MULTIPLEANSWERS");
	 RequestDispatcher dispatcher=req.getRequestDispatcher(forwadStr);
	 dispatcher.forward(req,res);
	 
	}


}	