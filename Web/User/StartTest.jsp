<%@ page contentType="text/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Exception.ApplicationException" %>

<HTML>
<HEAD>
<TITLE>Start Test</TITLE>
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
  document.StartTest.USEREVENT.value=doEvent;
  document.StartTest.submit();
}

</script>
</HEAD>
<BODY>
<jsp:include page="UserHeader.jsp" flush="true"/>

 <%
	 long User_Id = 0;
	 int Test_Id = 0,time=0,Test_type=0,Total_Ques=0;
	 String Ques_Type = null,Timed = null;
	 Connection con=null;	
	 Statement smt = null;
	 ResultSet rs=null;
     
     session=request.getSession(false);
     User_Id = (Long)session.getAttribute("User_Id");
     Test_Id = (Integer)session.getAttribute("Test_Id");
     
     if(Test_Id == 0 | User_Id == 0)
     {
     	System.out.println("Error in Session !!");
     	//file.writeLog(0,"Error in User Session");
     }

     System.out.println("StartTest -> Test_Id = "+ Test_Id);
 %>
     <P><BR><BR>
     <TABLE cellSpacing=0 cellPadding=15 width="629" align="center"
            bordercolor="blue" bgColor="#abcdef" border=1 style="WIDTH: 629px; HEIGHT: 232px">
     <TR><TD><FONT face=Arial><FONT size=2><STRONG>
     <FONT Color=blue size='+1'>
     <CENTER>Start Test Warning</CENTER></FONT></U></STRONG>
     </STRONG></FONT></FONT><br>
 <%
     //file.writeLog(User_Id,"Displaying Test Description");

	 ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
     Database db = new Database(pool);

	 rs = db.RetriveDb("Select * from Test where Test_Id = " + Test_Id);
	 
	 if(rs.next())
	 {
	   //Timed = rs.getInt("Timed");
	   
	   time = rs.getInt("Time");
	   Timed = "" + time + " Minutes";
	   
	   Total_Ques = rs.getInt("Total_Ques");
	   
	   Test_type = rs.getInt("Test_Type");
	   
	   if( Test_type == 0 )
	   {
	      Ques_Type  = rs.getString("Ques_Type");
	      
	      if(Ques_Type != null && Ques_Type.equals("MC"))
	        Ques_Type = "Multiple Choice";
	      else if(Ques_Type != null && Ques_Type.equals("TF"))
	        Ques_Type = "True or False";
	      else if(Ques_Type != null && Ques_Type.equals("MA"))
	        Ques_Type = "Multiple Answer";
	      else
	        Ques_Type = "Random";
	   }
	   else 
	     Ques_Type = "Random"; 
	 }
  %>

     You have read and accepted to the terms and conditions related for this test.
     The test comprises of <%= Total_Ques %> <%= Ques_Type %> questions to be completed in 
     <%= Timed %>
     We wish you good luck. You may proceed now by pressing the 'Start Test' button below.<BR><BR><BR>

 <FORM name="StartTest" id='StartTest' action="/ORS/UserController" method="post">
 <INPUT name="USEREVENT" type="hidden">
 <CENTER><INPUT name='StartTest' type='button' value="Start Test" onClick="Button('TSTPAGE')"></CENTER>
 </FORM>
 </TD></TR>
 </TABLE>  
 <BR>
 </P>
  <jsp:include page="UserFooter.jsp" flush="false"/>
 </BODY></HTML>