package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.math.*;
import ORS.ConnPool.*;

public class End extends HttpServlet
{
	ConnectionPool pool=null; 
	Connection con=null;
	PrintWriter out;
	  
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	  try{
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
	 ResultSet rst = null,rst1 = null,rst2 = null;
	 res.setContentType("text/html");
     PrintWriter out=res.getWriter();
     int User_Id = 0,Post_Id = 0,Test_Id = 0,Cat_Id = 0;
     Log file = new Log("C:/Log.dat");     

     /*-------------- Getting values from Session -------------*/ 

     HttpSession session=req.getSession(false);

     User_Id = (Integer)session.getAttribute("User_Id");
     if(User_Id == 0)
     {
     	System.out.println("Error in Session !!");
     	file.writeLog(0,"Error in User Session");        	 	      	
     }
     System.out.println(" -> User_Id = "+ User_Id);

     Post_Id = (Integer)session.getAttribute("Post_Id");
     if(Post_Id == 0)
     {
     	System.out.println("Error in Session !!");
     	file.writeLog(0,"Error in User Session");        	 	      	
     }
     System.out.println(" -> Post_Id = "+ Post_Id);

     Cat_Id = (Integer)session.getAttribute("Cat_Id");
     if(Cat_Id == 0)
     {
     	System.out.println("Error in Session !!");
     	file.writeLog(0,"Error in Category Session");        	 	      	
     }
     System.out.println(" -> Cat_Id = "+ Cat_Id);

     Test_Id = (Integer)session.getAttribute("Test_Id");
     if(Test_Id == 0)
     {
      	System.out.println("Error in Session !!");
     	file.writeLog(User_Id,"Error in User Session");        	 	       	
     }
     System.out.println(" -> Test_Id = "+ Test_Id);

     /*--------------------- Variables Declaration -----------------*/

     int num = 0,i;
     int Ans = 0,Correct = 0,Total = 0,Attempt = 0;
     float Percent = 0,AttPercent = 0,T_Percent = 0,T_Attempt = 0;     
     String result = null,T_Result = null;
     int Total_Ques = 0,Pass_Score = 0, Attempt_No = 0;
     int n1 = 0,n2 = 0,Aggregate = 0;

     /*-------------- Calculating Total for present Test_Id -------------*/   

  	 try
	 {	 	
	  smt=con.createStatement();
	  
      rst = null;
      rst = smt.executeQuery("select N.User_Answer,N.Correct_Ans " +
                             "from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id = " +
                              User_Id + " and Post_Id = " + Post_Id + " and Test_Id = " + Test_Id +
                             " order by N.Ques_Number");
      
      while(rst.next())
      {
        Ans = rst.getInt(1);
        Correct = rst.getInt(2);
        
        if(Ans > 0)
        {
          Attempt++;	
          if(Ans == Correct)
          {
             Total = Total + 1;
          }	        	
        }
      }
            
	  System.out.println("Total = " + Total + "Attempt = " + Attempt); 
	  	  
	 }
	 catch(Exception e)	 
	 {
	 	System.out.println("Problem in Result Processing !!");
	  	System.err.println(e.getMessage());
     	file.writeLog(User_Id,"Error in Result Processing");        	 	 	  	
	 }
	 
	 /*-------------- Getting Total Tests for given Post_Id -------------*/ 
	 
	 
	 try
	 {
	 	rst1 = null;	 	
        rst1 = smt.executeQuery("select max(rownum)from Post,TABLE(Post.CatEntry)N " + 
                                " where N.Test_Id > 0 and Post_Id = " + Post_Id);
        rst1.next();
        n1 = rst1.getInt(1);
        System.out.println("n1 = " + n1);
        
	 	rst1 = null;	 	
        rst1 = smt.executeQuery("select Aggregate from Post where Post_Id = " + Post_Id);                                
        rst1.next();
        Aggregate = rst1.getInt(1);
        System.out.println("Aggregate = " + Aggregate);        	
	 }
	 catch(Exception e)
	 {
	 	System.out.println("Problem in Total Tests !!");
	  	System.err.println(e.getMessage());
     	file.writeLog(User_Id,"Error in Total Tests");        	 	 	  		 	
	 }	
	 
	 /*-------------- Entry of Result in Database -------------*/ 
	                          
	 try
	 {
	 	rst = null;
	    rst = smt.executeQuery("select Pass_Score,Total_Ques from Test where" + 
	                           " Test_Id = " + Test_Id);
	    rst.next();
	    Pass_Score = rst.getInt(1);
	    Total_Ques = rst.getInt(2);                       
	    
	    Percent =  (Total*100)/Total_Ques;
	    AttPercent = (Attempt*100)/Total_Ques;
	    
	    if (Percent > Pass_Score)
          result = "Pass";
        else
          result = "Fail";     

       System.out.println("percent = " + Percent); 
       
       rst1 = null;
       rst1 = smt.executeQuery("select count(rownum) from Result,TABLE(Result.TestData)N where User_Id = " +
                       User_Id + " and Post_Id = " + Post_Id);
       rst1.next();
       n2 = rst1.getInt(1);
       System.out.println("n2 = " + n2);      
       
       int val = 0,temp = 0;
       String Query = null;
       temp = n2;
              
       if(n2 == 0)
       {
       	 T_Percent = Percent/n1;
       	 T_Attempt = AttPercent/n1;
       	 
       	 if(T_Percent >= Aggregate)
           T_Result = "Pass";
         else
           T_Result = "Fail";     
       	 
         smt.executeUpdate("insert into Result values (" + User_Id + "," + Post_Id + "," +
                            T_Percent + "," + T_Attempt + ",'" + T_Result + "',1" +
                            ",sysdate," + "TDATA_NT(TDATA_TY(" + Cat_Id + "," + Test_Id + "," + 
                            Percent + "," + AttPercent + ",'" + result + "')))");                                                        
         temp++;                   
       }
       else if(n2 > 0)
       {  
       	 rst1 = null;       	 
       	 rst1 = smt.executeQuery("Select Months_Between(SysDate,TestDate) from Result where User_Id = " +
       	                          User_Id + " and Post_Id = " + Post_Id);
       	 rst1.next();
       	 
       	 if(rst1.getDouble(1) == 0)
       	 {             
       	     System.out.println("Date Same !!");
             rst2 = null;
             rst2 = smt.executeQuery("select T_Percent,T_Attempt from Result where " + 
                                     "User_Id = " + User_Id + " and Post_Id = " + Post_Id);
             rst2.next();

             T_Percent = rst2.getInt(1);
             T_Attempt = rst2.getInt(2);
             T_Percent = ((T_Percent * n1) + Percent)/n1;
             T_Attempt = ((T_Attempt * n1) + AttPercent)/n1;

        	 if(T_Percent >= Aggregate)
               T_Result = "Pass";
             else
               T_Result = "Fail";                   
             
        	 smt.executeUpdate("insert into Table(select TestData from Result " + 
		                       "where User_Id = " + User_Id + " and Post_Id = " + Post_Id + 
		                       ") values (TDATA_TY(" + Cat_Id + "," + Test_Id + "," + Percent +
                               "," + AttPercent + ",'" + result + "'))");       	   	                               

             System.out.println("T_Percent = " + T_Percent);                    
             
             smt.executeUpdate("update Result set T_Percent = " + T_Percent + ",T_Attempt = " + 
                                T_Attempt + ",T_Result = '" + T_Result + "' where User_Id = " + 
                                User_Id + " and Post_Id = " + Post_Id);                  
             temp++;                   
       	 }  
       	 else
       	 {
       	 	 System.out.println("Entry in else case !!");
       	 	 rst2 = null;
       	 	 rst2 = smt.executeQuery("select Attempt_No from Result where User_Id = " + 
       	 	               User_Id + " and Post_Id = " + Post_Id);
       	 	 rst2.next();
       	 	 Attempt_No = rst2.getInt(1) + 1;
       	 	 System.out.println("Attempt_No = " + Attempt_No);
       	 	 
        	 T_Percent = Percent/n1;
        	 T_Attempt = AttPercent/n1;
       	 
        	 if(T_Percent >= Aggregate)
               T_Result = "Pass";
             else
               T_Result = "Fail";     
       	 	               
       	 	 smt.executeUpdate("delete Table(select TestData from Result where User_Id = " + 
       	 	                    User_Id + " and Post_Id = " + Post_Id + ")N");
       	 	 smt.executeUpdate("delete from Result where User_Id = " + User_Id +
       	 	                   " and Post_Id = " + Post_Id);       
             smt.executeUpdate("insert into Result values (" + User_Id + "," + Post_Id + "," +
                               T_Percent + "," + T_Attempt + ",'" + T_Result + "'," + Attempt_No +                               
                               ",sysdate," + "TDATA_NT(TDATA_TY(" + Cat_Id + "," + Test_Id + "," + Percent +
                               "," + AttPercent + ",'" + result + "')))");       	 	
             temp = 1;                  
       	 }                              	                                 	                         
       }
       else
       {
       	file.writeLog(User_Id,"Error in Result Entry, System Exits");
       	System.exit(0);       	
       }           
	    	    	  
	   smt.executeUpdate("delete Table(select TestEntry from TestQuestions where User_Id = " +
	     User_Id + "and Test_Id = " + Test_Id + ")N"); 
       
       n2 = temp; 	
	 }
	 catch(Exception e)
	 {
	 		System.out.println("Problem in Result Entry !!");
			System.out.println(e.getMessage());
     	    file.writeLog(User_Id,"Error in Result Entry");        	 	 	  				
	 }
	 
	 
	 /*-------------- Unpublishing and Committing the values  -------------*/ 
	 
	 try
	 {
	 	rst  = null;	                   		                  
		smt.executeUpdate("Commit");                  		
		smt.close();
        file.writeLog(User_Id,"Result Calulated Successfully");		
	 }
	 catch(Exception e)
	 {
	 		System.out.println("Problem in Commit !!");
			System.out.println(e.getMessage());
     	    file.writeLog(User_Id,"Error in Saving and Commit");
	 }
	 
	 System.out.println("Result Calulated Successfully !!");
	 String path = null;	 
	 
	 /*-------------- Finding appropriate path -------------*/ 
	         
     
     System.out.println("Values of n1,n2 are = " + n1 + "," + n2);	
        
     if(n1 == n2)
     {
       path = "http://localhost:8080/examples/servlets/User/Thanks.jsp";
     }   
     else
     {
       path = "http://localhost:8080/examples/servlet/ChooseTest";	
     }  
     
     System.out.println("Path = " + path);                        	 		
	 res.sendRedirect(path);	 
 	}

	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		 doGet(req,res);
	} 	
}	