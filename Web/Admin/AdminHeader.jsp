<%--
 * @author  Pranav
 * @version 1.4
 *
 * Development Environment : JCreator (JDK 1.5)
 * 
 * Name of the File : Header.jsp
 *
 * Creation/Modification History  :
 *    Pranav     27-Nov-2006      Created
 *
 * Overview of Application        :
 *
 *   This page is part of the ORS application.
 *   This page displays the Header information.
 *   This is included in the other JSPs to display the footer information.
 *
--%>
<%-- Set Context type and characterset --%>

<%@page contentType="text/html;charset=WINDOWS-1252" language="java" %>

<TABLE id=header cellSpacing=0 cellPadding=0 width=840 align=center 
      border=0>
        <TBODY>
        <TR vAlign=center align=left>
          <TD style="BACKGROUND-IMAGE: url(images/gradient1.jpg)" noWrap 
          width="74%" bgColor=#ffffff height=60>
            <P align=left><A href="http://www.vwi-media.com/index.htm"><IMG 
            height=60 alt="V W I" hspace=5 src="/ORS/Web/images/logo.gif" 
            width=60 border=0></A> </P></TD>
          <TD align=right width="25%" bgColor=#ffffff>
            <P><IMG height=65 alt="Three images of designers at work." hspace=0 
            src="/ORS/Web/images/imagestrip.jpg" width=210 align=right></P></TD>
        <TR>
          <TD colSpan=2><A accessKey=s 
            href="http://www.vwi-media.com/recruitment.htm#content"><IMG 
            height=2 alt="skip to page content" 
            src="/ORS/Web/images/white-spacer.gif" width=100 border=0></A></TD>
        <TR vAlign=top align=left>
          <TD vAlign=center bgColor=#cccccc colSpan=6>
            <TABLE id=location cellSpacing=0 cellPadding=0 width="52%" 
            align=left border=0>
              <TBODY>
              <TR>
                <TD vAlign=top align=left width=18>
                  <P>&nbsp;</P></TD>
                <TD class=link1 vAlign=center align=left width=424>Home &gt; 
            <STRONG>Admin Area </STRONG></TD>
              </TR></TBODY></TABLE></TD></TR>
        <TR>
          <TD vAlign=center align=left colSpan=4>
            <TABLE height=32 cellSpacing=0 cellPadding=0 width=849 border=0>
              <TBODY>
              <TR>
                <TD width=18>&nbsp;</TD>
                <TD width=831><A class=link3 accessKey=1 
                  href="/ORS/AdminController?EVENTNAME=FIRSTPAGE">Home |</A> <A 
                  class=link3 
                  href="/ORS/AdminController?EVENTNAME=ADMNACC_MN">Admin Accounts |</A> 
                  <A class=link3 
                  href="/ORS/AdminController?EVENTNAME=EXAMMGT_MN">Exam Management |</A> 
				  <A class=link3 
                  href="/ORS/AdminController?EVENTNAME=VWRSLT_MN">Candidate Selection |</A> 
				  <A class=link3 accessKey=2 
                  href="/ORS/AdminController?EVENTNAME=SYSLOG_MN">System |</A> 
				  <A class=link3 
				  href="/ORS/AdminController?EVENTNAME=ADHELP_MN">Help |</A> 
				  <A class=link3 accessKey=0 
                  href="/ORS/AdminController?EVENTNAME=ADCRDT_MN">Credits |</A>
				  <A class=link3 accessKey=3 
                  href="/ORS/AdminController?EVENTNAME=ADLOGOUT_MN"> Logoff |</A>
				  <A class=link3 accessKey=9 
                  href="http://www.vwi-media.com/contactus.htm">Contact Us |</A></TD>
              </TR></TBODY></TABLE>
		  </TD></TR></TBODY></TABLE>