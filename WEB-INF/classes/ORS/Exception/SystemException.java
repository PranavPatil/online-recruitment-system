
package ORS.Exception;

public class SystemException extends ORSException 
{
  public SystemException(Exception ex)
  {
    super(ex);
    setSolution("Press 'OK' to Terminate.");
  }

  public SystemException(Exception ex,String tlte)
  {
    super(ex);
    setTitle(tlte);
    setSolution("Press 'OK' to Terminate.");
  }

  public SystemException(Exception ex,String tlte,String err)
  {
    super(ex,err);
    setTitle(tlte);
    setSolution("Press 'OK' to Terminate.");
  }

  public String toString()
  {
  	return "SystemException: " + getMessage() + 
  	       " , Error : " + getError() +
  	       " , Message : " + getSolution() +
  	       " , Page : " + getTitle();
  }
}