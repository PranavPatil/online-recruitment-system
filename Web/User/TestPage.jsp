<%@ page contentType="text/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %>
<%@ page import = "ORS.ConnPool.ConnectionPool" %>
<%@ page import = "ORS.ConnPool.Database" %>
<%@ page import = "ORS.Model.Category" %>
<%@ page import = "ORS.Utils.Log" %>
<%@ page import = "ORS.Exception.ApplicationException" %>
<%@ page import = "ORS.User.TestBuffer" %>

<HTML>
<HEAD>
<%
   long User_Id = 0;
   int Post_Id = 0,Test_Id = 0,Cat_Id = 0;
   int hr = 0, mn = 0,sc = 0;
   boolean timeout = false;
   String Cat_Name = null;
   TestBuffer testbuff = null;

   String path = getServletContext().getRealPath(getServletContext().getInitParameter("UserLog"));
   Log file = new Log(path);

   session=request.getSession(false);

   User_Id = (Long)session.getAttribute("User_Id");
   Post_Id = (Integer)session.getAttribute("Post_Id");
   Cat_Id = (Integer)session.getAttribute("Cat_Id");
   Test_Id = (Integer)session.getAttribute("Test_Id");
   
   if(User_Id == 0 || Post_Id == 0 || Cat_Id == 0 || Test_Id == 0)
   {
      throw new Exception("Test not Initialized");
   }

   if(session.getAttribute("TestBuffer") != null)
   {
     testbuff = (TestBuffer)session.getAttribute("TestBuffer");
   }
   else
      throw new Exception("Buffer not created");
   
   Cat_Name = Category.getName(testbuff.getConnectionPool(),Cat_Id);
    
    /*--------------------- Timer Checking and Display -----------------------------*/
	         
    if (timeout != true)
    {
      Calendar CurTime = Calendar.getInstance();
      timeout = testbuff.FinalTime.before(CurTime);
      
      if(timeout == true) 
      {
        testbuff.Ans1 = null;
        testbuff.Ans2 = null;
        testbuff.Ans3 = null;
        testbuff.Ans4 = null;
     	file.writeLog(User_Id,"Timeout of the Test");
     	timeout = true;
      }    
      else if(testbuff.Hide == false)
      {
        int mins = 0,hrs = 0,sec = 0;

      	mn= CurTime.get(Calendar.MINUTE);
        hr = CurTime.get(Calendar.HOUR_OF_DAY);
        sc= CurTime.get(Calendar.SECOND);

        mins = testbuff.FinalTime.get(Calendar.MINUTE);
        hrs = testbuff.FinalTime.get(Calendar.HOUR_OF_DAY);
        sec = testbuff.FinalTime.get(Calendar.SECOND);
    
        if(sec < sc)
        {
          sc = sec - sc + 60;
          mins = mins - 1;
        }
        else
          sc = sec - sc;
      
        if(mins < mn)
        {
    	  mn = mins - mn + 60;
    	  hrs = hrs - 1;
        }
        else
          mn = mins - mn;
      
        if(hrs < hr)
          hr = hrs - hr + 24;
        else  
          hr = hrs - hr;
        
        System.out.println("****>>>" + hr + ">>>>>" + mn + ">>>>>>>" + sc + ">>>>>>>> ");
      }
    }
  %>
<TITLE><%= Cat_Name %> Test Page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<META http-equiv="pragma" content="no-cache">
<META http-equiv="cache-control" content="no-store">    <!-- HTTP 1.1 -->

<% response.setDateHeader ("Expires", 0); %>            <!-- disable caching at proxy server -->

<META NAME="Generator" CONTENT="EditPlus">
<META NAME="Author" CONTENT="">
<META NAME="Keywords" CONTENT="">
<META NAME="Description" CONTENT="">
<LINK href="/ORS/Web/images/links.css" type="text/css" rel="stylesheet">
<LINK href="/ORS/Web/images/Style.css"   type="text/css"  rel="stylesheet">
<script language=javascript>

 function Button(doEvent)
 {
   document.TestPage.USEREVENT.value=doEvent;
   document.TestPage.submit();
 }

<% 	if(timeout != true && testbuff.Hide == false)
	{
%>
       var timerID = null;
       var timerRunning = false;
	   var hours = <%= hr %>;
	   var minutes = <%= mn %>;
       var seconds = <%= sc %>;

	   function stopclock ()
	   {
         if(timerRunning)
		  clearTimeout(timerID);
         timerRunning = false; 
       }
                
       function startclock () 
       {
         stopclock();
         showtime();
       } 

       function showtime () 
       {
         if(seconds > 0) 
         {
           seconds = seconds - 1;
         }
         else if(seconds == 0)
         {
           if(minutes == 0)
           { 
             if(hours == 0)
             { 
             }
             else if(hours > 0)
             {
               hours = hours - 1;
               minutes = 59;
             }
           }
           else if (minutes > 0)
           {
             minutes = minutes - 1;
             seconds = 59;
           }
         }
         
         var timeValue = "" + ((hours >12) ? hours -12 :hours);
         timeValue += ((minutes < 10) ? ":0" : ":") + minutes;  
         timeValue += ((seconds < 10) ? ":0" : ":") + seconds; 

         document.TestPage.face.value = timeValue;

         timerID = setTimeout("showtime()",1000);
         timerRunning = true;
        
         if(seconds == 0 && minutes == 0 && hours == 0)
         {
           if(document.TestPage.USEREVENT.value != 'TSTSBMT')
           {
             Button('TSTSBMT');
           }
         }
       } 

       </script>
       </HEAD>
       <BODY text="#000000" link="#0000ff" alink="#008000" vlink="800080" onLoad="startclock()">
 <%
	}
    else
	{
      out.println("</script>");
      out.println("</HEAD>");
      out.println("<BODY>");
    }
 %>
   <jsp:include page="UserHeader.jsp" flush="true"/>

 	 <h2 align=center><%= Cat_Name %> Test</h2>
	 <hr>
	 <FORM name="TestPage" id='TestPage' action="/ORS/UserController" method='post'>
	 <input name="USEREVENT" type="hidden">
