<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<TITLE>New Test Question: True or False</TITLE>

<%@ page contentType="text/html"%>
<%@ page import="java.sql.*,java.util.*,java.io.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<% 
   Connection con=null;
   Statement stmt=null;
   ResultSet rs=null;
   try{
        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
        con=pool.getConnection();
        stmt=con.createStatement();
       }catch(Exception  e)
        {
         out.println(e.getMessage());
        }
 %>

<script language=javascript>
 function validate()
 {
   if(document.TrueOrFalseQues.txtQuestion.value=="")
   { 
     alert("Please! fill the Question Feild.");
     document.TrueOrFalseQues.txtQuestion.focus();
     return false;
   }
 }
 
function cancel(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/Create Question.html","right1");
}
</script>
</HEAD>
<BODY bgcolor="ivory" leftMargin=50 rightMargin=50>
<TABLE cellSpacing=1 cellPadding=1 width="532" border=0 style="WIDTH: 532px; HEIGHT: 27px">
  
  <TR>
    <TD style="COLOR: white; BACKGROUND-COLOR: #abcdef" 
      >&nbsp;<STRONG><FONT face=Verdana color=black>New Test Question: 
      True or False</FONT></STRONG></TD></TR></TABLE><br>
<FONT size=3>
<pre>Enter your <strong>true/false</strong> statement below, then 
indicate whether that statement is <strong><i>true </i></strong>or<strong> <i>false</i></strong>  
</pre>
</FONT>
<FORM name="TrueOrFalseQues" action="http://localhost:8080/examples/servlet/CreateTrueFalseQ" onsubmit="return validate()" >
<P><STRONG>Question</STRONG> : <FONT size=2><FONT 
face=Verdana>
  <i>Your Question goes here</i><br>
</FONT></FONT>
  <textarea name="txtQuestion" cols="50" rows="3"></textarea>
  &nbsp;
<br>
<br>
<br>
<STRONG>Answer:</STRONG> : <FONT size=2><FONT 
face=Verdana>
  <i>Please Check On The Correct Answer Below</i><br><br>
</FONT></font>
<center>
<INPUT name="radio" type="radio" CHECKED value="true">&nbsp;&nbsp;True
<INPUT name="radio" type="radio" value="false">&nbsp;&nbsp;False<br><br><br>
</center>
</P>
<strong><span class="style2">Select Category : </span></strong> 
<select name="select" >
  <%
      try{
      		rs=stmt.executeQuery("select name from category");     
   %>
    
   <OPTION>
   
    <%              
       while(rs.next())
        {       
         
         out.print(rs.getString("name"));
         out.print("<OPTION />");
       }       
     %>
     
    <%
       
       }catch(Exception e)
        {
         out.println(e.getMessage());
        }
    
    %>
</select>
<br>
<PRE>	    
<INPUT name="Add" type="submit" value="     Add     ">     <INPUT name="Cancel" type="button" value="Cancel" onclick="cancel(this.form)">
</PRE>
</FORM>
<P>&nbsp;</P>

</BODY>
</HTML>

