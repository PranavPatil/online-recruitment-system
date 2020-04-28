<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Model.Post" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
   int Post_Id = -1;
   DataEntryException exception = null;
   Post post = null;
   ConnectionPool conn = null;
   
   try
   {
     String str = null,event = null;
     exception = (DataEntryException) request.getAttribute("Exception");
     
     event = request.getParameter("EVENTNAME");
     
     if(event.equals("POSTMGT_EDT"))
     {
       str = request.getParameter("SelectPost");
       
       if(str != null)
        Post_Id = Integer.parseInt(str);
     }
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     Post_Id = -1;
     exception = null;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     Post_Id = -1;
   }
   
   System.out.println("Post_Id = " + Post_Id);
   
   if(exception != null)
   {
     post = new Post(request);
     System.out.println("fname = " + post.getName());
   }
   else if(Post_Id > 0)
   {
     conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
     post = new Post(Post_Id,conn);
   }
   else
     post = null;
 %>

<HTML>
<HEAD>
<TITLE>Create Post</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language=JavaScript>

 function Agefun(form)
 {
   if(document.CreatePost.AgeBound.selectedIndex==1)
   {
     document.CreatePost.Agelimit.disabled=true;
     document.CreatePost.Agelimit.value="";
   }
   else
   {
     document.CreatePost.Agelimit.disabled=false;
   }
 }
 
 function validate(doEvent)
 {
   document.CreatePost.EVENTNAME.value=doEvent;
   
   if(document.CreatePost.PostName.value=="")
   { 
     alert("Please enter the Post Name !!");
     document.CreatePost.PostName.focus();
     return false;
   }
   else if(document.CreatePost.PostDesc.value=="")
   { 
     alert("Please enter the Post Description !!");
     document.CreatePost.PostDesc.focus();
     return false;
   }
   else if(document.CreatePost.Aggregate.value=="")
   { 
     alert("Please give the Minimum Percentage Requirement for the Post !!");
     document.CreatePost.Aggregate.focus();
     return false;
   }
   else if(isNaN(document.CreatePost.Aggregate.value))
   {
     alert("Minimum Percentage Requirement is invalid !");
     document.CreatePost.Aggregate.focus();
     return false;
   }
   else if(document.CreatePost.Qualification.value=="")
   { 
     alert("Please give Qualification for the Post !!");
     document.CreatePost.Qualification.focus();
     return false;
   }
   else if(document.CreatePost.Experience.value=="")
   { 
     alert("Please give Experience for the Post !!");
     document.CreatePost.Experience.focus();
     return false;
   }
   else if(document.CreatePost.Branch.value=="")
   { 
     alert("Please give Branch for the Post !!");
     document.CreatePost.Branch.focus();
     return false;
   }
   else if(document.CreatePost.Agelimit.disabled == false & document.CreatePost.Agelimit.value=="")
   { 
     alert("Please give Age Limit for the Post !!");
     document.CreatePost.Agelimit.focus();
     return false;
   }
   else if(document.CreatePost.Agelimit.disabled == false & isNaN(document.CreatePost.Agelimit.value))
   { 
     alert("Age Limit given for the Post is invalid !!");
     document.CreatePost.Agelimit.focus();
     return false;
   }
   else if(document.CreatePost.Vacancy.value=="")
   { 
     alert("Please give Vacancy for the Post !!");
     document.CreatePost.Vacancy.focus();
     return false;
   }
   else if(isNaN(document.CreatePost.Vacancy.value))
   {
     alert("Vacancy is invalid !");
     document.CreatePost.Vacancy.focus();
     return false;
   }
   else if(document.CreatePost.Category.value=="")
   { 
     alert("Please give Categories for the Post !!");
     document.CreatePost.Category.focus();
     return false;
   }
   else 
   document.CreatePost.submit();
 }

