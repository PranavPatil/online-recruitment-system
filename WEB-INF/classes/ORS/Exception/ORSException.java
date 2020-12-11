
package ORS.Exception;

import javax.servlet.jsp.JspWriter;

public class ORSException extends Exception 
{
  private String error = null;
  private String cl = null;
  private String Solution = null;
  private String Title = null;
  
  public ORSException(String msg)
  {
    super(msg);
    cl = this.getClass().toString();
  }

  public ORSException(Exception ex)
  {
    super(ex.getMessage(),ex);
    cl = ex.getClass().toString();
  }

  public ORSException(Exception ex,String err)
  {
    super(ex.getMessage(),ex);
    cl = ex.getClass().toString();
    error = err;
  }

  public ORSException(Exception ex,String tlte,String err)
  {
    super(ex.getMessage(),ex);
    cl = ex.getClass().toString();
    error = err;
    Title = tlte + " ErrorPage";
  }

  public void setError(String err)
  {
  	error = err;
  }

  public void setTitle(String tlte)
  {
    Title = tlte + " ErrorPage";
  }

  public void setSolution(String sol)
  {
    Solution = sol;
  }

  public String getTitle()
  {
  	return Title;
  }

  public String getSolution()
  {
  	return Solution;
  }
  
  public String getError()
  {
  	return error;
  }

  public String getClassName()
  {
  	return cl;
  }

  public void printStackTrace(JspWriter s)
  throws java.io.IOException
  {
    s.println(super.toString());
    StackTraceElement[] trace = this.getStackTrace();
         
    for (int i=0; i < trace.length; i++)
      s.println("\tat " + trace[i]);
  }

  public static void printStackTrace(Throwable ex,JspWriter s)
  throws java.io.IOException
  {
    s.println(ex.toString());
    StackTraceElement[] trace = ex.getStackTrace();
         
    for (int i=0; i < trace.length; i++)
      s.println("\tat " + trace[i]);
  }

  public static String getClassName(Throwable ex,int no)
  {
  	StackTraceElement[] trace = ex.getStackTrace();
  	return trace[no].getClassName();
  }

  public static String getMethodName(Throwable ex,int no)
  {
  	StackTraceElement[] trace = ex.getStackTrace();
  	return trace[no].getMethodName();
  }

  public String toString()
  {
  	return "Exception: " + super.getMessage() + 
  	       " , Error : " + error +
  	       " , Message : " + Solution +
  	       " , Page : " + Title;
  }
}