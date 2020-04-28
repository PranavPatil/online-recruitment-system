<%@ page contentType="text/html" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.Model.User" %>

<% 
   long User_Id = -1;
   String error = null;
   User user = null;
   
   try
   {
     String str = null;
     Object obj  = request.getAttribute("Error");
     System.out.println("obj = " + obj);
     
     if(obj != null)
       error = (String) obj;

     str = request.getParameter("User_Id");
     System.out.println("str is = " + str);
       
     if(str != null)
       User_Id = Long.parseLong(str);
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     User_Id = -1;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     User_Id = -1;
   }
   
   System.out.println("id = " + User_Id);
   
   if(error != null)
   {
     user = new User(request);
     System.out.println("fname = " + user.getFname());
   }
   else if(User_Id > 0)
   {
     ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
     user = new User(User_Id,conn);
   }
   else
     user = null;
 %>

<HTML><HEAD><TITLE>User Registration Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<link rel="stylesheet" type="text/css" href="/ORS/Web/images/spiffyCal.css">
<script language="JavaScript" src="/ORS/Web/images/spiffyCal.js"></script>
<script language="JavaScript">

///////////////////////////////FUNCTION VALIDATE//////////////

var cal1=new ctlSpiffyCalendarBox("cal1", "Usrform", "txtDate","btnDate1","");

function validate()
{
  var flagfilled=filled(this.Usrform)
  
  if(flagfilled==true)
  { 
    var phno = checkno(document.Usrform.Telephone.value);
    
    if(phno== false)
    {
      alert("The Telephone No is not valid !");
      document.Usrform.Telephone.focus();
      return false;
    }
    else
    {
      var mail = checkmail(document.Usrform.Email.value);
      
      if(mail==false)
      {
        alert("The Mail ID is not valid !");
        document.Usrform.Email.focus();
        return false;
      }
      else
      {
        return true;
      }
    }
  }
  else
    return false;
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
  var pass1 = document.Usrform.Passwd.value;
  var pass2 = document.Usrform.Passwd1.value;

  document.Usrform.Bdate.value = cal1.getSelectedDate();

  if(document.Usrform.Fname.value == "")
  {
   alert("Please Enter Your First Name !");
   document.Usrform.Fname.focus();
   return false;  
  } 
  else if(document.Usrform.Mname.value == "")
  {
   alert("Please Enter Your Middle Name !");
   document.Usrform.Mname.focus();
   return false;  
  } 
  else if(document.Usrform.Lname.value == "")
  {
   alert("Please Enter Your Last Name !");
   document.Usrform.Lname.focus();
   return false;  
  } 
  else if(document.Usrform.Address.value == "")
  {
   alert("Please Enter Your Address !");
   document.Usrform.Address.focus();
   return false;  
  }
  else if(document.Usrform.Telephone.value == "")
  {
   alert("Please Enter Your Telephone No !");
   document.Usrform.Telephone.focus();
   return false;  
  }  
  else if(document.Usrform.Email.value == "")
  {
   alert("Please Enter Your Email Id !");
   document.Usrform.Email.focus();
   return false;  
  } 
  else if(document.Usrform.Bdate.value == "")
  {
   alert("Please Select your Birth Date !");
   return false;  
  }
  else if(document.Usrform.Qualification.value == "")
  {
   alert("Please Select Your Qualification !");
   document.Usrform.Qualification.focus();
   return false;  
  }
  else if(document.Usrform.Branch.value == "")
  {
   alert("Please Select Your Branch !");
   document.Usrform.Branch.focus();
   return false;  
  }
  else if(document.Usrform.Experience.value == "")
  {
   alert("Please Select Your Experience !");
   document.Usrform.Experience.focus();
   return false;  
  }  
  else if(document.Usrform.Login.value == "")
  {
   alert("Please Enter Your Login Name !");
   document.Usrform.Login.focus();
   return false;  
  } 
  else if(document.Usrform.Passwd.value == "")
  {
   alert("Please Enter Your Password !");
   document.Usrform.Passwd.focus();
   return false;  
  } 
  else if(document.Usrform.Passwd.value != document.Usrform.Passwd1.value)
  {
   alert("Please Retype Your Password !");
   document.Usrform.Passwd.value = "";
   document.Usrform.Passwd1.value = "";
   document.Usrform.Passwd.focus();
   return false;
  }   
  else           
   return true;
}

function cancel(form)
{
  doc=open("/ORS/Web/User/UserLoginPage.html","Page");
}

</script>

</HEAD>

