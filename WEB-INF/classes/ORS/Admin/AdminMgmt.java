package ORS.Admin;

import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import ORS.ConnPool.*;
import ORS.Exception.ApplicationException;
import ORS.Exception.DataEntryException;
import ORS.Model.*;
import ORS.Controller.Entry;
import ORS.Controller.EntryList;
import ORS.Utils.Log;

public class AdminMgmt
{
  private ConnectionPool pool;
  private EntryList list;
  private Log log;
  
  public AdminMgmt(ConnectionPool pl,EntryList lt,Log lg)
  {
	super();
	pool = pl;
	list = lt;
	log = lg;
  }
  
  public boolean AdminLogin(HttpServletRequest req) 
  throws Exception
  {
	boolean flag=false;
	int Admin_Id=0;
   	String Login=null,Passwd=null;

	Login=req.getParameter("Admin_Name");
	Passwd=req.getParameter("Admin_Passwd");	
	
	Database db = new Database(pool);
	ResultSet rs=db.RetriveDb("Select Admin_Id,Login,Password from Admin");

	while(rs.next())
	{
	  if(Passwd.equals(rs.getString("Password")) && Login.equals(rs.getString("Login")))
	  {
        Admin_Id = rs.getInt("Admin_Id");
	    flag=true;
	    break;
	  }	   	
	}

    if(flag==true)
	{
	  HttpSession session=req.getSession(true);
	  System.out.println("Logged  :" + Login);
	  System.out.println("Admin_Id :"+ Admin_Id);
      session.setAttribute("Admin_Id",Admin_Id);
      // get the access code
      session.setAttribute("Access_Code",Admin.getAccessCode(pool,Login));  // 2147417984
      session.setAttribute("MAINEVENT","FIRSTPAGE");
      session.setMaxInactiveInterval(3600);
      list.add(new Entry(Admin_Id,session.getId()));
      log.writeLog(Admin_Id,"Admin Logged In.");
	}
	else
	{
      throw new ApplicationException("Invalid Password, Login denied",
                                     "AdminLogin",
                                     "Please check your password again");
	}

 	rs.close();

	return flag;
  }
  
  public void AdminLogout(HttpServletRequest req)
  throws Exception
  {
  	HttpSession session=req.getSession(false);
  	
  	if(session != null)
    {
      int Admin_Id = -1;
    
      if(session.getAttribute("Admin_Id") != null)
        Admin_Id = ((Integer)session.getAttribute("Admin_Id")).intValue();
	  	    
	  if(list.contains(Admin_Id,session.getId()))
        list.removeEntry(Admin_Id);
  	
	  session.invalidate();
	  log.writeLog(Admin_Id,"Admin Logged Out.");
	}
  }

  public void AdminReg(HttpServletRequest req)
  throws Exception
  {
	int Admin_Id = -1, Id = -1;
	boolean status = false;

    Admin admin = new Admin(req);

	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Admin_Id");
	  
	  if(str != null)
	    Admin_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Admin_Id = 0;
	}
	
	if(Admin_Id > 0)
	{
	  status = admin.EditAdmin(Admin_Id,pool);
	  
	  if(status)
	   log.writeLog(Id,"Admin Profile Updated.");
	}
	else
	{
	  status = admin.CreateAdmin(pool);
	  
	  if(status)
	   log.writeLog(Id,"Admin Registration Completed for Admin : " + admin.getLogin());
	}

