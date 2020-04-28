<HTML>
	<HEAD>
		<TITLE>Create Post</TITLE>
<%@ page contentType="text/html"%>
<%@ page import="java.sql.*,java.util.*,java.io.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<% 
   Connection con=null;
   Statement smt=null;
   ResultSet rs=null;
   try{
        ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
        con=pool.getConnection();
        smt=con.createStatement();
       }catch(Exception  e)
        {
         out.println(e.getMessage());
        }
 %>
<script language="JavaScript">
 function validate(form)
 {
   if(document.form1.Postname.value=="")
   { 
     alert("Please! fill the PostName Feild.");
     document.form1.Postname.focus();
     return false;
   }
   else if(document.form1.Avg.value=="")
   { 
     alert("Please! give Average %age Requirement .");
     document.form1.Avg.focus();
     return false;
   }
   else if(document.form1.vacancy.value=="")
   { 
     alert("Please! give Vacancy For Post .");
     document.form1.vacancy.focus();
     return false;
   }
   else 
   document.form1.submit();
 }


function cancel(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/HomePage.html","right1");
}
</script>
</HEAD>

<BODY bgcolor="ivory">
<FORM action="http://localhost:8080/examples/servlet/CreatePost" id="form1" name="form1" method="post">
<TABLE cellSpacing="0" cellPadding="0" width="580" border="0" align="left">
<TBODY>
<TR>
<TD width="580">
	<TABLE cellSpacing="0" cellPadding="0" width="580" border="0" align="left">
		<TR>
			<TD vAlign="center" width="490" bgColor="##bcdde" height="35">
				<FONT face="arial" color="#000000" size="4"><STRONG>&nbsp;Post 
						Creation</STRONG></FONT> </FONT></TD>
			<TD vAlign="center" align="middle" width="90" bgColor="##bcdde">&nbsp;</FONT>
			</TD>
		</TR>
		<TR>
			<TD>&nbsp;</TD>
			<TD>&nbsp;</TD>
		</TR>
		<TR bgColor="#abcdef">
			<TD colSpan="2"><FONT face="Arial" color="#000000" size="-1"> <STRONG>&nbsp;&nbsp;Please 
					Fill In The Required Information</FONT></STRONG>
			</TD>
		</TR>
		<TR>
			<TD colSpan="2"><FONT face="Arial" color="#cc0033" size="3"><pre>            Filling all the fields are compulsory</pre>
				</FONT>
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Post 
					Name: </FONT>
			</TD>
			<TD vAlign="top" align="left" width="400"><INPUT maxLength="29" name="Postname">&nbsp;</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Avg. 
					%age Requirement:</FONT>
			</TD>
			<TD vAlign="top" align="left" width="400"><INPUT maxLength="29" name="Avg">
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"></FONT></TD>
			<TD vAlign="top" width="400">&nbsp;
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Qualification:
				</FONT>
			</TD>
			<TD vAlign="top" width="400"><SELECT name=Qualification> 
                <OPTION value=Diploma selected>--Select Qualification--<option>
            Diploma<OPTION 
		    value=BE>B.E<OPTION value=ME>M.E<OPTION 
		    value=BCA>BCA<OPTION value=MCA>MCA<OPTION 
		    value=Bsc>Bsc<OPTION value=Msc>Msc<OPTION 
		    value=MBA>MBA<OPTION value=BCom>B Com<OPTION 
		    value=MCom>M Com<OPTION value=CA>C.A<OPTION 
		    value=BTECH>BTech<OPTION value=MTECH>MTech<OPTION
                    value=PHD>Ph D</OPTION></SELECT>
				<BR>
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"></FONT></TD>
			<TD vAlign="top" width="400">&nbsp;
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Experience:
				</FONT>
			</TD>
			<TD vAlign="top" align="left" width="400">
			<SELECT name="experience" ID="Select1">
							<OPTION value="1" selected>
							Fresher<OPTION value="2">
							1-2yrs<OPTION value="3">
							2-5yrs<OPTION value="4">
							5-10yrs<OPTION value="5">
								above 10yrs</OPTION>
						</SELECT>
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"></FONT></TD>
			<TD vAlign="top" width="400">&nbsp;
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">
				Branch: </FONT>
			<td>
			<SELECT name=Branch> <OPTION 
                    value=General>General<OPTION value=Computer>Computer<OPTION 
		    value=Electronics>Electronics<OPTION value=Commerce>Commerce<OPTION 
		    value=Management>Management<OPTION value=Science>Science<OPTION 
		    value=Networking>Networking<OPTION value=Programming>Programming<OPTION 
		    value=Software>Software<OPTION value=IS>Information Systems<OPTION 
		    value=Logistics>Logistics<OPTION value=Economics>Economics<OPTION 
		    value=Instrumentation>Instrumentation<OPTION value=Telecommunication>Telecommunication<OPTION
                    value=Mathematics>Mathematics</OPTION></SELECT> 
			</td>
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"></FONT></TD>
			<TD vAlign="top" width="400">&nbsp;
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">
				Vacancy: </FONT>
			<td><input type="text" name=vacancy>	</td>
			</TD>
		</TR>
		<TR>
			<TD width="180">&nbsp;
			</TD>
			<TD vAlign="top" width="400"><FONT face="ARIAL, HELVETICA" size="-2">Select number 
			of vacancies<BR>
					for the particular Post </FONT>
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Choose Category:
				</FONT></td>
		<td>
			<select name="select2" style="WIDTH: 185px" multiple >
			  <%
			      try{
			      		rs=smt.executeQuery("select name from category");
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
		</TR>
		<TR>
			<TD width="180">&nbsp;
			</TD>

				<BR>
			</TD>
		</TR>
		<TR>
			<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">&nbsp;
				</FONT>
			</TD>
			<TD vAlign="top" width="400" height="60"><FONT face="ARIAL, HELVETICA" size="-1">
					<BR>
					<INPUT type="button" value="   Done  " name="btnsubmit" onclick="validate(this.form)"> 
					<INPUT type="reset" value="Reset" name="reset">
					<INPUT type="button" value=" Cancel " name="Cancel" onclick="cancel(this.form)">
				</FONT>
				<BR>
			</TD>
		</TR>
	</TABLE>
</FORM>
</TR></TBODY></TABLE>
</BODY>
</HTML>
