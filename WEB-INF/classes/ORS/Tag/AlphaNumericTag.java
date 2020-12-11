package ORS.Tag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class AlphaNumericTag implements Tag
{
  private PageContext pageContext;
  private Tag parentTag;
  private int number = 0;
  
  public void setPageContext(PageContext pageContext)
  {
  	this.pageContext = pageContext;
  }
  
  public void setParent(Tag parentTag)
  {
  	this.parentTag = parentTag;
  }

  public void setNumber(int number)
  {
  	this.number = number;
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
  	
      if(number == 1)
        out.println("Once. ");
      else if(number == 2)
        out.println("Twice. ");
      else if(number == 3)
        out.println("Thrice. ");
      else if(number > 3)
        out.println(number + ". ");
      else  
        out.println("Not Available. ");  	
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