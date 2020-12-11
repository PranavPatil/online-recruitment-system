package ORS.Admin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.lang.reflect.Method;
import ORS.Controller.*;
import ORS.Model.Admin;
import ORS.Utils.Log;
import ORS.ConnPool.ConnectionPool;
import ORS.Exception.ApplicationException;

public class AdminController extends HttpServlet 
{
  private HashMap urlMap;
  private HashMap objectMap;
  private EntryList list;
  private Log log;
  private ConnectionPool pool;
  private AdminMgmt mgmt;
  private String Screen = "";
  
  public void init(ServletConfig config) throws ServletException 
  {
    super.init(config);

    try
    {
      urlMap = (HashMap) config.getServletContext().getAttribute(FBSKeys.ADMINMAPPINGS);

      pool=(ConnectionPool)getServletContext().getAttribute("ConPool");
      list = new EntryList();

      String path = config.getServletContext().
                           getRealPath(getServletContext().getInitParameter("AdminLog"));
      log = new Log(path);

      mgmt = new AdminMgmt(pool,list,log);
      objectMap = new HashMap();
    }
    /*catch (MalformedURLException mx) 
    {
      System.out.println("Error initializing mappings " + mx);
      mx.printStackTrace();
    }*/
    catch (Exception ex) 
    {
      System.out.println("Error : " + ex.toString());
      ex.printStackTrace();
    }

    //getRequestProcessor();
    //getViewManager();
  }

