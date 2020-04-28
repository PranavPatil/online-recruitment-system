<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*"  %>
<%@ page import ="ORS.ConnPool.ConnectionPool" %>

<%
   Connection con=null;
   Statement smt=null;
   Statement smt1=null;
   ResultSet TData=null;
   ResultSet user=null;
   
   ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
   con=pool.getConnection();
   smt=con.createStatement();
   smt1=con.createStatement();
 %>

<html>
<head>
<title>User View</title>
<script language=javascript>
function back(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/UserUpdate.jsp","right1");
}
</script>
</head>
<body bgcolor="ivory">
<P>
<form action="http://localhost:8080/examples/servlet/" method="get">
<TABLE cellSpacing=1 cellPadding=5 width="100%" align=center border=0 id=TABLE1>
  
  <TR vAlign=center align=middle bgColor=black>
    <TD colSpan=6
    style="VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center">
    <FONT face=Verdana size=2><STRONG
    style="COLOR: white; BACKGROUND-COLOR: black"> 
    User View 
      </STRONG></FONT></TD></TR>
  <TR>
    <TD colspan="5">&nbsp;</TD></TR>
  <TR bgColor="#abcdef">
  <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>UserName</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>DOB</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Email</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Qualification</FONT></STRONG></P></TD>
     <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Branch</FONT></STRONG></P></TD>
     <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Experience</FONT></STRONG></P></TD>
  </TR>
  <% 
               
     TData=smt.executeQuery("select *from users");

		String str=null;
 
     while(TData.next())
          {
             
			 out.print("<TR>");
			 out.print("<td>");
			 out.print(TData.getString(5));
			 out.print("</td>");
			 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			 str = TData.getString(11);
		     str = str.substring(0,10);
		     System.out.println(str);
			 out.print(str);
			 out.print("</td>");

			 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			 out.print(TData.getString(10));
			 out.print("</td>");
			 
			 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			 out.print(TData.getString(12));
			 out.print("</td>");
			 
			 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			 out.print(TData.getString(13));
			 out.print("</td>");
			 
			 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

             int Experience = TData.getInt(14);
             
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
			 
			 out.print("</td>");			 
			 
			 out.print("</TR>");             
		  }
  %>
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
  			<center>
			<tr align="right">
         <td><INPUT type="button" value=" Back <<" name="btnBack" onclick="back(this.form)">
      </td></tr>
			</center>
  </table>
  <P></P>
  <P></P>
  <P></P></P>
  </form>   
</body>
</html>    