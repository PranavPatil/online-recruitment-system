<%@ page contentType="text/html" %>

<html>
<head>
<title>Post View</title>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META content="" name=keywords>
<META content="Current Openings" name=author>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
</head>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="UserHeader.jsp" flush="true"/>
      <BR>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="UserPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE cellSpacing=1 cellPadding=1 width="100%" align=center border=0 id=TABLE1>
            <TR vAlign=top align=right width=648>
             <TD colSpan=6>            
			  <TABLE height=10 cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD height=5><IMG height=20 alt=Recruitment src="/ORS/Web/images/recruitment_procedure.gif" width=625>
				</TD>
              </TR></TBODY></TABLE>
	  </TD></TR></TABLE>
   <jsp:include page="UserFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>