
package ORS.Exception;

public class DataEntryException extends ApplicationException 
{

  public DataEntryException(String msg,String tlte,String err,String sol)
  {
    super(msg,tlte,err,sol);
  }

  public DataEntryException(String msg,String tlte,String sol)
  {
    super(msg,tlte,sol);
  }
}