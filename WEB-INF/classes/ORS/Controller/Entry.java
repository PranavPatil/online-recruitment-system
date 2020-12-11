
package ORS.Controller;

import java.util.*;

public class Entry
{
  private long Id = -1;
  private String Session_Id = null;
  private Date LoginTime;
  private Date LastAccess;
  
  public Entry()
  {
  	super();
  }

  public Entry(long id,String session)
  {
  	super();
  	Id = id;
  	Session_Id = session;
  	LoginTime = Calendar.getInstance().getTime();
    LastAccess = LoginTime;
  }

  public Entry(long id,Date logintime,Date lastaccess)
  {
  	super();
  	Id = id;
    LoginTime = logintime;
    LastAccess = lastaccess;
  }

  public void setId(long id)
  {
  	Id = id;
  }
  
  public void setSessionId(String sid)
  {
  	Session_Id = sid;
  }
  
  public void setLoginTime(Date logintime)
  {
  	LoginTime = logintime;
  }
  
  public void setLastAccess(Date lastaccess)
  {
  	LastAccess = lastaccess;
  }

  public long getId()
  {
  	return Id;
  }
  
  public String getSessionId()
  {
  	return Session_Id;
  }
  
  public Date getLoginTime()
  {
  	return LoginTime;
  }
  
  public Date getLastAccess()
  {
  	return LastAccess;
  }
  
  public String toString()
  {
  	return Id + " , " + Session_Id + " , " + LoginTime + " , " + LastAccess;
  }
}