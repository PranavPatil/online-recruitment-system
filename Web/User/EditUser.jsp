<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%
   Connection con=null;
   ResultSet rs=null;
   Statement stmt=null;
   long User_Id=0;
  
   HttpSession sessn=request.getSession(false);
  
   if(session != null)
   {
    User_Id = (Long)sessn.getAttribute("User_Id");
   }
   else
    User_Id = 0;
   
   if(User_Id != 0)
   {
     try
     {
  	   System.out.println("User_id"+User_Id);
	   ConnectionPool	pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
  	   con=pool.getConnection();
	   stmt=con.createStatement();
  	   String query="select * from users where User_Id=" + User_Id;
  	   rs=stmt.executeQuery(query);
     }
     catch(Exception e)
     {
       out.println(e.getMessage());
     }
   }
%>

<HTML><HEAD><TITLE>User Registration Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<link rel="stylesheet" type="text/css" href="/ORS/gifs/spiffyCal.css">
<script language="JavaScript" src="/ORS/gifs/spiffyCal.js"></script>
<script language="JavaScript">
///////////////////////////////FUNCTION VALIDATE//////////////

var cal1=new ctlSpiffyCalendarBox("cal1", "Usrform", "txtDate","btnDate1","");

function validate()
{
  var flagfilled=filled(this.Usrform)
  
  if(flagfilled==true)
  { 
    var phno = checkno(document.Usrform.telephone.value);
    
    if(phno== false)
    {
      alert("The Telephone No is not valid !");
      document.Usrform.telephone.focus();
      return false;
    }
    else
    {
      var mail = checkmail(document.Usrform.email.value);
      
      if(mail==false)
      {
        alert("The Mail ID is not valid !");
        document.Usrform.email.focus();
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
  var pass1 = document.Usrform.passwd.value;
  var pass2 = document.Usrform.passwd1.value;

  document.Usrform.Bdate.value = cal1.getSelectedDate();

  if(document.Usrform.fname.value == "")
  {
   alert("Please Enter Your First Name !");
   document.Usrform.fname.focus();
   return false;  
  } 
  else if(document.Usrform.mname.value == "")
  {
   alert("Please Enter Your Middle Name !");
   document.Usrform.mname.focus();
   return false;  
  } 
  else if(document.Usrform.lname.value == "")
  {
   alert("Please Enter Your Last Name !");
   document.Usrform.lname.focus();
   return false;  
  } 
  else if(document.Usrform.address.value == "")
  {
   alert("Please Enter Your Address !");
   document.Usrform.address.focus();
   return false;  
  }
  else if(document.Usrform.telephone.value == "")
  {
   alert("Please Enter Your Telephone No !");
   document.Usrform.telephone.focus();
   return false;  
  }  
  else if(document.Usrform.email.value == "")
  {
   alert("Please Enter Your Email Id !");
   document.Usrform.email.focus();
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
  else if(document.Usrform.login.value == "")
  {
   alert("Please Enter Your Login Name !");
   document.Usrform.login.focus();
   return false;  
  } 
  else if(document.Usrform.passwd.value == "")
  {
   alert("Please Enter Your Password !");
   document.Usrform.passwd.focus();
   return false;  
  } 
  else if(document.Usrform.passwd.value != document.Usrform.passwd1.value)
  {
   alert("Please Retype Your Password !");
   document.Usrform.passwd.value = "";
   document.Usrform.passwd1.value = "";
   document.Usrform.passwd.focus();
   return false;
  }   
  else           
   return true;
}

function cancel(form)
{
  doc=open("/ORS/Web/User/PostView.jsp","Page");
}

</script>

</HEAD>
<BODY bgcolor=ivory>
 <div id="spiffycalendar" class="text" style="WIDTH: 43px; HEIGHT: 24px"></div>
  <FORM action="/ORS/EditUser"  id=form1 name=Usrform method="post" onsubmit="return validate()">
   <%     
     try{
          while(rs.next())
         { 
           
   	%>   
    <TABLE cellSpacing=0 cellPadding=0 width=580 border=0 align=left>
      <TBODY>
        <TR>
           <TD width=580>
              <TABLE cellSpacing=0 cellPadding=0 width=580 border=0 align=left>
	          <TR bgColor="#abcdef">
	            <TD colSpan="2"><FONT face="Arial" color="#000000" size="-1"> <STRONG>&nbsp;&nbsp;Update Profile</FONT></STRONG>
	            </TD></TR>
	          <TR>
	            <TD colSpan="2"><FONT face="Arial" color="#cc0033" size="3"><pre>            Filling all the fields is compulsory</pre></FONT>
		        </TD></TR>
              <TR>
                <TD colSpan=2>&nbsp;&nbsp; </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>First Name &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT id=fname maxLength=20 
                  name="fname" value='<% out.println(rs.getString("fname")); %>'>&nbsp;</TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Middle Name &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=20 
                  name="mname" value='<% out.println(rs.getString("mname")); %>'> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Last Name &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><INPUT maxLength=20 name="lname" value='<% out.println(rs.getString("lname")); %>'></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Gender &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=center width=400><FONT face="ARIAL, HELVETICA" 
                  size=-1>
                  <% String sex=rs.getString("gender");
                     if(sex.equalsIgnoreCase("m"))
                    {
                      out.print("<INPUT type=radio CHECKED value=M name=gender>"+
                      "Male&nbsp;<INPUT type=radio value=F name=gender> Female");
                    }
                  else
                  if(sex.equalsIgnoreCase("f"))
                  {
                    out.print("<INPUT type=radio  value=M name=gender> "+
					"Male&nbsp;<INPUT type=radio value=F name=gender CHECKED> Female");
                   }
                  %>
                  </FONT> <BR></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Address &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><TEXTAREA id=address name="address" rows=3 cols=17><% out.println(rs.getString("address")); %></TEXTAREA>&nbsp;</TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Telephone No &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=12 
                  size="22" name="telephone" value='<% out.print(rs.getString("TELEPHONE")); %>'>
                </TD></TR>              
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Email &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top align=left width=400><INPUT maxLength=30 
                  size="22" name="email" value='<% out.print(rs.getString("EMAIL")); %>'> </TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>

              <TR>
                <TD vAlign=center align=right width=180>
                  <FONT    
            face="ARIAL, HELVETICA" size=-1>Birth Date: </FONT>
                </TD>
   
   <%  String date = null,day = null,year = null,month = null;
                int mn = 0;
                
			    date = rs.getString("Date_Of_Birth");
			    System.out.println("date = " + date);
			    day = date.substring(8,10);
			    System.out.println("dd = " + day);
			    month = date.substring(5,7);
			    year = date.substring(0,4);
			    
			    try
			    {
			      mn = Integer.parseInt(month);
			    }
			    catch(NumberFormatException nex)
			    {
			      mn = 0;
			    }
			    
			    if(mn == 1)
			      month = "Jan";
			    else if(mn == 2)
			      month = "Feb";
			    else if(mn == 3)
			      month = "Mar";
			    else if(mn == 4)
			      month = "Apr";
			    else if(mn == 5)
			      month = "May";
			    else if(mn == 6)
			      month = "Jun";
			    else if(mn == 7)
			      month = "Jul";
			    else if(mn == 8)
			      month = "Aug";
			    else if(mn == 9)
			      month = "Sep";
			    else if(mn == 10)
			      month = "Oct";
			    else if(mn == 11)
			      month = "Nov";
			    else if(mn == 12)
			      month = "Dec";
			    else
			      month = null;
			    
			    if(day != null && month != null && year != null)
			    {
			      date = day + "-" + month + "-" + year;
			    }
			    else
			     date = null;  %>
			                     
			  <TD vAlign=top width=400>
			  <SCRIPT language="JavaScript"> cal1.writeControl();
	           cal1.setSelectedDate("<% out.print(date); %>");
			  </SCRIPT><INPUT name='Bdate' id='Bdate' type='hidden'></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Qualification &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><SELECT name="Qualification">
					<% 
							   String qfn = rs.getString("Qualification"); 
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
							    
							    if(buffer.get(i).equals(qfn))
							        out.println("selected");
							      
							    out.println(">" + buffer.get(i));
							    i++;
							   }
					%>
                 
                  </OPTION></SELECT> 
                  <FONT face="ARIAL, HELVETICA" size=-1>&nbsp;&nbsp;&nbsp; Branch &nbsp;</FONT>
                <SELECT name="Branch">
    <% 
							   String brch = rs.getString("Branch"); 
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
							    
							    if(buff.get(k).equals(brch))
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
							   int exp = rs.getInt("Experience"); 
							   System.out.println("Experience = " + exp);
							   
							   if(exp == 1)
   								 out.println("<OPTION value= \"1\" selected>Fresher");
							   else							   
							     out.println("<OPTION value= \"1\">Fresher");
							     
							   if(exp == 2)
							     out.println("<OPTION value=\"2\" selected>1-2yrs");
							   else
							     out.println("<OPTION value=\"2\">1-2yrs");
							     
							   if(exp == 3)
							     out.println("<OPTION value=\"3\" selected>2-5yrs");
							   else
							     out.println("<OPTION value=\"3\">2-5yrs");
							     
							   if(exp == 4)
							     out.println("<OPTION value=\"4\" selected>5-10yrs");
							   else							     
							     out.println("<OPTION value=\"4\">5-10yrs");
							     
							   if(exp == 5)
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
                <TD vAlign=top width=400><INPUT id=login maxLength=20 
                    name="login" value='<% out.println(rs.getString("Login")); %>' readonly></TD></TR>              
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2>Contains only letters (a-z)<BR>numbers (0-9) and 
                  underscore (_) </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Password &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><INPUT type=password maxLength=15 
                  name="passwd"></TD></TR>
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
                  name="passwd1"> <BR></TD></TR>
	          <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>&nbsp; </FONT>
                  <input type="hidden" name="User_Id" value='<% out.print(rs.getString("User_Id")); %>'></TD>
                <TD vAlign=top width=400 height=60><FONT 
                  face="ARIAL, HELVETICA" size=-1>
                  <BR><INPUT type=submit value="Update" name=submit> 
                  <INPUT type=reset value="Reset" name=reset> 
                  <INPUT type="button" value=" Cancel " name="Cancel" onclick="cancel(this.form)">                  
                  </FONT><BR></TD>
	          </TR>
	          </TABLE>
	          <%
		   }
		}
		catch(Exception e)
		{
		  out.println(e.getMessage());
		}
	%>
		
	<%
		try
		{
		  rs.close();
		  con.close();
		}
		catch(Exception e)
		{
		  out.println(e.getMessage());
		}
	%>
			           
		</FORM></TR></TBODY></TABLE>      
        </BODY>
        </HTML>
