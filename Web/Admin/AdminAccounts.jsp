<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %> 
<%@ page import = "java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<% 
   ConnectionPool pool = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
 %>

<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<TITLE>Administator Accounts</TITLE>
<SCRIPT language="javascript">

function sure(doEvent) 
{
   if(confirm("This button will DELETE the selected Admin . Are you sure you want to continue?")) 
   {
     document.AdminMain.EVENTNAME.value=doEvent;
     document.AdminMain.submit();     
   }
 }
 
function Button(doEvent)
{
  document.AdminMain.EVENTNAME.value=doEvent;
  document.AdminMain.submit();
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

      <FORM id="AdminMain" name="AdminMain" action="/ORS/AdminController">
      <INPUT TYPE="hidden" name="EVENTNAME">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>            
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

            <TABLE height=94 cellSpacing=0 cellPadding=1 width="90%" border=0 align="center">
            <TBODY>
             <TR><TD colSpan=10>&nbsp;</TD></TR>
             <TR><TD class="Title1" colSpan=10 align=center>&nbsp;Admin Accounts</TD></TR>
             <TR><TD colspan="10">&nbsp;</TD></TR>
             <TR><TD colspan="10">&nbsp;</TD></TR>
             <TR>
             <TD colspan="10" class="link10"><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
             &nbsp;&nbsp;&nbsp;<A class="HREF" onclick="Button('ADMNACC_CRE')">
			 <U>Create New Admin</U></A></TD>
             </TR>
             <TR><TD colspan="10">&nbsp;</TD></TR>
             <TR>
             <TD colspan="10" class="link10"><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
             &nbsp;&nbsp;&nbsp;<A class="HREF" onclick="Button('ADMNACC_VW')">
			 <U>Administrator View</U></A></TD>
             </TR>
             <TR><TD colspan="10">&nbsp;</TD></TR>
             <TR>
             <TD colspan="4"><img src="/ORS/Web/images/navyblueball.gif" align="middle" border=0></TD>
	         <TD width="52%" nowrap>
             <SELECT name="SelectAdmin" style="WIDTH: 142px;" class="breadcrumbs">

             <% String query = "select Login,Admin_Id from Admin"; %>
             <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>"/>

             </SELECT> 
              &nbsp;&nbsp; 
              <INPUT class="B1" type="button" name="DelAdmin" value="  Delete  Profile  " onclick="sure('ADMNACC_DEL')"> 
              &nbsp;</TD>
              <TD width="40%">&nbsp;
              <INPUT class="B1" type="button" name="EditAdmin" value="  Update  Profile  " onClick="Button('ADMNACC_EDT')" size=22></TD>
            </TR>
           </TABLE>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>