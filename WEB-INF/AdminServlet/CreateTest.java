package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class CreateTest extends HttpServlet
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
	
    String name = null,Description = null,Ques_Type = null;
    int Scrolling = -1,Pass_Score = -1,time = 0,Hide = -1,Test_Type = -1,Total_Ques = -1;
    int Negative = -1;
    boolean status = true;
				
    try
    {
      name = req.getParameter("TestName");
      Description = req.getParameter("TestDesc");
      Scrolling = Integer.parseInt(req.getParameter("Scrolling"));
      Pass_Score = Integer.parseInt(req.getParameter("MinPassScore"));
      Hide = Integer.parseInt(req.getParameter("Hide"));
      time = Integer.parseInt(req.getParameter("TestTime"));
      Test_Type = Integer.parseInt(req.getParameter("Testtype"));
      Total_Ques = Integer.parseInt(req.getParameter("Total_Ques"));
      Negative = Integer.parseInt(req.getParameter("Negative"));

	  if(name == null || Description == null)
	  {
	  	message = "Empty Parameter fields";
	  	status = false;
	  }
	  else if(Description.length() > 500)
	  {
	  	message = "Description length voilated";
	  	status = false;
	  }
	  else if(Scrolling != 0 && Scrolling != 1)
	  {
	  	message = "Scrolling entry voilated";
	  	status = false;
	  }
	  else if(Pass_Score < 0 || Pass_Score > 100)
	  {
	  	message = "Pass Score entry voilated";
	  	status = false;
	  }
	  else if(Negative != 0 && Negative != 1)
	  {
	  	message = "Negative Marking entry voilated";
	  	status = false;
	  }
	  else if(Hide != 0 && Hide != 1)
	  {
	  	message = "Hide Timer entry voilated";
	  	status = false;
	  }
	  else if(time < 0 || time > 180)
	  {
	  	message = "Test Time entry voilated";
	  	status = false;
	  }
	  else if(Test_Type != 0 && Test_Type != 1)
	  {
	  	message = "Test Type entry voilated";
	  	status = false;
	  }
	  else if(Total_Ques < 0)
	  {
	  	message = "Total Questions entry voilated";
	  	status = false;
	  }
	  
	  if(Test_Type == 1)
	  {
        Ques_Type = req.getParameter("Ques_type");

	  	if(Ques_Type != null)
	  	{
	  	  if(!Ques_Type.equals("MC") && !Ques_Type.equals("TF") && !Ques_Type.equals("MA"))
	  	  {
	  	  	message = "Test Type entry voilated";
	  	  	status = false;
	  	  }
	  	}
	  	else
	  	{
	  	  message = "Test Type entry voilated";
	  	  status = false;
	  	}
	  }
	  else if(Test_Type == 0)
	    Ques_Type = "";
    }
    catch(NumberFormatException ex)
    {
      message = "Illegal NumberFormat entry";
      //ex.printStackTrace();
      status = false;
    }
	
    if(status == true)
    {
      int Test_Id = -1;
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
  	  	Test_Id = Integer.parseInt(req.getParameter("Test_Id"));
  	  }
  	  catch(NumberFormatException ex)
  	  {
  	  	Test_Id = -1;
  	  }
  	  
  	  //System.out.println("Test_id = " + Test_Id);
  	  
  	  if(Test_Id > 0)
  	  {
	    try
	    {
	      String str = null;
		  rs = stmt.executeQuery("select Name from Test where Test_Id = " + Test_Id);
		
	  	  rs.next();
		  str = rs.getString("Name");
		
		  if(name.equals(str))
		    check = false;
                
          //System.out.println("Check = " + check);
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());	    	
	    }
  	  }
  	  
  	  if(check == true && Test_Id != 0)
  	  {
  	    try
	    {
		  rs = null;
		  rs = stmt.executeQuery("select Test_Id from Test where Name = '" + name + "'");
		
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
	  
      if(repeat == false && Test_Id != 0)
	  {
	    try
	    {
	  	  String Query = null;
	  		    
 		  if(Test_Id == -1)
 		  {
 		    Query = "Insert into Test values(Test_Id.nextval"
		 	               + ",'"
			               + name
			               + "',"
			               + Scrolling
			               + ","
			               + Pass_Score
			               + ",'"
 			               + Description
			               + "','"
			               + Ques_Type
			               + "',"
			               + time
			               + ","
		 	               + Hide
			               + ","
			               + Test_Type
			               + ","
			               + Total_Ques
			               + ","
			               + Negative
			               + ")";
 		  }
 		  else if(Test_Id > 0)
 		  {

	         Query = "Select N.Category_Id from Post,Table(Post.CatEntry )N where N.Test_Id = " + 
	                  Test_Id + " group by N.Category_Id";
  		     ResultSet rs1=stmt.executeQuery(Query);
          
             while(rs1.next())
             {
               try
               {
                 Statement smt1 = con.createStatement();
                 int Cat_Id = rs1.getInt(2);
            
                 if(Cat_Id != 0)
                 {
                    smt1.execute("Delete from TestQuestions where Category_Id = " + Cat_Id);
                 }
                 smt1.close();
               }
               catch(SQLException ex)
               {
                 out.println("Error in Deleting TestQuestions : " + ex.getMessage());
               }
             }
 		     
 		     Query = "Update Test set Name = '"
			               + name
			               + "',Scrolling = "
			               + Scrolling
			               + ",Pass_Score = "
			               + Pass_Score
			               + ",Description = '"
 			               + Description
			               + "',Ques_Type = '"
			               + Ques_Type
			               + "',Time = "
			               + time
			               + ",Hide = "
		 	               + Hide
			               + ",Test_Type = "
			               + Test_Type
			               + ",Total_Ques = "
			               + Total_Ques
			               + ",Negative = "
			               + Negative
			               + " where Test_Id = " 
			               + Test_Id;
 		  }
 		   
		  stmt.executeUpdate(Query);
		  stmt.executeUpdate("Commit");
		  stmt.close();
		  res.sendRedirect("/examples/servlets/Admin/Test.jsp");
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
        out.write("<TITLE>Test ErrorPage</TITLE>");
        out.write("</HEAD>");
        out.write("<BODY bgcolor=\"ivory\">");
        out.write("<CENTER><h1> Duplicate Test Present, Operation failed !! </h1>");
        out.write("<h1> Please insert another Test name !! </h1></CENTER>");
        out.write("<P>&nbsp;</P> <P>");
	    out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Test.jsp\">");
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
      out.write("<TITLE>Test ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> " + message + ", Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Test.jsp\">");
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