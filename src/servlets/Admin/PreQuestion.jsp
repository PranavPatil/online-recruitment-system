<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*"  %>
<%@ page import ="ORS.ConnPool.ConnectionPool" %>

<%
   Connection con=null;
   Statement stmt=null;
   ResultSet rs=null;

        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
        con=pool.getConnection();
        stmt=con.createStatement();
 %>
<HTML>
<HEAD>
<TITLE>Edit Question </TITLE>
</head>
<SCRIPT language="javascript">


function sure(form) {

   if(confirm("This button will DELETE the selected Test . Are you sure you want to continue?")) 
   {
     document.EditQues.action="http://localhost:8080/examples/servlet/DeleteQuestion";
     document.EditQues.submit();
     
   }

 }
function verify(form)
{
	if(document.EditQues.SelectCategory.value=="---Select Category---")
	return false;
}

function EditCButton(form)
{
  document.EditQues.action="http://localhost:8080/examples/servlet/EditQuestion";
  document.EditQues.submit();
}

function back(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/HomePage.html","right1");
}
</SCRIPT>
<body bgcolor="ivory">

<FORM NAME="EditQues" ACTION="Question.jsp" onsubmit="return verify(this.form)">
	<table align=center >
	   <TR bgColor="#abcdef">
    	<TD colSpan=4>
      		<P align=center><STRONG><FONT face=Verdana>Questions</FONT></STRONG></P>
      	</TD>
       </TR>
       <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
	    <TR>
         <TD colspan="4"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;<FONT face=Tahoma size=2>
          <A href="http://localhost:8080/examples/servlets/Admin/Create Question.html">
          Create a new question</A></FONT>
        </TD>
       </TR>
		<TR>
		<TD colspan="4">&nbsp;</TD>
		</TR>
	    <tr>
	      <TD style="WIDTH: 50%" width="50%"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
	      <FONT face=Verdana size=2>Search for a question in this category</FONT>
          
          
          
          
          <SELECT name=SelectCategory> 
     		<option>---Select Category---</option>
     		<%
               
              
                     rs=stmt.executeQuery("select name from category");
                     out.print("<option>");
                     while(rs.next())
                          {
           					 out.print(rs.getString("name"));
           					 out.print("<option />");
          				  }
             		  rs.close();
             		  stmt.close();
         	  
          %>
          </SELECT>
	       <input type="submit" value="  Go ">
		  </TD>
          </tr>
       <TR>
    	<TD colspan="4">&nbsp;</TD>
  		</TR>
       
       
       <TR>
  		<TD  nowrap style="WIDTH: 50%" width="50%">
		<IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
		<SELECT name=SelectQuestion style="WIDTH: 300px"> 
		<option>pls select category and press Go</option>
        </SELECT>
        </td></tr>
        <tr>
        <td>
         <INPUT type="button" value="Edit Question" name="Edit Question" onClick="EditCButton(this.form)">
         </td>
         <td>
         <INPUT type="button" value="Delete Question" name="DelQues" onclick="sure(this.form)">
      </td>
      <td>
         <INPUT type="button" value=" Back <<" name="btnBack" onclick="back(this.form)">
      </td>
      </tr>
 </table>
  </FORM>     
 
<% 
      con.close();
 %>
 
 </body>
 </html>