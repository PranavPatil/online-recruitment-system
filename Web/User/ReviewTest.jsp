<%@ page contentType="text/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Exception.ApplicationException" %>

<% /*<jsp:useBean id="connect" scope="page" class="ORS.ConnPool.Connect"/>
<jsp:setProperty name="connect" property="clear" value="dest"/>*/ %>

<HTML>
<HEAD>
<TITLE>Test Review</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="pragma" content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<LINK href="/ORS/Web/images/links.css" type="text/css" rel="stylesheet">
<script language=javascript>

function Button(doEvent)
{
  document.ReviewTest.USEREVENT.value=doEvent;
  document.ReviewTest.submit();
}

function ReviewButton(doEvent)
{
  document.ReviewTest.DisplayId.value = doEvent;
  document.ReviewTest.USEREVENT.value="TSTBACK";
  document.ReviewTest.submit();
}

</script>
</HEAD>
<BODY>
<jsp:include page="UserHeader.jsp" flush="true"/>

  <FORM id="ReviewTest" name="ReviewTest" action="/ORS/UserController" method="post">
  <INPUT name="USEREVENT" type="hidden">
  <INPUT name="DisplayId" type="hidden">
  <table cellpadding="0" cellspacing="0" width="745" border="0" align=center style="WIDTH: 745px; HEIGHT: 236px">
  <tr>
    <td align="center" valign="top" width="100%">
    <table cellpadding="5" cellspacing="0" width="100%" border="0" align=left>
  <tr>
    <td align="left" bgcolor="#abcdef" valign=center>
  <CENTER>
    <FONT size=5><STRONG>Test Review - Answer Summary</STRONG></FONT>
  </CENTER>
    </td>
  </tr>
  </table>
  </td>
  </tr>
  <tr>
     <td align="center" valign="top" width="100%">
     <table cellpadding="2" cellspacing="0" width="600" border="0" align="center">&nbsp;
  <tr>
     <td align="center" nowrap colspan="3"><b>Please click a question number to return to that question.</b></td>
  </tr>
  <tr>
     <td align="center" nowrap colspan="3"><b>Otherwise, click ok.</b></td>
  </tr>
  <tr>
     <td>&nbsp;</td>
  </tr>
  <tr>
     <th align="center" valign="top">Question Number</th><th align="center" valign="top">Answered</th><th align="center" valign="top">Unanswered</th>
  </tr>
  <tr>
     <td>&nbsp;</td>
  </tr>
<%
   long User_Id = 0;
   int Post_Id = 0;
   int Cat_Id = 0;
   int i = 0;
   
   User_Id = (Long)session.getAttribute("User_Id");
   Post_Id = (Integer)session.getAttribute("Post_Id");
   Cat_Id  = (Integer)session.getAttribute("Cat_Id");

   ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool");
   Database db = new Database(pool);

   String query = "Select N.User_Answer from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id = " +
                   User_Id + " and Category_Id = " + Cat_Id + " order by N.Ques_Number"; 
   
   ResultSet rs = db.RetriveDb(query);

   int value;
   
   while(rs.next())
   {
     out.println("<tr>");
     value = rs.getInt(1);
     
	 out.println("<tr>");
     out.println("<td align=\"middle\" valign=\"top\">");
     ++i;
     
     if(value == 0)
     {
       out.println("<A class=\"HREF\" onclick=\"ReviewButton('" + i + "')\">" + i);
       out.println("</A></td><td align=\"middle\" valign=\"top\">");
       out.println("<IMG src=\"/ORS/Web/images/checkbox_0.jpg\" border=0></td>");
       out.println("<td align=\"middle\" valign=\"top\">");
       out.println("<IMG src=\"/ORS/Web/images/checkbox_1.jpg\" border=0></td>");
     }
     else
     {
       out.println("<A class=\"HREF\" onclick=\"ReviewButton('" + i + "')\">" + i);
       out.println("</A></td><td align=\"middle\" valign=\"top\">");
       out.println("<IMG src=\"/ORS/Web/images/checkbox_1.jpg\" border=0></td>");
       out.println("<td align=\"middle\" valign=\"top\">");
       out.println("<IMG src=\"/ORS/Web/images/checkbox_0.jpg\" border=0></td>");
     }
     out.println("</tr>");
   }
%>
     </table>
     </td>
   </tr>
   <tr>
     <td align="center" valign="bottom" width="100%">
     <table cellpadding="0" cellspacing="0" width="100%" border="0" align=left>
   <tr><td>&nbsp;</td></tr>
   <tr>
      <td align="center"><input name="Ok" type='button' style="WIDTH: 81px; HEIGHT: 24px" onclick="Button('TSTPAGE')" value="OK" size=38></td>
   </tr>
   </table>
   </td>
   </tr>
   </table>            
   </FORM>
   <jsp:include page="UserFooter.jsp" flush="false"/>
</BODY>
</HTML>
