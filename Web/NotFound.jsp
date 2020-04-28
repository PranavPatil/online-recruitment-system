<HTML>
<HEAD>
<TITLE>Admin View</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript">

function back(form)
{
	doc=open("http://localhost:1977/examples/Admin/UserUpdate.jsp","right1");
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
      <jsp:include page="/Web/Admin/Header.jsp" flush="true"/>
      <H2>Error!</H2>
      The cannot find a page that matches
      <%= request.getRequestURI() %> on the system. Maybe you should
      try one of the following:
      <UL>
        <LI>Go to the server's <A HREF="http://localhost:1977/ORS/">home page</A>.
        <LI>Search for relevant pages.<BR>
            <FORM ACTION="http://www.google.com/search">
            <CENTER>
            Keywords: <INPUT TYPE="TEXT" NAME="q"><BR>
            <INPUT TYPE="SUBMIT" VALUE="Search">
            </CENTER>
            </FORM>
        <LI>Admire a random multiple of 404:
            <%= 404*((int)(1000*Math.random())) %>.
        <LI>Try a <A HREF="http://www.plinko.net/404/rndindex.asp"
                     TARGET="_blank">
            random 404 error message</A>. From the amazing and
            amusing plinko.net <A HREF="http://www.plinko.net/404/">
            404 archive</A>.
      </UL>
   <jsp:include page="/Web/Admin/Footer.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>