<% 
   if(error != null) 
   {
     out.println("<BR>");
     out.println("<BLOCKQUOTE><H3>Error : " + error + "</H3></BLOCKQUOTE>");
   } %>
<HR>
<BODY bgcolor=ivory>
 <div id="spiffycalendar" class="text" style="WIDTH: 43px; HEIGHT: 24px"></div>
  <FORM action="/ORS/UserReg"  id=form1 name=Usrform method="post" onsubmit="return validate()">
    <TABLE cellSpacing=0 cellPadding=0 width=580 border=0 align=left>
      <TBODY>
        <TR>
           <TD width=580>
              <TABLE cellSpacing=0 cellPadding=0 width=580 border=0 align=left>
	          <TR bgColor="#abcdef">
	            <TD colSpan="2"><FONT face="Arial" color="#000000" size="-1"> <STRONG>&nbsp;&nbsp;Registration Form</STRONG></FONT>
	            </TD></TR>
	          <TR>
	            <TD colSpan="2"><FONT face="Arial" color="#cc0033" size="3"><pre>            Filling all the fields is compulsory</pre></FONT>
		        </TD></TR>
              <TR>
                <TD colSpan=2>&nbsp;&nbsp; </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>First Name &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT id=Fname maxLength=20 
                  name="Fname" value='<% if(user != null) out.print(user.getFname()); %>'>&nbsp;</TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Middle Name &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=20 
                  name="Mname" value='<% if(user != null) out.print(user.getMname()); %>'> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Last Name &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><INPUT maxLength=20 name="Lname"
                 value='<% if(user != null) out.print(user.getLname()); %>'></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Gender &nbsp;&nbsp;</FONT></TD>
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
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Address &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><TEXTAREA id=Address name="Address" rows=3 cols=17><%
                 if(user != null) out.print(user.getAddress()); %></TEXTAREA>&nbsp;</TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Telephone No &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=12 
                  size="22" name="Telephone" value='<% if(user != null) out.print(user.getTelephone()); %>'> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Email &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=30 
                  size="22" name="Email"  value='<% if(user != null) out.print(user.getEmail()); %>'> </TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>

              <TR>
                <TD vAlign=center align=right width=180>
                  <FONT    
            face="ARIAL, HELVETICA" size=-1>Birth Date &nbsp;&nbsp;</FONT>
                </TD>
			  <TD vAlign=top width=400>
			  <SCRIPT language="JavaScript"> cal1.writeControl();
			  <% if(user != null) out.println("cal1.setSelectedDate(\"" + user.getBirthdate() + "\");"); %>
			  </SCRIPT><INPUT name='Bdate' id='Bdate' type='hidden' value='<% if(user != null) out.print(user.getBirthdate()); %>'></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Qualification &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><SELECT name="Qualification"> 
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
               <FONT face="ARIAL, HELVETICA" size=-1>&nbsp;&nbsp;&nbsp; Branch &nbsp;</FONT>
            <SELECT name="Branch">
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
					<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Experience &nbsp;&nbsp;
						</FONT>
					</TD>
					<TD vAlign="top" width="400"><SELECT name="Experience" ID="Select1">
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
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Login Name &nbsp;&nbsp;
                </FONT></TD>
                <TD vAlign=top width=400><INPUT id=Login maxLength=20 
                    name="Login" <% if(user != null) out.print("value='" + user.getLogin() + "' readonly"); %>></TD></TR>
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2>Contains only letters (a-z)<BR>numbers (0-9) and 
                  underscore (_) </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Password &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><INPUT type=password maxLength=15 
                  name="Passwd"></TD></TR>
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2>To ensure the security of your account, your 
                  <BR>password should be at least four (4) characters. 
                  </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Re-Type Password &nbsp;&nbsp;
                  </FONT><BR><BR></TD>
                <TD vAlign=top width=400><INPUT type=password maxLength=15
                  name="Passwd1"> <BR></TD></TR>
	          <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>&nbsp; </FONT>
                <% out.println("<input type=\"hidden\" name=\"User_Id\" value='" + User_Id + "'>");%></TD>
                <TD vAlign=top width=400 height=60><FONT 
                  face="ARIAL, HELVETICA" size=-1>
                  <BR><INPUT type=submit value="Register" name=submit> 
                  <INPUT type=reset value="Reset" name=reset> 
                  <INPUT type="button" value=" Cancel " name="Cancel" onclick="cancel(this.form)">                  
                  </FONT><BR></TD>
	          </TR>
	          </TABLE>
	</FORM></TR></TBODY>      
</BODY>
</HTML>