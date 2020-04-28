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
<HTML>
<HEAD>

<script language=javascript>
///////////////////////////////FUNCTION checkString//////////////
function checkString(form)
{
 if(document.createTest.TestDesc.value.length>200)
	{
		alert("Sorry! Discription Cannot exceed More Than 200 Caracters !!");
	}
}
///////////////////////////////FUNCTION TimeBound//////////////
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
///////////////////////////////FUNCTION Verify//////////////
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
///////////////////////////////FUNCTION Hide//////////////
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
    }
}    
//////////////////////////////////////////////////////////
</script>
</HEAD>
<BODY bgcolor="ivory" leftMargin=50 rightMargin=50>
<TABLE style="WIDTH: 657px; HEIGHT: 27px" cellSpacing=1 cellPadding=1 width=657 
border=0>
  
  <TR>
    <TD style="COLOR: black; BACKGROUND-COLOR: #abcdef">&nbsp;<FONT 
      face=Verdana><STRONG>Test Administration: Create a new 
    test</STRONG></FONT></TD></TR></TABLE><br>
<P><FONT face=Arial size=2>Test Administration allows you to generate tests from a bank of 
questions organized by topic. You can develop<br>
the questions before or after creating the test. Also, you can decide whether to test
learners with all of the<br>
questions or allow Test Administration to generate tests with some questions from each
topic. For example,<br>
you might build 30 questions for Topic A and want each learner to see only between 5
and 8 of them in your test.</FONT> </P>
<P><FONT face=Arial size=2>The topics listed below are included in modules used 
in this course. By associating test questions with a topic,<br>
Test Administration allows you to reuse test questions. It also can help learners
<I>pinpoint troublesome topics for<br>
further review.</I> </FONT></P>

<FORM NAME="createTest" ACTION="http://localhost:8080/examples/servlet/CreateTest">
 
 <%     
     try
     {
          while(rs.next())
         {            
  %>
 
 
 <STRONG>Test Name</STRONG>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 <INPUT name="TestName" size=35 value=<% out.println(rs.getString("name")); %> >
<br><br><STRONG>
Test Description<br></STRONG>
<TEXTAREA maxlength=2 onchange="checkString(this.form)" name="TestDesc" rows=6 cols=55 >
<% out.println(rs.getString("Description")); %>
</TEXTAREA>
<br><br><STRONG>Question Type</STRONG>
  &nbsp;&nbsp;&nbsp;&nbsp;
	<% String sex=rs.getString("Test_type");
	if(sex.equalsIgnoreCase("Random"))
	{
	out.print("<INPUT type=radio CHECKED value=Random name=ran>"+
	"Random&nbsp;<INPUT type=radio value=Sequential name=ran> Select Type");
	}
	else
	if(sex.equalsIgnoreCase("Sequential"))
	{
	out.print("<INPUT type=radio  value=Random name=ran> "+
	"Random&nbsp;<INPUT type=radio value=Sequential name=ran CHECKED> Select Type");
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
    <TD>Time Bound</TD>
    <TD style="WIDTH: 10%" width="10%">
    <SELECT  name="TimeBound" onChange="timebound(this.form)"> 
      <OPTION selected>Select
      <OPTION>Yes</OPTION>
      <OPTION>No</OPTION>
    </SELECT></TD>
    <TD style="WIDTH: 35%" width="35%">Maximum Test Time 
      (in minutes)</TD>
    <TD style="WIDTH: 10%" width="10%">
 
<INPUT type=integer name="TestTime" value=<% out.println(Integer.parseInt(rs.getString("time")));%></TD></TR>
  <TR>
    <TD style="WIDTH: 35%" width="35%">Total number of 
      questions to ask</TD>
    <TD>
 
 
<INPUT type=text name="Total_ques" size=5 value=<% out.println(rs.getString("total_ques")); %>></TD>
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
     <INPUT type=submit value=Cancel name=BackToTestHome> </P>
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
