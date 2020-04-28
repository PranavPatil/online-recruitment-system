<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Model.TF_Question" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<% 
   long Ques_Id = -1;

   DataEntryException exception = null;
   TF_Question tf_question = null;
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
     tf_question = new TF_Question(request);
     System.out.println("fname = " + tf_question.toString());
   }
   else if(Ques_Id > 0)
   {
     tf_question = new TF_Question(Ques_Id,pool);
   }
   else
     tf_question = null;
 %>
<HTML>
<HEAD>
<TITLE>Create True Or False Question</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript>

 function validate(doEvent)
 {
   document.CreateTFQues.EVENTNAME.value=doEvent;

   if(document.CreateTFQues.Question.value=="")
   { 
     alert("Please enter the Question .");
     document.CreateTFQues.Question.focus();
   }
   else if(document.CreateTFQues.Question.value.length>200)
   {
     alert("Question should not exceed over 200 alphabets !");
     document.CreateTFQues.Question.focus();
   }
   else
   {
     document.CreateTFQues.submit();
   }
 }
 
function cancel(doEvent)
{
  document.CreateTFQues.EVENTNAME.value=doEvent;
  document.CreateTFQues.submit();
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

      <FORM id="CreateTFQues" name="CreateTFQues" action="/ORS/AdminController">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <INPUT type=hidden name="Ques_Id" value='<%= Ques_Id %>'>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=left width=648 height=196>
          <TABLE name="Heading" cellSpacing=1 cellPadding=1 width="90%" border=0 align="center">
           <TR><TD class="Title1">&nbsp;&nbsp;&nbsp;Create Question: True or False</TD></TR>
           <TR><TD>&nbsp;</TD></TR>

           <c:if test="<%= (exception != null) %>">
             <TR><TD class="LabelA" style="color:#FF3333" colSpan=3>
              Error : <%= exception.getMessage() %><BR><%= exception.getSolution() %>
             </TD><TD></TD></TR>
			 <TR><TD><HR></TD><TD><HR></TD></TR>
           </c:if>

          <TR><TD class="h2">Enter your <strong>true/false</strong> statement below, then 
          indicate whether that statement is <strong><i>true </i></strong>or<strong> <i>false</i></strong></TD></TR>
           <TR><TD>&nbsp;</TD></TR>
           <TR><TD><P><FONT class="LabelA">Question</FONT><FONT class="Title3"> : <i>Type your question in the textArea below</i></FONT></TD></TR>
           <TR><TD>&nbsp;</TD></TR>
           <TR><TD><TEXTAREA name="Question" cols="45" rows="6" maxLength=80 style="width:400 " class="breadcrumbs"><% if(tf_question != null) out.print(tf_question.getQuestion()); %></TEXTAREA></TD></TR>
           <TR><TD>&nbsp;</TD></TR>
           <TR><TD><FONT class="LabelA">Answer:</FONT> : <FONT class="Title3"><i>Please Check On The Correct Answer Below</i></TD></TR>
           <TR><TD align="center">
		   <% 
             if(tf_question != null && tf_question.getCorrectAns() == 1)
             {
               out.println("<INPUT name=\"Ans\" type=\"radio\" value=\"1\" checked>&nbsp;&nbsp;True");
               out.println("<INPUT name=\"Ans\" type=\"radio\" value=\"2\">&nbsp;&nbsp;False");
             }
             else if(tf_question != null && tf_question.getCorrectAns() == 2)
             {
               out.println("<INPUT name=\"Ans\" type=\"radio\" value=\"1\">&nbsp;&nbsp;True");
               out.println("<INPUT name=\"Ans\" type=\"radio\" value=\"2\" checked>&nbsp;&nbsp;False");
             }
             else
             {
               out.println("<INPUT name=\"Ans\" type=\"radio\" value=\"1\">&nbsp;&nbsp;True");
               out.println("<INPUT name=\"Ans\" type=\"radio\" value=\"2\">&nbsp;&nbsp;False");
             }
           %></TD></TR>
           <TR><TD>&nbsp;</TD></TR>
           <TR><TD class="LabelA">Select Category : &nbsp;&nbsp;<SELECT name="SelCategory" class="breadcrumbs">

            <% String query = "select Category_Id, Name from category"; %>
            <% if(tf_question != null)
               {
            %>
                <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= tf_question.getCategory_Id() %>" />
            <% }
               else
               {
            %>
                <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" />
            <% } %>

           </SELECT></TD></TR>
          </TABLE>
          <BR><BR>
          <TABLE name="MC_Buttons" cellSpacing=1 cellPadding=1 width="55%" border=0>
           <TR align="center">
             <TD width="7%">&nbsp;&nbsp;</TD>

          <% if(request.getParameter("EVENTNAME") != null && request.getParameter("EVENTNAME").equals("SELQUES_TF"))
             { %>
             <TD width="29%"><INPUT class="B1" name="Add" type="button" value="Add" onclick="validate('CREQSTF_CRE')"></TD>
             <TD width="36%"><INPUT class="B1" name="Reset" type="reset" value="Reset"></TD>
             <TD width="28%"><INPUT class="B1" name="Cancel" type="button" value="Cancel" onclick="cancel('CREQSTF_CL')"></TD>
          <% }
             else if(request.getParameter("EVENTNAME") != null)
             { %>
             <TD width="29%"><INPUT class="B1" name="Add" type="button" value="Add" onclick="validate('EDTQSTF_CRE')"></TD>
             <TD width="36%"><INPUT class="B1" name="Reset" type="reset" value="Reset"></TD>
             <TD width="28%"><INPUT class="B1" name="Cancel" type="button" value="Cancel" onclick="cancel('EDTQSTF_CL')"></TD>
          <% } %>
           </TR>
          </TABLE>
   </TABLE>
   </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>