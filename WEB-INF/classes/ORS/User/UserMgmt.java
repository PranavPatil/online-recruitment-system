package ORS.User;

import java.sql.*;
import java.util.Calendar;
import javax.servlet.http.*;
import ORS.ConnPool.*;
import ORS.Exception.ApplicationException;
import ORS.Exception.DataEntryException;
import ORS.Model.*;
import ORS.Utils.Log;
import ORS.Controller.Entry;
import ORS.Controller.EntryList;


public class UserMgmt
{
  private ConnectionPool pool;
  private EntryList list;
  private Log log;
  
  public UserMgmt(ConnectionPool pl,EntryList lt,Log lg)
  {
	super();
	pool = pl;
	list = lt;
	log = lg;
  }
  
  public boolean UserLogin(HttpServletRequest request) 
  throws Exception
  {
	boolean flag = false;
	long User_Id = 0;
   	String Login = null,Passwd=null;

	Login=request.getParameter("User_Name");
	Passwd=request.getParameter("User_Passwd");	
	
	Database db = new Database(pool);
	ResultSet rs = db.RetriveDb("Select User_Id,Login,Password from Users");

	if(rs!=null)
	{
	  while(rs.next())
	  {
	    if(Passwd.equals(rs.getString("Password")) && Login.equals(rs.getString("Login")))
	    {
	      User_Id = rs.getLong("User_Id");
	      flag = true;
	      break;
	    }	   	
	  }

      if(flag==true)
	  {
	    HttpSession session=request.getSession(true);
	    System.out.println("Logged  :" + Login);
	    System.out.println("User_Id :"+ User_Id);
        session.setAttribute("User_Id",User_Id);
        //session.setAttribute("MAINEVENT","FIRSTPAGE");
        session.setMaxInactiveInterval(3600);
        list.add(new Entry(User_Id,session.getId()));
	  }
	  else
	  {
        throw new ApplicationException("Invalid Password, Login denied",
                                       "UserLogin",
                                       "Please check your password again");
	  }
	}
	else
	{
        throw new ApplicationException("Invalid Login Id, Login denied",
                                       "UserLogin",
                                       "Please check your Login Id again");
	}

 	rs.close();

	return flag;
  }
  
  public void UserLogout(HttpServletRequest request)
  throws Exception
  {
  	HttpSession session=request.getSession(false);

  	if(session != null)
    {
      long User_Id = -1;
    
      if(session.getAttribute("User_Id") != null)
        User_Id = ((Long)session.getAttribute("User_Id")).longValue();
	  	    
	  if(list.contains(User_Id,session.getId()))
        list.removeEntry(User_Id);
  	
	  session.invalidate();
	}
  }

  public void UserReg(HttpServletRequest request)
  throws Exception
  {
	long User_Id = -1;
	boolean status = false;

    User user = new User(request);
    
	try
	{
	  String str = request.getParameter("User_Id");
	  
	  if(str != null)
	    User_Id = Long.parseLong("User_Id");
	}
	catch(NumberFormatException ex)
	{
	  User_Id = 0;
	}
	
	if(User_Id > 0)
	{
	  status = user.EditUser(User_Id,pool);
	}
	else
	{
	  status = user.CreateUser(pool);
	}

    if(status == false)
    {
      throw new ApplicationException("User Registration ErrorPage",
                                     "Invalid User Id, Registration denied !!",
                                     "Please check your LoginId !!");
    }
  }

