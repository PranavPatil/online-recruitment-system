package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Edit_Category extends HttpServlet
{
	  ConnectionPool pool=null; 
	  Connection con=null;
	  PrintWriter out;
	  Statement smt,smt1;
	  String CatName=null;
	  String Cat_Desc=null;
	  String Cat_Id=null;
	  ResultSet rs=null;
	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		res.setContentType("text/html");
		out=res.getWriter();
		
		CatName=req.getParameter("SelectCategory");
		System.out.println("cat"+CatName);
		try
		{
		    //get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 
			 smt=con.createStatement();
			 String query = "select * from category where Name='"
			 +CatName
			 +"'";
			 
			 rs=smt.executeQuery(query);
			 
			 if(rs==null)
			 {
				  System.out.println("Error!!  No such Category was found!");
			 }
			 while(rs.next())
			 {   
				Cat_Id=rs.getString("Category_Id");
	   		    Cat_Desc=rs.getString("DESCRIPTION");
			 }
			 System.out.println("Cat_id::::"+Cat_Id);
			 System.out.println("Cat_Desc::::"+Cat_Desc);
			 
			 out.println("<HTML><HEAD>");
			 out.println("<SCRIPT language=\"javascript\">");
			 out.println("function cancel(form){doc=open(\"http://localhost:8080/examples/servlets/Admin/Category.jsp\",\"right1\");}");
			 out.println("</SCRIPT><TITLE>Edit Category</TITLE></HEAD>");
			 out.println("<BODY bgcolor=ivory><FORM NAME=\"EditCat\" METHOD=Post ACTION=\"http://127.0.0.1:8080/examples/servlet/Edit_Category\">");
			 out.println("<TABLE cellSpacing=1 cellPadding=1 cols=\"2\" width=\"75%\" align=left border=0>");
			 out.println("<TR style=\"BACKGROUND-COLOR: #abcdef\"><TD colspan=2><P align=center><STRONG><FONT face=Verdana size=4>Edit&nbsp;Category</FONT></STRONG></P></TD></TR>");
			 out.println("<TR><TD><FONT face=Verdana size=2><STRONG>Category Code</STRONG></FONT></TD><TD><INPUT size=7 readonly name=CatCode value=\""
			 +Cat_Id
			 +"\"></TD></TR>");
			 out.println("<TR><TD><FONT face=Verdana size=2><STRONG>Category Name</STRONG></FONT></TD><TD><INPUT size=40 name=CatName value=\""
			 +CatName
			 +"\"></TD></TR>");
			 out.println("<TR><TD><FONT face=Verdana size=2><STRONG>Category Description</STRONG></FONT></TD><TD><INPUT size=40 name=CatDesc value=\""
			 +Cat_Desc
			 +"\"></TD></TR>");
			 out.println("<TR><TD colspan=2>&nbsp;</TD></TR>");
			 out.println("<TR>");
    		 out.println("<TD colspan=2 align=\"middle\"><INPUT type=submit value=\"Submit\" name=SubmitCat>&nbsp;<INPUT type=button value=\"Cancel\" name=btnCancel onclick=\"cancel(this.form)\"></TD></TR>");
			 out.println("</TABLE></U></FORM></BODY></HTML>");
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }                        
	}
	
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		out=res.getWriter();
		res.setContentType("text/html");
		Cat_Id=req.getParameter("CatCode");
		CatName=req.getParameter("CatName");
		Cat_Desc=req.getParameter("CatDesc");
		
		System.out.println("Cat_id-->"+Cat_Id);
		System.out.println("Cat_Name-->"+CatName);
		System.out.println("Cat_Desc-->"+Cat_Desc);
		
		out.println("Values Updated!!");
		try
		{
		 	
			 smt1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 String query = "update Category set Name='"
			 +CatName
			 +"',Description='"
			 +Cat_Desc
			 +"' "
			 +"where Category_Id="
			 +Cat_Id;
			 smt1.executeUpdate(query); 
		
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }                        
		 
	}
}	