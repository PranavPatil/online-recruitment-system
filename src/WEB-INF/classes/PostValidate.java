package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class PostValidate extends HttpServlet
{
  ConnectionPool pool=null; 
	  	  
  public void init(ServletConfig conf)
  throws ServletException
  {
	super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {  
    Statement smt = null;
	ResultSet rs=null,rs1 = null;
	String path = "C:/Log.dat";
	System.out.println("Path = " + req.getPathInfo());
	System.out.println("Session Id = " + req.getRequestedSessionId());
	System.out.println("Session Valid = " + req.isRequestedSessionIdValid());
	
	HttpSession session=req.getSession(false);			     

    System.out.println("Session Id 1 = " + session.getId());	 
    System.out.println("Session Creation 1 = " + session.getCreationTime());	 	       
            	
	if(session == null)
	{
	  System.out.println("Session is null !!!");	
	}
	
	Log file = new Log(path);             	 		 
	res.setContentType("text/html");
	PrintWriter out=res.getWriter();
	 
	boolean flag = false,Dflag = false;
	int User_Id = 0,Post_Id = 0;
	String Qualification = null,Branch = null;
	int Experience = 0,Mdiff = 0,Ddiff = 0;
	Connection con=null;
    file.writeLog(0,"Validating User's Application");        	 	 
    
    try
    {
      String str = 	req.getParameter("Post_Id");
      System.out.println("str = " + str);
      Post_Id = Integer.parseInt(str);    
      System.out.println("Post_Id = " + Post_Id); 	
    }
    catch (NumberFormatException exp)
    {
      System.out.println("Problem in Post_Id");
      System.err.println(exp.getMessage());
      file.writeLog(User_Id,"Error in Selected Post_Id");        	 	 
    }
    
    if(Post_Id != 0)
    {               
      User_Id = (Integer)session.getAttribute("User_Id");      
      
      if(User_Id == 0)
      {
        System.out.println("Error in Session (User_Id) !!");
      }
      System.out.println(" PostValidate -> User_Id = "+ User_Id);          
    
      try
      {
	    rs = null;
	    pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();
	    smt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
	    
	    rs = smt.executeQuery("Select Months_Between(SysDate,TestDate) from Result where User_Id = " +
       	                        User_Id + " and Post_Id = " + Post_Id);                 
        rs.next();
        Mdiff = rs.getInt(1);
        System.out.println("Months Between :" + Mdiff);
      	
      	if(Mdiff == 0)
      	{
          rs = null;
  	      rs = smt.executeQuery("Select Sysdate - TestDate from Result where User_Id = " +
       	                        User_Id + " and Post_Id = " + Post_Id);                 
          rs.next();
          Ddiff = rs.getInt(1);
          System.out.println("Days Between :" + Ddiff);      	      		
      	}
      	      	
      	if(Mdiff == 0 & Ddiff != 0)
      	{
          if(Mdiff < 6)
          {
      	    Dflag = true;
	        System.out.println("Application for Post Denied");
	        file.writeLog(User_Id,"User Application Denied");  
            out.println("<HTML>");
            out.println("<HEAD>");
            out.println("<TITLE>Prompt</TITLE>");
            out.println("</HEAD>");
            out.println("<BODY bgcolor=\"ivory\">");	   		
            out.print("<h1> Application for Post Denied !! </h1>");	   		
            out.print("<h1> Please try after 6 months !! </h1>");
            out.println("<P>&nbsp;</P> " + "<P>" +
	   	 	            "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/User/PostView.jsp\">" +				 
			  	        "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
		                "</FORM>");          
            out.println("</BODY>");
            out.println("</HTML>");	   			        	
          }    	      		
      	}              
      }	
      catch(Exception e)
      {
	   	 System.err.println(e.getMessage());
	  	 file.writeLog(0,"Error in TestDate, PostValidate");    	
      }
    	     	 
      if(Dflag == false)
      {
	    try
	    {
	      rs = null;
	      smt=con.createStatement();
	      rs=smt.executeQuery("select Qualification,Branch,Experience from Users where User_Id = " + 
	                           User_Id);
          rs.next();
          Qualification = rs.getString(1);
          Branch = rs.getString(2);
          Experience = rs.getInt(3); 
          System.out.println("QualHello  = " + Qualification + "," + Branch + "," + Experience);	  
      
	      smt=con.createStatement();
	      rs1=smt.executeQuery("select Qualification,Branch,Experience from Post where Post_Id = " + 
	                            Post_Id);
                 
          rs1.next();
      
          if(Qualification.equals(rs1.getString(1)) && Branch.equals(rs1.getString(2)))
          {
      	    if(Experience >= rs1.getInt(3))      	
      	    {
      		  flag = true;
            }
          }  
          System.out.println("flag = " + flag);            				
	    }
	    catch(Exception e)
	    {
	  	  System.err.println(e.getMessage());
	  	  file.writeLog(0,"Error in Quaftion retrival, PostValidate");
	    } 
	
	    if(flag == true)
	    {
	      session.setAttribute("Post_Id",Post_Id);
  	      file.writeLog(User_Id,"User Application verified");        	 	 
	      res.sendRedirect("http://localhost:8080/examples/servlet/ChooseTest");	  
	    }                       
        else
        {
  	      file.writeLog(User_Id,"User Application denied");          	 	 
          out.println("<HTML>");
          out.println("<HEAD>");
          out.println("<TITLE>Prompt</TITLE>");
          out.println("</HEAD>");
          out.println("<BODY bgcolor=\"ivory\">");	   		
          out.print("<h1> Application for Post Denied !! </h1>");	   		
          out.print("<h1> You are not eligible for the Post !! </h1>");
          out.println("<P>&nbsp;</P> " + "<P>" +
	  	 	          "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/User/PostView.jsp\">" +				 
				      "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
		              "</FORM>");          
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
      out.println("<TITLE>Prompt</TITLE>");
      out.println("</HEAD>");
      out.println("<BODY bgcolor=\"ivory\">");	   		
      out.print("<h1> Please select the post to apply !! </h1>");	   		
      out.println("<P>&nbsp;</P> " + "<P>" +
	  	 	      "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/User/PostView.jsp\">" +				 
				  "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
		          "</FORM>");          
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