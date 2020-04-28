package ORS.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class UserReg extends HttpServlet
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
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		Connection con = null;
		Statement smt = null;
		boolean repeat = false;		
				
		String Fname,Mname,Lname;
		String Gender,Address,Telephone,Email;
		String dob_dd,dob_mm,dob_yy;
		String Qualification,Branch,Experience;
		String Login,Passwd;
		
		StringBuffer Birthdate=new StringBuffer("ini");	//Becoz Date in Java has format(mm-dd-yy)
												//and of sql is(dd-mm-yy)
		int Attempt=0;
		int count = 0;
		
		Fname=req.getParameter("fname");
		Mname=req.getParameter("mname");
		Lname=req.getParameter("lname");
		Gender=req.getParameter("gender");
		Address=req.getParameter("address");
		Telephone=req.getParameter("telephone");
		Email= req.getParameter("email");
		Birthdate.delete(0,3);
		Birthdate.append(req.getParameter("dob_dd"));
		String Temp=new String(req.getParameter("dob_mm"));
		
		if(Temp.equalsIgnoreCase("1"))
		{
			Temp=new String("Jan");
		}
		else if(Temp.equalsIgnoreCase("2"))
		{
			Temp=new String("Feb");
		}
		else if(Temp.equalsIgnoreCase("3"))
		{
			Temp=new String("Mar");
		}
		else if(Temp.equalsIgnoreCase("4"))
		{
			Temp=new String("Apr");
		}
		else if(Temp.equalsIgnoreCase("5"))
		{
			Temp=new String("May");
		}
		else if(Temp.equalsIgnoreCase("6"))
		{
			Temp=new String("Jun");
		}
		else if(Temp.equalsIgnoreCase("7"))
		{
			Temp=new String("Jul");
		}
		else if(Temp.equalsIgnoreCase("8"))
		{
			Temp=new String("Aug");
		}
		else if(Temp.equalsIgnoreCase("9"))
		{
			Temp=new String("Sep");
		}
		else if(Temp.equalsIgnoreCase("10"))
		{
			Temp=new String("Oct");
		}
		else if(Temp.equalsIgnoreCase("11"))
		{
			Temp=new String("Nov");
		}
		else if(Temp.equalsIgnoreCase("12"))
		{
			Temp=new String("Dec");
		}
		
		Birthdate.append("-"+Temp);
		Birthdate.append("-"+req.getParameter("dob_yy"));
		Qualification= req.getParameter("Qualification");
		Branch= req.getParameter("Branch");
		Experience= req.getParameter("Experience");				
		Login = req.getParameter("login");
		Passwd=req.getParameter("passwd");
				
		try
		{
			count = 0;
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			con=pool.getConnection();
		    smt=con.createStatement();

		    ResultSet rs = smt.executeQuery("select User_Id from Users " + 
		                                     "where Login = '" + Login + "'");
		    while(rs.next())
		      count++;
            System.out.println("Count = " + count);
		     		
		    if(count == 0)
		      repeat = false;
		    else if(count > 0)        		  
		      repeat = true;
		}
		
		catch(Exception e)
		{
			System.out.println("Problem in User Entry ! ");
			System.err.println(e.getStackTrace());
		}
						                           
        if(repeat == false)
        {
		  try
		  {
		  	String query = "insert into Users values(User_Id.nextval"
			 +",'"
			 +Fname
			 +"','"
			 +Mname
			 +"','"
			 +Lname
			 +"','"
			 +Login
			 +"','"			 
			 +Passwd
			 +"','"
			 +Gender
			 +"','"
			 +Telephone
			 +"','"
			 +Address
			 +"','"
			 +Email
			 +"','"
			 +Birthdate
			 +"','"
			 +Qualification
			 +"','"
			 +Branch
			 +"',"
			 +Experience + ")";			 
			 
		     smt.executeUpdate(query);
		     smt.close();		     		  
		  }		
		  catch(Exception exp)
		  {
			System.out.println("Problem in User Entry ! ");
			System.err.println(exp.getMessage());
		  } 
 		  res.sendRedirect("http://localhost:8080/examples/servlets/User/UserLoginPage.html");        	
        }
		else if (repeat == true)
		{
		  out.println("<html>");
		  out.println("<head>");
		  out.println("<title>");
		  out.println("</title>");
		  out.println("</head>");
		  out.println("<body bgcolor=ivory>");
		  out.println("<h1> Please insert another Login Id !!</h1>");
		  out.println("The Name is :- " + Fname + " " + Lname);
		  out.println("<br>");
		  out.println("The BirthDate is :- " + Birthdate);
		  out.println("<br>");
		  out.println("The Login is :- " + Login);
		  out.println("<br>");
		  out.println("The Mail ID is :- " + Email);
		  out.println("<br>");
          out.println("<P>&nbsp;</P> " + "<P>" +
				 "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/User/UserRegistrationPage.html\">" +				 
				 "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
				 "</FORM>");          
		  out.println("</body>");
		  out.println("<html>");
		}
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}