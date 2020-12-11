package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;

public class TF_Question extends Question
{ 
  private long TF_Ques_Id = 0;
  private Boolean CorrectAns = null;

  public TF_Question()
  {
    super();
  }
  
  public TF_Question(long Ques_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setTF_Question(Ques_Id,pool);
  }
  
  public TF_Question(HttpServletRequest req)
  {
  	super();
  	setTF_Question(req);
  }
 
  public long getTF_QuesId()
  {
  	return TF_Ques_Id;
  }

  public Boolean CorrectAns()
  {
  	return CorrectAns;
  }
  
  public int getCorrectAns()
  {
  	if(CorrectAns == null)
  	  return 0;
  	else if(CorrectAns == true)
  	  return 1;
  	else if(CorrectAns == false)
  	  return 2;
  	else
  	  return 0;
  }

  public void setCorrectAns(boolean correct)
  {
  	CorrectAns = correct;
  }

  public void setCorrectAns(int correct)
  {
    if(correct == 1)
      CorrectAns = true;
  	else if(correct == 2)
      CorrectAns = false;
    else
      CorrectAns = null;
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	
  	if(super.isValid() == false)
  	{
  	  flag = false;
  	}
  	else if(CorrectAns == null)
  	{
  	  setMessage("Correct Answer not selected");
  	}
  	else
  	{
  	  flag = true;
  	  setMessage("Process Completed");
  	}
  	
  	return flag;
  }
  
  public void setTF_Question(long TF_Ques_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;

    Database db = new Database(pool);

  	String Query = "Select A.*,B.Correct_Ans from Questions A,TF_Answers B where A.Ques_Id = " + 
  	               TF_Ques_Id + " and B.Ques_Id = " + TF_Ques_Id;
  	rs = db.RetriveDb(Query);

  	if(rs.next())
  	{
      this.TF_Ques_Id = TF_Ques_Id;

  	  setQuestion(rs.getString("Question"));
   	  setCategory_Id(rs.getInt("Category_Id"));
  	  setType(rs.getString("Type"));
  	  setCorrectAns(rs.getInt("Correct_Ans"));
  	}  	

  }
  
  public void setTF_Question(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Ques_Id",rs))
  	  this.TF_Ques_Id = rs.getInt("Ques_Id");

    setQuestion(rs);

    if(Database.isColumn("Correct_Ans",rs))
  	  setCorrectAns(rs.getInt("Correct_Ans"));
  }

  public void setTF_Question(HttpServletRequest req)
  {
   	setQuestion(req);
   	setType("TF");

    try
  	{
  	  int ans = Integer.parseInt(req.getParameter("Ans"));
  	  setCorrectAns(ans);
  	}
  	catch(NumberFormatException nex)
  	{
  	  setMessage(nex.getMessage());
  	}
  }

  public boolean Create_TF_Question(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid())
    {
      StringBuffer Query = new StringBuffer();
      Query.append("Insert into Questions values(Ques_Id.nextval,'");
      Query.append(getQuestion()).append("',").append(getCategory_Id()).append(",'TF')");
	 
	  db.ExecuteDb(Query.toString());
		
	  ResultSet rs = null;
	  rs = db.RetriveDb("Select Ques_Id.Currval from Questions");
	  rs.next();
		
	  long Ques_Id = rs.getLong(1);

	  Query = new StringBuffer();
	  Query.append("Insert into TF_Answers values(TF_Ans_Id.nextval,");
	  Query.append(Ques_Id).append("," + getCorrectAns()).append(")");

      db.ExecuteDb(Query.toString());
	  status = true;
    }
	
	return(status);
  }
  
  public boolean Edit_TF_Question(long Ques_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);
        
    if(isValid())
    {
      if(isPresent(db,Ques_Id))
      {
        StringBuffer Query = new StringBuffer();
        Query.append("Update Questions set Question = '").append(getQuestion());
        Query.append("',Category_Id = ").append(getCategory_Id());
        Query.append(" where Ques_Id = ").append(Ques_Id);

        db.ExecuteDb(Query.toString());

        Query = new StringBuffer();
        Query.append("Update TF_Answers  set Correct_Ans = ");
        Query.append(getCorrectAns()).append(" where Ques_Id = ").append(Ques_Id);

        db.ExecuteDb(Query.toString());
        status = true;
      }
  	}
  	 
  	return(status);
  }
  
  public int hashcode()
  {
    return (int)TF_Ques_Id;
  } 
 
  public String toString()
  {
    return getQuestion();
  }
 
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;

    if (!(obj instanceof TF_Question))
      return false;

    TF_Question tfques = (TF_Question) obj;

    return tfques.TF_Ques_Id == this.TF_Ques_Id  &&
           tfques.getQuestion().equals(this.getQuestion()) &&
           tfques.CorrectAns.equals(this.CorrectAns);
  }
}