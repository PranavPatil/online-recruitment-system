package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Delete_User extends HttpServlet
{	  
  public void init(ServletConfig conf)throws ServletException
  {
    super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
    ArrayList User_Id = null;
    int Count = 0,counter = 0;
    long urid = 0;
    String Uid = null;
    String Address = null;
    boolean status = true;
    
    Address = req.getParameter("RequestUrl");
    
	try
	{
	  String st = null;
	  st = req.getParameter("Count");
	  Count = Integer.parseInt(st);
	  
	  if(Count == 0)
	    status = false;
	}
	catch(NumberFormatException ne)
	{
	  status = false;
	}
	
	if(status == true)
	{
      User_Id = new ArrayList();
      Uid = req.getParameter("User_Id" + counter);
      System.out.println("Userid = " + Uid);
      
      while(counter < Count && status != false)
      {
        if(Uid != null)
        {
          try
          {
      	    urid = Long.parseLong(Uid);
      	    User_Id.add(urid);
          }
          catch(NumberFormatException ne)
          {
            status = false;
          }
        }
      
        counter++;
        Uid = req.getParameter("User_Id" + counter);
      }
	}
				
    if(status == true && User_Id.size() != 0)
    {
      Connection con = null;
      Statement smt = null;
      
      try
	  {
		ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
		smt=con.createStatement();
	  }
	  catch(Exception e)
	  {
	 	out.println(e.getMessage());
	  }
	  
	  try
	  {
         for(int i = 0;i < User_Id.size();i++)
         {
           smt.executeUpdate("Delete from Result where User_Id = " + (Long)User_Id.get(i));
           smt.executeUpdate("Delete from TestQuestions where User_Id = " + (Long)User_Id.get(i));
           smt.executeUpdate("Delete from Users where User_Id= " + (Long)User_Id.get(i));
           System.out.println("Users deleted = " + i);
         }
         
         smt.executeUpdate("Commit");
         smt.close();
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
    }
	
	System.out.println("Welcome to Recruit.java : " + Address);
	
    if(Address.equals("SelectedView"))
      Address = "/examples/servlets/Admin/SelectedView.jsp";
    else if(Address.equals("EmpView"))
      Address = "/examples/servlets/Admin/EmpView.jsp";
    else
      Address = "/examples/servlets/Admin/disp.html";

    res.sendRedirect(Address);
  }
	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}