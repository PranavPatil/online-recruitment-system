package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import ORS.ConnPool.*;
import ORS.Utils.Validator;

public class Category implements Validate
{ 
  private int Cat_Id;
  private String Name;
  private String Description;
  private String Message = "";

  public Category()
  {
  	super();
  }
  
  public Category(int Cat_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setCategory(Cat_Id,pool);
  }
  
  public Category(HttpServletRequest req)
  {
	super();
	setCategory(req);
  }
  	
  public long getCategoryId()
  {
  	return Cat_Id;
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

  public String getMessage()
  {
  	return Message;
  }

  public boolean isValid()
  {
  	boolean flag = false;
  	
    if(Name == null)
  	{
  	  Message = "Category Name not entered";
  	}
  	else if(!Validator.isNameField(Name))
  	{
  	  Message = "Invalid Category Name";
  	}
  	else if(Name.length() > 20)
  	{
  	  Message = "Category Name out of bounds";
  	}
  	else if(Description == null)
  	{
  	  Message = "Description not entered";
  	}
  	else if(Description.length() > 500)
  	{
  	  Message = "Description out of bounds";
  	}
  	else
  	{
  	  flag = true;
  	  Message = "Process Completed";
  	}
  	
  	return flag;
  }
  
  public void setCategory(int Cat_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;
  	
    Database db = new Database(pool);
  	rs = db.RetriveDb("Select * from Category where Category_Id = " + Cat_Id);

  	if(rs.next())
  	{
      this.Cat_Id = Cat_Id;

  	  Name = rs.getString("Name");
  	  Description = rs.getString("Description");
  	}  	
  }
  
  public void setCategory(long Cat_Id,ConnectionPool pool,String Query) throws Exception
  {
  	ResultSet rs = null;
  	
  	if(Query.toLowerCase().contains(" where "))
      Query = Query + " and";
  	else
  	  Query = Query + " where";

    Database db = new Database(pool);
  	rs = db.RetriveDb(Query + " Category_Id = " + Cat_Id);

    //this.Cat_Id = Cat_Id;
    
    if(rs.next())
      setCategory(rs);
  }

  public void setCategory(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Category_Id",rs))
  	  this.Cat_Id = rs.getInt("Category_Id");

  	if(Database.isColumn("Name",rs))
  	  Name = rs.getString("Name");
  	 
  	if(Database.isColumn("Description",rs))
  	  Description = rs.getString("Description");
  }

  public void setCategory(HttpServletRequest req)
  {
	Name=req.getParameter("CatName");
	Name = Name.trim();
	Description=req.getParameter("CatDesc");
	Description = Description.trim();
  }

  public boolean CreateCategory(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid() && isNameAbsent(db,0))
    {
      String Query = "Insert into Category values(Category_Id.nextval"
	                  + ",'" + Name + "','" + Description + "')";

	  db.ExecuteDb(Query);
	  status = true;
    }

	return(status);
  }
  
  public boolean EditCategory(int Cat_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);

    if(isValid() && isNameAbsent(db,Cat_Id))
    {
      if(isPresent(db,Cat_Id))
      {
        String Query = "Update Category set NAME='"
     	                + Name + "',Description ='"
		                + Description + "' Where Category_Id=" + Cat_Id;

  	    db.ExecuteDb(Query);
  	    status = true;
  	  }
    }
  	 
  	return(status);
  }
  
  public boolean DeleteCategory(int Cat_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);

    if(isPresent(db,Cat_Id))
    {
      String Query = "Select Post_Id from Post";
  	  ResultSet rs = db.RetriveDb(Query);

      while(rs.next())
      {
        int Post_Id = 0;
        Post_Id = rs.getInt(1);
        System.out.println("Post = " + Post_Id);
            
        Query = "Delete Table(select CatEntry from Post where Post_Id = "
                       + Post_Id + ")N where N.category_Id =" + Cat_Id;
          
        Database db1 = new Database(pool);
        db1.ExecuteDb(Query);
      }
			 
	  Query = "Delete from Category Where Category_Id=" + Cat_Id;
      db.ExecuteDb(Query);
  	  status = true;
    }
  	 
  	return(status);
  }

  private boolean isNameAbsent(Database db,int Cat_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Category_Id) from Category where Name = '" + Name + "'");

    if(rs.next())
  	{
  	  int value = rs.getInt(1);
  	    
  	  if(value == 0)
  	  {
  	    flag = true;
  	  }
  	  if(Cat_Id > 0 && value == 1)
  	  {
  	    rs = null;
  	    rs = db.RetriveDb("Select Category_Id from Category where Name = '" + Name + "'");
  	    rs.next();
  	    
  	    if(Cat_Id == rs.getInt(1))
  	      flag = true;
  	    else
   	      Message = "Please insert another Category Name";
  	  }
  	  else
  	  {
  	    Message = "Please insert another Category Name";
  	  }
  	}

  	return flag;
  }
  
  private boolean isPresent(Database db,int Cat_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Category_Id) from Category where Category_Id = " + Cat_Id);

    if(rs.next())
  	{
  	  if(rs.getInt(1) == 1)
  	  {
  	    flag = true;
  	  }
  	  else
  	  {
  	    Message = "Invalid Category Name";
  	  }
  	}

  	return flag;
  }

 public static String getName(ConnectionPool pool,int Cat_Id)
 throws Exception
 {
   String Cname = null;
  
   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select Name from Category where Category_Id = " + Cat_Id);
  	  
   if(res.next())
    Cname = res.getString("Name");

   return Cname;
 }

 public int hashcode()
 {
   return (int)Cat_Id;
 } 
 
 public String toString()
 {
   return Name;
 }
 
 public boolean equals(Object obj)
 {
   if (obj == this)
     return true;

   if (!(obj instanceof Category))
     return false;

   Category cat = (Category) obj;

   return cat.Cat_Id == this.Cat_Id  &&
          cat.Name.equals(this.Name);
 }
}