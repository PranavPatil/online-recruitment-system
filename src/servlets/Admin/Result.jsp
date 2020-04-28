<%@ page contentType="text/html" %>
<%@ page import="java.sql.*" %>  
<%@ page import="java.io.*" %>  
<%@ page import="java.util.*" %>  

<jsp:useBean id="connect" scope="session" class="ORS.ConnPool.Connect"/>    
<jsp:setProperty name="connect" property="clear" value="dest"/>    							

<html>
<head>
<title>Post View</title>
<script language=javascript>

<!-- This script and many more are available free online at -->
<!-- The JavaScript Source!! http://javascript.internet.com -->

<!-- Begin
function setDataType(cValue)
  {
    // THIS FUNCTION CONVERTS DATES AND NUMBERS FOR PROPER ARRAY
    // SORTING WHEN IN THE SORT FUNCTION
    var isDate = new Date(cValue);
    if (isDate == "NaN")
      {
        if (isNaN(cValue))
          {
            // THE VALUE IS A STRING, MAKE ALL CHARACTERS IN
            // STRING UPPER CASE TO ASSURE PROPER A-Z SORT
            cValue = cValue.toUpperCase();
            return cValue;
          }
        else
          {
            // VALUE IS A NUMBER, TO PREVENT STRING SORTING OF A NUMBER
            // ADD AN ADDITIONAL DIGIT THAT IS THE + TO THE LENGTH OF
            // THE NUMBER WHEN IT IS A STRING
            var myNum;
            myNum = String.fromCharCode(48 + cValue.length) + cValue;
            return myNum;
          }
        }
  else
      {
        // VALUE TO SORT IS A DATE, REMOVE ALL OF THE PUNCTUATION AND
        // AND RETURN THE STRING NUMBER
        //BUG - STRING AND NOT NUMERICAL SORT .....
        // ( 1 - 10 - 11 - 2 - 3 - 4 - 41 - 5  etc.)
        var myDate = new String();
        myDate = isDate.getFullYear() + " " ;
        myDate = myDate + isDate.getMonth() + " ";
        myDate = myDate + isDate.getDate(); + " ";
        myDate = myDate + isDate.getHours(); + " ";
        myDate = myDate + isDate.getMinutes(); + " ";
        myDate = myDate + isDate.getSeconds();
        //myDate = String.fromCharCode(48 + myDate.length) + myDate;
        return myDate ;
      }
  }
function sortTable(col, tableToSort)
  {
    var iCurCell = col + tableToSort.cols;
    var totalRows = tableToSort.rows.length;
    var bSort = 0;
    var colArray = new Array();
    var oldIndex = new Array();
    var indexArray = new Array();
    var bArray = new Array();
    var newRow;
    var newCell;
    var i;
    var c;
    var j;
    // ** POPULATE THE ARRAY colArray WITH CONTENTS OF THE COLUMN SELECTED
    for (i=1; i < tableToSort.rows.length; i++)
      {
        colArray[i - 1] = setDataType(tableToSort.cells(iCurCell).innerText);
        iCurCell = iCurCell + tableToSort.cols;
      }
    // ** COPY ARRAY FOR COMPARISON AFTER SORT
    for (i=0; i < colArray.length; i++)
      {
        bArray[i] = colArray[i];
      }
    // ** SORT THE COLUMN ITEMS
    //alert ( colArray );
    colArray.sort();
    //alert ( colArray );
    for (i=0; i < colArray.length; i++)
      { // LOOP THROUGH THE NEW SORTED ARRAY
        indexArray[i] = (i+1);
        for(j=0; j < bArray.length; j++)
          { // LOOP THROUGH THE OLD ARRAY
            if (colArray[i] == bArray[j])
              {  // WHEN THE ITEM IN THE OLD AND NEW MATCH, PLACE THE
                // CURRENT ROW NUMBER IN THE PROPER POSITION IN THE
                // NEW ORDER ARRAY SO ROWS CAN BE MOVED ....
                // MAKE SURE CURRENT ROW NUMBER IS NOT ALREADY IN THE
                // NEW ORDER ARRAY
                for (c=0; c<i; c++)
                  {
                    if ( oldIndex[c] == (j+1) )
                    {
                      bSort = 1;
                    }
                      }
                      if (bSort == 0)
                        {
                          oldIndex[i] = (j+1);
                        }
                          bSort = 0;
                        }
          }
    }
  // ** SORTING COMPLETE, ADD NEW ROWS TO BASE OF TABLE ....
  for (i=0; i<oldIndex.length; i++)
    {
      newRow = tableToSort.insertRow();
      for (c=0; c<tableToSort.cols; c++)
        {
          newCell = newRow.insertCell();
          newCell.innerHTML = tableToSort.rows(oldIndex[i]).cells(c).innerHTML;
        }
      }
  //MOVE NEW ROWS TO TOP OF TABLE ....
  for (i=1; i<totalRows; i++)
    {
      tableToSort.moveRow((tableToSort.rows.length -1),1);
    }
  //DELETE THE OLD ROWS FROM THE BOTTOM OF THE TABLE ....
  for (i=1; i<totalRows; i++)
    {
      tableToSort.deleteRow();
    }
  }
//  End -->