  public void process(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException 
  {
    String nextScreen = "";
    String event = null;
    HttpSession session = request.getSession(false);
    //System.out.println("list = " + list.toString());
    //System.out.println("Screen = " + Screen);
    
    //System.out.println("rweq~~~~~~~~~~~~~~~~~~~~~~~~ " + request.getHeader("accept"));
    
    /*java.util.Enumeration names = request.getHeaderNames();
    while (names.hasMoreElements())
    {
      String name = (String) names.nextElement(); 
      String value = request.getHeader(name).toString();
      System.out.println(name + " = " + value);
    }*/
    
    if(request.getParameter("EVENTNAME") != null)
      event = request.getParameter("EVENTNAME");
	System.out.println("Event = " + event);

  	try
  	{
 	  if(request.getAttribute("Exception") != null)
 	  {
   	    Exception ex = (Exception) request.getAttribute("Exception");
   	    request.removeAttribute("Exception");
   	    throw ex;
 	  }
 	  //else if(request.getHeader("accept").equals("*/*"))
	  /*{System.out.println("000000000000000000000000000000000000000000000000000000");
   	    throw new ApplicationException("Invalid Request !!",
   	                                   "AdminController",
                                       "Request denied by the System !!");
	  }*/
	  else if(event == null || (event != null && !event.equals("ADLOGIN_OK")))
	  {
	    if(session != null && session.getAttribute("Admin_Id") != null)
	    {
	      int Admin_Id = (Integer)session.getAttribute("Admin_Id");

	  	  if(list.contains(Admin_Id,session.getId()))
	  	  {
	  	  	list.set(Admin_Id,Calendar.getInstance().getTime());
	  	    RequestProcessor rp = getRequestProcessor();
            rp.processRequest(request,"EVENTNAME");
            nextScreen = getViewManager().nextScreen(request,"EVENTNAME");

            if(!nextScreen.equals(Screen))
            {
              if(!event.equals("ADLOGOUT_MN"))
                session.setAttribute("ADMINMAIN",event);
              Screen = nextScreen;
            }

            setHeader(response);
            request.getSession(true).removeAttribute("CALLOUT_EVENT");
	  	  }
	  	  else
	  	  {
  	        session.invalidate();
  	        
  	        throw new ApplicationException("Invalid Admin, Access denied !!",
  	                                       "AdminController",
                                           "Please check your LoginName !!");
	  	  }
	    }
	    else if(event != null && event.equals("ADLOGOUT_MN"))
	    {
	      nextScreen = getViewManager().nextScreen(request,"EVENTNAME");
	    }
	    else
	    {
  	      throw new ApplicationException("Invalid Request, Access denied !!",
  	                                     "AdminController",
                                         "Please check your LoginName !!");
	    }
	  }
	  else if(event != null && event.equals("ADLOGIN_OK"))
	  {
	    String Log_Id = request.getParameter("Admin_Name");
        int Admin_Id = Admin.getAdminId(pool,Log_Id);

	    if(Log_Id != null)
	    {
          System.out.println("LOGIN_________________________________");

          if(session != null)
          {
            if(list.contains(Admin_Id,session.getId()))
              list.removeEntry(Admin_Id);

            session.invalidate();
          }

          // log the user
          if(mgmt.AdminLogin(request))
          {
          	session = request.getSession(false);
            // show the next page
            nextScreen = getViewManager().nextScreen(request,"EVENTNAME");
            session.setAttribute("ADMINMAIN","FIRSTPAGE");

            setHeader(response);
            request.getSession(true).removeAttribute("CALLOUT_EVENT");
          }
        }
	    else
	    {
  	     throw new ApplicationException("Invalid Admin, Login denied !!",
  	                                    "AdminController",
                                        "Please check your LoginName !!");
	    }
      }
      else
      {
 	    throw new ApplicationException("Invalid Request !!",
  	                                   "AdminController",
                                       "Invalid Request Page !!");
      }
  	}
  	catch(Throwable ex)
  	{
  	  String className = ex.getClass().getName();
  	  System.out.println("Excep Throwable = " + className);
  	  ex.printStackTrace();
      
      if (request.getSession(true).getAttribute("CALLOUT_EVENT") == null) 
      {
        request.getSession(true).setAttribute("CALLOUT_EVENT",request.getParameter("CALLOUT_EVENT"));
      }
      
      request.setAttribute("Exception",ex);
      //request.setAttribute("javax.servlet.jsp.jspException", ex);
      //request.setAttribute("ErrorMessage",ex.toString());
      
      if(ex.getClass().getName().equals("ORS.Exception.DataEntryException"))
        nextScreen = Screen;
      else
        nextScreen = "/Web/ErrorPage.jsp";
      
      /*if (nextScreen == null) 
      {
        ex.printStackTrace();
        throw new ServletException("MainServlet: unknown exception: " + className);
      }*/
  	}

    System.out.println("next = " + nextScreen);

    if(event != null && event.equals("ADLOGOUT_MN"))
      response.sendRedirect("/ORS" + nextScreen);
    else
      getServletConfig().getServletContext().getRequestDispatcher(nextScreen)
      .forward(request, response);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException 
  {
    process(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException 
  {
    doGet(request,response);
  }

  private RequestProcessor getRequestProcessor() 
  {
    RequestProcessor rp =
      (RequestProcessor) getServletContext()
        .getAttribute(FBSKeys.ADMINPROCESSOR);

    if (rp == null) 
    {
      rp = new RequestProcessor(pool,list,log);
      rp.init(getServletContext(),FBSKeys.ADMINMAPPINGS);

      getServletContext().setAttribute(FBSKeys.ADMINPROCESSOR, rp);
    }

    return rp;
  }

  private ViewManager getViewManager() 
  {
    ViewManager vm =
      (ViewManager) getServletContext().getAttribute(FBSKeys.ADMINVIEW);

    if (vm == null) 
    {
      vm = new ViewManager();
      vm.init(getServletContext(),FBSKeys.ADMINMAPPINGS);

      getServletContext().setAttribute(FBSKeys.ADMINVIEW, vm);
    }

    return vm;
  }

  public void setHeader(HttpServletResponse response)
          throws ServletException, IOException 
  {
    //response.setHeader("Cache-Control","no-store"); //HTTP 1.1
    response.setHeader("Cache-Control", "no-cache");  // HTTP 1.1
    response.setHeader("Pragma", "no-cache");         // HTTP 1.0
    response.setDateHeader ("Expires", 0);  // Prevents caching at the proxy server
    //response.setDateHeader("Expires", -1);  // Prevents caching at the proxy server
  }
}