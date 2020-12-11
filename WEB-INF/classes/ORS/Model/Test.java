package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;
import ORS.Utils.Validator;

public class Test implements Validate
{ 
  private int Test_Id;
  private String Name;
  private String Description;
  private String Ques_Type;
  private int Pass_Score = -1;
  private int time = 0;
  private int Total_Ques = -1;
  private boolean Test_Type;
  private boolean Scrolling;
  private boolean Hide;
  private boolean Negative;
  private String Message = "";  
      
  public Test()
  {
  	super();
  }
  
  public Test(int Test_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setTest(Test_Id,pool);
  }
  
  public Test(HttpServletRequest req)
  {
	super();
	setTest(req);
  }
  	
  public int getTestId()
  {
  	return Test_Id;
  }

  public String getName()
  {
  	return Name;
  }
  
  public void setName(String name)
  {
  	Name = name;
  }

  public String getDescription()
  {
  	return Description;
  }
  
  public void setDescription(String desc)
  {
  	Description = desc;
  }

  public String getQuesType()
  {
    return Ques_Type;
  }
  
  public void setQuesType(String qtype)
  {
  	if(qtype == null)
      Ques_Type = "";
    else
  	  Ques_Type = qtype;
  }
  
  public boolean getScrolling()
  {
  	return Scrolling;
  }
  
  public void setScrolling(int scroll)
  {
  	if(scroll == 1)
  	  Scrolling = true;
    else
      Scrolling = false;
  }
  
  public int getPassScore()
  {
  	return Pass_Score;
  }
  
  public void setPassScore(int pscore)
  {
  	Pass_Score = pscore;
  }
  
  public int getTime()
  {
  	return time;
  }
  
  public void setTime(int time)
  {
  	this.time = time;
  }
  
  public boolean getHide()
  {
  	return Hide;
  }
  
  public void setHide(int hide)
  {
  	if(hide == 1)
  	  Hide = true;
    else
      Hide = false;
  }
  
  public boolean getTestType()
  {
  	return Test_Type;
  }
  
  public void setTestType(int testtype)
  {
  	if(testtype == 1)
  	  Test_Type = true;
    else
      Test_Type = false;
  }

  public int getTotalQuestion()
  {
  	return Total_Ques;
  }

  public void setTotalQuestion(int total)
  {
  	Total_Ques = total;
  }

  public boolean getNegative()
  {
  	return Negative;
  }

  public void setNegative(int negative)
  {
  	if(negative == 1)
  	  Negative = true;
    else
      Negative = false;
  }

  public String getMessage()
  {
  	return Message;
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	
    if(Name == null)
  	{
  	  Message = "Test Name not entered";
  	}
  	else if(!Validator.isNameField(Name))
  	{
  	  Message = "Invalid Test Name";
  	}
  	else if(Name.length() > 20)
  	{
  	  Message = "Test Name out of bounds";
  	}
  	else if(Description == null)
  	{
  	  Message = "Description not entered";
  	}
  	else if(Description.length() > 500)
  	{
  	  Message = "Description out of bounds";
  	}
  	else if(Pass_Score < 0 || Pass_Score > 100)
  	{
  	  Message = "Invalid Pass Score";
  	}
  	else if(time < 0 || time > 180)
  	{
  	  Message = "Invalid Test Time";
  	}
  	else if(Total_Ques < 0)
  	{
  	  Message = "Invalid Total Questions";
  	}
  	else if(Test_Type && !Ques_Type.equals("MC") && !Ques_Type.equals("TF") && !Ques_Type.equals("MA"))
  	{
  	  Message = "Invalid Question Type";
  	}
  	else
  	{
      if(!Test_Type)
  	    Ques_Type = "";
  	  
  	  flag = true;
  	  Message = "Process Completed";
  	}

  	return flag;
  }
  
