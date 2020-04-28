package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class TestPage extends HttpServlet
{
	ConnectionPool pool=null;
   	Connection con=null;			
	int Q_Load = 0,Q_Display = 0;
	int Ques_Id = 0,option = 0,max = 0;
	String Next="Next Question",Prev=null;
	String Ans1=null,Ans2=null,Ans3=null,Ans4=null;
	boolean NewEntry = false,Scrolling = false,Initial = false;
	//ArrayList list = new ArrayList();
	ArrayList list;	
	int seconds,minutes,hours,Timed = 0;
	PrintWriter out;
	String Type = null;	
	int temp = 0;
	int TId = 0;
	
    /*-------------------------- Servlet Initialization ------------------------*/
	         
	
	public void init(ServletConfig conf)
	throws ServletException
	{
	  super.init(conf);
      try
      {
        pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
        con = pool.getConnection();
      }  
 	  catch(Exception e) 
	  {
	    System.err.println("Problem in TestPage Initialization "+e.getMessage());
	    e.printStackTrace(out);
	  }	  	     	
	}
	
    /*--------------------- Initialization Function --------------------------------*/	         	

   public void Initialize(int Test_Id,int User_Id)
   {
      Q_Load = 0;
      Q_Display = 0;
      Ques_Id = 0;
      option = 0;
      max = 0;
	  Next="Next Question";
	  Prev=null;
	  Ans1=null;
	  Ans2=null;
	  Ans3=null;
	  Ans4=null;
	  NewEntry = false;
	  Scrolling = false;
	  Initial = false;
	  list = new ArrayList();
      Timed = 0;
      Type = null;
      temp = 0;	
      TId = Test_Id;	 
	 
	  Statement stmt1 = null;	
	  ResultSet res = null,rs1 = null;
	  int temp = 0;
	  int Test_Type = 0;
	  int min =0, hrs = 0, sec = 0;
      java.util.Date currentDate;        
      Log file = new Log("C:/Log.dat");       
      
      try
      {
        stmt1=con.createStatement();	 	
	    res=stmt1.executeQuery("select Scrolling,Ques_Type,Time,Timed,Test_Type,Total_Ques " +
	                        " from Test where Test_Id = " + Test_Id);    		
	    res.next();                        	    
	    temp = res.getInt("Scrolling");
	    if(temp == 0)
	      Scrolling = false;
	    else if (temp == 1)
	      Scrolling = true;
	    max = res.getInt("Total_Ques");
	    Timed = res.getInt("Timed");
	    
	    if(Timed == 1)
	    {
	      minutes = res.getInt("Time");	    	

	      if(minutes > 59)
	      {
	    	hours = minutes / 60;
	    	minutes = minutes % 60; 
	      }
	    
          currentDate = new java.util.Date();        
          min= currentDate.getMinutes();
          hrs = currentDate.getHours();
          sec= currentDate.getSeconds();

          System.out.println("<<<<<<<<<<<<<<hours           >>>>>>>>>> " + hours);
          System.out.println("<<<<<<<<<<<<<<minutes         >>>>>>>>>>> " + minutes);            	    

	      seconds = seconds + sec;	    
	    
	      if(seconds >= 60)
	        minutes++;
	      
	      minutes = minutes + min;
	    
	      if(minutes >= 60)
	        hours++;
	      	    
	      hours = hours + hrs;
	    
	      if(hours >= 24)
	        hours = hours - 24;
	    
          System.out.println(">>>>>>>hours>>>>>>>>>>>>>>>>>>>> " + hours);
          System.out.println(">>>>>>>minutes>>>>>>>>>>>>>>>>>>>> " + minutes);            	    	      
	    }
	    
	    Test_Type  = res.getInt("Test_Type");
	    
	    if(Test_Type == 1)
	    {
	      Type = res.getString("Ques_Type");	    	
	    }	    
	    else if(Test_Type == 0)
	    {
	      Type = "None"; 
	    }
	    
	      System.out.println(">>>>>>>Type>>>>>>>>>>>>> " + Type);	    
	  }
	  catch(Exception e) 
	  {
	    System.err.println("Problem in Test Retrival "+e.getMessage());
	    e.printStackTrace(out);
	    file.writeLog(User_Id,"Error in Test Initialization Fn");
	  }	  	  
	  System.out.println("TestPage Initialized Successfully !!");   	
	  file.writeLog(User_Id,"Test Initialized Successfully");	  
   }	
   
    /*------------------------ do Get Function --------------------------------*/
	         
   
   public void doGet(HttpServletRequest req,HttpServletResponse res)
   throws ServletException,IOException
   {
     HttpSession session=req.getSession(false);
    
     System.out.println("Initial == " + Initial);
/*     if(Initial == true) 
     {    	
      int SQues_Id = (Integer)session.getAttribute("Ques_Id");
      System.out.println(" SQues_Id =                          " + SQues_Id);
     }
*/   	
    Statement smt=null;
    ResultSet rs = null;
    int hr = 0, mn = 0,sc = 0;
	int User_Id = 0,Post_Id = 1,Test_Id = 0,Cat_Id = 0;
	int opn1=0,opn2=0,opn3=0,opn4=0;
	String Cat_Name = null;
    Log file = new Log("C:/Log.dat");     
                 
    User_Id = (Integer)session.getAttribute("User_Id");
    if(User_Id == 0)
    {
      System.out.println("Error in Session (User_Id) !!");
    }
    System.out.println(" TestPage -> User_Id = "+ User_Id);

    Post_Id = (Integer)session.getAttribute("Post_Id");
    if(Post_Id == 0)
    {
      System.out.println("Error in Session (Post_Id) !!");
    }
    System.out.println(" TestPage -> Post_Id = "+ Post_Id);
 
    Cat_Id = (Integer)session.getAttribute("Cat_Id");
    if(Cat_Id == 0)
    {
      System.out.println("Error in Session (Cat_Id) !!");
    }
    System.out.println(" TestPage -> Cat_Id = "+ Cat_Id);
      
    Test_Id = (Integer)session.getAttribute("Test_Id");
    if(Test_Id == 0)
    {
      System.out.println("Error in Session (Test_Id) !!");
    }
    System.out.println("Test Page -> Test_Id = "+ Test_Id);
    
    System.out.println("TId = " + TId); 
    System.out.println("Temp = " + temp);     
    
    if(TId != Test_Id)
    {
      temp = 0;
      Initial = false;      
    }
    
    if(Initial == false)
    {
    	Initialize(Test_Id,User_Id);
    	Initial = true;
    	temp++;
	    file.writeLog(User_Id,"Test of Test_Id " + Test_Id + " Starts");	      	
    }
    
    try
    {
      ResultSet res1 = null;	          
      Statement stmt = con.createStatement();	 	
	  res1 = stmt.executeQuery(" select Name from Category where Category_Id = " + Cat_Id);
      res1.next();  
      Cat_Name = res1.getString(1);    	
    }
    catch(Exception e) 
	{
	  System.err.println("Problem in TestName Retrival "+e.getMessage());
	  e.printStackTrace(out);
	  file.writeLog(User_Id,"Error in Test Name Retrival");    	
    }
    
    /*--------------------- Timer Checking and Display -----------------------------*/
	         
    if (Timed == 1)
    {
      java.util.Date curtDate;            
      int mins = 0,hrs = 0;
    
      curtDate = new java.util.Date();        
      sc= curtDate.getSeconds();    
      mn= curtDate.getMinutes();
      hr = curtDate.getHours();    
    
      if(sc >= seconds | sc == 0) 
      {
      	if(mn >= minutes | mn == 0)
      	{
      		if(hr >= hours | hours == 0)
      		{
              Ans1 = null;
              Ans2 = null;
              Ans3 = null;
              Ans4 = null;
     	      file.writeLog(User_Id,"Timeout of the Test");	           
              /*out.println("<HTML>");
              out.println("<HEAD>");
              out.println("<TITLE>Prompt</TITLE>");
              out.println("</HEAD>");
              out.println("<BODY bgcolor=\"ivory\">");	   		
              out.print("<h1> Application for Post Denied !! </h1>");	   		
              out.print("<h1> Please try after 6 months !! </h1>");
              out.println("<P>&nbsp;</P> " + "<P>" +
	   	 	              "<FORM NAME=\"Prompt\" ACTION=\"http://localhost:8080/examples/servlets/User/ReviewAnswers.jsp\">" +				 
			  	          "<INPUT name=\"Ok\" type=\"submit\" value=\" OK  \">" +
		                  "</FORM>");          
              out.println("</BODY>");
              out.println("</HTML>");	        	      */
    	      res.sendRedirect("http://localhost:8080/examples/servlets/User/ReviewAnswers.jsp");              
      		}
      	}
      }    

      System.out.println("****>>>" + hr + ">>>>>" + mn + ">>>>>>>" + sc + ">>>>>>>> ");
    
      mins = minutes;
      hrs = hours;
    
      if(seconds < sc)
      {
        sc = seconds - sc + 60;    
        mins = mins - 1;	
      }    
      else
        sc = seconds - sc;    
      
      if(mins < mn)
      {
    	mn = mins - mn + 60;
    	hrs = hrs - 1;
      }
      else
        mn = mins - mn;
      
      if(hrs < hr)
        hr = hrs - hr + 24;
      else  
        hr = hrs - hr;
    
      System.out.println(">>>" + hours + ">>>>>" + minutes + ">>>>>>>" + seconds + ">>>>>>>> ");
      System.out.println("****>>>" + hr + ">>>>>" + mn + ">>>>>>>" + sc + ">>>>>>>> ");    	
    }
        
	res.setContentType("text/html");
    out=res.getWriter();	
	out.println("<html>");
	out.println("<head>");
	out.println("<META NAME=\"GENERATOR\" Content=\"Microsoft Visual Studio 6.0\">");
	out.println("<title>");
	out.println(Cat_Name + " Test");
	out.println("</title>");
	out.println("</head>");
	
	if(Timed == 1)
	{
	  out.println("<script Language=\"JavaScript\"> ");

      out.println("var timerID = null;" + 
				  "var timerRunning = false; " +
				  "var hours = " + hr + ";" + 
				  "var minutes = " + mn + ";" + 
				  "var seconds = " + sc + "; ");

	  out.println("function stopclock (){ " +
                  "if(timerRunning) " +
				  "clearTimeout(timerID); " +
                  "timerRunning = false;" + 
                  "}");
                
      out.println("function startclock () { " +
                  "stopclock(); " + 
                  "showtime(); " +
                  "} ");


      out.println("function showtime () { " +
                  "if(seconds == 0 & minutes == 0  & hours == 0)" +
                  "{ }");
      out.println("else" +
                  "{");
       out.println("if(seconds > 0)" +
                  "{" +
                     "seconds = seconds - 1;" +
                  "} ");
      out.println("else if(seconds == 0)" +
                  "{" +
                      "seconds = 60;" +
                    "if(minutes == 0)" + 
                    "{"); 
           out.println("if(hours == 0)" + 
                       "{" + 
                         "hours = 1;" + 
                       "}  "); 
           out.println("if(hours > 0)" + 
                       "{" + 
                         "hours = hours - 1;" + 
                       "}" + 
                       "minutes = 59;"); 
        out.println("}" + 
                    "else" + 
                    "{" + 
                      "minutes = minutes - 1;" + 
                    "}" + 
                  "}" + 
                "}"); 

      out.println("var timeValue = \"\" + ((hours >12) ? hours -12 :hours) ");
      out.println("timeValue += ((minutes < 10) ? \":0\" : \":\") + minutes "); 
      out.println("timeValue += ((seconds < 10) ? \":0\" : \":\") + seconds "); 

      out.println("document.clock.face.value = timeValue; ");

      out.println("timerID = setTimeout(\"showtime()\",1000); ");
      out.println("timerRunning = true; ");
      out.println("}"); 

      out.println("</script> ");

	  out.println("<body bgcolor=ivory text=\"#000000\" link=\"#0000ff\"" + 
                 "alink=\"#008000\" vlink=\"800080\" onLoad=\"startclock()\">");		
	}
	else
	{
		out.println("<body bgcolor=ivory>");
	}
	out.println("<h1>");
	out.println(Cat_Name + " Test");
    out.println("</h1>");
	out.println("<hr>");
	
	if(Timed == 1)
	{
      out.println("<form name=\"clock\" onSubmit=\"0\"> "); 
      out.println("<p style=\"font-family: Arial;font-size: 16pt\" align=\"right\">Time Elapsed : ");
      out.println("<input type=\"text\" name=\"face\" size=3 align=\"right\" ");
      out.println("readonly=\"true\" style=\"font-family: Arial;font-size: 16pt\" maxlength=\"7\">"); 
      out.println("</p>");      
	}
		
	out.println("<FORM NAME=\"Question21\" METHOD=post " + 
	            "ACTION=\"http://localhost:8080/examples/servlet/TestPage\">");
	
	int val = 0,i=0;
	boolean equal;
	String temp=null;
    
    System.out.println("Q_Load %%**%%**%%**%%**%%**%%**%%**%%**> " + Q_Load);            
    
    if(Scrolling == false)
      Prev = null;
                
    if (Q_Load > 0)
    {
      try
      {       
        smt=con.createStatement();	 	
	    rs=smt.executeQuery("select Type from Questions where Ques_Id = " + 
	                           Ques_Id);    		
	    rs.next();                        
        String typ = rs.getString(1);
        
        try
        {
          if(typ.equals("MA"))
          {
            if((Ans1!=null) && (Ans1.length()!=0))
               opn1 = Integer.parseInt(Ans1);                          
            if((Ans2!=null) && (Ans2.length()!=0))
               opn2 = Integer.parseInt(Ans2);
            if((Ans3!=null) && (Ans3.length()!=0))
               opn3 = Integer.parseInt(Ans3);
            if((Ans4!=null) && (Ans4.length()!=0))
               opn4 = Integer.parseInt(Ans4);

            if(Ans1 ==null && NewEntry == true)
            	 opn1 = 0;        	
            if(Ans2 ==null && NewEntry == true)
            	 opn2 = 0;        	
            if(Ans3 ==null && NewEntry == true)
            	 opn3 = 0;        	
            if(Ans4 ==null && NewEntry == true)
            	 opn4 = 0;        	

          	option = (opn1 * 1000) + (opn2 * 100) + (opn3 * 10) + opn4;
          }	
          else
          {
            if((Ans1!=null) && (Ans1.length()!=0))
               option = Integer.parseInt(Ans1);            
            if(Ans1 ==null && NewEntry == true)
           	   option = 0;        	           	
          }           	 
        }
        catch(NumberFormatException exp)
        {
      	  System.out.println("Problem in Ans Format");
     	  System.err.println(exp.getMessage());
     	  file.writeLog(User_Id,"Error in Ans Formatting");	  
        }
        
      	System.out.println("NewEntry -> " + NewEntry);
        AnsEntry(con,Ques_Id,option,NewEntry,Q_Load,User_Id,Post_Id,Test_Id);        
        NewEntry = false;    	
	  }
	  catch (SQLException sqlep)
	  {
	     System.out.println("EEError in Ans Entry -> " + sqlep);
     	 file.writeLog(User_Id,"Error in Ans Entry");	  	     
	  }	 
    }
    
    /*--------------- Actual Analysis Begins ----------------------------------*/
	         
    System.out.println("PPPREV -> " + Prev);
    System.out.println("NNNEXT -> " + Next);
    
    if(Next != null)
    {
      if(Q_Display == Q_Load)
      {
	   if(list.size() < max)
	   {				 
         if(list.size() == 0)
         {
            try
            {
       	      do
       	      {
	             Ques_Id = doCallableStmt(con,Cat_Id,Type);                	 	
       	      }   while(Ques_Id == 0);
	        }
	        catch (SQLException sqlep)
	        {
	          System.out.println("EEError in Random -> " + sqlep);
	          file.writeLog(User_Id,"Error in Random Question Generation");	  
	        }	  
         }
         else
         {
           do
           {
       	     equal=false;
             try
             {
               do
               {
           	     Ques_Id = doCallableStmt(con,Cat_Id,Type);         
               }while(Ques_Id == 0);	       
	         }
	         catch (SQLException sqlep)
	         {
	            System.out.println("EEError in Random -> " + sqlep);
	            file.writeLog(User_Id,"Error in Random Question Generation");	  	            
	         }	  
	         i=0;
	         while(equal != true & i < list.size())
	         {	      
	 	         val = (Integer)list.get(i);
	 	         if(val == Ques_Id)
	 	         {
	 	  	        equal = true;
	 	  	        System.out.println("Repetition !!");
	 	         }
	 	         i++;  
	         }
           }while(equal == true);    	
         }
         list.add(Ques_Id);	  	 
         System.out.println("-------------- List = " + list);	  	 	      	
         Q_Display++;      
         Q_Load++;
         NewEntry=true;   	    		    	  		 	  
       }         
      }
      else if(Q_Display < Q_Load)
      {
      	Q_Display++;
      	Ques_Id = (Integer)list.get(Q_Display - 1);
      }               
    }
    else if(Prev != null)
    {
    	if(Q_Display > 1)
    	{
    		Q_Display = Q_Display - 1;
      	    Ques_Id = (Integer)list.get(Q_Display - 1);		
    	}
    }
    
    if(NewEntry == false)
    {
    	try
    	{
    	  rs = null;	
          smt=con.createStatement();	 	
	      rs=smt.executeQuery("select N.User_Answer from TestQuestions," + 
                              "Table(TestQuestions.TestEntry)N where User_Id = " + User_Id +
                              " and Post_Id = " + Post_Id + " and Test_Id = " + Test_Id +
                              " and N.Ques_Id = " + Ques_Id);    		
	      rs.next();                        
	      String str = rs.getString(1);	      
	      if((str!=null) && (str.length()!=0))
            option = Integer.parseInt(str);
          System.out.println("OOOOOOOOOOOOOOOOOOOpion -> " + option);  
    	}
    	catch (Exception ep)
	    {
	      System.out.println("EEError in GetOption -> " + ep);
	      file.writeLog(User_Id,"Error in Getting Previous Answers");	  
	    }	 
    }
    else if(NewEntry == true)    
      option = 0;
    
    try
    {
       session.setAttribute("Ques_Id",Ques_Id);	
       Display(out,con,Ques_Id,option);
	}
	catch (Exception ep)
	{
	  System.out.println("EEError in Display -> " + ep);
	  file.writeLog(User_Id,"Error in Question Display");	  	  
	}	 
	
	System.out.println("###############  Q_Display -> " + Q_Display);  	     	     	
	System.out.println("###############  Q_Load -> " + Q_Load);  	     	     	
    	
	out.println("<BR><BR> ");
	out.println("<P>");
	out.println("<INPUT name=\"Prev\" type=\"submit\" value=\"Prev Question\">");
	out.println("&nbsp;&nbsp;");
	out.println("<INPUT name=\"Next\" type=\"submit\" value=\"Next Question\">");
	out.println("</FORM>");
	out.println("<P>");
	out.println("<FORM NAME=\"ReviewAnswers\" ACTION=\"http://localhost:8080/examples/servlets/User/ReviewAnswers.jsp\">");
	out.println("<INPUT name=\"SubmitTest\" type=\"submit\" value=\" Submit Test  \">");
    out.println("</FORM>");
	out.println("</P>");
	out.println("</body>");
	out.println("</html>");
	
   }
   
    
    	
	/*  ----------------------    Oracle Function    ----------------------  */
	
	private int doCallableStmt(Connection con,int Category_Id,
						   String Type)		        
	throws SQLException{
	  	
	   CallableStatement statemt = null;
	   long no = 0;
	   double num = 0;
	   int Ques_Id = 0;

	   num = (double) Math.random();	
	   num = 100 * num;
	   no = (long) num;
       Ques_Id = (int)no;
       System.out.println("Function	 Ques_id = " + Ques_Id + "type is " + Type);	
       System.out.println("Category_id = " + Category_Id);	  	 	   
       try
       {     
	  	 String fun = " ? = call Random(?,?,?) "; 
		 statemt = con.prepareCall("{" + fun + "}");		 		
		 statemt.setInt(2,Category_Id);
		 statemt.setString(3,Type);
		 statemt.setInt(4,Ques_Id);
		 statemt.registerOutParameter(1,Types.INTEGER);
		 statemt.execute();
		 Ques_Id = statemt.getInt(1);		 
		 System.out.println("Function Returns = " + Ques_Id);		
	   }
	   catch (SQLException sqlep)
	   {
		 System.out.println("EEError in Random -> " + sqlep);
	   }	  
	   finally {
	   	if(statemt != null)
	   	{
	      statemt.close(); 	   		
	   	}	   
	   }
	   return(Ques_Id);
	}

/*------------------------  Ans Entry  ----------------------------------*/


    private void AnsEntry(Connection con,int Ques_Id,
						   int option,boolean Entry,int QLoad,int User_Id,
						   int Post_Id,int Test_Id)		        
	throws SQLException{
		
	   Statement statmt = null;
	   ResultSet res = null;
	   String query = null;
	   int val,Correct = 0;
	   CallableStatement stamt = null;
	                
       System.out.println("Option is **************************** = " + option);
       System.out.println("Type = " + Type + "  Entry = " + Entry);
	   try
	   {
	   	statmt=con.createStatement();		 					
		if(Entry == true)
		{
		  res = statmt.executeQuery("select Type from Questions where Ques_Id = " + Ques_Id);
		  res.next();
		  String str = res.getString(1);
		 
		  query = null;
		  
		  if(str.equals("MC"))	
		  {
		  	query =  "select Correct_Ans from MC_Answers where Ques_Id = " + Ques_Id;
		  }
		  else if(str.equals("TF"))
		  {
		  	query =  "select Correct_Ans from TF_Answers where Ques_Id = " + Ques_Id;
		  }	
		  else if(str.equals("MA"))
		  {
		  	query =  "select Correct_Ans from MA_Answers where Ques_Id = " + Ques_Id;
		  }		
		  		  		  
		  res = null;
		  res = statmt.executeQuery(query);
		  res.next();
		  Correct = res.getInt(1);		  	
		  System.out.println("Correct = " + Correct);			  					
	  	  String qry = " ? = call Checked(?,?,?,?,?,?,?) "; 
		  System.out.println("Before correct1 !!" + qry);	
		  stamt = con.prepareCall("{" + qry + "}");		
		  stamt.setInt(2,User_Id);
		  stamt.setInt(3,Post_Id);		  
		  stamt.setInt(4,Test_Id);
		  stamt.setInt(5,QLoad);
		  stamt.setInt(6,Ques_Id);
		  stamt.setInt(7,option);
		  stamt.setInt(8,Correct);			
		  stamt.registerOutParameter(1,Types.INTEGER);		  
		  System.out.println("before exe");
		  stamt.execute();
		  val = stamt.getInt(1);		 
		  System.out.println("11111111111111111111111111Entry = " + val);					
		}
		else if(Entry == false)
		{
	      query = null;		
		  query = "Update Table(select TestEntry from TestQuestions where User_Id = " +
		           User_Id + " and Test_Id = " + Test_Id + 
		          ")N set N.User_Answer = " + option +
		  		  " where N.Ques_Id = " + Ques_Id;		
		  System.out.println("EEError in Query -> " + query);				  				
		  statmt.executeUpdate(query);		  
		}		
	   }
	   catch(SQLException e)
	   {
		  System.out.println("EEError in AnsEntry -> " + e);		
	   }                        
	  finally {
	   	if(statmt != null)
	   	{
	      statmt.close(); 	 	      
	   	}	   
	   	if(stamt != null)
	   	{
	   	  stamt.close();  		
	   	}
	  }
	} 
		
/*------------------------  Display Questions  ----------------------------------*/


	private void Display(PrintWriter out,Connection con,int Ques_Id,int option)
	{
	 Statement smt,smt1=null;
	 ResultSet rs1 = null;
	 int count=0;
	 int opn1=0,opn2=0,opn3=0,opn4=0;

	 try
	 {
       System.out.println("Ques_Id = " + Ques_Id);
       System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDoption = " + option);
       smt=con.createStatement();
	   smt1=con.createStatement();
	 	
	 	
	   ResultSet rs=smt.executeQuery("select Question,Type from Questions where Ques_Id = "
	                              + Ques_Id);
     	 
	   if(rs.next())
	   {
	  		 	
	    out.println("<P><STRONG>Question :</STRONG>    &nbsp;&nbsp;&nbsp;");
	    out.println(rs.getString("Question"));
        out.println("</P>");
        out.println("<INPUT name=\"Ques_Id\" type=\"hidden\" value=\"" + Ques_Id + "\">");
        	        
        String temp = rs.getString("Type");
	 
	    if(temp.equals("TF"))
	    {	    	
	     out.println("<P><STRONG>True Or False:</STRONG></P>");
	     if(option == 1)
	       out.println("<INPUT name=\"Ans1\" type=\"radio\" value=\"1\" checked> True<br>");
	     else  
	       out.println("<INPUT name=\"Ans1\" type=\"radio\" value=\"1\"> True<br>");
	     if(option == 2)
	       out.println("<INPUT name=\"Ans1\" type=\"radio\" value=\"2\" checked> False<br>");	     	
	     else  
  	       out.println("<INPUT name=\"Ans1\" type=\"radio\" value=\"2\"> False<br>");	     		     
	    }
	    else if (temp.equals("MC"))
	    {
	      rs1 = smt1.executeQuery("select Ans1,Ans2,Ans3,Ans4 from " + 
	       									"MC_Answers where Ques_Id = " +
	       									 Ques_Id);	  
	   	  rs1.next();
	   	  out.println("<P><STRONG>Answers:</STRONG></P>");	     												
	      count = 0;
	      while(count < 4)
	      {
	      	if(option == (count + 1))
	      	{
	          out.println("<INPUT name=\"Ans1\" type=\"radio\"" +
	                      " value=\"" + (count + 1) + "\" checked>");	     		      		
	      	}
	      	else
	      	{
	          out.println("<INPUT name=\"Ans1\" type=\"radio\"" +
	                      " value=\"" + (count + 1) + "\">");	     		      			      		
	      	}
 		    out.println(rs1.getString(count + 1));
    	    out.println("<br>");
    	    count++;
	      }		   	   	
		}
		else if(temp.equals("MA"))
		{
		  rs1 = null;	
	      rs1=smt1.executeQuery("select Ans1,Ans2,Ans3,Ans4 from " + 
	       									"MA_Answers where Ques_Id = " +
	       									 Ques_Id);	  
	   	  rs1.next();
	   	  out.println("<P><STRONG>Answers:</STRONG></P>");	     												
	      count = 0;	      	      
	      
	      opn4 = option % 10;
	      opn3 = ((option % 100) - opn4)/10;
	      opn2 = ((option % 1000) - opn3 - opn4)/100;
	      opn1 = (option - opn2 - opn3 - opn4)/1000;
	      
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn1);
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn2);
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn3);
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn4);	      	      	      
	      
	      out.println("<P><STRONG>Answers:</STRONG></P>");
	     
	      if(opn1 == 1)
	        out.println("<INPUT name=\"Ans1\" type=\"checkbox\" value=\"1\" checked>&nbsp;&nbsp;" + 
	                  rs1.getString(1) + "<br>");
	      else  
	        out.println("<INPUT name=\"Ans1\" type=\"checkbox\" value=\"1\">&nbsp;&nbsp;" + 
	                  rs1.getString(1) + "<br>");
	      if(opn2 == 2)
	        out.println("<INPUT name=\"Ans2\" type=\"checkbox\" value=\"2\" checked>&nbsp;&nbsp;" + 
	                  rs1.getString(2) + "<br>");
	      else  
	        out.println("<INPUT name=\"Ans2\" type=\"checkbox\" value=\"2\">&nbsp;&nbsp;" +
	                  rs1.getString(2) + "<br>");
	      if(opn3 == 3)
	        out.println("<INPUT name=\"Ans3\" type=\"checkbox\" value=\"3\" checked>&nbsp;&nbsp;" +
	                  rs1.getString(3) + "<br>");
	      else  
	        out.println("<INPUT name=\"Ans3\" type=\"checkbox\" value=\"3\">&nbsp;&nbsp;" +
	                  rs1.getString(3) + "<br>");
	      if(opn4 == 4)
	        out.println("<INPUT name=\"Ans4\" type=\"checkbox\" value=\"4\" checked>&nbsp;&nbsp;" +
	                  rs1.getString(4) + "<br>");
	      else  
	        out.println("<INPUT name=\"Ans4\" type=\"checkbox\" value=\"4\">&nbsp;&nbsp;" +
	                  rs1.getString(4) + "<br>");	      
		}		   
		rs1.close();		     			
	   }	    
	   rs.close();	  	 
	   smt.close();
	   smt1.close();	 
	 }
	 catch(Exception e)
	 {
	  System.out.println("Problem in Display Questions !");
	  System.out.println("Message" + e.getMessage());	
	  System.out.println(e.getStackTrace());
	 }                        
	}	 
	
	/*-------------------------- do Post Function ----------------------------------*/
    	    	    	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	  HttpSession session=req.getSession(true);	 	  
	  String temp = req.getParameter("Ques_Id");
      
      if((temp!=null) && (temp.length() !=0))
      {
        req.setAttribute("Choice",temp);
      }
	  
      Ans1 = req.getParameter("Ans1");      
      if((Ans1!=null) && (Ans1.length()!=0))
      {
        req.setAttribute("Choice",Ans1);
      }
      
      Ans2 = req.getParameter("Ans2");      
      if((Ans2!=null) && (Ans2.length()!=0))
      {
        req.setAttribute("Choice",Ans2);
      }

      Ans3 = req.getParameter("Ans3");      
      if((Ans3!=null) && (Ans3.length()!=0))
      {
        req.setAttribute("Choice",Ans3);
      }

      Ans4 = req.getParameter("Ans4");      
      if((Ans4!=null) && (Ans4.length()!=0))
      {
        req.setAttribute("Choice",Ans4);
      }
      
      Prev = req.getParameter("Prev");      
      if((Prev!=null) && (Prev.length()!=0))
      {
        req.setAttribute("Choice",Prev);
      }
            
      Next = req.getParameter("Next");      
      if((Next!=null) && (Next.length()!=0))
      {
        req.setAttribute("Choice",Next);
      }
      
      doGet(req,res);
	}
}

