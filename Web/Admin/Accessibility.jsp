<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<HTML>
<HEAD>
<TITLE>Accessibility Options</TITLE>
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

  function changeUid(count)
  {
    document.Accessibility.User_Id.value = count;
    document.Accessibility.EVENTNAME.value='VWRSLT_DET';
    //document.Accessibility.target="window";
  }
  
  function Button(doEvent)
  {
    document.Accessibility.EVENTNAME.value=doEvent;
    document.Accessibility.submit();
    //window.open("welcome.html", "" , "[resizable,scrollbars,dependent=true,,toolbar]" , "" );
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

      <FORM id="Accessibility" name="Accessibility" action="/ORS/AdminController" method="post">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

          <TABLE id="TABLE1" name="TABLE1" cellSpacing=1 cellPadding=1 width="90%" border=0 align="center">
          <TR vAlign="center" Align="middle">
          <TD class="Title1" colSpan="9">Accessibility Options</TD></TR>
          <TR><TD colspan="9">&nbsp;</TD></TR>
          <TR>
          <TD width="1%"></TD>
          <TD width="28%" class="Title3">Designation:&nbsp;&nbsp;
          <SELECT name="Access_Id" style="WIDTH: 139px" class="breadcrumbs">
          <%

        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;

        int Access_Id = 0;
      
        try
        {
          Access_Id= Integer.parseInt(request.getParameter("Access_Id"));
        }
        catch(NumberFormatException e)
        {
          Access_Id = 0;
        }
        
       String query = "Select Access_Id,Designation from Accessibility where Access_Id > 1"; %>
       
       <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= Access_Id %>" />

          </SELECT></TD>
          <TD width="3%">&nbsp;&nbsp;</TD>
          <TD width="12%"><INPUT class="B1" name="btnGo" value=" Go ->" type="button" onclick="Button('ACSSOP_GO')"></TD>
          <TD width="9%">&nbsp;&nbsp;&nbsp;</TD>
          <TD width="34%" class="Title3">Administrator:&nbsp;&nbsp; 
          <SELECT name='Admin_Id' style="LEFT: 275px; WIDTH: 130px;" class="breadcrumbs">
        <%
          int Admin_Id = 0;

          try
          {
            Admin_Id= Integer.parseInt(request.getParameter("Admin_Id"));
          }
          catch(NumberFormatException e)
          {
            Admin_Id = 0;
          }

          query = "Select Admin_Id,Login from Admin where Admin_Id > 1 and Access_Id = " + Access_Id;
        %>

        <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= Admin_Id %>" />

        </SELECT></TD>
	    <TD width="12%">&nbsp;&nbsp;<INPUT class="B1" name="btnSet" value=" Set " type="button" onclick="Button('ACSSOP_ST')"></TD>
	    <TD width="1%"></TD>
        </TR>
	    <TR><TD colspan="9">&nbsp;</TD></TR></TABLE>
        <INPUT name='Access_Id' type='hidden' value = '<%= Access_Id %>'>
        <INPUT name='User_Id' type='hidden' value = '0'>
        <TABLE id="rsTable" name ="rsTable" cellSpacing=1 cellPadding=2 width="100%" border=0  cols=12 align="center">
        <TR>
          <TD bgColor="#abcdef" width=2%></TD>
	      <TD bgColor="#abcdef" width=6%>
	       <P align=center><A href="javascript:sortTable(1, rsTable);"><STRONG><FONT face=Verdana size=1>Sr No</FONT></STRONG></A></TD>
	      <TD bgColor="#abcdef" width=78%>
	       <P align=center><A href="javascript:sortTable(2, rsTable);"><STRONG><FONT face=Verdana size=1>Accessibility</FONT></STRONG></A></TD>
	      <TD bgColor="#abcdef" width=13%>
	       <P align=center><A href="javascript:sortTable(3, rsTable);"><STRONG><FONT face=Verdana size=1>Priority</FONT></STRONG></A></P></TD>
          <TD width=1%></TD>
        </TR>
  <%/*
     int Experience = 0;
     long User_Id = 0;
     int count = 0;
     java.util.Date date=null;
	 
	 Calendar Cdate = Calendar.getInstance();
	 Calendar Bdate = Calendar.getInstance();
     
     if(Post_Id != 0)
     {
       ResultSet TData = null,UData = null;
     
       query = "select User_Id,Email,Login,Date_of_Birth,Qualification,Branch,Experience,SelectNo from Users where Selected = 0 and User_Id in" + 
               " (select User_Id from Result where Post_Id = " + Post_Id + ")";
       System.out.println("query = " + query);
       UData = db.RetriveDb(query);
     
       query = "select T_Percent,T_Attempt,T_Result,Attempt_No" + 
               " from Result where Post_Id = " + Post_Id; 
       
       String qry = "";
       
       if(Result == 1)
         qry = " and T_Result = 'Pass'";
       else if(Result == 2)
         qry = " and T_Percent > -1";
       
       query = query + qry;
       qry = "";
       
       if(Conducted == 1)
         qry = " and (Sysdate - TestDate) < 1";
       else if(Conducted == 2)
         qry = " and (Sysdate - TestDate) < 8";
       else if(Conducted == 3)
         qry = " and Months_Between(Sysdate,TestDate) = 0";
       else if(Conducted == 4)
         qry = " and Months_Between(Sysdate,TestDate) < 6";
         
       query = query + qry;
       System.out.println("query = " + query);
       TData = db.RetriveDb(query);
       
       try
       {
         while(UData.next() & TData.next())
         { 
		   User_Id = UData.getLong(1);
		   out.println("<TR>");
		   out.println("<td><P align=center><input name='Details' type='image' src='/ORS/Web/images/navyblueball.gif' alt=\"View Result Details\" onclick='changeUid(\"" + User_Id + "\")'>");
		   out.println("</P></td>");
		   out.println("<td><P align=center><A href=\"mailto:" + UData.getString(2) + "\"><STRONG><FONT face=Verdana size=2>");
		   out.println(UData.getString(3));
		   out.println("</FONT></B></A></STRONG></P></td>");
           
		   float ft = TData.getFloat(1);
		   
		   if(ft < 0)
		   {
		     out.println("<td><P align=center><A><STRONG><FONT color ='red' face=Verdana size=2>");
		     out.println("Negative");
		   }
		   else
		   {
		     out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		     out.println(ft);
		   }
		   
		   out.println("</FONT></B></A></STRONG></P></td>");
		   
		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   out.println(TData.getFloat(2));
		   out.println("</FONT></B></A></STRONG></P></td>");
		   
		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   out.println(TData.getString(3));
		   out.println("</FONT></B></A></STRONG></P></td>");
           
		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   out.println(TData.getInt(4));
		   out.println("</FONT></B></A></STRONG></P></td>");
		   
		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   
		   date = UData.getDate(4);
		   Bdate.setTime(date);
		   int age = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR);
		   
		   out.println(age);
		   out.println("</FONT></B></A></STRONG></P></td>");
		   
		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   out.println(UData.getString(5));
		   out.println("</FONT></B></A></STRONG></P></td>");

		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   out.println(UData.getString(6));
		   out.println("</FONT></B></A></STRONG></P></td>");

		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
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
		   
		   out.println("</FONT></B></A></STRONG></P></td>");

		   out.println("<td><P align=center><A><STRONG><FONT face=Verdana size=2>");
		   out.println(UData.getInt(8));
		   out.println("</FONT></B></A></STRONG></P></td>");

		   out.println("<td><input type='checkbox' name='User_Id" + count + "' value = '" + User_Id + "'>");
           out.println("</td>");
           out.println("</TR>");
           count++;
	     }
	   }
	   catch(Exception e)
	   {
	 	 out.println("Problem in Result Retrival :" + e.getMessage());
		 e.printStackTrace();
	   }
	 }*/
  %>       
        </TABLE>

        <TABLE id="end" name="end" cellSpacing=1 cellPadding=1 width="90%" align="center" border=0 >
        <TR>
		  <TD vAlign="top" width="107">&nbsp;</TD>
		  <TD vAlign="top" width="211">&nbsp;</TD>
		  <TD vAlign="top" width="213">&nbsp;</TD>
		  <TD vAlign="top" width="77">&nbsp;</TD>
		  <TD vAlign="top" width="114">&nbsp;</TD>
	    </TR>
	    <TR>
		  <TD vAlign="top" width="107">&nbsp;</TD>
		  <TD vAlign="top" width="211">&nbsp;</TD>
		  <TD vAlign="top" width="213">&nbsp;</TD>
		  <TD vAlign="top" width="77">&nbsp;</TD>
		  <TD vAlign="top" width="114">&nbsp;</TD>
	    </TR>
        <TR align=center>
  	      <TD vAlign="top" width="107"><INPUT name="Count" type='hidden' value="<% /*=count*/ %>">&nbsp;</TD>
	      <TD><INPUT class="B1" type="button" name="Ok" value="    Ok    " onclick="Button('ACSSOP_SEL')" size=25></TD>
          <TD><INPUT class="B1" type="button" name="Cancel" value="  Cancel  " onclick="Button('ACSSOP_CL')" size=25></TD>
	      <TD vAlign="top" width="77"><INPUT type='hidden' name='RequestUrl' value = 'ResultView'>&nbsp;</TD>
	      <TD vAlign="top" width="114">&nbsp;</TD>
        </TR>
    </TABLE>
    <P></P><P></P><P></P></P>
    </TABLE>
    </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>