  public void setTest(int Test_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;
  	
    Database db = new Database(pool);
  	rs = db.RetriveDb("Select * from Test where Test_Id = " + Test_Id);

  	if(rs.next())
  	{
      this.Test_Id = Test_Id;

  	  Name = rs.getString("Name");
  	  Description = rs.getString("Description");
      Scrolling = rs.getBoolean("Scrolling");
  	  Pass_Score = rs.getInt("Pass_Score");
  	  time = rs.getInt("Time");
  	  Hide = rs.getBoolean("Hide");
  	  Test_Type = rs.getBoolean("Test_Type");

  	  if(Test_Type == true)
  	    Ques_Type = rs.getString("Ques_Type");
  	  
      Total_Ques = rs.getInt("Total_Ques");
      Negative = rs.getBoolean("Negative");
  	}  	
  }
  
  public void setTest(int Test_Id,ConnectionPool pool,String Query) throws Exception
  {
  	ResultSet rs = null;
  	
  	if(Query.toLowerCase().contains(" where "))
      Query = Query + " and";
  	else
  	  Query = Query + " where";

    Database db = new Database(pool);
  	rs = db.RetriveDb(Query + " Test_Id = " + Test_Id);

    //this.Test_Id = Test_Id;
    
    if(rs.next())
      setTest(rs);
  }

  public void setTest(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Test_Id",rs))
  	  this.Test_Id = rs.getInt("Test_Id");

  	if(Database.isColumn("Name",rs))
  	  Name = rs.getString("Name");
  	 
  	if(Database.isColumn("Description",rs))
  	  Description = rs.getString("Description");
  	  
  	if(Database.isColumn("Scrolling",rs))
  	  Scrolling = rs.getBoolean("Scrolling");
  	  
  	if(Database.isColumn("PassScore",rs))
  	  Pass_Score = rs.getInt("PassScore");
	    
  	if(Database.isColumn("Ques_Type",rs))
  	  Ques_Type = rs.getString("Ques_Type");
  	  
  	if(Database.isColumn("Time",rs))
  	  time = rs.getInt("Time");
  	  
  	if(Database.isColumn("Hide",rs))
  	  Hide = rs.getBoolean("Hide");
  	  
  	if(Database.isColumn("Test_Type",rs))
  	  Test_Type = rs.getBoolean("Test_Type");

  	if(Database.isColumn("Total_Ques",rs))
  	  Total_Ques = rs.getInt("Total_Ques");

  	if(Database.isColumn("Negative",rs))
  	  Negative = rs.getBoolean("Negative");
  }

  public void setTest(HttpServletRequest req)
  {
    Name = req.getParameter("TestName");
    Description = req.getParameter("TestDesc");
    Ques_Type = req.getParameter("Ques_Type");

	try
	{
      Pass_Score = Integer.parseInt(req.getParameter("MinPassScore"));
      time = Integer.parseInt(req.getParameter("TestTime"));
      Total_Ques = Integer.parseInt(req.getParameter("Total_Ques"));
      
      setScrolling(Integer.parseInt(req.getParameter("Scrolling")));
      setHide(Integer.parseInt(req.getParameter("Hide")));
      setTestType(Integer.parseInt(req.getParameter("TestType")));
      setNegative(Integer.parseInt(req.getParameter("Negative")));      
	}
	catch(NumberFormatException nex)
	{
	  Message = "Illegal NumberFormat";
    }
  }

  public boolean CreateTest(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid() && isNameAbsent(db,0))
    {
      StringBuffer Query = new StringBuffer();
      
      Query.append("Insert into Test values(Test_Id.nextval");
	  Query.append(",'").append(Name);

      if(Scrolling == true)
        Query.append("',1,");
      else
        Query.append("',0,");

	  Query.  append(Pass_Score).append(",'").append(Description);
	  Query.append("','").append(Ques_Type) .append("',").append(time);
		  
      if(Hide == true)
        Query.append(",1,");
      else
        Query.append(",0,");

      if(Test_Type == true)
        Query.append("1,");
      else
        Query.append("0,");

	  Query.append(Total_Ques);

      if(Negative == true)
        Query.append(",1)");
      else
        Query.append(",0)");

	  db.ExecuteDb(Query.toString());
	  status = true;
    }