</script>
</head>
<body bgcolor="ivory">
<P>
<form action="http://localhost:8080/examples/servlets/Admin/Result.jsp" method="get" name=form1>
<TABLE cellSpacing=1 cellPadding=5 width="100%" align=center border=0 id=TABLE1>
  
  <tr vAlign="center" align="middle" bgColor="##bcdde">
    	<TD style="VERTICAL-ALIGN: middle; BACKGROUND-COLOR: #bcdde; TEXT-ALIGN: center" colSpan="9">
		<FONT face="Verdana" size="-1"><STRONG style="COLOR: Black; BACKGROUND-#bcdde: #darkgreen">Result 
				-View</STRONG></FONT>
	    </TD>
	</tr>
  <TR>
    <TD colspan="9">&nbsp;</TD></TR>
    
    <tr>
    	<td></td><td><strong>Post:</strong><td>
    	<select style="WIDTH: 90px" name=postname>
    	
  <%
    int Post_Id = 0,var = 0;
    
    try
    {  
      Post_Id= Integer.parseInt(request.getParameter("postname"));
    }
    catch(NumberFormatException e)
    {
      System.out.println("Problem in Post_Id !!");
      System.out.println(e.getMessage());
      Post_Id = 0;
    }  
    System.out.println("Post_id = " + Post_Id);	
  
    ResultSet rs1 = null;
    String query = null,name = null;
    
    query = "select POSTNAME,Post_Id from post";             
    rs1 = connect.setRSQuery(query);  
        	
	try
	{
		while(rs1.next())
	    {
	      var = rs1.getInt(2);
	      name = rs1.getString(1);
	      
	      if(Post_Id == var)
	      {
		    out.print("<option value=" + var + " selected>" + name);
		  } 
		  else
		  {
		    out.print("<option value=" + var + ">" + name);
		  }
		   out.print("</option>");
		}
		 
	}
	catch(Exception e)
	{
	  System.out.println("Problem in Post Retrival !");
	  System.out.println(e.getMessage());	  
	}		
  %>
   
    </select></td></td><td>
	
    <input type="Submit" name="btnGo" value=" Go ->"></td>
  </tr>
  </form>   
 </table>
    
 <form action="http://localhost:8080/examples/servlet/" method="get" name=form1>
 <TABLE cellSpacing=1 cellPadding=5 width="100%" align=center border=0 name="rsTable" id=rsTable cols=9>

 <TR>
    <TD>
       <P align=center><STRONG><FONT face=Verdana size=1></FONT></STRONG></P></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(1, rsTable);"><STRONG><FONT face=Verdana size=1><B>User Name</FONT></B></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(2, rsTable);"><STRONG><FONT face=Verdana size=1><B>Total%age</FONT></B></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(3, rsTable);"><STRONG><FONT face=Verdana size=1>Attempt%age</FONT></STRONG></P></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(4, rsTable);"><STRONG><FONT face=Verdana size=1>Agg.Result</FONT></STRONG></P></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(5, rsTable);"><STRONG><FONT face=Verdana size=1>DateOfExamination</FONT></STRONG></P></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(6, rsTable);"><STRONG><FONT face=Verdana size=1>TotalAttempt</FONT></STRONG></P></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(7, rsTable);"><STRONG><FONT face=Verdana size=1>Qualification</FONT></STRONG></P></A></TD>
	<TD bgColor="#abcdef">
	   <P align=center><A href="javascript:sortTable(8, rsTable);"><STRONG><FONT face=Verdana size=1>Experience</FONT></STRONG></P></A></TD>
 </TR>            
  
  <%   
     String str = null;
     int Experience = 0;
     
     if(Post_Id != 0)
     {     
       ResultSet TData = null,UData = null;
     
       query = "select FName,LName,Qualification,Experience from users where user_Id in" + 
               " (select user_Id from result where Post_Id = " + Post_Id + ")";
             
       UData = connect.setRSQuery(query);  
     
       query = "select T_Percent,T_Attempt,T_Result,TestDate,Attempt_No" + 
               " from Result where Post_Id = " + Post_Id; 
             
       TData = connect.setRSQuery(query);  
       System.out.println("No Problem !!");
       try
       {      
         while(UData.next() & TData.next())
         {             
		   out.println("<TR>");
		   out.println("<td><input type=radio name=choose checked>");
		   out.println("</td>");		   
		   out.println("<td>");
		   out.println(UData.getString(1) + " " + UData.getString(2));
		   out.println("</td>");
         
		   out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		   out.println(TData.getInt(1));
		   out.println("</td>");
		 
		   out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		   out.println(TData.getInt(2));
		   out.println("</td>");
		 
		   out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		   out.println(TData.getString(3));
		   out.println("</td>");
         
		   out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;");
		   str = TData.getString(4);
		   str = str.substring(0,10);
		   System.out.println("str" + str);
		   out.println(str);
		   out.println("</td>");
		 
		   out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		   out.println(TData.getInt(5));
		   out.println("</td>");			 
		  
		   out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		   out.println(UData.getString(3));
		   out.println("</td>");			 

		   out.println("<td>&nbsp;&nbsp;");
		   Experience = UData.getInt(4);

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
		   
		   out.println("</td>");			 		 
           out.println("</TR>");             
	     }
	   }
	   catch(Exception e)
	   {
	 	 System.out.println("Problem in Result Retrival !!");
		 System.out.println(e.getMessage());
		 System.out.println(e.getStackTrace());
	   }	
	 }                             	 
  %>       
  </table>
  
  <table cellSpacing=1 cellPadding=5 width="100%" align=center border=0 id=end>
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
	<center>
	<tr align="right"><td><input type="Submit" name="btnSubmit" value=" Select "></td>
	<td><input type="button" name="btnHelp" value=" Details "></td>
	<td></td>
	<td><input type="Reset" value="   Refresh   " name="btnReset"></td>
	</tr>
	</center>  
  </table>	    
  <P></P>
  <P></P>
  <P></P></P>
  </form>   
</body>
</html>

    