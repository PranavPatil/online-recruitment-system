package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;
import ORS.Utils.Validator;

public class User extends Person
{ 
  private long User_Id;
  private String Mname;
  private String Gender;
  private String Address;
  private String Birthdate;
  private String Qualification;
  private String Branch;
  private int Experience;
  private int Select_No;
  private boolean Selected;
  private boolean Employee;

  public User()
  {
  	super();
  }
  
  public User(long User_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setUser(User_Id,pool);
  }
  
  public User(HttpServletRequest req)
  {
	super();
	setUser(req);
  }
  	
  public long getUserId()
  {
  	return User_Id;
  }

  public String getMname()
  {
  	return Mname;
  }
  
  public void setMname(String name)
  {
  	Mname = name;
  }

  public String getGender()
  {
  	return Gender;
  }
  
  public void setGender(String gender)
  {
  	Gender = gender;
  }
  
  public String getAddress()
  {
  	return Address;
  }
  
  public void setAddress(String add)
  {
  	Address = add;
  }
  
  public String getBirthdate()
  {
  	return Birthdate;
  }
  
  public void setBirthdate(String date)
  {
  	Birthdate = date;
  }
  
  public String getQualification()
  {
  	return Qualification;
  }
  
  public void setQualification(String qualfn)
  {
  	Qualification = qualfn;
  }
  
  public String getBranch()
  {
  	return Branch;
  }
  
  public void setBranch(String branch)
  {
  	Branch = branch;
  }
  
  public int getExperience()
  {
  	return Experience;
  }
  
  public void setExperience(int expnce)
  {
  	Experience = expnce;
  }
  
  public int getSelectno()
  {
  	return Select_No;
  }

  public boolean getSelected()
  {
  	return Selected;
  }

  public boolean getEmployee()
  {
  	return Employee;
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	
  	if(super.isValid() == false)
  	{
  	  flag = false;
  	}
  	else if(Mname == null)
  	{
  	  setMessage("Middle Name not entered");
  	}
  	else if(!Validator.isAlphabet(Mname))
  	{
  	  setMessage("Invalid Middle Name");
  	}
  	else if(Mname.length() > 20)
  	{
  	  setMessage("Middle Name out of bounds");
  	}
  	else if(Gender == null)
  	{
  	  setMessage("Gender not entered");
  	}
  	else if(!Gender.equals("M") && !Gender.equals("F"))
  	{
  	  setMessage("Invalid Gender");
  	}
  	else if(Address == null)
  	{
  	  setMessage("Address not entered");
  	}
  	else if(Address.length() > 500)
  	{
  	  setMessage("Address out of bounds");
  	}
  	else if(Birthdate == null)
  	{
  	  setMessage("Birthdate not entered");
  	}
  	else if(Birthdate.length() > 11)
  	{
  	  setMessage("Invalid Birthdate");
  	}
  	else if(Qualification == null)
  	{
  	  setMessage("Qualification not entered");
  	}
  	else if(Branch == null)
  	{
  	  setMessage("Branch not entered");
  	}
  	else if(Experience < 1 || Experience > 5)
  	{
  	  setMessage("Invalid Experience");
  	}
  	else
  	{
  	  flag = true;
  	  setMessage("Process Completed");
  	}
  	
  	return flag;
  }
  
  public void setUser(long User_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;
  	
    Database db = new Database(pool);
  	rs = db.RetriveDb("Select * from Users where User_Id = " + User_Id);

  	if(rs.next())
  	{
      this.User_Id = User_Id;

  	  setFname(rs.getString("Fname"));
  	  setLname(rs.getString("Lname"));
  	  setTelephone(rs.getLong("Telephone"));
  	  setEmail(rs.getString("Email"));
  	  setLogin(rs.getString("Login"));
  	  setPassword(rs.getString("Password"));

  	  Mname = rs.getString("Mname");
  	  Gender = rs.getString("Gender");
  	  Address = rs.getString("Address");
  	  Birthdate = rs.getString("Date_of_Birth");
  	  Birthdate = formatDate(Birthdate);
  	  Qualification = rs.getString("Qualification");
  	  Branch = rs.getString("Branch");
  	  Experience = rs.getInt("Experience");
      Select_No = rs.getInt("SelectNo");
      Selected = rs.getBoolean("Selected");
      Employee = rs.getBoolean("Employee");
  	}  	
  }
  
