<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>  
<%@ page import = "java.io.*" %>  
<%@ page import = "java.util.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page errorPage="/Web/Admin/ErrorPage.jsp" %>

<%
   ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool");
 %>

<HTML>
<HEAD>
<TITLE>View Employees</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="/ORS/Web/images/Sort.js"></script>
<SCRIPT language=javascript>

 function conform(doEvent)
 {
    //if(window.confirm("Are you sure you want to Delete the Selected Employees. "))
    //{
      document.EmpView.EVENTNAME.value=doEvent;
      document.EmpView.submit();      
    //}
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

      <FORM id="EmpView" name="EmpView" action="/ORS/AdminController" method="get">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

          <TABLE id="TABLE1" cellSpacing=1 cellPadding=1 width="100%" align=center border=0>

          <TR vAlign=center align=middle>
          <TD class="Title1" colSpan=6>View Employees </TD>
          </TR>
          <TR><TD colspan="5">&nbsp;</TD></TR>
      </TABLE> 
 
      <TABLE id=rsTable name="rsTable" cellSpacing=1 cellPadding=1 width="100%" align=center border=0 cols=8>  
      <TR>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(0, rsTable);"><STRONG><FONT face=Verdana size=1><B>Login Name</B></FONT></A></TD>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(1, rsTable);"><STRONG><FONT face=Verdana size=1>Full Name</FONT></STRONG></A></P></TD>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(2, rsTable);"><STRONG><FONT face=Verdana size=1><B>Age</B></FONT></A></TD>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(3, rsTable);"><STRONG><FONT face=Verdana size=1>Qualification</FONT></STRONG></A></P></TD>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(4, rsTable);"><STRONG><FONT face=Verdana size=1>Branch</FONT></STRONG></A></P></TD>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(5, rsTable);"><STRONG><FONT face=Verdana size=1>Experience</FONT></STRONG></A></P></TD>
	    <TD bgColor="#abcdef">
	      <P align=center><A href="javascript:sortTable(6, rsTable);"><STRONG><FONT face=Verdana size=1>Post</FONT></STRONG></A></P></TD>
        <TD>
          <P align=center><STRONG><FONT face=Verdana size=1></FONT></STRONG></P></TD>
      </TR>
      <% 
         ResultSet UData = null;
         Database db = new Database(pool);     
         UData=db.RetriveDb("Select User_Id,Login,Fname,Lname,Date_Of_Birth,Email,Qualification,Branch,Experience,Employee from Users where Employee > 0");

	     java.util.Date date=null;
	     long User_Id = 0;
	     int count = 0;
    	 
	     Calendar Cdate = Calendar.getInstance();
	     Calendar Bdate = Calendar.getInstance();
         
         while(UData.next())
         {
	       User_Id = UData.getLong(1);
	       out.println("<TR>");
	       out.println("<TD><P align=center><A href=\"mailto:" + UData.getString(6) + "\" class=h1 style=\"color:#0033FF\">");
	       out.print(UData.getString(2));
	       out.println("</A></P></TD>");
    
	       out.println("<TD><P align=center><FONT class=h1>");
	       out.print(UData.getString(3) + " " + UData.getString(4));
	       out.println("</FONT></P></TD>");
		   
	       out.println("<TD><P align=center><FONT class=h1>");
	       date = UData.getDate(5);
	       Bdate.setTime(date);
		   
	       int age = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR);
	       out.print(age + " yrs");
	       out.println("</FONT></P></TD>");

	       out.println("<TD><P align=center><FONT class=h1>");
	       out.print(UData.getString(7));
	       out.println("</FONT></P></TD>");
		   
	       out.println("<TD><P align=center><FONT class=h1>");
	       out.print(UData.getString(8));
	       out.println("</FONT></P></TD>");
		   
	       out.println("<TD><P align=center><FONT class=h1>");

           int Experience = UData.getInt(9);
       
 	       if(Experience == 5)
	       {
	         out.print("above 10yrs");
	       }
	       else if(Experience == 4)
	       {
		     out.print("5-10yrs");
	       }
	       else if(Experience == 3)
	       {
		     out.print("2-5yrs");
	       }
	       else if(Experience == 2)
	       {
	         out.print("1-2yrs");
	       }
	       else
	       {
		     out.print("Fresher");
	       }
	    	
	       out.println("</FONT></P></TD>");
	       out.println("<TD><P align=center><FONT class=h1>");
	       
	       int Post = UData.getInt(10);
	       
	       if(Post != 0)
	       {
	         ResultSet res = db.RetriveDb("Select PostName from Post where Post_Id = " + Post);
	         res.next();
	         out.print(res.getString(1));
	       }
	       else
	         out.print("Not Available");
    	   
	       out.println("</FONT></P></TD>");
	       out.println("<TD><INPUT type='checkbox' name='User_Id" + count + "' value = '" + User_Id + "'>");
	       out.println("</TD>");
	       out.println("</TR>");
	       count++;
	     }
      %>
      </TABLE>
      <TABLE cellSpacing=1 cellPadding=5 width="100%" align=center border=0 id=end>
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

  	  <CENTER>
      <TR>
	  <TD><INPUT name="Count" type='hidden' value="<%= count %>"></TD>
	  <TD><INPUT name='RequestUrl' type='hidden' value = 'EmpView'></TD>
      <TD><INPUT class="B1" name='btnDelete' type='button' value=" Delete " onclick="conform('VWEMP_DEL')" size=25></TD>
	  </CENTER>
      </TABLE>
      <P></P><P></P><P></P></P>
      </TABLE>
   </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>