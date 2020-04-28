<html>
<body bgcolor="ivory">
<head>
<TITLE>Admin Home Page</TITLE>
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

function sure(form) 
{
   if(confirm("This button will DELETE the selected Admin . Are you sure you want to continue?")) 
   {
     document.EditAdmin.action="http://localhost:8080/examples/servlet/Delete_Admin";
     document.EditAdmin.submit();     
   }
 }
 
function EditCButton(form)
{
  document.EditAdmin.action="http://localhost:8080/examples/servlets/Admin/EditAdmin.jsp";
  document.EditAdmin.submit();
}

</SCRIPT>
<TABLE cellSpacing=1 cols=4 cellPadding=1 width="50%" align=center border=0>
<TR>
    <TD colSpan=2>&nbsp;</TD></TR>
  <TR style="BACKGROUND-COLOR: #abcdef">
    <TD colSpan=2>
      <P align=center><FONT face=Verdana><STRONG>&nbsp;Admin</STRONG></FONT></P></TD>
  </TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>
    <TD colspan="2"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>
    <FONT face=Verdana size=2>&nbsp;<A href="http://localhost:8080/examples/servlets/Admin/AdminRegistrationPage.html">
    Create New Admin</FONT> </A></TD>
  </TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>  
 
    <FORM name="EditAdmin" method=get >
    <TD nowrap><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
    <SELECT name=SelectAdmin>      
   <%
      int id = 0;
     
      try
      {
          rs=stmt.executeQuery("select Login,Admin_Id from Admin");

          while(rs.next())
          {
             if(id == 0)
             {
               out.print("<option value="+rs.getInt("Admin_Id")+" selected>");
               id = 1;
             }
             else
             {
               out.print("<option value="+rs.getInt("Admin_Id")+" >");
             }
             out.print(rs.getString("Login"));
             out.print("</option>");
          }
         rs.close();
      }
      catch(Exception e)
      {
        out.print(e.getMessage());
      }      
   %>
                
	</SELECT> &nbsp;&nbsp;
	<INPUT type=button value="Edit  Profile" name="EditAdmin" onClick="EditCButton(this.form)" style="WIDTH: 132px; HEIGHT: 24px" size=22>
	</TD>
    <TD>&nbsp;
    <INPUT onclick="sure(this.form)" type=button value="Delete  Profile" name="DelAdmin"></TD>
    </FORM>
  </TR>  
  </table>
  </body>
</html>