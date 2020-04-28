<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>  
<%@ page import = "java.io.*" %>  
<%@ page import = "java.util.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<% ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ; %>	

<HTML>
<HEAD>
<TITLE>View Selected Candidates</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="/ORS/Web/images/Sort.js"></SCRIPT>
<SCRIPT language="javascript">
  var skn; 

  function init()
  {
    skn=PrintButton.style;
    showPrint();
  }

  function printPage()
  {
    window.print();
  }

  function hidePrint()
  {
    skn.visibility="hidden";
  }

  function showPrint()
  {
    skn.visibility="visible";
  }
    
  function changeUid(count)
  {
    document.SelectedView.User_Id.value = count;
    document.SelectedView.target="resource window";
  }

  function Del(doEvent)
  {
    if(window.confirm("Are you sure you want to Delete the Selected Candidates. "))
    {
     document.SelectedView.EVENTNAME.value=doEvent;
     document.SelectedView.submit();
    }
  }
  
  function Employ(doEvent)
  {
    if(window.confirm("Are you sure you want to Recruit the Selected Candidates. "))
    {
      document.SelectedView.EVENTNAME.value=doEvent;
      document.SelectedView.submit();
    }
  }
  
  function Button(doEvent)
  {
    document.SelectedView.EVENTNAME.value=doEvent;
    document.SelectedView.submit();
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

      <FORM id="SelectedView" name="SelectedView" action="/ORS/AdminController" method="get">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>            
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <P>
          <TABLE id="TABLE1" name="TABLE1" cellSpacing=1 cellPadding=1 width="100%" border=0 align="center">
            <TR vAlign="center" align="middle">
   	        <TD class="Title1" colSpan="9">View Selected Candidates</TD></TR>
            <TR><TD colspan="9">&nbsp;</TD></TR>
            <TR>
    	    <TD></TD><TD class="Title3">Post:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	    <SELECT name="postname" style="WIDTH: 150px" class="breadcrumbs">
            <%
               int Post_Id = 0,var = 0;
    
               try
               {
                 Post_Id= Integer.parseInt(request.getParameter("postname"));
               }
               catch(NumberFormatException e)
               {
                 Post_Id = 0;
               }
    %>
    
        <% String query = "select Postname, Post_Id from post"; %>
        <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= Post_Id %>" />

    	    </SELECT></TD>
      	  <TD>
      	  <INPUT class="B1" name="btnGo" type="button" value=" Go ->" onclick="Button('VWSELD_MAL')">
    	  <INPUT name='Post_Id' type='hidden' value = '<%= Post_Id %>'>
      	  <INPUT name='User_Id' type='hidden' value = '0'>  
      	  </TD>
      	  </TR>
      	  <TR><TD colspan="9">&nbsp;</TD></TR>
          </TABLE>

          <TABLE id="rsTable" name="rsTable" cellSpacing=1 cellPadding=2 width="103%" border=0 cols=12 align="center">
          <TR>
            <TD bgColor="#abcdef" width="1%"></TD>
          	<TD bgColor="#abcdef" width="16%">
	        <P align=center><A href="javascript:sortTable(1, rsTable);"><STRONG><FONT face=Verdana size=1><B>User Name</B></FONT></STRONG></A></TD>
	        <TD bgColor="#abcdef" width="8%">
	        <P align=center><A href="javascript:sortTable(2, rsTable);"><STRONG><FONT face=Verdana size=1><B>Percent</B></FONT></STRONG></A></TD>
          	<TD bgColor="#abcdef" width="9%">
            <P align=center><A href="javascript:sortTable(3, rsTable);"><STRONG><FONT face=Verdana size=1>Attpt %</FONT></STRONG></A></P></TD>
          	<TD bgColor="#abcdef" width="6%">
            <P align=center><A href="javascript:sortTable(4, rsTable);"><STRONG><FONT face=Verdana size=1>Result</FONT></STRONG></A></P></TD>
	        <TD bgColor="#abcdef" width="9%">
            <P align=center><A href="javascript:sortTable(5, rsTable);"><STRONG><FONT face=Verdana size=1>Attpt No</FONT></STRONG></A></P></TD>
          	<TD bgColor="#abcdef" width="4%">
            <P align=center><A href="javascript:sortTable(6, rsTable);"><STRONG><FONT face=Verdana size=1>Age</FONT></STRONG></A></P></TD>
          	<TD bgColor="#abcdef" width="11%">
            <P align=center><A href="javascript:sortTable(7, rsTable);"><STRONG><FONT face=Verdana size=1>Qualification</FONT></STRONG></A></P></TD>
          	<TD bgColor="#abcdef" width="15%">
            <P align=center><A href="javascript:sortTable(8, rsTable);"><STRONG><FONT face=Verdana size=1>Branch</FONT></STRONG></A></P></TD>
          	<TD bgColor="#abcdef" width="10%">
            <P align=center><A href="javascript:sortTable(9, rsTable);"><STRONG><FONT face=Verdana size=1>Experience</FONT></STRONG></A></P></TD>
          	<TD bgColor="#abcdef" width="4%">
            <P align=center><A href="javascript:sortTable(10, rsTable);"><STRONG><FONT face=Verdana size=1>Select</FONT></STRONG></A></P></TD>
            <TD bgColor="#abcdef" width="7%"></TD>
           </TR>
   <%
     int Experience = 0;
     int User_Id = 0,count = 0;
     java.util.Date date=null;
	 
	 Calendar Cdate = Calendar.getInstance();
	 Calendar Bdate = Calendar.getInstance();
     
     if(Post_Id != 0)
     {
       Database db = new Database(pool);
       ResultSet TData = null,UData = null;
     
       query = "select User_Id,Email,Login,Date_of_Birth,Qualification,Branch,Experience,SelectNo from Users where  Selected > 0 and Employee = 0 and User_Id in" + 
               " (select User_Id from Result where Post_Id = " + Post_Id + ")";
       
       UData = db.RetriveDb(query);
     
       query = "select T_Percent,T_Attempt,T_Result,Attempt_No" + 
               " from Result where Post_Id = " + Post_Id; 
       
       TData = db.RetriveDb(query);
       
       try
       {
         while(UData.next() & TData.next())
         { 
		   User_Id = UData.getInt(1);
		   out.println("<TR>");
		   out.println("<TD><P align=center><input type='image' name='Details' src='/ORS/Web/images/navyblueball.gif' alt=\"View Result Details\" onclick='changeUid(\"" + User_Id + "\")'>");
		   out.println("</P></TD>");
		   out.println("<TD><P align=center><A href=\"mailto:" + UData.getString(2) + "\" class=h1 style=\"color:#0033FF\">");
		   out.println(UData.getString(3));
		   out.println("</P></TD>");
           
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(TData.getInt(1));
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(TData.getInt(2));
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(TData.getString(3));
		   out.println("</FONT></P></TD>");
           
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(TData.getInt(4));
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><P align=center><FONT class=h1>");
		   
		   date = UData.getDate(4);
		   Bdate.setTime(date);
		   int age = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR);
		   
		   out.println(age);
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(UData.getString(5));
		   out.println("</FONT></P></TD>");

		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(UData.getString(6));
		   out.println("</FONT></P></TD>");

		   out.println("<TD><P align=center><FONT class=h1>");
		   Experience = UData.getInt(7);

		   if(Experience == 5)
		   {
		     out.println("above 10yrs");
		   }
		   else if(Experience == 4)
		   {
		     out.println("5-10yrs");
		   }
		   else if(Experience == 3)
		   {
		     out.println("2-5yrs");
		   }
		   else if(Experience == 2)
		   {
		     out.println("1-2yrs");
		   }
		   else
		   {
		     out.println("Fresher");
		   }
		   
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(UData.getInt(8));
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><input type='checkbox' name='User_Id" + count + "' value = '" + User_Id + "'>");
           out.println("</TD>");
           out.println("</TR>");
           count++;
	     }
	   }
	   catch(Exception e)
	   {
	 	 out.println("Problem in Result Retrival :" + e.getMessage());
		 e.printStackTrace();
	   }
	 }
  %>       
  </TABLE>
  <TABLE cellSpacing=1 cellPadding=1 width="75%" align=center border=0 id=end>
	<TR>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
	</TR>
	<TR>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
		<TD vAlign="top" width="100">&nbsp;</TD>
	</TR>
    <TR align="center">
	  <TD><INPUT class="B1" type="button" name="UnSelection" value="  UnSelect  " size=25 onclick="Button('VWSELD_USL')"></TD>
      <TD><INPUT class="B1" type="button" name="Delete" value="  Delete  " size=25 onclick="Del('VWSELD_DEL')"></TD>
	  <TD><INPUT class="B1" type="button" name="Mail" value="  Mail  " size=25 onclick="Button('VWSELD_MAL')"></TD>
	  <TD><INPUT class="B1" type="button" name="Recruit" value="  Recruit  " size=25 onclick="Employ('VWSELD_REC')"></TD>
      <TD>
      <INPUT type=hidden name="Count" value="<%= count %>">
      <INPUT type='hidden' name='RequestUrl' value = 'SelectedView'>
      <div id="PrintButton">
      <dd><INPUT class="B1" type="button" name="print" value="  Print  " size=25 onClick="printPage()"> </dd>
      </div>
      </TD>
      </TR>
      </TABLE>
      <P></P><P></P><P></P></P>
      </TABLE>
   </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>