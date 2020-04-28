package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;


public class CreatePost extends HttpServlet
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
		ResultSet rs=null;
		String Postname;
	    int Avg;
	    String Qualification;
	    int experience=0;
	    int vacancy=0;
	    String Branch;
	    String Category[]=new String[5];
	    int Cat_Id[]=new int[5];
		int val=0;
		
		Category=req.getParameterValues("select2");
		Postname=req.getParameter("Postname");
		Avg=Integer.parseInt(req.getParameter("Avg"));
		Qualification=req.getParameter("Qualification");
		Branch=req.getParameter("Branch");
		experience=Integer.parseInt(req.getParameter("experience"));
		vacancy=Integer.parseInt(req.getParameter("vacancy"));
		
		System.out.println("PostName----"+Postname);
		System.out.println("Avg----"+Avg);
		System.out.println("Qualification----"+Qualification);
		System.out.println("experience----"+experience);
		System.out.println("vacancy----"+vacancy);

		Connection con=null;
		
		for(int j=0;j<Category.length;j++)
		{
			Cat_Id[j]=0;
		}
		
		try
		{
			//get a refrence to the connectionPool from the global
			// servlet context 
			
			pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
			
			//GET A CONNECTION FROM A CONNECTION POOL
			con=pool.getConnection();
		
			
			//create a statement
			
			 Statement smt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			 
			 for(int i=0;i<Category.length;i++)
			 {	
			 	rs=smt.executeQuery("select category_id from category where name='"+Category[i]+"'");
			 	rs.next();
				if(rs==null)
				{
				  System.out.println("Error!!  No Category was found!");
				}
			 	Cat_Id[i]=Integer.parseInt(rs.getString(1));
			 }
			 
			  	String query1="insert into post values(post_id.nextval,'"
			 	+Postname
			 	+"',CATEGORY_NT(CATEGORY_TY("
			 	+Cat_Id[0]
			 	+",0),"
			 	+"CATEGORY_TY("
			 	+Cat_Id[1]
			 	+",0),"
			 	+"CATEGORY_TY("
			 	+Cat_Id[2]
			 	+",0),"
			 	+"CATEGORY_TY("
			 	+Cat_Id[3]
			 	+",0),"
			 	+"CATEGORY_TY("
			 	+Cat_Id[4]
			 	+",0)"
			 	+"),"
			 	+Avg
			 	+",'"
			 	+Qualification
			 	+"','"
			 	+Branch
			 	+"',"
			 	+experience
			 	+","
			 	+vacancy
			 	+")";
				
				System.out.println("Query ==>"+query1);
				Statement smt1=con.createStatement();
				smt1.executeUpdate(query1);
				res.sendRedirect("http://localhost:8080/examples/servlets/Admin/Post.jsp");
		 
		}
		catch(Exception e)
		{
			
			e.printStackTrace(out);
		    out.println(e.getMessage());
		
		}

		/*out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1> Hello! </h1>");

		for(int i=0;i<Category.length;i++)
		{
			out.println(Category[i]);
			out.println("<br>");
		}
		for(int i=0;i<Category.length;i++)
		{
			out.println(Cat_Id[i]);
			out.println("<br>");
		}
		out.println("</body>");
		out.println("<html>");*/
	
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		doGet(req,res);
	}
}