  public void setUser(long User_Id,ConnectionPool pool,String Query) throws Exception
  {
  	ResultSet rs = null;
  	
  	if(Query.toLowerCase().contains(" where "))
      Query = Query + " and";
  	else
  	  Query = Query + " where";

    Database db = new Database(pool);
  	rs = db.RetriveDb(Query + " User_Id = " + User_Id);

    //this.User_Id = User_Id;
    
    if(rs.next())
      setUser(rs);
  }

  public void setUser(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("User_Id",rs))
  	  this.User_Id = rs.getInt("User_Id");

    setPerson(rs);

  	if(Database.isColumn("Mname",rs))
  	  Mname = rs.getString("Mname");
  	 
  	if(Database.isColumn("Gender",rs))
  	  Gender = rs.getString("Gender");
  	  
  	if(Database.isColumn("Address",rs))
  	  Address = rs.getString("Address");
  	  
  	if(Database.isColumn("Date_of_Birth",rs))
  	{
  	  Birthdate = rs.getString("Date_of_Birth");
  	  Birthdate = formatDate(Birthdate);
  	}
	    
  	if(Database.isColumn("Qualification",rs))
  	  Qualification = rs.getString("Qualification");
  	  
  	if(Database.isColumn("Branch",rs))
  	  Branch = rs.getString("Branch");
  	  
  	if(Database.isColumn("Experience",rs))
  	  Experience = rs.getInt("Experience");
  	  
  	if(Database.isColumn("SelecteNo",rs))
  	  Select_No = rs.getInt("SelecteNo");

  	if(Database.isColumn("Selected",rs))
  	  Selected = rs.getBoolean("Selected");

