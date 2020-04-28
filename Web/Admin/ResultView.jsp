<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>  
<%@ page import = "java.io.*" %>  
<%@ page import = "java.util.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<% 
   ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
   
    int Conducted = -1;
    int Result = -1;
    
    String str = null;
    str = request.getParameter("Date");
    
    if(str != null)
    {
      int i = 0;
      i = Integer.parseInt(str);
      Conducted = i;
    }
    
    str = null;
    str = request.getParameter("result");
    
    if(str != null)
    {
      int i = 0;
      i = Integer.parseInt(str);
      Result = i;
    }
 %>

<HTML>
<HEAD>
<TITLE>Result View</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->
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
    document.ResultView.User_Id.value = count;
    document.ResultView.EVENTNAME.value='VWRSLT_DET';
    //document.ResultView.target="window";
  }
  
  function Button(doEvent)
  {
    document.ResultView.EVENTNAME.value=doEvent;
    document.ResultView.submit();
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

      <FORM id="ResultView" name="ResultView" action="/ORS/AdminController" method="post">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

        <TABLE id="TABLE1" Name="TABLE1" cellSpacing=1 cellPadding=1 width="100%" border=0 align="center">
        <TR vAlign="center" Align="middle">
        <TD style="VERTICAL-ALIGN: middle;" class="Title1" colSpan="9">View Results</TD></TR>
        <TR><TD colspan="9">&nbsp;</TD>
        </TR>
        <TR><TD></TD>
            <TD class="Title3">Post:&nbsp;&nbsp;
        <SELECT name="postname" style="WIDTH: 149px" class="breadcrumbs">
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
    <TD></TD>
    <TD class="Title3">Conducted:&nbsp;&nbsp;
    <SELECT style="WIDTH: 90px" name='Date' class="breadcrumbs">
    <%
        if(Conducted == 2)
        {
          out.print("<OPTION value=1>Today</OPTION>");
          out.print("<OPTION value=2 selected>This Week</OPTION>");
          out.print("<OPTION value=3>This Month</OPTION>");
          out.print("<OPTION value=4>6 Months</OPTION>");
        }
        else if(Conducted == 3)
        {
          out.print("<OPTION value=1>Today</OPTION>");
          out.print("<OPTION value=2>This Week</OPTION>");
          out.print("<OPTION value=3 selected>This Month</OPTION>");
          out.print("<OPTION value=4>6 Months</OPTION>");
        }
        else if(Conducted == 4)
        {
          out.print("<OPTION value=1>Today</OPTION>");
          out.print("<OPTION value=2>This Week</OPTION>");
          out.print("<OPTION value=3>This Month</OPTION>");
          out.print("<OPTION value=4 selected>6 Months</OPTION>");
        }
        else
        {
          out.print("<OPTION value=1 selected>Today</OPTION>");
          out.print("<OPTION value=2>This Week</OPTION>");
          out.print("<OPTION value=3>This Month</OPTION>");
          out.print("<OPTION value=4>6 Months</OPTION>");
          Conducted = 1;
        }
    %>
      </SELECT></TD>
    <TD></TD>
    <TD class="Title3">Result:&nbsp;&nbsp; 
    <SELECT style="LEFT: 275px; WIDTH: 110px;" name='result' class="breadcrumbs"> 
    <%
        if(Result == 2)
        {
          out.print("<OPTION value=1>Passed</OPTION>");
          out.print("<OPTION value=2 selected>Non Negative</OPTION>");
          out.print("<OPTION value=3>All</OPTION>");
        }
        else if(Result == 3)
        {
          out.print("<OPTION value=1>Passed</OPTION>");
          out.print("<OPTION value=2>Non Negative</OPTION>");
          out.print("<OPTION value=3 selected>All</OPTION>");
        }
        else
        {
          out.print("<OPTION value=1 selected>Passed</OPTION>");
          out.print("<OPTION value=2>Non Negative</OPTION>");
          out.print("<OPTION value=3>All</OPTION>");
          Result = 1;
        }
    %>
    </SELECT></TD>
	<TD></TD>
	<TD>
    <INPUT class="B1" name="btnGo" value=" Go ->" type="button" onclick="Button('VWRSLT_GO')"></TD>
    </TR>
	<TR><TD colspan="9">&nbsp;</TD></TR></TABLE>
 <INPUT name='Post_Id' type='hidden' value = '<%= Post_Id %>'>
 <INPUT name='User_Id' type='hidden' value = '0'>
 <TABLE id="rsTable" name="rsTable" cellSpacing=1 cellPadding=2 width="100%" border=0  cols=12 align="center">
 <TR>
    <TD bgColor="#abcdef" width="1%"></TD>
	<TD bgColor="#abcdef" width="16%">
	   <P align=center><A href="javascript:sortTable(1, rsTable);"><STRONG><FONT face=Verdana size=1>User Name</FONT></STRONG></A></TD>
	<TD bgColor="#abcdef" width="7%">
	   <P align=center><A href="javascript:sortTable(2, rsTable);"><STRONG><FONT face=Verdana size=1>Percent</FONT></STRONG></A></TD>
	<TD bgColor="#abcdef" width="9%">
	   <P align=center><A href="javascript:sortTable(3, rsTable);"><STRONG><FONT face=Verdana size=1>Attpt %</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="6%">
	   <P align=center><A href="javascript:sortTable(4, rsTable);"><STRONG><FONT face=Verdana size=1>Result</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="8%">
	   <P align=center><A href="javascript:sortTable(5, rsTable);"><STRONG><FONT face=Verdana size=1>Atpt No</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="4%">
	   <P align=center><A href="javascript:sortTable(6, rsTable);"><STRONG><FONT face=Verdana size=1>Age</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="12%">
	   <P align=center><A href="javascript:sortTable(7, rsTable);"><STRONG><FONT face=Verdana size=1>Qualification</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="17%">
	   <P align=center><A href="javascript:sortTable(8, rsTable);"><STRONG><FONT face=Verdana size=1>Branch</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="12%">
	   <P align=center><A href="javascript:sortTable(9, rsTable);"><STRONG><FONT face=Verdana size=1>Experience</FONT></STRONG></A></P></TD>
	<TD bgColor="#abcdef" width="6%">
	   <P align=center><A href="javascript:sortTable(10, rsTable);"><STRONG><FONT face=Verdana size=1>Select</FONT></STRONG></A></P></TD>
    <TD bgColor="#abcdef" width="2%"></TD>
 </TR>
  
  <%
     int Experience = 0;
     long User_Id = 0;
     int count = 0;
     java.util.Date date=null;
	 
	 Calendar Cdate = Calendar.getInstance();
	 Calendar Bdate = Calendar.getInstance();
     
     if(Post_Id != 0)
     {
       Database db = new Database(pool);
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
		   out.println("<TD><P align=center><INPUT name='Details' type='image' src='/ORS/Web/images/navyblueball.gif' alt=\"View Result Details\" onclick='changeUid(\"" + User_Id + "\")'>");
		   out.println("</P></TD>");
		   out.println("<TD><P align=center><A href=\"mailto:" + UData.getString(2) + "\" class=h1 style=\"color:#0033FF\" >");
		   out.println(UData.getString(3));
		   out.println("</A></P></TD>");
           
		   float ft = TData.getFloat(1);
		   
		   if(ft < 0)
		   {
		     out.println("<TD><P align=center><A><FONT color ='red' class=h1>");
		     out.println("Negative");
		   }
		   else
		   {
		     out.println("<TD><P align=center><FONT class=h1>");
		     out.println(ft);
		   }
		   
		   out.println("</FONT></P></TD>");
		   
		   out.println("<TD><P align=center><FONT class=h1>");
		   out.println(TData.getFloat(2));
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

		   out.println("<TD><INPUT type='checkbox' name='User_Id" + count + "' value = '" + User_Id + "'>");
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
  
  <TABLE id="end" name="end" cellSpacing=1 cellPadding=5 width="100%" align="center" border=0 >
   <TR>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	</TR>
	<TR>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	   <TD vAlign="top" width="400">&nbsp;</TD>
	</TR>
      <TR align=center>
       <TD><INPUT class="B1" type="button" name="Select" value="  Select  " onclick="Button('VWRSLT_SEL')" size=25></TD>
       <TD>
        <INPUT type=hidden name="Count" value="<%= count %>">
        <INPUT type='hidden' name='RequestUrl' value = 'ResultView'>
        <div id="PrintButton">
        <dd><input class="B1" type="button" name="print" value="  Print  " onClick="printPage()" size=25> </dd>
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