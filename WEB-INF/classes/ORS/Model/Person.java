package ORS.Model;

import java.sql.*;
import javax.servlet.http.*;
import ORS.Utils.Validator;
import ORS.ConnPool.Database;

public abstract class Person implements Validate
{ 
  private String Fname;
  private String Lname;
  private String Email;
  private String Login;
  private String Passwd;
  private String Message;
  private long Telephone;

  public Person()
  {
    super();
  }
  
  public Person(HttpServletRequest req)
  {
  	super();
    setPerson(req);
  }

  public String getFname()
  {
  	return Fname;
  }
  
  public void setFname(String name)
  {
  	Fname = name;
  }
  
  public String getLname()
  {
  	return Lname;
  }
  
  public void setLname(String name)
  {
  	Lname = name;
  }
  
  public long getTelephone()
  {
  	return Telephone;
  }
  
  public void setTelephone(long teleno)
  {
  	Telephone = teleno;
  }

  public String getEmail()
  {
  	return Email;
  }
  
  public void setEmail(String mail)
  {
  	Email = mail;
  }
  
  public String getLogin()
  {
  	return Login;
  }
  
  public void setLogin(String login)
  {
  	Login = login;
  }
  
  public String getPassword()
  {
  	return Passwd;
  }
  
  public void setPassword(String passwd)
  {
  	Passwd = passwd;
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
  	
  	if(Fname == null)
  	{
  	  Message = "First Name not entered";
  	}
  	else if(!Validator.isAlphabet(Fname))
  	{
  	  Message = "Invalid First Name";
  	}
  	else if(Fname.length() > 20)
  	{
  	  Message = "First Name out of bounds";
  	}
  	else if(Lname == null)
  	{
  	  Message = "Last Name not entered";
  	}
  	else if(!Validator.isAlphabet(Lname))
  	{
  	  Message = "Invalid Last Name";
  	}
  	else if(Lname.length() > 20)
  	{
  	  Message = "Last Name out of bounds";
  	}
  	else if(Telephone < 1000)
  	{
  	  setMessage("Invalid Telephone No");
  	}
  	else if(Email == null)
  	{
  	  Message = "Email not entered";
  	}
  	else if(!Validator.isEmailValid(Email))
  	{
  	  Message = "Invalid Email Id";
  	}
  	else if(Email.length() > 30)
  	{
  	  Message = "Email out of bounds";
  	}
  	else if(Login == null)
  	{
  	  Message = "Login Id not entered";
  	}
  	else if(!Validator.isValidField(Login))
  	{
  	  Message = "Invalid Login Id";
  	}
  	else if(Login.length() > 20)
  	{
  	  Message = "Login Id out of bounds";
  	}
  	else if(Passwd == null)
  	{
  	  Message = "Password not entered";
  	}
  	else if(!Validator.isValidField(Passwd))
  	{
  	  Message = "Invalid Password";
  	}
  	else if(Passwd.length() > 15)
  	{
  	  Message = "Password out of bounds";
  	}
  	else
  	{
  	  flag = true;
  	  Message = "Process Completed";
  	}
  	
  	return flag;
  }
  
  protected void setPerson(HttpServletRequest req)
  {
  	Fname = req.getParameter("Fname");
  	Fname = Fname.trim();
  	System.out.println("Fanme = " + Fname);
  	Lname = req.getParameter("Lname");
  	Lname = Lname.trim();
  	Email = req.getParameter("Email");
  	Email = Email.trim();
  	Login = req.getParameter("Login");
  	Login = Login.trim();
  	Passwd = req.getParameter("Passwd");
  	
	try
	{
      Telephone = Long.parseLong(req.getParameter("Telephone"));
	}
	catch(NumberFormatException nex)
	{
	  setMessage("Illegal NumberFormat");
    }
  }

  protected void setPerson(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Fname",rs))
  	  setFname(rs.getString("Fname"));
  	
  	if(Database.isColumn("Lname",rs))
   	  setLname(rs.getString("Lname"));
   	  
  	if(Database.isColumn("Telephone",rs))
  	  Telephone = rs.getLong("Telephone");
  	
  	if(Database.isColumn("Email",rs))
  	  setEmail(rs.getString("Email"));
  	
  	if(Database.isColumn("Login",rs))
  	  setLogin(rs.getString("Login"));
  	
  	if(Database.isColumn("Password",rs))
  	  setPassword(rs.getString("Password"));
  }
}