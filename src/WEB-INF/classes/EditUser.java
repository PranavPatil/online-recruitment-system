package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class EditUser extends HttpServlet
{ 
   ConnectionPool pool=null;
   Statement smt;
   Connection con=null;
   
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
		
		String fname;
		String mname;
		String lname;
		String gender;
		String address;
		int contact;
		String email;
		String dob_dd;
		String dob_mm;
		String dob_yy;
		String Qualification;
		String Branch;
		String Experience;
		int User_Id=0;
	
				
		StringBuffer date=new StringBuffer("ini");	//Becoz Date in Java has format(mm-dd-yy)
												//and of sql is(dd-mm-yy)
		int Attempt=0;
		
		fname=req.getParameter("fname");
		mname=req.getParameter("mname");
		lname=req.getParameter("lname");
		gender=req.getParameter("gender");
		address=req.getParameter("address");
		contact=Integer.parseInt(req.getParameter("telephone"));
		email= req.getParameter("email");
		date.delete(0,3);
		date.append(req.getParameter("dob_dd"));
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
		
		date.append("-"+Temp);
		date.append("-"+req.getParameter("dob_yy"));
		Qualification= req.getParameter("Qualification");
		Branch= req.getParameter("Branch");
		Experience= req.getParameter("Experience");
		User_Id=Integer.parseInt(req.getParameter("User_Id"));
					
		System.out.println("User_Id--"+User_Id);
		System.out.println("mname--"+mname);
		System.out.println("lname--"+lname);
		System.out.println("gender--"+gender);
		System.out.println("address--"+address);
		System.out.println("email--"+email);
		System.out.println("Date--"+date);
		System.out.println("Qualification--"+Qualification);
		System.out.println("contact--"+contact);
		/*System.out.println("login--"+login);
		System.out.println("passwd--"+passwd);*/
		
				
		try
		{
		 	 //get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			 smt=con.createStatement();
			 			 
			 String query1 = "update Users set FNAME='"
			 +fname
			 +"',MNAME='"
			 +mname
			 +"',LNAME='"
			 +lname
			 +"',GENDER='"
			 +gender
			 +"',TELEPHONE="
			 +contact
			 +",ADDRESS='"
			 +address
			 +"',EMAIL='"
			 +email
			 +"',DATE_OF_BIRTH='"
			 +date
			 +"',QUALIFICATION='"
			 +Qualification
			 +"',BRANCH='"
			 +Branch
			 +"',EXPERIENCE="
			 +Experience
			 +" "
			 +"where User_id='"
			 +User_Id
			 +"'";
			 smt.executeUpdate(query1); 
		
		 }
		 catch(Exception e)
		 {
		 	System.err.println(e.getMessage());
	     }                        
		 
		
		res.sendRedirect("http://localhost:8080/examples/servlets/Admin/UserUpdate.jsp");

		/*out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hello! </h1>");

		out.println("The First Date is :- " + date);
		out.println("<br>");
		out.println("The Last Name is :- " + fname);
		out.println("<br>");
		out.println("The Login is :- " + login);
		out.println("<br>");
		out.println("The Mail ID is :- " + email);
		out.println("<br>");

		out.println("</body>");
		out.println("<html>");*/
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}