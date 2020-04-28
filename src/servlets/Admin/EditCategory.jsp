<HTML>
<HEAD>
<TITLE>Edit Category Page</TITLE>
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
<BODY bgcolor="ivory">
<SCRIPT language="javascript">
function sure1() {

   if(confirm("This button WILL DELETE the selected test. Are you sure you want to continue?")) 
   {
      return true;
   }

   return false;
}
function sure2() {

   if(confirm("This button WILL DELETE the selected category. Are you sure you want to continue?")) 
   {
      return true;
   }

   return false;
}

</SCRIPT>

<TABLE cellSpacing=1 cols=4 cellPadding=1 width="50%" align=left border=0>

  <TR bgColor="#abcdef">
    <TD colSpan=4>
      <P align=left><STRONG><FONT face=Verdana 
      size=2>Categories</FONT></STRONG></P></TD></TR>
  <TR>
    <TD colspan="4"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;<FONT face=Tahoma size=2>
    <A href="http://localhost:8080/examples/servlets/Admin/CreateCategory.html">Create a 
      new category</A></FONT></TD></TR>
  <TR>

   <TD><IMG src="../gifs/navyblueball.gif" align=bottom border=0>
    <SELECT name=SelectCategory>
	<FORM name=DelCat method=get action=http://localhost:8080/examples/servlets/delete_Catdeleteme.html >
    
            
    <%
       String f;
       size =  connect.getSize();
       for(int i =0;i<size;i++)
       {       
         connect.setId(i);
         out.println("<OPTION>");
         f = connect.getCat();
         out.println(f);
         out.println("</OPTION>");
       }       
     %>
     
     

	</SELECT>
	</TD>
	<FORM name=CreateEditCat action=http://localhost:8080/examples/servlets/Admin/CategoryHome.jsp >
    <TD align=middle><INPUT style="WIDTH: 180px; FONT-FAMILY: sans-serif" type=submit value="Edit Category" name=EditCategory width="180"></TD></FORM>
    
    <TD><INPUT style="WIDTH: 180px; FONT-FAMILY: sans-serif" onclick="return sure2()" type=submit value="Delete Category" name=DelCategory width="180"></TD></FORM>
    <TD></TD></TR>
  <TR>
    <TD colspan="4">&nbsp;</TD>
  </TR>
  <TR>
    <TD colspan="4"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;<FONT face=Verdana size=2><STRONG><A style="COLOR: blue" href="http://127.0.0.1:8080/examples/servlets/AdminLoginPage.html" >Log 
      out</A></STRONG></FONT></TD>
  </TR></TABLE>
</body>
</html>
