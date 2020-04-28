<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %> 
<%@ page import = "java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Utils.Log" %>
<%@ page import = "ORS.Utils.Record" %>
<%@ page import = "ORS.Model.Admin" %>
<%@ page import = "ORS.Model.User" %>

<HTML>
<HEAD>
<TITLE>System Log</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<LINK href="/ORS/Web/images/Styles.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="AdminHeader.jsp" flush="true"/>
      <BR>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>            
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=396>
           <TABLE id=TABLE1 cellSpacing=1 cellPadding=2 width="100%" align=center border=0>
            <TR>
             <TD colspan="5">
            <TABLE class=catlist id=table2 style="BORDER-COLLAPSE: collapse" cellSpacing=0 cellPadding=0 width="100%">
            <TBODY>
            <TR>
              <TD vAlign=bottom><A class=tabOff 
                  href="/ORS/AdminController?EVENTNAME=SYSLOG_MN&ID=ADMIN">Admin Log</A></TD>
              <TD vAlign=bottom><A class=tabOffEnd
                  href="/ORS/AdminController?EVENTNAME=SYSLOG_MN&ID=USER">User Log</A></TD>
              <TD style="BORDER-BOTTOM: 1px solid" vAlign=bottom borderColor=#3770a8 width="100%"></TD>
			</TR></TBODY></TABLE>
			</TD></TR>
         <%
            int count = 0,i = 0,flag = 0;
            long id = 0;
            String enter = null;
            String path = null;
            String parameter = null,Logname = null;

			parameter = request.getParameter("ID");

            if(parameter == null)
			{
         %>
            <TR vAlign=center align=middle>
              <TD colSpan=6 class="Title1" style="background-color:#B5B5FD; color:#FFFFFF"> System Log View</TD>
            </TR>
            <P></P><P></P>
         <%
			}
			else
			{
              if(parameter != null && parameter.equals("ADMIN"))
			  {
                 path = config.getServletContext().getRealPath(getServletContext().getInitParameter("AdminLog"));
				 Logname = "Admin";
				 flag = 1;
			  }
			  else if(parameter != null && parameter.equals("USER"))
			  {
                 path = config.getServletContext().getRealPath(getServletContext().getInitParameter("UserLog"));
				 Logname = "User";
				 flag = 2;
			  }

              java.util.Date date = null;
              boolean check = false;
        
              Log file = new Log(path);
              check = file.ifExists();

              if(parameter != null && check == false)
              {
                out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>");
                out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>");
                out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>");
                out.println("<TR vAlign=center align=middle>");
                out.println("<TD>&nbsp;<BR></TD><TD>&nbsp;<BR></TD>");
				out.println("<TD><CENTER><h2>File Does not Exists.</h2><P></P></TD></TR>");
                out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>");
                out.println("<TR><TD>&nbsp;</TD><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>");
              }
              else if(check == true)
              {
                count = file.getCount();
                Record[] rec = file.readLog(count);
         %>
            <TR vAlign=center align=middle>
              <TD colSpan=6 class="Title1" style="background-color:#B5B5FD; color:#FFFFFF"> System Log View : <%=Logname%></TD>
            </TR>
            <TR bgColor="#abcdef" vAlign=center align=middle>
             <TD width="18%">
             <P align=center><STRONG><FONT face=Verdana size=1>Login Id</FONT></STRONG></P></TD>
             <TD width="54%">
             <P align=center><STRONG><FONT face=Verdana size=1>Event</FONT></STRONG></P></TD>
             <TD width="28%">
             <P align=center><STRONG><FONT face=Verdana size=1>Time</FONT></STRONG></P></TD>
            </TR>
            <P></P><P></P>
         <%
                ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
			    long temp = 0;
			    String name = null;
		 
                for(i = 0;i<count;i++)
                {
                  enter = rec[i].getEntry();
                  id = rec[i].getId();
                  date  = rec[i].getDate();
                  //System.out.println("Entry = " + enter + "Id = " + id);
                  out.println("<TR>");
                  out.println("<TD>");
                  out.println("<P align=center><STRONG><FONT face=Verdana size=1>");

			      if(id == temp)
			      {
                   out.println(name + "</FONT></STRONG></P></TD>");
			      }
			      else
			      {
				   if(flag == 1)
			         name = Admin.getLoginId(conn,new Long(id).intValue());
				   else if(flag == 2)
			         name = User.getLoginId(conn,id);

                   out.println(name + "</FONT></STRONG></P></TD>");
			      }

			      temp = id;
			      
			      java.text.SimpleDateFormat format = new java.text.SimpleDateFormat();
			      //format.setTimeZone(java.util.TimeZone.getTimeZone("America/New_York"));
			      // "EEE, d MMM yyyy HH:mm:ss Z"  Wed, 4 Jul 2001 12:08:56 -0700  
			      format.applyPattern("EEE, dd MMM yyyy hh:mm:ss aaa");
			      
                  out.println("<TD>");
                  out.println("<P align=center><STRONG><FONT face=Verdana size=1>");
                  out.println(enter + "</FONT></STRONG></P></TD>");
                  out.println("<TD>");
                  out.println("<P align=center><STRONG><FONT face=Verdana size=1>");
                  out.println(format.format(date) + "</FONT></STRONG></P></TD>");
                  out.println("</TR>");
                  out.println("<P></P>");
                }
              }
			}
         %>
       <P></P><P></P></P>
      </TABLE>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>