  	if(Database.isColumn("Employee",rs))
  	  Employee = rs.getBoolean("Employee");
  }

  public void setUser(HttpServletRequest req)
  {
  	setPerson(req);
	Mname=req.getParameter("Mname");
	Mname = Mname.trim();
	Gender=req.getParameter("Gender");
	Address=req.getParameter("Address");
	Address = Address.trim();
	Birthdate=req.getParameter("Bdate");
	Qualification= req.getParameter("Qualification");
	Branch= req.getParameter("Branch");

	try
	{
	  Experience = Integer.parseInt(req.getParameter("Experience"));
	}
	catch(NumberFormatException nex)
	{
	  setMessage("Illegal NumberFormat");
    }
  }

  public boolean CreateUser(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid() && isNameAbsent(db,0))
    {
      StringBuffer Query = new StringBuffer();
      Query.append("Insert into Users values(User_Id.nextval");
	  Query.append(",'") .append(getFname())    .append("','").append(Mname);
	  Query.append("','").append(getLname())    .append("','").append(getLogin());
	  Query.append("','").append(getPassword()) .append("','").append(Gender);
	  Query.append("','").append(getTelephone()).append("','").append(Address);
      Query.append("','").append(getEmail())    .append("','").append(Birthdate);
	  Query.append("','").append(Qualification) .append("','").append(Branch);
	  Query.append("',") .append(Experience)    .append(",0,0,0)");

	  db.ExecuteDb(Query.toString());
	  status = true;
    }

	return(status);
  }
  
  public boolean EditUser(long User_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    setLogin("");
    
    Database db = new Database(pool);

    if(isValid() && isNameAbsent(db,User_Id))
    {
      if(isPresent(db,User_Id))
      {
        StringBuffer Query = new StringBuffer();
         
        Query.append("Update Users set FNAME='").append(getFname());
        Query.append("',MNAME='")        .append(Mname);
	    Query.append("',LNAME='")        .append(getLname());
		Query.append("',PASSWORD='")     .append(getPassword());
		Query.append("',GENDER='")       .append(Gender);
		Query.append("',TELEPHONE=")     .append(getTelephone());
		Query.append(",ADDRESS='")       .append(Address);
		Query.append("',EMAIL='")        .append(getEmail());
		Query.append("',DATE_OF_BIRTH='").append(Birthdate);
		Query.append("',QUALIFICATION='").append(Qualification);
		Query.append("',BRANCH='")       .append(Branch);
		Query.append("',EXPERIENCE=")    .append(Experience);
		Query.append(" Where User_Id=")  .append(User_Id);

  	    db.ExecuteDb(Query.toString());
  	    status = true;
  	  }
  	}
 
  	return(status);
  }
  
  public boolean DeleteUser(long User_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);

    if(isPresent(db,User_Id))
    {
      db.ExecuteDb("Delete from Result where User_Id = " + User_Id);
      db.ExecuteDb("Delete from TestQuestions where User_Id = " + User_Id);
      db.ExecuteDb("Delete from Users where User_Id = " + User_Id);

      status = true;
    }

  	return(status);
  }

   private boolean isNameAbsent(Database db,long User_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(User_Id) from Users where Login = '" + getLogin() + "'");

    if(rs.next())
  	{
  	  int value = rs.getInt(1);
  	    
  	  if(value == 0)
  	  {
  	    flag = true;
  	  }
      else if(User_Id > 0 && value == 1)
  	  {
  	    rs = null;
  	    rs = db.RetriveDb("Select User_Id from Users where Login = '" + getLogin() + "'");
  	    rs.next();
  	    
  	    if(User_Id == rs.getLong(1))
  	      flag = true;
  	    else
  	      setMessage("Please insert another Login Id");
  	  }
  	  else
  	  {
  	    setMessage("Please insert another Login Id");
  	  }
  	}

  	return flag;
  }
  
  private boolean isPresent(Database db,long User_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(User_Id) from Users where User_Id = " + User_Id);

    if(rs.next())
  	{
  	  if(rs.getInt(1) == 1)
  	  {
  	    flag = true;
  	  }
  	  else
  	  {
  	    setMessage("Invalid Login Id");
  	  }
  	}

  	return flag;
  }
  
  public static String formatDate(String date)
  {
    String day = null,year = null,month = null;
    int mn = 0;

    day = date.substring(8,10);
    month = date.substring(5,7);
    year =  date.substring(0,4);
			    
    try
    {
      mn = Integer.parseInt(month);
    }
    catch(NumberFormatException nex)
    {
      mn = 0;
    }
		    
    if(mn == 1)
      month = "Jan";
    else if(mn == 2)
      month = "Feb";
    else if(mn == 3)
      month = "Mar";
    else if(mn == 4)
      month = "Apr";
    else if(mn == 5)
      month = "May";
    else if(mn == 6)
      month = "Jun";
    else if(mn == 7)
      month = "Jul";
    else if(mn == 8)
      month = "Aug";
    else if(mn == 9)
      month = "Sep";
    else if(mn == 10)
      month = "Oct";
    else if(mn == 11)
      month = "Nov";
    else if(mn == 12)
      month = "Dec";
    else
      month = null;
		    
    if(day != null && month != null && year != null)
       return (day + "-" + month + "-" + year);
    else
       return null;
  }
  
 public static long getUserId(ConnectionPool pool,String UserLogin)
 throws Exception
 {
   long Uid = 0;

   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select User_Id from Users where Login = '" + UserLogin + "'");

   if(res.next())
    Uid = res.getLong("User_Id");

   return Uid;
 }
  
 public static String getLoginId(ConnectionPool pool,long User_Id)
 throws Exception
 {
   String name = null;

   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select Login from Users where User_Id = " + User_Id);

   if(res.next())
    name = res.getString("Login");

   return name;
 }

 public int hashcode()
 {
   return (int)User_Id;
 } 
 
 public String toString()
 {
   return getFname() + " " + Mname + " " + getLname();	
 }
 
 public boolean equals(Object obj)
 {
   if (obj == this)
     return true;

   if (!(obj instanceof User))
     return false;

   User usr = (User) obj;

   return usr.User_Id == this.User_Id  &&
          usr.getFname().equals(this.getFname()) &&
          usr.Mname.equals(Mname) &&
          usr.getLname().equals(this.getLname());
 }
}