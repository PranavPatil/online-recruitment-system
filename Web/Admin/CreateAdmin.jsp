<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.Model.Admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
   int Admin_Id = -1;
   DataEntryException exception = null;
   Admin admin = null;
   
   try
   {
     String str = null,event = null;
     exception = (DataEntryException) request.getAttribute("Exception");
     System.out.println("Exception = " + exception);
     
     event = request.getParameter("EVENTNAME");
     
     if(exception != null && request.getParameter("Admin_Id")!= null)
	 {
	   str = request.getParameter("Admin_Id");
       
       if(str != null)
        Admin_Id = Integer.parseInt(str);
	 }
	 else if(event.equals("ADMNACC_EDT") && session != null)
     {
       Admin_Id = ((Integer)session.getAttribute("Admin_Id")).intValue();
     }
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     Admin_Id = -1;
     exception = null;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     Admin_Id = -1;
   }
   
   System.out.println("Admin_Id = " + Admin_Id);
   
   if(exception != null)
   {
     admin = new Admin(request);
     System.out.println("fname = " + admin.getFname());
   }
   else if(Admin_Id > 0)
   {
     ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
     admin = new Admin(Admin_Id,conn);
     System.out.println("fname = " + admin.getFname());
   }
   else
     admin = null;
 %>

<HTML>
<HEAD>
<TITLE>Admin Registration Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">

