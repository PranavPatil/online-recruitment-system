package ORS.Admin;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class DeleteTest extends HttpServlet
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
    String Test=null;
    int Test_Id=0;
		
    Test=req.getParameter("SelectTest");
    
	if(Test != null)
	{
	  try
	  {
	  	Test_Id = Integer.parseInt(Test);
	  }
	  catch(NumberFormatException ex)
	  {
	  	Test_Id = 0;
	  }
	}
	
    if(Test_Id != 0)
    {
      Connection con = null;
      
      try
      {
        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
	    con = pool.getConnection();
	    Statement smt=con.createStatement();
	    
	    String Query = "Select Post_Id,N.Category_Id from Post,Table(Post.CatEntry )N where N.Test_Id = " + 
	                     Test_Id;
  		ResultSet rs1=smt.executeQuery(Query);
        int P_Id = 0;
          
        while(rs1.next())
        {
          try
          {
            Statement smt1 = con.createStatement();
            Query = null;
            int Post_Id = rs1.getInt(1);
            int Cat_Id = rs1.getInt(2);
            
            if(Cat_Id != 0)
            {
              Query = "Delete from TestQuestions where Post_Id = " + 
                        Post_Id + " and Category_Id = " + Cat_Id;
              
              smt1.execute(Query);
            }
            
            if(P_Id != Post_Id)
            {
              System.out.println("Post = " + Post_Id);
            
              Query = "Update Table(select CatEntry from Post where Post_Id = " +
                               Post_Id + ")N set N.Test_Id = 0 where N.Test_Id = " + Test_Id;
              
              smt1.executeUpdate(Query);
              P_Id = Post_Id;
            }
            
            smt1.close();
          }
          catch(SQLException ex)
          {
            out.print("Error in Deleting TestQuestions : " + ex.getMessage());
          }
        }
	    
	    smt.execute("Delete from Test where Test_Id = " + Test_Id);
	    smt.execute("Commit");
	    smt.close();
      }
      catch(Exception e)
      {
      	e.printStackTrace();
        out.println(e.getMessage());
        
        try
        {
          Statement stmt = con.createStatement();
          stmt.executeUpdate("Rollback");
          stmt.close();        	
        }
        catch(Exception ee)
        {
          out.print("Error : System restoration failed" + ee.getMessage());
        }
      }
    }
    res.sendRedirect("/examples/servlets/Admin/Test.jsp");
  }		
  
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
	doGet(req,res);
  }
}