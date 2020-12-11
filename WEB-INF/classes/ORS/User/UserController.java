package ORS.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.lang.reflect.Method;
import ORS.Controller.*;
import ORS.Exception.ApplicationException;
import ORS.Model.User;
import ORS.ConnPool.ConnectionPool;
import ORS.Utils.Log;

public class UserController extends HttpServlet 
{
  private HashMap urlMap;
  private HashMap objectMap;
  private EntryList list;
  private Log log;
  private ConnectionPool pool;
  private UserMgmt mgmt;
  private String Screen = "";
  
  public void init(ServletConfig config) throws ServletException 
  {
    super.init(config);

    try
    {
      urlMap = (HashMap) config.getServletContext().getAttribute(FBSKeys.USERMAPPINGS);

      pool=(ConnectionPool)getServletContext().getAttribute("ConPool");
      list = new EntryList();
      
      String path = config.getServletContext().
                           getRealPath(getServletContext().getInitParameter("UserLog"));
      log = new Log(path);

      mgmt = new UserMgmt(pool,list,log);
      objectMap = new HashMap();
    }
    catch (Exception ex) 
    {
      System.out.println("Error : " + ex.toString());
      ex.printStackTrace();
    }
  }

  public void process(HttpServletRequest request, HttpServletResponse response)
                     throws ServletException, IOException 
  {
    String nextScreen = "";
    String event = null;
    HttpSession session = request.getSession(false);
    
    if(request.getParameter("USEREVENT") != null)
      event = request.getParameter("USEREVENT");
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
	  /*{
   	    throw new ApplicationException("Invalid Request !!",
   	                                   "UserController",
                                       "Request denied by the System !!");
	  }*/

	  else if(event == null || (event != null && !event.equals("USLOGIN")))
	  {
	    if(session != null && session.getAttribute("User_Id") != null)
	    {
	      long User_Id = (Long)session.getAttribute("User_Id");

	  	  if(list.contains(User_Id,session.getId()))
	  	  {
	  	  	list.set(User_Id,Calendar.getInstance().getTime());
	  	    RequestProcessor rp = getRequestProcessor();
            rp.processRequest(request,"USEREVENT");
            nextScreen = getViewManager().nextScreen(request,"USEREVENT");

            if(!nextScreen.equals(Screen))
            {
              //System.out.println("Event in Session ====== " + event);
              session.setAttribute("USERMAIN",event);
              Screen = nextScreen;
            }

            setHeader(response);
            request.getSession(true).removeAttribute("CALLOUT_EVENT");
	  	  }
	  	  else
	  	  {
  	        session.invalidate();
  	        throw new ApplicationException("Invalid User, Access denied !!",
  	                                       "UserController",
                                           "Please check your LoginName !!");
	  	  }
	    }
	    else if(event != null && event.equals("USLOGOUT"))
	    {
	      nextScreen = getViewManager().nextScreen(request,"USEREVENT");
	    }
	    else
	    {
  	      throw new ApplicationException("Invalid Request, Access denied !!",
  	                                     "UserController",
                                         "Please check your LoginName !!");
	    }
	  }
	  else if(event != null && event.equals("USLOGIN"))
	  {
	    String Log_Id = request.getParameter("User_Name");
        long User_Id = User.getUserId(pool,Log_Id);

	    if(Log_Id != null)
	    {
          if(session != null)
          {
            if(list.contains(User_Id,session.getId()))
              list.removeEntry(User_Id);

            session.invalidate();
          }

          // log the user
          if(mgmt.UserLogin(request))
          {
          	session = request.getSession(false);
            // show the next page
            nextScreen = getViewManager().nextScreen(request,"USEREVENT");
            session.setAttribute("USERMAIN","FIRSTPAGE");
            
            setHeader(response);
            request.getSession(true).removeAttribute("CALLOUT_EVENT");
          }
        }
	    else
	    {
  	     throw new ApplicationException("Invalid User, Login denied !!",
  	                                    "UserController",
                                        "Please check your LoginName !!");
	    }
      }
      else
      {
 	    throw new ApplicationException("Invalid Request !!",
  	                                   "UserController",
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
      
      if(ex.getClass().getName().equals("ORS.Exception.DataEntryException"))
        nextScreen = Screen;
      else
        nextScreen = "/Web/ErrorPage.jsp";
  	}

    //System.out.println("next = " + nextScreen);

    if(event != null && event.equals("USLOGOUT"))
      response.sendRedirect("/ORS/" + nextScreen);
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
        .getAttribute(FBSKeys.USERPROCESSOR);

    if (rp == null) 
    {
      rp = new RequestProcessor(pool,list,log);
      rp.init(getServletContext(),FBSKeys.USERMAPPINGS);

      getServletContext().setAttribute(FBSKeys.USERPROCESSOR, rp);
    }

    return rp;
  }

  private ViewManager getViewManager() 
  {
    ViewManager vm =
      (ViewManager) getServletContext().getAttribute(FBSKeys.USERVIEW);

    if (vm == null) 
    {
      vm = new ViewManager();
      vm.init(getServletContext(),FBSKeys.USERMAPPINGS);

      getServletContext().setAttribute(FBSKeys.USERVIEW, vm);
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