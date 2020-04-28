package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class CreateCategory extends HttpServlet
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
	Connection con=null;
    String message = "Unknown Error";
	String CatName = null;
    String CatDesc = null;
	boolean status = true;
	
	CatName=req.getParameter("CatName");
	CatDesc=req.getParameter("CatDesc");
	
	if(CatName == null || CatDesc == null)
	{
	  message = "Empty Parameter fields";
	  status = false;
	}
	else if(CatDesc.length() > 500)
	{
	  message = "Description length voilated";
	  status = false;
	}
	
	if(status == true)
	{
	  int Cat_Id = -1;
  	  boolean repeat = false,check = true;
  	  int count = 0;
  	  ResultSet rs = null;
      Statement stmt = null;
	  
	  try
      {
      	ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
      	con=pool.getConnection();
      	stmt=con.createStatement();
      }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	    out.println(e.getMessage());
	  }
	    	  
  	  try
  	  {
  	  	Cat_Id = Integer.parseInt(req.getParameter("Cat_Id"));
  	  }
  	  catch(NumberFormatException ex)
  	  {
  	  	Cat_Id = -1;
  	  }
  	  
  	  //System.out.println("Cat_id = " + Cat_Id);
  	  
  	  if(Cat_Id > 0)
  	  {
	    try
	    {
	      String str = null;
		  rs = stmt.executeQuery("select Name from Category where Category_Id = " + Cat_Id);
		
	  	  rs.next();
		  str = rs.getString("Name");
		
		  if(CatName.equals(str))
		    check = false;
                
          //System.out.println("Check = " + check);
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());	    	
	    }
  	  }
  	  
  	  if(check == true && Cat_Id != 0)
  	  {
  	    try
	    {
		  rs = null;
		  rs = stmt.executeQuery("select Category_Id from Category where Name = '" + CatName + "'");
		
		  while(rs.next())
		    count++;
        
          //System.out.println("Count = " + count);
		
		  if(count == 0)
		    repeat = false;
		  else if(count > 0)
		    repeat = true;
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      out.println(e.getMessage());
	    }	    	  	  	
  	  }
	  
      if(repeat == false && Cat_Id != 0)
	  {
	    try
	    {
	  	  String Query = null;
	  		    
 		  if(Cat_Id == -1)
 		  {
 		    Query = "Insert into Category values(Category_Id.nextval"
		 	               + ",'"
			               + CatName
			               + "','"
 			               + CatDesc
			               + "')";
 		  }
 		  else if(Cat_Id > 0)
 		  {
 		    Query = "Update Category set Name = '"
			               + CatName
			               + "',Description = '"
 			               + CatDesc
			               + "' where Category_Id = " 
			               + Cat_Id; 		  	
 		  }
 		   
		  stmt.executeUpdate(Query);
		  stmt.executeUpdate("Commit");
		  stmt.close();
		  res.sendRedirect("/examples/servlets/Admin/Category.jsp");
		}
 	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());
	      
	      try
          {
            Statement smt = con.createStatement();
            smt.executeUpdate("Rollback");
            smt.close();        	
          }
          catch(Exception ee)
          {
            out.print("Error : System restoration failed" + ee.getMessage());
          }
	    }
	  } // else if (Test_Id == 0) out.write("Error : Test_Id = 0 !");
 	  else
	  {
        out.write("<HTML>");
        out.write("<HEAD>");
        out.write("<TITLE>Category ErrorPage</TITLE>");
        out.write("</HEAD>");
        out.write("<BODY bgcolor=\"ivory\">");
        out.write("<CENTER><h1> Duplicate Category Present, Operation failed !! </h1>");
        out.write("<h1> Please insert another Category name !! </h1></CENTER>");
        out.write("<P>&nbsp;</P> <P>");
	    out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Category.jsp\">");
	    out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	    out.write("</CENTER></FORM>");
        out.write("</BODY>");
        out.write("</HTML>");
	  }
    }
    else
    {
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>Category ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> " + message + ", Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Category.jsp\">");
	  out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER>");
	  out.write("</CENTER></FORM>");
      out.write("</BODY>");
      out.write("</HTML>");
    }
  }

  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}