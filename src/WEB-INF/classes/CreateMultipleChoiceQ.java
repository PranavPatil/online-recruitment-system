package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

public class CreateMultipleChoiceQ extends HttpServlet
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
		ResultSet rs=null,rs1=null;
		String Question;
		String ans1;
		String ans2;
		String ans3;
		String ans4;
		String CorrectAns=null;
		String ans;
		String select;
		int CatId;
		int flag=1;
		String Type="MC";
		
		Question=req.getParameter("txtQuestion");
		ans=req.getParameter("ans");
		ans1 = req.getParameter("ans1");
		ans2= req.getParameter("ans2");
		ans3= req.getParameter("ans3");
		ans4=req.getParameter("ans4");
		select=req.getParameter("select");
		
		if(ans.equalsIgnoreCase("on"))
		{
			CorrectAns=ans1;
		}
		else
		{
			int ians=Integer.parseInt(ans);
			if(ians==2)
			{
				CorrectAns=ans2;
			}
			else if(ians==3)
			{		
				CorrectAns=ans3;
			}
			else if(ians==4)
			{
				CorrectAns=ans4;
			}
			
		}
		
		
		Connection con=null;
		
		
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
			
			//create a statement
			Statement smt=con.createStatement();
			Statement smt1=con.createStatement();
			
			rs1=smt1.executeQuery("select question from questions");
			while(rs1.next())
			{
				if(Question.equalsIgnoreCase(rs1.getString(1)))
				{
					flag=0;
				}
			}
			if(flag==1)
			{
				rs=smt.executeQuery("Select category_id from category where Name='"
				+select
				+"'");
				rs.next();
				String Temp=rs.getString(1);
				CatId=Integer.parseInt(Temp);
				
				String query1 = "insert into Questions values(ques_Id.nextval"
				+",'"
				+Question
				+"',"
				+CatId
				+",'"
				+Type
				+"')";
				
				String query2 = "insert into MC_Answers values(MC_ANS_ID.nextval"
				+",ques_id.currval,'"
				+CorrectAns
				+"','"
				+ans1
				+"','"
				+ans2
				+"','"
				+ans3
				+"','"
				+ans4   
				+"')";
				
				smt.executeUpdate(query1);
				smt.executeUpdate(query2);
			}
			else
			{
				out.println("<html><body bgcolor=ivory><form name=form action=http://localhost:8080/examples/servlets/Admin/MultipleChoice.jsp>");
				out.println("<center><strong>Duplicate Question Found!!</strong>!!");
				out.println("<input type=submit value=Retry name=submit>");
				out.println("</form></body></html>");
			}
		}
		
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
		
		

		/*out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hello! </h1>");

		out.println("The question is :- " + Question);
		out.println("The First ans is :- " + ans1);
		out.println("<br>");
		out.println("The 2nd ans is :- " + ans2);
		out.println("<br>");
		out.println("The 3rd ans is :- " + ans3);
		out.println("<br>");
		out.println("The 4th ans is :- " + ans4);
		out.println("The Radio val is :- " + ans);
		out.println("<br>");
		out.println("The category is :- " + select);
		out.println("<br>");
		out.println("The CorrectAns is::" +CorrectAns);
		out.println("<br>");

		out.println("</body>");
		out.println("<html>");*/
		res.sendRedirect("http://localhost:8080/examples/servlets/Admin/PreQuestion.jsp");
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}