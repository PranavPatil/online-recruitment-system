package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class TestRules extends HttpServlet
{
  public void init(ServletConfig conf)throws ServletException
  {      		
    super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    Connection con = null;		
   	Statement smt = null;
	ResultSet rs=null,rs1 = null;
	res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    HttpSession session=req.getSession(false);     
          
    int Post_Id = 0,Cat_Id = 0,Test_Id = 0,count = 0,val = 0;
    int Ddiff = 0;
    long User_Id = 0;
    String temp = null,query = null;
    String Desc1 = null,Desc2 = null,Desc3 = null;
    boolean Questions = false;     
     
    Log file = new Log("C:/Log.dat");     
     
    out.println("<HTML>");
    out.println("<HEAD>");
    out.println("<META NAME=\"GENERATOR\" Content=\"Microsoft Visual Studio 6.0\">");
    out.println("<TITLE>Test Rules</TITLE>");
    out.println("</HEAD>");
    out.println("<BODY bgcolor=ivory>");

    User_Id = (Long)session.getAttribute("User_Id");
    if(User_Id == 0)
    {
      System.out.println("Error in Session !!");
    }
     
    Post_Id = (Integer)session.getAttribute("Post_Id");
    if(Post_Id == 0)
    {
      System.out.println("Error in Session !!");
      file.writeLog(0,"Error in Post Session");
    }

    temp = req.getParameter("Category");
          
    try
	{
  	  ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	  con=pool.getConnection();
	   
	  Statement state = con.createStatement();
	  rs = state.executeQuery("Select Sysdate - TestDate from Result where User_Id = " +
                               User_Id + " and Post_Id = " + Post_Id);
      rs.next();
      Ddiff = rs.getInt(1);
	}
	catch(Exception e) 
	{
	  System.err.println("Problem in Intialization : "+e.getMessage());
	  e.printStackTrace(out);
	}	  

    if(Ddiff == 0 || Ddiff >= 183)
    {
     try
     {
       Cat_Id = Integer.parseInt(temp);    
       session.setAttribute("Cat_Id",Cat_Id);           
       System.out.println("Cat_Id = " + Cat_Id); 	
     }
     catch (NumberFormatException exp)
     {
     	out.println("Problem in Cat_Id : " + exp.getMessage());
     	exp.printStackTrace();
     	file.writeLog(User_Id,"No Category Selected");        	 	 
     }
    
     file.writeLog(User_Id,"Displaying Test Rules"); 
     try
     {
     	smt = con.createStatement();
     	
     	rs = null;
     	rs = smt.executeQuery("select count(rownum) from Post,TABLE(Post.CatEntry)N where " + 
     	                      "N.Test_Id != 0 and Post_Id = " + Post_Id + 
     	                      " and N.Category_Id = " + Cat_Id);
     	                      
     	rs.next();
     	count = rs.getInt(1);
     	System.out.println("Count" + count);
     	
     }
     catch (SQLException ex)
     {
       ex.printStackTrace();
       out.print("Error : Invalid count : " + ex.getMessage());
       file.writeLog(User_Id,"Error in Getting No of Published Tests");        	 	        
     }

     if(count == 1)
     {
       try
       {
     	  rs = null;     	       	          
     	  rs = smt.executeQuery("select N.Test_Id from Post,TABLE(Post.CatEntry)N where N.Test_Id != 0" +
     	                        " and Post_Id = " + Post_Id +  
     	                        " and N.Category_Id = " + Cat_Id);    
     	  rs.next();
     	  Test_Id = rs.getInt(1);         	  	
       }
       catch (SQLException ex)
       {
         ex.printStackTrace();
         out.print("Error in Published Test : " + ex.getMessage());
         file.writeLog(User_Id,"Error in Getting Published Tests");        	 	                 
       }
       System.out.println("Test_Id = " + Test_Id);
     }
     else if(count == 0)
     {
       Test_Id = 0;
     }
     
     if(Test_Id != 0)
     {
       int QTotal = 0,TType = 0,max = 0;;
       String QType = null;
       try
       {
          rs = null;
          rs = smt.executeQuery("select Total_Ques,Ques_Type,Test_Type " +
                           "from Test where Test_Id = " + Test_Id);     	     		
          rs.next();
          QTotal = rs.getInt("Total_Ques");
          TType = rs.getInt("Test_Type");
          
          if(TType == 1)
             QType = rs.getString("Ques_Type");                    
       }
       catch (SQLException ex)
       {
          ex.printStackTrace();
          out.println("Error in Test Details Retrival : " + ex.getMessage());
          file.writeLog(User_Id,"Error in Test Details Retrival");        	 	                  
       }                                                 

       if(TType == 1)
       {
       	 query = "select count(Ques_Id) from Questions where Category_Id = " +
         	                      Cat_Id + " and Type = '" + QType + "'";
       }
       else
       {
       	 query = "select count(Ques_Id) from Questions where Category_Id = " + 
       	                          Cat_Id;
       }

       try
       {
         rs = null;
         rs = smt.executeQuery(query);
         rs.next();
         max = rs.getInt(1);
         	
         if(max > QTotal)
           Questions = true;
         else
           Questions = false;                        
       }  	
       catch (SQLException ex)
       {
         ex.printStackTrace();
         out.print("Error in Max Comparsion : " + ex.getMessage());
         file.writeLog(User_Id,"Error in Max Comparsion");        	 	                           
       }
       
       System.out.println("Questions = " + Questions);
     }

     if(Cat_Id == 0)   
     { 
       out.print("<h1> Please select one of the Category to Proceed !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"/examples/servlet/ChooseTest\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");
     }
     else if(Test_Id == 0)   
     { 
       out.print("<h1> No Tests Published For this Category !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"/examples/servlet/ChooseTest\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");
     }
     else if(Questions == false)
     {
       out.print("<h1> Questions available for Test are Insufficient !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"/examples/servlet/ChooseTest\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");
     }
     else if(Test_Id != 0 & Questions == true)
     {
	   session.setAttribute("Test_Id",Test_Id);
	   
       out.println("<FORM NAME=\"UserAgreement\" ACTION=\"/examples/servlet/StartTest\">");
       out.println("<TABLE style=\"WIDTH: 760px; HEIGHT: 22px\" cellSpacing=1 cellPadding=5 width=760 ");
       out.println("align=center border=0>");
       out.println("<TR bgcolor=\"#abcdef\" height=7><TD align=\"middle\" colspan=\"2\" ><FONT size=3 face=Arial>");
       out.println("<STRONG>Test Rules</STRONG></FONT></TD></TR>");
       out.println("</TABLE>");
       out.println("<br><br><CENTER><TEXTAREA name=TestDesc rows=14 cols=75 readOnly warp=\"off\">");
       
       try
       {
     	 smt = con.createStatement();
         rs = null;
         rs1 = null;
         System.out.println("To Execute Query");             
         rs = smt.executeQuery("Select Name,Description from Category where Category_Id = " + Cat_Id  );
         smt = con.createStatement();         
         rs1 = smt.executeQuery("Select Description from Test where Test_Id = " + Test_Id);
         rs.next();
         rs1.next();
         Desc1 = rs.getString(1);
         Desc2 = rs.getString(2);
         Desc3 = rs1.getString(1);
         out.println("1 : " + Desc1 + " Test.");
         out.println("");
         out.println("2 : " + Desc2 + ".");
         out.println("");
         out.println("3 : " + Desc3 + ".");
         out.println("");
         out.println("4 : The Selection of the students will be finalized by respective " +
                     "authority and will be bound to all the candidates." );
         out.println("");
         out.println("5 : Candidates will be exempted from the Exam if they try to copy " +
                     "or tried to hamper the System." );
         out.println("");
         out.println("6 : In case of crash candidates will be allowed to reappear for " +
                     "the Exam." );
         out.println("");
         out.println("7 : Fraud candidates will not be entertained at the interview. ");
         out.println("");
         out.println("8 : Only selected candidates will be informed about the interview " + 
                     "through letter or by telephone within 10 days." );
       }
       catch(Exception e)
       {
         e.printStackTrace();
         out.print("Error in Description : " + e.getMessage());
         file.writeLog(User_Id,"Error in Description Display");         
       }

       out.println("</TEXTAREA></CENTER>");
       out.println("<br>");
       out.println("<TABLE style=\"WIDTH: 460px; HEIGHT: 32px\" cellSpacing=1 cellPadding=1 width=460 ");
       out.println("align=center border=0>");
       out.println("<TBODY>");
  
       out.println("<TR>");
       out.println("<TD noWrap align=right><INPUT type=submit value='I Accept' name='IAccept' style=\"WIDTH: 101px; HEIGHT: 26px\" size=26>");
       out.println("</TD></FORM>");
       out.println("<FORM NAME=\"UserAgreement\" ACTION=\"/examples/servlet/ChooseTest\">&nbsp;");
       out.println("<TD noWrap>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=submit value=\"I Decline\" name=IDecline  style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
       out.println("</TD></TR></FORM></TBODY></TABLE>");
       out.println("<br>");
       out.println("</BODY>");
       out.println("</HTML>");     	
       
       try
       {
       	 smt.close();
       }                                                                                                   
       catch (SQLException ex)
       {
         ex.printStackTrace();
         out.print("Error in Closing : " + ex.getMessage());
         file.writeLog(User_Id,"Problem in Closing Conn");         
       }       	      
     }
    }
    else
    {
      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<TITLE>TestRules ErrorPage</TITLE>");
      out.println("</HEAD>");
      out.println("<BODY bgcolor=\"ivory\">");
      out.print("<CENTER><h1> The time limit for the Test is over !! </h1>");
      out.print("<h1> Please try after 6 months !! </h1></CENTER>");
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