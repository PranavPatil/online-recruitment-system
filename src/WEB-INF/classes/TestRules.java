package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class TestRules extends HttpServlet
{
	ConnectionPool pool=null; 
	Connection con = null;	
	PrintWriter out;	
	  
	public void init(ServletConfig conf)throws ServletException
	{      		
	  super.init(conf);
	  try
	  {
  	    pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
		con=pool.getConnection();		       
	  }
	  catch(Exception e) 
	  {
		System.err.println("Problem in Intialization : "+e.getMessage());
		e.printStackTrace(out);
	  }	  
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	 Statement smt = null;
	 ResultSet rs=null,rs1 = null;
	 res.setContentType("text/html");
     PrintWriter out=res.getWriter();
     HttpSession session=req.getSession(false);     
          
     int Cat_Id = 0,Test_Id = 0,count = 0,val = 0;
     int User_Id = 0,Post_Id = 0;
     String temp = null,query = null;
     String Desc1 = null,Desc2 = null;      
     boolean Questions = false;     
     
     Log file = new Log("C:/Log.dat");     
     
     out.println("<HTML>");
     out.println("<HEAD>");
     out.println("<META NAME=\"GENERATOR\" Content=\"Microsoft Visual Studio 6.0\">");
     out.println("<TITLE></TITLE>");
     out.println("</HEAD>");
     out.println("<BODY bgcolor=ivory>");

     User_Id = (Integer)session.getAttribute("User_Id");
     if(User_Id == 0)
     {
     	System.out.println("Error in Session !!");
     	file.writeLog(0,"Error in User Session");        	 	 
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
       Cat_Id = Integer.parseInt(temp);    
       session.setAttribute("Cat_Id",Cat_Id);           
       System.out.println("Cat_Id = " + Cat_Id); 	
     }
     catch (NumberFormatException exp)
     {
     	System.out.println("Problem in Cat_Id");
     	System.err.println(exp.getMessage());
     	file.writeLog(User_Id,"No Category Selected");        	 	 
     }
    
     file.writeLog(User_Id,"Displaying Test Rules"); 
     try
     {
     	smt = con.createStatement();
     	rs = smt.executeQuery("select count(rownum) from Post,TABLE(Post.CatEntry)N where " + 
     	                      "N.Test_Id != 0 and Post_Id = " + Post_Id + 
     	                      "and N.Category_Id = " + Cat_Id);
     	                      
     	rs.next();
     	count = rs.getInt(1);
     	System.out.println("Count" + count);
     	
     }
     catch (SQLException ex)
     {
       System.out.println("Problem in Count !");      
       ex.printStackTrace(out);
       file.writeLog(User_Id,"Error in Getting No of Published Tests");        	 	        
     }

     if(count != 0)
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
         System.out.println("Problem in Published Test !" + ex);      
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
          System.out.println("Problem in Test Details Retrival!" + ex);      
          System.out.println(ex.getMessage());
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
         System.out.println("Problem in Max Comparsion !" + ex);      
         file.writeLog(User_Id,"Error in Max Comparsion");        	 	                           
       }
       
       System.out.println("Questions = " + Questions);
     }

     if(Cat_Id == 0)   
     { 
       out.print("<h1> Please select one of the Category to Proceed !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlet/ChooseTest\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");
     }
     else if(Test_Id == 0)   
     { 
       out.print("<h1> No Tests Published For this Category !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlet/ChooseTest\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");
     }
     else if(Questions == false)
     {
       out.print("<h1> Questions available for Test are Insufficient !! </h1>");
       out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlet/ChooseTest\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
       out.println("</BODY>");
       out.println("</HTML>");
     }
     else if(Test_Id != 0 & Questions == true)
     {
	   session.setAttribute("Test_Id",Test_Id);
	   
       out.println("<P>&nbsp;</P>");
       out.println("<FORM NAME=\"UserAgreement\" ACTION=\"http://localhost:8080/examples/servlet/StartTest\">");
       out.println("<TABLE style=\"WIDTH: 460px; HEIGHT: 22px\" cellSpacing=1 cellPadding=1 width=460 ");
       out.println("align=left border=0>");
       out.println("<TR ");
       out.println("bgcolor=\"#abcdef\" height=7><TD align=\"middle\" colspan=\"2\" ><FONT size=2 face=Arial><STRONG>Test ");
       out.println("Rules</STRONG></FONT> </TD></TR>");
       out.println("</TABLE>");
       out.println("<br><br><TEXTAREA name=TestDesc rows=10 cols=55 warp=\"off\">");
       
       try
       {
     	 smt = con.createStatement();
         rs = null;
         rs1 = null;
         System.out.println("To Execute Query");             
         rs = smt.executeQuery("Select Description from Category where Category_Id = " + Cat_Id  );
         smt = con.createStatement();         
         rs1 = smt.executeQuery("Select Description from Test where Test_Id = " + Test_Id);
         rs.next();
         rs1.next();
         Desc1 = rs.getString(1);
         Desc2 = rs1.getString(1);
         out.println("1 :" + Desc1);          
         out.println("");         
         out.println("2 :" + Desc2); 
         out.println("");         
         out.println("3 : The Selection of the students will be finalized by respective " +
                     "authority and will be bound to all the candidates." );                  
         out.println("");                     
         out.println("4 : Candidates will be exempted from the Exam if they try to copy " +
                     "or tried to hamper the System." );                  
         out.println("");                     
         out.println("5 : In case of crash candidates will be allowed to reappear for " +
                     "the Exam." );                  
         out.println("");                     
         out.println("6 : Fraud candidates will not be entertained at the interview. "); 
         out.println("");
         out.println("7 : Only selected candidates will be informed about the interview " + 
                     "through letter or by telephone within 10 days." );                  
       }
       catch(Exception e)
       {
       	 System.out.println("Problem in Description !");
         System.out.println(e.getMessage());
         System.out.println(e.getStackTrace());         
         file.writeLog(User_Id,"Error in Description Display");         
       }

       out.println("</TEXTAREA>");
       out.println("<br>");
       out.println("<TABLE style=\"WIDTH: 460px; HEIGHT: 32px\" cellSpacing=1 cellPadding=1 width=460 ");
       out.println("align=left border=0>");
       out.println("<TBODY>");
  
       out.println("<TR>");
       out.println("<TD noWrap align=right><INPUT type=submit value=\"I Accept\" name=IAccept></TD>");
       out.println("</FORM>");
       out.println("<FORM NAME=\"UserAgreement\" ACTION=\"http://localhost:8080/examples/servlet/ChooseTest\">&nbsp;");
       out.println("<TD   noWrap><INPUT type=submit value=\"I Decline\" name=IDecline></TD></TR>");
       out.println("</FORM></TBODY></TABLE>");
       out.println("<br>");
       out.println("</BODY>");
       out.println("</HTML>");     	
       
       try
       {
       	 smt.close();
       }                                                                                                   
       catch (SQLException ex)
       {
         System.out.println("Problem in Closing !" + ex);      
         file.writeLog(User_Id,"Problem in Closing Conn");         
       }       	      
     }    			          	         
 	}
 	
 	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	   doGet(req,res);
	}
}