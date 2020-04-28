package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class StartTest extends HttpServlet
{
	ConnectionPool pool=null; 
	PrintWriter out;
	String user=null,id=null;

	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	 int Test_Id = 0,User_Id = 0;	
	 int time=0,Timed=0,Test_type=0,Total_Ques=0;
	 String Ques_Type = null,STimed = null;
	 Connection con=null;	
	 Statement smt = null;
	 ResultSet rs=null;
	 res.setContentType("text/html");
     PrintWriter out=res.getWriter();
     Log file = new Log("C:/Log.dat");     
     
     HttpSession session=req.getSession(false);
     User_Id = (Integer)session.getAttribute("User_Id");     
     Test_Id = (Integer)session.getAttribute("Test_Id");
     
     if(Test_Id == 0 | User_Id == 0)
     {
     	System.out.println("Error in Session !!");
     	file.writeLog(0,"Error in User Session");        	 	 
     }
     System.out.println("StartTest -> Test_Id = "+ Test_Id);        	 	 	  	

     out.println("<HTML>");
     out.println("<HEAD>");
     out.println("<META NAME=\"GENERATOR\" Content=\"Microsoft Visual Studio 6.0\">");
     out.println("<TITLE>Start Test Warning</TITLE>");
     out.println("</HEAD>");
     out.println("<BODY bgcolor=ivory>");
     out.println("<P>");
     out.println("<TABLE cellSpacing=0 cellPadding=15 width=\"547\" align=\"left\" " +
                 "bordercolor=\"blue\" bgColor=\"#abcdef\" border=1 style=\"WIDTH: 547px; HEIGHT: 232px\" >");
     out.println("<TR><TD><FONT face=Arial><FONT size=2><STRONG>");
     out.println("<FONT ");
     out.println(">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
     out.println("      Start Test Warning</FONT></U></STRONG>");
     out.println("<br></STRONG></FONT></FONT><br>");
     file.writeLog(User_Id,"Displaying Test Description");        	 	 			     
	 try
	 {
	    pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();	 	
	    smt=con.createStatement();
	    rs = smt.executeQuery("select * " +
	                          "from Test where Test_Id = " +
	 	                        Test_Id);
	 	
	 	rs.next();
	 	Timed = rs.getInt("Timed");
	 	
	 	if(Timed == 1)	 	
	 	{
	 	  time = rs.getInt("Time");
	 	  STimed = "" + time + " Minutes";	 		
	 	}
	 	else
	 	  STimed = "Infinite Time";
	 	  	 	  
	 	Total_Ques = rs.getInt("Total_Ques");
	 	
	 	Test_type = rs.getInt("Test_type");
	 	
	 	if( Test_type == 0 )
	 	{
	 	   Ques_Type  = rs.getString("Ques_Type");	
	 	   if(Ques_Type.equals("MC"))
	 	      Ques_Type = "Multiple Choice";
	 	   else if(Ques_Type.equals("TF"))
	 	      Ques_Type = "True or False";
	 	   else
	 	      Ques_Type = "Random";       
	 	}
	    else 
	     Ques_Type = "Random"; 

		smt.close();	
	 }
	 catch(Exception e)
	 {
	 		System.out.println("Problem in Closing !!");
			System.out.println(e.getMessage());
     	    file.writeLog(User_Id,"Error in Test Description");        	 	 			
	 }
     
     out.println("You have read and accepted to the terms and conditions related for this test.");
     out.println("The test comprises of " + Total_Ques + " " + Ques_Type + " questions to be completed in " + 
                   STimed);
     out.println("We wish you good luck. You may proceed now by pressing the 'Start Test' button " +
                 "below.<br><br>");

     out.println("<FORM name=\"StartTest\" action=\"http://localhost:8080/examples/servlet/TestPage\">");
     out.println("<center><INPUT type=submit value=\"Start Test\" name=StartTest></center>");
	 out.println("</FORM>");
	 out.println("</TD></TR>");
	 out.println("</TABLE>");  
	 out.println("<BR>");
	 out.println("</P>");
	 out.println("</BODY></HTML>");
	 
    }

	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
      doGet(req,res);
	}
}