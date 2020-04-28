<%@ page contentType="text/html;charset=WINDOWS-1252" language="java" %>
<%@ page isErrorPage="true" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import="ORS.Exception.*" %>

<% 
   Throwable ex = null;
   String event = null;
   String Ename = "";

   if(request.getParameter("EVENTNAME") != null)
     Ename = "EVENTNAME";
   else if(request.getParameter("USEREVENT") != null)
     Ename = "USEREVENT";
   //else
     //throw new NullPointerException();

   if(exception != null)
   {
     ex = exception;
     
     if(request.getParameter("EVENTNAME") != null)
       event = request.getParameter("EVENTNAME");
     else if(request.getParameter("USEREVENT") != null)
       event = request.getParameter("USEREVENT");
       
   }
   else
   {
     try
     {
       ex = (Throwable) request.getAttribute("Exception");

       if(request.getParameter("EVENTNAME") != null)
         event = (String) session.getAttribute("ADMINMAIN");
       else if(request.getParameter("USEREVENT") != null)
         event = (String) session.getAttribute("USERMAIN");
     }
     catch(Exception e)
     {
       ex = e;
     }
   }
   
   if(event == null)
   {
     if(request.getParameter("EVENTNAME") != null)
       event = "ADLOGOUT_MN";
     else if(request.getParameter("USEREVENT") != null)
       event = "USLOGOUT";
   }
   
 %>
 
 <HTML>
 <HEAD>
 <META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
 <META content="" name=keywords>
 <META http-equiv=Content-Type content="text/html; charset=windows-1252">
 <META http-equiv="pragma" content="no-cache">
 <META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

 <% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

 <LINK href="../Web/images/Style.css" type=text/css rel=stylesheet>
 <TITLE><% 

   if(ex != null && ex instanceof ApplicationException)
   {
     ApplicationException aex = (ApplicationException) ex;
     out.print(aex.getTitle());
   }
   else
     out.print("ERROR PAGE");

 %></TITLE>
 <SCRIPT language="javascript">
 var i=0;

 function Button(doEvent)
 {
   if(doEvent == "")
   {
     document.ErrorPage.action="/ORS/Web/Home.html";
     document.ErrorPage.submit();
   }
   else
   {
     document.ErrorPage.<% out.print(Ename); %>value=doEvent;
     document.ErrorPage.submit();
   }
 }
 
 function Load()
 {
   if(i == 0)
   {
     eval("document.all.Stacktrace.style.display=''");
     i = 1;
   }
   else
   {
     eval("document.all.Stacktrace.style.display='none'");
     i = 0;
   }
 }

 </SCRIPT>
 </HEAD>
 <BODY>
 <DIV align=center>
 <CENTER>

 <% 
    if(ex != null && ex instanceof ApplicationException)
    {   
 %>

 <BR><BR><BR>
 <TABLE align=center><TBODY>
   <TR>
     <TD><img src="../Web/images/error.gif" align="bottom"></TD>
     <TD><H3>&nbsp;&nbsp;&nbsp;<% out.print(ex.getMessage()); %></H3></TD></TR>
   <TR>
     <TD></TD>
     <TD><H3>&nbsp;&nbsp;&nbsp;
     <% ApplicationException aex = (ApplicationException) ex;
        out.print(aex.getSolution()); %></H3></TD></TR>
 </TBODY></TABLE>

 <% }
    else if(ex != null && ex instanceof SQLException){
 %> 

 <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#111111 cellSpacing=0 
        cellPadding=0 width=600 border=0>
 <TBODY>
   <TR>
     <TD align=middle width="100%"><FONT face='' color=#ff00ff 
      size=5><% out.print("Database Exception"); %></FONT></TD></TR>
   <TR>
     <TD align=middle width="100%"><FONT face=Fixedsys color=#ff00ff 
       size=2>__________________________________________________________________________________</FONT></TD></TR>
   <TR>
     <TD align=middle width="100%">&nbsp;</TD></TR>
   <TR>
     <TD width="100%">
     <P align=justify><FONT face=Fixedsys color=#ff00ff><% out.println(ex); 
        out.println("Message : Please consult the System Administrator !!");%>.</FONT></P></TD></TR>
   <TR>
     <TD width="100%">
     <P align=center><FONT face=Fixedsys color=#ff00ff>__________________________________________________________________________________</FONT></P></TD></TR>
   <TR>
     <TD width="100%"><BR>
     <P align=center><FONT face=Fixedsys color=#ff00ff>Detail Exception Analysis</FONT></P></TD></TR>
   <TR>
     <TD width="100%"><BR>
       <TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
       <TBODY>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#ff00ff size=2>Primary Error :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#ff00ff size=2>
           <%  out.print(ex.getMessage()); %></FONT></B></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#ff00ff size=2>Exception Type :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#ff00ff size=2>
           <%  out.print(ex.getClass()); %></FONT>
           </B></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#ff00ff size=2>Excecution Event :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#ff00ff size=2>
           <%  out.print(request.getParameter("EVENTNAME")); %></FONT></B></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#ff00ff size=2>Excecution Class :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#ff00ff size=2>
           <%  out.print(ORSException.getClassName(ex,4)); %></FONT></B></FONT></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#ff00ff size=2>Excecution Method :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#ff00ff size=2>
           <%  out.print(ORSException.getMethodName(ex,4)); %></FONT></B></TD></TR>
         </TBODY></TABLE></TD></TR>
         <TR>
           <TD width="100%">&nbsp;</TD></TR>
         <TR>
           <TD width="100%"><P align=center><FONT face=Fixedsys color=#ff00ff>__________________________________________________________________________________</FONT></P>
           </TD></TR>
         <TR>
           <TD width="100%">&nbsp;</TD></TR>
         <TR>
           <TD width="100%" nowrap>
           <A href="javascript:Load()" class="clickable" onMouseOver="this.style.color='blue';" onFocus="this.style.color='blue';" onMouseOut="status='';this.style.color='darkblue'" onBlur="status='';this.style.color='darkblue'"><FONT 
              face=Fixedsys color=#ff00ff>Click here for Details 
           </FONT></A></TD></TR>
         <TR>
           <TD width="100%"><FONT face=Fixedsys color=#ff00ff><BR>
           <div ID="Stacktrace" Style="display:none;"><%  ORSException.printStackTrace(ex,out);  %></div></FONT></TD></TR>
         <TR>
           <TD width="100%"><FONT face=Fixedsys color=#ff00ff></FONT></TD></TR>
         <TR>
         <TD width="100%">&nbsp;</TD></TR></TBODY></TABLE> 
 
 <%  }
     else{ 
  %>

 <TABLE style="BORDER-COLLAPSE: collapse" borderColor=#111111 cellSpacing=0 
        cellPadding=0 width=600 border=0>
 <TBODY>
   <TR>
     <TD align=middle width="100%"><FONT face='' color=#0000ff size=5>
  <% 
     if(ex != null)
     {
       out.print("System Exception");
     }
     else
	  out.print("Unknown Exception"); %>
   </FONT></TD></TR>
   <TR>
     <TD align=middle width="100%"><FONT face=Fixedsys color=#0000ff 
       size=2>__________________________________________________________________________________</FONT></TD></TR>
   <TR>
     <TD align=middle width="100%">&nbsp;</TD></TR>
   <TR>
     <TD width="100%">
     <P align=center><FONT face=Fixedsys color=#0000ff>
     <% if(ex != null) out.println(ex); else out.println("NullPointerException");
        out.println("Message : Press 'OK' to Terminate.");%>.</FONT></P></TD></TR>
   <TR>
     <TD width="100%">
     <P align=center><FONT face=Fixedsys color=#0000ff>__________________________________________________________________________________</FONT></P></TD></TR>
   <TR>
     <TD width="100%"><BR>
     <P align=center><FONT face=Fixedsys color=#0000ff>Detail Exception Analysis</FONT></P></TD></TR>
   <TR>
     <TD width="100%"><BR>
       <TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
       <TBODY>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#0000ff size=2>Primary Error :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#0000ff size=2>
           <%  if(ex != null) out.print(ex.getMessage()); else out.print("NullPointerException"); %></FONT></B></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#0000ff size=2>Exception Type :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#0000ff size=2>
           <%  if(ex != null) out.print(ex.getClass()); else out.print("java.lang.NullPointerException"); %></FONT>
           </B></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#0000ff size=2>Excecution Event :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#0000ff size=2>
           <%  out.print(request.getParameter("EVENTNAME")); %></FONT></B></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#0000ff size=2>Excecution Class :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#0000ff size=2>
           <%  if(ex != null) out.print(ORSException.getClassName(ex,1)); else out.print("Unknown"); %></FONT></B></FONT></TD></TR>
         <TR>
           <TD width="30%"><FONT face=Tahoma color=#0000ff size=2>Excecution Method :</FONT></TD>
           <TD width="70%"><B><FONT face=Tahoma color=#0000ff size=2>
           <%  if(ex != null) out.print(ORSException.getMethodName(ex,1));
               else  out.print("Unknown Error");%></FONT></B></TD></TR>
         </TBODY></TABLE></TD></TR>
         <TR>
           <TD width="100%">&nbsp;</TD></TR>
         <TR>
           <TD width="100%"><P align=center><FONT face=Fixedsys color=#0000ff>__________________________________________________________________________________</FONT></P>
           </TD></TR>
         <TR>
           <TD width="100%">&nbsp;</TD></TR>
         <TR>
           <TD width="100%" nowrap>
           <A href="javascript:Load()" class="clickable" onMouseOver="this.style.color='blue';" onFocus="this.style.color='blue';" onMouseOut="status='';this.style.color='darkblue'" onBlur="status='';this.style.color='darkblue'"><FONT 
              face=Fixedsys color=#0000ff>Click here for Details 
           </FONT></A></TD></TR>
         <TR>
           <TD width="100%"><FONT face=Fixedsys color=#0000ff><BR>
           <div ID="Stacktrace" Style="display:none;">
           <%  if(ex != null) ORSException.printStackTrace(ex,out); %>
           </div></FONT></TD></TR>
         <TR>
           <TD width="100%"><FONT face=Fixedsys color=#0000ff></FONT></TD></TR>
         <TR>
         <TD width="100%">&nbsp;</TD></TR></TBODY></TABLE>

  <%  }  %>

</CENTER></DIV><P>&nbsp;</P> <P>
  <FORM NAME="ErrorPage" ACTION=>
  <INPUT name='<% out.print(Ename); %>' TYPE='hidden'>

  <CENTER><INPUT name="Ok" type="button" value="  OK  " onclick="Button('<% out.print(event); %>')" style="WIDTH: 99px; HEIGHT: 27px" size=25>
  </CENTER></FORM>
  </BODY>
  </HTML>