function cancel(doEvent)
{
  document.CreatePost.EVENTNAME.value=doEvent;
  document.CreatePost.submit();
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

      <FORM id="CreatePost" name="CreatePost" action="/ORS/AdminController" method="post">
      <INPUT TYPE="hidden" name="EVENTNAME">
      <INPUT name='Post_Id' type='hidden' value = '<%= Post_Id %>'>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>

          <TABLE name="Table2" cellSpacing=0 cellPadding=0 width="90%" align="center" border=0>
          <TBODY>
          <TR>
            <TD align=center class="Title1">&nbsp;&nbsp;Post Management :</TD>
            <TD align=left class="Title1">&nbsp;&nbsp;Create a new Post</TD></TR>
          <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD></TR>
          <TR>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD></TR>

            <c:if test="<%= (exception != null) %>">
               <TR><TD class="LabelA" style="color:#FF3333" colSpan=3>
               Error : <%= exception.getMessage() %><BR><%= exception.getSolution() %>
               </TD><TD></TD></TR>
			   <TR><TD><HR></TD><TD><HR></TD></TR>
            </c:if>

          <TR>
            <TD vAlign='center' align='right' width=180 class="LabelA">Post Name :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
            <TD vAlign=top align=left width=400>
		     <INPUT name="PostName" maxLength=45 style="WIDTH: 207px;" size=25 class="breadcrumbs"
		            value= '<% if(post != null) out.print(post.getName()); %>'>&nbsp;</TD></TR>
          <TR>
            <TD vAlign='center' align='right' width=180>&nbsp;</TD>
            <TD vAlign='top' width=400>&nbsp;</TD></TR>
          <TR>
            <TD vAlign='center' align='right' width=180 class="LabelA">Description :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
            <TD vAlign='top' align='left' width=400>
	         <TEXTAREA name="PostDesc" style="WIDTH: 288px; HEIGHT: 47px" class="breadcrumbs"
                       cols=31><% if(post != null) out.print(post.getDescription()); %></TEXTAREA>&nbsp;</TD></TR>
          <TR>
            <TD vAlign='center' align='right' width=180>&nbsp;</TD>
            <TD vAlign='top' width=400>&nbsp; </TD></TR>
          <TR>
            <TD vAlign='center' align='right' width=180 class="LabelA">Minimum Percent :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
            <TD vAlign='top' align='left' width=400>
	         <INPUT name="Aggregate" maxLength=2 style="WIDTH: 151px;" size=19
                    value= '<% if(post != null) out.print(post.getAggregate()); %>' class="breadcrumbs"></TD></TR>
          <TR>
            <TD vAlign='center' align='right' width=180>&nbsp;</TD>
            <TD vAlign=top width=400>&nbsp; </TD></TR>
          <TR>
            <TD vAlign='center' align='right' width=180 class="LabelA">Qualification :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
            <TD vAlign="top" width=400><SELECT name="Qualification" class="breadcrumbs">
            <% 
			   ArrayList buffer = new ArrayList();
			   
			   if(post == null)
			    buffer.add("--Select Qualification--");

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
			    
			     if(post != null && buffer.get(i).equals(post.getQualification()))
			        out.println("selected");
			        
			    if(i == 0 && post == null)
			        out.println("selected");
			      
			     out.println(">" + buffer.get(i));
			     i++;
			   }
		    %>
            </OPTION></SELECT><BR></TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180>&nbsp;&nbsp;</TD>
             <TD vAlign='top' width=400>&nbsp;&nbsp;</TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180 class="LabelA">Experience :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
             <TD vAlign='top' align='left' width=400>
		     <SELECT id="Experience" name="Experience" style="WIDTH: 155px" class="breadcrumbs">
            <% 
			   if(post == null)
				 out.println("<OPTION value= \"1\" selected>--Select Experience--");
			   
			   if(post != null && post.getExperience() == 1)
				 out.println("<OPTION value= \"1\" selected>Fresher");
			   else
			     out.println("<OPTION value= \"1\">Fresher");
    		   
			   if(post != null && post.getExperience() == 2)
			     out.println("<OPTION value=\"2\" selected>1-2yrs");
			   else
			     out.println("<OPTION value=\"2\">1-2yrs");
			   
			   if(post != null && post.getExperience() == 3)
			     out.println("<OPTION value=\"3\" selected>2-5yrs");
			   else
			     out.println("<OPTION value=\"3\">2-5yrs");
		       
			   if(post != null && post.getExperience() == 4)
			     out.println("<OPTION value=\"4\" selected>5-10yrs");
			   else
			     out.println("<OPTION value=\"4\">5-10yrs");
	           
			   if(post != null && post.getExperience() == 5)
			     out.println("<OPTION value=\"5\" selected>above 10yrs");
			   else
			     out.println("<OPTION value=\"5\">above 10yrs");
		    %>
           </OPTION></SELECT> </TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180>&nbsp;</TD>
             <TD vAlign=top width=400>&nbsp;</TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180 class="LabelA">Branch :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </TD>
             <TD><SELECT name="Branch" style="WIDTH: 155px" class="breadcrumbs">
		    <% 
			   ArrayList buff = new ArrayList();
			   
			   if(post == null)
			    buff.add("--Select Branch--");
			   
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
			    
			    if(post != null && buff.get(k).equals(post.getBranch()))
			        out.println("selected");

			    if(k == 0 && post == null)
			        out.println("selected");

			    out.println(">" + buff.get(k));
			    k++;
			   }
            %>
           </OPTION></SELECT></TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180>&nbsp;</TD>
             <TD vAlign='top' width=400>&nbsp;</TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180 class="LabelA">Age Limit :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
             <TD><SELECT name="AgeBound" style="WIDTH: 67px;" onChange="Agefun(this.form)" class="breadcrumbs">
           <% 
             if(post != null && post.getAgelimit() == -999)  // (post.getAgelimit() > 60 || post.getAgelimit() < 17)
             {
               out.write("<OPTION value=1>Yes<OPTION value=0 selected>No</OPTION>");
               out.write("</SELECT>");
               out.write("&nbsp;&nbsp; &nbsp;<INPUT name=\"Agelimit\" maxLength=2 style=\"LEFT: 83px; WIDTH: 72px; \" size=6 class=\"breadcrumbs\" disabled> ");
             }
             else
             {
               out.println("<OPTION value=1 selected>Yes<OPTION value=0>No</OPTION>");
               out.write("</SELECT>");
               out.write("&nbsp;&nbsp; &nbsp;<INPUT name=\"Agelimit\" maxLength=2 style=\"LEFT: 83px; WIDTH: 72px; \" size=6 class=\"breadcrumbs\" value='");
               
               if(post != null)
                  out.print(post.getAgelimit() + "'> ");
               else
                  out.print("'> ");
             }
           %>
		     </TD>
          </TR>
          <TR>
             <TD vAlign='center' align='right' width=180></TD>
             <TD vAlign=top width=400>&nbsp; </TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180 class="LabelA">Vacancy :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
             <TD><INPUT name="Vacancy" maxLength=6 value= '<% if(post != null) out.print(post.getVacancy()); %>' class="breadcrumbs"> </TD></TR>
          <TR>
             <TD width=180>&nbsp;</TD>
             <TD vAlign='top' width=400>
		     <FONT face="ARIAL, HELVETICA" size=-2>Select any number of categories<BR>for a particular Post </FONT></TD></TR>
          <TR>
             <TD vAlign='center' align='right' width=180 class="LabelA">Select Categories :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
             <TD><SELECT name="Category" style="WIDTH: 285px;" class="breadcrumbs" multiple>
          <%
		     ResultSet rs = null;

		     conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
		     Database db = new Database(conn);
             rs = db.RetriveDb("Select Category_Id,Name from Category");

             if(post != null)
             {
			   try
			   {
			     int compare = 0;
			     boolean equals = false;
			     
			     while(rs.next())
			     {
			       equals = false;
			       compare = rs.getInt("Category_Id");
			       
			       if(compare != 0)
			       {
			         int iter = 0;
			         int[] catid = post.getCategoryId();

			         while(iter < catid.length && equals == false)
			         {
			           if(compare == catid[iter])
			           {
			             equals = true;
			           }
			           iter++;
			         }
			       }
			       
			       if(equals == true)
			       {
			         out.print("<OPTION value='" + compare + "' selected>");
			         out.println(rs.getString("Name"));
			       }
			       else
			       {
			         out.print("<OPTION value='" + compare + "'>");
			         out.println(rs.getString("Name"));
			       }
			     }
			     out.print("</OPTION>");
		       }
		       catch(Exception e)
			   {
			     out.println(e.getMessage());
			   }
			 }
			 else
             {
            System.out.println("post id = " + post);
			   try
			   {
			     while(rs.next())
			     {
  		           out.print("<OPTION value='" + rs.getInt("Category_Id") + "'>");
			       out.println(rs.getString("Name"));
			     }
			     out.print("</OPTION>");
		       }
		       catch(Exception e)
			   {
			     out.println(e.getMessage());
			   }
			 }
		  %>
          </SELECT></TD></TR>
        <TR>
          <TD width=180>&nbsp;</TD>
		  <TD width=180>&nbsp;</TD></TR>
        <TR>
          <TD vAlign='center' align='right' width=180><FONT 
            face="ARIAL, HELVETICA" size=-1>&nbsp; </FONT></TD>
          <TD vAlign='top' width=400 height=60><BR>
          <INPUT class="B1" name="Ok" type=button value="   Done  " onclick=validate('CREPOST_OK') size=23>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <INPUT class="B1" name="reset" type=reset value=Reset size=30>&nbsp;&nbsp;&nbsp; 
          <INPUT class="B1" name="Cancel" type=button value=" Cancel " onclick=cancel('CREPOST_CL')> 
          <BR>
         </TD></TR>
     </TBODY></TABLE>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>