<%@ page contentType="text/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Exception.ApplicationException" %>

<HTML>
<HEAD>
<TITLE>Choose Test</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="pragma" content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<LINK href="/ORS/Web/images/links.css" type="text/css" rel="stylesheet">
<LINK href="/ORS/Web/images/Style.css"   type="text/css"  rel="stylesheet">
<script language=javascript>

function Button(doEvent)
{
  document.ChooseTest.USEREVENT.value=doEvent;
  document.ChooseTest.submit();
}

</script>
</HEAD>
<BODY>
<jsp:include page="UserHeader.jsp" flush="true"/>

<%
    ConnectionPool pool = (ConnectionPool)getServletContext().getAttribute("ConPool") ;

	ResultSet rs=null,rs1 = null;
	int Post_Id = 0,Cat_Id = 0,C_Id = 0;
	int i = 0,val = 0,count = 0,given = 0,Ddiff = 0;
	long User_Id = 0;
	boolean equal = false;
	String PostName = null;
	 
	ArrayList buffer = new ArrayList();

	session = request.getSession(false);

    User_Id = ((Long)session.getAttribute("User_Id")).longValue();
    Post_Id = ((Integer)session.getAttribute("Post_Id")).intValue();

    if(Post_Id != 0 && User_Id != 0)
    {
      //file.writeLog(User_Id,"Displaying Tests");
	  Database db = new Database(pool);
	    
	  rs = null;	   
	  rs = db.RetriveDb("Select PostName from Post where Post_Id = " + Post_Id);

      if(rs.next())
 	  PostName = rs.getString(1);

      rs = null;
      rs = db.RetriveDb("Select N.Category_Id from Result ,TABLE(Result.TestData)N" + 
                        " where User_Id = " + User_Id + " and Post_Id = " + Post_Id);
      while(rs.next())
      {
        C_Id = rs.getInt(1);
	    
        if(C_Id != 0)
        {
          System.out.println("C_Id = " + C_Id);
          buffer.add(C_Id);
	    }
      }
      
      System.out.println("BBuffer = " + buffer);

	  Database db1 = new Database(pool);
 	  rs1 = null;
 	  rs1 = db1.RetriveDb("Select N.Category_Id from Post,TABLE(Post.CatEntry)N where N.Test_Id != 0 and Post_Id = "
 	                      + Post_Id);
 	  
	  while(rs1.next())
	  {
	    if(count == 0)
	    {
	   	    %>
          <FORM name='ChooseTest' id='ChooseTest' action="/ORS/UserController" method="post">
          <INPUT name="USEREVENT" type="hidden">
            <P><STRONG><FONT >

            <TABLE id=Tests cellSpacing=1 cellPadding=5 width="90%" align=center border=0 >
            <TR vAlign=center align=middle bgColor=black>
            <TD colSpan=6 style="VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center">
            <FONT face=Verdana size=2><STRONG style="COLOR: white; BACKGROUND-COLOR: black">
            Tests for the Post <%= PostName %></STRONG></FONT>
            </TD></TR>
            <TR><TD>&nbsp;</TD></TR></TABLE>

            <TABLE style="WIDTH: 761px; HEIGHT: 27px" cellSpacing=1 cellPadding=1 width=761
                   bgColor=#abcdef border=0 align=center>
            <TR>
            <TD noWrap align=bottom bgColor=#abcdef><CENTER><STRONG>
                Choose from below the test(s) you wish to take and click submit to proceed further:
            </STRONG></CENTER></TD></TR></TABLE>          
            </FONT></STRONG></P><BR><BR>
  	        <UL>
  	        <% 
	   	}

        Cat_Id = rs1.getInt("Category_Id");
	    System.out.println("ID = " + Cat_Id);
	      
	    i=0;
	    equal = false;
	      
	    while(equal != true & i < buffer.size())
	    {
	 	  val = (Integer)buffer.get(i);

	 	  if(val == Cat_Id)
	 	  {
	 	    equal = true;
	 	    System.out.println("Already Attempted !!");
	 	  }

	 	  i++;
	    }
	      
	    out.println("<LI>");
        out.println("<DIV>");
          
        rs = null;
        rs = db.RetriveDb("select Name from Category where Category_Id = " + Cat_Id);
        
        if(rs.next())
        {
          if(equal == true)
          {
            out.println("<INPUT id = \"Cat" + (given + 1) + "\" name=\"Category\" type=\"radio\" value=\"" + 
                  Cat_Id + "\" disabled>&nbsp;&nbsp;<FONT color=blue><STRONG>" + rs.getString("Name"));
            given++;
          }
          else
          {
            out.println("<INPUT id = \"Cat" + (given + 1) + "\" name=\"Category\" type=\"radio\" value=\"" + 
                  Cat_Id + "\">&nbsp;&nbsp;<FONT color=blue><STRONG>" + rs.getString("Name"));         	
          }
        }

        out.println("</STRONG></FONT><br></A></DIV>");
        count++;
	  }
	  
   	  rs1.close();
	  rs.close();

	  out.println("</UL>");
	}
   %>
      <BR>
      <TABLE cellSpacing=1 cellPadding=1 width=460 align=center border=0 style="WIDTH: 460px; HEIGHT: 32px">
      <TBODY>
       <TR>
       <TD noWrap align=right>
       <INPUT name='TestSubmit' type='button' value=" Submit " onClick="Button('TESTSUB')"
        style="WIDTH: 101px; HEIGHT: 26px" size=26></TD>
       <TD noWrap>&nbsp;&nbsp;&nbsp;&nbsp;
       <INPUT name='Back' type='button' value=" << Back " onClick="Button('CUROPENG')"
              style="WIDTH: 99px; HEIGHT: 27px" size=25>
       </TD></TR></FORM></TBODY></TABLE>
  <jsp:include page="UserFooter.jsp" flush="false"/>
</BODY>
</HTML>
