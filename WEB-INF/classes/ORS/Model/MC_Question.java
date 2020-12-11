package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;

public class MC_Question extends Question
{ 
  private long MC_Ques_Id = 0;
  private String Ans1=null,Ans2=null,Ans3=null,Ans4=null;
  private short CorrectAns = -1;

  public MC_Question()
  {
    super();
  }

  public MC_Question(long Ques_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setMC_Question(Ques_Id,pool);
  }
  
  public MC_Question(HttpServletRequest req)
  {
  	super();
  	setMC_Question(req);
  }

  public long getMC_QuesId()
  {
  	return MC_Ques_Id;
  }
  
  public String getAns1()
  {
  	return Ans1;
  }
  
  public void setAns1(String ans1)
  {
  	Ans1 = ans1;
  }

  public String getAns2()
  {
  	return Ans2;
  }
  
  public void setAns2(String ans2)
  {
  	Ans2 = ans2;
  }

  public String getAns3()
  {
  	return Ans3;
  }
  
  public void setAns3(String ans3)
  {
  	Ans3 = ans3;
  }

  public String getAns4()
  {
  	return Ans4;
  }
  
  public void setAns4(String ans4)
  {
  	Ans4 = ans4;
  }

  public short getCorrectAns()
  {
  	return CorrectAns;
  }
  
  public void setCorrectAns(short correct)
  {
  	if(correct > 0 && correct < 5)
  	  CorrectAns = correct;
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	System.out.println("Correct ans = " + CorrectAns);
  	
  	if(super.isValid() == false)
  	{
  	  flag = false;
  	}
  	else if(Ans1 == null)
  	{
  	  setMessage("Choice 1 not entered");
  	}
  	else if(Ans1.length() > 30)
  	{
  	  setMessage("Invalid Choice 1");
  	}
  	else if(Ans2 == null)
  	{
  	  setMessage("Choice 2 not entered");
  	}
  	else if(Ans2.length() > 30)
  	{
  	  setMessage("Invalid Choice 2");
  	}
  	else if(Ans3 == null)
  	{
  	  setMessage("Choice 3 not entered");
  	}
  	else if(Ans3.length() > 30)
  	{
  	  setMessage("Invalid Choice 3");
  	}
  	else if(Ans4 == null)
  	{
  	  setMessage("Choice 4 not entered");
  	}
  	else if(Ans4.length() > 30)
  	{
  	  setMessage("Invalid Choice 4");
  	}
  	else if(CorrectAns < 1 || CorrectAns > 4)
  	{
  	  setMessage("Invalid Correct Answer");
  	}
  	else
  	{
  	  flag = true;
  	  setMessage("Process Completed");
  	}
  	
  	return flag;
  }
  
  public void setMC_Question(long MC_Ques_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;

    Database db = new Database(pool);

  	String Query = "Select A.*,B.Correct_Ans,B.Ans1,B.Ans2,B.Ans3,B.Ans4 from Questions A,MC_Answers B where A.Ques_Id = " + 
  	               MC_Ques_Id + " and B.Ques_Id = " + MC_Ques_Id;
  	rs = db.RetriveDb(Query);

  	if(rs.next())
  	{
      this.MC_Ques_Id = MC_Ques_Id;

  	  setQuestion(rs.getString("Question"));
   	  setCategory_Id(rs.getInt("Category_Id"));
  	  setType(rs.getString("Type"));
  	  CorrectAns = rs.getShort("Correct_Ans");
  	  Ans1 = rs.getString("Ans1");
  	  Ans2 = rs.getString("Ans2");
  	  Ans3 = rs.getString("Ans3");
  	  Ans4 = rs.getString("Ans4");
  	}
  }
  
  public void setMC_Question(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Ques_Id",rs))
  	  this.MC_Ques_Id = rs.getInt("Ques_Id");

    setQuestion(rs);

    if(Database.isColumn("Correct_Ans",rs))
  	  CorrectAns = rs.getShort("Correct_Ans");

    if(Database.isColumn("Ans1",rs))
  	  Ans1 = rs.getString("Ans1");

    if(Database.isColumn("Ans2",rs))
  	  Ans2 = rs.getString("Ans2");

    if(Database.isColumn("Ans3",rs))
  	  Ans3 = rs.getString("Ans3");

    if(Database.isColumn("Ans4",rs))
  	  Ans4 = rs.getString("Ans4");
  }
  
  public void setMC_Question(HttpServletRequest req)
  {
    setQuestion(req);
    setType("MC");

    Ans1 = req.getParameter("Ans1");
    Ans2 = req.getParameter("Ans2");
    Ans3 = req.getParameter("Ans3");
    Ans4 = req.getParameter("Ans4");

  	try
  	{
  	  System.out.println("Correct Ans = " + req.getParameter("Ans"));
  	  CorrectAns = Short.parseShort(req.getParameter("Ans"));
  	}
  	catch(NumberFormatException nex)
  	{
      nex.printStackTrace();
  	  setMessage(nex.getMessage());
  	}
  }

  public boolean Create_MC_Question(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid())
    {
      StringBuffer Query = new StringBuffer();
      Query.append("Insert into Questions values(Ques_Id.nextval,'");
      Query.append(getQuestion()).append("',").append(getCategory_Id()).append(",'MC')");
	 
	  db.ExecuteDb(Query.toString());

	  ResultSet rs = null;
	  rs = db.RetriveDb("Select Ques_Id.Currval from Questions");
	  rs.next();
	
	  long Ques_Id = rs.getLong(1);

      Query = new StringBuffer();
	  Query.append("Insert into MC_Answers values(MC_Ans_Id.nextval,");
	  Query.append(Ques_Id).append(",")  .append(CorrectAns).append(",'");
	  Query.append(Ans1)   .append("','").append(Ans2)      .append("','");
	  Query.append(Ans3)   .append("','").append(Ans4)      .append("')");

      db.ExecuteDb(Query.toString());
	  status = true;
    }
	
	return(status);
  }
  
  public boolean Edit_MC_Question(long Ques_Id,ConnectionPool pool)
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
        Query.append("',Category_Id = ")                 .append(getCategory_Id());
        Query.append(" where Ques_Id = ")                .append(Ques_Id);

        db.ExecuteDb(Query.toString());

        Query = new StringBuffer();
        Query.append("Update MC_Answers set Correct_Ans = ").append(CorrectAns);
	    Query.append(",Ans1 = '") .append(Ans1);
	    Query.append("',Ans2 = '").append(Ans2);
	    Query.append("',Ans3 = '").append(Ans3);
	    Query.append("',Ans4 = '").append(Ans4);
	    Query.append("' where Ques_Id = ").append(Ques_Id);

        db.ExecuteDb(Query.toString());
        status = true;
      }
  	}

  	return(status);
  }

  public int hashcode()
  {
    return (int)MC_Ques_Id;
  } 
 
  public String toString()
  {
    return getQuestion();
  }
 
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;

    if (!(obj instanceof MC_Question))
      return false;

    MC_Question mcques = (MC_Question) obj;

    return mcques.MC_Ques_Id == this.MC_Ques_Id  &&
           mcques.getQuestion().equals(this.getQuestion()) &&
           mcques.CorrectAns == this.CorrectAns;
  }
}