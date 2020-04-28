package ORS.User;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.ConnPool.*;

public class TestPage extends HttpServlet
{
  private Connection con=null;
  private PrintWriter out;
  private int Q_Load = 0,Q_Display = 0;
  private int CId = 0,option = 0,max = 0;
  private long Ques_Id = 0;
  private Calendar FinalTime = null;
  private String Next="Next Question",Prev=null;
  private String Ans1=null,Ans2=null,Ans3=null,Ans4=null;
  private String Type = null;	
  private boolean NewEntry = false,Scrolling = false,Initial = false,Hide = false;
  private ArrayList list;
	
  /*-------------------------- Servlet Initialization ------------------------*/
	
  
  public void init(ServletConfig conf)
  throws ServletException
  {
    super.init(conf);
    try
    {
      ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
      con = pool.getConnection();
    }  
    catch(Exception e) 
	{
	  System.err.println("Problem in TestPage Initialization "+e.getMessage());
	  e.printStackTrace(out);
	}	  	     	
  }
	
  /*--------------------- Initialization Function --------------------------------*/

  private void Initialize(long User_Id,int Post_Id,int Cat_Id,int Test_Id)
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
	Hide = false;
	Initial = false;
	list = null;
	list = new ArrayList();
    FinalTime = null;
    Type = null;
    CId = Cat_Id;
	 
	Statement stmt1 = null;	
	ResultSet res = null,rs1 = null;
	int temp = 0;
	int Test_Type = 0;
    Log file = new Log("C:/Log.dat");
     
