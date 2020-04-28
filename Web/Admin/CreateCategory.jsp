<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.Exception.DataEntryException" %>
<%@ page import = "ORS.Model.Category" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
   int Cat_Id = -1;
   DataEntryException exception = null;
   Category category = null;
   
   try
   {
     String str = null,event = null;
     exception = (DataEntryException) request.getAttribute("Exception");
     System.out.println("Exception = " + exception);
     
     event = request.getParameter("EVENTNAME");
     
     if(event.equals("CATMGT_EDT"))
     {
       str = request.getParameter("SelectCategory");
       
       if(str != null)
        Cat_Id = Integer.parseInt(str);
     }
   }
   catch(NullPointerException pex)
   {
     pex.printStackTrace();
     Cat_Id = -1;
     exception = null;
   }
   catch(NumberFormatException nex)
   {
     nex.printStackTrace();
     Cat_Id = -1;
   }
   
   System.out.println("Cat_d = " + Cat_Id);
   
   if(exception != null)
   {
     category = new Category(request);
     System.out.println("fname = " + category.getName());
   }
   else if(Cat_Id > 0)
   {
     ConnectionPool conn = (ConnectionPool)getServletContext().getAttribute("ConPool") ;
     category = new Category(Cat_Id,conn);
   }
   else
     category = null;
 %>

<HTML>
<HEAD>
<TITLE>Create Category</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript>

function verify(doEvent)
{
  document.CreateCat.EVENTNAME.value=doEvent;
  
  if(document.CreateCat.CatName.value=="")
  {
    alert("Please fill the Category Name !!");
    document.CreateCat.CatName.focus();
  }
  else if(document.CreateCat.CatDesc.value=="")
  {
    alert("Please fill the Category Description !!");
    document.CreateCat.CatDesc.focus();
  }
  else if(document.CreateCat.CatDesc.value.length>500)
  {
    alert("Category Description should not exceed over 500 alphabets !");
    document.CreateCat.CatDesc.focus();
  }
  else
    document.CreateCat.submit(); 
}

function cancel(doEvent)
{
  document.CreateCat.EVENTNAME.value=doEvent;
  document.CreateCat.submit(); 
}

</SCRIPT>
</HEAD>
<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
 <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%" height=532>
      <TABLE width=50><TBODY></TBODY></TABLE>
      <IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
      <jsp:include page="AdminHeader.jsp" flush="true"/>

      <FORM name="CreateCat" id="CreateCat" action="/ORS/AdminController" method="post">
      <INPUT name='Cat_Id' type='hidden' value = '<%= Cat_Id %>'>
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=176>

          <TABLE cellSpacing=2 border=0 cols=2 cellPadding=1 width="90%" align=center>
          <TBODY>
          <TR>
            <TD align="center" class="Title1" colSpan=2>Create&nbsp;Category</TD></TR>
          <TR>
            <TD colSpan=2>&nbsp;</TD></TR>
            
            <c:if test="<%= (exception != null) %>">
               <TR><TD class="LabelA" style="color:#FF3333" colSpan=3>
               Error : <%= exception.getMessage() %><BR><%= exception.getSolution() %>
               </TD></TR>
			   <TR><TD><HR></TD><TD><HR></TD></TR>
            </c:if>
            
		  <TR>
            <TD vAlign=center align=right width=180></FONT></TD>
            <TD vAlign=top width=400>&nbsp;</TD></TR>
          <TR>
            <TD class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Category Name&nbsp;&nbsp;</TD>
            <TD><INPUT name="CatName"size=38 maxLength=45 style="LEFT: 2px; WIDTH: 273px;" value = '<% if(category != null) out.print(category.getName()); %>' class="breadcrumbs"> </TD></TR>
          <TR>
            <TD class="LabelA">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Description&nbsp;</TD>
            <TD vAlign=top align=left width=400><TEXTAREA id="CatDesc" name="CatDesc" style="WIDTH: 273px;" rows=3 cols=10 maxLength="90" class="breadcrumbs"><% if(category != null) out.print(category.getDescription()); %></TEXTAREA>&nbsp;</TD></TR>
          <TR>
            <TD colSpan=2>&nbsp;</TD></TR>
          <TR>
            <TD colSpan=2>&nbsp;</TD></TR>
          <TR>
            <TD align="right">
		    <INPUT class="B1" name="SubmitCat" type="button" value="     Submit     " onclick="verify('CRECAT_CRE')">
	        </TD>
	        <TD>&nbsp;&nbsp;&nbsp;<INPUT class="B1" name="Cancel" type="button" value="  Cancel   " onclick="cancel('CRECAT_CL')" size=23>
            </TD>
          </TR>
          </TBODY></TABLE>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>