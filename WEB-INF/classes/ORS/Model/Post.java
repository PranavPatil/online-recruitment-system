package ORS.Model;

import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import ORS.ConnPool.*;
import ORS.Utils.Validator;

public class Post implements Validate
{ 
  private int Post_Id;
  private String Name;
  private String Description;
  private String Qualification;
  private String Branch;
  private int Aggregate = 0;
  private int Experience=0;
  private int Agelimit = -1;
  private int Vacancy=0;
  private int Cat_Id[];
  private int Test_Id[];
  private String Message = "";  

  public Post()
  {
  	super();
  }
  
  public Post(int Post_Id,ConnectionPool pool) throws Exception
  {
  	super();
  	setPost(Post_Id,pool);
  }
  
  public Post(HttpServletRequest req)
  {
	super();
	setPost(req);
  }
  	
  public long getPostId()
  {
  	return Post_Id;
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
  
  public int getAggregate()
  {
  	return Aggregate;
  }
  
  public void setAggregate(int aggregate)
  {
  	Aggregate = aggregate;
  }
  
  public int getExperience()
  {
  	return Experience;
  }
  
  public void setExperience(int expnce)
  {
  	Experience = expnce;
  }
  
  public int getAgelimit()
  {
  	return Agelimit;
  }
  
  public void setAgelimit(int age)
  {
  	Agelimit = age;
  }

  public int getVacancy()
  {
  	return Vacancy;
  }

  public void setVacancy(int vacancy)
  {
  	Vacancy = vacancy;
  }

  public int[] getCategoryId()
  {
    return Cat_Id;
  }

  public int getCategoryId(int i)
  {
  	if(Cat_Id != null)
  	{
  	  if(i > 0 && i < Cat_Id.length)
  	    return Cat_Id [i];
  	  else
  	    return -1;
  	}
  	else
  	  return -1;
  }

  public boolean setCategoryId(int cid,int i)
  {
  	if(Cat_Id == null)
  	  return false;
  	
  	if(i > 0 && i < Cat_Id.length)
  	{
  	  Cat_Id [i] = cid;
  	  return true;
  	}
  	else
  	  return false;
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
  	  Message = "Post Name not entered";
  	}
  	else if(!Validator.isNameField(Name))
  	{
  	  Message = "Invalid Post Name";
  	}
  	else if(Name.length() > 20)
  	{
  	  Message = "Post Name out of bounds";
  	}
  	else if(Description == null)
  	{
  	  Message = "Description not entered";
  	}
  	else if(Description.length() > 500)
  	{
  	  Message = "Description out of bounds";
  	}
  	else if(Qualification == null)
  	{
  	  Message = "Qualification not entered";
  	}
  	else if(Branch == null)
  	{
  	  Message = "Branch not entered";
  	}
  	else if(Aggregate < 0 || Aggregate > 100)
  	{
  	  Message = "Invalid Telephone No";
  	}
  	else if(Experience < 1 || Experience > 5)
  	{
  	  Message = "Invalid Experience";
  	}
  	else if(Agelimit != -999 && (Agelimit < 18 || Agelimit > 59))
  	{
  	  Message = "Invalid Agelimit";
  	}
	else if(Vacancy < 0 || Vacancy > 100000)
	{
	  Message = "Invalid Vacancy";
    }
    else if(Cat_Id == null)
    {
      Message = "Category Selection entry voilated";
    }
    else if(Cat_Id != null && Cat_Id.length == 0)
    {
      Message = "No Categories Selected";
    }
  	else
  	{
  	  flag = true;
  	  Message = "Process Completed";
  	}
  	
  	return flag;
  }
  
  public void setPost(int Post_Id,ConnectionPool pool) throws Exception
  {
  	ResultSet rs = null;
  	
    Database db = new Database(pool);
  	rs = db.RetriveDb("Select Post_Id,PostName,Description,Aggregate,Qualification,Branch," +
  	                  "Experience,Agelimit,Vacancy,N.Category_Id from Post,TABLE(Post.CatEntry)N where Post_Id = " + 
  	                   Post_Id);

  	if(rs.next())
  	{
      this.Post_Id = Post_Id;

  	  Name = rs.getString("Postname");
  	  Description = rs.getString("Description");
  	  Aggregate = rs.getInt("Aggregate");
  	  Qualification = rs.getString("Qualification");
  	  Branch = rs.getString("Branch");
  	  Experience = rs.getInt("Experience");
      Agelimit = rs.getInt("Agelimit");
      Vacancy = rs.getInt("Vacancy");
      
      ArrayList Category = new ArrayList();

      do
      {
        Category.add(rs.getInt(10));
      }while(rs.next());

      Cat_Id = new int[Category.size()];
      int i = 0;
      
      while(i < Category.size())
      {
        Cat_Id[i] = (Integer) Category.get(i);
        i++;
      }
  	}  	
  }
  
