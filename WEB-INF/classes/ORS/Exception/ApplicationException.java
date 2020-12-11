
package ORS.Exception;

public class ApplicationException extends ORSException 
{  
  public ApplicationException(String msg,String tlte,String err,String sol)
  {
    this(msg,tlte,sol);
    setError(err);
  }

  public ApplicationException(String msg,String tlte,String sol)
  {
    super(msg);
    setTitle(tlte);
    setSolution(sol);
  }

  public String toString()
  {
  	return "ApplicationException: " + getMessage() + 
  	       " , Error : " + getError() +
  	       " , Message : " + getSolution() +
  	       " , Page : " + getTitle();
  }
}