<html>
<body bgcolor="ivory">
<head>
<TITLE>Post Page</TITLE>
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

</HEAD>
<SCRIPT language="javascript">


function sure(form) {

   if(confirm("This button will DELETE the selected Post. Are you sure you want to continue?")) 
   {
     document.EditCat.action="http://localhost:8080/examples/servlet/Delete_Post";
     document.EditCat.submit();
     
   }

 }


function EditCButton(form)
{
  document.EditCat.action="http://localhost:8080/examples/servlets/Admin/EditPost.jsp";
  document.EditCat.submit();
}

function back(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/HomePage.html","right1");
}

</SCRIPT>
<TABLE cellSpacing=1 cols=4 cellPadding=1 width="50%" align=center border=0>
<TR>
    <TD colSpan=2>&nbsp;</TD></TR>
  <TR style="BACKGROUND-COLOR: #abcdef">
    <TD colSpan=2>
      <P align=center><FONT face=Verdana><STRONG>&nbsp;Post</STRONG></FONT></P></TD>
  </TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>
    <TD colspan="2"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>
    <FONT face=Verdana size=2>&nbsp;<A href="http://localhost:8080/examples/servlets/Admin/CreatePost.jsp">
    Create New Post</FONT> </A></TD>
  </TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>
  
 
    <FORM name="EditCat" method=get >
    <TD nowrap><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
    <SELECT name=SelectPost> 
     <%
      try{
          rs=stmt.executeQuery("select POSTNAME from post");
          out.print("<option>");
          while(rs.next())
          {
           
           out.print(rs.getString("POSTNAME"));
           out.print("<option />");
          }
         rs.close();
         
         }catch(Exception e)
          {
           out.print(e.getMessage());
          }
      
     %>
              
   
	</SELECT> 
	<INPUT type=button value="Edit Post" name=EditCategory onClick="EditCButton(this.form)" style="WIDTH: 132px; HEIGHT: 24px" size=22>
	</TD>
	
	
    <TD>&nbsp;
    <INPUT onclick="sure(this.form)" type=button value="Delete Post" name=DelCategory></TD>
    </tr>
    <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
    <tr>
    <td align="right" colspan="3">
	<input type="button" name="btnBack" value="   Back << " onclick="back(this.form)">
	</td>
    </FORM>
  </table>
  </body>
</html>