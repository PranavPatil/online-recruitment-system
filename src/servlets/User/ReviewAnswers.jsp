<html>
    <head>
        <title>Course Test</title>
<!--
        <style>
        BODY {font-family: Arial,Helvetica,Sans-Serif;}
        BODY {color: #003333; }
        BODY {font-size: 12pt; }
        BODY {background-color: #FFFFFF; }
        H1 {font-family: 'Times New Roman', Times, Serif;}
        H1 {color: #000066;}
        H1 {font-size: 14pt;}
        TD{font-family: Arial,Helvetica,Sans-Serif;}
        TD{color: #003333; }
        TD{font-size: 12pt; }
      </style>
      -->
    </head>
    <body bgcolor=ivory>
        <FORM action="http://localhost:8080/examples/servlet/End" >
            
                <table cellpadding="0" cellspacing="0" width="645" border="0" align=left style="WIDTH: 645px; HEIGHT: 236px">
                    <tr>
                        <td align="middle" valign="top" width="100%">
                            <table cellpadding="0" cellspacing="0" width="100%" border="0" 
      align=left>
                                <tr>
                                    <td align="left" bgcolor="#abcdef" valign=center >
                                        
                                            <H1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Course Test - Question Summary</H1>
                                        
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="middle" valign="top" width="100%">
                            <table cellpadding="2" cellspacing="0" width="600" border="0" 
      align=left>&nbsp;
        
                                <tr>
                                    <td align="middle" nowrap colspan="3"><b>Please click a question number to return to that question.</b></td>
                                </tr>
                                <tr>
                                    <td align="middle" nowrap colspan="3"><b>Otherwise, click submit.</b></td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <th align="middle" valign="top">Question Number</th><th align="middle" valign="top">Answered</th><th align="middle" valign="top">Unanswered</th>
                                </tr>                                                                
                                
                                <%@ page import="java.sql.*" %>  
  								<%@ page import="java.io.*" %>  
  								<%@ page import="java.util.*" %>  

  
  								<%! PrintWriter out = null;
      								int size = 0;
                                    int User_Id = 0;
      								int Test_Id = 0;      								
      								String query = null; %>
  
    							<jsp:useBean id="connect" scope="session" class="ORS.ConnPool.Connect"/>    
    							<jsp:setProperty name="connect" property="clear" value="dest"/>
    							
    							<% 
    							   Object s =  session.getAttribute("User_Id");
    							   User_Id = Integer.parseInt(s.toString());
    							   Object t =  session.getAttribute("Test_Id");
    							   Test_Id = Integer.parseInt(t.toString());    							   
    							%>
          
                                <% query = "select N.User_Answer from TestQuestions,Table(TestQuestions.TestEntry)N where User_Id = " +
                                            User_Id + " and Test_Id = " + Test_Id + " order by N.Ques_Number"; 
                                            
                                   connect.setQuery(query);   %>       

    							<%
       								String f;
       								int value;
       								size =  connect.getSize();
       								for(int i =0;i<size;i++)
       								{       
         								connect.setId(i);
         								System.out.println("Size = " + size);
         								out.println("<tr>");
         								f = connect.getCat();
         								value = Integer.parseInt(f);
         								System.out.println("Value = " + value);
         								
			  							out.println("<tr>");
                                        out.println("<td align=\"middle\" valign=\"top\">");
                                     					
         								if(value == 0)
         								{
         					              out.println((i + 1));
                                          out.println("</A></td><td align=\"middle\" valign=\"top\">");
                                          out.println("<IMG src=\"../gifs/checkbox_0.jpg\" border=0></td>");
                                          out.println("<td align=\"middle\" valign=\"top\">");
                                          out.println("<IMG src=\"../gifs/checkbox_1.jpg\" border=0></td>");
         								}
         								else
         								{
                                          out.println((i + 1));
                                          out.println("</A></td><td align=\"middle\" valign=\"top\">");
                                          out.println("<IMG src=\"../gifs/checkbox_1.jpg\" border=0></td>");
                                          out.println("<td align=\"middle\" valign=\"top\">");
                                          out.println("<IMG src=\"../gifs/checkbox_0.jpg\" border=0></td>");
         								}
         								out.println("</tr>");
       								}       
     							%>

                            </table>
      <P>&nbsp;</P>
      <P>&nbsp;</P>
                        </td>
                    </tr>
                    <tr>
                        <td align="middle" valign="bottom" width="100%">
                            <table cellpadding="0" cellspacing="0" width="100%" border="0" 
      align=left>
                                <tr><td>&nbsp;</td></tr>
                                <tr>
                                    <td align="middle"><input value="Submit Test" name="disp" type="submit"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>            
        </FORM>
    </body>
</html>
