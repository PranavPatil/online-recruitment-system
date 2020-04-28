<%@ page contentType="text/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Exception.ApplicationException" %>

<HTML>
<HEAD>
<TITLE>Test Rules</TITLE>
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
  document.TestRules.USEREVENT.value=doEvent;
  document.TestRules.submit();
}

</script>
</HEAD>
<BODY>
 <jsp:include page="UserHeader.jsp" flush="true"/>
 <FORM NAME="TestRules" ID="TestRules" ACTION="/ORS/UserController" method="post">
 <input name="USEREVENT" type="hidden">
 <TABLE style="WIDTH: 760px; HEIGHT: 22px" cellSpacing=1 cellPadding=5 width=760 align=center border=0>
 <TR bgcolor="#abcdef" height=7><TD align="middle" colspan="2" ><FONT size=3 face=Arial>
 <STRONG>Test Rules</STRONG></FONT></TD></TR>
 </TABLE>
 <br><br><CENTER><TEXTAREA name=TestDesc rows=14 cols=75 readOnly warp="off">
 <%
    int Post_Id = 0,Cat_Id = 0,Test_Id = 0;
    long User_Id = 0;
    String Desc1 = null,Desc2 = null,Desc3 = null;

    session = request.getSession(false);

    User_Id = (Long)session.getAttribute("User_Id");
    if(User_Id == 0)
    {
      System.out.println("Error in Session !!");
    }
     
    Post_Id = (Integer)session.getAttribute("Post_Id");
    if(Post_Id == 0)
    {
      System.out.println("Error in Session !!");
      //file.writeLog(0,"Error in Post Session");
    }

    Cat_Id = (Integer)session.getAttribute("Cat_Id");
    if(Cat_Id == 0)
    {
      System.out.println("Error in Session !!");
      //file.writeLog(0,"Error in Post Session");
    }

    Test_Id = (Integer)session.getAttribute("Test_Id");
    if(Test_Id == 0)
    {
      System.out.println("Error in Session !!");
      //file.writeLog(0,"Error in Post Session");
    }
    
    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool");

    Database db = new Database(pool);

	ResultSet rs=null;

    System.out.println("Cat_Id ================================ " + Cat_Id);

    rs = db.RetriveDb("Select Name,Description from Category where Category_Id = " + Cat_Id  );
    
    if(rs.next())
    {
      Desc1 = rs.getString(1);
      Desc2 = rs.getString(2);
    }
    
    rs = null;
    rs = db.RetriveDb("Select Description from Test where Test_Id = " + Test_Id);
    
    if(rs.next())
     Desc3 = rs.getString(1);
    
    out.println("1 : " + Desc1 + " Test.");
    out.println("");
    out.println("2 : " + Desc2 + ".");
    out.println("");
    out.println("3 : " + Desc3 + ".");
    out.println("");
    out.println("4 : The Selection of the students will be finalized by respective " +
                "authority and will be bound to all the candidates." );
    out.println("");
    out.println("5 : Candidates will be exempted from the Exam if they try to copy " +
                "or tried to hamper the System." );
    out.println("");
    out.println("6 : In case of crash candidates will be allowed to reappear for " +
                "the Exam." );
    out.println("");
    out.println("7 : Fraud candidates will not be entertained at the interview. ");
    out.println("");
    out.println("8 : Only selected candidates will be informed about the interview " + 
                "through letter or by telephone within 10 days." );
 %>
 </TEXTAREA></CENTER>
 <BR>
 <TABLE style="WIDTH: 460px; HEIGHT: 32px" cellSpacing=1 cellPadding=1 width=460 align=center border=0>
 <TBODY>
  <TR>
   <TD noWrap align=right><INPUT name='IAccept' type='button' value='I Accept' onClick="Button('RULESACC')" style="WIDTH: 101px; HEIGHT: 26px" size=26>
   </TD></FORM>
   <TD noWrap>&nbsp;&nbsp;&nbsp;&nbsp;<INPUT name=IDecline type='button' value="I Decline" onClick="Button('USAPPLY')" style="WIDTH: 99px; HEIGHT: 27px" size=25>
   </TD></TR></FORM></TBODY></TABLE>
   <br>
 <jsp:include page="UserFooter.jsp" flush="false"/>
 </BODY>
 </HTML>