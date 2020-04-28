import java.io.IOException;

class FileFormatException extends IOException
{
  public FileFormatException()  { }
  public FileFormatException(String exception)
  {
  	super(exception);
  }
}