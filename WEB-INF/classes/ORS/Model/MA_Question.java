package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;

public class MA_Question extends Question
{ 
  private long MA_Ques_Id = 0;
  private String Ans1=null,Ans2=null,Ans3=null,Ans4=null;
  private int CorrectAns = -1;

  public MA_Question()
  {
    super();
  }
  
  public MA_Question(long Ques_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setMA_Question(Ques_Id,pool);
  }
  
  public MA_Question(HttpServletRequest req)
  {
  	super();
    setMA_Question(req);
  }

  public long getMA_QuesId()
  {
  	return MA_Ques_Id;
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

  public int getCorrectAns()
  {
  	return CorrectAns;
  }
  
  public int getCorrectAns(int no)
  {
    int ans = -1;

  	switch(no)
  	{
  	  case 1:  ans = CorrectAns / 1000;
  	           break;

  	  case 2:  ans = CorrectAns / 100;
  	           ans = ans % 10;
  	           break;

  	  case 3:  ans = CorrectAns % 100;
  	           ans = ans / 10;
  	           break;

  	  case 4:  ans = CorrectAns % 10;
  	           break;

  	  default: ans = -1;
  	           break;
  	}
  	
  	if(ans < 0 || ans > 4)
  	  return -1;
  	else
  	  return ans;
  }

  public void setCorrectAns(int correct)
  {
  	CorrectAns = correct;
  }

  public void setCorrectAns(int no,boolean check)
  {
  	switch(no)
  	{
  	 case 1:  if(check == true)
  	            CorrectAns = (1 * 1000) + (getCorrectAns(2) * 100) + (getCorrectAns(3) * 10) + getCorrectAns(4);
  	          else
  	            CorrectAns = (getCorrectAns(2) * 100) + (getCorrectAns(3) * 10) + getCorrectAns(4);
  	          break;

  	 case 2:  if(check == true)
  	            CorrectAns = (getCorrectAns(1) * 1000) + (2 * 100) + (getCorrectAns(3) * 10) + getCorrectAns(4);
  	          else
  	            CorrectAns = (getCorrectAns(1) * 1000) + (getCorrectAns(3) * 10) + getCorrectAns(4);
  	          break;

  	 case 3:  if(check == true)
  	            CorrectAns = (getCorrectAns(1) * 1000) + (getCorrectAns(2) * 100) + (3 * 10) + getCorrectAns(4);
  	          else
  	            CorrectAns = (getCorrectAns(1) * 1000) + (getCorrectAns(2) * 100) + getCorrectAns(4);
  	          break;

  	 case 4:  if(check == true)
  	            CorrectAns = (getCorrectAns(1) * 1000) + (getCorrectAns(2) * 100) + (getCorrectAns(3) * 10) + 4;
  	          else
  	            CorrectAns = (getCorrectAns(1) * 1000) + (getCorrectAns(2) * 100) + (getCorrectAns(3) * 10);
  	          break;

  	 default: break;
    }
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	
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
  	else if(CorrectAns < 0 || CorrectAns > 1234)
  	{
  	  setMessage("Invalid Correct Ans");
  	}
  	else
  	{
  	  flag = true;
  	  setMessage("Process Completed");
  	}
  	
  	return flag;
  }
  
  public void setMA_Question(long MA_Ques_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;

    Database db = new Database(pool);

  	String Query = "Select A.*,B.Correct_Ans,B.Ans1,B.Ans2,B.Ans3,B.Ans4 from Questions A,MA_Answers B where A.Ques_Id = " + 
  	               MA_Ques_Id + " and B.Ques_Id = " + MA_Ques_Id;
  	rs = db.RetriveDb(Query);

  	if(rs.next())
  	{
      this.MA_Ques_Id = MA_Ques_Id;

  	  setQuestion(rs.getString("Question"));
   	  setCategory_Id(rs.getInt("Category_Id"));
  	  setType(rs.getString("Type"));
  	  CorrectAns = rs.getInt("Correct_Ans");
  	  Ans1 = rs.getString("Ans1");
  	  Ans2 = rs.getString("Ans2");
  	  Ans3 = rs.getString("Ans3");
  	  Ans4 = rs.getString("Ans4");
  	}  	

  }
  
  public void setMA_Question(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Ques_Id",rs))
  	  this.MA_Ques_Id = rs.getInt("Ques_Id");

    setQuestion(rs);

    if(Database.isColumn("Correct_Ans",rs))
  	  CorrectAns = rs.getInt("Correct_Ans");

    if(Database.isColumn("Ans1",rs))
  	  Ans1 = rs.getString("Ans1");

    if(Database.isColumn("Ans2",rs))
  	  Ans2 = rs.getString("Ans2");

    if(Database.isColumn("Ans3",rs))
  	  Ans3 = rs.getString("Ans3");

    if(Database.isColumn("Ans4",rs))
  	  Ans4 = rs.getString("Ans4");
  }

  public void setMA_Question(HttpServletRequest req)
  {
    setQuestion(req);
    setType("MA");

    Ans1 = req.getParameter("Ans1");
    Ans2 = req.getParameter("Ans2");
    Ans3 = req.getParameter("Ans3");
    Ans4 = req.getParameter("Ans4");

  	try
  	{
  	  int AnsA = 0,AnsB = 0,AnsC = 0,AnsD = 0;

  	  if(req.getParameter("AnsA") != null)
  	    AnsA = Integer.parseInt(req.getParameter("AnsA"));
  	  
  	  if(req.getParameter("AnsB") != null)
  	    AnsB = Integer.parseInt(req.getParameter("AnsB"));

  	  if(req.getParameter("AnsC") != null)
  	    AnsC = Integer.parseInt(req.getParameter("AnsC"));

  	  if(req.getParameter("AnsD") != null)
        AnsD = Integer.parseInt(req.getParameter("AnsD"));

      if(AnsA < 0 || AnsA > 4)
	  	AnsA = -1;
      if(AnsB < 0 || AnsB > 4)
	  	AnsB = -1;
      if(AnsC < 0 || AnsC > 4)
	  	AnsC = -1;
      if(AnsD < 0 || AnsD > 4)
	  	AnsD = -1;

      if(AnsA != -1 && AnsB != -1 && AnsC != -1 && AnsD != -1)
      {
        CorrectAns = (AnsA * 1000) + (AnsB * 100) + (AnsC * 10) + AnsD;
	  }
	  else
	  {
	  	setMessage("Invalid Correct Answer");
      }
  	}
  	catch(NumberFormatException nex)
  	{
  	  setMessage(nex.getMessage());
  	}
  }

  public boolean Create_MA_Question(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid())
    {
      StringBuffer Query = new StringBuffer();
      Query.append("Insert into Questions values(Ques_Id.nextval,'");
      Query.append(getQuestion()).append("',").append(getCategory_Id() + ",'MA')");
	 
	  db.ExecuteDb(Query.toString());
		
	  ResultSet rs = null;
	  rs = db.RetriveDb("Select Ques_Id.Currval from Questions");
	  rs.next();
		
	  long Ques_Id = rs.getLong(1);

	  Query = new StringBuffer();
	  Query.append("Insert into MA_Answers values(MA_Ans_Id.nextval,");
	  Query.append(Ques_Id).append(",")  .append(CorrectAns).append(",'");
	  Query.append(Ans1)   .append("','").append(Ans2)           .append("','");
	  Query.append(Ans3)   .append("','").append(Ans4 + "')");

      db.ExecuteDb(Query.toString());
	  status = true;
    }

	return(status);
  }
  
  public boolean Edit_MA_Question(long Ques_Id,ConnectionPool pool)
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
	    Query.append("Update MA_Answers set Correct_Ans = ").append(CorrectAns);
	    Query.append(",Ans1 = '")                           .append(Ans1);
	    Query.append("',Ans2 = '")                          .append(Ans2);
	    Query.append("',Ans3 = '")                          .append(Ans3);
	    Query.append("',Ans4 = '")                          .append(Ans4);
	    Query.append("' where Ques_Id = ")                  .append(Ques_Id);

        db.ExecuteDb(Query.toString());
        status = true;
      }
  	}

  	return(status);
  }

  public int hashcode()
  {
    return (int)MA_Ques_Id;
  } 
 
  public String toString()
  {
    return getQuestion();
  }
 
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;

    if (!(obj instanceof MA_Question))
      return false;

    MA_Question maques = (MA_Question) obj;

    return maques.MA_Ques_Id == this.MA_Ques_Id  &&
           maques.getQuestion().equals(this.getQuestion()) &&
           maques.CorrectAns == this.CorrectAns;
  }
}