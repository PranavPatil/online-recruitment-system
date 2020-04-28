
package ORS.Controller;

// Import Required Packages
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This Class finds the appropriate View (JSP Page) for this request. For
 * any request, Controller Servlet (ControllerServlet.java) takes the help of this
 * class to get the next view.
 *
 * This Class also helps in getting a suitable Error Page to show the Error Message.
 *
 * In the MVC Architecture, the Controller Component takes the responsibility of
 * controlling the entire execution flow. The View Layer, assumes the responsibility
 * of the representing the data whereas the Model Layer is responsible for implementing
 * the Business Logic.
 *
 * @version 1.0
 * @since 1.0
 *
 */
public class ViewManager {

  // Declare Instance Variables
  private ServletContext ctx;

  // Data Structure to store Objects of URLMapping Class
  // URLMapping Maps an Event Name with the Business Method to be invoked and
  // the View to be used to display the data. See URLMapping.java for more info.  
  private HashMap urlMap;

  // Data Structure to Store Exception Information. The exception information
  // is stored in class ExceptionMapping.java
  //private HashMap exceptionMap;

  /**
   * Empty Constructor of this Class.
   * @since 1.0
   */
  public ViewManager()  { }

  /**
   * Method to initialize the values of Instance Variables
   *
   * @param ctx  Servlet Context passed to this method.
   * @since 1.0
   */
  public void init(javax.servlet.ServletContext ctx,String Mappings) {

    this.ctx     = ctx;

    urlMap = (HashMap) ctx.getAttribute(Mappings);
    //exceptionMap = (HashMap) ctx.getAttribute(FBSKeys.EXCEPTIONMAPPINGS);
  }

  /**
   * Method to get the View (JSP Page) which acts as a Next Page for a particular
   * request.
   *
   * @param  request HttpServletRequest Object
   * @param  reqParam String representing which attribute to fetch
   * @return View Name (JSP Page Name)
   * @since 1.0
   *
   */
  public String nextScreen(HttpServletRequest request, String reqParam) {

    // Get the Event Name

    String eventName = (String) request.getAttribute(reqParam);
    if (eventName == null) 
    {
      eventName = request.getParameter(reqParam);
    }

    // If there is no event, then show treat this as FIRSTPAGE event
    if ((eventName == null) || (eventName.equals(""))) 
    {
      eventName = "FIRSTPAGE";
    }

    // Get the URL Mapping for the Event 
    URLMapping u1 = (URLMapping) urlMap.get(eventName);
    if (u1 == null) 
    {
      u1 = (URLMapping) urlMap.get("FIRSTPAGE");
    }

    // Return the Screen Name
    return "/" + u1.getNextScreen();
  }

  /**
   * Method to get the proper View (JSP Page) which displays the Error Message for
   * this kind of exception.
   *
   * @param Throwable Exception raised by the application
   * @param  request HttpServletRequest Object
   * @return Exception View Name (JSP Page Name)
   *
   * @since 1.0
   */
  /*public String nextErrorScreen(Throwable ex, HttpServletRequest request) {

    // Loop through all the exceptions in the Exception Map
    Iterator it = exceptionMap.keySet().iterator();
    while (it.hasNext()) {

      // Get the Exception Name from the Exception Map
      String exceptionName        = (String) it.next();
      Class  targetExceptionClass = null;
      try {
        targetExceptionClass =
          this.getClass().getClassLoader().loadClass(exceptionName);
      } catch (ClassNotFoundException cnfe) {  // Trap Errors When Class is Not Found 
        System.out.println("ScreenFlowManager: Could not load exception "
                           + exceptionName);
      }

      // check if the raised exception is a sub class of the exception in exception
      // mapping
      if ((targetExceptionClass != null)
              && targetExceptionClass.isAssignableFrom(ex.getClass())) {
        ExceptionMapping excp =
          (ExceptionMapping) exceptionMap.get(exceptionName);

        // Check whether the request is from mobile browser.
        boolean mobileBrowser =
          (request.getHeader("User-Agent").indexOf("Mozilla") == -1)
          ? true
          : false;
        if (mobileBrowser) {
          return "/" + excp.getNextMobileScreen();
        }
        else {
          return "/" + excp.getNextWebScreen();
        }
      }
    }
  
    // When the error is not a generic error (mentioned in Exception.xml) but
    // application specific error. Then show the error in the same page
    return nextScreen(request,"CALLOUT_EVENT");
  }*/
}