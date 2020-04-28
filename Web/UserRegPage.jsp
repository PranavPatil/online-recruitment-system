<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.Model.User" %>

<% 
   long User_Id = -1;
   DataEntryException exception = null;
   User user = null;
   
   try
   {
     String str = null,event = null;
     exception = (DataEntryException) request.getAttribute("Exception");
     System.out.println("Exception = " + exception);
     
     event = request.getParameter("USEREVENT");
     
     if(exception != null && request.getParameter("User_Id")!= null)
	 {
	   str = request.getParameter("User_Id");
       
       if(str != null)
        User_Id = Long.parseLong(str);
	 }
	 else if(event.equals("USUPDATE") && session != null)
     {
       User_Id = ((Long)session.getAttribute("User_Id")).longValue();
     }
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     User_Id = -1;
     exception = null;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     User_Id = -1;
   }
   
   System.out.println("User_Id = " + User_Id);
   
   if(exception != null)
   {
     user = new User(request);
     System.out.println("fname = " + user.getFname());
   }
   else if(User_Id > 0)
   {
     ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
     System.out.println("Id = " + User_Id);
     user = new User(User_Id,conn);
     System.out.println("fname = " + user.getFname());
   }
   else
     user = null;
 %>

<HTML><HEAD><TITLE>User Registration Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
<META content="MSHTML 6.00.2800.1276" name=GENERATOR>
<META content="User Registration Page" name=author>
<LINK href="/ORS/Web/images/links.css" type=text/css rel=stylesheet>
<LINK href="/ORS/Web/images/spiffyCal.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/ORS/Web/images/spiffyCal.js"></script>
<script language="JavaScript">

///////////////////////////////FUNCTION VALIDATE//////////////

var cal1=new ctlSpiffyCalendarBox("cal1", "UserReg", "txtDate","btnDate1","");

function validate()
{
  var flagfilled=filled(this.UserReg)
  
  if(flagfilled==true)
  { 
    var phno = checkno(document.UserReg.Telephone.value);
    
    if(phno== false)
    {
      alert("The Telephone No is not valid !");
      document.UserReg.Telephone.focus();
    }
    else
    {
      var mail = checkmail(document.UserReg.Email.value);
      
      if(mail==false)
      {
        alert("The Mail ID is not valid !");
        document.UserReg.Email.focus();
      }
      else
      {
        document.UserReg.submit();        
      }
    }
  }
}

///////////////////////////////// VALIDATE E-MAIL///////////////////

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

///////////////////////////////// CHECK NUMBERS ///////////////////

function checkno(teleno)
{
  if (isNaN(teleno))
    return false;  
  else if(teleno.length < 7)
    return false;
  else
    return true;
}

//////////////////////////////// FILLED FUNCTION /////////////////////////////////

