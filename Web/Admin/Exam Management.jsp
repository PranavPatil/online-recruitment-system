<%@ page contentType="text/html;charset=windows-1252" language="java" %>

<HTML>
<HEAD>
<TITLE>Exam Management</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript">

function Button(doEvent)
{
  document.ExamManagement.EVENTNAME.value=doEvent;
  document.ExamManagement.submit();
}

function Change(value)
{
  document.images["Picture"].src = value;
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

      <FORM id="ExamManagement" name="ExamManagement" action="/ORS/AdminController">
      <INPUT TYPE="hidden" name="EVENTNAME">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="Image" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648>
          <TABLE name='Heading' cellSpacing=1 cols=4 cellPadding=1 width="96%" align='center' border=0>
          <TR Align="center">
            <TD style="VERTICAL-ALIGN: middle; TEXT-ALIGN: center" colSpan=4 class="Title1">Exam Management</TD>
          </TR>
          <TR><TD>&nbsp;</TD></TR>
          <TR><TD height="345">
           <TABLE name='Image' cellSpacing=1 cols=4 cellPadding=1 width="99%" align='center' border=0>
            <TR><TD width="31%" height="329">
             <TABLE name='Menu' width="98%" height="311" border=0 align='left' cellPadding=1 cellSpacing=1 cols=4 >
             <TR><TD colspan="4" align=left class="link10"><IMG src="/ORS/Web/images/navyblueball.gif" align=center border=0>
               <A class="HREF" onMouseOver="Change('/ORS/Web/images/Post.jpg')" onclick="Button('POSTMGT_MN')">
               <U>Post Management</U></A>
             </TD>
             </TR>
             <TR>
              <TD colspan="4" align=left class="link10">
              <IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
              <A class="HREF" onMouseOver="Change('/ORS/Web/images/Test.jpg')" onclick="Button('TESTMGT_MN')">
              <U>Test Management</U></A></TD>
             </TR>
             <TR>
              <TD colspan="4" align=left class="link10">
              <IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
              <A class="HREF" onMouseOver="Change('/ORS/Web/images/Publish.jpg')" onclick="Button('TESTPUB_MN')">
              <U>Publish Test</U></A></TD>
             </TR>
             <TR>
              <TD colspan="4" align=left class="link10">
              <IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
              <A class="HREF" onMouseOver="Change('/ORS/Web/images/Questions.jpg')" onclick="Button('QUESMGT_MN')">
              <U>Question Management</U></A></TD>
             </TR>
             <TR>
              <TD height="68" colspan="4" align=left class="link10">
              <IMG src="/ORS/Web/images/navyblueball.gif" align="middle" border=0>
              <A class="HREF" onMouseOver="Change('/ORS/Web/images/Category.jpg')" onclick="Button('CATMGT_MN')">
              <U>Category Management</U></A></TD>
             </TR>
             </TABLE>	
	        </TD>
	        <TD width="69%"><img name='Picture' src="/ORS/Web/images/Exam.jpg" width="421" height="311" border="1" align="right"></TD>
            </TR>
           </TABLE>
          </TD></TR>
        </TABLE>
      </TABLE>
     </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>