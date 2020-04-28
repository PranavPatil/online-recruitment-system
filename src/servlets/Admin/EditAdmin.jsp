<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%
  Connection con=null;
  ResultSet rs=null;
  Statement stmt=null;
  String str = null;
  int DAdmin_Id = 0;
  
  try{  		
		ConnectionPool	pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
  	   	con=pool.getConnection();
		stmt=con.createStatement();				
		
        try
    	{
		  str=request.getParameter("SelectAdmin");
  	  	  System.out.println("Admin_name-->>"+str);
          DAdmin_Id = Integer.parseInt(str);    
          System.out.println("Admin_Id = " + DAdmin_Id); 	
    	}
    	catch (NumberFormatException exp)
    	{
      	  System.out.println("Problem in Admin_Id");
          System.err.println(exp.getMessage());
        }					
		
  	   	String query="select * from admin where Admin_Id = " + DAdmin_Id;
  	   	rs=stmt.executeQuery(query);
  	   	 	   	
     }catch(Exception e)
      {
       out.println(e.getMessage());
       }   
%>

<HTML>
	<HEAD>
		<TITLE>Admin Registration Page</TITLE>
		<META http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<script language="JavaScript">
 
function validate()
{
  var pass1 = document.form1.Passwd.value;
  var pass2 = document.form1.Passwd1.value;

  if(document.form1.Fname.value == "")
  {
   alert("Give Your First name !!");
   document.form1.Fname.focus();
   return false;  
  } 
  else if(document.form1.Lname.value == "")
  {
   alert("Give Your Last name !!");
   document.form1.Lname.focus();
   return false;  
  } 
  else if(document.form1.Email.value == "")
  {
   alert("Give Your Email Id !!");
   document.form1.Email.focus();
   return false;  
  }
  else if(document.form1.Login.value == "")
  {
   alert("Give Your Login name !!");
   document.form1.Login.focus();
   return false;  
  }
  else if(pass1== "")
  {
   alert("Give Your Password  !!");
   document.form1.Passwd.focus();
   return false;  
  }  
  else if(pass1 != pass2)
  {
   alert("Type Your Password Again !!");
   document.form1.Passwd.value = "";
   document.form1.Passwd1.value = "";
   return false;
  } 
  else if(document.form1.Email.value!="")
  {
 	var result=checkmail(document.form1.Email.value);
 	if(result==false)
 	{
 	    alert("The MailID is not valid.");
 	    document.form1.Email.focus();
 		return false; 
 	}	
 	else
 		return true;	
  }           
  else
   return true;
}

// VALIDATE E-MAIL
function checkmail(emailadd)
{
	var result = false;
  	var theStr = new String(emailadd);
  	var index = theStr.indexOf("@");
  	if (index > 0)
  	{
    	var pindex = theStr.indexOf(".",index);
    	if ((pindex > index+1) && (theStr.length > pindex+2))
		result = true;
  	}
  		
  	return result;
}
function cancel(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/AdminOption.jsp","right1");
}
		</script>
	</HEAD>
	<BODY bgcolor="ivory">
		<FORM action="http://localhost:8080/examples/servlet/EditAdmin" id="form1" name="form1" method="post" onsubmit="return validate()">
		
	<%		
		try{
		  while(rs.next())
		 { 		   
	 %>
   
			<TABLE cellSpacing="0" cellPadding="0" width="580" border="0" align="left">
				<TBODY>
					<TR>
						<TD width="580">
							<TABLE cellSpacing="0" cellPadding="0" width="580" border="0" align="left">
								<TR>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
								<TR bgColor="#abcdef">
									<TD colSpan="2"><FONT face="Arial" color="#000000" size="-1"> <STRONG>&nbsp;&nbsp;Edit 
											the Required Information you want to update</FONT></STRONG>
									</TD>
								</TR>
								<TR>
									<TD colSpan="2">
									<FONT face="Arial" color="#cc0033" size="3">
									<pre>          Filling all the fields are compulsory</pre>
										</FONT>
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">First 
											Name: </FONT>
									</TD>
									<TD vAlign="top" align="left" width="400"><INPUT id="Fname" maxLength="20" name="Fname" value=<% out.println(rs.getString("Fname")); %>>&nbsp;</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Last 
											Name: </FONT>
									</TD>
									<TD vAlign="top" align="left" width="400"><INPUT maxLength="20" name="Lname" value=<% out.println(rs.getString("lname")); %>>
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"></FONT></TD>
									<TD vAlign="top" width="400">&nbsp;
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Email:
										</FONT>
									</TD>
									<TD vAlign="top" align="left" width="400"><INPUT maxLength="30" size="20" name="Email" value=<% out.print(rs.getString("Email")); %>>
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"></FONT></TD>
									<TD vAlign="top" width="400">&nbsp;
									</TD>
								</TR>								
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Your 
											Login name: </FONT>
									</TD>
									<TD vAlign="top" width="400"><INPUT id="Login" maxLength="20" name="Login" value=<%out.println(rs.getString("Login"));%> readonly></TD>
								</TR>
								<TR>
									<TD width="180">&nbsp;
									</TD>
									<TD vAlign="top" width="400"><FONT face="ARIAL, HELVETICA" size="-2">Contains only 
											letters (a-z)<BR>
											numbers (0-9) and underscore (_) </FONT>
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Password:
										</FONT>
									</TD>
									<TD vAlign="top" width="400"><INPUT type="password" maxLength="15" name="Passwd"></TD>
								</TR>
								<TR>
									<TD width="180">&nbsp;
									</TD>
									<TD vAlign="top" width="400"><FONT face="ARIAL, HELVETICA" size="-2">To ensure the 
											security of your account, your
											<BR>
											password should be at least four (4) characters. </FONT>
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Re-Type 
											Password: </FONT>
										<BR>
										<BR>
									</TD>
									<TD vAlign="top" width="400"><INPUT type="password" maxLength="15" name="Passwd1">
										<BR>
									</TD>
								</TR>
								<TR>
									<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">&nbsp;
										</FONT>
									</TD>
									<TD vAlign="top" width="400" height="60"><FONT face="ARIAL, HELVETICA" size="-1">
											<BR>
											<INPUT type="submit" value=" Update!! " name="submit"> 
											<INPUT type="reset" value="Reset" name="reset">
											<INPUT type="button" value=" Cancel " name="Cancel" onclick="cancel(this.form)">
										</FONT>
										<BR>
									</TD>
								</TR>
							</TABLE>
		</FORM>
		</TR></TBODY></TABLE>
			
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
		
</BODY>
</HTML>
