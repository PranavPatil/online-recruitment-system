<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.Model.Test" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
   int Test_Id = -1;
   DataEntryException exception = null;
   Test test = null;
   
   try
   {
     String str = null,event = null;
     exception = (DataEntryException) request.getAttribute("Exception");
     System.out.println("Exception = " + exception);
     
     event = request.getParameter("EVENTNAME");
     
     if(event.equals("TESTMGT_EDT"))
     {
       str = request.getParameter("SelectTest");
       
       if(str != null)
         Test_Id = Integer.parseInt(str);
     }
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     Test_Id = -1;
     exception = null;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     Test_Id = -1;
   }
   
   System.out.println("Test_Id = " + Test_Id);
   
   if(exception != null)
   {
     test = new Test(request);
     System.out.println("fname = " + test.getName());
   }
   else if(Test_Id > 0)
   {
     ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
     test = new Test(Test_Id,conn);
     System.out.println("fname = " + test.getName());
   }
   else
     test = null;
 %>

<HTML>
<HEAD>
<TITLE>Create Test</TITLE>
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

function Verify(doEvent)
{
  document.CreateTest.EVENTNAME.value=doEvent;

  if(document.CreateTest.TestName.value=="")
  {
    alert("Please Give the Test Name !");
    document.CreateTest.TestName.focus();
  }
  else if(document.CreateTest.TestDesc.value=="")
  {
    alert("Please Give the Test Description !");
    document.CreateTest.TestDesc.focus();
  }
  else if(document.CreateTest.TestDesc.value.length > 500)
  {
    alert("Test Description should not exceed 500 words !");
    document.CreateTest.TestDesc.focus();
  }
  else if(document.CreateTest.Ques_Type.disabled == false & document.CreateTest.Ques_Type.value=="")
  {
    alert("Please Give the Question Type !");
    document.CreateTest.Ques_Type.focus();
  }
  else if(document.CreateTest.Hide.value=="")
  {
    alert("Please Select whether to Hide the Timer !");
    document.CreateTest.Hide.focus();
  }
  else if(document.CreateTest.TestTime.value=="")
  {
    alert("Please Give the Test Time !");
    document.CreateTest.TestTime.focus();
  }
  else if(isNaN(document.CreateTest.TestTime.value))
  {
    alert("Test Time is invalid !");
    document.CreateTest.TestTime.focus();
  }
  else if(document.CreateTest.TestTime.value > 180)
  {
    alert("Test Time should not exceed 3 Hours !");
    document.CreateTest.TestTime.focus();
  }
  else if(document.CreateTest.Total_Ques.value=="")
  {
    alert("Please Give the Total Number of Questions !");
    document.CreateTest.Total_Ques.focus();
  }
  else if(isNaN(document.CreateTest.Total_Ques.value))
  {
    alert("Total Number of Questions are invalid !");
    document.CreateTest.Total_Ques.focus();
  }
  else if(document.CreateTest.MinPassScore.value=="")
  {
    alert("Please Give the Minimum Passing Score !");
    document.CreateTest.MinPassScore.focus();
  }
  else if(isNaN(document.CreateTest.MinPassScore.value))
  {
    alert("Minimum Passing Score is invalid !");
    document.CreateTest.MinPassScore.focus();
  }
  else if(document.CreateTest.Negative.value=="")
  {
    alert("Please Select whether Negative Marking !");
    document.CreateTest.Negative.focus();
  }
  else
  {
    document.CreateTest.submit();
  }
}

function funcHide(form)
{
  if(document.CreateTest.TestType[0].checked)
  {
    document.CreateTest.Ques_Type.disabled=true;
    document.CreateTest.Ques_Type.value="Disabled";
  }
  else 
  {
    document.CreateTest.Ques_Type.disabled=false;
  }
}

