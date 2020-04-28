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
<TITLE>Category Management</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript">

function sure(doEvent) 
{
  if(confirm("This button will DELETE the selected CATEGORY and all the QUESTIONS in this Category. Are you sure you want to continue?")) 
  {
    document.CategoryMain.EVENTNAME.value=doEvent;
    document.CategoryMain.submit();
  }
}

function Button(doEvent)
{
  document.CategoryMain.EVENTNAME.value=doEvent;
  document.CategoryMain.submit();
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

      <FORM id="CategoryMain" name="CategoryMain" action="/ORS/AdminController">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE cellSpacing=1 cols=4 cellPadding=1 width="90%" align=center border=0>
          <TR><TD colSpan=2>&nbsp;</TD></TR>
          <TR><TD class="Title1" colSpan=2 align=center>&nbsp;Category Management</TD></TR>
          <TR><TD colspan="4">&nbsp;</TD></TR>
          <TR><TD colspan="4">&nbsp;</TD></TR>
          <TR>
            <TD colspan="2" class="link10"><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
           &nbsp;&nbsp;&nbsp;<A class="HREF" onclick="Button('CATMGT_CRE')">
           <U>Create New Category</U></A></TD>
          </TR>
          <TR><TD colspan="4">&nbsp;</TD></TR>
          <TR> 
          <TD width="48%" nowrap><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>&nbsp;&nbsp;&nbsp;
          <SELECT name="SelectCategory" style="WIDTH: 142px; " class="breadcrumbs"> 
          
          <% String query = "Select Category_Id,Name from Category"; %>
          <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" />

	      </SELECT> &nbsp;&nbsp;&nbsp;
          <INPUT class="B1" type="button" name="EditCategory" value="Edit Category" onClick="Button('CATMGT_EDT')" size=22>
          </TD>
           <TD width="52%">&nbsp;<INPUT class="B1" type="button" name="DelCategory" value="Delete Category" onclick="sure('CATMGT_DEL')" size=22></TD>
          <TR><TD colspan="4">&nbsp;</TD></TR>
          <TR><TD width="48%" nowrap></TD>
           <TD width="52%">&nbsp;<INPUT class="B1" type="button" name="btnBack" value="   Back << " onclick="Button('EXAMMGT_MN')" size=22> </TD>
          </TR>
          </TABLE>
      </TABLE>
    </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>
  </BODY>
</HTML>