function validate(doEvent)
{
  document.CreateAdmin.EVENTNAME.value=doEvent;
  var pass1 = document.CreateAdmin.Passwd.value;
  var pass2 = document.CreateAdmin.Passwd1.value;

  if(document.CreateAdmin.Fname.value == "")
  {
    alert("Please enter your First name !");
    document.CreateAdmin.Fname.focus();
  } 
  else if(document.CreateAdmin.Lname.value == "")
  {
    alert("Please enter your Last name !");
    document.CreateAdmin.Lname.focus();
  } 
  else if(document.CreateAdmin.Telephone.value == "")
  {
    alert("Please Enter Your Telephone No !");
    document.CreateAdmin.Telephone.focus();
  }  
  else if(document.CreateAdmin.Email.value == "")
  {
    alert("Please enter your Email Id !");
    document.CreateAdmin.Email.focus();
  }
  else if(checkmail(document.CreateAdmin.Email.value) == false)
  {
    alert("The Email Id entered is Invalid !");
    document.CreateAdmin.Email.focus();
  }
  <c:if test="<%= (Admin_Id == -1) %>">
     else if(document.CreateAdmin.Designation.value == "")
     {
       alert("Please select the Designation !");
       document.CreateAdmin.Designation.focus();
     }
  </c:if>
  else if(document.CreateAdmin.Login.value == "")
  {
    alert("Please enter the Login name !");
    document.CreateAdmin.Login.focus();
  }
  else if(pass1== "")
  {
    alert("Please enter the Password !");
    document.CreateAdmin.Passwd.focus();
  }  
  else if(pass1 != pass2)
  {
    alert("Please retype the Password !");
    document.CreateAdmin.Passwd.value = "";
    document.CreateAdmin.Passwd1.value = "";
  }
  else
    document.CreateAdmin.submit();
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

function Button(doEvent)
{
  document.CreateAdmin.EVENTNAME.value=doEvent;
  document.CreateAdmin.submit();
}

</SCRIPT>
</HEAD>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="AdminHeader.jsp" flush="true"/>

      <FORM id="CreateAdmin" name="CreateAdmin" action="/ORS/AdminController" method="post">
      <INPUT TYPE="hidden" name="EVENTNAME">
      <INPUT name='Admin_Id' type='hidden' value = '<%= Admin_Id %>'>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

          <TABLE name='Form' cellSpacing="0" cellPadding="1" width="90%" border="0" align="center">
	       <TR>
		    <TD class="Title1" vAlign="center">&nbsp;Administrator&nbsp;Profile</TD>
	        <TD class="Title1" vAlign="center" Align="middle">&nbsp;</TD></TR>
	       <TR>
		    <TD>&nbsp;</TD><TD>&nbsp;</TD>
	       </TR>
	       
            <c:if test="<%= (exception != null) %>">
               <TR><TD class="LabelA" style="color:#FF3333" colSpan=3>
               Error : <%= exception.getMessage() %><BR><%= exception.getSolution() %>
               </TD><TD></TD></TR>
			   <TR><TD><HR></TD><TD><HR></TD></TR>
            </c:if>
            
	       </TR>
	       <TR>
		    <TD colSpan="2" class="Title3">&nbsp;&nbsp;Please Fill In The Required Details : &nbsp;&nbsp;
            <FONT class="Title5">Filling all the fields is mandatory</FONT></TD>
	       <TR><TD>&nbsp;</TD><TD>&nbsp;</TD></TR>
	       <TR>
		      <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;First Name: </TD>
		      <TD vAlign="top" colSpan=3><INPUT id="Fname" name="Fname" maxLength="20" value='<% if(admin != null) out.print(admin.getFname()); %>' class="breadcrumbs">&nbsp;</TD>
	       </TR>
	       <TR>
		      <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Last Name: </TD>
		      <TD vAlign="top" colSpan=3><INPUT name="Lname" maxLength="20" value='<% if(admin != null) out.print(admin.getLname()); %>' class="breadcrumbs"></TD>
     	   </TR>
	       <TR>
		      <TD vAlign="center" width="27%"></TD>
		      <TD vAlign="top">&nbsp;</TD>
	       </TR>
	       <TR>
		      <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Telephone No: </TD>
		      <TD vAlign="top" colSpan=3><INPUT name="Telephone" maxLength="12" size="20" value='<% if(admin != null) out.print(admin.getTelephone()); %>' class="breadcrumbs"></TD>
	       </TR>
	       <TR>
		      <TD vAlign="center" width="27%"></TD>
		      <TD vAlign="top">&nbsp;</TD>
	       </TR>
	       <TR>
		      <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email: </TD>
		      <TD vAlign="top" colSpan=3><INPUT name="Email" maxLength="30" size="20" value='<% if(admin != null) out.print(admin.getEmail()); %>' class="breadcrumbs"></TD>
           </TR>
	       <TR>
	          <TD vAlign="center" width="27%"></TD>
	          <TD vAlign="top">&nbsp;</TD>
	       </TR>
           <TR>
              <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Designation: </TD>
              <TD vAlign="top">
  <% 
      try
      {
        ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
        Database db = new Database(conn);
		java.sql.ResultSet rs = null;

	   if(Admin_Id == -1)
	   {
 	     out.println("<SELECT name=\"Designation\" style=\"WIDTH: 143px\" class=\"breadcrumbs\">");
 	     out.println("<OPTION>&nbsp;&nbsp;&nbsp;     -- Select --");

         int val = 0;
         rs = db.RetriveDb("Select Access_Id,Designation from Accessibility where Access_Id > 1");

         while(rs.next())
         {
           val = rs.getInt("Access_Id");

           if(admin != null && admin.getAccess_Id() == val)
            out.print("<OPTION value = '" + val + "' selected>");
           else
            out.print("<OPTION value = '" + val + "'>");
           
           out.print(rs.getString("Designation"));
         }       
         out.print("</OPTION>");
         out.println("</SELECT>");
	   }
	   else
	   {
    	 out.println("<FONT class=h1><STRONG>&nbsp;");
		 String Designation = null;
  	
         if(admin != null)
  	     {
           rs = db.RetriveDb("Select Designation from Accessibility where Access_Id = " + admin.getAccess_Id());

           if(rs.next())
           Designation = rs.getString("Designation");
	     }
		   
	     if(Designation != null)
  	       out.print(Designation + "<INPUT name='Designation' type='hidden' value = '" + 
		             admin.getAccess_Id() + "'>");
		 else
		   out.print("Not Available");
		 
		 out.println("</STRONG></FONT>");
	   }
      }
      catch(Exception e)
      {
        out.println(e.getMessage());
      }
  %>
              <BR></TD>
           </TR>
           <TR>
              <TD vAlign="center" width="27%"></TD>
              <TD vAlign="top">&nbsp;</TD>
           </TR>
	       <TR>
		      <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your Login name: </TD>
	          <TD vAlign="top"><INPUT id="Login" name="Login" maxLength="20" value='<% if(admin != null) out.print(admin.getLogin()); %>' <% if(Admin_Id > 0) out.print("readonly"); %> class="breadcrumbs"></TD>
	       </TR>
	       <TR>
		      <TD width="27%">&nbsp;</TD>
		      <TD vAlign="top"><FONT face="ARIAL, HELVETICA" size="-2">Contains only 
		  	   letters (a-z)<BR> numbers (0-9) and underscore (_) </FONT></TD>
	       </TR>
	       <TR>
	          <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Password: </TD>
		      <TD vAlign="top"><INPUT name="Passwd" type="password" maxLength="15" class="breadcrumbs"></TD>
	       </TR>
	       <TR>
		      <TD width="27%">&nbsp;</TD>
		      <TD vAlign="top"><FONT face="ARIAL, HELVETICA" size="-2">To ensure the 
			   security of your account, your <BR> password should be at least four (4) characters. </FONT></TD>
           </TR>
	       <TR>
		      <TD vAlign="center" width="27%" class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Re-Type Password: <BR><BR></TD>
 		      <TD vAlign="top"><INPUT name="Passwd1" type="password" maxLength="15" class="breadcrumbs"><BR></TD>
	       </TR>
      </TABLE>
      <BR><BR>
      <TABLE name='Buttons' cellSpacing="0" cellPadding="0" width="85%" border="0" align="center">
	  <TR>
	    <TD vAlign="center" align="right" width="115"></TD>
		<TD vAlign="top" width="120" height="60">
			<INPUT class="B1" name="Create" type="button" value=" Create " onclick="validate('CREADMN_REG')" size=24> </TD>
		<TD vAlign="top" width="118" height="60">
			<INPUT class="B1" name="reset1" type="reset" value=" Reset " size=24></TD>
		<TD vAlign="top" width="227" height="60">
			<INPUT class="B1" name="Cancel" type="button" value=" Cancel " onclick="Button('CREADMN_CL')" size=24></TD>
      </TR>
      </TABLE>
      </TABLE>
   </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>