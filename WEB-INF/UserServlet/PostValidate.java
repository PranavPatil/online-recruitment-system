package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class PostValidate extends HttpServlet
{
  public void init(ServletConfig conf)
  throws ServletException
  {
	super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {  
    Statement smt = null;
	ResultSet rs=null;
	String path = "C:/Log.dat";
	
	HttpSession session=req.getSession(false);
            	
	if(session == null)
	{
	  System.out.println("Session is null !!!");	
	}
	
	Log file = new Log(path);             	 		 
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	 
	boolean flag = false,Dflag = false;
	int msge = -1;
	long User_Id = 0;

	int Post_Id = 0,Ddiff = 0;
	Connection con=null;
    
    try
    {
      User_Id = (Long)session.getAttribute("User_Id");
    }
    catch (Exception expn)
    {
      User_Id = 0;
      expn.printStackTrace();
    }

    if(User_Id != 0)
    {
      try
      {
        Post_Id = Integer.parseInt(req.getParameter("Post_Id"));
        System.out.println("Post_Id = " + Post_Id); 	
      }
      catch (NumberFormatException exp)
      {
        Post_Id = 0;
        file.writeLog(User_Id,"Error in Selected Post_Id");
      }
    }
          
    System.out.println(" PostValidate -> User_Id = "+ User_Id);

    if(User_Id != 0 && Post_Id != 0)
    {               
      file.writeLog(User_Id,"Validating User's Application");
      
      try
      {
	    rs = null;
	    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();
	    smt=con.createStatement();
	    
        rs = smt.executeQuery("Select Sysdate - TestDate from Result where User_Id = " +
       	                       User_Id + " and Post_Id = " + Post_Id);
        rs.next();
        Ddiff = rs.getInt(1);
        System.out.println("Days Between :" + Ddiff);      	
      }	
      catch(Exception e)
      {
	  	 file.writeLog(User_Id,"No Entry found in Result");
	  	 Ddiff = 0;
      }
    	     	 
      if(Ddiff < 183 && Ddiff != 0)
      {
         Dflag = true;
	     System.out.println("Application for Post Denied");
	     file.writeLog(User_Id,"User Application Denied");
         out.println("<HTML>");
         out.println("<HEAD>");
         out.println("<TITLE>PostValidate ErrorPage</TITLE>");
         out.println("</HEAD>");
         out.println("<BODY bgcolor=\"ivory\">");
         out.print("<CENTER><h1> Application for Post Denied !! </h1>");
         out.print("<h1> Please try after 6 months !! </h1></CENTER>");
         out.println("<P>&nbsp;</P> " + "<P>");
	   	 out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/PostView.jsp\">");
		 out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	     out.println("</CENTER></FORM>");
         out.println("</BODY>");
         out.println("</HTML>");
      }              
      
      if(Dflag == false)
      {
	    try
	    {
	      String Qualification = null,Branch = null;
	      int Experience = 0,Agelimit = -1;
	      int Selected = -1,Employee = -1;
	      
	      java.util.Date date=null;
          Calendar Cdate = Calendar.getInstance();
	      Calendar Bdate = Calendar.getInstance();

	      rs = null;
	      smt=con.createStatement();
	      rs=smt.executeQuery("Select Date_Of_Birth,Qualification,Branch,Experience,Selected,Employee from Users where User_Id = " + 
	                           User_Id);
          rs.next();

		  date = rs.getDate(1);
		  Bdate.setTime(date);
		  Agelimit = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR);
          Qualification = rs.getString(2);
          Branch = rs.getString(3);
          Experience = rs.getInt(4);
          Selected = rs.getInt(5);
          Employee = rs.getInt(6);
          System.out.println("Validate = " + Qualification + "," + Branch + "," + Experience);	  
      
	      smt=con.createStatement();
	      ResultSet rs1=smt.executeQuery("Select Qualification,Branch,Experience,Agelimit from Post where Post_Id = " + 
	                            Post_Id);
                 
          rs1.next();
      
          if(Employee == 0)
          {
          	if(Selected == 0)
          	{
              if(Qualification.equals(rs1.getString(1)) && Branch.equals(rs1.getString(2)))
              {
      	        if(Experience >= rs1.getInt(3))
      	        {
      		      int num = -1;
      		      num = rs1.getInt(4);
      		      
      		      if(num == 0 || (Agelimit > 17 && num < 60 && Agelimit <= num))
      		      {
      		  	    flag = true;
      		      }
      		      else
      		       msge = 0;
                }
                else
                 msge = 0;
              }
              else
               msge = 0;
          	}
          	else
          	 msge = 1;
          }
          else
           msge = 2;

          System.out.println("flag = " + flag);
	    }
	    catch(Exception e)
	    {
	  	  System.err.println(e.getMessage());
	  	  file.writeLog(0,"Error in Quaftion retrival, PostValidate");
	    } 
	
	    if(flag == true)
	    {
	      if(Ddiff >= 183)
	      {
	      	try
	      	{
	      	  Statement stmt=con.createStatement();
	          stmt.execute("Delete from TestQuestions where User_Id =" + User_Id 
	                       + " and Post_Id = " + Post_Id);
	      	}
	      	catch(SQLException ex)
	      	{
	      	  System.out.println("Error in Removing Previous entries : " + ex.getMessage());
	      	}
	      }
	      
	      session.setAttribute("Post_Id",Post_Id);
  	      file.writeLog(User_Id,"User Application verified");        	 	 
	      res.sendRedirect("/examples/servlet/ChooseTest");	  
	    }                       
        else
        {
  	      file.writeLog(User_Id,"User Application denied");
          out.println("<HTML>");
          out.println("<HEAD>");
          out.println("<TITLE>PostValidate ErrorPage</TITLE>");
          out.println("</HEAD>");
          out.println("<BODY bgcolor=\"ivory\">");
          
          if(msge == 0)
          {
            out.print("<CENTER><h1> Application the for Post Denied !! </h1>");
            out.print("<h1> You are not eligible for the Post !! </h1></CENTER>");
          }
          else if(msge == 1)
          {
            out.print("<CENTER><h1> Application the for Post Denied !! </h1>");
            out.print("<h1> Candidates selected for the Interview cannot Apply !! </h1></CENTER>");
          }
          else if(msge == 2)
          {
            out.print("<CENTER><h1> Application the for Post Denied !! </h1>");
            out.print("<h1> Employees are not allowed to Apply !! </h1></CENTER>");
          }
          else
          {
            out.print("<CENTER><h1> Unexpected Error !! </h1>");
            out.print("<h1> Application could not be verified !! </h1></CENTER>");
          }
          
          out.println("<P>&nbsp;</P> " + "<P>");
	  	  out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/PostView.jsp\">");
		  out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
		  out.println("</CENTER></FORM>");
          out.println("</BODY>");
          out.println("</HTML>");
        }     	
      }	       	
    }
    else
    {
  	  file.writeLog(User_Id,"User Application denied");       	 	 
      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<TITLE>PostValidate ErrorPage</TITLE>");
      out.println("</HEAD>");
      out.println("<BODY bgcolor=\"ivory\">");
      
      if(User_Id == 0)
       out.print("<CENTER><h1> Invalid User, Application Failed !! </h1></CENTER>");
      else if(Post_Id == 0)
       out.print("<CENTER><h1> Please Select the Post to Apply !! </h1></CENTER>");
      else
       out.print("<CENTER><h1> Unknown Error !! </h1></CENTER>");
      
      out.println("<P>&nbsp;</P> " + "<P>");
	  out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/PostView.jsp\">");
	  out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	  out.println("</CENTER></FORM>");
      out.println("</BODY>");
      out.println("</HTML>");	   			  	     	
    }  
  }

  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	 doGet(req,res);
  }
}