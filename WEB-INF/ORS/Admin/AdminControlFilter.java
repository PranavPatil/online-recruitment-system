
package ORS.Admin;

// Java Utility classes
import java.util.HashMap;
import java.io.IOException;
import ORS.Controller.*;

// Servlet related classes
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

// Servlet Filter classes
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import ORS.Controller.Accessibility;
import ORS.Exception.ApplicationException;

/**
 * This class implements the Filter interface. Each request is passed through
 * filter class when the URL corresponds to the URL configured in web.xml.
 * If any invalid access is attempted, the request attribute is set so that
 * the controllerservlet redirects the user to login page. Thus this filter 
 * acts as a single point of control, restricting invalid access in the 
 * application. @see http://java.sun.com/products/servlet/Filters.html
 *
 * @version 1.0
 * @since 1.0
 */
public final class AdminControlFilter implements Filter 
{
  /** Filter configuration */
  private FilterConfig filterConfig = null;

  /** Hashmap to hold URLMappings */
  private HashMap urlMap = null;

  /**
   * This method is called by the server before the filter goes into service,
   * and here it initializes the filter config and the URL Mappings
   *
   * @param config Filter configuration
   * @exception ServletException
   * @since 1.0
   */
  public void init(FilterConfig config)
    throws ServletException {

    this.filterConfig = config;

    String MapFile = config.getServletContext().getRealPath(config.getInitParameter("AdminMap"))
                           .toString();
    urlMap = XMLParser.parseControlFile(MapFile);
    config.getServletContext().setAttribute(FBSKeys.ADMINMAPPINGS, urlMap);

    System.out.println("Admin filter running");
  }

  /**
   * This method performs the actual filtering work .In its doFilter() method,
   * each filter receives the current request and response, as well as a
   * FilterChain containing the filters that still must be processed. Here
   * the doFilter() method examines the request URI, if the URI is protected, and
   * the session does not have the required privileges, redirects to Login page.
   *
   * @param request Servlet request object
   * @param response Servlet response object
   * @param chain Filer chain
   * @exception IOException
   * @exception ServletException
   * @since 1.0
   */
  public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)
  throws IOException, ServletException
  {
    long code = 0;
    System.out.println("filtering.............");
    String eventName = request.getParameter("EVENTNAME");
    
    try
    {
      if(eventName != null && !eventName.equals("ADLOGIN_OK"))
      {
        HttpSession session   = ((HttpServletRequest) request).getSession(false);

        if (session != null && eventName != null && urlMap != null ) 
        {
          /*String role = (String) session.getAttribute("ROLE");
          if (role == null)    role = "DEFAULT";*/

          URLMapping event = (URLMapping) urlMap.get(eventName);
    
          if(session.getAttribute("Access_Code") != null)
           code = (Long) session.getAttribute("Access_Code");

          Accessibility access = new Accessibility(code);
          System.out.println("code = " + access.toString());
        
          if ((event != null) && (code != 0))
          {
            // New session so not logged in yet.Redirect to login page
            if (session.isNew())
            {
              System.out.println("Session new");
              throw new ApplicationException("Invalid Request !!",
   	                                         "AdminFilter",
                                             "Request denied by the System !!");
            }
            // If invalid access, redirect to login page
            else if(!access.isAccessible(event.getEventName()))
            {
              throw new ApplicationException("Access Denied !!",
   	                                         "AdminFilter",
                                             "Unable to Access the Requested Page !!");
            }
          }
        }
        else 
        {
          if(session == null)
          throw new ApplicationException("Invalid Request !!",
                                         "AdminFilter",
                                         "Request denied by the System !!");
          else
          throw new ApplicationException("Invalid Event !!",
                                         "AdminFilter",
                                         "Please use the valid input buttons !!");
        }
      }
    }
    catch(Throwable ex)
  	{
      request.setAttribute("Exception",ex);
  	}

    // The privileges are sufficient to invoke this URL, continue normal
    // processing of the request
    chain.doFilter(request, response);
  }

  /**
   * This method is called after the filter has been taken
   * out of service.
   *
   * @since 1.0
   */
  public void destroy() 
  {
    this.filterConfig = null;
    this.urlMap = null;
  }
}