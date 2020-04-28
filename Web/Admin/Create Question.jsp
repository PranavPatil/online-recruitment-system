<%@ page contentType="text/html;charset=windows-1252" language="java" %>

<HTML>
<HEAD>
<TITLE>Add A New Test Question</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript">

function Button(doEvent)
{
  document.AddNewQuestions.EVENTNAME.value=doEvent;
  document.AddNewQuestions.submit();
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

      <FORM ID="AddNewQuestions" NAME="AddNewQuestions" ACTION="/ORS/AdminController">
      <INPUT TYPE="hidden" name="EVENTNAME">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=left width=648 height=196>
          <TABLE cellSpacing=1 cellPadding=1 width="96%" align='center' border=0>
          <TR Align="center">
           <TD class="Title1">Create A New Question</TD>
           </TR>
		  <TR><TD>&nbsp;</TD></TR>
          <TR><TD class="Title3">Please select the question type by clicking on icon below:</TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD class="Title3"><A class="HREF" onclick="Button('SELQUES_MC')"><IMG src="/ORS/Web/images/multi_choice_icon.gif" align=bottom border=0></A> 
           &nbsp;&nbsp;  Multiple Choice<BR></TD></TR>
          <TR><TD class="Title3"><A class="HREF" onclick="Button('SELQUES_MA')"><IMG src="/ORS/Web/images/multi_answer_icon.gif" align=bottom border=0></A> 
           &nbsp;&nbsp;  Multiple Answer<BR></TD></TR>
          <TR><TD class="Title3"><A class="HREF" onclick="Button('SELQUES_TF')"><IMG src="/ORS/Web/images/true_false_icon.gif" align=bottom border=0></A> 
           &nbsp;&nbsp;  True/False<BR></TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD>&nbsp;&nbsp;&nbsp;<INPUT class="B1" id="Back" name="Back" type="button" value="Click after you are done with adding questions"  onclick="Button('SELQUES_BK')">&nbsp;</TD></TR>
		   </TABLE>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>