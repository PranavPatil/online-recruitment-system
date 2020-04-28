package ORS.Admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;


public class CreatePost extends HttpServlet
{ 
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
	String message = "Unknown Error";
	Connection con=null;
	
	String Postname = null,Desc = null,Qualification = null,Branch = null;
    int Avg = 0,Experience=0,Agelimit = -1,Vacancy=0;
    String Category[] = null;
    int Cat_Id[] = null;
	int val=0,length=0;
	boolean status = true;
		
    try
    {
	  Postname=req.getParameter("Postname");
	  Desc=req.getParameter("PostDesc");
	  Avg=Integer.parseInt(req.getParameter("Avg"));
	  Qualification=req.getParameter("Qualification");
	  Branch=req.getParameter("Branch");
	  Experience=Integer.parseInt(req.getParameter("Experience"));
	  int AgeBound=Integer.parseInt(req.getParameter("AgeBound"));
	  Vacancy=Integer.parseInt(req.getParameter("Vacancy"));
   	  Category=req.getParameterValues("Category");
	  
	  System.out.println("AgeBound = " + AgeBound);
	  
	  if(AgeBound == 1)
	  {
	  	Agelimit=Integer.parseInt(req.getParameter("Agelimit"));
	  }
	  else
	    Agelimit = 0;
	  
	  if(Postname == null || Desc == null || Qualification == null || Branch == null)
	  {
	  	message = "Empty Parameter fields";
	  	status = false;
	  }
	  else if(Desc.length() > 500)
	  {
	  	message = "Description length voilated";
	  	status = false;
	  }
	  else if(Avg < 0 || Avg > 100)
	  {
	  	message = "Min Percent entry voilated";
	  	status = false;
	  }
	  else if(Experience < 1 || Experience > 5)
	  {
	  	message = "Experience entry voilated";
	  	status = false;
	  }
	  else if(AgeBound == 1 && (Agelimit < 18 || Agelimit > 59))
	  {
	  	message = "Agelimit entry voilated";
	  	status = false;
	  }
	  else if(Vacancy < 0 || Vacancy > 100000)
	  {
	  	message = "Vacancy entry voilated";
	  	status = false;
	  }
	  else if(Category.length == 0 || Category == null)
	  {
	  	message = "Category Selection entry voilated";
	  	status = false;
	  }
    }
    catch(NumberFormatException ex)
    {
      message = "Illegal NumberFormat entry";
      //ex.printStackTrace();
      status = false;
    }
	
	if(status == true)
	{
      length = Category.length;
	  Cat_Id =new int[length];
	  int counter = 0;
	
	  for(int j=0;j<length;j++)
	  {
	    try
	    {
	  	  Cat_Id[j] = Integer.parseInt(Category[j]);
	    }
	    catch(NumberFormatException ex)
	    {
	  	  Cat_Id[j]=0;
	  	  counter++;
	    }
	    //System.out.println("id" + j + " = " + Cat_Id[j]);
	  }
	  
	  //System.out.println("count = " + counter + "length = " + length);
	  
	  if(counter > 0)
	  {
	    message = "Invalid Category Selection";
	    status = false;	
	  }
	  else if(Cat_Id == null)
	  {
	    message = "No Category Selection";
	  	status = false;
	  }
	}
		
