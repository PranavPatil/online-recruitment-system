package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;

public class Admin extends Person
{ 
  private int Admin_Id = 0;
  private int Access_Id = 0;

  public Admin()
  {
  	super();
  }
  
  public Admin(int Admin_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setAdmin(Admin_Id,pool);
  }
  
  public Admin(HttpServletRequest req)
  {
	super(req);
	try
	{
      Access_Id = Integer.parseInt(req.getParameter("Designation"));	
    }
	catch(NumberFormatException nex)
	{
	  setMessage("Illegal NumberFormat");
    }
  }
  	
  public int getAdminId()
  {
  	return Admin_Id;
  }

  public int getAccess_Id()
  {
  	return Access_Id;
  }
  
  public void setAccess_Id(int access_id)
  {
  	if(access_id > 1 && access_id < 7)
  	  Access_Id = access_id;
  }
  
  public boolean isValid()
  {
  	boolean flag = false;
  	
  	if(super.isValid() == false)
  	{
  	  flag = false;
  	}
  	else if(Access_Id < 2 || Access_Id > 6)
  	{
  	  setMessage("Designation not entered");
  	}
  	else
  	{
  	  flag = true;
  	  setMessage("Process Completed");
  	}
  	
  	return flag;
  }
  
  public void setAdmin(int Admin_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;
  	
  	Database db = new Database(pool);
  	rs = db.RetriveDb("Select * from Admin where Admin_Id = " + Admin_Id);
  	
  	if(rs.next())
  	{
      this.Admin_Id = Admin_Id;

  	  setFname(rs.getString("Fname"));
  	  setLname(rs.getString("Lname"));
  	  setTelephone(rs.getLong("Telephone"));
  	  setEmail(rs.getString("Email"));
  	  setLogin(rs.getString("Login"));
  	  setPassword(rs.getString("Password"));

  	  Access_Id = rs.getInt("Access_Id");
  	}
  }

  public void setAdmin(int Admin_Id,ConnectionPool pool,String Query) throws Exception
  {
  	ResultSet rs = null;
  	
  	if(Query.toLowerCase().contains(" where "))
      Query = Query + " and";
  	else
  	  Query = Query + " where";

    Database db = new Database(pool);
  	rs = db.RetriveDb(Query + " Admin_Id = " + Admin_Id);

    this.Admin_Id = Admin_Id;

  	if(rs.next())
      setAdmin(rs);
  }

  public void setAdmin(ResultSet rs) throws SQLException
  {
    if(Database.isColumn("Admin_Id",rs))
      this.Admin_Id = rs.getInt("Admin_Id");

    setPerson(rs);

    if(Database.isColumn("Access_Id",rs))
  	  Access_Id = rs.getInt("Access_Id");
  }

  public void setAdmin(HttpServletRequest req)
  {
	setPerson(req);
	try
	{
      Access_Id = Integer.parseInt(req.getParameter("Designation"));	
    }
	catch(NumberFormatException nex)
	{
	  setMessage("Illegal NumberFormat");
    }
  }

  public boolean CreateAdmin(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);

    if(isValid() && isNameAbsent(db,0))
    {
      StringBuffer Query = new StringBuffer();
      Query.append("Insert into Admin values(Admin_Id.nextval");
	  Query.append(",'") .append(getFname()).append("','").append(getLname());
	  Query.append("','").append(getLogin()).append("','").append(getPassword());
	  Query.append("',").append(getTelephone()).append(",'").append(getEmail());
	  Query.append("',").append(Access_Id);
	  Query.append(")");

	  db.ExecuteDb(Query.toString());
  	  status = true;
    }
	
	return(status);
  }
  
  public boolean EditAdmin(int Admin_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    setLogin("");
    setAccess_Id(2);
    
    Database db = new Database(pool);

    if(isValid() && isNameAbsent(db,Admin_Id))
    {
      if(isPresent(db,Admin_Id))
      {
        StringBuffer Query = new StringBuffer();
        Query.append("Update Admin set Fname='");
    	Query.append(getFname()) .append("',Lname='");
		Query.append(getLname()) .append("',Password='").append(getPassword());
        Query.append("',Telephone=").append(getTelephone());
        Query.append(",Email='").append(getEmail());
        Query.append("' Where Admin_Id = ").append(Admin_Id);

  	    db.ExecuteDb(Query.toString());
  	    status = true;
  	  }
  	}
  	 
  	return(status);
  }

  public boolean EditAccessId(int Admin_Id,int Access_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);

    if(isPresent(db,Admin_Id))
    {
      StringBuffer Query = new StringBuffer();
      Query.append("Update Admin set Access_Id=");
      Query.append(Access_Id).append(" Where Admin_Id = ").append(Admin_Id);

  	  db.ExecuteDb(Query.toString());
  	  status = true;
  	}
  	 
  	return(status);
  }

  public boolean DeleteAdmin(int Admin_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);

    if(isPresent(db,Admin_Id))
    {
      String Query = "Delete from Admin where Admin_Id = " + Admin_Id;

      db.ExecuteDb(Query);
      status = true;
    }

  	return(status);
  }

  private boolean isNameAbsent(Database db,int Admin_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = db.RetriveDb("Select count(Admin_Id) from Admin where Login = '"
  	               + getLogin() + "'");
      
    if(rs.next())
  	{
  	  int value = rs.getInt(1);
  	  
  	  if(value == 0)
  	  {
  	    flag = true;
  	  }
  	  if(Admin_Id > 0 && value == 1)
  	  {
  	    rs = null;
  	    rs = db.RetriveDb("Select Admin_Id from Admin where Login = '" + getLogin() + "'");
  	    rs.next();
  	    
  	    if(Admin_Id == rs.getInt(1))
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
  
  private boolean isPresent(Database db,int Admin_Id)
  throws SQLException
  {
  	boolean flag = false;

  	ResultSet rs = db.RetriveDb("Select count(Admin_Id) from Admin where Admin_Id = " + Admin_Id);
      
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

  public static int getAdminId(ConnectionPool pool,String AdminLogin)
  throws Exception
  {
  	int Aid = 0;

   	Database db = new Database(pool);
  	ResultSet res = db.RetriveDb("Select Admin_Id from Admin where Login = '" + AdminLogin + "'");
  	  
  	if(res.next())
  	 Aid = res.getInt("Admin_Id");

  	return Aid;
  }
  
 public static String getLoginId(ConnectionPool pool,int Admin_Id)
 throws Exception
 {
   String name = null;

   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select Login from Admin where Admin_Id = " + Admin_Id);

   if(res.next())
    name = res.getString("Login");

   return name;
 }

  public static long getAccessCode(ConnectionPool pool,String AdminLogin)
  throws Exception
  {
  	long Aid = 0;

    Database db = new Database(pool);
  	ResultSet res = db.RetriveDb("Select AccessCode from Accessibility where Access_Id=(Select Access_Id from Admin where Login = '" 
  	                + AdminLogin + "')");
  	  
  	if(res.next())
  	 Aid = res.getLong("AccessCode");

  	return Aid;
  }

  public int hashcode()
  {
    return (int)Admin_Id;
  } 
 
  public String toString()
  {
    return getFname() + " " + getLname();	
  }
 
  public boolean equals(Object obj)
  {
    if (obj == this)
      return true;

    if (!(obj instanceof Admin))
      return false;

    Admin adm = (Admin) obj;

    return adm.Admin_Id == this.Admin_Id  &&
           adm.getFname().equals(this.getFname()) &&
           adm.getLname().equals(this.getLname());
  }
}