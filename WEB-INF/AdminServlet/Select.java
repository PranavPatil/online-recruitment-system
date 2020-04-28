package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class Select extends HttpServlet
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
		
    ArrayList User_Id = null;
    int Count = 0,counter = 0,Post_Id = 0;
    long urid = 0;
    String Uid = null,Address = null;
    boolean status = true;
    
	Address = req.getParameter("RequestUrl");
	
	if(Address == null)
	{
	  status = false;
	}
	
	try
	{
	  String st = null;
	  st = req.getParameter("Count");
	  Count = Integer.parseInt(st);
	  
	  if(Count == 0)
	    status = false;
	    
      st = null;
      st = req.getParameter("Post_Id");
      Post_Id = Integer.parseInt(st);
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
         if(Address.equals("ResultView"))
         {
           for(int i = 0;i < User_Id.size();i++)
           {
             smt.executeUpdate("Update Users set Selected = " + Post_Id + ",SelectNo = SelectNo + 1 where User_Id = " + (Long)User_Id.get(i));
             System.out.println("Users Selected = " + i);
           }
         }
         else if(Address.equals("SelectedView"))
         {
           for(int i = 0;i < User_Id.size();i++)
           {
             smt.executeUpdate("Update Users set Selected = 0 where User_Id = " + (Long)User_Id.get(i));
             System.out.println("Users UnSelected = " + i);
           }
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

	out.println("Welcome to Select.java : " + Address);

    if(Address.equals("ResultView"))
      Address = "/examples/servlets/Admin/ResultView.jsp";
    else if(Address.equals("SelectedView"))
      Address = "/examples/servlets/Admin/SelectedView.jsp";
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