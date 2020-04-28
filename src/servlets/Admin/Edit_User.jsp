<%@ page contentType="text/html" %>
<%@ page import="java.util.*,java.sql.*,java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%
  Connection con=null;
  ResultSet rs=null;
  Statement stmt=null;
  int User_Id=0;
  
  try{
  		User_Id=Integer.parseInt(request.getParameter("SelectUser"));
  		System.out.println("User_id"+User_Id);
		ConnectionPool	pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
  	   	con=pool.getConnection();
		stmt=con.createStatement();
  	   	String query="select * from users where User_Id="
  	   	+User_Id;
  	   	rs=stmt.executeQuery(query);
  	   	 	   	
     }catch(Exception e)
      {
       out.println(e.getMessage());
       }
   
%>

<HTML><HEAD><TITLE>Admin Registration Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">

<script language="JavaScript">

function cancel(form)
{
	doc=open("http://localhost:8080/examples/servlets/Admin/UserUpdate.jsp","right1");
}

function copy()
{
 var temp = document.form1.fname.value;
 document.form1.login.value = temp;
}

function validate()
{
  var flagfilled=filled(this.form)
  if(flagfilled==true)
  {
    var vDate=validate_date(document.form1.dob_dd.value,document.form1.dob_mm.value,document.form1.dob_yy.value);
     if(vDate==false)
      {
       alert(document.form1.dob_dd.value + "/" + document.form1.dob_mm.value + "/" + document.form1.dob_yy.value + " is not a valid date");
       document.form1.dob_dd.focus();
       return false;
      }
   
  var mail = checkmail(document.form1.email.value);
  if(mail==false)
  {
    alert("The Mail ID is not valid !!");
    document.form1.email.focus();
    return false;
  }
  }
  if(flagfilled==true && vDate==true && mail==true)
   return true;
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

//////////////////////////////// FILLED FUNCTION /////////////////////////////////

function filled(form)
{  
  var pass1 = document.form1.passwd.value;
  var pass2 = document.form1.passwd1.value;

  if(document.form1.fname.value == "")
  {
   alert("Give Your First name !!");
   document.form1.fname.focus();
   return false;  
  } 
  else if(document.form1.mname.value == "")
  {
   alert("Give Your Middle name !!");
   document.form1.mname.focus();
   return false;  
  } 
  else if(document.form1.lname.value == "")
  {
   alert("Give Your Last name !!");
   document.form1.lname.focus();
   return false;  
  } 
  else if(document.form1.address.value == "")
  {
   alert("Give Your Address !!");
   document.form1.address.focus();
   return false;  
  }
  else if(document.form1.telephone.value == "")
  {
   alert("Give Your Telephone No !!");
   document.form1.telephone.focus();
   return false;  
  }  
  else if(document.form1.email.value == "")
  {
   alert("Give Your Email Id !!");
   document.form1.email.focus();
   return false;  
  } 
  else if(document.form1.dob_dd.value == "")
  {
   alert("Give Your Date of birth !!");
   document.form1.dob_dd.focus();
   return false;  
  }
  else if(document.form1.dob_mm.value == "")
  {
   alert("Give Your Month of birth !!");
   document.form1.dob_mm.focus();
   return false;  
  }
  else if(document.form1.dob_yy.value == "")
  {
   alert("Give Your Year of birth !!");
   document.form1.dob_yy.focus();
   return false;  
  }
  else if(document.form1.Qualification.value == "")
  {
   alert("Give Your Qualification !!");
   document.form1.Qualification.focus();
   return false;  
  }
  else if(document.form1.Branch.value == "")
  {
   alert("Give Your Branch !!");
   document.form1.Branch.focus();
   return false;  
  }
  else if(document.form1.Experience.value == "")
  {
   alert("Give Your Experience !!");
   document.form1.Experience.focus();
   return false;  
  }  
  else if(document.form1.login.value == "")
  {
   alert("Give Your Login name !!");
   document.form1.login.focus();
   return false;  
  } 
  else if(document.form1.passwd.value == "")
  {
   alert("Give Your Password !!");
   document.form1.passwd.focus();
   return false;  
  } 
  else if(document.form1.passwd.value != document.form1.passwd1.value)
  {
   alert("Type Your Password Again !!");
   document.form1.passwd.value = "";
   document.form1.passwd1.value = "";
   document.form1.passwd.focus();
   return false;
  } 
  
  else           
   return true;
}

//VALIDATE DATE
function validate_date(day,month,year)
{
	// since jan equals one and not zero, hence thirteen elements in the array.  
	var no_of_days_in_month = new Array(0,31,28,31,30,31,30,31,31,30,31,30,31)
	
	if (month >= 1 && month <= 12 && day >=  1 && day <= 31 && year >= 0)
	{ 	
		//Handling february, special case. 
		if (month == 2)
		{
			if ( (year%4==0 && year%100 != 0) || year%400 == 0 )
				no_of_days_in_month[month]=29
		}

		if (day >= 1 && day <= no_of_days_in_month[month])
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
	else
	{
		return false;
	}
}
</script>

</HEAD>
<BODY bgColor=ivory>
      
  <FORM action="http://localhost:8080/examples/servlet/EditUser"  id=form1 name=form1 method="post" 
        onsubmit="return validate()">
   <%     
     try{
          while(rs.next())
         { 
           
   	%>   
        
   <TABLE cellSpacing=0 cellPadding=0 width=580 border=0 
      align=left>
        <TR>
          <TD width=580>
            <TABLE cellSpacing=0 cellPadding=0 width=580 border=0 align=left>
					<TR bgColor="#abcdef">
									<TD colSpan="2"><FONT face="Arial" color="#000000" size="-1"> <STRONG>&nbsp;&nbsp; 
											Editing user Profile!</FONT></STRONG>
									</TD>
								</TR>
				<TR>
					<TD colSpan="2"><FONT face="Arial" color="#cc0033" size="3"><pre>            Filling all the fields are compulsory</pre>
						</FONT>
					</TD>
				</TR>
				<TR>
              <TR>
                <TD colSpan=2>&nbsp;&nbsp; </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>First Name: </FONT></TD>
                <TD vAlign=top align=left width=400><INPUT id=fname  
                  name=fname onkeyup="copy()" value=<% out.println(rs.getString("fname")); %>>&nbsp;</TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Middle Name: </FONT></TD>
                <TD vAlign=top align=left width=400><INPUT type=text value=<% out.println(rs.getString("mname")); %> 
                  name=mname> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Last Name: </FONT></TD>
                <TD vAlign=top width=400><INPUT value=<% out.println(rs.getString("lname")); %> name=lname></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Gender: </FONT></TD>
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
                  face="ARIAL, HELVETICA" size=-1>Address: </FONT></TD>
                <TD vAlign=top align=left width=400><textarea name="address"
                 id="address" cols="17" 
                 rows="3"><% out.println(rs.getString("address")); %></textarea>&nbsp;</TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Telephone No: </FONT></TD>
                <TD vAlign=top align=left width=400><INPUT value=<% out.print(rs.getString("TELEPHONE")); %> 
                  size="25" name=telephone> </TD></TR>              
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Email: </FONT></TD>
                <TD vAlign=top align=left width=400><INPUT value=<% out.print(rs.getString("EMAIL")); %> 
                  size="25" name=email> </TD></TR>             
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>

              <TR>
                <TD vAlign=center align=right width=180>
                  <FONT  face="ARIAL, HELVETICA" size=-1>Birth Date: </FONT>
                </TD>
			  <TD vAlign=top width=400>
			   <SELECT	class=textboxes1 name=dob_dd>
			   
			   <% String dob = rs.getString("Date_Of_Birth");  
			      System.out.println("Date of Birth = " + dob);
			      int year = 0;
			      int month = 0;
			      int day = 0;
			      
    			  try
    			  {
			        year = Integer.parseInt(dob.substring(0,4));
			        month = Integer.parseInt(dob.substring(5,7));
			        day = Integer.parseInt(dob.substring(8,10));
    			  }
			      catch (NumberFormatException expn)
			      {
				      System.out.println("Problem in Str -> Number");
				      System.err.println(expn.getMessage());
			      }			      
			      
			      System.out.println("Year = " + year + "  Month = " + month + "  Day" + day);			      
			    %>
			  
			    <% out.println("<OPTION value=" + day + " selected>" + day + "</OPTION>"); %> 
			    
			   <OPTION>..date..</OPTION>
			    <OPTION value=1>1</OPTION> <OPTION 
		value=2>2</OPTION><OPTION value=3>3</OPTION><OPTION 
		value=4>4</OPTION><OPTION value=5>5</OPTION> <OPTION 
		value=6>6</OPTION><OPTION value=7>7</OPTION><OPTION 
		value=8>8</OPTION><OPTION value=9>9</OPTION> <OPTION 
		value=10>10</OPTION><OPTION value=11>11</OPTION><OPTION 
		value=12>12</OPTION><OPTION value=13>13</OPTION><OPTION 
		value=14>14</OPTION> <OPTION value=15>15</OPTION><OPTION 
		value=16>16</OPTION><OPTION value=17>17</OPTION><OPTION 
		value=18>18</OPTION><OPTION value=19>19</OPTION> <OPTION 
		value=20>20</OPTION><OPTION value=21>21</OPTION><OPTION 
		value=22>22</OPTION><OPTION value=23>23</OPTION><OPTION 
		value=24>24</OPTION> <OPTION value=25>25</OPTION><OPTION 
		value=26>26</OPTION><OPTION value=27>27</OPTION><OPTION 
		value=28>28</OPTION><OPTION value=29>29</OPTION> <OPTION 
		value=30>30</OPTION><OPTION value=31>31</OPTION></SELECT> <SELECT 
		class=textboxes1 name=dob_mm>
		
	    <% out.println("<OPTION value=" + month + " selected>");
	       
	       if(month == 1)
	         out.print("January");
	       if(month == 2)
	         out.print("February");
	       if(month == 3)
	         out.print("March");
	       if(month == 4)
	         out.print("April");
	       if(month == 5)
	         out.print("May");
	       if(month == 6)
	         out.print("June");
	       if(month == 7)
	         out.print("July");
	       if(month == 8)
	         out.print("August");
	       if(month == 9)
	         out.print("September");
	       if(month == 10)
	         out.print("October");
	       if(month == 11)
	         out.print("November");
	       if(month == 12)
	         out.print("December");
	    %> 	    
		</OPTION>
                <OPTION>..month..</OPTION> 
		<OPTION value=1>January<OPTION value=2>February<OPTION
                    value=3>March<OPTION value=4>April<OPTION value=5>May<OPTION  
                    value=6>June<OPTION value=7>July<OPTION value=8>August<OPTION  
                    value=9>September<OPTION value=10>October<OPTION value=11>November<OPTION  
                    value=12>December</OPTION></SELECT> 
                    <SELECT class=textboxes1 name=dob_yy> 

	    <% out.println("<OPTION value=" + year + " selected>" + year + "</OPTION>"); %> 
                    
		<OPTION>..year..</OPTION> <OPTION value=1970>1970</OPTION><OPTION 
		value=1971>1971</OPTION><OPTION value=1972>1972</OPTION><OPTION 
		value=1973>1973</OPTION><OPTION value=1974>1974</OPTION><OPTION 
		value=1975>1975</OPTION> <OPTION value=1976>1976</OPTION><OPTION 
		value=1977>1977</OPTION><OPTION value=1978>1978</OPTION><OPTION 
		value=1979>1979</OPTION><OPTION value=1980>1980</OPTION><OPTION 
		value=1981>1981</OPTION><OPTION value=1982>1982</OPTION><OPTION 
		value=1983>1983</OPTION> <OPTION value=1984>1984</OPTION><OPTION 
		value=1985>1985</OPTION><OPTION value=1986>1986</OPTION><OPTION 
		value=1987>1987</OPTION><OPTION value=1988>1988</OPTION><OPTION 
		value=1989>1989</OPTION><OPTION value=1990>1990</OPTION></SELECT> </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Qualification: </FONT></TD>
                <TD vAlign=top width=400><SELECT name=Qualification> 
                
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
                  <FONT face="ARIAL, HELVETICA" size=-1>&nbsp;&nbsp;Branch: </FONT>
                <SELECT name=Branch> 

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
					<TD vAlign="center" align="right" width="180"><FONT face="ARIAL, HELVETICA" size="-1">Experience:&nbsp;&nbsp;
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
                    name="login" value=<% out.println(rs.getString("Login")); %> readonly></TD></TR>              
              <TR>
                <TD width=180>&nbsp; </TD>
                <TD vAlign=top width=400><FONT face="ARIAL, HELVETICA" 
                  size=-2>Contains only letters (a-z)<BR>numbers (0-9) and 
                  underscore (_) </FONT></TD></TR>
              <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>Password &nbsp;&nbsp;</FONT></TD>
                <TD vAlign=top width=400><INPUT type=password maxLength=15 
                  name=passwd></TD></TR>
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
                  name=passwd1> <BR></TD></TR>
             <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400>&nbsp; </TD></TR>
            <TR>
                <TD vAlign=center align=right width=180></FONT></TD>
                <TD vAlign=top width=400><input type="hidden" name="User_Id" value=<% out.print(rs.getString("User_Id")); %>> </TD></TR>
	          <TR>
                <TD vAlign=center align=right width=180><FONT 
                  face="ARIAL, HELVETICA" size=-1>&nbsp; </FONT></TD>
                <TD><INPUT type=submit value="Update" name=submit>&nbsp;&nbsp;&nbsp;
                <INPUT type="button" value="   Cancel  " name="btncancel" onclick="cancel(this.form)"> 
                  </FONT><BR></TD>
	          </TR>
	          </TABLE> 
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