  public void PostValidate(HttpServletRequest request)
  throws Exception
  {
	long User_Id = -1;
	boolean status = true;
	boolean flag = false,Dflag = false;
	String message = "Unknown Exception";
	int Post_Id = 0,Ddiff = 0;

  	HttpSession session=request.getSession(false);
	
	User_Id = (Long)session.getAttribute("User_Id");

    if(User_Id > 0)
    {
      try
      {
        if(request.getParameter("Post_Id") != null)
        {
          Post_Id = Integer.parseInt(request.getParameter("Post_Id"));
        }
        else
        {
          Post_Id = (Integer)session.getAttribute("Post_Id");
        }
        System.out.println("Post_Id = " + Post_Id);
      }
      catch (NumberFormatException exp)
      {
        Post_Id = 0;
        log.writeLog(User_Id,"Error in Selected Post_Id");
      }
    }
    else
    {
      status = false;
      message = "Invalid User, Application Failed";
    }

    if(status && Post_Id != 0)
    {
      log.writeLog(User_Id,"Validating User's Application");
      
      ResultSet rs=null;
      Database db = new Database(pool);

      try
      {
        rs = db.RetriveDb("Select Sysdate - TestDate from Result where User_Id = " +
       	                       User_Id + " and Post_Id = " + Post_Id);
        rs.next();
        Ddiff = rs.getInt(1);
        System.out.println("Days Between :" + Ddiff);      	
      }	
      catch(SQLException e)
      {
	  	 log.writeLog(User_Id,"No Entry found in Result");
	  	 Ddiff = 0;
      }
    	     	 
      if(Ddiff < 183 && Ddiff != 0)
      {
         Dflag = true;
	     System.out.println("Application for Post Denied");
	     log.writeLog(User_Id,"User Application Denied");

         throw new ApplicationException("Application for Post Denied",
                                        "PostValidate",
                                        "Please try after 6 months");
      }              
      
      if(Dflag == false)
      {
	    String Qualification = null,Branch = null;
	    int Experience = 0,Agelimit = -1;
	    int Selected = -1,Employee = -1;
	    
	    java.util.Date date=null;
        Calendar Cdate = Calendar.getInstance();
	    Calendar Bdate = Calendar.getInstance();

        rs = null;
	    rs = db.RetriveDb("Select Date_Of_Birth,Qualification,Branch,Experience,Selected,Employee from Users where User_Id = " + 
	                       User_Id);
        rs.next();

	    date = rs.getDate(1);
		Bdate.setTime(date);
		Agelimit = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR);
        Qualification = rs.getString(2);
        Branch = rs.getString(3);
        Experience = rs.getInt(4);
        Selected = rs.getInt(5);
        Employee = rs.getInt(6);
        System.out.println("Validate = " + Qualification + "," + Branch + "," + Experience);	  
      
	    ResultSet rs1=db.RetriveDb("Select Qualification,Branch,Experience,Agelimit from Post where Post_Id = " + 
	                                Post_Id);
        rs1.next();

        if(Employee == 0)
        {
          if(Selected == 0)
          {
            if(Qualification.equals(rs1.getString(1)) && Branch.equals(rs1.getString(2)))
            {
      	      if(Experience >= rs1.getInt(3))
      	      {
      	        int num = -1;
      	        num = rs1.getInt(4);

      		    if(num == 0 || (Agelimit > 17 && num < 60 && Agelimit <= num))
      		    {
      		  	  flag = true;
      		    }
      		    else
      		    {
                 throw new ApplicationException("Age Criteria not Satisfied, Application Denied",
                                                "PostValidate",
                                                "You are not eligible for the Post");
      		    }
              }
              else
              {
               throw new ApplicationException("Inadequate Experience, Application Denied",
                                              "PostValidate",
                                              "You are not eligible for the Post");
              }
            }
            else
            {
             throw new ApplicationException("Insufficient Qualification, Application Denied",
                                            "PostValidate",
                                            "You are not eligible for the Post");
            }
          }
          else
          {
           throw new ApplicationException("Application the for Post Denied",
                                          "PostValidate",
                                          "Candidates selected for the Interview cannot Apply");
          }
        }
        else
        {
         throw new ApplicationException("Application the for Post Denied",
                                        "PostValidate",
                                        "Employees are not allowed to Apply");
        }

        System.out.println("flag = " + flag);

	    if(flag == true)
	    {
	      if(Ddiff >= 183)
	      {
	        db.ExecuteDb("Delete from TestQuestions where User_Id =" + User_Id 
	                      + " and Post_Id = " + Post_Id);
	      }
	      
	      session.setAttribute("Post_Id",Post_Id);
  	      log.writeLog(User_Id,"User Application verified");        	 	 
	      //"/examples/servlet/ChooseTest"
	    }                       
        else
        {
  	      log.writeLog(User_Id,"User Application denied");
        }     	
      }	       	
    }
    else
    {
      status = false;
      message = "Invalid Post, Application Failed";
    }
    	