	if(status == true)
	{
      int Post_Id = -1;
  	  boolean repeat = false,check = true;
  	  int count = 0;
  	  ResultSet rs = null;
      Statement stmt = null;
      
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
	    	  
  	  try
  	  {
  	  	Post_Id = Integer.parseInt(req.getParameter("Post_Id"));
  	  }
  	  catch(NumberFormatException ex)
  	  {
  	  	Post_Id = -1;
  	  }
  	  
  	  //System.out.println("Post_id = " + Post_Id);
  	  
  	  if(Post_Id > 0)
  	  {
	    try
	    {
	      String str = null;
		  rs = stmt.executeQuery("select PostName from Post where Post_Id = " + Post_Id);
	  	  rs.next();
		  str = rs.getString("PostName");
		  
		  if(Postname.equals(str))
		    check = false;
		    
		  //System.out.println("Check = " + check);
	    }
	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());	    	
	    }
  	  }
  	  
  	  if(check == true && Post_Id != 0)
  	  {
  	    try
	    {
	      rs = null;
		  rs = stmt.executeQuery("select Post_Id from Post where PostName = '" + Postname + "'");
		
		  while(rs.next())
		    count++;
		  
          //System.out.println("Count = " + count);
          
		  if(count == 0)
		    repeat = false;
		  else if(count > 0)
		    repeat = true;
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      out.println(e.getMessage());
	    }	    	  	  	
  	  }
	  
      if(repeat == false && Post_Id != 0)
	  {
	    try
	    {
	  	  String Query = null;
	  	  
 		  if(Post_Id == -1)
 		  {
	  	    Query = "Insert into Post values(Post_Id.nextval,'"
	 	                   + Postname + "','" + Desc + "',CATEGORY_NT(";
	 	
	 	    for(int j=0;j<length;j++)
	 	    {
	 	      if(j == (length-1))
	 	      {
	 	  	    Query = Query + "CATEGORY_TY(" + Cat_Id[j] + ",0)";
	 	      }
	 	      else	 	  
	 	        Query = Query + "CATEGORY_TY(" + Cat_Id[j] + ",0),";
	 	    }

            Query = Query + ")," + Avg + ",'" + Qualification + "','" + Branch
                	      + "'," + Experience + "," + Agelimit + "," + Vacancy + ")";
		
		    //System.out.println("Query ==>"+Query);
 		  }
 		  else if(Post_Id > 0)
 		  {
 		    rs = null;
 		    rs = stmt.executeQuery("Select N.Category_Id from Post,TABLE(Post.CatEntry)N where Post_Id = " + 
 		                           Post_Id);
            
            ArrayList Present = new ArrayList();
            
            while(rs.next())
            {
              Present.add(rs.getInt(1));
            }
            
            //System.out.println("Present Before = " + Present);
            
            for(int i = 0;i < length;i++)
            {
              int j = 0;
              boolean equals = false;
              
              while(equals == false && j < Present.size())
              {
              	if(Cat_Id[i] == (Integer)Present.get(j))
              	{
              	  Present.remove(j);
              	  equals = true;
              	}
              	j++;
              }
              
              if(equals == false)
              {
              	//System.out.println("Insert cat_Id " + Cat_Id[i]);
              	stmt.executeUpdate("Insert into Table(Select CatEntry from Post where Post_Id = "
              	                    + Post_Id + ") values(CATEGORY_TY(" + Cat_Id[i] + ",0))");
              }
            }
            
            //System.out.println("Present After = " + Present);
            
            for(int k = 0;k < Present.size();k++)
            {
              stmt.executeUpdate("Delete Table(select CatEntry from Post where Post_Id = " +  
                                  Post_Id + ")N where N.Category_Id = " + (Integer)Present.get(k));
            }
             		    
 		    Query = "Update Post set PostName = '"
			               + Postname
			               + "',Description = '"
			               + Desc
			               + "',Aggregate = "
			               + Avg
			               + ",Qualification = '"
			               + Qualification
			               + "',Branch = '"
 			               + Branch
			               + "',Experience = "
			               + Experience
			               + ",Agelimit = "
			               + Agelimit
			               + ",Vacancy = "
			               + Vacancy
			               + " where Post_Id = " 
			               + Post_Id;
 		  }
 		   
		  stmt.executeUpdate(Query);
		  stmt.executeUpdate("Commit");
		  stmt.close();
		  res.sendRedirect("/examples/servlets/Admin/Post.jsp");
		}
 	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());
	      
	      try
          {
            Statement smt = con.createStatement();
            smt.executeUpdate("Rollback");
            smt.close();        	
          }
          catch(Exception ee)
          {
            out.print("Error : System restoration failed" + ee.getMessage());
          }
	    }
	  } // else if (Post_Id == 0) out.write("Error : Post_Id = 0 !");
 	  else
	  {
        out.write("<HTML>");
        out.write("<HEAD>");
        out.write("<TITLE>Post ErrorPage</TITLE>");
        out.write("</HEAD>");
        out.write("<BODY bgcolor=\"ivory\">");
        out.write("<CENTER><h1> Duplicate Post Present, Operation failed !! </h1>");
        out.write("<h1> Please insert another Post name !! </h1></CENTER>");
        out.write("<P>&nbsp;</P> <P>");
	    out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Post.jsp\">");
	    out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	    out.write("</CENTER></FORM>");
        out.write("</BODY>");
        out.write("</HTML>");			
	  }
	}
	else
	{
      out.write("<HTML>");
      out.write("<HEAD>");
      out.write("<TITLE>Post ErrorPage</TITLE>");
      out.write("</HEAD>");
      out.write("<BODY bgcolor=\"ivory\">");
      out.write("<CENTER><h1> " + message + ", Operation failed !! </h1>");
      out.write("<h1> Please check all the fields again !! </h1></CENTER>");
      out.write("<P>&nbsp;</P> <P>");
	  out.write("<FORM NAME=\"Prompt\" ACTION=\"/examples/servlets/Admin/Post.jsp\">");
	  out.write("<CENTER><INPUT name=\"Ok\" type=\"submit\" value=\" OK  \" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25>");
	  out.write("</CENTER></FORM>");
      out.write("</BODY>");
      out.write("</HTML>");
	}
  }
	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}