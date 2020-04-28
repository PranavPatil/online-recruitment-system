<HTML>
<HEAD>
<TITLE>New Test Settings</TITLE>
<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>

<%
  Connection con=null;
  ResultSet rs=null;
  Statement stmt=null;
  
  try{
  		
		ConnectionPool	pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
  	   	con=pool.getConnection();
		stmt=con.createStatement();
  	   	String query="select * from Test where name='"
  	   	+request.getParameter("SelectTest")
  	   	+"'";
  	   	rs=stmt.executeQuery(query);
  	   	 	   	
     }catch(Exception e)
      {
       out.println(e.getMessage());
       }   
%>


<script language=javascript>

function checkString(form)
{
 if(document.createTest.TestDesc.value.length>200)
	{
		alert("Sorry! Discription Cannot exceed More Than 200 Caracters !!");
	}
}

function timebound(form)
{
  if(document.createTest.TimeBound.selectedIndex==2)
  {
    document.createTest.TestTime.value="Disabled";
    document.createTest.TestTime.disabled=true;
  }
  else
  {
    document.createTest.TestTime.value="";
    document.createTest.TestTime.disabled=false;
  }
}

function Verify(form)
{
  if(document.createTest.TestName.value=="")
  {
    alert("Please! Give the Test Name !!");
    document.createTest.TestName.focus();
  }
  else if(document.createTest.TestDesc.value=="")
  {
    alert("Please! Give the Test Discription !!");
    document.createTest.TestDesc.focus();
  }
  else if(document.createTest.Total_ques.value=="")
  {
    alert("Please! Give the Total Number of Question !!");
    document.createTest.Total_ques.focus();
  }
  else if(document.createTest.MinPassScore.value=="")
  {
    alert("Please! Give the Minimum Passing Score !!");
    document.createTest.MinPassScore.focus();
  }
  else if(document.createTest.categories.selectedIndex==0)
  {
    alert("Please! Give the Category !!");
    document.createTest.categories.focus();
  }
  else if(document.createTest.TimeBound.selectedIndex==0)
  {
    alert("Please! Give the Test Boundation !!");
    document.createTest.TimeBound.focus();
  }

  else
  {
    document.createTest.submit();
  }
}

function funcHide(form)
{

	if(document.createTest.ran[0].checked)
	{
     document.createTest.Ques_type.disabled=true;
     document.createTest.Ques_type.value="Disabled";
    }
    else 
	{
     document.createTest.Ques_type.disabled=false;
     document.createTest.Ques_type.options[0].defaultSelected;
    }
}    
function cancel(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/PreTest.jsp","right1");
}

</script>

</HEAD>
<BODY bgcolor="ivory" leftMargin=50 rightMargin=50>
<TABLE style="WIDTH: 657px; HEIGHT: 27px" cellSpacing=1 cellPadding=1 width=657 
border=0>
  
  <TR>
    <TD style="COLOR: black; BACKGROUND-COLOR: #abcdef">&nbsp;<FONT 
      face=Verdana><STRONG>Test Administration: Edit 
    test</STRONG></FONT></TD></TR></TABLE><br>
<pre><P><FONT face=Arial size=2>
Test Administration allows you to generate tests from a bank of questions organized 
by topic. You can develope  the questions before or after creating the test.Also,  you
can decide whether to test learners with all of the questions or  allow Test 
Administration  to generate tests with some questions from each topic. </FONT> </P>
<P><FONT face=Arial size=2>The topics listed below are included in modules used 
in this course. By associating test questions with a topic.</pre> </FONT></P>

