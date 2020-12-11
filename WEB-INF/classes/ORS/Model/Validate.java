package ORS.Model;

public interface Validate {
 
  /**
   * getMessage. The Application property returns the Message in case of Exception or invalid Input.
   *
   * @return    return value.  A String
   */
  public String getMessage ();

  /**
   * isValid. The Application property returns where the state of the Object is valid or invalid.
   *
   * @return    return value.  A boolean
   */
  public boolean isValid ();
}