    try
    {
      String Query = null;
      stmt1=con.createStatement();
	  
	  Query = "Select N.Ques_Id from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id = " +
	          User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id;
	  
	  res=stmt1.executeQuery(Query);
	  
	  while(res.next())
	  {
	  	Ques_Id = res.getLong(1);
	  	list.add(Ques_Id);
        Q_Display++;
        Q_Load++;
	  }
      
      System.out.println("load = " + Q_Load + " display = " + Q_Load);
	  
	  res = null;
	  Query = "Select Scrolling,Ques_Type,Time,Hide,Test_Type,Total_Ques " +
	          "from Test where Test_Id = " + Test_Id;
	  
	  res=stmt1.executeQuery(Query);
	  res.next();
	  temp = res.getInt("Scrolling");
	  
	  if(temp == 1)
	    Scrolling = true;
	  else
	    Scrolling = false;
	  
	  max = res.getInt("Total_Ques");
	  
	  temp = 0;
	  temp = res.getInt("Hide");	  
	    
	  if(temp == 1)
	    Hide = true;
	  else
	    Hide = false;
	    
	  int minutes = 0;
	  minutes = res.getInt("Time");
	  System.out.println("load = " + Q_Load + " time = " + minutes);
	    
	  int seconds = minutes * 60;
	    
	  if(Q_Load != 0)
	    seconds = (seconds * (max - Q_Load))/max;
	    
	  System.out.println("Sec = " + seconds);
	  seconds = seconds + 1;                      // Extra one second allotted to the candidate

      FinalTime = Calendar.getInstance();
      FinalTime.add(Calendar.SECOND,seconds);

      /*int min = FinalTime.get(Calendar.MINUTE);
        int hrs = FinalTime.get(Calendar.HOUR_OF_DAY);
        int sec = FinalTime.get(Calendar.SECOND);
        System.out.println(">>>>>>>hours>>>>>>>>>>>>>>>>>>>> " + hrs);
        System.out.println(">>>>>>>minutes>>>>>>>>>>>>>>>>>>>> " + min);*/
	    
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
	catch(SQLException ex)
	{
	  ex.printStackTrace();
	  out.println("Problem in Test Retrival "+ ex.getMessage());
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
    
    Statement smt=null;
    ResultSet rs = null;
    int hr = 0, mn = 0,sc = 0;
	long User_Id = 0;
	int Post_Id = 0,Test_Id = 0,Cat_Id = 0;
	int opn1=0,opn2=0,opn3=0,opn4=0;
	boolean timeout = false,Entry = true;
	String Cat_Name = null;
    Log file = new Log("C:/Log.dat");
                 
    User_Id = (Long)session.getAttribute("User_Id");
    if(User_Id == 0)
    {
      System.out.println("Error in Session (User_Id) !!");
    }
    //System.out.println(" TestPage -> User_Id = "+ User_Id);

    Post_Id = (Integer)session.getAttribute("Post_Id");
    if(Post_Id == 0)
    {
      System.out.println("Error in Session (Post_Id) !!");
    }
    //System.out.println(" TestPage -> Post_Id = "+ Post_Id);
 
    Cat_Id = (Integer)session.getAttribute("Cat_Id");
    if(Cat_Id == 0)
    {
      System.out.println("Error in Session (Cat_Id) !!");
    }
    //System.out.println(" TestPage -> Cat_Id = "+ Cat_Id);
      
    Test_Id = (Integer)session.getAttribute("Test_Id");
    if(Test_Id == 0)
    {
      System.out.println("Error in Session (Test_Id) !!");
    }
    //System.out.println("Test Page -> Test_Id = "+ Test_Id);
    
    //System.out.println("CId = " + CId); 
    
    //System.out.println("Initial == " + Initial);
    
    if(CId != Cat_Id)
    {
      Initial = false;
    }
    
    if(Initial == false)
    {
      Entry = false;
      Initialize(User_Id,Post_Id,Cat_Id,Test_Id);
      Initial = true;
	  file.writeLog(User_Id,"Test of Cat_Id " + Cat_Id + " Test_Id " + Test_Id + " Starts");
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
	         
    if (timeout != true)
    {

      Calendar CurTime = Calendar.getInstance();
      timeout = FinalTime.before(CurTime);
      
      if(timeout == true) 
      {
        Ans1 = null;
        Ans2 = null;
        Ans3 = null;
        Ans4 = null;
     	file.writeLog(User_Id,"Timeout of the Test");
     	timeout = true;
   	    //res.sendRedirect("/examples/servlets/User/ReviewAnswers.jsp");
      }    
      else if(Hide == false)
      {
        int mins = 0,hrs = 0,sec = 0;

      	mn= CurTime.get(Calendar.MINUTE);
        hr = CurTime.get(Calendar.HOUR_OF_DAY);
        sc= CurTime.get(Calendar.SECOND);

        mins = FinalTime.get(Calendar.MINUTE);
        hrs = FinalTime.get(Calendar.HOUR_OF_DAY);
        sec = FinalTime.get(Calendar.SECOND);
    
        if(sec < sc)
        {
          sc = sec - sc + 60;    
          mins = mins - 1;
        }
        else
          sc = sec - sc;
      
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
    
        System.out.println("****>>>" + hr + ">>>>>" + mn + ">>>>>>>" + sc + ">>>>>>>> ");
      }
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
	
	if(timeout != true)
	{
	 out.println("<script Language=\"JavaScript\"> ");

     out.println("function ReviewButton(form)"); 
     out.println("{"); 

     if(Scrolling == true)
     {
       out.println("document.TestPage.action=\"/examples/servlets/User/ReviewAnswers.jsp\";"); 
       out.println("document.TestPage.submit();");      	
     }

     out.println("}"); 

     out.println("function SubmitButton(form)"); 
     out.println("{"); 
     out.println("document.TestPage.action=\"/examples/servlet/End\";"); 
     out.println("document.TestPage.submit();"); 
     out.println("}"); 
	
	 if(Hide == false)
	 {
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

       out.println("function showtime () { ");
       out.println("if(seconds > 0)"); 
       out.println("{");
          out.println("seconds = seconds - 1;");
       out.println("}");
       out.println("else if(seconds == 0)");
       out.println("{");
         out.println("if(minutes == 0)");
         out.println("{"); 
           out.println("if(hours == 0)");
           out.println("{"); 
           out.println("}");
           out.println("else if(hours > 0)");
           out.println("{");
             out.println("hours = hours - 1;");
             out.println("minutes = 59;");
           out.println("}");  
         out.println("}"); 
         out.println("else if (minutes > 0)");
         out.println("{");
           out.println("minutes = minutes - 1;");
           out.println("seconds = 59;");
         out.println("} ");
       out.println("}");
       out.println("");

       out.println("var timeValue = \"\" + ((hours >12) ? hours -12 :hours) ");
       out.println("timeValue += ((minutes < 10) ? \":0\" : \":\") + minutes "); 
       out.println("timeValue += ((seconds < 10) ? \":0\" : \":\") + seconds "); 

       out.println("document.TestPage.face.value = timeValue; ");

       out.println("timerID = setTimeout(\"showtime()\",1000); ");
       out.println("timerRunning = true; ");
       out.println("}"); 

       out.println("</script> ");
	   out.println("<body bgcolor=ivory text=\"#000000\" link=\"#0000ff\"" + 
                  "alink=\"#008000\" vlink=\"800080\" onLoad=\"startclock()\">");
	 }
	 else
	 {
	   out.println("</script> ");
	   out.println("<body bgcolor=ivory>");
	 }
	 out.println("<h1 align=center>");
	 out.println(Cat_Name + " Test");
     out.println("</h1>");
	 out.println("<hr>");
			
	 out.println("<FORM NAME=\"TestPage\" METHOD=post " + 
	             "ACTION=\"/examples/servlet/TestPage\">");

	 if(Hide == false)
	 {
      out.println("<p style=\"font-family: Arial;font-size: 16pt\" align=\"right\">Time Elapsed : ");
      out.println("<input type=\"text\" name=\"face\" size=3 value=\"\" align=\"right\" ");
      out.println("readonly=\"true\" style=\"font-family: Arial;font-size: 16pt\" maxlength=\"7\">"); 
      out.println("</p>");      
	 }
	
	 long val = 0;
	 int i=0;
	 boolean equal;
	 String temp=null;
    
     //System.out.println("Q_Load %%**%%**%%**%%**%%**%%**%%**%%**> " + Q_Load);
    
     if(Scrolling == false)
       Prev = null;
                
     String NewDisid = req.getParameter("Display_Id");
	 System.out.println("Display_Id" + NewDisid);
     
     if(NewDisid != null)
     {
       Ans1 = req.getParameter("Ans1");
       Ans2 = req.getParameter("Ans2");
       Ans3 = req.getParameter("Ans3");
       Ans4 = req.getParameter("Ans4");
     }
     
     if (Q_Load > 0 && Entry == true)
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
        
      	 System.out.println(" AnsEntry -> qid=" + Ques_Id + " option=" + option + " Display=" + Q_Display);
      	 System.out.println(" AnsEntry ->Qload =  " + Q_Load + " NewEntry=" + NewEntry);
         AnsEntry(con,Ques_Id,option,NewEntry,Q_Load,User_Id,Post_Id,Cat_Id);
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
     
     if(NewDisid != null)
     {
       int t = 0;
      
       try
       {
      	 t = Integer.parseInt(NewDisid);
       }
       catch(NumberFormatException ex)
       { 
         t = 0;
       }
      
       if(t != 0)
       {
         Q_Display = t;
         System.out.println("ttttttt_Id" + t);
         Ques_Id = (Long)list.get(Q_Display - 1);
       }
     }
     else if(Next != null)
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
	          sqlep.printStackTrace();
	          out.println("EEError in Random -> " + sqlep);
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
	            sqlep.printStackTrace();
	            out.println("EEError in Random -> " + sqlep);
	            file.writeLog(User_Id,"Error in Random Question Generation");
	         }	  
	         i=0;
	         while(equal != true && i < list.size())
	         {	      
	 	         val = (Long)list.get(i);
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
      	Ques_Id = (Long)list.get(Q_Display - 1);
      }               
     }
     else if(Prev != null)
     {
    	if(Q_Display > 1)
    	{
    		Q_Display = Q_Display - 1;
      	    Ques_Id = (Long)list.get(Q_Display - 1);
    	}
     }
    
     if(NewEntry == false)
     {
    	try
    	{
    	  rs = null;
          smt=con.createStatement();
          System.out.println("uid = " + User_Id + " pid = " + Post_Id + " Cid = " + Cat_Id + " qid = " + Ques_Id);	 	
	      rs=smt.executeQuery("select N.User_Answer from TestQuestions," + 
                              "Table(TestQuestions.TestEntry)N where User_Id = " + User_Id +
                              " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id +
                              " and N.Ques_Id = " + Ques_Id);    		
	      rs.next();
	      String str = rs.getString(1);
	      if((str!=null) && (str.length()!=0))
            option = Integer.parseInt(str);
          System.out.println("Get Previous Answers/Options -> " + option);
    	}
    	catch (Exception ep)
	    {
	      ep.printStackTrace();
	      out.println("EEError in GetOption -> " + ep);
	      file.writeLog(User_Id,"Error in Getting Previous Answers");
	    }	 
     }
     else if(NewEntry == true)
       option = 0;
    
     try
     {
       Display(out,con,Ques_Id,option);
	 }
	 catch (Exception ep)
	 {
	   ep.printStackTrace();
	   out.println("EEError in Display -> " + ep);
	   file.writeLog(User_Id,"Error in Question Display");
	 }
	
	 System.out.println("###############  Q_Display -> " + Q_Display);
	 System.out.println("###############  Q_Load -> " + Q_Load);
    	
	 out.println("<BR><BR> " + "<P>&nbsp;&nbsp;&nbsp;" +
				 "<INPUT name=\"Prev\" type=\"submit\" value=\"Prev Question\">");
	 out.println("&nbsp;&nbsp;" +
				 "<INPUT name=\"Next\" type=\"submit\" value=\"Next Question\">");
	 out.println("&nbsp;&nbsp;" +	
				 "<INPUT name=\"ReviewTest\" type=\"button\" value=\" Review Test  \" onclick='ReviewButton(this.form)'>");
	 out.println("&nbsp;&nbsp;" +	
				 "<INPUT name=\"SubmitTest\" type=\"button\" value=\" Submit Test  \" onclick='SubmitButton(this.form)'>" +
				 "</P>");
	 out.println("</FORM>");
	 out.println("</body>");
	 out.println("</html>");
    }
    else
    {
	 out.println("<body bgcolor=ivory>");
	 out.println("<h1 align=center>");
	 out.println(Cat_Name + " Test");
     out.println("</h1>");
	 out.println("<hr>");
	 out.println("<center><h1>Sorry, The allotted time is over !!</h1></center>");
     out.println("<FORM action=\"/examples/servlet/End\" " +
                 " id=form1 name=form1 method=\"post\">");
     out.println("<P>&nbsp;</P><P><CENTER>");
   	 out.println("<INPUT name=\"Ok\" type=\"submit\" value=\" OK\"  style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER></P></FORM>");
	 out.println("</body>");
	 out.println("</html>");
    }
  }
    	
	/*  ----------------------    Oracle Function    ----------------------  */
	
  private long doCallableStmt(Connection con,int Category_Id,String Type)
  throws SQLException
  {	  	
	CallableStatement statemt = null;
	int no = 0;
	double num = 0;
	long Ques_Id = 0;

	num = (double) Math.random();	
	num = 100 * num;
	Ques_Id = (long) num;
    no = (int)Ques_Id;
    System.out.println("Function	 Ques_id = " + Ques_Id + "type is " + Type);
    //System.out.println("Category_id = " + Category_Id);
    
    try
    {     
	  String fun = " ? = call Random(?,?,?) "; 
	  statemt = con.prepareCall("{" + fun + "}");
	  statemt.setInt(2,Category_Id);
	  statemt.setString(3,Type);
	  statemt.setInt(4,no);
	  statemt.registerOutParameter(1,Types.INTEGER);
	  statemt.execute();
	  Ques_Id = statemt.getLong(1);		 
	  System.out.println("Function Returns = " + Ques_Id);
	}
	catch (SQLException sqlep)
	{
	  sqlep.printStackTrace();
	  out.println("EEError in Random -> " + sqlep);
	}	  
	finally
	{
	  if(statemt != null)
	  {
	    statemt.close();
	  }	   
	}
	return(Ques_Id);
  }

/*------------------------  Ans Entry  ----------------------------------*/


  private void AnsEntry(Connection con,long Ques_Id,
					    int option,boolean Entry,int QLoad,long User_Id,
					    int Post_Id,int Cat_Id)
  throws SQLException
  {		
    Statement statmt = null;
	ResultSet res = null;
	String query = null;
	int val,Correct = 0;
	                
    System.out.println("Option is **************************** = " + option);
    //System.out.println("Type = " + Type + "  Entry = " + Entry);
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

        int count = 0;
        query = null;
        query = "select count(rownum) from TestQuestions where User_Id = " + User_Id
                      + " and Post_Id = " + Post_Id  + " and Category_Id = " + Cat_Id;
        
        res = null;
        Statement stemt=con.createStatement();
        res = stemt.executeQuery(query);
        res.next();
        count = res.getInt(1);

        query = null;
        
        if(count > 0)
        {
          query = "insert into Table(select TestEntry from TestQuestions where User_Id = "
		          + User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id
                  + ") values (TestQues_TY("+ QLoad + "," + Ques_Id + "," + option + "," + Correct + "))";
        }
        else
        {
          query = "insert into TestQuestions values(" + User_Id + "," + Post_Id + "," + Cat_Id + "," +
		          "TestQues_NT(TestQues_TY("+ QLoad + "," + Ques_Id + "," + option + "," + Correct + ")))";
        }
        
        stemt.execute(query);
	  }
	  else if(Entry == false)
	  {
	    query = null;		
	    query = "Update Table(select TestEntry from TestQuestions where User_Id = " +
	             User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id + 
	            ")N set N.User_Answer = " + option +
	            " where N.Ques_Id = " + Ques_Id;
		statmt.executeUpdate(query);
	  }
	}
	catch(SQLException e)
	{
	  e.printStackTrace();
	  out.println("EEError in AnsEntry -> " + e);
	}
	finally
	{
	 if(statmt != null)
	 {
	   statmt.close();
	 }
	}
  } 
	