	return(status);
  }
  
  public boolean EditTest(int Test_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);

    if(isValid() && isNameAbsent(db,Test_Id))
    {
      if(isPresent(db,Test_Id))
      {
        StringBuffer Query = new StringBuffer("Update Test set Name = '" + Name);
       
        if(Scrolling == true)
          Query.append("',Scrolling = 1");
        else
          Query.append("',Scrolling = 0");

        Query.append(",Pass_Score = ")  .append(Pass_Score);
	    Query.append(",Description = '").append(Description);
	    Query.append("',Ques_Type = '") .append(Ques_Type);
        Query.append("',Time = ")       .append(time);

        if(Hide == true)
         Query.append(",Hide = 1");
        else
         Query.append(",Hide = 0");

        if(Test_Type == true)
         Query.append(",Test_Type = 1");
        else
         Query.append(",Test_Type = 0");

        Query.append(",Total_Ques = ").append(Total_Ques);
         
        if(Negative == true)
          Query.append(",Negative = 1");
        else
          Query.append(",Negative = 0");

		Query.append(" where Test_Id = ").append(Test_Id);

  	    db.ExecuteDb(Query.toString());
  	    status = true;
  	  }
  	}
  	 
  	return(status);
  }
  
  public boolean DeleteTest(int Test_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);

    if(isPresent(db,Test_Id))
    {
	  String Query = "Select Post_Id,N.Category_Id from Post,Table(Post.CatEntry )N where N.Test_Id = " + 
	                  Test_Id;

  	  ResultSet rs = db.RetriveDb(Query);
      int P_Id = 0;
          
      while(rs.next())
      {
        Database db1 = new Database(pool);
        Query = null;
        int Post_Id = rs.getInt(1);
        int Cat_Id = rs.getInt(2);
         
        if(Cat_Id != 0)
        {
          Query = "Delete from TestQuestions where Post_Id = " + 
                   Post_Id + " and Category_Id = " + Cat_Id;
         
          db1.ExecuteDb(Query);
        }
        
        if(P_Id != Post_Id)
        {
          System.out.println("Post = " + Post_Id);
        
          Query = "Update Table(select CatEntry from Post where Post_Id = " +
                   Post_Id + ")N set N.Test_Id = 0 where N.Test_Id = " + Test_Id;
          
          db1.ExecuteDb(Query);
          P_Id = Post_Id;
        }
      }

      Query = "Delete from Test where Test_Id = " + Test_Id;
      db.ExecuteDb(Query.toString());
      status = true;
  	}
  	 
  	return(status);
  }
  
  private boolean isNameAbsent(Database db,int Test_Id)
  throws SQLException
  {
  	boolean flag = false;

  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Test_Id) from Test where Name = '" + Name + "'");

    if(rs.next())
  	{
  	  int value = rs.getInt(1);
  	  
  	  if(value == 0)
  	  {
  	    flag = true;
  	  }
  	  if(Test_Id > 0 && value == 1)
  	  {
  	    rs = null;
  	    rs = db.RetriveDb("Select Test_Id from Test where Name = '" + Name + "'");
  	    rs.next();
  	    
  	    if(Test_Id == rs.getInt(1))
  	      flag = true;
  	    else
  	      Message = "Please insert another Test Name";
  	  }
  	  else
  	  {
  	    Message = "Please insert another Test Name";
  	  }
  	}

  	return flag;
  }
  
  private boolean isPresent(Database db,int Test_Id)
  throws SQLException
  {
  	boolean flag = false;

  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Test_Id) from Test where Test_Id = " + Test_Id);

    if(rs.next())
  	{
  	  if(rs.getInt(1) == 1)
  	  {
  	    flag = true;
  	  }
  	  else
  	  {
  	    Message = "Invalid Test Name";
  	  }
  	}

  	return flag;
  }

 public static String getName(ConnectionPool pool,int Test_Id)
 throws Exception
 {
   String Tname = null;
  
   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select Name from Test where Test_Id = " + Test_Id);
  	  
   if(res.next())
    Tname = res.getString("Name");

   return Tname;
 }
 
 public int hashcode()
 {
   return (int)Test_Id;
 } 
 
 public String toString()
 {
   return Name;
 }
 
 public boolean equals(Object obj)
 {
   if (obj == this)
     return true;

   if (!(obj instanceof Test))
     return false;

   Test tst = (Test) obj;

   return tst.Test_Id == this.Test_Id  &&
          tst.Name.equals(this.Name);
 }
}