<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %> 
<%@ page import = "java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<%!
    private int Cat_Id = -1;
    private String Type = null;
%>

<% 
    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
    String str = request.getParameter("SelectCategory");
    System.out.println("cat = " + str);

    if(str != null)
    {
      int i = 0;
      i = Integer.parseInt(str);
      
      if(i > -1)
      {
        Cat_Id = i;
      }
    }
    
    str = null;
    str = request.getParameter("SelectType");
    System.out.println("type = " + str);
    
    if(str != null)
    {
      Type = str;
      
      if(Type.equals("All"))
      {
        Type = null;
      }
    }
 %>
 
<HTML>
<HEAD>
<TITLE>Question Management</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript">

function sure(doEvent) 
{
  if(confirm("This button will DELETE the selected Test . Are you sure you want to continue?")) 
  {
    document.QuestionMain.EVENTNAME.value=doEvent;
    document.QuestionMain.submit();
  }
}

function verify(doEvent)
{
  document.QuestionMain.EVENTNAME.value=doEvent;

  if(document.QuestionMain.SelectCategory.value=="---Select Category---")
    return false;
  else
    document.QuestionMain.submit();
}

function Button(doEvent)
{
  document.QuestionMain.EVENTNAME.value=doEvent;
  document.QuestionMain.submit();
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

      <FORM id="QuestionMain" name="QuestionMain" action="/ORS/AdminController">
      <INPUT TYPE="hidden" name="EVENTNAME">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>            
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

          <TABLE align=center>
            <TR>
              <TD class="Title1" colSpan=4 align="center">Question&nbsp;&nbsp;Management</TD></TR>
            <TR><TD colSpan=4>&nbsp;</TD></TR>
            <TR><TD colSpan=4>&nbsp;</TD></TR>
            <TR>
              <TD colSpan=4 class="link10"><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>&nbsp;
	            <A class="HREF" onclick="Button('QUESMGT_CRE')">
	            <U>Create a new question</U></A></TD></TR>
            <TR><TD colSpan=4>&nbsp;</TD></TR>
            <TR>
              <TD width="50%" class="link10"><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>&nbsp; 
                <A class="HREF" onclick="Button('QUESMGT_SRH')">
	            <U>Search Questions</U></A></TD>
              <TD> </TD></TR>
            <TR><TD colSpan=4>&nbsp;</TD></TR>
            <TR>
              <TD width="50%" class="link11"><IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>&nbsp;
			   View all Questions&nbsp;&nbsp;&nbsp;<SELECT name=SelectCategory class="breadcrumbs">
            <%
               out.print("<OPTION value = 0>---Select Category---");
               String query = "select Category_Id,Name from Category";
             %>
             
             <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= Cat_Id %>" />

            </SELECT>&nbsp;&nbsp;&nbsp;&nbsp;
            <SELECT style="WIDTH: 116px" name=SelectType class="breadcrumbs">
            <%
               if(Type != null)
               {
                 if(Type.equals("MC"))
                 {
                   out.print("<OPTION>--Select Type--<OPTION value = \"MC\"  selected>Multiple Choice<OPTION value = \"MA\">Multiple Ans<OPTION value = \"TF\">True or False</OPTION>");
                 }
                 else if(Type.equals("MA"))
                 {
                   out.print("<OPTION>--Select Type--<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\"  selected>Multiple Ans<OPTION value = \"TF\">True or False</OPTION>");
                 }
                 else if(Type.equals("TF"))
                 {
                   out.print("<OPTION>--Select Type--<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\">Multiple Ans<OPTION value = \"TF\"  selected>True or False</OPTION>");
                 }
                 else
                 {
                   out.print("<OPTION selected>--Select Type--<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\">Multiple Ans<OPTION value = \"TF\">True or False</OPTION>");
                 }
               }
               else
               {
                 out.print("<OPTION selected>--Select Type--<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\">Multiple Ans<OPTION value = \"TF\">True or False</OPTION>");
               }
            %>
            </SELECT>&nbsp;</TD>
            <TD>&nbsp;&nbsp; <INPUT class="B1" type=button id="Go" name="Go" value="  Go " onclick="Button('QUESMGT_GO')" size=24>
            </TD></TR>    
         <TR>
           <TD colSpan=4>&nbsp;</TD></TR>
         <TR>
           <TD style="WIDTH: 50%" noWrap width="50%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		   <SELECT style="WIDTH: 300px" class="breadcrumbs" name="SelectQuestion">

          <% query = "select Ques_Id,Question from Questions where category_id="+ Cat_Id + 
	                 " and Type = '" + Type + "'"; %>
          <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= Cat_Id %>" />

          </SELECT></TD>
        <TD></TD></TR>
        <TR>
        <TD colSpan=4>&nbsp;</TD></TR>
        <TR>
        <TD colSpan=4>&nbsp;</TD></TR>
        <TR>
        <TD align=right>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT class="B1" name="Edit Question" type=button value="Edit" onclick="Button('QUESMGT_EDT')" size=12>
               &nbsp;&nbsp;&nbsp;
        <INPUT class="B1" type=button value="Delete" name=DelQues onclick=sure('QUESMGT_DEL') size=12>
               &nbsp;&nbsp;&nbsp;
        <INPUT class="B1" type=button value="<< Back " name=btnBack onclick=Button('EXAMMGT_MN') size=12></TD>
        </TR></TABLE>
      </TABLE>
   </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY>
 </BODY></HTML>