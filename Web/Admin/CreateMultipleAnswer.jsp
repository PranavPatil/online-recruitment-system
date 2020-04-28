<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Model.MA_Question" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<% 
   long Ques_Id = -1;

   DataEntryException exception = null;
   MA_Question ma_question = null;
   ConnectionPool pool = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
   
   try
   {
     String str = null,event = null;
     exception = (DataEntryException) request.getAttribute("Exception");
     System.out.println("Exception = " + exception);
     
     event = request.getParameter("EVENTNAME");
     
     if(event.equals("QUESMGT_EDT"))
     {
       str = request.getParameter("SelectQuestion");
       
       if(str != null)
         Ques_Id = Long.parseLong(str);
     }
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     Ques_Id = -1;
     exception = null;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     Ques_Id = -1;
   }
   
   System.out.println("Ques_Id = " + Ques_Id);
   
   if(exception != null)
   {
     ma_question = new MA_Question(request);
     System.out.println("fname = " + ma_question.toString());
   }
   else if(Ques_Id > 0)
   {
     ma_question = new MA_Question(Ques_Id,pool);
   }
   else
     ma_question = null;
 %>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<TITLE>Create Multiple Answer Question</TITLE>
<SCRIPT language=javascript>

 function validate(doEvent)
 {
   document.CreateMAQues.EVENTNAME.value=doEvent;
     
   if(document.CreateMAQues.Question.value=="")
   { 
     alert("Please enter the Question .");
     document.CreateMAQues.Question.focus();
   }
   else if(document.CreateMAQues.Question.value.length>200)
   {
     alert("Question should not exceed over 200 alphabets !");
     document.CreateMAQues.Question.focus();
   }
   else if(document.CreateMAQues.Ans1.value=="")
   { 
     alert("Please enter Answer A .");
     document.CreateMAQues.Ans1.focus();
   }
   else if(document.CreateMAQues.Ans2.value=="")
   { 
     alert("Please enter Answer B .");
     document.CreateMAQues.Ans2.focus();
   }
   else if(document.CreateMAQues.Ans3.value=="")
   { 
     alert("Please enter Answer C .");
     document.CreateMAQues.Ans3.focus();
   }
   else if(document.CreateMAQues.Ans4.value=="")
   { 
     alert("Please enter Answer D .");
     document.CreateMAQues.Ans4.focus();
   }
   else
   {
     document.CreateMAQues.submit();
   }
 }
 
function cancel(doEvent)
{
  document.CreateMAQues.EVENTNAME.value=doEvent;
  document.CreateMAQues.submit();
}

