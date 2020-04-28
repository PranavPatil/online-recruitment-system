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
<TITLE>Test Page </TITLE>
</head>
<SCRIPT language="javascript">


function sure(form) {

   if(confirm("This button will DELETE the selected Test . Are you sure you want to continue?")) 
   {
     document.EditTest.action="http://localhost:8080/examples/servlet/DeleteTest";
     document.EditTest.submit();
     
   }

 }
function verify(form)
{
	if(document.EditTest.value=="---Select Category---")
	return false;
}

function EditCButton(form)
{
  document.EditTest.action="http://localhost:8080/examples/servlets/Admin/EditTest.jsp";
  document.EditTest.submit();
}

function back(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/HomePage.html","right1");
}
</SCRIPT>
<body bgcolor="ivory">

<FORM NAME="EditTest" ACTION="Test.jsp" onsubmit="return verify(this.form)">
	<table align=center >
	   <TR bgColor="#abcdef">
    	<TD colSpan=4>
      		<P align=center><STRONG><FONT face=Verdana>Test</FONT></STRONG></P>
      	</TD>
       </TR>
       <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
	    <TR>
         <TD colspan="4"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;<FONT face=Tahoma size=2>
          <A href="http://localhost:8080/examples/servlets/Admin/CreateTest.jsp">
          Create a new Test</A></FONT>
        </TD>
       </TR>
		<TR>
		<TD colspan="4">&nbsp;</TD>
		</TR>
	    <tr>
	      <TD style="WIDTH: 50%" width="50%"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
  
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
		  </TD></tr>
		  <TR>
   		 <TD colspan="4">&nbsp;</TD>
  		</TR>
			<Td>
			<IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
			<SELECT name=SelectTest style="WIDTH: 300px"> 
			<option>pls select category and press Go</option>
			</SELECT>
			</td>
       
        <TR>
  		
      	</tr>
      	<tr>
      	<td>
         <INPUT type=button value="Edit Test" name="EditCategory" onClick="EditCButton(this.form)">
         <td>
         <INPUT onclick="sure(this.form)" type=button value="Delete Test" name=DelQues>
      </td>
      <td>
         <INPUT onclick="back(this.form)" type=button value="Back << " name="btnBack">
      </td>
      </tr>
 </table>
  </FORM>     
 
<% 
      con.close();       
 %>

 </body>
 </html>