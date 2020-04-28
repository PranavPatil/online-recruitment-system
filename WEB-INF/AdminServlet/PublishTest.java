package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class PublishTest extends HttpServlet
{ 
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
	boolean status = true;
    int Post_Id = 0,counter = 0;    
	ArrayList Cat_Id = null,Test_Id = null;
	
	try
	{
	  String st = null;
	  st = req.getParameter("Post_Id");
	  Post_Id = Integer.parseInt(st);
	}
	catch(NumberFormatException ne)
	{
	  status = false;
	}
	
	if(status == true)
	{
      int m = 0,n = 0;
      String Cid = null,Tid = null;
      
      Cat_Id = new ArrayList();
      Test_Id = new ArrayList();
      
      Cid = req.getParameter("Cat_Id" + counter);
      Tid = req.getParameter("Test_Id" + counter);
      
      while(Cid != null && Tid != null && status != false)
      {
      	try
      	{
      	  m = Integer.parseInt(Cid);
      	  n = Integer.parseInt(Tid);
      	  Cat_Id.add(m);
      	  Test_Id.add(n);
      	}
      	catch(NumberFormatException ne)
      	{
      	  status = false;
      	}

        counter++;
        Cid = req.getParameter("Cat_Id" + counter);
        Tid = req.getParameter("Test_Id" + counter);
      }
    }  
      
	if(status == true)
	{
	  Connection con=null;
	  
	  try
	  {
		ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
		Statement smt=con.createStatement();

        for(int i = 0;i < counter;i++)
        {
          String Query = "Update Table(select CatEntry from Post where Post_Id = " +
                          Post_Id + ")N set N.Test_Id = " + (Integer)Test_Id.get(i) +
                         " where N.Category_Id = " + (Integer)Cat_Id.get(i);

		  smt.executeUpdate(Query);
        }
        
        smt.executeUpdate("Commit");
        smt.close();
        System.out.println("Test Published = " + counter);
	  }
	  catch(Exception e)
	  {
		e.printStackTrace();
		out.println(e.getMessage());
		
		try
        {
          Statement stmt = con.createStatement();
          stmt.executeUpdate("Rollback");
          stmt.close();        	
        }
        catch(Exception ee)
        {
          out.print("Error : System restoration failed" + ee.getMessage());
        }
	  }
	  
	  res.sendRedirect("/examples/servlets/Admin/Publish.jsp");				
	}
	else
	{
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>Publish ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> Invalid Input, Operation failed !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Publish.jsp\">");
	  out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	  out.write("</CENTER></FORM>");
      out.write("</BODY>");
      out.write("</HTML>");
	}
  }
	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    doPost( req,  res);
  }
}