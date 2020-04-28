<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.io.*" %> 
<%@ page import = "java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ taglib prefix="ors" uri="http://www.sscl.com/tags/ors-taglib" %>

 <%!
     private int Post_Id = 0;
  %>

<% 
   ConnectionPool pool = (ConnectionPool)getServletContext().getAttribute("ConPool") ;

   int pid = 0;
   String str = null; 
   str = request.getParameter("Post_Id");
      
   if(str != null)
   {
     try
     {  
       Post_Id = Integer.parseInt(str);
     }
     catch(NumberFormatException e)
     {
       Post_Id = 0;
     }
   }
%>

<HTML>
<HEAD>
<TITLE>Publish Tests</TITLE>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript>

function Button(doEvent)
{
  document.PublishTest.EVENTNAME.value=doEvent;
  document.PublishTest.submit();
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

      <FORM id="PublishTest" name="PublishTest" action="/ORS/AdminController">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <TABLE id="TABLE1" cellSpacing=1 cellPadding=1 width="90%" align=center border=0>
          <TBODY> 
          <TR vAlign="center" align="middle">
          <TD class="Title1" colSpan="4">Publish Tests</TD>
	      </TR>
          <TR><TD colspan="9">&nbsp;</TD></TR>    
          <TR>
    	   <TD width="10%"></TD><TD width="51%" class="Title3">Post :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	   <SELECT name="Post_Id" class="breadcrumbs" style="WIDTH: 90px">

           <% String query = "Select PostName,Post_Id from Post"; %>
           <ors:fillcombo connectionPool="<%= pool %>" query="<%= query %>" selected="<%= Post_Id %>" />

           </SELECT></TD><TD width="39%">
           <INPUT class="B1" type="button" name="Go" value=" Go ->" onclick="Button('TESTPUB_GO')" size=33></TD>
          </TR>
          <TR><TD colspan="9">&nbsp;</TD></TR>
          </TBODY></TABLE>

         <TABLE id=rsTable cellSpacing=1 cellPadding=3 width="90%" align=center border=0 name="rsTable" cols=4>
         <TR>
	      <TD bgColor="#abcdef" width="65%" align=center class="Title3"><A><B>Category Name</B></TD>
	      <TD bgColor="#abcdef" width="25%" align=center class="Title3"><B>Test Published</B></TD>
         </TR>
         <TR>
           <TD vAlign=center align=right width=180></TD>
           <TD vAlign=top width=400>&nbsp; </TD></TR>
         <%
             String Tname[] = null;
             String cname = null;
             int Test_Id = 0,Cat_Id = 0;
             int TId[] = null;

             if(Post_Id != 0)
             {
               int count = 0,no = 0;
               Database db = new Database(pool);
               ResultSet TData = null,PData = null,CData = null;
       
               TData = db.RetriveDb("Select count(rownum) from Test");
               TData.next();
               count = Integer.parseInt(TData.getString(1));
       
               if(count != 0)
               {
                 count++;
                 Tname = new String[count];
                 TId = new int[count];
                 Tname[0] = "None";
                 TId[0] = 0;
         
                 TData = null;
                 TData = db.RetriveDb("Select Test_Id,Name from Test");
         
                 int a = 1;
         
                 while(TData.next())
                 {
                   TId[a] = TData.getInt(1);
                   Tname[a] = TData.getString(2);
                   a++;
                 }
               }
       
               query = "Select N.Category_Id,N.Test_Id from Post,TABLE(Post.CatEntry)N where Post_Id = " +
                               Post_Id;
       
               PData = db.RetriveDb(query);

               try
               {
                 while(PData.next())
                 {
		           out.println("<TR>");
        		   
		           Cat_Id = PData.getInt(1);
		           
        		   if(Cat_Id != 0)
		           {
		             CData = null;
		             CData = db.RetriveDb("Select Name from Category where Category_Id = " + Cat_Id);
		             CData.next();
		             cname = CData.getString(1);
		           }
        		   
		           out.println("<TD align=center class=Title3><INPUT name='Cat_Id" + no + "' type='hidden' value = '" + Cat_Id + "'>");
		           out.println(cname);
		           out.println("</TD>");
                   
                   Test_Id = PData.getInt(2);
                   
		           out.println("<TD><P align=center><SELECT name='Test_Id" + no + "' class=\"breadcrumbs\">");
		           no++;
		   
                   for(int x = 0;x < count;x++)
                   {
                     if(TId[x] == Test_Id)
                     {
                       out.println("<OPTION value = \"" + TId[x] + "\" selected>");
                     }
                     else
                     {
                       out.println("<OPTION value = \"" + TId[x] + "\">");
                     }
                     
                     out.print(Tname[x]);
                   }
        	       
                   out.println("</OPTION>");
                   out.println("</SELECT>");
		           out.println("</P></TD>");
                   out.println("</TR>");
	             }
	           }
	           catch(Exception e)
	           {
	 	         out.println("Problem in Result Retrival !!");
		         out.println(e.getMessage());
		         e.printStackTrace();
	           }
	         }
         %>
         </TABLE>

         <TABLE cellSpacing=1 cellPadding=3 width="85%" align=center border=0 id=end>
	       <TR>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
	       </TR>
	       <TR>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
		     <TD vAlign="top" width="400">&nbsp;</TD>
	       </TR>
	       <CENTER>
	       <TR>
	        <TD></TD><TD></TD>
	        <TD><input class="B1" type="button" name="btnPublish" value=" Publish " size=30 onclick="Button('TESTPUB_PUB')"></td>	
	        <TD><input class="B1" type="button" name="Back" value="<-- Back" onclick="Button('EXAMMGT_MN')"></td>
	       </TR>
	       </CENTER>
           </TABLE>
           <P></P><P></P><P></P><P></P>
      </TABLE>
      </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>