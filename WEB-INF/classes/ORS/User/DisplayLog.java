package ORS.User;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import ORS.Utils.*;

public class DisplayLog extends HttpServlet
{
	public void init(ServletConfig conf)throws ServletException
	{
	  super.init(conf);
	}
		 
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	 res.setContentType("text/html");
     PrintWriter out=res.getWriter();     
     HttpSession session=req.getSession(true);

     int count = 0,i = 0;
     long id = 0;
     String path = "C:/Log.dat",enter = null;
     Log file = null;
     Date date = null;    
     boolean check = false;
        
     file = new Log(path);
                            
     out.println("<HTML>");
     out.println("<HEAD>");
     out.println("<META NAME=\"GENERATOR\" Content=\"Microsoft Visual Studio 6.0\">");
     out.println("<TITLE>User Log</TITLE>");
     out.println("</HEAD>");
     out.println("<BODY bgcolor=\"ivory\">");
     out.println("<P>");

     check = file.ifExists();          

     if(check == false)
     {
	   out.println("<CENTER><h1>File Does not Exists !</h1><P></P>");	     	
	   out.println("<h1>Please Check the Path Again. </h1></CENTER>");	     		   
     }     
     else if(check == true)
     {
      count = file.getCount();        
      Record[] rec = file.readLog(count);
      
      out.println("<TABLE cellSpacing=1 cellPadding=5 width=\"100%\" align=center border=0 id=TABLE1>");
      out.println("<TR vAlign=center align=middle bgColor=black>");
      out.println("<TD colSpan=5");
      out.println("style=\"VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center\">");
      out.println("<FONT face=Verdana size=2><STRONG");
      out.println("style=\"COLOR: white; BACKGROUND-COLOR: black\"> ");
      out.println("Log View Of Users");
      out.println("</STRONG></FONT></TD></TR>");
      out.println("<TR>");
      out.println("<TD colspan=\"5\">&nbsp;</TD></TR>");
      out.println("<TR bgColor=\"#abcdef\">");
      out.println("<TD>");
      out.println("<P align=center><STRONG><FONT face=Verdana size=1>Id</FONT></STRONG></P></TD>");
      out.println("<TD>");
      out.println("<P align=center><STRONG><FONT face=Verdana size=1>Event</FONT></STRONG></P></TD>");
      out.println("<TD>");
      out.println("<P align=center><STRONG><FONT face=Verdana size=1>Time</FONT></STRONG></P></TD>");
      out.println("</TR>");
      out.println("<P></P>");
      out.println("<P></P>");     
     
      for(i = 0;i<count;i++)
      {
        enter = rec[i].getEntry();
        id = rec[i].getId();  	
        date  = rec[i].getDate();
        //System.out.println("Entry = " + enter + "Id = " + id);
        out.println("<TR>");
        out.println("<TD>");
        out.println("<P align=center><STRONG><FONT face=Verdana size=1>");
        out.println(id + "</FONT></STRONG></P></TD>");
        out.println("<TD>");
        out.println("<P align=center><STRONG><FONT face=Verdana size=1>");
        out.println(enter + "</FONT></STRONG></P></TD>");
        out.println("<TD>");
        out.println("<P align=center><STRONG><FONT face=Verdana size=1>");
        out.println(date + "</FONT></STRONG></P></TD>");
        out.println("</TR>");
        out.println("<P></P>");
      }     	
     }
     
     out.println("<P></P>");
     out.println("<P></P></P>");
	 out.println("</BODY></HTML>");
	 
    }

	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		 doGet(req,res);
	}
}