function filled(form)
{  
  var pass1 = document.UserReg.Passwd.value;
  var pass2 = document.UserReg.Passwd1.value;

  document.UserReg.Bdate.value = cal1.getSelectedDate();

  if(document.UserReg.Fname.value == "")
  {
   alert("Please Enter Your First Name !");
   document.UserReg.Fname.focus();
   return false;  
  } 
  else if(document.UserReg.Mname.value == "")
  {
   alert("Please Enter Your Middle Name !");
   document.UserReg.Mname.focus();
   return false;  
  } 
  else if(document.UserReg.Lname.value == "")
  {
   alert("Please Enter Your Last Name !");
   document.UserReg.Lname.focus();
   return false;  
  } 
  else if(document.UserReg.Address.value == "")
  {
   alert("Please Enter Your Address !");
   document.UserReg.Address.focus();
   return false;  
  }
  else if(document.UserReg.Telephone.value == "")
  {
   alert("Please Enter Your Telephone No !");
   document.UserReg.Telephone.focus();
   return false;  
  }  
  else if(document.UserReg.Email.value == "")
  {
   alert("Please Enter Your Email Id !");
   document.UserReg.Email.focus();
   return false;  
  } 
  else if(document.UserReg.Bdate.value == "")
  {
   alert("Please Select your Birth Date !");
   return false;  
  }
  else if(document.UserReg.Qualification.value == "")
  {
   alert("Please Select Your Qualification !");
   document.UserReg.Qualification.focus();
   return false;  
  }
  else if(document.UserReg.Branch.value == "")
  {
   alert("Please Select Your Branch !");
   document.UserReg.Branch.focus();
   return false;  
  }
  else if(document.UserReg.Experience.value == "")
  {
   alert("Please Select Your Experience !");
   document.UserReg.Experience.focus();
   return false;  
  }  
  else if(document.UserReg.Login.value == "")
  {
   alert("Please Enter Your Login Name !");
   document.UserReg.Login.focus();
   return false;  
  } 
  else if(document.UserReg.Passwd.value == "")
  {
   alert("Please Enter Your Password !");
   document.UserReg.Passwd.focus();
   return false;  
  } 
  else if(document.UserReg.Passwd.value != document.UserReg.Passwd1.value)
  {
   alert("Please Retype Your Password !");
   document.UserReg.Passwd.value = "";
   document.UserReg.Passwd1.value = "";
   document.UserReg.Passwd.focus();
   return false;
  }   
  else           
   return true;
}

function cancel(form)
{
  document.UserReg.action="/ORS/Web/UserLogin.jsp";
  document.UserReg.submit();
}