  	if(status == false)
  	{
  	  log.writeLog(User_Id,"User Application denied");

      throw new ApplicationException(message,
                                     "PostValidate",
                                     "Please Select the Post to Apply Again");
  	}
  	
  	CategoryValidate(request,User_Id,Post_Id);
  }

  public void CategoryValidate(HttpServletRequest request,long User_Id,int Post_Id)
  throws Exception
  {
    int count = 0,given = 0,Ddiff = 0;
    ResultSet rs = null;

    Database db = new Database(pool);
	
 	rs = db.RetriveDb("Select count(N.Category_Id) from Post,TABLE(Post.CatEntry)N where N.Test_Id != 0 and Post_Id = "
 	                   + Post_Id);

    if(rs.next())
      count = rs.getInt(1);

 	rs = null;
    rs = db.RetriveDb("Select count(N.Category_Id) from Result ,TABLE(Result.TestData)N" + 
                        " where User_Id = " + User_Id + " and Post_Id = " + Post_Id);
    if(rs.next())
      given = rs.getInt(1);

	if(count == 0)
	{	 	
	  throw new ApplicationException("No Tests Published By the Administrator",
                                     "ChooseTest",
                                     "Please wait until tests are published");
	  //file.writeLog(User_Id,"No Tests Published");
	}
	else
	{
	  if(count == given)
	  {
	    throw new ApplicationException("All Tests are Already Attempted",
                                       "ChooseTest",
                                       "Please try after 6 months");
	  }
	}
  }

  public void TestValidate(HttpServletRequest request)
  throws Exception
  {
    HttpSession session=request.getSession(false);
          
    int Post_Id = 0,Cat_Id = 0,Test_Id = 0,count = 0,val = 0;
    int Ddiff = 0;
    long User_Id = 0;
    String temp = null,query = null;
    boolean Questions = false;
    ResultSet rs = null;

    User_Id = (Long)session.getAttribute("User_Id");
    if(User_Id == 0)
    {
      System.out.println("Error in Session !!");
    }
     
    Post_Id = (Integer)session.getAttribute("Post_Id");
    if(Post_Id == 0)
    {
      System.out.println("Error in Session !!");
      //file.writeLog(0,"Error in Post Session");
    }

    temp = request.getParameter("Category");
    
    Database db = new Database(pool);
	
	rs = db.RetriveDb("Select Sysdate - TestDate from Result where User_Id = " +
                       User_Id + " and Post_Id = " + Post_Id);
    if(rs.next())
     Ddiff = rs.getInt(1);

    if(Ddiff == 0 || Ddiff >= 183)
    {
      Cat_Id = Integer.parseInt(temp);
      session.setAttribute("Cat_Id",Cat_Id);
      System.out.println("Cat_Id = " + Cat_Id);
      //file.writeLog(User_Id,"Displaying Test Rules"); 

      rs = null;
   	  rs = db.RetriveDb("select count(rownum) from Post,TABLE(Post.CatEntry)N where " + 
                        "N.Test_Id != 0 and Post_Id = " + Post_Id + 
                        " and N.Category_Id = " + Cat_Id);
      
   	  if(rs.next())
       count = rs.getInt(1);
      System.out.println("Count" + count);

      if(count == 1)
      {
        rs = null;
        rs = db.RetriveDb("select N.Test_Id from Post,TABLE(Post.CatEntry)N where N.Test_Id != 0" +
                          " and Post_Id = " + Post_Id +  
                          " and N.Category_Id = " + Cat_Id);
        rs.next();
        Test_Id = rs.getInt(1);
        System.out.println("Test_Id = " + Test_Id);
      }
      else if(count == 0)
      {
        Test_Id = 0;
      }
      
      if(Test_Id != 0)
      {
        int QTotal = 0,TType = 0,max = 0;;
        String QType = null;

        rs = null;
        rs = db.RetriveDb("select Total_Ques,Ques_Type,Test_Type " +
                          "from Test where Test_Id = " + Test_Id);
        rs.next();
        QTotal = rs.getInt("Total_Ques");
        TType = rs.getInt("Test_Type");
        
        if(TType == 1)
           QType = rs.getString("Ques_Type");

        if(TType == 1)
        {
          query = "select count(Ques_Id) from Questions where Category_Id = " +
                   Cat_Id + " and Type = '" + QType + "'";
        }
        else
        {
          query = "select count(Ques_Id) from Questions where Category_Id = " + Cat_Id;
        }

        rs = null;
        rs = db.RetriveDb(query);
        rs.next();
        max = rs.getInt(1);
        
        if(max > QTotal)
          Questions = true;
        else
          Questions = false;

        System.out.println("Questions = " + Questions);
      }

      if(Cat_Id == 0)
      { 
	    throw new ApplicationException("No Category Selected",
                                       "TestRules",
                                       "Please select one of the Category to Proceed");
      }
      else if(Test_Id == 0)   
      { 
	    throw new ApplicationException("No Tests Published For this Category",
                                       "TestRules",
                                       "Please wait until tests are published");
      }
      else if(Questions == false)
      {
        throw new ApplicationException("Questions available for Test are Insufficient",
                                       "TestRules",
                                       "Please wait until sufficient questions are available");
      }
      else if(Test_Id != 0 & Questions == true)
      {
	    session.setAttribute("Test_Id",Test_Id);
	  }
    }
    else
    {
      throw new ApplicationException("The time limit for the Test is over",
                                     "TestRules",
                                     "Please try after 6 months");
    }
  }  

  public void loadTestBuffer(HttpServletRequest request)
  throws Exception
  {
    TestBuffer testbuff;

    HttpSession session=request.getSession(false);

    if(session.getAttribute("TestBuffer") != null)
    {
      testbuff = (TestBuffer)session.getAttribute("TestBuffer");

      String temp = null;
      temp = request.getParameter("USEREVENT");
      
      if(temp != null && !temp.equals("TSTBACK") && !temp.equals("TSTPAGE"))
      {
        testbuff.Ans1 = request.getParameter("Ans1");
        testbuff.Ans2 = request.getParameter("Ans2");
        testbuff.Ans3 = request.getParameter("Ans3");
        testbuff.Ans4 = request.getParameter("Ans4");

        if (testbuff.Q_Load > 0)
        {
          Database db = new Database(pool);
	      ResultSet rs=db.RetriveDb("Select Type from Questions where Ques_Id = " 
	                                + testbuff.Ques_Id);

	      if(rs.next())
	      {
            String typ = rs.getString(1);
          
            if(typ.equals("MA"))
            {
             testbuff.option = testbuff.getMAOption(testbuff.Ans1,testbuff.Ans2,testbuff.Ans3,testbuff.Ans4);
            }
            else
            {
              if((testbuff.Ans1!=null) && (testbuff.Ans1.length()!=0))
                testbuff.option = Integer.parseInt(testbuff.Ans1);
              if(testbuff.Ans1 ==null && testbuff.NewEntry == true)
                testbuff.option = 0;
            }
	      }
        
          long User_Id = (Long)session.getAttribute("User_Id");
          int  Post_Id = (Integer)session.getAttribute("Post_Id");
          int  Cat_Id  = (Integer)session.getAttribute("Cat_Id");

          if(User_Id == 0 || Post_Id == 0 || Cat_Id == 0)
           throw new Exception("Test not Initialized");

          System.out.println(" AnsEntry -> qid=" + testbuff.Ques_Id + " option=" + testbuff.option + " Display=" + testbuff.Q_Display);
          System.out.println(" AnsEntry ->Qload =  " + testbuff.Q_Load + " NewEntry=" + testbuff.NewEntry);
          testbuff.AnsEntry(User_Id,Post_Id,Cat_Id,testbuff.Ques_Id,testbuff.option,testbuff.NewEntry,testbuff.Q_Load);
          testbuff.NewEntry = false;
        }
      }

      testbuff.Next = null;
      testbuff.Prev = null;

      if(temp!=null && (temp.length()!=0))
      {
        if(temp.equals("TSTPREV"))
        {
          testbuff.Prev = "Prev Question";
        }
        else if(temp.equals("TSTNEXT"))
        {
          testbuff.Next = "Next Question";
        }
        else if(temp.equals("TSTBACK"))
        {
          String NewDisid = request.getParameter("DisplayId");
	     
          if(NewDisid != null)
          {
            int t = 0;
      
      	    t = Integer.parseInt(NewDisid);
       
            if(t != 0)
            {
              testbuff.Q_Display = t;
              testbuff.Ques_Id = (Long)testbuff.list.get(testbuff.Q_Display - 1);
            }
          }
        }
        else if(temp.equals("TSTSBMT"))
        {
          session.removeAttribute("TestBuffer");
        }
      }
    }
    else
    {
      long User_Id = 0;
      int Post_Id = 0,Test_Id = 0,Cat_Id = 0;

      User_Id = (Long)session.getAttribute("User_Id");
      Post_Id = (Integer)session.getAttribute("Post_Id");
      Cat_Id = (Integer)session.getAttribute("Cat_Id");
      Test_Id = (Integer)session.getAttribute("Test_Id");

      if(User_Id == 0 || Post_Id == 0 || Cat_Id == 0 || Test_Id == 0)
        throw new Exception("Test not Initialized");

      testbuff = new TestBuffer(User_Id,Post_Id,Cat_Id,Test_Id,pool);
      session.setAttribute("TestBuffer",testbuff);
    }
  }

  public void ResultCal(HttpServletRequest request)
  throws Exception
  {
    loadTestBuffer(request);

    long User_Id = 0;
    int Post_Id = 0,Test_Id = 0,Cat_Id = 0;

    /*-------------- Getting values from Session -------------*/ 

  	HttpSession session=request.getSession(false);

    User_Id = (Long)session.getAttribute("User_Id");
    if(User_Id == 0)
    {
      System.out.println("Error in Session !!");
    }

    Post_Id = (Integer)session.getAttribute("Post_Id");
    if(Post_Id == 0)
    {
      System.out.println("Error in Session !!");
    }

    Cat_Id = (Integer)session.getAttribute("Cat_Id");
    if(Cat_Id == 0)
    {
      System.out.println("Error in Session !!");
    }

    Test_Id = (Integer)session.getAttribute("Test_Id");
    if(Test_Id == 0)
    {
      System.out.println("Error in Session !!");
    }

    /*--------------------- Variables Declaration -----------------*/

    int num = 0,i;
    int Ans = 0,Correct = 0,Total = 0,Attempt = 0,Wrong = 0;
    float Percent = 0,AttPercent = 0,T_Percent = 0,T_Attempt = 0;     
    String result = null,T_Result = null;
    int Total_Ques = 0,Pass_Score = 0,Negative = 0,Attempt_No = 0;
    int n1 = 0,n2 = 0,Aggregate = 0;
	Database db = null;	
	ResultSet rst = null,rst1 = null,rst2 = null;

    /*-------------- Calculating Total for present Test_Id -------------*/   

	if(User_Id != 0 && Post_Id != 0 && Test_Id != 0 && Cat_Id != 0)
	{
	  db = new Database(pool);
      rst = null;
      rst = db.RetriveDb("select N.User_Answer,N.Correct_Ans from TestQuestions," +
                             "Table(TestQuestions.TestEntry)N where User_Id = " +
                              User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " +
                              Cat_Id + " order by N.Ques_Number");
      
      while(rst.next())
      {
        Ans = rst.getInt(1);
        Correct = rst.getInt(2);
        
        if(Ans == Correct)
        {
          Attempt++;
          Total = Total + 1;
        }
        else if(Ans > 0)
        {
          Attempt++;
          Wrong++;
        }
      }
        
	  System.out.println("Total = " + Total + " Attempt = " + Attempt + " Wrong = " + Wrong); 
	 
	/*-------------- Getting Total Tests for given Post_Id -------------*/ 
	 
	  rst1 = null;	 	
      rst1 = db.RetriveDb("Select max(rownum)from Post,TABLE(Post.CatEntry)N " + 
                          " where N.Test_Id > 0 and Post_Id = " + Post_Id);
      if(rst1.next())
       n1 = rst1.getInt(1);
      System.out.println("n1 = " + n1);
      
	  rst1 = null;	 	
      rst1 = db.RetriveDb("select Aggregate from Post where Post_Id = " + Post_Id);

      if(rst1.next())
       Aggregate = rst1.getInt(1);
      System.out.println("Aggregate = " + Aggregate);        	
	  
	    /*-------------- Entry of Result in Database -------------*/ 
	  
	  rst = null;
	  rst = db.RetriveDb("Select Pass_Score,Total_Ques,Negative from Test where" + 
	                     " Test_Id = " + Test_Id);
      if(rst.next())
      {
        Pass_Score = rst.getInt(1);
        Total_Ques = rst.getInt(2);
        Negative = rst.getInt(3);
      }
    
      if(Negative == 1)
      {
        Total = Total - Wrong;
      }

      Percent    = (Total*100)/Total_Ques;
      AttPercent = (Attempt*100)/Total_Ques;
	  
      if (Percent > Pass_Score)
        result = "Pass";
      else
        result = "Fail";     

      System.out.println("percent = " + Percent); 
       
      rst1 = null;
      rst1 = db.RetriveDb("Select count(rownum) from Result,TABLE(Result.TestData)N where User_Id = " +
                           User_Id + " and Post_Id = " + Post_Id);
      if(rst1.next())
       n2 = rst1.getInt(1);
      System.out.println("n2 = " + n2);      
      
      int val = 0,Temp = 0;
      String Query = null;
      
      if(n2 == 0)
      {
   	    T_Percent = Percent/n1;
   	    T_Attempt = AttPercent/n1;
        
   	    Temp = 0;
   	    Temp = n2 + 1;
   	    
   	    if(n1 == Temp)
        {
          if(T_Percent >= Aggregate)
            T_Result = "Pass";
          else
            T_Result = "Fail";
        }
        else
          T_Result = "Fail";
        
        db.ExecuteDb("Insert into Result values (" + User_Id + "," + Post_Id + "," +
                      T_Percent + "," + T_Attempt + ",'" + T_Result + "',1" +
                     ",sysdate," + "TDATA_NT(TDATA_TY(" + Cat_Id + "," + Test_Id + "," + 
                      Percent + "," + AttPercent + ",'" + result + "')))");
        n2++;
      }
      else if(n2 > 0)
      {  
        rst1 = null;       	 
        rst1 = db.RetriveDb("Select SysDate - TestDate from Result where User_Id = " +
                             User_Id + " and Post_Id = " + Post_Id);
        int Days = 0;

        if(rst1.next())
         Days = rst1.getInt(1);
      
        if(Days == 0)
        {             
          System.out.println("Date Same !!");
          rst2 = null;
          rst2 = db.RetriveDb("Select T_Percent,T_Attempt from Result where " + 
                              "User_Id = " + User_Id + " and Post_Id = " + Post_Id);

          if(rst2.next())
          {
            T_Percent = rst2.getInt(1);
            T_Attempt = rst2.getInt(2);
          }

          T_Percent = ((T_Percent * n1) + Percent)/n1;
          T_Attempt = ((T_Attempt * n1) + AttPercent)/n1;

       	  Temp = 0;
       	  Temp = n2 + 1;

          if(n1 == n2)
          {
            if(T_Percent >= Aggregate)
              T_Result = "Pass";
            else
              T_Result = "Fail";
          }
          else
            T_Result = "Fail";
          
          db.ExecuteDb("Insert into Table(Select TestData from Result " + 
		               "where User_Id = " + User_Id + " and Post_Id = " + Post_Id + 
		               ") values (TDATA_TY(" + Cat_Id + "," + Test_Id + "," + Percent +
                       "," + AttPercent + ",'" + result + "'))");

          System.out.println("T_Percent = " + T_Percent);                    
        
          db.ExecuteDb("Update Result set T_Percent = " + T_Percent + ",T_Attempt = " + 
                        T_Attempt + ",T_Result = '" + T_Result + "',TestDate = Sysdate where User_Id = " + 
                        User_Id + " and Post_Id = " + Post_Id);
          n2++;                   
        }  
        else if(Days >= 183)
        {
          System.out.println("Entry in else case !!");
       	
          T_Percent = Percent/n1;
          T_Attempt = AttPercent/n1;
          
          if(n1 == 1)
          {
            if(T_Percent >= Aggregate)
              T_Result = "Pass";
            else
              T_Result = "Fail";
          }
          else
            T_Result = "Fail";

       	  db.ExecuteDb("Delete Table(select TestData from Result where User_Id = " + 
       	                User_Id + " and Post_Id = " + Post_Id + ")N");

          db.ExecuteDb("Update Result set T_Percent = " + T_Percent + ",T_Attempt = " + 
                        T_Attempt + ",T_Result = '" + T_Result +
                       "',Attempt_No = Attempt_No + 1,TestDate = Sysdate where User_Id = " + 
                        User_Id + " and Post_Id = " + Post_Id);

          db.ExecuteDb("Insert into Table(select TestData from Result " + 
		               "where User_Id = " + User_Id + " and Post_Id = " + Post_Id + 
		               ") values (TDATA_TY(" + Cat_Id + "," + Test_Id + "," + Percent +
                       "," + AttPercent + ",'" + result + "'))");

          n2 = 1;
        }
        else
        {
          n2 = n1 + 1;          // So as to display the Error Page Below
        }
      }
      else
      {
        System.exit(0);       	
      }
	    	    	  
	  db.ExecuteDb("Delete from TestQuestions where User_Id = " + User_Id +
	               " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id);
    
	
	 /*-------------- Unpublishing and Committing the values  -------------*/ 
	
	  db.ExecuteDb("Commit");                  		
	  System.out.println("Result Calulated Successfully !!");
	 
	 /*-------------- Finding appropriate path -------------*/ 
	
    
      System.out.println("Values of n1,n2 are = " + n1 + "," + n2);	
      session.removeAttribute("Test_Id");
      session.removeAttribute("Cat_Id");
        
      if(n1 == n2)
      {
        session.removeAttribute("Post_Id");
        //request.setAttribute("USEREVENT","");  // Redirect to Thanks
      }   
      else if(n1 > n2)
      {
        request.setAttribute("USEREVENT","SELTEST");  // Redirect to ChooseTest
      }
      else
      {
        throw new ApplicationException("The time limit for the Test is over",
                                       "ResultCalculate",
                                       "Please try after 6 months");
	    //response.sendRedirect("examples/servlets/User/PostView.jsp");
      }
	}
  }
}