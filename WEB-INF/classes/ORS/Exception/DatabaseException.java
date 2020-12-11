
package ORS.Exception;

public class DatabaseException extends ORSException 
{
  public DatabaseException(Exception ex)
  {
    super(ex);
    setSolution("Please consult the System Administrator !!");
  }

  public DatabaseException(Exception ex,String tlte)
  {
    super(ex);
    setTitle(tlte);
    setSolution("Please consult the System Administrator !!");
  }

  public DatabaseException(Exception ex,String tlte,String err)
  {
    super(ex,err);
    setTitle(tlte);
    setSolution("Please consult the System Administrator !!");
  }

  public DatabaseException(String msg,String tlte,String err)
  {
    super(msg);
    setTitle(tlte);
    setSolution("Please consult the System Administrator !!");
  }
  
  public String toString()
  {
  	return "DatabaseException: " + getMessage() + 
  	       " , Error : " + getError() +
  	       " , Message : " + getSolution() +
  	       " , Page : " + getTitle();
  }
}