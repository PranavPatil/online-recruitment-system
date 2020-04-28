<HTML><HEAD><TITLE>User Login</TITLE>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="SSCL HomePage" name=author>
<LINK href="/ORS/Web/images/links.css" type=text/css rel=stylesheet>
<META content="MSHTML 6.00.2800.1276" name=GENERATOR>
<script language=javascript>

 function verify(doEvent)
 {
  if(document.UserLoginPage.User_Name.value=="")
  {
    alert("Please enter your Login Id.");
    document.UserLoginPage.User_Name.focus();
  }  
  else if(document.UserLoginPage.User_Passwd.value=="")
  {
    alert("Please enter your Password.");
    document.UserLoginPage.User_Passwd.focus();
  }  
  else
  {
    document.UserLoginPage.USEREVENT.value=doEvent;
    document.UserLoginPage.submit();
  }
 }
       
</script>
</HEAD>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50>
        <TBODY></TBODY></TABLE><IMG 
      height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 
      align=top>
      <jsp:include page="Header.jsp" flush="true"/>
      <TABLE height=393 cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
          <jsp:include page="Panel.jsp" flush="true"/>
            <IMG height=40 alt="" 
            src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
            <DIV align=left>
            <TABLE height=94 cellSpacing=0 cellPadding=0 width=648 border=0>
              <TBODY>
              <TR>
                <TD vAlign=center align=left><IMG height=93 
                  alt="Solutions Areas" 
                  src="/ORS/Web/images/banner_sa1.v1.gif" width=625 
                  align=right></TD></TR></TBODY></TABLE><A 
            name=content></A>
            <TABLE height=10 cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD height=5>&nbsp;</TD></TR>
              <TR>
                <TD height=5><IMG height=20 alt=Recruitment 
                  src="/ORS/Web/images/recruitment.gif" 
              width=625></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD vAlign=top align=left width="58%">
                  <P><BR><SPAN class=h1>Speed up the recruitment process</SPAN> 
                  <BR><SPAN class=h2>VWI has worked on Human Resource systems to 
                  speed up the assessment process for new recruits. We have 
                  developed two key systems to enable you to rapidly review and 
                  assess candidates. </SPAN><BR><BR><SPAN class=h1>Dynamic 
                  Assessment systems</SPAN> <BR><SPAN class=h2>VWI has developed 
                  a dynamic assessment system that allows you to show the 
                  candidate short situation based videos or presentations from 
                  which they can then be assessed <BR><BR><BR>VWI has also 
                  developed a series of assessment and testing tools to hep you 
                  rapidly create online assessment areas to speed up your 
                  decision process</SPAN> <BR><BR><SPAN 
                  class=h1>RecruitTalk</SPAN><BR><SPAN class=h2>RecruitTalk is a 
                  fantastic way to send recordings of internal interviews or 
                  assessments rapidly and securely. The system allows you to 
                  either record live interviews or to get the interviewee to 
                  record their own interview. <BR></SPAN><BR><BR></P></TD>
                <TD vAlign=top align=center width="2%"><TD>
                <TD vAlign=top align=right width="40%">
               <FORM name="UserLoginPage" id='UserLoginPage' action="/ORS/UserController" method="get">

               <P><input name="USEREVENT" type="hidden">&nbsp;</P>
               <P align=left><SPAN class=h1>For New Users:</SPAN></P>
               <P align=left><SPAN class=h2><A href="/ORS/Web/UserRegPage.jsp">Click</A> for new registration.</SPAN></P>
               <P align=left><SPAN class=h1>For Existing Users:</SPAN></P>
               <P align=left><SPAN class=h2>Please enter your login name and password to apply for the desired position.</SPAN></P>
          	
              <TABLE cellSpacing=0 border=0 cellPadding=3 width="45%" align=center>
              <TR>
                <TD><SPAN class=h2>Login Name:</SPAN></TD>
                <TD><INPUT name="User_Name" maxlength="25"></TD></TR>
              <TR>
                <TD><SPAN class=h2>Password:</SPAN></TD>
                <TD><INPUT name="User_Passwd" type='password' maxlength="25"></TD></TR>
              <TR>
                <TD></TD><TD></TD></TR>
              <TR>
                <TD></TD>
                <TD align="center"><INPUT name='ULogin' type="button" value='Login' onClick="verify('USLOGIN')" style="MARGIN-TOP: 2ex">
                    <INPUT type="reset" value='Reset'></TD>
              </TR>
           </TABLE>
          </FORM>
				</TD>
              </TR></TBODY></TABLE></DIV></TD>
          </TR>
    </TBODY></TABLE>
    <jsp:include page="Footer.jsp" flush="false"/>
  </TD></TR></TBODY></TABLE>
  </BODY></HTML>
