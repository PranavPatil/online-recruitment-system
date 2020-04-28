<html>
<body bgcolor="ivory">
<head>
<TITLE>User Home Page</TITLE>
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

   if(confirm("This button will DELETE the selected User . Are you sure you want to continue?")) 
   {
     document.EditCat.action="http://localhost:8080/examples/servlet/Delete_User";
     document.EditCat.submit();
     
   }

 }


function EditCButton(form)
{
  document.EditCat.action="http://localhost:8080/examples/servlets/Admin/Edit_User.jsp";
  document.EditCat.submit();
}



</SCRIPT>
<TABLE cellSpacing=1 cols=4 cellPadding=1 width="50%" align=center border=0>
<TR>
    <TD colSpan=2>&nbsp;</TD></TR>
  <TR style="BACKGROUND-COLOR: #abcdef">
    <TD colSpan=2>
      <P align=center><FONT face=Verdana><STRONG>&nbsp;User</STRONG></FONT></P></TD>
  </TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>
    <TD colspan="2"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>
    <FONT face=Verdana size=2>&nbsp;<A href="http://localhost:8080/examples/servlets/Admin/UserView.jsp">
    View User</FONT> </A></TD>
  </TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>
  
 
    <FORM name="EditCat" method=Post >
    <TD nowrap><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
    <SELECT name=SelectUser>
     <%
        
        int id = 0;

      try{
           rs=stmt.executeQuery("select Login,user_id from Users");

           while(rs.next())
           {
             if(id == 0)
             {
               out.print("<option value="+rs.getInt("user_id")+" selected>");
               id = 1;
             }
             else
             {
               out.print("<option value="+rs.getInt("user_id")+" >");
             }
             out.print(rs.getString("Login"));
             out.print("</option>");                          
          }
         rs.close();
         
         }catch(Exception e)
          {
           out.print(e.getMessage());
          }      
     %>
              
   
	</SELECT> &nbsp;&nbsp;
	<INPUT type=button value="Edit User" name=EditUser onClick="EditCButton(this.form)" style="WIDTH: 132px; HEIGHT: 24px" size=22>
	</TD>
	
	
    <TD>&nbsp;
    <INPUT onclick="sure(this.form)" type=button value="Delete User" name=DelUser></TD>
    </FORM>
  </TR>  
  </table>
  </body>
</html>