/*------------------------  Display Questions  ----------------------------------*/


  private void Display(PrintWriter out,Connection con,long Ques_Id,int option)
  {
	 Statement smt,smt1=null;
	 ResultSet rs1 = null;
	 int count=0;
	 int opn1=0,opn2=0,opn3=0,opn4=0;

	 try
	 {
       //System.out.println("Ques_Id = " + Ques_Id);
       //System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDoption = " + option);
       smt=con.createStatement();
	   smt1=con.createStatement();
	 	
	 	
	   ResultSet rs=smt.executeQuery("select Question,Type from Questions where Ques_Id = "
	                              + Ques_Id);
     	 
	   if(rs.next())
	   {
	  		 	
	    out.println("<P>&nbsp;&nbsp;&nbsp;<STRONG>Question :</STRONG>    &nbsp;&nbsp;&nbsp;");
	    out.println(rs.getString("Question"));
        out.println("</P>");
        out.println("<INPUT name=\"Ques_Id\" type=\"hidden\" value=\"" + Ques_Id + "\">");
        	        
        String temp = rs.getString("Type");
	 
	    if(temp.equals("TF"))
	    {	    	
	     out.println("<P>&nbsp;&nbsp;&nbsp;<STRONG>True Or False:</STRONG></P>");
	     if(option == 1)
	       out.println("&nbsp;&nbsp;&nbsp;<INPUT name=\"Ans1\" type=\"radio\" value=\"1\" checked> True<br>");
	     else  
	       out.println("&nbsp;&nbsp;&nbsp;<INPUT name=\"Ans1\" type=\"radio\" value=\"1\"> True<br>");
	     if(option == 2)
	       out.println("&nbsp;&nbsp;&nbsp;<INPUT name=\"Ans1\" type=\"radio\" value=\"2\" checked> False<br>");
	     else
  	       out.println("&nbsp;&nbsp;&nbsp;<INPUT name=\"Ans1\" type=\"radio\" value=\"2\"> False<br>");
	    }
	    else if (temp.equals("MC"))
	    {
	      rs1 = smt1.executeQuery("select Ans1,Ans2,Ans3,Ans4 from " + 
	       									"MC_Answers where Ques_Id = " +
	       									 Ques_Id);
	   	  rs1.next();
	   	  out.println("<P>&nbsp;&nbsp;&nbsp;<STRONG>Answers:</STRONG></P>");
	      count = 0;
	      while(count < 4)
	      {
	      	if(option == (count + 1))
	      	{
	          out.println("&nbsp;&nbsp;&nbsp;<INPUT name=\"Ans1\" type=\"radio\"" +
	                      " value=\"" + (count + 1) + "\" checked>");
	      	}
	      	else
	      	{
	          out.println("&nbsp;&nbsp;&nbsp;<INPUT name=\"Ans1\" type=\"radio\"" +
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
	      count = 0;	      	      
	      
	      opn4 = option % 10;
	      opn3 = ((option % 100) - opn4)/10;
	      opn2 = ((option % 1000) - opn3 - opn4)/100;
	      opn1 = (option - opn2 - opn3 - opn4)/1000;
	      
	      /*System.out.println(" OOOOOOOOOOOOpn111 = " + opn1);
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn2);
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn3);
	      System.out.println(" OOOOOOOOOOOOpn111 = " + opn4);*/
	      
	      out.println("<P>&nbsp;&nbsp;&nbsp;<STRONG>Answers:</STRONG></P>&nbsp;&nbsp;&nbsp;");
	     
	      if(opn1 == 1)
	        out.println("<INPUT name=\"Ans1\" type=\"checkbox\" value=\"1\" checked>&nbsp;&nbsp;" + 
	                  rs1.getString(1) + "<br>&nbsp;&nbsp;&nbsp;");
	      else  
	        out.println("<INPUT name=\"Ans1\" type=\"checkbox\" value=\"1\">&nbsp;&nbsp;" + 
	                  rs1.getString(1) + "<br>&nbsp;&nbsp;&nbsp;");
	      if(opn2 == 2)
	        out.println("<INPUT name=\"Ans2\" type=\"checkbox\" value=\"2\" checked>&nbsp;&nbsp;" + 
	                  rs1.getString(2) + "<br>&nbsp;&nbsp;&nbsp;");
	      else  
	        out.println("<INPUT name=\"Ans2\" type=\"checkbox\" value=\"2\">&nbsp;&nbsp;" +
	                  rs1.getString(2) + "<br>&nbsp;&nbsp;&nbsp;");
	      if(opn3 == 3)
	        out.println("<INPUT name=\"Ans3\" type=\"checkbox\" value=\"3\" checked>&nbsp;&nbsp;" +
	                  rs1.getString(3) + "<br>&nbsp;&nbsp;&nbsp;");
	      else  
	        out.println("<INPUT name=\"Ans3\" type=\"checkbox\" value=\"3\">&nbsp;&nbsp;" +
	                  rs1.getString(3) + "<br>&nbsp;&nbsp;&nbsp;");
	      if(opn4 == 4)
	        out.println("<INPUT name=\"Ans4\" type=\"checkbox\" value=\"4\" checked>&nbsp;&nbsp;" +
	                  rs1.getString(4) + "<br>&nbsp;&nbsp;&nbsp;");
	      else  
	        out.println("<INPUT name=\"Ans4\" type=\"checkbox\" value=\"4\">&nbsp;&nbsp;" +
	                  rs1.getString(4) + "<br>&nbsp;&nbsp;&nbsp;");
		}
		
		if(rs1 != null)
		  rs1.close();
	   }
	   rs.close();
	   smt.close();
	   smt1.close();
	 }
	 catch(SQLException e)
	 {
	  out.println("Problem in Display Questions : " + e.getMessage());
	  e.printStackTrace();
	 }
  }	 
	
	/*-------------------------- do Post Function ----------------------------------*/
    	    	    	
  public void doPost(HttpServletRequest req,HttpServletResponse res)
  throws ServletException,IOException
  {
    HttpSession session=req.getSession(false);
	  
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
