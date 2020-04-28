<%--
 * @author  Pranav
 * @version 1.4
 *
 * Development Environment : JCreator (JDK 1.5)
 * 
 * Name of the File : Panel.jsp
 *
 * Creation/Modification History  :
 *    Pranav     14-Dec-2006      Created
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

            <TABLE id=rightnav cellSpacing=0 cellPadding=0 width=188 align=left 
            border=0>
              <TBODY>
              <TR>
                <TD class=normalCell width=16 rowSpan=39>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" width=161 colSpan=2>
                  <P><A class=link4 
                  href="/ORS/AdminController?EVENTNAME=">Admin Accounts</A></P></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link5 
                  href="/ORS/AdminController?EVENTNAME=ADMNACC_MN" 
                  target=_self><IMG height=10 alt="" 
                  src="/ORS/Web/images/arrow.jpg" width=7 border=0> View Accounts</A></TD>
              </TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link5 
                  href="/ORS/AdminController?EVENTNAME=ACSSOP_MN" 
                  target=_self><IMG height=10 alt="" 
                  src="/ORS/Web/images/arrow.jpg" width=7 border=0> Accessibility Options</A></TD>
              </TR>
              <TR>
                <TD class=normalCell colSpan=2>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link4 href="/ORS/AdminController?EVENTNAME=EXAMMGT_MN" 
                  target=_self>Exam Management</A></TD>
              </TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=POSTMGT_MN" 
                  target=_self> Post Management</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=TESTMGT_MN" target=_self> 
                Test Management</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=TESTPUB_MN" target=_self> 
                  Publish Test</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=QUESMGT_MN" target=_self> 
                  Question Management</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link5 
                  href="/ORS/AdminController?EVENTNAME=CATMGT_MN" 
                  target=_self><IMG height=10 alt="" 
                  src="/ORS/Web/images/arrow.jpg" width=7 border=0> Category Management</A></TD></TR>
              <TR>
                <TD class=normalCell colSpan=2>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link4 
                  href="/ORS/AdminController?EVENTNAME=" 
                  target=_self>Candidate Processing</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=VWRSLT_MN" target=_self> View Results</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=VWSELD_MN" 
                  target=_self> View Selected</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=VWEMP_MN" target=_self> 
                  View Employees</A></TD></TR>
              <TR>
                <TD class=normalCell colSpan=2>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link4 href="/ORS/AdminController?EVENTNAME=SYSTEM_MN" 
                  target=_self>System Settings</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=SERVER_MN" 
                  target=_self> Server Management</A></TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><IMG 
                  height=10 alt="" src="/ORS/Web/images/arrow.jpg" width=7 
                  border=0><A class=link5 
                  href="/ORS/AdminController?EVENTNAME=SYSLOG_MN" 
                  target=_self> System Log</A></TD></TR>
              <TR>
                <TD class=normalCell colSpan=2>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link4 href="/ORS/AdminController?EVENTNAME=ADHELP_MN" 
                  target=_self>Help</A></TD></TR>
              <TR>
                <TD class=normalCell colSpan=2>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link4 href="/ORS/AdminController?EVENTNAME=ADCRDT_MN" 
                  target=_self>Credits</A></TD></TR>
              <TR>
                <TD class=normalCell colSpan=2>&nbsp;</TD></TR>
              <TR>
                <TD class=normalCell onmouseover="this.className='hoverCell';" 
                onmouseout="this.className='normalCell';" colSpan=2><A 
                  class=link4 href="/ORS/AdminController?EVENTNAME=ADLOGOUT_MN" 
                  target=_self>Logout</A></TD></TR>
              <TR>
                <TD class=normalCell colSpan=2 
            height=2></TD></TR></TBODY></TABLE>