  public void setPost(int Post_Id,ConnectionPool pool,String Query) throws Exception
  {
  	ResultSet rs = null;
  	
  	if(Query.toLowerCase().contains(" where "))
      Query = Query + " and";
  	else
  	  Query = Query + " where";

    Database db = new Database(pool);
  	rs = db.RetriveDb(Query + " Post_Id = " + Post_Id);

    //this.Post_Id = Post_Id;
    
    if(rs.next())
      setPost(rs);
  }

  public void setPost(ResultSet rs) throws SQLException
  {
  	if(Database.isColumn("Post_Id",rs))
  	  this.Post_Id = rs.getInt("Post_Id");

  	if(Database.isColumn("Postname",rs))
  	  Name = rs.getString("Postname");

  	if(Database.isColumn("Description",rs))
  	  Description = rs.getString("Description");
  	  
  	if(Database.isColumn("Aggregate",rs))
  	  Aggregate = rs.getInt("Aggregate");
  	  
  	if(Database.isColumn("Qualification",rs))
  	  Qualification = rs.getString("Qualification");
  	  
  	if(Database.isColumn("Branch",rs))
  	  Branch = rs.getString("Branch");
  	  
  	if(Database.isColumn("Experience",rs))
  	  Experience = rs.getInt("Experience");
  	  
  	if(Database.isColumn("Agelimit",rs))
      Agelimit = rs.getInt("Agelimit");

  	if(Database.isColumn("Vacancy",rs))
      Vacancy = rs.getInt("Vacancy");
  }

  public void setPost(HttpServletRequest req)
  {
    String Category[] = null;

	Name=req.getParameter("PostName");
	Name = Name.trim();
	Description=req.getParameter("PostDesc");
	Description = Description.trim();
	Qualification=req.getParameter("Qualification");
	Branch=req.getParameter("Branch");
   	Category=req.getParameterValues("Category");

	try
	{
      Aggregate=Integer.parseInt(req.getParameter("Aggregate"));
      Experience=Integer.parseInt(req.getParameter("Experience"));
      int AgeBound=Integer.parseInt(req.getParameter("AgeBound"));
      
	  if(AgeBound == 1)
	  {
	  	Agelimit=Integer.parseInt(req.getParameter("Agelimit"));
	  }
	  else
	    Agelimit = -999;  // AgeLimit set to a value to indicate that there is no Ageboundation
      
      Vacancy=Integer.parseInt(req.getParameter("Vacancy"));
	}
	catch(NumberFormatException nex)
	{
	  Message = "Illegal NumberFormat";
    }

    int length = Category.length;
    Cat_Id = new int[length];
	
    for(int j=0;j<length;j++)
    {
      try
      {
        Cat_Id[j] = Integer.parseInt(Category[j]);
      }
      catch(NumberFormatException ex)
      {
        Cat_Id = null;
        break;
      }
    }
  }

  public boolean CreatePost(ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);
          
    if(isValid() && isNameAbsent(db,0))
    {
      StringBuffer Query = new StringBuffer();
	  
	  Query.append("Insert into Post values(Post_Id.nextval,'");
	  Query.append(Name).append("','").append(Description);
	  Query.append("',CATEGORY_NT(");
	 	
	  for(int j=0;j<Cat_Id.length;j++)
	  {
	    if(j == (Cat_Id.length-1))
	    {
	      Query.append("CATEGORY_TY(").append(Cat_Id[j]).append(",0)");
	    }
	    else	 	  
	      Query.append("CATEGORY_TY(").append(Cat_Id[j]).append(",0),");
	  }

      Query.append("),").append(Aggregate).append(",'").append(Qualification);
      Query.append("','").append(Branch).append("',").append(Experience);
      Query.append(",").append(Agelimit).append(",").append(Vacancy).append(")");

	  db.ExecuteDb(Query.toString());
	  status = true;
    }