</SCRIPT>
</HEAD>
<BODY leftMargin=50 rightMargin=50 style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="AdminHeader.jsp" flush="true"/>

      <FORM id="CreateMAQues" name="CreateMAQues" action="/ORS/AdminController">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <INPUT type=hidden name="Ques_Id" value='<%= Ques_Id %>'>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

          <TABLE name="Heading" cellSpacing=1 cellPadding=1 width="90%" border=0 align="center">
          <TR><TD class="Title1">&nbsp;&nbsp;&nbsp;Create Question: Multiple Answer</TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          
            <c:if test="<%= (exception != null) %>">
               <TR><TD class="LabelA" style="color:#FF3333" colSpan=3>
               Error : <%= exception.getMessage() %><BR><%= exception.getSolution() %>
               </TD><TD></TD></TR>
			   <TR><TD><HR></TD><TD><HR></TD></TR>
            </c:if>
            
          <TR><TD class="h2">Please complete this form to build your new question. <i>Make sure to 
		  <strong>indicate</strong> which answers are the <strong>correct one</strong>.</i></TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD><FONT class="LabelA">Question</FONT> : <FONT class="Title3"><i>Type your question in the textArea below</i></FONT></TD></TR>
          <TR><TD><TEXTAREA name="Question" maxLength="80" rows=6 cols=45 class="breadcrumbs" style="width:400 "><% if(ma_question != null) out.print(ma_question.getQuestion()); %></TEXTAREA></TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD class="Title3">
		  <%  if(ma_question != null && ma_question.getCorrectAns(1) == 1)
             {
               out.println("&nbsp;A <INPUT name=\"AnsA\" type=\"checkbox\" value=\"1\" checked>&nbsp;&nbsp;&nbsp;");
             }
             else
             {
               out.println("&nbsp;A <INPUT name=\"AnsA\" type=\"checkbox\" value=\"1\">&nbsp;&nbsp;&nbsp;");
             }
          %><INPUT name="Ans1" size=77 style="WIDTH: 346px;" maxLength=30 value='<% if(ma_question != null) out.print(ma_question.getAns1()); %>' class="breadcrumbs"><br>
          <%  if(ma_question != null && ma_question.getCorrectAns(2) == 2)
             {
               out.println("&nbsp;B <INPUT name=\"AnsB\" type=\"checkbox\" value=\"2\" checked>&nbsp;&nbsp;&nbsp;");
             }
             else
             {
               out.println("&nbsp;B <INPUT name=\"AnsB\" type=\"checkbox\" value=\"2\">&nbsp;&nbsp;&nbsp;");
             }
          %><INPUT name="Ans2" size=77 style="WIDTH: 346px;" maxLength=30 value='<% if(ma_question != null) out.print(ma_question.getAns2()); %>' class="breadcrumbs"><br>
          <%  if(ma_question != null && ma_question.getCorrectAns(3) == 3)
             {
               out.println("&nbsp;C <INPUT name=\"AnsC\" type=\"checkbox\" value=\"3\" checked>&nbsp;&nbsp;&nbsp;");
             }
             else
             {
               out.println("&nbsp;C <INPUT name=\"AnsC\" type=\"checkbox\" value=\"3\">&nbsp;&nbsp;&nbsp;");
             }
          %><INPUT name="Ans3" size=77 style="WIDTH: 346px;" maxLength=30 value='<% if(ma_question != null) out.print(ma_question.getAns3()); %>' class="breadcrumbs"><br>
          <%  if(ma_question != null && ma_question.getCorrectAns(4) == 4)
             {
               out.println("&nbsp;D <INPUT name=\"AnsD\" type=\"checkbox\" value=\"4\" checked>&nbsp;&nbsp;&nbsp;");
             }
             else
             {
               out.println("&nbsp;D <INPUT name=\"AnsD\" type=\"checkbox\" value=\"4\">&nbsp;&nbsp;&nbsp;");
             }
           %><INPUT name="Ans4" size=77 style="WIDTH: 346px;" maxLength=30 value='<% if(ma_question != null) out.print(ma_question.getAns4()); %>' class="breadcrumbs"></TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD class="LabelA">&nbsp;Select Category : &nbsp;&nbsp;<SELECT NAME="SelCategory" class="breadcrumbs">

            <% String query = "select Category_Id, Name from category"; %>
            <% if(ma_question != null)
               {
            %>
                <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= ma_question.getCategory_Id() %>" />
            <% }
               else
               {
            %>
                <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" />
            <% } %>

          </SELECT></TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          </TABLE><BR>
         <TABLE name="MA_Buttons" cellSpacing=1 cellPadding=1 width="55%" border=0 align="left">
          <TR align="center">
            <TD width="7%">&nbsp;</TD>
         <% if(request.getParameter("EVENTNAME") != null && request.getParameter("EVENTNAME").equals("SELQUES_MA"))
            { %>
            <TD width="29%"><INPUT class="B1" name="Add" type="button" value="Add" onclick="validate('CREQSMA_CRE')"></TD>
            <TD width="36%"><INPUT class="B1" name="Reset" type="reset" value="Reset"></TD>
            <TD width="28%"><INPUT class="B1" name="Cancel" type="button" value="Cancel" onclick="cancel('CREQSMA_CL')"></TD>
         <% }
            else if(request.getParameter("EVENTNAME") != null)
            { %>
            <TD width="29%"><INPUT class="B1" name="Add" type="button" value="Add" onclick="validate('EDTQSMA_CRE')"></TD>
            <TD width="36%"><INPUT class="B1" name="Reset" type="reset" value="Reset"></TD>
            <TD width="28%"><INPUT class="B1" name="Cancel" type="button" value="Cancel" onclick="cancel('EDTQSMA_CL')"></TD>
         <% } %>
          </TR>
      </TABLE>
      </TABLE>
    </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>