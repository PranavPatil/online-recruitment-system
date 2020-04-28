package ORS.Model;

import java.sql.*;
import javax.servlet.http.*;
import ORS.ConnPool.Database;
import ORS.ConnPool.ConnectionPool;

public abstract class Question implements Validate
{ 
  private String Question;
  private String Type;
  private int Cat_Id = 0;
  private String Message = "";

  public Question()
  {
    super();
  }
  
  public Question(HttpServletRequest req)
  {
  	super();
    setQuestion(req);
  }

  public String getQuestion()
  {
  	return Question;
  }
  
  public void setQuestion(String question)
  {
  	Question = question;
  }
  
  public int getCategory_Id()
  {
  	return Cat_Id;
  }
  
  public void setCategory_Id(int id)
  {
  	Cat_Id = id;
  }
  
  public String getType()
  {
  	return Type;
  }
  
  public void setType(String type)
  {
  	Type = type;
  }
  
  final public String getMessage()
  {
  	return Message;
  }
  
  final protected void setMessage(String message)
  {
  	Message = message;
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	
  	if(Question == null)
  	{
  	  Message = "Question not entered";
  	}
  	else if(Question.length() > 200)
  	{
  	  Message = "Question out of bounds";
  	}
  	else if(Type == null)
  	{
  	  Message = "Question Type not entered";
  	}
  	else if(!Type.equals("MC") && !Type.equals("TF") && !Type.equals("MA"))
  	{
  	  Message = "Invalid Question Type";
  	}
  	else if(Cat_Id <= 0)
  	{
  	  Message = "Question Category not specified";
  	}
  	else
  	{
  	  flag = true;
  	  Message = "Process Completed";
  	}
  	
  	return flag;
  }

  protected void setQuestion(HttpServletRequest req)
  {
  	Question = req.getParameter("Question");
  	Question = Question.trim();
  	
  	try
  	{
  	  Cat_Id = Integer.parseInt(req.getParameter("SelCategory"));
  	}
  	catch(NumberFormatException numex)
  	{
  	  Cat_Id = 0;
  	  Message = "Invalid Category Id : " + numex.getMessage();
  	}
  }

  protected void setQuestion(ResultSet rs) throws SQLException
  {
    if(Database.isColumn("Question",rs))
  	  setQuestion(rs.getString("Question"));
  	
  	if(Database.isColumn("Category_Id",rs))
   	  setCategory_Id(rs.getInt("Category_Id"));
  	
  	if(Database.isColumn("Type",rs))
  	  setType(rs.getString("Type"));
  }
  
  public boolean DeleteQuestion(long Ques_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);

    if(isPresent(db,Ques_Id))
    {
      String Query = "Delete from Questions where Ques_Id = " + Ques_Id;

      db.ExecuteDb(Query);
      status = true;
    }

  	return(status);
  }
  
  protected boolean isPresent(Database db,long Ques_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Ques_Id) from Questions where Ques_Id = " + Ques_Id);

    if(rs.next())
  	{
  	  if(rs.getInt(1) == 1)
  	  {
  	    flag = true;
  	  }
  	  else
  	  {
 	    setMessage("Invalid Question Id");
  	  }
  	}

  	return flag;
  }
}