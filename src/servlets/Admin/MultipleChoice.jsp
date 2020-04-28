<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<TITLE>New Test Question: Multiple Choice</TITLE>

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
   if(document.MultipleChoiceQues.Question.value=="")
   { 
     alert("Please! fill the Question Feild.");
     document.MultipleChoiceQues.Question.focus();
     return false;
   }
   else if(document.MultipleChoiceQues.ans1.value=="")
   { 
     alert("Please! give Answer A .");
     document.MultipleChoiceQues.ans1.focus();
     return false;
   }
   else if(document.MultipleChoiceQues.ans2.value=="")
   { 
     alert("Please! give Answer B .");
     document.MultipleChoiceQues.ans2.focus();
     return false;
   }
   else if(document.MultipleChoiceQues.ans3.value=="")
   { 
     alert("Please! give Answer C .");
     document.MultipleChoiceQues.ans3.focus();
     return false;
   }
   else if(document.MultipleChoiceQues.ans4.value=="")
   { 
     alert("Please! give Answer D .");
     document.MultipleChoiceQues.ans4.focus();
     return false;
   }
   else 
   document.MultipleChoiceQues.submit();
 }
 
function cancel(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/Create Question.html","right1");
}
</script>
</HEAD>
<BODY bgcolor="ivory" leftMargin=50 rightMargin=50>
<TABLE cellSpacing=1 cellPadding=1 width="75%" border=0>
  
  <TR>
    <TD style="COLOR: white; BACKGROUND-COLOR: #abcdef" 
      >&nbsp;<STRONG><FONT face=Verdana color=black>New Test Question: 
      Multiple Choice</FONT></STRONG></TD></TR></TABLE><br>
<FONT face=Verdana size=2>Please complete this form to build your new question. <i>Make sure to <br>
<strong>indicate</strong> which answer is the <strong>correct one</strong>.</i><br></FONT><br>
<FORM name="MultipleChoiceQues" action="http://localhost:8080/examples/servlet/CreateMultipleChoiceQ" onsubmit="return validate()">
<P><STRONG>Question</STRONG> : <FONT face=Verdana><FONT 
size=2>
  <i>Type your question in the textArea below</i><br></FONT></FONT>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
<TEXTAREA name=txtQuestion rows=6 cols=45></TEXTAREA>
<br><br>
&nbsp;A <INPUT name="ans" type="radio" value="1" >&nbsp;&nbsp;
        <INPUT name="ans1" size=77 style="WIDTH: 375px; HEIGHT: 22px"><br>
&nbsp;B <INPUT name="ans" type="radio" value="2" >&nbsp;&nbsp;
        <INPUT name="ans2" size=77 style="WIDTH: 375px; HEIGHT: 22px"><br>
&nbsp;C <INPUT name="ans" type="radio" value="3" >&nbsp;&nbsp;
        <INPUT name="ans3" size=77 style="WIDTH: 375px; HEIGHT: 22px"><br>
&nbsp;D <INPUT name="ans" type="radio" value="4" checked >&nbsp;&nbsp;
        <INPUT name="ans4" size=77 style="WIDTH: 375px; HEIGHT: 22px"><br>
&nbsp;<br>
<FONT face=Verdana size=2></FONT><br>	
<strong><span class="style2">&nbsp;Select Category : </span></strong>
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
<FONT face=Verdana>
<BR>
<PRE>	    
<INPUT name="Add" type="submit" value="       Add       ">  <INPUT name="Reset" type="reset" value="       Reset       ">     <INPUT name="Cancel" type="button" value="   Cancel   "onclick="cancel(this.form)">	
</PRE>
</FORM>
<P>&nbsp;</P></FONT>

</BODY>
</HTML>
