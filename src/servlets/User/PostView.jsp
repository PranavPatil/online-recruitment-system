<%@ page contentType="text/html" %>
<%@ page import="java.sql.*" %>  
<%@ page import="java.io.*" %>  
<%@ page import="java.util.*" %>  

<html>
<head>
<title>Post View</title>
<script language=javascript>
function start1(form)
{ 
  window.close();
  window.open("welcome.html", "" , "[resizable,scrollbars,dependent=true,,toolbar]" , "" );
}

</script>
</head>
<body bgcolor="ivory">
<P>
<form name="main" action="http://localhost:8080/examples/servlet/PostValidate" method="get">
<TABLE cellSpacing=1 cellPadding=5 width="100%" align=center border=0 id=TABLE1>
  
  <TR vAlign=center align=middle bgColor=black>
    <TD colSpan=6
    style="VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center">
    <FONT face=Verdana size=2><STRONG
    style="COLOR: white; BACKGROUND-COLOR: black"> 
    Post View 
      </STRONG></FONT></TD></TR>
  <TR>
    <TD colspan="5">&nbsp;</TD></TR>
  <TR bgColor="#abcdef">
  <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Post ID</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Post Name</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Qualification</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Branch</FONT></STRONG></P></TD>      
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Experience</FONT></STRONG></P></TD>
    <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>Vacancy</FONT></STRONG></P></TD>      
     <TD>
      <P align=center><STRONG><FONT face=Verdana size=1>SelectPost</FONT></STRONG></P></TD>
  </TR>
  
  <%! PrintWriter out = null;
      int Post_Id = 0,Experience = 0;
      String query = null;
   %>
  
  <jsp:useBean id="connect" scope="request" class="ORS.ConnPool.Connect"/>
            
  <% ResultSet TData = null;
     query = "select Post_Id,Postname,Qualification,Branch,Experience,Vacancy from post where vacancy != 0";
     TData = connect.setRSQuery(query);  
         
     try
     {      
       while(TData.next())
       {             
		 out.print("<TR>");
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 Post_Id = TData.getInt("Post_Id");
		 out.print(Post_Id);
		 out.print("</td>");
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 out.print(TData.getString("PostName"));
		 out.print("</td>");
		 
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 out.print(TData.getString("Qualification"));
		 out.print("</td>");
		 
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 out.print(TData.getString("Branch"));
		 out.print("</td>");
         
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 Experience = TData.getInt("Experience");
		 
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
		 
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 out.print(TData.getString("Vacancy"));
		 out.print("</td>");			 
		 
		 out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		 out.print("<input type=\"radio\" name=\"Post_Id\" value = " + Post_Id + ">");		  		    
		 out.print("</td>");
		 
         out.print("</TR>");             
	   }
	 }
	 catch(Exception e)
	 {
	 		System.out.println("Problem in Post Retrival !!");
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
	 }	                           
  %>       
      
			<TR>
				<TD vAlign="top" width="400">&nbsp;</TD>
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
				<TD vAlign="top" width="400">&nbsp;</TD>
			</TR>
  			<center>
            <TR>
            <TD noWrap align=right><INPUT type=Submit value=" Submit " name=btnSubmit></TD>
			<TD><input type="button" name="btnHelp" value="   Help   " onclick="start1(this.form)"></TD>            
            </FORM>
            <FORM NAME="FExit" ACTION="http://127.0.0.1:8080/examples/servlets/User/UserLoginPage.html">&nbsp;
            <TD   noWrap><INPUT type=submit value="   Exit   " name=Exit></TD></TR>
			</center>
  </FORM></TBODY></TABLE>
  <P></P>
  <P></P>
  <P></P></P>
  </form>
</body>
</html>

 
    
    