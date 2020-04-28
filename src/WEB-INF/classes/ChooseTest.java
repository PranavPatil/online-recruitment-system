package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class ChooseTest extends HttpServlet
{
    ConnectionPool pool=null; 
    String user=null,id=null;
	  	  
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
	 ResultSet rs=null,rs1 = null,rs2 = null;
	 int Cat_Id = 0,User_Id = 0,i = 0,val = 0,count = 0;
	 int given = 0,Post_Id = 0;
	 int Rcat_Id = 0,C_Id = 0,Mdiff = 0;	 
	 boolean equal = false;
	 String path = "C:/Log.dat";
	 
	 Log file = new Log(path);             	 		 	 
	 ArrayList buffer = new ArrayList();
	 res.setContentType("text/html");
     PrintWriter out=res.getWriter();

	 HttpSession session=req.getSession(false);	 
	 System.out.println("welcome to choosetest -> Session = " + session.getId());		 	 
	 	 
     Post_Id = (Integer)session.getAttribute("Post_Id");
     if(Post_Id == 0)
     {
       System.out.println("Error in Session (Post_Id) !!");
     }
     System.out.println("  Post_Id = "+ Post_Id);
	 	 
	 System.out.println("Session Id 2 = " + session.getId());	 
          
     User_Id = (Integer)session.getAttribute("User_Id");     
     if(User_Id == 0)
     {
       System.out.println("Error in Session !!");
       file.writeLog(0,"Error in User Session");        	 	 
     }	 
     System.out.println("Choose Test -> User_Id = "+ User_Id);              
              
     out.println("<HTML>");
     out.println("<HEAD>");
     out.println("<TITLE>Choose Test</TITLE>");
     out.println("</HEAD>");
     out.println("<BODY bgcolor=ivory>");
     
     file.writeLog(User_Id,"Displaying Tests");        	 	 

     try
     {
	   pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	   con=pool.getConnection();
	   smt=con.createStatement();

	   rs = smt.executeQuery("Select Months_Between(SysDate,TestDate) from Result where User_Id = " +
                               User_Id + " and Post_Id = " + Post_Id);                 
       rs.next();
       Mdiff = rs.getInt(1);
       System.out.println("Month Difference = "+ Mdiff);     	
     }
     catch(Exception e)
     {
	   System.err.println(e.getMessage());
	   file.writeLog(0,"Error in TestDate, ChooseTest");    	
     }     
 
  	 try
	 {	 	
	  rs = null; 
	  smt1 = con.createStatement();	  	   	   
	   	  
	  if(Mdiff < 6)
	  {
	    rs=smt.executeQuery("select N.Category_Id from Result ,TABLE(Result.TestData)N" + 
	                      " where User_Id = " + User_Id + " and Post_Id = " + Post_Id);
	    if(rs==null)
	    {
		  System.out.println("Query Error");
	    }
	  
	    while(rs.next())
	    {  
	      rs2 = null;
  	      Rcat_Id = rs.getInt(1);
	      System.out.println("Rcat_Id = " + Rcat_Id);
	     
	      if(Rcat_Id != 0)
	      {
	        rs2 = smt1.executeQuery("select N.Category_Id from Post,TABLE(Post.CatEntry)N " + 
	                               "where N.Category_Id = " + Rcat_Id); 
	        rs2.next();                     
	        C_Id = rs2.getInt(1);    
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
	                         
	 try
	 {
 	   rs1 = null; 	   
 	   rs1 = smt.executeQuery("select N.Category_Id from Post,TABLE(Post.CatEntry)N where N.Test_Id != 0 and Post_Id = " + Post_Id);
 	   if(rs1==null)
	   {
		 System.out.println("Query Error");
         file.writeLog(User_Id,"Error in Query");        	 	 	  			 
	   }
	  	   	    
	   while(rs1.next())
	   {
	   	 if(count == 0)
	   	 {
           out.println("<FORM action=\"http://localhost:8080/examples/servlet/TestRules\" " +
                       " id=form1 name=form1 method=\"post\">");	 
           out.println("<P>&nbsp;</P>");
           out.println("<P>&nbsp;</P>");
           out.println("<P><STRONG><FONT >");
           out.println("<TABLE style=\"WIDTH: 561px;" +
                       " HEIGHT: 27px\" cellSpacing=1 cellPadding=1 width=561 ");
           out.println("bgColor=#abcdef border=0>");
           out.println("<TR>");
	       out.println("<TD noWrap align=bottom bgColor=#abcdef>&nbsp;<STRONG>" +
           		       "Choose from below ");
           out.println("the test(s) you wish to take and click on that to proceed ");
           out.println("further:</STRONG></TD></TR></TABLE> ");         
           out.println("</FONT></STRONG></P>");
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
                      Cat_Id + "\" disabled>&nbsp;&nbsp;" + rs.getString("Name") + "<br>");         	
             given++;         
           }
           else
           {
             out.println("<INPUT id = \"Cat" + (given + 1) + "\" name=\"Category\" type=\"radio\" value=\"" + 
                      Cat_Id + "\">&nbsp;&nbsp;" + rs.getString("Name") + "<br>");         	
           }                                  
           out.println("</A></DIV>");           
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
		System.out.println(e.getMessage());
		System.out.println(e.getStackTrace());
	    file.writeLog(User_Id,"Error in Category Retrival");
	 }

	 out.println("</UL>");
	 if(count == 0)
	 {	 	
	   out.println("<h1>No Tests Published By the Administrator !</h1>");	
       out.println("<FORM action=\"http://localhost:8080/examples/servlets/User/PostView.jsp\" " +
                   " id=form1 name=form1 method=\"post\">");	 
       out.println("<P>&nbsp;</P>");     
   	   out.println("<P><INPUT name=\"Ok\" type=\"submit\" value=\" OK\">");	 		 		 	
	   file.writeLog(User_Id,"No Tests Published");   	   	   
	 }
	 else
	 {
	   if(count == given)
	   {	
	     out.println("<h1>All Tests are Already Attempted !</h1>");	
	     out.println("</FORM>");
         out.println("<FORM action=\"http://localhost:8080/examples/servlets/User/PostView.jsp\" " +
                     " id=form1 name=form1 method=\"post\">");	 
         out.println("<P>&nbsp;</P>");     
   	     out.println("<P><INPUT name=\"Ok\" type=\"submit\" value=\" OK\">");	 		 		 		 	
	   }	
	   else
	   {
         out.println("<P>&nbsp;</P>");     
   	     out.println("<P><INPUT name=\"SubmitTest\" type=\"submit\" value=\" Submit\">");	 		 		   	
	     file.writeLog(User_Id,"Tests Displayed To User");   	      	     	
	   }	 	
	 }	
	 out.println("</P>");       
     out.println("</FORM>");
     out.println("</BODY>");
     out.println("</HTML>");			 	
	
	 try
	 {
	   //rs1.close();	
	   //rs.close();
	   smt.close();		 	
	   smt1.close();		 		   
	 }
	 catch(Exception e)
	 {
	 	System.out.println("Problem in Closing Resultset !");
		System.out.println(e.getMessage());
	    file.writeLog(User_Id,"Error in Closing ResultSet");		
	 }	 
 	}
 	
 	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	  doGet(req,res);     	
	}

}	