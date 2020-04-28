<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*"  %>
<%@ page import ="ORS.ConnPool.ConnectionPool" %>

<%
   Connection con=null;
   Statement stmt=null;
   Statement stmt1=null;
   ResultSet rs=null;
   ResultSet rs1=null;

        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
        con=pool.getConnection();
        stmt=con.createStatement();
 %>
<HTML>
<HEAD>
<TITLE>Test</TITLE>
</head>
<SCRIPT language="javascript">

function verify(form)
{
	if(document.EditTest.SelectCategory.value=="---Select Category---")
	return false;
}

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
	      <FONT face=Verdana size=2>Search for a Test in this category</FONT>
          
          
          
          
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
       <% 
        
         String Category=new String(request.getParameter("SelectCategory"));
         
         	Category.trim();
         
           int cat_id=0;
           int test_id=0;
           if(!Category.equals("---Select Category---"))
            { 
       
            stmt=con.createStatement();
            
            rs=stmt.executeQuery("select category_id from category where name='"+Category+"'");
            rs.next();
            cat_id=Integer.parseInt(rs.getString("category_id"));
            out.println("category id "+cat_id);
            
            
            	/*rs1=stmt1.executeQuery("select test_id from test_category where category_id="+cat_id);
            	
          	    while(rs1.next())
         		test_id=Integer.parseInt(rs1.getString("test_id"));
         		rs.close();
            	rs1.close();
            	stmt.close();
            	stmt1.close();
            */           	            
           }
        %>
                      
       <TR>
  		<TD  nowrap style="WIDTH: 50%" width="50%">
		<IMG src="../gifs/navyblueball.gif" align=bottom border=0>&nbsp;
		<SELECT name="SelectTest" style="WIDTH: 300px"> 				
		
		<%		
			stmt=con.createStatement();
			rs=stmt.executeQuery("select name from test where test_id="+test_id);
			out.println("<OPTION>");
			while(rs.next())
		    {       
	  	    out.print(rs.getString("name"));
	 	    out.print("<OPTION />");
		    }       
	     	rs.close();	   	   
		%>
		
        </SELECT>
        </td>
      	</tr>
      	<tr>
      	<td>
         <INPUT type=button onClick="EditCButton(this.form)" value="Edit Test" name=EditTest style="WIDTH: 132px; HEIGHT: 24px" size=22>
         </td>
         <td>
         <INPUT onclick="sure(this.form)" style="WIDTH: 148px; HEIGHT: 24px" type=button size=24 value="Delete Test" name=DelTest>
      </td>
      <td>
         <INPUT onclick="back(this.form)" type=button value=" Back << " name="btnBack">
      </td>
      
 </table>
  </FORM>     
 
<% 
      stmt.close();
      con.close();       
 %>
 
 </body>
 </html>