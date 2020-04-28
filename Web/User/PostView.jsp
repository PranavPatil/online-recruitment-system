<%@ page contentType="text/html" %>
<%@ page import="java.sql.*" %>  
<%@ page import="java.io.*" %>  
<%@ page import="java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>

<% /*<jsp:useBean id="connect" scope="request" class="ORS.ConnPool.Database"/> */ %>

<html>
<head>
<title>Post View</title>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META content="" name=keywords>
<META content="Current Openings" name=author>
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<script language=javascript>

 function Button(doEvent)
 {
   document.PostView.USEREVENT.value=doEvent;
   document.PostView.submit();
 }

 function PostApply(postid)
 {
   document.PostView.Post_Id.value=postid;
   document.PostView.USEREVENT.value="USAPPLY";;
   document.PostView.submit();
 }

 function PostDetails(postid)
 {
   document.PostView.Post_Id.value=postid;
   //document.PostView.USEREVENT.value="USAPPLY";;
   //document.PostView.submit();
 }

</script>
</head>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=732>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="UserHeader.jsp" flush="true"/>

      <FORM name="PostView" id='PostView' action="/ORS/UserController" method="get">
      <INPUT name="USEREVENT" type="hidden">
      <INPUT name="Post_Id" type="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="UserPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE cellSpacing=1 cellPadding=1 width="100%" align=center border=0 id=TABLE1>
            <TR vAlign=top align=right width=648>
             <TD colSpan=6>            
			  <TABLE height=10 cellSpacing=0 cellPadding=0 width=625 border=0>
              <TBODY>
              <TR>
                <TD height=5><IMG height=20 alt=Recruitment src="/ORS/Web/images/current_openings.gif" width=625>
				</TD>
              </TR></TBODY></TABLE></TD></TR>
            <TR><TD>&nbsp;</TD></TR>
          </TABLE>
          <TABLE cellSpacing=1 cellPadding=2 width="100%" align=center border=0 name="rsTable" id=rsTable cols=6>
          <TR bgColor="#abcdef">
          <TD width="18%">
          <P align=center><STRONG><FONT face=Verdana size=2>Post Name</FONT></STRONG></P></TD>
          <TD width="20%">
          <P align=center><STRONG><FONT face=Verdana size=2>Qualification</FONT></STRONG></P></TD>
          <TD width="12%">
          <P align=center><STRONG><FONT face=Verdana size=2>Branch</FONT></STRONG></P></TD>      
          <TD width="18%">
          <P align=center><STRONG><FONT face=Verdana size=2>Experience</FONT></STRONG></P></TD>
          <TD width="11%">
          <P align=center><STRONG><FONT face=Verdana size=2>Vacancy</FONT></STRONG></P></TD>      
           <TD width="11%">
          <P align=center><STRONG><FONT face=Verdana size=2></FONT></STRONG></P></TD>
           <TD width="10%">
          <P align=center><STRONG><FONT face=Verdana size=2></FONT></STRONG></P></TD>
          </TR>
  <% 
     int Post_Id = 0,Experience = 0;
     String query = null;
     ResultSet TData = null;

     ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;
     Database db = new Database(pool);
     query = "Select Post_Id,Postname,Qualification,Branch,Experience,Vacancy from post where vacancy != 0";
     TData = db.RetriveDb(query);  
     
     while(TData.next())
     {
	   Post_Id = TData.getInt("Post_Id");
	   out.println("<TR>");
	   out.println("<TD><P align=center><A><STRONG><FONT class=h1><B>");
	   out.println(TData.getString("PostName"));
	   out.println("</FONT></B></A></STRONG></P></TD>");
	   
	   out.println("<TD><P align=center><A><STRONG><FONT class=h1><B>");
	   out.println(TData.getString("Qualification"));
	   out.println("</FONT></B></A></STRONG></P></TD>");
	   
	   out.println("<TD><P align=center><A><STRONG><FONT class=h1><B>");
	   out.println(TData.getString("Branch"));
	   out.println("</FONT></B></A></STRONG></P></TD>");
       
	   out.println("<TD><P align=center><A><STRONG><FONT class=h1><B>");
	   Experience = TData.getInt("Experience");
	   
	   if(Experience == 5)
	   {
	     out.println("above 10yrs");
	   }
	   else if(Experience == 4)
	   {
	     out.println("5-10yrs");
	   }
	   else if(Experience == 3)
	   {
	     out.println("2-5yrs");
	   }
	   else if(Experience == 2)
	   {
	     out.println("1-2yrs");
	   }
	   else
	   {
	     out.println("Fresher");
	   }
	   
	   out.println("</FONT></B></A></STRONG></P></TD>");
	   
	   out.println("<TD><P align=center><A><STRONG><FONT class=h1><B>");
	   out.println(TData.getString("Vacancy"));
	   out.println("</FONT></B></A></STRONG></P></TD>");
	   
	   out.println("<TD><P align=center>");
	   out.println("<U><A class=\"link10\" style=\"font-size:10px\" onclick=\"PostDetails('" + Post_Id + "')\">Details</A></U>");
	   out.println("</P></TD>");

	   out.println("<TD><P align=center>");
	   out.println("<U><A class=\"link10\" style=\"font-size:10px\" onclick=\"PostApply('" + Post_Id + "')\">Apply</A></U>");
	   out.println("</P></TD>");
	   
         out.println("</TR>");
	   }
     %>      
     </TABLE>
     <BR><BR><BR>
     </TABLE>
    </FORM>
   <jsp:include page="UserFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>