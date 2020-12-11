package ORS.User;

import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;
import ORS.ConnPool.Database;
import ORS.ConnPool.ConnectionPool;
import ORS.Exception.ApplicationException;

public class TestBuffer
{
  private ConnectionPool pool;
  public int Q_Load = 0,Q_Display = 0;
  public int CId = 0,option = 0,max = 0;
  public long Ques_Id = 0;
  public Calendar FinalTime = null;
  public String Next="Next Question",Prev=null;
  public String Ans1=null,Ans2=null,Ans3=null,Ans4=null;
  public String Type = null;	
  public boolean NewEntry = false,Scrolling = false,Hide = false;
  public ArrayList list = null;
	
  /*--------------------- Initialization Function --------------------------------*/

  public TestBuffer(long User_Id,int Post_Id,int Cat_Id,int Test_Id,ConnectionPool pl)
  throws Exception
  {
	list = new ArrayList();
    CId = Cat_Id;
	pool = pl;
    
	ResultSet res = null;
	int temp = 0;
	int Test_Type = 0;
    
    String Query = null;
    Database db = new Database(pool);
    
	Query = "Select N.Ques_Id from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id = " +
	         User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id;
    
	res=db.RetriveDb(Query);
    
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
    
	res = db.RetriveDb(Query);
	
	if(res.next())
	{
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
	}

	System.out.println(">>>>>>>Type>>>>>>>>>>>>> " + Type);
	System.out.println("TestPage Initialized Successfully !!");
  }	
   
	/*  ----------------------    Oracle Function    ----------------------  */
	
  public long doCallableStmt(int Category_Id,String Type)
  throws Exception
  {	  	
	CallableStatement statemt = null;
	Connection con = pool.getConnection();
	int no = 0;
	double num = 0;
	long Ques_Id = 0;

	num = (double) Math.random();	
	num = 100 * num;
	Ques_Id = (long) num;
    no = (int)Ques_Id;
    System.out.println("Function	 Ques_id = " + Ques_Id + "type is " + Type);
    //System.out.println("Category_id = " + Category_Id);
    
	String fun = " ? = call Random(?,?,?) "; 
	statemt = con.prepareCall("{" + fun + "}");
	statemt.setInt(2,Category_Id);
	statemt.setString(3,Type);
	statemt.setInt(4,no);
	statemt.registerOutParameter(1,Types.INTEGER);
	statemt.execute();
	Ques_Id = statemt.getLong(1);		 
	System.out.println("Function Returns = " + Ques_Id);
    statemt.close();
    con.close();
	return(Ques_Id);
  }

/*------------------------  Ans Entry  ----------------------------------*/


  public void AnsEntry(long User_Id,int Post_Id,int Cat_Id,long Ques_Id,
                       int option,boolean Entry,int QLoad)
  throws Exception
  {		
    Database db = null;
	ResultSet res = null;
	String query = null;
	int val,Correct = 0;
	                
    System.out.println("Option is **************************** = " + option);
    //System.out.println("Type = " + Type + "  Entry = " + Entry);

	db = new Database(pool);

	if(Entry == true)
	{
	  res = db.RetriveDb("select Type from Questions where Ques_Id = " + Ques_Id);
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
	  res = db.RetriveDb(query);
	  res.next();
	  Correct = res.getInt(1);

      int count = 0;
      query = null;
      query = "Select count(rownum) from TestQuestions where User_Id = " + User_Id
               + " and Post_Id = " + Post_Id  + " and Category_Id = " + Cat_Id;
        
      res = null;
      res = db.RetriveDb(query);
      res.next();
      count = res.getInt(1);

      query = null;
        
      if(count > 0)
      {
       query = "insert into Table(select TestEntry from TestQuestions where User_Id = " + 
		       User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id + 
               ") values (TestQues_TY("+ QLoad + "," + Ques_Id + "," + option + "," + Correct + "))";
      }
      else
      {
       query = "insert into TestQuestions values(" + User_Id + "," + Post_Id + "," + Cat_Id + "," +
	           "TestQues_NT(TestQues_TY("+ QLoad + "," + Ques_Id + "," + option + "," + Correct + ")))";
      }
        
      db.ExecuteDb(query);
	}
	else if(Entry == false)
	{
	  query = null;		
	  query = "Update Table(select TestEntry from TestQuestions where User_Id = " +
	           User_Id + " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id + 
	          ")N set N.User_Answer = " + option +
	          " where N.Ques_Id = " + Ques_Id;
	  db.ExecuteDb(query);
	}
  } 
	
/*------------------------  Display Questions  ----------------------------------*/


  public void Display(long Ques_Id,int option,JspWriter out)
  throws Exception
  {
    Database db1,db2 = null;
	ResultSet rs1 = null;
	int count=0;
	int opn1=0,opn2=0,opn3=0,opn4=0;

    //System.out.println("Ques_Id = " + Ques_Id);
    //System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDoption = " + option);
    db1 = new Database(pool);
    db2 = new Database(pool);
    
	ResultSet rs = db1.RetriveDb("Select Question,Type from Questions where Ques_Id = " + Ques_Id);
     	 
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

       out.println("<br><br>");
      }
      else if (temp.equals("MC"))
      {
       rs1 = db2.RetriveDb("select Ans1,Ans2,Ans3,Ans4 from " + "MC_Answers where Ques_Id = " +
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
	    rs1=db2.RetriveDb("Select Ans1,Ans2,Ans3,Ans4 from " + "MA_Answers where Ques_Id = " +
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
	}
  }	 
	
	/*----------------------- get Multiple Answer Option Function -------------------------*/


  public int getMAOption(String Ans1,String Ans2,String Ans3,String Ans4)
  {
    int opn1=0,opn2=0,opn3=0,opn4=0;
    int option = 0;
  	
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
    return option;
  }
  
  public ConnectionPool getConnectionPool()
  {
  	return pool;
  }

  public String toString()
  {
    return "Cid = " + CId;
  }
}