<FORM NAME="createTest" ACTION="http://localhost:8080/examples/servlet/est">
 <%     
     try{
          while(rs.next())
         { 
           
   %>
 
 <STRONG>Test Name</STRONG>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <INPUT name="TestName" size=35 value=<% out.println(rs.getString("name")); %> >
<br><br><STRONG>

Test Description<br></STRONG><TEXTAREA maxlength=250 onchange="checkString(this.form)" name="TestDesc" rows=6 cols=55 >
<% out.println(rs.getString("Description")); %></TEXTAREA>
<br><br><STRONG>Question Type</STRONG>
  &nbsp;&nbsp;&nbsp;&nbsp;
<% int testtype=Integer.parseInt(rs.getString("Test_type"));
	if(testtype==0)
	{
	out.print("<INPUT type=radio CHECKED value=Random name=ran onblur=funcHide(this.form) onclick=funcHide(this.form)>"+
	"Random&nbsp;<INPUT type=radio value=Sequential name=ran onclick=funcHide(this.form) > Select Type");
	}
	else
	if(testtype==1)
	{
	out.print("<INPUT type=radio  value=Random name=ran > "+
	"Random&nbsp;<INPUT type=radio value=Sequential name=ran CHECKED > Select Type");
	}
	%>
	
<SELECT name="Ques_type" > <OPTION 
        selected>Multiple Choice 
        <OPTION>True/False</OPTION>
        <OPTION>Multiple Answer</OPTION>
 	</SELECT><BR>
<P>&nbsp;

<FONT face=Verdana size=2><STRONG>Select 
      Category</STRONG></FONT>
<SELECT name="categories"><%
      try{
      		rs=stmt.executeQuery("select name from category");
     
    %>
   <OPTION>
    <%              
       while(rs.next())
        {       
         
         out.print(rs.getString("name"));
         out.print("<OPTION />");
       }       
     %>
     
    <%       
       }catch(Exception e)
        {
         out.println(e.getMessage());
        }    
    %>
</select>

 </P>
<P>
<TABLE id=TABLE1 cellSpacing=1 cellPadding=1 width="80%" align=left border=0 style="WIDTH: 80%">
  
  <TR>
    <TD>Time Bound&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
    <SELECT  name="TimeBound" onChange="timebound(this.form)"> 
      <OPTION selected>Select
      <OPTION>Yes</OPTION>
      <OPTION>No</OPTION>
    </SELECT></TD>
    <TD style="WIDTH: 35%" width="35%">Maximum Test Time 
      (in minutes)</TD>
    <TD style="WIDTH: 10%" width="10%">
 
 	<% 
 		int itime=Integer.parseInt(rs.getString("TIMED"));
 		System.out.print(rs.getString("TIMED"));
 		if(itime==1)
 		{
 			out.print("<INPUT name=TestTime size=5 type=text value="+Integer.parseInt(rs.getString("timed"))+">");	
 		}
 		else if(itime==0)
 		{
 			out.print("<INPUT name=TestTime size=5 type=text value="+Integer.parseInt(rs.getString("timed"))+">");	
 		}
  	%>
  	</TD></TR>
  <TR>
    <TD style="WIDTH: 35%" width="35%">Total number of 
      questions to ask</TD>
    <TD>
 
 
<INPUT name="Total_ques" size=5 value=<% out.println(rs.getString("total_ques")); %>></TD>
    <TD></TD>
    <TD></TD></TR>
  <TR>
    <TD>Scrolling allowed</TD>
    <TD> 
  <SELECT  id=select1 name="Scrolling">
	<OPTION SELECTED>Yes
	<OPTION>No</OPTION>
</SELECT></TD>
    <TD></TD>
    <TD></TD></TR>
  <TR>
    <TD>Minimum passing score (if any)</TD>
    <TD style="WIDTH: 15%" width="15%" nowrap>
    <INPUT name="MinPassScore" size=5 value=<% out.println(rs.getString("pass_score")); %>> (%)</TD>
    <TD></TD>
    <TD></TD></TR></TABLE>

<br>&nbsp;&nbsp;&nbsp;&nbsp; </P>
<P>
</P>
<P>&nbsp;</P>
<P>&nbsp;</P>
<P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
          <INPUT type="button"  value="Proceed" onClick="Verify(this.form)">&nbsp;&nbsp;&nbsp; 
     <INPUT type="button" value=" Cancel " name="btnCancel" onclick="cancel(this.form)"> </P>
<P><BR>&nbsp; </P>
<%
	   }
	 }catch(Exception e)
	  {
	    out.println(e.getMessage());
	   }
	
	%>
	<%
	try{
	     rs.close();
	     con.close();
	   }catch(Exception e)
	    {
	     out.println(e.getMessage());
	     }
	
	%>
</FORM>

</BODY>
</HTML>
