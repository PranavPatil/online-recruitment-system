<HTML><HEAD><TITLE>VWI - Client Area</TITLE>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="VWI LIMITED" name=author>
<META content="" name=keywords>
<META content="vwi is a communications consultancy developing solutions for e-learning, training, collaborative software, multimedia design and project management." name=description>
<LINK href="/ORS/Web/images/links.css" type="text/css" rel="stylesheet">
<META content="MSHTML 6.00.2800.1106" name=GENERATOR>
<script language="javascript">
        
   function verify(doEvent)
   {
     if(document.LoginPage.Admin_Name.value=="")
     {
       alert("Please enter your Login Id.");
       document.LoginPage.Admin_Name.focus();
     }

     else if(document.LoginPage.Admin_Passwd.value=="")
     {
       alert("Please enter your Password.");
       document.LoginPage.Admin_Passwd.focus();
     }
     else
     { 
       document.LoginPage.EVENTNAME.value=doEvent;
       document.LoginPage.submit();
     }
   }
        
   function cancel(form)
   {
     parent.close();
     window.close(); 
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
      height=4 alt="" src="/ORS/Web/imagesVWI - Client Area_files/white-spacer.gif" width=732 
      align=top> 
      <jsp:include page="Header.jsp" flush="true"/>
      <TABLE height=393 cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
          <jsp:include page="Panel.jsp" flush="true"/>
          <IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
            <DIV align=left>
            <TABLE height=94 cellSpacing=0 cellPadding=0 width=648 border=0>
              <TBODY>
              <TR>
                <TD vAlign=center align=left><IMG height=94 alt="Client Area" 
                  src="/ORS/Web/images/banner_aa.v4.gif" width=625 
                  align=right></TD>
              </TR></TBODY></TABLE><A 
            name=content></A>
            <TABLE height=40 cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD>&nbsp;</TD></TR>
              <TR>
                <TD height=20>&nbsp;</TD></TR></TBODY></TABLE>
            <P style="MARGIN-BOTTOM: 0px"><IMG height=10 alt="" 
            src="/ORS/Web/images/white-spacer.gif" width=625></P>
            <P class=body3 style="MARGIN-TOP: 0px; MARGIN-BOTTOM: 0px">
			<FORM name="LoginPage" id="LoginPage" action="/ORS/AdminController" method="post">
			<input type="hidden" name="EVENTNAME">
            <TABLE cellSpacing=0 cellPadding=6 align=center>
              <TBODY>
              <TR bgColor=#999999>
                <TD class=dataroomBorderLT align=right><LABEL 
                  class=dataroomBold for=login>Username:</LABEL></TD>
                <TD class=dataroomBorderRT><INPUT class=dataroom id=login 
                  name="Admin_Name"></TD></TR>
              <TR bgColor=#999999>
                <TD class=dataroomBorderLB align=right><LABEL 
                  class=dataroomBold for=password>Password:</LABEL></TD>
                <TD class=dataroomBorderRB><INPUT class=dataroom id=password 
                  type=password name="Admin_Passwd"></TD></TR>
              <TR>
                <TD align=right colSpan=2><INPUT class=dataroom type=button value=OK  onClick="verify('ADLOGIN_OK')">
                </TD></TR></TBODY>
            </TABLE></FORM></P>
            <TABLE cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD vAlign=top align=left width=596><BR></TD>
                <TD vAlign=top align=right width=250></TD>
              </TR></TBODY></TABLE>
              </DIV></TD>
              <jsp:include page="Footer.jsp" flush="false"/>
          </TBODY></TABLE></TD></TR></TBODY></TABLE></BODY></HTML>