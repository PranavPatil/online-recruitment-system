<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>

<% 
   ConnectionPool pool = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
 %>

<HTML>
<HEAD>
<TITLE>Admin View</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="/ORS/Web/images/Sort.js"></SCRIPT>
<SCRIPT language="javascript">

function back(form)
{
	doc=open("http://localhost:1977/examples/Admin/UserUpdate.jsp","right1");
}

</SCRIPT>
</HEAD>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="AdminHeader.jsp" flush="true"/>

      <FORM id="AdminView" name="AdminView" action="/ORS/AdminController">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE name="HDTable" id=HDTable cellSpacing=1 cellPadding=1 width="100%" align=center border=0 cols=5>  
           <TR vAlign=center align=middle>
             <TD colSpan=6 class="Title1" style="background-color:#B5B5FD; color:#FFFFFF">Admin View </TD></TR>
           <TR><TD colspan="5">&nbsp;</TD></TR>
         </TABLE>
         <TABLE name="AvTable" id=AvTable cellSpacing=1 cellPadding=2 width="100%" align=center border=0 cols=5>
           <TR>
             <TD><P align=center></P></TD>
             <TD bgColor="#abcdef">
             <P align=center><A href="javascript:sortTable(1, AvTable);"><STRONG><FONT face=Verdana size=1><B>AdminName</B></FONT></A></TD>
         	 <TD bgColor="#abcdef">
     	     <P align=center><A href="javascript:sortTable(2, AvTable);"><STRONG><FONT face=Verdana size=1><B>Designation</B></FONT></A></TD>
     	  	 <TD bgColor="#abcdef">
	         <P align=center><A href="javascript:sortTable(3, AvTable);"><STRONG><FONT face=Verdana size=1>TelephoneNo</FONT></STRONG></A></P></TD>
	     	 <TD bgColor="#abcdef">
     	     <P align=center><A href="javascript:sortTable(4, AvTable);"><STRONG><FONT face=Verdana size=1>Email</FONT></STRONG></A></P></TD>
      	   </TR>
           <% 
              Database db = new Database(pool);
              ResultSet TData = db.RetriveDb("Select * from Admin");
  			  Database temp = new Database(pool); 

              while(TData.next())
              {
			    out.print("<TR>");
       	        out.print("<TD></TD><TD><P align=center><FONT  class=h1>");
			    out.print(TData.getString(2) + " " + TData.getString(3));
			    out.print("</FONT></P></TD>");
			    out.print("<TD><P align=center><FONT class=h1>");
                ResultSet rs = db.RetriveDb("Select Designation from Accessibility where Access_Id = " + TData.getInt(8));
				rs.next();
			    out.print(rs.getString(1));
			    out.print("</FONT></P></TD>");

			    out.print("<TD><P align=center><FONT class=h1>");
			    out.print(TData.getString(6));
			    out.print("</FONT></P></TD>");
			 
			    out.print("<TD><P align=center><FONT class=h1>");
			    out.print(TData.getString(7));
			    out.print("</FONT></P></TD>");
			    out.print("</TR>");
		      }
           %>
           </TABLE>
           <P></P><P></P><P></P></P>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>    