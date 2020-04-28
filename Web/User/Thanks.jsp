<HTML>
<HEAD>
<TITLE>Exam End</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="pragma" content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<LINK href="/ORS/Web/images/links.css" type="text/css" rel="stylesheet">
<LINK href="/ORS/Web/images/Style.css"   type="text/css"  rel="stylesheet">
<script language=javascript>

function Button(doEvent)
{
  document.EndExam.USEREVENT.value=doEvent;
  document.EndExam.submit();
}

</script>
</HEAD>
<BODY>
  <jsp:include page="UserHeader.jsp" flush="true"/>
  <P>&nbsp;</P>
  <h2 align="center">Congratulations</h2>
  <P>&nbsp;</P>
  <STRONG>
  <ul>
  <li>&nbsp;&nbsp; We thank you for giving this Exam. 
  <li>&nbsp;&nbsp; The results will be mandatory to all the Candidates.
  <li>&nbsp;&nbsp; Candidates with suitable criteria and good performance will be interviewed.
  <li>&nbsp;&nbsp; The candidates selected for the interview will be informed via email.
  <li>&nbsp;&nbsp; If you do not get an email then please check your profile.
  <li>&nbsp;&nbsp; Thanking you once again for applying in our company&nbsp;!!
  </li></ul>
  </STRONG>
  <P>&nbsp;</P>
  <FORM ID="EndExam" NAME="EndExam" ACTION="/ORS/UserController">
  <input name="USEREVENT" type="hidden">
  <TABLE id="Heading" cellPadding=0 cellSpacing=1 border=0 width="50%" align="center">
  <TR>
  <TD><INPUT name ="Continue" type='button' value="Continue" size=13 onClick="Button('FIRSTPAGE')" style="WIDTH: 150px; HEIGHT: 27px">
  </TD>
  <TD><INPUT name ="Logout" type='button' value="Logout" size=13 onClick="Button('USLOGOUT')" style="WIDTH: 150px; HEIGHT: 27px">
  </TD>
  </TR>
  </TABLE>
  </FORM>
  <P>&nbsp;</P>
  <jsp:include page="UserFooter.jsp" flush="false"/>
  </BODY>
  </HTML>