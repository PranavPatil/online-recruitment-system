package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class CreateQuestion extends HttpServlet
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
    Connection con = null;
    String message = "Unknown Error";
    String Type = null,Question = null;
    String Temp = null;
    String Ans1=null,Ans2=null,Ans3=null,Ans4=null;
    int CorrectAns = 0,Cat_Id = 0;
    boolean valid = false;
      
    Type = req.getParameter("Type");
    
    //System.out.println("Type" + Type);	
	
	if(Type != null)
	{
	  Question = req.getParameter("Question");
	  Temp = req.getParameter("SelCategory");
	  
	  if(Temp != null)
	  {
	  	try
	  	{
	  	 Cat_Id = Integer.parseInt(Temp);	
	  	}
	  	catch(NumberFormatException ex)
	  	{
	  	  ex.printStackTrace();
	  	  message = "Invalid Category";
	  	  Cat_Id = 0;
	  	}
	  }	  
	  
 	  if(Question != null && Question.length() < 200 && Cat_Id != 0)
 	  {
 	    if(Type.equals("TF"))
	    {
	      CorrectAns = 0;
	      Temp = null;  	
	  	  Temp = req.getParameter("Ans");
	  	
	  	  if(Temp != null)
	  	  {
	  	    try
	  	    {  CorrectAns = Integer.parseInt(Temp);
	  	    }
	  	    catch(NumberFormatException ex)
	  	    {
	  	      ex.printStackTrace();
	  	      message = "Invalid Correct Ans Format";
	  	    }
	  	    
	  	    if(CorrectAns > 0 && CorrectAns < 3)
	  	    {
               valid = true;
	  	    }
	  	    else
	  	      message = "Invalid Correct Ans Entry";
	  	  }
	    }
	    else
	    {
  	      Ans1 = req.getParameter("Ans1");
 	  	  Ans2 = req.getParameter("Ans2");
 	  	  Ans3 = req.getParameter("Ans3");
 	  	  Ans4 = req.getParameter("Ans4");

	  	  if(Ans1!=null && Ans2!=null && Ans3!=null && Ans4!=null)
	  	  {
 	  	    if(Type.equals("MC"))
	  	    {
	  	      CorrectAns = 0;
	  	      Temp = null;
 	  	      Temp = req.getParameter("Ans");
 	  	      System.out.println("Temp = " + Temp);
	  	  
	  	      if(Temp != null)
	  	      {
	  	        if(Temp.equals("on"))
	  	        {
	  	          out.print("<Center><h1>Radio Button Parameter Passing Error.</h1><Center>");
	  	          CorrectAns = 0;
	  	        }
	  	        else
	  	        {
	  	          try
	  	          { CorrectAns = Integer.parseInt(Temp);
	  	          }
	  	          catch(NumberFormatException ex)
	  	          {
	  	            ex.printStackTrace();
	  	            message = "Invalid Correct Ans Format";
	  	            CorrectAns = 0;
	  	          }
	  	        }
	  	        
	  	        if(CorrectAns > 0 &&  CorrectAns < 5)
	  	        {
 	  	          valid = true;
	  	        }
	  	        else
	  	         message = "Invalid Correct Ans Entry";
	  	      }
	  	    }
	  	    else if(Type.equals("MA"))
	  	    {
	  	     int AnsA=0,AnsB=0,AnsC=0,AnsD=0;
	  	     CorrectAns = -1;
	  	     Temp = null;
	  	  
	  	     try
	  	     {
	  	  	   Temp = req.getParameter("AnsA");
	  	  	
  	  	       if(Temp != null)
	  	       {
	  	        AnsA = Integer.parseInt(Temp);
	  	        
	  	        if(AnsA < -1 || AnsA > 5)
	  	          AnsA = -1;
	  	       }

 	  	       Temp = null;
 	  	       Temp = req.getParameter("AnsB");
	  	  
	  	       if(Temp != null)
	  	       {
	  	        AnsB = Integer.parseInt(Temp);

	  	        if(AnsB < -1 || AnsB > 5)
	  	          AnsB = -1;
	  	       }

 	  	       Temp = null;
 	  	       Temp = req.getParameter("AnsC");
	  	  
	  	       if(Temp != null)
	  	       {
	  	        AnsC = Integer.parseInt(Temp);

	  	        if(AnsC < -1 || AnsC > 5)
	  	          AnsC = -1;
	  	       }

  	  	       Temp = null;
 	  	       Temp = req.getParameter("AnsD");
	  	  
	  	       if(Temp != null)
	  	       {
	  	        AnsD = Integer.parseInt(Temp);

	  	        if(AnsD < -1 || AnsD > 5)
	  	          AnsD = -1;
	  	       }
	  	    
	  	       //System.out.println("params = " + AnsA + "," + AnsB + "," + AnsC + "," + AnsD);
	  	       
	  	       if(AnsA != -1 && AnsB != -1 && AnsC != -1 && AnsD != -1)
	  	       {
	  	       	 CorrectAns = (AnsA * 1000) + (AnsB * 100) + (AnsC * 10) + AnsD;
	  	       }
	  	       else
	  	       {
	  	       	 CorrectAns = -1;
	  	       	 message = "Invalid Correct Ans Entry";
	  	       }
	  	     }
	  	     catch(NumberFormatException ex)
	  	     {
	  	  	   ex.printStackTrace();
	  	       message = "Invalid Correct Ans Format";
	  	  	   CorrectAns = -1;
	  	     }
	  	     
	  	     if(CorrectAns > -1)
	  	     {
	  	        valid = true;
	  	     }
	  	    }	  	  	
	  	  }
	    }
 	  }
	  //System.out.println("Ans = " + CorrectAns);
	}
	
    if(valid == true)
    {
      long Ques_Id = -1;
  	  ResultSet rs = null;
      Statement stmt = null;
      String Address = "/examples/servlets/Admin/Question.jsp";
       
      try
      {
 	    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();
		stmt=con.createStatement();
      }
      catch(Exception ex)
      {
	    ex.printStackTrace();
	    out.print("Error : Conn Failed : " + ex.getMessage());
      }
              
  	  try
  	  {
  	  	Ques_Id = Long.parseLong(req.getParameter("Ques_Id"));
  	  }
  	  catch(Exception ex)
  	  {
  	  	Ques_Id = -1;
  	  }
      
      try
      {
        String Query1 = null,Query2 = null;
         
        if(Ques_Id == -1)
        {
          Ques_Id = 0;
	 
	      Query1 = "Insert into Questions values(Ques_Id.nextval,'" + Question +
		           "'," + Cat_Id + ",'" + Type + "')";
		  
		  stmt.executeUpdate(Query1);
		  rs = null;
		  rs = stmt.executeQuery("Select Ques_Id.Currval from Questions");
		  rs.next();
		  Ques_Id = rs.getLong(1);
		  //System.out.println("Ques_Id = " + Ques_Id);
		 
		  if(Type.equals("MC"))
		  {
		    Query2 = "Insert into MC_Answers values(MC_Ans_Id.nextval,"
		             + Ques_Id + "," + CorrectAns + ",'"
		             + Ans1 + "','" + Ans2 + "','" + Ans3 + "','" 
		             + Ans4 + "')";

		    Address = "/examples/servlets/Admin/MultipleChoice.jsp";
		  }
		  else if(Type.equals("MA"))
		  {
		    Query2 = "Insert into MA_Answers values(MA_Ans_Id.nextval,"
		             + Ques_Id + "," + CorrectAns + ",'"
		             + Ans1 + "','" + Ans2 + "','" + Ans3 + "','" 
		             + Ans4 + "')";		 	

		    Address = "/examples/servlets/Admin/MultipleAnswer.jsp";
		  }
		  else if(Type.equals("TF"))
		  {
		    Query2 = "Insert into TF_Answers values(TF_Ans_Id.nextval,"
		             + Ques_Id + "," + CorrectAns + ")";		 	

		    Address = "/examples/servlets/Admin/TrueOrFalse.jsp";
		  }
        }
        else if(Ques_Id > 0)
        {
		  Query1 = "Update Questions set Question = '" + 
		            Question + "',Category_Id = " + 
		            Cat_Id + " where Ques_Id = " + Ques_Id;
		 
		  stmt.executeUpdate(Query1);
		  //System.out.println("Ques_Id = " + Ques_Id);
		 
		  if(Type.equals("MC"))
		  {
		    Query2 = "Update MC_Answers set Correct_Ans = " + 
		              CorrectAns + ",Ans1 = '" + 
		              Ans1 + "',Ans2 = '" + 
		              Ans2 + "',Ans3 = '" + 
		              Ans3 + "',Ans4 = '" +
		              Ans4 + "' where Ques_Id = " +
		              Ques_Id;
		  }
		  else if(Type.equals("MA"))
		  {
		    Query2 = "Update MA_Answers set Correct_Ans = " + 
		              CorrectAns + ",Ans1 = '" + 
		              Ans1 + "',Ans2 = '" + 
		              Ans2 + "',Ans3 = '" + 
		              Ans3 + "',Ans4 = '" +
		              Ans4 + "' where Ques_Id = " +
		              Ques_Id;
		  }
		  else if(Type.equals("TF"))
		  {
		    Query2 = "Update TF_Answers  set Correct_Ans = " + 
		              CorrectAns + " where Ques_Id = " +
		              Ques_Id;
		  }         	
        }
		
		//System.out.println("Query1 = " + Query2);
		stmt.executeUpdate(Query2);
		stmt.executeUpdate("Commit");
		//rs.close();
		stmt.close();
      }	
      catch(SQLException ex)
      {
	    ex.printStackTrace();
	  	out.print("Error : " + ex.getMessage());
	  	 
		try
		{
    	  Statement smt=con.createStatement();
 		  smt.execute("Rollback");
 		  smt.close();
 		}
 		catch(Exception e)
 	    {
          out.println("Data Restoration Failed : " + e.getMessage());
 	    } 	  	 	  	 
      }
      
      res.sendRedirect(Address);
    }
    else
    {
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>Question ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> " + message + ", Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1><CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Question.jsp\">");
	  out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER>");
	  out.write("</FORM>");
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