    if(status == false)
    {
      throw new DataEntryException(admin.getMessage(),
                                   "AdminRegistration",
                                   "Please check entries again");
    }
  }
  
  public void ChangeDesign(HttpServletRequest req)
  throws Exception
  {
	int Admin_Id = -1, Id = -1;
	int Access_Id = -1;
	boolean status = false;

    Admin admin = new Admin();
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Admin_Id");
	  
	  if(str != null)
	    Admin_Id = Integer.parseInt(str);

	  str = req.getParameter("Access_Id");
	  
	  if(str != null)
	    Access_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Admin_Id = 0;
	}
	
	if(Admin_Id > 0)
	{
	  status = admin.EditAccessId(Admin_Id,Access_Id,pool);
	  
	  if(status)
	  {
        String msge = "Admin Designation Changed to " + 
                      this.getDesignation(pool, Access_Id) + 
                      " : " + admin.getLoginId(pool,Admin_Id);
	   log.writeLog(Id, msge);
	  }
	}
	else
	{
      throw new ApplicationException("Invalid Admin Id",
                                     "SetDesignation",
                                     "Please check Admin Id again");   
	}
  }

  public void DeleteAdmin(HttpServletRequest req)
  throws Exception
  {
	int Admin_Id = -1,Id = -1;
	boolean status = false;

    Admin admin = new Admin();
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("SelectAdmin");
	  
	  if(str != null)
	    Admin_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Admin_Id = 0;
	}
	
	if(Admin_Id == 1)
	{
      throw new ApplicationException("Request denied !!",
                                     "DeleteAdmin",
                                     "Super Administrator Cannot be Deleted !!");
	}
	else if(Admin_Id == Id)
	{
      throw new ApplicationException("Request denied !!",
                                     "DeleteAdmin",
                                     "Unable to Delete the Requested Profile !!");
	}
	else if(Admin_Id > 1)
	{
	  String adminName = admin.getLoginId(pool,Admin_Id);
	  status = admin.DeleteAdmin(Admin_Id,pool);
	  
	  if(status)
	   log.writeLog(Id,"Admin Profile Deleted for Admin : " + adminName);
	}

    if(status == false)
    {
      throw new ApplicationException(admin.getMessage(),
                                   "AdminDeletion",
                                   "Please check the Login Id");
    }
  }  
  
  public void CreateCategory(HttpServletRequest req)
  throws Exception
  {
	int Cat_Id = -1, Id = -1;
	boolean status = false;

    Category category = new Category(req);
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Cat_Id");
	  
	  if(str != null)
	    Cat_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Cat_Id = 0;
	}
	
	if(Cat_Id > 0)
	{
	  status = category.EditCategory(Cat_Id,pool);

	  if(status)
	   log.writeLog(Id,"Category Updated : " + category.getName());
	}
	else
	{
	  status = category.CreateCategory(pool);

	  if(status)
	   log.writeLog(Id,"New Category Created : " + category.getName());
	}

    if(status == false)
    {
      throw new DataEntryException(category.getMessage(),
                                   "Create Category",
                                   "Please check entries again");
    }
  }
  
  public void DeleteCategory(HttpServletRequest req)
  throws Exception
  {
	int Cat_Id = -1, Id = -1;
	boolean status = false;

    Category category = new Category();
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("SelectCategory");
	  
	  if(str != null)
	    Cat_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Cat_Id = 0;
	}
	
    if(Cat_Id > 0)
	{
	  String catName = category.getName(pool,Cat_Id);
	  status = category.DeleteCategory(Cat_Id,pool);

	  if(status)
	   log.writeLog(Id,"Category Deleted : " + catName);
	}

    if(status == false)
    {
      throw new ApplicationException(category.getMessage(),
                                   "CategoryDeletion",
                                   "Please check the Category Id");
    }
  }  

  public void CreatePost(HttpServletRequest req)
  throws Exception
  {
	int Post_Id = -1, Id = -1;
	boolean status = false;

    Post post = new Post(req);
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Post_Id");
	  
	  if(str != null)
	    Post_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Post_Id = 0;
	}
	
	if(Post_Id > 0)
	{
	  status = post.EditPost(Post_Id,pool);

	  if(status)
	   log.writeLog(Id,"Post Updated : " + post.getName());
	}
	else
	{
	  status = post.CreatePost(pool);

	  if(status)
	   log.writeLog(Id,"New Post Created : " + post.getName());
	}

    if(status == false)
    {
      throw new DataEntryException(post.getMessage(),
                                   "Create Post",
                                   "Please check entries again");
    }
  }
  
  public void DeletePost(HttpServletRequest req)
  throws Exception
  {
	int Post_Id = -1, Id = -1;
	boolean status = false;

    Post post = new Post();
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("SelectPost");
	  
	  if(str != null)
	    Post_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Post_Id = 0;
	}
	
    if(Post_Id > 0)
	{
	  String postName = post.getName(pool, Post_Id);
	  status = post.DeletePost(Post_Id, pool);

	  if(status)
	   log.writeLog(Id,"Post Deleted : " + postName);
	}

    if(status == false)
    {
      throw new ApplicationException(post.getMessage(),
                                   "PostDeletion",
                                   "Please check the Post Id");
    }
  }  

  public void CreateTest(HttpServletRequest req)
  throws Exception
  {
	int Test_Id = -1, Id = -1;
	boolean status = false;

    Test test = new Test(req);
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Test_Id");
	  
	  if(str != null)
	    Test_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Test_Id = 0;
	}
	
	if(Test_Id > 0)
	{
	  status = test.EditTest(Test_Id,pool);

	  if(status)
	   log.writeLog(Id,"Test Updated : " + test.getName());
	}
	else
	{
	  status = test.CreateTest(pool);

	  if(status)
	   log.writeLog(Id,"New Test Created : " + test.getName());
	}

    if(status == false)
    {
      throw new DataEntryException(test.getMessage(),
                                   "Create Test",
                                   "Please check entries again");
    }
  }
  
  public void DeleteTest(HttpServletRequest req)
  throws Exception
  {
	int Test_Id = -1, Id = -1;
	boolean status = false;

    Test test = new Test();
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("SelectTest");
	  
	  if(str != null)
	    Test_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Test_Id = 0;
	}
	
    if(Test_Id > 0)
	{
	  String testName = test.getName(pool,Test_Id);
	  status = test.DeleteTest(Test_Id,pool);
	  
	  if(status)
	   log.writeLog(Id,"Test Deleted : " + testName);
	}

    if(status == false)
    {
      throw new ApplicationException(test.getMessage(),
                                   "TestDeletion",
                                   "Please check the Test Id");
    }
  }  

  public void EditQuestion(HttpServletRequest req)
  throws Exception
  {
	long Ques_Id = 0;
	String Question = null,Type = null;
	
	Question=req.getParameter("SelectQuestion");
	
	if(Question != null)
	{
	  try
	  {
	    Ques_Id = Long.parseLong(Question);
	  }
	  catch(NumberFormatException ex)
	  {
	    Ques_Id = 0;
	  }
	} 
		
	if(Ques_Id != 0)
	{
	  String query = "Select Type from Questions where Ques_Id = "
	                  + Ques_Id;
			 
	  Database db = new Database(pool);
	  ResultSet rs = db.RetriveDb(query);
	  
	  rs.next();
	  Type = rs.getString("TYPE");
	  System.out.println("Type-->"+ Type);
			 
	  if(Type != null)
	  {
	    if(Type.equalsIgnoreCase("MC"))
		{
		  req.setAttribute("EVENTNAME","SELQUES_MC");
		}
		else if(Type.equalsIgnoreCase("TF"))
		{
		  req.setAttribute("EVENTNAME","SELQUES_TF");
		}
		else if(Type.equalsIgnoreCase("MA"))
		{
		  req.setAttribute("EVENTNAME","SELQUES_MA");
		}
	  }
	  else
	  {
        throw new ApplicationException("Question Entry not Found",
                                       "Edit Question",
                                       "Please Check the Question Again");
	  }
	}
	else
	{
      throw new ApplicationException("Invalid Question, Operation failed",
                                     "Edit Question",
                                     "Please Select the Question to Edit");
	}
  }
  
  public void CreateMC_Question(HttpServletRequest req)
  throws Exception
  {
	long MC_Ques_Id = -1, Id = -1;
	boolean status = false;

    MC_Question mc_question = new MC_Question(req);
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Ques_Id");
	  
	  if(str != null)
	    MC_Ques_Id = Long.parseLong(str);
	}
	catch(NumberFormatException ex)
	{
	  MC_Ques_Id = 0;
	}
	
	if(MC_Ques_Id > 0)
	{
	  status = mc_question.Edit_MC_Question(MC_Ques_Id,pool);

	  if(status)
	   log.writeLog(Id,"Multi Choice Question Updated of Category : " + Category.getName(pool, mc_question.getCategory_Id()));
	}
	else
	{
	  status = mc_question.Create_MC_Question(pool);

	  if(status)
	   log.writeLog(Id,"New Multi Choice Question Created of Category : " + Category.getName(pool, mc_question.getCategory_Id()));
	}

    if(status == false)
    {
      throw new DataEntryException(mc_question.getMessage(),
                                   "Create MC_Question",
                                   "Please check entries again");
    }
  }
  
  public void CreateMA_Question(HttpServletRequest req)
  throws Exception
  {
	long MA_Ques_Id = -1, Id = -1;
	boolean status = false;

    MA_Question ma_question = new MA_Question(req);
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Ques_Id");
	  
	  if(str != null)
	    MA_Ques_Id = Long.parseLong(str);
	}
	catch(NumberFormatException ex)
	{
	  MA_Ques_Id = 0;
	}
	
	if(MA_Ques_Id > 0)
	{
	  status = ma_question.Edit_MA_Question(MA_Ques_Id,pool);

	  if(status)
	   log.writeLog(Id,"Multi Ans Question Updated of Category : " + Category.getName(pool, ma_question.getCategory_Id()));
	}
	else
	{
	  status = ma_question.Create_MA_Question(pool);

	  if(status)
	   log.writeLog(Id,"New Multi Ans Question Created of Category of Category : " + Category.getName(pool, ma_question.getCategory_Id()));
	}

    if(status == false)
    {
      throw new DataEntryException(ma_question.getMessage(),
                                   "Create MA_Question",
                                   "Please check entries again");
    }
  }
  
  public void CreateTF_Question(HttpServletRequest req)
  throws Exception
  {
	long TF_Ques_Id = -1, Id = -1;
	boolean status = false;

    TF_Question tf_question = new TF_Question(req);
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Ques_Id");
	  
	  if(str != null)
	    TF_Ques_Id = Long.parseLong(str);
	}
	catch(NumberFormatException ex)
	{
	  TF_Ques_Id = 0;
	}
	
	if(TF_Ques_Id > 0)
	{
	  status = tf_question.Edit_TF_Question(TF_Ques_Id,pool);

	  if(status)
	   log.writeLog(Id,"True or False Choice Question Updated of Category : " + Category.getName(pool, tf_question.getCategory_Id()));
	}
	else
	{
	  status = tf_question.Create_TF_Question(pool);

	  if(status)
	   log.writeLog(Id,"New True or False Choice Question Created of Category : " + Category.getName(pool, tf_question.getCategory_Id()));
	}

    if(status == false)
    {
      throw new DataEntryException(tf_question.getMessage(),
                                   "Create TF_Question",
                                   "Please check entries again");
    }
  }
  
  public void DeleteQuestion(HttpServletRequest req)
  throws Exception
  {
	long Ques_Id = -1, Id = -1;
	boolean status = false;

    Question question = new MC_Question();
    
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("SelectQuestion");
	  
	  if(str != null)
	    Ques_Id = Long.parseLong(str);

      System.out.println("Ques_Id = " + Ques_Id);
	}
	catch(NumberFormatException ex)
	{
	  Ques_Id = 0;
	}
	
    if(Ques_Id > 0)
	{
	  String quesType = question.getQuestionType(pool, Ques_Id);
	  String catName = question.getCategoryName(pool, Ques_Id);
	  status = question.DeleteQuestion(Ques_Id,pool);

	  if(status)
	   log.writeLog(Id,"" + quesType + " Question Deleted of Category : " + catName);
	}

    if(status == false)
    {
      throw new ApplicationException(question.getMessage(),
                                   "QuestionDeletion",
                                   "Please check the Question Id");
    }
  }
  
  public void InitSearch(HttpServletRequest req)
  throws Exception
  {
    Database db = new Database(pool);

    String Query = "Drop Index Question_Context_Index Force";

	db.ExecuteDb(Query);

    Query = "create index Question_Context_Index on Questions(Question) " + 
            "indextype is ctxsys.context";

	db.ExecuteDb(Query);	
  }

  public void PublishTest(HttpServletRequest req)
  throws Exception
  {
	boolean status = true;
	String message = "Unknown Exception";
    int Post_Id = -1,counter = 0;
    int Id = -1;
	ArrayList Cat_Id = null,Test_Id = null;
	
	try
	{
      Id = this.getCurrentAdminId(req);
	  String str = req.getParameter("Post_Id");
	  
	  if(str != null)
	    Post_Id = Integer.parseInt(str);
	}
	catch(NumberFormatException ex)
	{
	  Post_Id = 0;
	}
	
	if(Post_Id > 0)
	{
      int m = 0,n = 0;
      String Cid = null,Tid = null;
      
      Cat_Id = new ArrayList();
      Test_Id = new ArrayList();
      
      Cid = req.getParameter("Cat_Id" + counter);
      Tid = req.getParameter("Test_Id" + counter);
      
      while(Cid != null && Tid != null && status != false)
      {
      	try
      	{
      	  m = Integer.parseInt(Cid);
      	  n = Integer.parseInt(Tid);
      	  Cat_Id.add(m);
      	  Test_Id.add(n);
      	}
      	catch(NumberFormatException ne)
      	{
      	  status = false;
      	  message = "Invalid Category Or Test_Id";
      	}

        counter++;
        Cid = req.getParameter("Cat_Id" + counter);
        Tid = req.getParameter("Test_Id" + counter);
      }
    }
    else
      message = "Invalid Post Id";
      
	if(status == true)
	{
      Database db = new Database(pool);

      for(int i = 0;i < counter;i++)
      {
        String Query = "Update Table(select CatEntry from Post where Post_Id = " +
                        Post_Id + ")N set N.Test_Id = " + (Integer)Test_Id.get(i) +
                       " where N.Category_Id = " + (Integer)Cat_Id.get(i);

	    db.ExecuteDb(Query);
      }
	}
	
    if(status == false)
    {
      throw new ApplicationException(message,
                                     "PublishTest",
                                     "Please check the values again");
    }
  }

  public void AutoDelUser(HttpServletRequest req)
  throws Exception
  {
	ResultSet result = null;
    Database db1 = new Database(pool);

    String Query = "Select User_Id from Result group by User_Id having min(Months_Between(Sysdate,Testdate)) > 12";

    result = db1.RetriveDb(Query);

    while(result.next())
    {
      long User_Id = 0;
      User_Id = result.getLong(1);

      Database db2 = new Database(pool);
      db2.ExecuteDb("Delete from Result where User_Id = " + User_Id);
      db2.ExecuteDb("Delete from TestQuestions where User_Id = " + User_Id);
      db2.ExecuteDb("Delete from Users where User_Id= " + User_Id);
    }
  }

  public void Select(HttpServletRequest req)
  throws Exception
  {
    ArrayList User_Id = null;
    int Count = 0,counter = 0,Post_Id = 0;
    int Id = -1;
    long urid = 0;
    String Uid = null,event = null;
    boolean status = true;
    
	event = req.getParameter("EVENTNAME");
	
	if(event == null)
	{
	  status = false;
	}
	
	try
	{
	  String st = null;
	  st = req.getParameter("Count");
	  Count = Integer.parseInt(st);
	  
	  if(Count == 0)
	    status = false;
	    
      st = null;
      st = req.getParameter("Post_Id");
      Post_Id = Integer.parseInt(st);
	}
	catch(NumberFormatException ne)
	{
	  status = false;
	}
	
	if(status == true)
	{
      Id = this.getCurrentAdminId(req);
      User_Id = new ArrayList();
      Uid = req.getParameter("User_Id" + counter);
      System.out.println("Userid = " + Uid);
      
      while(counter < Count && status != false)
      {
        if(Uid != null)
        {
          try
          {
      	    urid = Long.parseLong(Uid);
      	    User_Id.add(urid);
          }
          catch(NumberFormatException ne)
          {
            status = false;
          }
        }
      
        counter++;
        Uid = req.getParameter("User_Id" + counter);
      }
	}
				
    if(status == true && User_Id.size() != 0)
    {
      Database db = new Database(pool);

      if(event.equals("VWRSLT_SEL"))
      {
        for(int i = 0;i < User_Id.size();i++)
        {
          db.ExecuteDb("Update Users set Selected = " + Post_Id + ",SelectNo = SelectNo + 1 where User_Id = " + (Long)User_Id.get(i));
          System.out.println("Users Selected = " + i);
        }
      }
      else if(event.equals("VWSELD_USL"))
      {
        for(int i = 0;i < User_Id.size();i++)
        {
          db.ExecuteDb("Update Users set Selected = 0 where User_Id = " + (Long)User_Id.get(i));
          System.out.println("Users UnSelected = " + i);
        }
      }
    }
  }

  public void Recruit(HttpServletRequest req)
  throws Exception
  {
    ArrayList User_Id = null;
    int Count = 0,counter = 0,Post_Id = 0;
    int Id = -1;
    long urid = 0;
    String Uid = null;
    boolean status = true;
    
	try
	{
	  String st = null;
	  st = req.getParameter("Count");
	  Count = Integer.parseInt(st);
	  
	  if(Count == 0)
	    status = false;
	    
      st = null;
      st = req.getParameter("Post_Id");
      Post_Id = Integer.parseInt(st);
	}
	catch(NumberFormatException ne)
	{
	  status = false;
	}
	
	if(status == true)
	{
      Id = this.getCurrentAdminId(req);
      User_Id = new ArrayList();
      Uid = req.getParameter("User_Id" + counter);
      System.out.println("Userid = " + Uid);
      
      while(counter < Count && status != false)
      {
        if(Uid != null)
        {
          try
          {
      	    urid = Long.parseLong(Uid);
      	    User_Id.add(urid);
          }
          catch(NumberFormatException ne)
          {
            status = false;
          }
        }
      
        counter++;
        Uid = req.getParameter("User_Id" + counter);
      }
	}
				
    if(status == true && User_Id.size() != 0)
    {
      Database db = new Database(pool);
      	  
      for(int i = 0;i < User_Id.size();i++)
      {
        db.ExecuteDb("Update Users set Employee = " + Post_Id + " where User_Id = " + (Long)User_Id.get(i));
        System.out.println("Users deleted = " + i);
      }
    }
  }
  
 public static String getDesignation(ConnectionPool pool,int Access_Id)
 throws Exception
 {
   String designation = null;

   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select Designation from Accessibility where Access_Id = " + Access_Id);

   if(res.next())
    designation = res.getString("Designation");

   return designation;
 }
 
 public int getCurrentAdminId(HttpServletRequest req)
 {
   int Id = -1;
   HttpSession session=req.getSession(false);

   if(session != null && session.getAttribute("Admin_Id") != null)
     Id = ((Integer)session.getAttribute("Admin_Id")).intValue();

   return Id;
 }
}