function cancel(doEvent)
{
  document.CreateTest.EVENTNAME.value=doEvent;
  document.CreateTest.submit();
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

      <FORM ID="CreateTest" NAME="CreateTest" ACTION="/ORS/AdminController" method="post">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <INPUT name='Test_Id' type='hidden' value = '<%= Test_Id %>'>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE cellSpacing=1 cellPadding=1 width="90%" border=0 align="center">
          <TR align="center">
             <TD class="Title1">&nbsp;Test Management:  Create a new test</TD></TR>
          <TR align="center"><TD>&nbsp;</TD></TR>

          <c:if test="<%= (exception != null) %>">
             <TR><TD class="LabelA" style="color:#FF3333" colSpan=3>
              Error : <%= exception.getMessage() %><BR><%= exception.getSolution() %>
			 <HR></TD></TR>
          </c:if>

          <TR>
             <TD class="h2">Test Management allows you to create tests for all the Categories.
             <BR>You can create categories and questions before or after creating the test.
             <BR>The Test here is actually the test pattern which allows you to decide various settings about the test.
             <BR>The test pattern can also be used to change the difficulty level as per the requirements of the Manager.</TD></TR>
          <TR><TD class="h1"><BR>The topics listed below are included in modules used in this course. By associating test questions with a topic.</TD></TR>
          <TR align="center"><TD>&nbsp;</TD></TR>
          <TR align="left"><TD class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Test Name&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <INPUT name="TestName" maxLength=45 size=35 style="WIDTH: 268px;" value='<% if(test!=null) out.print(test.getName()); %>' class="breadcrumbs"></TD></TR>
          <TR align="center"><TD>&nbsp;</TD></TR>
          <TR align="left"><TD class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Test Description<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <TEXTAREA name="TestDesc" maxlength="35" style="WIDTH: 352px; HEIGHT: 62px" rows=6 cols=53 class="breadcrumbs"><% if(test!=null) out.print(test.getDescription()); %></TEXTAREA></TD></TR>
          <TR align="center"><TD>&nbsp;</TD></TR>
          <TR align="left"><TD>
             <FONT class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Question Type</FONT>&nbsp;&nbsp;&nbsp;&nbsp;
             <FONT face="Times New Roman">
             <% 
                if(test != null && test.getTestType())
                {
                 out.println("<INPUT NAME=\"TestType\" TYPE=\"radio\" VALUE=\"0\" onclick=\"funcHide(this.form)\">&nbsp;&nbsp;<font class=\"LabelA\">Random</font>");
                 out.println("&nbsp;&nbsp;&nbsp;&nbsp;<INPUT NAME=\"TestType\" TYPE=\"radio\" CHECKED VALUE=\"1\" onclick=\"funcHide(this.form)\">&nbsp;&nbsp;<font class=\"LabelA\">Select Type</font>&nbsp;");
                 out.println("<SELECT name=\"Ques_Type\" class=\"breadcrumbs\">");
                }
                else
                {
                 out.println("<INPUT NAME=\"TestType\" TYPE=\"radio\" CHECKED VALUE=\"0\" onclick=\"funcHide(this.form)\">&nbsp;&nbsp;<font class=\"LabelA\">Random</font>");
                 out.println("&nbsp;&nbsp;&nbsp;&nbsp;<INPUT NAME=\"TestType\" TYPE=\"radio\" VALUE=\"1\" onclick=\"funcHide(this.form)\">&nbsp;&nbsp;<font class=\"LabelA\">Select Type</font>&nbsp;");
                 out.println("<SELECT name=\"Ques_Type\" class=\"breadcrumbs\" DISABLED>");
                }
                
                if(test != null)
                {
                  if(test.getQuesType() == null)
                  {
                    out.println("<OPTION value='MC'>Multiple Choice</OPTION>");
                    out.println("<OPTION value='TF'>True/False</OPTION>");
                    out.println("<OPTION value='MA'>Multiple Answer</OPTION>");
                  }
                  else if(test.getQuesType().equalsIgnoreCase("TF"))
                  {
                    out.println("<OPTION Selected>-- Select --</OPTION>");
                    out.println("<OPTION value='MC'>Multiple Choice</OPTION>");
                    out.println("<OPTION value='TF'>True/False</OPTION>");
                    out.println("<OPTION value='MA'>Multiple Answer</OPTION>");
                  }
                  else if(test.getQuesType().equalsIgnoreCase("MA"))
                  {
                    out.println("<OPTION value='MC'>Multiple Choice</OPTION>");
                    out.println("<OPTION value='TF'>True/False</OPTION>");
                    out.println("<OPTION value='MA' Selected>Multiple Answer</OPTION>");
                  }
                  else
                  {
                    out.println("<OPTION value='MC' Selected>Multiple Choice</OPTION>");
                    out.println("<OPTION value='TF'>True/False</OPTION>");
                    out.println("<OPTION value='MA'>Multiple Answer</OPTION>");
                  }
                }
                else
                {
                  out.println("<OPTION Selected>-- Select --</OPTION>");
                  out.println("<OPTION value='MC'>Multiple Choice</OPTION>");
                  out.println("<OPTION value='TF'>True/False</OPTION>");
                  out.println("<OPTION value='MA'>Multiple Answer</OPTION>");
                }
             %>

             </SELECT></FONT></TD></TR>
          <TR align="center"><TD>&nbsp;</TD></TR>
		  </TABLE>
             <TABLE id="TABLE1" cellSpacing=1 cellPadding=1 width="90%" align="center" border=0 style="WIDTH: 80%">
              <TR>
                 <TD class="LabelA">Hide Timer</TD>
                 <TD style="WIDTH: 10%" width="10%">
                 <SELECT name="Hide" style="WIDTH: 65px" class="breadcrumbs">
              <% 
                 if(test == null)
                 {
                   out.println("<OPTION selected>Select<OPTION value=1>Yes</OPTION><OPTION value=0>No</OPTION>");
                 }
                 else if(test.getHide())
                 {
                   out.println("<OPTION value=1  Selected>Yes</OPTION><OPTION value=0>No</OPTION>");
                 }
                 else
                 {
                   out.println("<OPTION value=1>Yes</OPTION><OPTION value=0 Selected>No</OPTION>");
                 }
              %>
                 </SELECT></TD>
                 <TD style="WIDTH: 35%" width="35%" class="LabelA">Maximum Test Time&nbsp;</TD>
                 <TD style="WIDTH: 10%" width="10%">
 
                 <INPUT name="TestTime" maxLength=3 size=5 type="integer" style="LEFT: 0px;" value='<% if(test != null) out.print(test.getTime()); %>' class="breadcrumbs">&nbsp;<FONT face="Times New Roman">mins</FONT></TD></TR>
              <TR>
                 <TD style="WIDTH: 35%" width="35%" class="LabelA">Total Questions&nbsp;</TD>
                 <TD><INPUT name="Total_Ques" maxLength=3 size=4 style="WIDTH: 65px;" value='<% if(test != null) out.print(test.getTotalQuestion()); %>' class="breadcrumbs"></TD>
                 <TD></TD>
                 <TD></TD></TR>
              <TR>
                 <TD class="LabelA">Scrolling allowed</TD>
                 <TD> 
                 <SELECT id="Scrolling" name="Scrolling" style="WIDTH: 65px" class="breadcrumbs">

                 <% 
                     if(test != null && test.getScrolling() == false)
                     {
                       out.println("<OPTION value = 0 SELECTED>No");
                       out.println("<OPTION value = 1>Yes</OPTION>");
                     }
                     else
                     {
                       out.println("<OPTION value = 1 SELECTED>Yes");
                       out.println("<OPTION value = 0>No</OPTION>");
                     }
                 %>

                 </SELECT></TD>
                 <TD></TD>
                 <TD></TD></TR>
              <TR>
                 <TD class="LabelA">Min passing score</TD>
                 <TD style="WIDTH: 15%" width="15%" nowrap>
                 <INPUT name="MinPassScore" maxLength=2 size=2 style="WIDTH: 65px;" value='<% if(test != null) out.print(test.getPassScore()); %>' class="breadcrumbs"><FONT class="h1"> (%)</FONT></TD>
                 <TD></TD>
                 <TD></TD></TR>
              <TR>
                 <TD class="LabelA">Negative Marking</TD>
                 <TD>
                 <SELECT id="Negative" name="Negative" style="WIDTH: 65px" class="breadcrumbs">
               <% 
                  if(test != null)
                  {
                    if(test.getNegative())
                    {
                      out.println("<OPTION value = 1 SELECTED>Yes");
                      out.println("<OPTION value = 0>No</OPTION>");
                    }
                    else
                    {
                      out.println("<OPTION value = 0 SELECTED>No");
                      out.println("<OPTION value = 1>Yes</OPTION>");
                    }
                  }
                  else
                  {
                    out.println("<OPTION Selected>Select");
                    out.println("<OPTION value = 1>Yes");
                    out.println("<OPTION value = 0>No</OPTION>");
                  }
               %>
                 </SELECT></TD>
                 <TD></TD>
                 <TD></TD></TR></TABLE>
             <BR><BR><BR>
             <CENTER>
               <INPUT class="B1" name="Ok" type="button"  value="Proceed" onClick="Verify('CRETEST_OK')" size=24>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
               <INPUT class="B1" name="btnCancel" type="button" value=" Cancel " onClick="cancel('CRETEST_CL')" size=21>
             </CENTER>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>