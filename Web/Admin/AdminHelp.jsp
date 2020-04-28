<%@ page contentType="text/html;charset=windows-1252" language="java" %>

<HTML>
<HEAD>
<TITLE>ORS Help</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<LINK href="/ORS/Web/images/Styles.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=center width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="AdminHeader.jsp" flush="true"/>
      <BR>
      <TABLE cellSpacing=0 cellPadding=0 width=910 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>            
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=left width=648 height=1200>
		  <iframe name="Help" src="/ORS/Web/Help/index.html" frameborder="0" scrolling="auto" height="100%" width="100%"></iframe>
          <jsp:include page="AdminFooter.jsp" flush="false"/></TD>
		</TR>
	    </TBODY>
	  </TABLE>
	  </TD></TR>
</TBODY></TABLE>
</BODY></HTML>