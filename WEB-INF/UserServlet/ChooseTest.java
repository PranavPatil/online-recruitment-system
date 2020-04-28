package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class ChooseTest extends HttpServlet
{
  public void init(ServletConfig conf)throws ServletException
  {
    super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    Connection con=null;
	Statement smt = null;
	Statement smt1 = null;
	ResultSet rs=null,rs1 = null;
	int Post_Id = 0,Cat_Id = 0,C_Id = 0;
	int i = 0,val = 0,count = 0,given = 0,Ddiff = 0;
	long User_Id = 0;
	boolean equal = false;
	String path = "C:/Log.dat";
	String PostName = null;
	 
	Log file = new Log(path);
	ArrayList buffer = new ArrayList();
	res.setContentType("text/html");
    PrintWriter out=res.getWriter();

	HttpSession session=req.getSession(false);
              
    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<TITLE>Choose Test</TITLE>");
    out.println("</HEAD>");
    out.println("<BODY bgcolor=ivory>");
    
    try
    {
      User_Id = (Long)session.getAttribute("User_Id");
      Post_Id = (Integer)session.getAttribute("Post_Id");
    }
    catch(NullPointerException ex)
    {
      String str = null;

      if(User_Id == 0)
      {
        str = "Invalid User";
      }
      else if(Post_Id == 0)
      {
        str = "Invalid Post";
        file.writeLog(User_Id,"Error in Post_Id");
      }
	  
	  out.println("<h1>Error : " + str + "</h1>");	
    }
     
    if(Post_Id != 0 & User_Id != 0)
    {
      file.writeLog(User_Id,"Displaying Tests");        	 	 

  	  try
	  {	 	
	    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con=pool.getConnection();
	    smt=con.createStatement();
	    
	    rs = null;
	    rs = smt.executeQuery("Select Sysdate - TestDate from Result where User_Id = " +
       	                       User_Id + " and Post_Id = " + Post_Id);
        rs.next();
        Ddiff = rs.getInt(1);

	    rs = null;	   
	    rs=smt.executeQuery("Select PostName from Post where Post_Id = " + Post_Id);
	    rs.next();
	    PostName = rs.getString(1);

	    if(Ddiff == 0)
	    {
	      rs = null;	   
	      rs=smt.executeQuery("Select N.Category_Id from Result ,TABLE(Result.TestData)N" + 
	                          " where User_Id = " + User_Id + " and Post_Id = " + Post_Id);
	      while(rs.next())
	      {
  	        C_Id = rs.getInt(1);
	      
	        if(C_Id != 0)
	        {
 	          System.out.println("C_Id = " + C_Id);
              buffer.add(C_Id);
	        }
          }
        
          System.out.println("BBuffer = " + buffer);
	    }
 	  }
	  catch(Exception e)
	  {
	 	System.out.println("Problem in Buffer Storage !");
	  	System.err.println(e.getMessage());
        file.writeLog(User_Id,"Error in Getting Attempted Tests");
	  }
	 
	  if(Ddiff == 0 || Ddiff >= 183)
	  {
	    try
	    {
 	      rs1 = null;
 	      smt=con.createStatement();
 	      rs1 = smt.executeQuery("select N.Category_Id from Post,TABLE(Post.CatEntry)N where N.Test_Id != 0 and Post_Id = "
 	                             + Post_Id);
 	    
 	      if(rs1==null)
	      {
		    System.out.println("Query Error");
            file.writeLog(User_Id,"Error in Query");
	      }
	  	   	    
	      while(rs1.next())
	      {
	        if(count == 0)
	   	    {
              out.println("<FORM action=\"/examples/servlet/TestRules\" " +
                          " id=form1 name=form1 method=\"post\">");	 
              out.println("<P><STRONG><FONT >");

              out.println("<TABLE cellSpacing=1 cellPadding=5 width=\"90%\" align=center border=0 id=TABLE1>");
              out.println("<TR vAlign=center align=middle bgColor=black>");
              out.println("<TD colSpan=6 style=\"VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center\">");
              out.println("<FONT face=Verdana size=2><STRONG style=\"COLOR: white; BACKGROUND-COLOR: black\">");
              out.println("Tests for the Post "+ PostName + "</STRONG></FONT>");
              out.println("</TD></TR>");
              out.println("<TR><TD>&nbsp;</TD></TR></TABLE>");

              out.println("<TABLE style=\"WIDTH: 761px;" +
                          " HEIGHT: 27px\" cellSpacing=1 cellPadding=1 width=761 ");
              out.println("bgColor=#abcdef border=0 align=center>");
              out.println("<TR>");
	          out.println("<TD noWrap align=bottom bgColor=#abcdef><CENTER><STRONG>" +
           	              "Choose from below ");
              out.println("the test(s) you wish to take and click submit to proceed ");
              out.println("further:</STRONG></CENTER></TD></TR></TABLE> ");         
              out.println("</FONT></STRONG></P><BR><BR>");
  	          out.println("<UL>");
	   	    }

            Cat_Id = rs1.getInt("Category_Id");
	        System.out.println("ID = " + Cat_Id);
	     
	        i=0;
	        equal = false;
	        
	        while(equal != true & i < buffer.size())
	        {	      
	 	      val = (Integer)buffer.get(i);
	 	      if(val == Cat_Id)
	 	      {
	 	  	    equal = true;
	 	  	    System.out.println("Already Attempted !!");
	 	      }
	 	      i++;
	        }
	    
	        out.println("<LI>");
            out.println("<DIV>");
         
            try
            {           	
	  	      smt1=con.createStatement();
              rs = smt1.executeQuery("select Name from Category where Category_Id = " + 
                                      Cat_Id);
              rs.next();                       
    
              if(equal == true)
              {
               out.println("<INPUT id = \"Cat" + (given + 1) + "\" name=\"Category\" type=\"radio\" value=\"" + 
                       Cat_Id + "\" disabled>&nbsp;&nbsp;<FONT color=blue><STRONG>" + rs.getString("Name"));
               given++;         
              }
              else
              {
               out.println("<INPUT id = \"Cat" + (given + 1) + "\" name=\"Category\" type=\"radio\" value=\"" + 
                        Cat_Id + "\">&nbsp;&nbsp;<FONT color=blue><STRONG>" + rs.getString("Name"));         	
              }                                  
              out.println("</STRONG></FONT><br></A></DIV>");           
            }
            catch(Exception excpt)
            {
              System.out.println("Problem in Category Name Retrival !");
         	  System.err.println(excpt.getMessage());
              file.writeLog(User_Id,"Error in Category Name Retrival");
            }
            count++;
	      }	  
	    }
	    catch(SQLException e)
	    {
	 	  System.out.println("Problem in Category Retrival !");
		  System.out.println(e.getStackTrace());
	      file.writeLog(User_Id,"Error in Category Retrival");
	    }

	    out.println("</UL>");
	    if(count == 0)
	    {	 	
	     out.println("<CENTER><h1>No Tests Published By the Administrator !</h1></CENTER>");	
         out.println("<FORM action=\"/examples/servlets/User/PostView.jsp\" " +
                     " id=form1 name=form1 method=\"post\">");	 
         out.println("<P>&nbsp;</P>");     
   	     out.println("<P><CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK\" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER></P></FORM>");
	     file.writeLog(User_Id,"No Tests Published");
	    }
	    else
	    {
	     if(count == given)
	     {	
	       out.println("<CENTER><h1>All Tests are Already Attempted !</h1></CENTER>");	
	       out.println("</FORM>");
           out.println("<FORM action=\"/examples/servlets/User/PostView.jsp\" " +
                       " id=form1 name=form1 method=\"post\">");	 
           out.println("<P>&nbsp;</P>");
   	       out.println("<P><CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK\" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER></P></FORM>");
	     }	
	     else
	     {
          out.println("<BR>");
          out.println("<TABLE style=\"WIDTH: 460px; HEIGHT: 32px\" cellSpacing=1 cellPadding=1 width=460");
          out.println(" align=center border=0>");
          out.println("<TBODY>");
          out.println("<TR>");
          out.println("<TD noWrap align=right><INPUT type=submit value=\" Submit \" name=TSubmit");
          out.println(" style=\"WIDTH: 101px; HEIGHT: 26px\" size=26></TD></FORM>");
          out.println("<FORM NAME=\"UserBack\" ACTION=\"/examples/servlets/User/PostView.jsp\">&nbsp;");
          out.println("<TD noWrap>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=submit value=\" << Back \"");
          out.println(" name=Back style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></TD></TR></FORM></TBODY></TABLE>");
	      file.writeLog(User_Id,"Tests Displayed To User");   	      	     	
	     }	 	
	    }
	
	    try
	    {
	      rs1.close();	
	      rs.close();
	      smt.close();		 	
	      smt1.close();
	      con.close();		 		   
	    }
	    catch(Exception e)
	    {
	 	 System.out.println("Problem in Closing Resultset !");
		 System.out.println(e.getMessage());
	     file.writeLog(User_Id,"Error in Closing ResultSet");		
	    }
	  }
	  else
	  {
        out.print("<CENTER><h1> The time limit for the Test is over !! </h1>");
        out.print("<h1> Please try after 6 months !! </h1></CENTER>");
        out.println("<P>&nbsp;</P> " + "<P>");
	   	out.println("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/User/PostView.jsp\">");
		out.println("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	    out.println("</CENTER></FORM>");
	  }
    }
    
    out.println("</BODY>");
    out.println("</HTML>");
  }
 	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    doGet(req,res);     	
  }
}	