<%
    if(timeout != true)
    {
     if(testbuff.Hide == false)
	 {
      out.println("<p style=\"font-family: Arial;font-size: 16pt\" align=\"right\">Time Elapsed : ");
      out.println("<input type=\"text\" name=\"face\" size=3 value=\"\" align=\"right\" ");
      out.println("readonly=\"true\" style=\"font-family: Arial;font-size: 16pt\" maxlength=\"7\">");
      out.println("</p>");
	 }
	 
	 long val = 0;
	 int i=0;
	 boolean equal;
     
     //System.out.println("Q_Load %%**%%**%%**%%**%%**%%**%%**%%**> " + Q_Load);
     
     if(testbuff.Scrolling == false)
       testbuff.Prev = null;
     
     /*--------------- Actual Analysis Begins ----------------------------------*/


     //System.out.println("PPPREV -> " + testbuff.Prev);
     //System.out.println("NNNEXT -> " + testbuff.Next);
     
     if(testbuff.Next != null)
     {
      if(testbuff.Q_Display == testbuff.Q_Load)
      {
	   if(testbuff.list.size() < testbuff.max)
	   {				 
         if(testbuff.list.size() == 0)
         {
       	   do
       	   {
	         testbuff.Ques_Id = testbuff.doCallableStmt(Cat_Id,testbuff.Type);
       	   }   while(testbuff.Ques_Id == 0);
         }
         else
         {
           do
           {
       	     equal=false;

             do
             {
           	   testbuff.Ques_Id = testbuff.doCallableStmt(Cat_Id,testbuff.Type);
             }while(testbuff.Ques_Id == 0);

	         i=0;
	         while(equal != true && i < testbuff.list.size())
	         {	      
	 	         val = (Long)testbuff.list.get(i);
	 	         if(val == testbuff.Ques_Id)
	 	         {
	 	  	        equal = true;  // Repetition
	 	         }
	 	         i++;  
	         }
           }while(equal == true);
         }
         testbuff.list.add(testbuff.Ques_Id);
         System.out.println("List -------> " + testbuff.list);
         testbuff.Q_Display++;
         testbuff.Q_Load++;
         testbuff.NewEntry=true;
       }         
      }
      else if(testbuff.Q_Display < testbuff.Q_Load)
      {
      	testbuff.Q_Display++;
      	testbuff.Ques_Id = (Long)testbuff.list.get(testbuff.Q_Display - 1);
      }
     }
     else if(testbuff.Prev != null)
     {
    	if(testbuff.Q_Display > 1)
    	{
    		testbuff.Q_Display = testbuff.Q_Display - 1;
      	    testbuff.Ques_Id = (Long)testbuff.list.get(testbuff.Q_Display - 1);
    	}
     }
     
     if(testbuff.NewEntry == false)
     {
       ResultSet rs = null;
       Database db = new Database(testbuff.getConnectionPool());
       System.out.println("uid = " + User_Id + " pid = " + Post_Id + " Cid = " + Cat_Id + " qid = " + testbuff.Ques_Id);
	   rs=db.RetriveDb("Select N.User_Answer from TestQuestions," + 
                        "Table(TestQuestions.TestEntry)N where User_Id = " + User_Id +
                        " and Post_Id = " + Post_Id + " and Category_Id = " + Cat_Id +
                        " and N.Ques_Id = " + testbuff.Ques_Id);
	   if(rs.next())
         testbuff.option = rs.getInt(1);

       System.out.println("Previous Answer --> " + testbuff.option);
     }
     else if(testbuff.NewEntry == true)
       testbuff.option = 0;
    
     testbuff.Display(testbuff.Ques_Id,testbuff.option,out);
	
	 System.out.println("Q_Display -> " + testbuff.Q_Display);
	 System.out.println("Q_Load    -> " + testbuff.Q_Load);
%>
	 <BR><BR>
	 <P>&nbsp;&nbsp;&nbsp;
<% if(testbuff.Scrolling == true) {  %>
     <INPUT name="Prev" type="button" value="Prev Question" onclick="Button('TSTPREV')">
     &nbsp;&nbsp;
<% } %>
     <INPUT name="Next" type="button" value="Next Question" onclick="Button('TSTNEXT')">
     &nbsp;&nbsp;	
<% if(testbuff.Scrolling == true) {  %>
     <INPUT name="ReviewTest" type="button" value=" Review Test  " onclick="Button('TSTREVW')">
     &nbsp;&nbsp;
<% } %>
     <INPUT name="SubmitTest" type="button" value=" Submit Test  " onclick="Button('TSTSBMT')">
<%
    }
    else
    {
	  out.println("<center><h3>Sorry, The allotted time is over.</h3></center>");
	  out.println("<center><h3>Please click ok to continue.</h3></center>");
      out.println("<P>&nbsp;</P><P><CENTER>");
   	  out.println("<INPUT name=\"Ok\" type='button' value=\" OK\" onclick=\"Button('TSTSBMT')\" style=\"WIDTH: 99px; HEIGHT: 27px\" size=25></CENTER>");
    }
%>
  </P><BR><BR>
  <jsp:include page="UserFooter.jsp" flush="false"/>
  </FORM>
  </body>
  </html>