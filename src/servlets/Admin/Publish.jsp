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
<TITLE>Test</TITLE>
</head>

<SCRIPT language="javascript">

function buttonGo1(form)
{
  document.Post.action="http://localhost:8080/examples/servlets/Admin/Publish1.jsp";
  document.Post.submit();
}

function buttonGo2(form)
{
  document.Post.action="http://localhost:8080/examples/servlets/Admin/Publish2.jsp";
  document.Post.submit();
}

function back(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/Post.jsp","right1");
}

</SCRIPT>
<body bgcolor="ivory">

<FORM NAME="Post" ACTION="Test.jsp">
	<table >
	   <TR bgColor="#abcdef">
    	<TD colSpan=4>
      		<P align=center><STRONG><FONT face=Verdana>Tests</FONT></STRONG></P>
      	</TD>
       </TR>
       <TR>
   		 <TD colspan="4">&nbsp;</TD>
  		</TR>
	    <TR>
         <TD colspan="4"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>
         &nbsp;<FONT face=Tahoma size=2><strong>Post:</strong>
          <SELECT name=SelectPost> 
          <option>--Select Post--</option>
     				<%
                     rs=stmt.executeQuery("select postname from post");
                     out.print("<option>");
                     while(rs.next())
                          {
           					 out.print(rs.getString("postname"));
           					 out.print("<option />");
          				  }
             		  rs.close();
             		  stmt.close();
          %>
          </SELECT>
	       <input type="button" value="  Go " name="btnGo1" onclick="buttonGo1(this.form)"></FONT>
        </TD>
       </TR>
		<TR>
   		 <TD colspan="4">&nbsp;</TD>
  		</TR>
	    <tr>
	      <TD style="WIDTH: 50%" width="50%"><IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
	      <FONT face=Verdana size=2>Search for a Test in this <strong>category</strong></FONT>
          
          
          
          
          <SELECT name=SelectCategory> 
     		<option>---SelectPostToSeeCategories---</option>
     		</SELECT>
	       <TD><input type="button" value="  Go " name="btnGo2" onclick="buttonGo1(this.form)"></TD>
		  </TD>
          </tr>
          <TR>
   		 <TD colspan="4">&nbsp;</TD>
  		</TR>
       
       
       
       <TR>
  		<TD  nowrap style="WIDTH: 50%" width="50%"> 
		<IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;Select Test to be Publish
		<SELECT name="SelectTest" style="WIDTH: 150px"> 
		<option>---Select Category---</option>
		</SELECT>
        <IMG src="../gifs/navyblueball.gif" align=bottom border=0>
		    <FONT face=Verdana size=2>&nbsp;<A href="http://localhost:8080/examples/servlet/PublishTest">
		    Publish Test</FONT> </A>
        </td>
      	</tr>
      	<tr>
      	<td>
         
         </td>
         <td>
         
      </td>
      <td>
         <INPUT onclick="back(this.form)" type=button value=" Back << " name="btnBack">
      </td>
      
 </table>
  </FORM>     
 
<% 
      con.close();
 %>
 
 </body>
 </html>