	return(status);
  }
  
  public boolean EditPost(int Post_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;

    Database db = new Database(pool);

    if(isValid() && isNameAbsent(db,Post_Id))
    {
      if(isPresent(db,Post_Id))
      {
        StringBuffer Query = new StringBuffer();

 		ResultSet rs = null;
 		rs = db.RetriveDb("Select N.Category_Id from Post,TABLE(Post.CatEntry)N where Post_Id = " + 
 		                  Post_Id);
            
        ArrayList Present = new ArrayList();
            
        while(rs.next())
        {
          Present.add(rs.getInt(1));
        }

        for(int i = 0;i < Cat_Id.length;i++)
        {
          int j = 0;
          boolean equals = false;
            
          while(equals == false && j < Present.size())
          {
            if(Cat_Id[i] == (Integer)Present.get(j))
            {
              Present.remove(j);
              equals = true;
            }
            j++;
          }
            
          if(equals == false)
          {
            db.ExecuteDb("Insert into Table(Select CatEntry from Post where Post_Id = "
                         + Post_Id + ") values(CATEGORY_TY(" + Cat_Id[i] + ",0))");
          }
        }

        for(int k = 0;k < Present.size();k++)
        {
          db.ExecuteDb("Delete Table(select CatEntry from Post where Post_Id = " +
                       Post_Id + ")N where N.Category_Id = " + (Integer)Present.get(k));
        }
             		    
 		Query.append("Update Post set PostName = '").append(Name);
		Query.append("',Description = '").append(Description);
		Query.append("',Aggregate = ").append(Aggregate);
		Query.append(",Qualification = '").append(Qualification);
		Query.append("',Branch = '").append(Branch);
		Query.append("',Experience = ").append(Experience);
        Query.append(",Agelimit = ").append(Agelimit);
		Query.append(",Vacancy = ").append(Vacancy);
		Query.append(" where Post_Id = ").append(Post_Id);

        db.ExecuteDb(Query.toString());
  	    status = true;
  	  }
  	}
  	 
  	return(status);
  }
  
  public boolean DeletePost(int Post_Id,ConnectionPool pool)
  throws Exception
  {
    boolean status = false;
    
    Database db = new Database(pool);

    if(isPresent(db,Post_Id))
    {
	  String Query = "Delete from TestQuestions where Post_Id =" + Post_Id;
      db.ExecuteDb(Query);
	  Query = "Delete from Result where Post_Id =" + Post_Id;
      db.ExecuteDb(Query);
	  Query = "Delete from Post Where Post_Id=" + Post_Id;
      db.ExecuteDb(Query);

  	  status = true;
    }
  	 
  	return(status);
  }

  private boolean isNameAbsent(Database db,int Post_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Post_Id) from Post where Postname = '" + Name + "'");

    if(rs.next())
  	{
  	  int value = rs.getInt(1);
  	  
  	  if(value == 0)
  	  {
  	    flag = true;
  	  }
  	  if(Post_Id > 0 && value == 1)
  	  {
  	    rs = null;
  	    rs = db.RetriveDb("Select Post_Id from Post where Postname = '" + Name + "'");
  	    rs.next();
  	    
  	    if(Post_Id == rs.getInt(1))
  	      flag = true;
  	    else
  	      Message = "Please insert another Post Name";
  	  }
  	  else
  	  {
  	    Message = "Please insert another Post Name";
  	  }
  	}

  	return flag;
  }

  private boolean isPresent(Database db,int Post_Id)
  throws SQLException
  {
  	boolean flag = false;
  	
  	ResultSet rs = null;
  	rs = db.RetriveDb("Select count(Post_Id) from Post where Post_Id = " + Post_Id);

    if(rs.next())
  	{
  	  if(rs.getInt(1) == 1)
  	  {
  	    flag = true;
  	  }
  	  else
  	  {
  	    Message = "Invalid Post Name";
  	  }
  	}
  	
  	return flag;
  }

 public static String getName(ConnectionPool pool,int Post_Id)
 throws Exception
 {
   String Pname = null;
  
   Database db = new Database(pool);
   ResultSet res = db.RetriveDb("Select PostName from Post where Post_Id = " + Post_Id);
  	  
   if(res.next())
    Pname = res.getString("PostName");

   return Pname;
 }
 
 public int hashcode()
 {
   return (int)Post_Id;
 } 
 
 public String toString()
 {
   return Name;	
 }
 
 public boolean equals(Object obj)
 {
   if (obj == this)
     return true;

   if (!(obj instanceof Post))
     return false;

   Post pst = (Post) obj;

   return pst.Post_Id == this.Post_Id  &&
          pst.Name.equals(this.Name);
 }
}