package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class ResultDetails extends HttpServlet
{
  public void init(ServletConfig conf)throws ServletException
  {
    super.init(conf);
  }
		 
  public void doGet(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    res.setContentType("text/html");
    PrintWriter out=res.getWriter();
    
    long User_Id = 0;
    int Post_Id = 0;
    String temp = null;
    Connection con=null;
    ResultSet rs = null;
    Statement stmt = null;
    
    temp = req.getParameter("User_Id");
    
    try
    {
      User_Id = Long.parseLong(temp);
    }
    catch(NumberFormatException ex)
    {
      User_Id = 0;
    }
    
    temp = null;
    temp = req.getParameter("Post_Id");
    
    try
    {
      Post_Id = Integer.parseInt(temp);
    }
    catch(NumberFormatException ex)
    {
      Post_Id = 0;
    }
    
    System.out.println("User = " + User_Id);
    System.out.println("Post = " + Post_Id);
    
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
    
    String LogId = "Not Available",Fname = "Not Available",Address = "Not Available",Email = "Not Available";
    String Exp = "Not Available",Gender = "Not Available",Qualfn = "Not Available",Branch = "Not Available";
    int Telno = 0,Age = 0,Selected = -1;
    
    float Tpercent = -100,Tattempt = -1;
    int Attpt_no = 0;
    String result = "Not Available";
    java.util.Date date = null;
    
    if(User_Id != 0)
    {
      try	 
      {
		int var = 0;
		rs = null;
		rs = stmt.executeQuery("Select * from Users where User_Id = " + User_Id + "");
		rs.next();
		LogId = rs.getString(5);
		Fname = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
		Gender = rs.getString(7);
		
		if(Gender != null)
		{
		   if(Gender.equals("M"))
		     Gender = "Male";
		   else if(Gender.equals("F"))
		     Gender = "Female";
		}
		
		Address = rs.getString(9);
		Telno = rs.getInt(8);
		Email = rs.getString(10);

		date = rs.getDate(11);
		
		if(date != null)
		{
		  Calendar Cdate = Calendar.getInstance();
	      Calendar Bdate = Calendar.getInstance();
	      Bdate.setTime(date);
		  Age = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR); 
		}
				
		Qualfn = rs.getString(12);
		Branch = rs.getString(13);
		var = rs.getInt(14);
		
		if(var == 5)
		{
		  Exp = "above 10yrs";
		}
		else if(var == 4)
		{
		  Exp = "5-10yrs";
		}
		else if(var == 3)
		{
		  Exp = "2-5yrs";
		}
		else if(var == 2)
		{
		  Exp = "1-2yrs";
		}
		else
		{
		  Exp = "Fresher";
		}
		
		Selected = rs.getInt(16);
	  }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	    out.println(e.getMessage());
	  }
	          
      if(Post_Id != 0)
      {
        try
	    {
		  String Query = "Select T_Percent,T_Attempt,T_Result,Attempt_No,TestDate from Result where User_Id = " 
                         + User_Id + " and Post_Id = " + Post_Id;
		  rs = null;
		  date = null;
		  rs = stmt.executeQuery(Query);
		  rs.next();
		  
		  Tpercent = rs.getFloat(1);
		  Tattempt = rs.getFloat(2);
		  result = rs.getString(3);
		  Attpt_no = rs.getInt(4);
		  date = rs.getDate(5);
		}
 	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());
	    }
      }
    }
    
    out.write("<html>");
    out.write("<head>");
    out.write("<title>Result Details</title>");
    out.write("<script language=javascript>");
    out.write("function start1(form)");
    out.write("{ window.close(); }");
    out.write("</script>");
    out.write("</head>");
    out.write("<body bgcolor=\"ivory\">");
    out.write("<P>");
    out.write("<form name=\"Detailsfrm\" method=\"get\">");
    out.write("<center><h1> Details </h1></center>");
    out.write("<hr>");
    out.write("<CENTER><h2>Candidate Profile</h2></CENTER><BR>");
    out.write("<TABLE cellSpacing=1 cellPadding=5 width=\"100%\" align=center border=0 id=TABLE1>");
    out.write("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD width=150><STRONG><FONT color='#9900ff' size=3>Login Id : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + LogId + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Full Name : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Fname + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Address : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Address + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Telephone No : </FONT></STRONG></TD>");
    
    if(Telno == 0)
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");
    else
      out.println("<TD><STRONG>" + Telno + ". </STRONG></TD></TR>");
    
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Email Id : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Email + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Age : </FONT></STRONG></TD>");
    
    if(Age == 0)
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");
    else
      out.println("<TD><STRONG>" + Age + " yrs. </STRONG></TD></TR>");
    
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Gender : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Gender + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Qualification : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Qualfn + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Branch : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Branch + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Experience : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + Exp + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Selected : </FONT></STRONG></TD>");

    
    if(Selected == 1)
      out.println("<TD><STRONG>Once. </STRONG></TD></TR>");
    else if(Selected == 2)
      out.println("<TD><STRONG>Twice. </STRONG></TD></TR>");
    else if(Selected == 3)
      out.println("<TD><STRONG>Thrice. </STRONG></TD></TR>");
    else if(Selected > 3)
      out.println("<TD><STRONG>" + Selected + ". </STRONG></TD></TR>");
    else  
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");

    out.println("</TABLE>"); 
    out.println("<hr>");
    out.println("<CENTER><h2>Result Details - Exam for Post Manager</h2></CENTER><BR>");
    out.println("<TABLE cellSpacing=1 cellPadding=5 width=\"50%\" align=left border=0 id=TABLE2>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Aggregate Percentage : </FONT></STRONG></TD>");
    
    if(Tpercent > -1)
      out.println("<TD><STRONG>" + Tpercent + " %. </STRONG></TD></TR>");
    else if(Tpercent > -51)
      out.println("<TD><STRONG><FONT color='red'>Negative. </FONT></STRONG></TD></TR>");
    else
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");
    
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Attempted Percentage : </FONT></STRONG></TD>");
    
    if(Tattempt == -1)
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");
    else
      out.println("<TD><STRONG>" + Tattempt + " %. </STRONG></TD></TR>");
    
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Result : </FONT></STRONG></TD>");
    out.println("<TD><STRONG>" + result + ". </STRONG></TD></TR>");
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Attempt No : </FONT></STRONG></TD>");
    
    if(Attpt_no == 0)
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");
    else
      out.println("<TD><STRONG>" + Attpt_no + ". </STRONG></TD></TR>");
    
    out.println("<TR>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD>&nbsp;</TD>");
    out.println("<TD><STRONG><FONT color='#9900ff' size=3>Date of Exam : </FONT></STRONG></TD>");
    
    if(date == null)
      out.println("<TD><STRONG>Not Available. </STRONG></TD></TR>");
    else
      out.println("<TD><STRONG>" + date + ". </STRONG></TD></TR>");
    
    out.println("</TABLE>");
    out.println("<BR><BR><BR><BR><BR><BR><BR><BR><BR><hr><BR>");
    out.println("<TABLE cellSpacing=1 cellPadding=5 width=\"75%\" align=center border=1 id=TABLE3>");
    out.println("<TR vAlign=center align=middle bgColor=black>");
    out.println("<TD colSpan=6 style=\"VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center\">");
    out.println("<FONT face=Verdana size=2><STRONG style=\"COLOR: white; BACKGROUND-COLOR: black\">");
    out.println("Result Overview</STRONG></FONT></TD></TR>");
    
    out.println("<TR bgColor=\"#abcdef\">");
    out.println("<TD>");
    out.println("<P align=center><STRONG><FONT face=Verdana size=1>Category Name</FONT></STRONG></P></TD>");
    out.println("<TD>");
    out.println("<P align=center><STRONG><FONT face=Verdana size=1>Test Name</FONT></STRONG></P></TD>");
    out.println("<TD>");
    out.println("<P align=center><STRONG><FONT face=Verdana size=1>Total %</FONT></STRONG></P></TD>");
    out.println("<TD>");
    out.println("<P align=center><STRONG><FONT face=Verdana size=1>Attempted %</FONT></STRONG></P></TD>");
    out.println("<TD>");
    out.println("<P align=center><STRONG><FONT face=Verdana size=1>Result</FONT></STRONG></P></TD>");
    out.println("</TR>");
    
    try
    {
      int cid = 0,tid = 0;
      ResultSet rs1 = null,rs2 = null;

      rs = null;
      result = "Not Available";
      
      Statement smt = con.createStatement();
      
      rs = stmt.executeQuery("Select N.Category_Id,N.Test_Id from Post,TABLE(Post.CatEntry)N where Post_Id = "
                            + Post_Id);
      
      while(rs.next())
      {
        out.println("<TR>");
        
        cid = rs.getInt(1);
        tid = rs.getInt(2);
        
        if(cid != 0)
        {
          out.println("<TD>");
          
          rs1 = null;
          rs1 = smt.executeQuery("Select Name from Category where Category_Id = " + cid);
          rs1.next();
          
          out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs1.getString(1) +"</FONT></STRONG></P></TD>");

          out.println("<TD>");
          
          if(tid != 0)
          {
            rs1 = null;
            rs1 = smt.executeQuery("Select Name from Test where Test_Id = " + tid);
            rs1.next();
            out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs1.getString(1) +"</FONT></STRONG></P></TD>");
          }
          else
            out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Available</FONT></STRONG></P></TD>");
          
          rs2 = null;
          rs2 = smt.executeQuery("Select N.Percent,N.Attempt,N.Result from Result,TABLE(Result.TestData)N where User_Id = "
                + User_Id + " and Post_Id = " + Post_Id + " and N.Category_Id = " + cid);
          
          if(rs2.next())
          {
           float Percent = rs2.getFloat(1);
           
           out.println("<TD>");
           
           if(Percent > -1)
             out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + Percent + "</FONT></STRONG></P></TD>");
           else
             out.println("<P align=center><STRONG><FONT color='red' face=Verdana size=1>Negative</FONT></STRONG></P></TD>");

           out.println("<TD>");
           out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs2.getFloat(2) + "</FONT></STRONG></P></TD>");
           out.println("<TD>");
           out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs2.getString(3) + "</FONT></STRONG></P></TD>");
          }
          else
          {
           out.println("<TD>");
           out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Attempted</FONT></STRONG></P></TD>");
           out.println("<TD>");
           out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Attempted</FONT></STRONG></P></TD>");
           out.println("<TD>");
           out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Attempted</FONT></STRONG></P></TD>");
          }
        }
                
        out.println("</TR>");
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      out.println(ex.getMessage());
    }
    
    out.write("</TABLE>");
    out.write("<P></P>");
    out.write("<P></P>");
    out.write("<P></P>");
    out.write("<hr>");
    out.write("<center><input type=\"button\" name=\"Ok\" value=\" OK \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25 onclick='start1(this.form)'></center>");
    out.write("<P></P></form>");
    out.write("</body>");
    out.write("</html>");
  }
 	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    doGet(req,res);     	
  }
}	    