</script>
</HEAD>
<BODY style="MARGIN: 0px">
<div id="spiffycalendar" class="text" style="WIDTH: 43px; HEIGHT: 24px"></div>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50>
        <TBODY></TBODY></TABLE><IMG 
      height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 
      align=top>
      
      <jsp:include page="Header.jsp" flush="true"/>
      
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>

          <jsp:include page="Panel.jsp" flush="true"/>

          <IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=63>
            <DIV align=left><A name=content></A>
            <TABLE height=10 cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD height=5>&nbsp;</TD></TR>
              <TR>
            <TD height=5><IMG height=20 alt=Recruitment 
                  src="/ORS/Web/images/recruitment.gif" 
              width=625></TD></TR></TBODY></TABLE></TD></TR>

            <TR><TD>
            <FORM id="UserReg" name="UserReg" method="post" action="/ORS/UserReg" > 
            <% 
               if(exception != null) 
               {
                 out.println("<BR>");
                 out.println("<BLOCKQUOTE><H3>Error : " + exception.getMessage() + "<BR>");
                 out.println(exception.getSolution() + "</H3></BLOCKQUOTE>");
                 out.println("<HR>");
               } 
            %>
              <TABLE cellSpacing=0 cellPadding=0 width=480 border=0 align=left>
	          <TR bgColor="#abcdef">
	            <TD colSpan="2"><FONT face="Arial" color="#000000" size="-1"> <STRONG>&nbsp;&nbsp;Registration Form</STRONG></FONT>
	            </TD></TR>
	          <TR>
	            <TD colSpan="2" align="center"><SPAN class=h1><FONT color="#cc0033">Filling all the fields is compulsory</FONT></SPAN>
		        </TD></TR>
              <TR>
                <TD colSpan=2>&nbsp;&nbsp; </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">First Name &nbsp;&nbsp;</TD>
                <TD vAlign=top align=left width=400><INPUT id=Fname maxLength=20 
                  name="Fname" value='<% if(user != null) out.print(user.getFname()); %>' class="breadcrumbs">&nbsp;</TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Middle Name &nbsp;&nbsp;</TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=20 
                  name="Mname" value='<% if(user != null) out.print(user.getMname()); %>' class="breadcrumbs"> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Last Name &nbsp;&nbsp;</TD>
                <TD vAlign=top width=400><INPUT maxLength=20 name="Lname"
                 value='<% if(user != null) out.print(user.getLname()); %>' class="breadcrumbs"></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Gender &nbsp;&nbsp;</TD>
                <TD vAlign=center width=400><FONT face="ARIAL, HELVETICA" 
                  size=-1>
                  <%
                     if(user != null)
                     {
                       String gender = user.getGender();
                       
                       if(gender != null && gender.equals("M"))
                       {
                         out.print("<INPUT type=radio CHECKED value='M' name=gender>"+
                         "Male&nbsp;<INPUT type=radio value='F' name=gender> Female");
                       }
                       else if(gender != null && gender.equals("F"))
                       {
                         out.print("<INPUT type=radio  value='M' name=gender> "+
	                     "Male&nbsp;<INPUT type=radio value='F' name=gender CHECKED> Female");
                       }
                       else
                       {
                         out.print("<INPUT type=radio  value='M' name=gender> "+
					     "Male&nbsp;<INPUT type=radio value='F' name=gender> Female");
                       }
                     }
                     else
                     {
                       out.print("<INPUT type=radio  value='M' name=Gender> "+
					   "Male&nbsp;<INPUT type=radio value='F' name=Gender> Female");
                     }
                  %>
                  </FONT> <BR></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Address &nbsp;&nbsp;</TD>
                <TD vAlign=top align=left width=400><TEXTAREA id=Address name="Address" rows=3 cols=27 class="breadcrumbs"><%
                 if(user != null) out.print(user.getAddress()); %></TEXTAREA>&nbsp;</TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Telephone No &nbsp;&nbsp;</TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=12 
                  size="22" name="Telephone" value='<% if(user != null) out.print(user.getTelephone()); %>' class="breadcrumbs"> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Email &nbsp;&nbsp;</TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=30 
                  size="22" name="Email"  value='<% if(user != null) out.print(user.getEmail()); %>' class="breadcrumbs"> </TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>

              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Birth Date &nbsp;&nbsp;</TD>
 			    <TD vAlign=top width=400>
			    <SCRIPT language="JavaScript"> cal1.writeControl();
			    <% if(user != null) out.println("cal1.setSelectedDate(\"" + user.getBirthdate() + "\");"); %>
			    </SCRIPT><INPUT name='Bdate' id='Bdate' type='hidden' value='<% if(user != null) out.print(user.getBirthdate()); %>'></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Qualification &nbsp;&nbsp;</TD>
                <TD vAlign=top width=400 class="LabelA"><SELECT name="Qualification" class="breadcrumbs"> 
 				<% 
				   String var = "";
				   
				   if(user != null)
				     var = user.getQualification();
				   else
				     out.println("<OPTION>-- Select --");
				   
				   ArrayList buffer = new ArrayList();
				   buffer.add("Diploma");
				   buffer.add("BE");
				   buffer.add("ME");
				   buffer.add("BCA");
				   buffer.add("MCA");
				   buffer.add("Bsc");
				   buffer.add("Msc");
				   buffer.add("MBA");
				   buffer.add("BCom");
				   buffer.add("MCom");
				   buffer.add("CA");
				   buffer.add("Btech");
				   buffer.add("Mtech");
				   buffer.add("Phd");
				   
				   int i = 0;
				   while(i < buffer.size())
				   {
				    out.println("<OPTION value= \"" + buffer.get(i) + "\" ");
				    
				    if(buffer.get(i).equals(var))
				        out.println("selected");
				      
				    out.println(">" + buffer.get(i));
				    i++;
				   }
				%>
				</OPTION></SELECT> 
               &nbsp;&nbsp;&nbsp; Branch &nbsp;
            <SELECT name="Branch" class="breadcrumbs">
            <% 
			   var = "";

			   if(user != null)
			     var = user.getBranch();
			   else
			     out.println("<OPTION>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;----  Select  ----");
			   
			   ArrayList buff = new ArrayList();
			   buff.add("General");
			   buff.add("Computer");
			   buff.add("Electronics");
			   buff.add("Commerce");
			   buff.add("Management");
			   buff.add("Science");
			   buff.add("Networking");
			   buff.add("Programming");
			   buff.add("Software");
			   buff.add("Information Systems");
			   buff.add("Logistics");
			   buff.add("Economics");
			   buff.add("Instrumentation");
			   buff.add("Telecommunication");
			   buff.add("Mathematics");
			   
			   int k = 0;
			   while(k < buff.size())
			   {
			    out.println("<OPTION value= \"" + buff.get(k) + "\" ");
			    
			    if(buff.get(k).equals(var))
			       out.println("selected");
			    
			    out.println(">" + buff.get(k));
			    k++;
			   }
            %>
         </OPTION></SELECT> 
                  </TD>
              </TR>              

              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
				<TR>
					<TD vAlign="center" align="right" width="180" class="LabelA">Experience &nbsp;&nbsp;
					</TD>
					<TD vAlign="top" width="400"><SELECT name="Experience" ID="Select1" class="breadcrumbs">
					<%
					   int no = 0;
					   
					   if(user != null)
					     no = user.getExperience();
					   else
					     out.println("<OPTION>-- Select --");
					   
					   if(no == 1)
   						 out.println("<OPTION value= \"1\" selected>Fresher");
					   else
					     out.println("<OPTION value= \"1\">Fresher");
					     
					   if(no == 2)
					     out.println("<OPTION value=\"2\" selected>1-2yrs");
					   else
					     out.println("<OPTION value=\"2\">1-2yrs");
					     
					   if(no == 3)
					     out.println("<OPTION value=\"3\" selected>2-5yrs");
					   else
					     out.println("<OPTION value=\"3\">2-5yrs");
					     
					   if(no == 4)
					     out.println("<OPTION value=\"4\" selected>5-10yrs");
					   else
					     out.println("<OPTION value=\"4\">5-10yrs");
					     
					   if(no == 5)
					     out.println("<OPTION value=\"5\" selected>above 10yrs");
					   else
					     out.println("<OPTION value=\"5\">above 10yrs");
					%>
					</OPTION>
					</SELECT>
					<BR>
				</TD>
				</TR>
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2><br>Login Name should be unique</FONT></TD></TR>                
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Login Name &nbsp;&nbsp;
                </TD>
                <TD vAlign=top width=400><INPUT id=Login maxLength=20 
                    name="Login" <% if(user != null) out.print("value='" + user.getLogin() + "' readonly"); %> class="breadcrumbs"><BR></TD></TR>
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2>Contains only letters (a-z)<BR>numbers (0-9) and 
                  underscore (_) </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Password &nbsp;&nbsp;</TD>
                <TD vAlign=top width=400><INPUT type=password maxLength=15 
                  name="Passwd" class="breadcrumbs"><BR></TD></TR>
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2>To ensure the security of your account, your 
                  <BR>password should be at least four (4) characters. 
                  </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180 class="LabelA">Re-Type Password &nbsp;&nbsp;<BR><BR></TD>
                <TD vAlign=top width=400><INPUT type=password maxLength=15
                  name="Passwd1" class="breadcrumbs"> <BR></TD></TR>
	          <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>&nbsp; </FONT>
                <% out.println("<input type=\"hidden\" name=\"User_Id\" value='" + User_Id + "'>");%></TD>
                <TD vAlign=top width=400 height=59><FONT 
                  face="ARIAL, HELVETICA" size=-1>
                  <BR>
                  <INPUT type="button" value="Register" name="button" onclick="validate()">&nbsp; 
                  <INPUT type=reset value="Reset" name=reset>&nbsp; 
                  <INPUT type="button" value=" Cancel " name="Cancel" onclick="cancel(this.form)">                  
                  </FONT><BR></TD>
	          </TR>
	          </TABLE>
        </FORM></TD></TR>
	  </TABLE>
	  
      <jsp:include page="Footer.jsp" flush="false"/>
	  
	</BODY></HTML>
  