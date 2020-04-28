<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>  
<%@ page import = "java.io.*" %>  
<%@ page import = "java.util.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

<%
    long User_Id = 0;
    int Post_Id = 0;
    String temp = null;
    Connection con=null;
    ResultSet rs = null;
    Statement stmt = null;
    
    temp = request.getParameter("User_Id");
    
    try
    {
      User_Id = Long.parseLong(temp);
    }
    catch(NumberFormatException ex)
    {
      User_Id = 0;
    }
    
    temp = null;
    temp = request.getParameter("Post_Id");
    
    try
    {
      Post_Id = Integer.parseInt(temp);
    }
    catch(NumberFormatException ex)
    {
      Post_Id = 0;
    }
    
    System.out.println("User = " + User_Id);
    System.out.println("Post = " + Post_Id);
    
	try
    {
      ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
      con=pool.getConnection();
      stmt=con.createStatement();
    }
	catch(Exception e)
	{
	  e.printStackTrace();
	  out.println(e.getMessage());
	}
    
    String LogId = "Not Available",Fname = "Not Available",Address = "Not Available",Email = "Not Available";
    String Exp = "Not Available",Gender = "Not Available",Qualfn = "Not Available",Branch = "Not Available";
    int Telno = 0,Age = 0,Selected = -1;
    
    float Tpercent = -100,Tattempt = -1;
    int Attpt_no = 0;
    String result = "Not Available";
    java.util.Date date = null;
    
    if(User_Id != 0)
    {
      try	 
      {
		int var = 0;
		rs = null;
		rs = stmt.executeQuery("Select * from Users where User_Id = " + User_Id + "");
		rs.next();
		LogId = rs.getString(5);
		Fname = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
		Gender = rs.getString(7);
		
		if(Gender != null)
		{
		   if(Gender.equals("M"))
		     Gender = "Male";
		   else if(Gender.equals("F"))
		     Gender = "Female";
		}
		
		Address = rs.getString(9);
		Telno = rs.getInt(8);
		Email = rs.getString(10);

		date = rs.getDate(11);
		
		if(date != null)
		{
		  Calendar Cdate = Calendar.getInstance();
	      Calendar Bdate = Calendar.getInstance();
	      Bdate.setTime(date);
		  Age = Cdate.get(Calendar.YEAR) - Bdate.get(Calendar.YEAR); 
		}
				
		Qualfn = rs.getString(12);
		Branch = rs.getString(13);
		var = rs.getInt(14);
		
		if(var == 5)
		{
		  Exp = "above 10yrs";
		}
		else if(var == 4)
		{
		  Exp = "5-10yrs";
		}
		else if(var == 3)
		{
		  Exp = "2-5yrs";
		}
		else if(var == 2)
		{
		  Exp = "1-2yrs";
		}
		else
		{
		  Exp = "Fresher";
		}
		
		Selected = rs.getInt(16);
	  }
	  catch(Exception e)
	  {
	    e.printStackTrace();
	    out.println(e.getMessage());
	  }
	          
      if(Post_Id != 0)
      {
        try
	    {
		  String Query = "Select T_Percent,T_Attempt,T_Result,Attempt_No,TestDate from Result where User_Id = " 
                         + User_Id + " and Post_Id = " + Post_Id;
		  rs = null;
		  date = null;
		  rs = stmt.executeQuery(Query);
		  rs.next();
		  
		  Tpercent = rs.getFloat(1);
		  Tattempt = rs.getFloat(2);
		  result = rs.getString(3);
		  Attpt_no = rs.getInt(4);
		  date = rs.getDate(5);
		}
 	    catch(Exception ex)
	    {
	      ex.printStackTrace();
	      out.println(ex.getMessage());
	    }
      }
    }
     %>
    
  <HTML>
  <HEAD>
  <TITLE>Result Details</TITLE>
  <META http-equiv=Content-Type content="text/html; charset=windows-1252">
  <META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
  <META http-equiv="Expires" Content="no-cache">
  <META http-equiv="Pragma" Content="no-cache">
  <META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

  <META Content="" name=keywords>
  <META http-equiv=Content-Type content="text/html; charset=windows-1252">
  <LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
  <script language=javascript>

   function start1(form)
   {
     window.close(); 
   }

   function Button(doEvent)
   {
     document.ResultDetails.EVENTNAME.value=doEvent;
     document.ResultDetails.submit();
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

      <FORM name="ResultDetails" action="/ORS/AdminController" method="get">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE id=Main cellSpacing=1 cellPadding=1 width="100%" align=center border=0>
		  <TR><TD align="center" class="Title1">Result Details </TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  <TR><TD align="center" class="Title1" style="background-color:#BCBEFE">Candidate Profile</TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  <TR><TD>
          <TABLE id=TABLE1 cellSpacing=1 cellPadding=2 width="100%" align=center border=0>
          <TR><TD width="23">&nbsp;</TD><TD width="26">&nbsp;</TD>
            <TD width=205><FONT class="LabelA" style="color:#9900ff">Login Id : </FONT></TD>
            <TD width="367" class="LabelA"><%= LogId %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Full Name : </FONT></TD>
            <TD class="LabelA"><%= Fname %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Address : </FONT></TD>
            <TD class="LabelA"><%= Address %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Telephone No : </FONT></TD>
            <TD class="LabelA">
            <% if(Telno == 0) out.print("Not Available.");
               else out.print(Telno);
             %> </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Email Id : </FONT></TD>
            <TD class="LabelA"><%= Email %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Age : </FONT></TD>
            <TD class="LabelA">
            <% if(Age == 0) out.print("Not Available.");
               else out.print(Age + " yrs.");
            %>
            </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Gender : </FONT></TD>
            <TD class="LabelA"><%= Gender %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Qualification : </FONT></TD>
            <TD class="LabelA"><%= Qualfn %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Branch : </FONT></TD>
            <TD class="LabelA"><%= Branch %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Experience : </FONT></TD>
            <TD class="LabelA"><%= Exp %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Selected : </FONT></TD>
            <TD class="LabelA">
            
          <ors:alphanum number="<%= Selected %>"/>
          
          </TD></TR>
          </TABLE>
          </TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  <TR><TD align="center" class="Title1" style="background-color:#BCBEFE">Result Details - Exam for Post Manager</TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  <TR><TD>
          <TABLE id=TABLE2 cellSpacing=1 cellPadding=1 width="100%" align=left border=0>
            <TR>
            <TD width="5%">&nbsp;</TD>
            <TD width="5%">&nbsp;</TD>
            <TD width="32%"><FONT class="LabelA" style="color:#9900ff">Aggregate Percentage : </FONT></TD>
            <TD width="58%" class="LabelA">
         <%
            if(Tpercent > -1)
              out.println(Tpercent + " %. ");
            else if(Tpercent > -51)
              out.println("<FONT color='red'>Negative. </FONT>");
            else
              out.println("Not Available. ");
         %>
            </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Attempted Percentage : </FONT></TD>
            <TD class="LabelA">
         <%
            if(Tattempt == -1)
              out.println("Not Available. ");
            else
              out.println(Tattempt + " %. ");
         %>
            </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Result : </FONT></TD>
            <TD class="LabelA"><%= result %>. </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Attempt No : </FONT></TD>
            <TD class="LabelA">
         <%
            if(Attpt_no == 0)
              out.println("Not Available. ");
            else
              out.println(Attpt_no);
         %>
            </TD></TR>
            <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
            <TD><FONT class="LabelA" style="color:#9900ff">Date of Exam : </FONT></TD>
            <TD class="LabelA">
         <%
            if(date == null)
              out.println("Not Available. ");
            else
              out.println(date + ". ");
         %>
		  </TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  </TABLE>		  
		  </TD></TR>
		  <TR><TD><HR></TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  <TR><TD>
          <TABLE cellSpacing=1 cellPadding=5 width="85%" align=center border=1 id=TABLE3>
            <TR vAlign=center align=middle bgColor=black>
            <TD colSpan=6 style="VERTICAL-ALIGN: middle; BACKGROUND-COLOR: black; TEXT-ALIGN: center">
            <FONT face=Verdana size=2><STRONG style="COLOR: white; BACKGROUND-COLOR: black">
            Result Overview</STRONG></FONT></TD></TR>
    
            <TR bgColor="#abcdef">
            <TD>
            <P align=center><STRONG><FONT face=Verdana size=1>Category Name</FONT></STRONG></P></TD>
            <TD>
            <P align=center><STRONG><FONT face=Verdana size=1>Test Name</FONT></STRONG></P></TD>
            <TD>
            <P align=center><STRONG><FONT face=Verdana size=1>Total %</FONT></STRONG></P></TD>
            <TD>
            <P align=center><STRONG><FONT face=Verdana size=1>Attempted %</FONT></STRONG></P></TD>
            <TD>
            <P align=center><STRONG><FONT face=Verdana size=1>Result</FONT></STRONG></P></TD>
            </TR>
         <%
            try
            {
              int cid = 0,tid = 0;
              ResultSet rs1 = null,rs2 = null;

              rs = null;
              result = "Not Available";
      
              Statement smt = con.createStatement();
      
              rs = stmt.executeQuery("Select N.Category_Id,N.Test_Id from Post,TABLE(Post.CatEntry)N where Post_Id = "
                            + Post_Id);
      
              while(rs.next())
              {
                out.println("<TR>");
        
                cid = rs.getInt(1);
                tid = rs.getInt(2);
        
                if(cid != 0)
                {
                  out.println("<TD>");
          
                  rs1 = null;
                  rs1 = smt.executeQuery("Select Name from Category where Category_Id = " + cid);
                  rs1.next();
          
                  out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs1.getString(1) +"</FONT></STRONG></P></TD>");

                  out.println("<TD>");
          
                  if(tid != 0)
                  {
                    rs1 = null;
                    rs1 = smt.executeQuery("Select Name from Test where Test_Id = " + tid);
                    rs1.next();
                    out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs1.getString(1) +"</FONT></STRONG></P></TD>");
                  }
                  else
                    out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Available</FONT></STRONG></P></TD>");
          
                  rs2 = null;
                  rs2 = smt.executeQuery("Select N.Percent,N.Attempt,N.Result from Result,TABLE(Result.TestData)N where User_Id = "
                        + User_Id + " and Post_Id = " + Post_Id + " and N.Category_Id = " + cid);
          
                  if(rs2.next())
                  {
                   float Percent = rs2.getFloat(1);
           
                   out.println("<TD>");
           
                   if(Percent > -1)
                     out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + Percent + "</FONT></STRONG></P></TD>");
                   else
                     out.println("<P align=center><STRONG><FONT color='red' face=Verdana size=1>Negative</FONT></STRONG></P></TD>");

                   out.println("<TD>");
                   out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs2.getFloat(2) + "</FONT></STRONG></P></TD>");
                   out.println("<TD>");
                   out.println("<P align=center><STRONG><FONT face=Verdana size=1>" + rs2.getString(3) + "</FONT></STRONG></P></TD>");
                  }
                  else
                  {
                   out.println("<TD>");
                   out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Attempted</FONT></STRONG></P></TD>");
                   out.println("<TD>");
                   out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Attempted</FONT></STRONG></P></TD>");
                   out.println("<TD>");
                   out.println("<P align=center><STRONG><FONT face=Verdana size=1>Not Attempted</FONT></STRONG></P></TD>");
                  }
                }
                
                out.println("</TR>");
              }
            }
            catch(Exception ex)
            {
              ex.printStackTrace();
              out.println(ex.getMessage());
            }   %>    

          </TABLE>		  
		  </TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
		  <TR><TD><HR></TD></TR>
		  <TR align="center"><TD><INPUT class="B1" name="Ok" type="button" value=" OK " size=25 onclick=Button('VWRSLT_MN')>
          </TD></TR>
		  <TR><TD>&nbsp;</TD></TR>
          </TABLE> 
      </TABLE>
	  </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>