package ORS.Tag;

import java.sql.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import ORS.ConnPool.ConnectionPool;
import ORS.ConnPool.Database;

public class ComboFillerTag implements Tag
{
  private PageContext pageContext;
  private Tag parentTag;
  private ConnectionPool connPool;
  private String query;
  private int selected = -1;
  
  public void setPageContext(PageContext pageContext)
  {
  	this.pageContext = pageContext;
  }
  
  public void setParent(Tag parentTag)
  {
  	this.parentTag = parentTag;
  }

  public void setConnectionPool(ConnectionPool connPool)
  {
  	this.connPool = connPool;
  }

  public void setQuery(String query)
  {
  	this.query = query;
  }

  public void setSelected(int selected)
  {
  	this.selected = selected;
  }

  public Tag getParent()
  {
  	return this.parentTag;
  }
  
  public int doStartTag() throws JspException
  {
    try 
    {
  	  JspWriter out = pageContext.getOut();
  	
      Database db = new Database(connPool);
      ResultSet rs = db.RetriveDb(query);
      query = query.toLowerCase();
      String id = null, name = null;
      
      if(query.contains("from admin"))
      {
      	id = "Admin_Id";
      	name = "Login";
      }
      else if(query.contains("from accessibility"))
      {
      	id = "Access_Id";
      	name = "Designation";
      }
      else if(query.contains("from post"))
      {
      	id = "Post_Id";
      	name = "PostName";
      }
      else if(query.contains("from test"))
      {
      	id = "Test_Id";
      	name = "Name";
      }
      else if(query.contains("from question"))
      {
      	id = "Ques_Id";
      	name = "Question";
      }
      else if(query.contains("from category"))
      {
      	id = "Category_Id";
      	name = "Name";
      }
      else if(query.contains("from users"))
      {
      	id = "User_Id";
      	name = "Login";
      }

      if(id != null)
      {
        if(selected == -1)
        {
          while(rs.next())
          {
              out.println("<OPTION value = \"" + rs.getString(id) + "\">");
              out.println(rs.getString(name));
          }
        }
        else
        {
          while(rs.next())
          {
       	    int idvalue = rs.getInt(id);
       	    
            if(selected == idvalue)
            {
              out.println("<OPTION value = \"" + idvalue + "\" selected>");
              out.println(rs.getString(name));
            }
            else
            {
              out.println("<OPTION value = \"" + idvalue + "\">");
              out.println(rs.getString(name));
       	    }
          }
        }
        
        out.println("</OPTION>");
      }
    }
    catch(Exception e)
    {
    	throw new JspException("Error in AlphaNumericTag.");
    }

  	return SKIP_BODY;
  }
  
  public int doEndTag()
  {
  	return EVAL_PAGE;
  }
  
  public void release()
  {
  	
  }
}