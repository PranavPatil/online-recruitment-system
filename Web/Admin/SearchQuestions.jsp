<%@ page contentType="text/html;charset=windows-1252" language="java" %>
<%@ page import = "java.sql.*" %>  
<%@ page import = "java.io.*" %>  
<%@ page import = "java.util.*" %>  
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>

<%!
    private int Cat_Id = -1;
    private String Type = null;
    private String Text = null;
%>

<%
    ConnectionPool pool=(ConnectionPool)getServletContext().getAttribute("ConPool") ;

    String str = request.getParameter("SelectCategory");

    if(str != null)
    {
      int i = 0;
      i = Integer.parseInt(str);
      
      if(i > -1)
      {
        Cat_Id = i;
      }
    }
    
    str = null;
    str = request.getParameter("SelectType");
    
    if(str != null)
    {
      Type = str;
      
      if(Type.equals("All"))
      {
        Type = null;
      }
    }
    
    Text = request.getParameter("Question");
 %>

<HTML>
<HEAD>
<TITLE>Search Questions</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<SCRIPT language=javascript src="/ORS/Web/images/Sort.js"></SCRIPT>
<SCRIPT language=javascript>

  function DelQButton(doEvent)
  {
    if(confirm("This button will DELETE the selected Test . Are you sure you want to continue?")) 
    {
      document.SearchQues.EVENTNAME.value=doEvent;
      document.SearchQues.submit();
    }
  }

  function Button(doEvent)
  {
    document.SearchQues.EVENTNAME.value=doEvent;
    document.SearchQues.submit();
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

      <FORM id="SearchQues" name="SearchQues" action="/ORS/AdminController">
      <INPUT name="EVENTNAME" TYPE="hidden">
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
          <P>
          <TABLE id="TABLE1" cellSpacing=1 cellPadding=1 width="100%" align="center" border=0>
          <TBODY>
          <TR vAlign="center" align="middle">
          <TD class="Title1" colSpan=9>Search Questions</TD>
          </TR>
          <TR><TD colSpan=9>&nbsp;</TD></TR><TR>
          <TD class="Title3">Search Questions :&nbsp;&nbsp;
           <INPUT name="Question" style="WIDTH: 393px;" class="breadcrumbs" size=46 value='<%  if(Text != null) out.print(Text); else  out.print("");%>'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <INPUT class="B1" name="btnGo" type="button" value=" Go ->" onclick="Button('SRHQUES_GO')" size=25></TD>
          </TR>
          <TR><TD class="Title3">Search Category&nbsp; :&nbsp;&nbsp;
          <SELECT style="WIDTH: 199px" name="SelectCategory" class="breadcrumbs">
          <%
             out.print("<OPTION value = 0>All");
             ResultSet rs = null;
             Database db = new Database(pool);
             rs=db.RetriveDb("Select Category_Id,Name from Category");
             int temp = 0;
            
             while(rs.next())
             {
	           temp = rs.getInt("Category_Id");
    	       
	           if(temp == Cat_Id)
	           {
	       	     out.print("<OPTION value = \"" + temp + "\" selected>");
	           }
	           else
	           {
	       	     out.print("<OPTION value = \"" + temp + "\">");
	           }
    	       
               out.print(rs.getString("Name"));
             }

             out.print("</OPTION>");
             rs.close();
          %>
          </SELECT>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <STRONG>Type:&nbsp; </STRONG>&nbsp;
         <SELECT name="SelectType" class="breadcrumbs">
          <%
            System.out.println("type = " + Type);
            if(Type == null)
            {
              out.print("<OPTION selected>All<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\">Multiple Answers<OPTION value = \"TF\">True or False</OPTION>");
            }
            else if(Type.equals("MC"))
            {
              out.print("<OPTION>All<OPTION value = \"MC\"  selected>Multiple Choice<OPTION value = \"MA\">Multiple Answers<OPTION value = \"TF\">True or False</OPTION>");
            }
            else if(Type.equals("MA"))
            {
              out.print("<OPTION>All<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\"  selected>Multiple Answers<OPTION value = \"TF\">True or False</OPTION>");
            }
            else if(Type.equals("TF"))
            {
              out.print("<OPTION>All<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\">Multiple Answers<OPTION value = \"TF\"  selected>True or False</OPTION>");
            }
            else
            {
              out.print("<OPTION selected>All<OPTION value = \"MC\">Multiple Choice<OPTION value = \"MA\">Multiple Answers<OPTION value = \"TF\">True or False</OPTION>");
            }
         %>
         </SELECT></TD>
      </TR>
      <TR><TD colSpan=9>&nbsp;</TD></TR>	 
      </TBODY></TABLE>
      <TABLE id="rsTable" name="rsTable" cellSpacing=1 cols=5 cellPadding=2 width="100%" align=center border=0>
      <TBODY>
      <TR>
        <TD bgColor="#abcdef" width='4%'></TD>
        <TD bgColor="#abcdef" width='68%'>
          <P align=center><A href="javascript:sortTable(1,rsTable);"><STRONG>
          <FONT face=Verdana size=1><B>Question</B></FONT></STRONG></A></P></TD>
        <TD bgColor="#abcdef" width='14%'>
          <P align=center><A href="javascript:sortTable(2,rsTable);"><STRONG>
          <FONT face=Verdana size=1><B>Type</B></FONT></STRONG></A></P></TD>
        <TD bgColor="#abcdef" width='8%'>
          <P align=center><A href="javascript:sortTable(3,rsTable);"><STRONG>
          <FONT face=Verdana size=1>Rank</FONT></STRONG></A></P></TD>
        <TD width='6%'></TD></TR>
        <%
           String query = null;
           
           if(Text != null)
           {
             ResultSet SData = null;
             String buffer = "";
             boolean flag = false;
             
             for(int i = 0;i < Text.length();i++)
             {
               char ch = Text.charAt(i);
               
               if(!buffer.equals("") || ch != ' ')
               {
                 if(ch == ' ')
                   flag = true;
                 
                 if(ch != ' ')
                 {
                   if(flag == true)
                   {
                     buffer = buffer + " ACCUM " + ch;
                     flag = false;
                   }
                   else
                    buffer = buffer + ch;
                 }
               }
             }
             
             if(!buffer.equals(""))
             {
               query = "Select Ques_Id,Substr(Question,0,68),Type,Score(10) from Questions where";
               String qry = "";
               
               if(Cat_Id > 0)
                 qry = " Category_Id = " + Cat_Id;
               
               if(Type != null)
               {
                 if(qry.equals(""))
                   qry = " Type = '" + Type + "'";
                 else
                   qry = qry + " and Type = '" + Type + "'";
               }
               
               if(qry.equals(""))
               {
                 query = query + " Contains(Question,'" + buffer + "',10) > 0 order by Score(10) desc";
               }
               else
               {
                 query = query + qry + " and Contains(Question,'" + buffer + "',10) > 0 order by Score(10) desc";
               }
               
               /*try
               {*/
                 //db.ExecuteDb("Alter Index Question_Context_Index Rebuild");
                 //System.out.println("Query = " + query);
                 SData = db.RetriveDb(query);
               
                 while(SData.next())
                 { 
		           String ques = SData.getString(3);
      		       
	               if(ques != null)
		           {
		             if(ques.equals("MC"))
		              ques = "Multiple Choice";
		             else if(ques.equals("MA"))
		              ques = "Multiple Answer";
		             else if(ques.equals("TF"))
		              ques = "True or False";
		             else
		              ques = "Not Available";
		           }
		           else
		             ques = "Not Available";
		           
                   out.println("<TR>");
                   out.println("<TD>");
                   out.println("<P align=center><INPUT type=radio value='" + SData.getLong(1) + "'");
                   out.println("alt=\"Select Question\" name=\"SelectQuestion\"> </P></TD>");
                   out.println("<TD>");
                   out.println("<P align=center><FONT class=h1><STRONG>" + SData.getString(2) +  "/ORS.</STRONG></FONT></P></TD>");
                   out.println("<TD>");
                   out.println("<P align=center><A><FONT class=h1><STRONG>" + ques + " ");
                   out.println("</STRONG></FONT></B></A></P></TD>");
                   out.println("<TD>");
                   out.println("<P align=center><FONT class=h1><A><STRONG>" + SData.getInt(4) + " ");
                   out.println("</STRONG></FONT></B></A></P></TD>");
                   out.println("<TD></TD></TR>");
	             }
	           /*}
	           catch(Exception e)
	           {
	 	         out.println("Problem in Result Retrival :" + e.getMessage());
		         e.printStackTrace();
	           }*/
             }
	       }
        %>

        </TBODY></TABLE>
      <TABLE id="End" cellSpacing=1 cellPadding=5 width="100%" align="center" border=0>
       <TBODY>
       <TR>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD></TR>
       <TR>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD>
          <TD vAlign=top width=400>&nbsp;</TD></TR>
       <TR Align="middle">
       <TD><INPUT class="B1" name="Edit" value="  Edit  " type="button" onclick="Button('SRHQUES_EDT')" size=25></TD>
       <TD><INPUT class="B1" name="Delete" value="  Delete  " type="button" onclick="DelQButton('SRHQUES_DEL')" size=25></TD>
       <TD><INPUT class="B1" name="Cancel" value="  Cancel  " type="button" onclick="Button('SRHQUES_CL')" size=25></TD>
	  </TR></TBODY></TABLE>
      <P></P><P></P><P></P><P></P>
      </TABLE>
    </FORM>
   <jsp:include page="AdminFooter.jsp" flush="false"/>
   </TD></TR></TBODY></TABLE>
 </BODY></HTML>