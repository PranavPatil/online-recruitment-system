
package ORS.Controller;

// Import Required Packages
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.util.HashMap;
import java.lang.reflect.Method;
import ORS.Controller.*;
import ORS.ConnPool.ConnectionPool;
import ORS.Utils.Log;

/**
 * This Class handles all the Request Processing for this entire application. For
 * any request, controller Servlet (ControllerServlet.java) takes the help of this
 * class to handle and process the request.
 *
 * In the MVC Architecture, the Controller Component takes the responsibility of
 * controlling the entire execution flow. The View Layer, assumes the responsibility
 * of the representing the data whereas the Model Layer is responsible for implementing
 * the Business Logic.
 *
 * In a typical web application, there exists various events in the form of
 * button clicks, combo box selections etc.
 *
 * In response to these events, application executes the functionality embedded in
 * some methods in some classes. This Class helps to facilitate the execution of methods
 * in Model Layer using Java Reflection.
 *
 * @version 1.0 
 * @since   1.0
 */
public class RequestProcessor {

  // Declare Instance Variables
  private ServletContext ctx;
  private ConnectionPool pool;
  private EntryList list;
  private Log log;

  // Data Structure to store Objects of URLMapping Class
  // URLMapping Maps an Event Name with the Business Method to be invoked and
  // the View to be used to display the data. See URLMapping.java for more info.
  private HashMap urlMap;

  // This DataStructure Stores Instances of Classes which implement various 
  // business methods. Whenever there is a new Event which need to be processed,
  // this class first checks whether the Class corresponding to this event is already
  // present here.
  private HashMap objectMap;

  /**
   * Empty Constructor of this Class.
   * @since 1.0
   */
  public RequestProcessor(ConnectionPool p,EntryList lt,Log lg) { pool = p; list = lt; log = lg; }

  /**
   * Method to initialize the values of Instance Variables
   *
   * @param ctx  Servlet Context passed to this method.
   * @since 1.0
   */
  public void init(javax.servlet.ServletContext ctx,String Mappings) {

    this.ctx = ctx;

    // Get All the URLMapping Objects initialized in the init() method 
    // of ControllerServlet 

    urlMap = (HashMap) ctx.getAttribute(Mappings);

    // Initialize Object Map to hold business classes
    objectMap = new HashMap();
  }

  /**
   * Method to process a particular request. A particular request is characterized
   * by the distinct EVENT_NAME. Each EVENT_NAME is associated with a particular
   * business method in a particular business class. This relationship is captured in
   * an XML file and uploaded as URLMapping Objects during initialization of the
   * controller servlet.
   *
   * Note that this method makes use of Reflection APIs to invoke a particular
   * method in a particular class.
   *
   * @param request Incoming HTTP Request. From the Request we can find out the Event
   *                occured.
   * @exception Throwable
   * @since 1.0
   */
  public void processRequest(HttpServletRequest request,String reqParam) throws Throwable 
  {
    // Every Business Method takes HttpServletRequest as a parameter
    // We construct args and params for executing the method using Reflection API
    Class[]  args   = { HttpServletRequest.class };
    Object[] params = { request };

    Class[] ArgsClass = new Class[]{ConnectionPool.class,EntryList.class,Log.class};
	Object[] antArgs = {pool,list,log};


    //Get the Event Name
    String eventName = (String) request.getAttribute(reqParam);
    if (eventName == null) {
      eventName = request.getParameter(reqParam);
    }

    try
    {
      if ( (eventName != null)&& (!eventName.equals(""))) 
      {
        // Get the URL Map for the Event Occured
        URLMapping u1 = (URLMapping) urlMap.get(eventName);
      
        if (u1 == null) 
        {
           u1 = (URLMapping) urlMap.get("FIRSTPAGE");
        }

        if (u1.getClassName() != null) 
        {
          //System.out.println("method = " + u1.getMethodName());
          // When there is a valid Class Name and valid Method Name present in the
          // URL Map, then invoke the method using Java Reflection
          if (!(u1.getClassName().equals(""))
                &&!(u1.getMethodName().equals(""))) 
          {
            // Try to get the required Class from the Object Map
            Object obj = objectMap.get(u1.getClassName());
            //System.out.println("obj = " + u1.getClassName());
            //System.out.println("obj = " + Class.forName(u1.getClassName()));

            // When the Class does not exist in the Object Map, instantiate a 
            // new instance and put the Class in the Object Map
            if (obj == null) 
            {
              obj = Class.forName(u1.getClassName()).getConstructor(ArgsClass).newInstance(antArgs);
              objectMap.put(u1.getClassName(), obj);
            }

            // Get the required Method to be invoked
            Method m1 = obj.getClass().getMethod(u1.getMethodName(), args);

            // Invoke the method and get the Result as an object
            Object obj1 = m1.invoke(obj, params);
            //System.out.println("object = " + obj1);

            // Put the result object in the Session so that result can be 
            // accessed in the pages which show the result.
            request.getSession().setAttribute(eventName + ".RESPONSE", obj1);
          }
        }
      }
    } catch (java.lang.reflect.InvocationTargetException ex) {  // Trap Errors when
      // Execution of Methods raised some exceptions. We need the similar exceptions
      Throwable e = ex.getTargetException();
      throw e;
    }
  }
}