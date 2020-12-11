<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft Visual Studio 6.0">
<META http-equiv="Expires" Content="no-cache">
<META http-equiv="Pragma" Content="no-cache">
<META Content="" name=keywords>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="/ORS/Web/images/links.css" rel="stylesheet" type="text/css">
<TITLE>Home Page</TITLE>
</HEAD>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">

    <!--

    function scrollerObj(name,initH,initW,heightB,widthB,content,initBg,Bg,speed,initFl){

    //**data**//

    this.name=name;

    this.initH=initH;

    this.initW=initW;

    this.heightB=heightB;

    this.widthB=widthB;

    this.content=content;

    this.initBg=initBg;

    this.Bg=Bg;

    this.iniFl=initFl;

    this.speed=parseInt(speed);

    this.timer = name + "Timer";

    this.elem;


    //**methods**//

    this.getElement = getElement;

    this.createLayer=createLayer;

    this.scrollLayer = scrollLayer; 

    this.scrollLoop=scrollLoop;


    //**initiate methods**//

    this.createLayer();

    this.getElement();

    this.scrollLayer();

    }


    //**call this method to stop scrolling**//

    function scrollLoop(s){

    this.speed = s;

    }


    //**pretty obvious**//

    function scrollLayer(){

    if(parseInt(this.elem.style.top)>(this.elem.offsetHeight*(-1))){

        this.elem.style.top = parseInt(this.elem.style.top)-this.speed;

        //alert(parseInt(this.elem.style.top)+"\n"+this.elem.id);

    }

    else {this.elem.style.top = this.initH;}

    }



    //**get the specific dom-expression**//

    function getElement(){

    if(document.getElementById){

        this.elem = document.getElementById(this.name);

        }

    else if (document.all){

    this.elem = document.all[name];

        }

    else if (document.layers){

    this.elem = document.layers[name];

        }

    }


    //**pretty obvious - if NS4 - please upgrade to a standard compliant browser**//

    function createLayer(){

    if(document.getElementById || document.all){

    document.write('<div id="layer'+this.name+'" style="position:relative;overflow:hidden;float:'+this.initFl+';background-color:#'+this.initBg+';border:0px solid black;width:'+this.initW+'px;height:'+this.initH+'px;" onMouseover="'+this.name+'.scrollLoop(0)" onMouseout="'+this.name+'.scrollLoop('+this.speed+')">');

    document.write('<div id="'+this.name+'" style="position:absolute;top:'+this.initH+'px;left:0px;border:0px solid black;width:'+this.widthB+'px;height:'+this.heightB+'px;background-color:#'+this.Bg+'">');

    document.write(this.content);

    document.write('<\/div><\/div>');}

    else if(document.layers){

        document.write('<ilayer name="'+this.name+'" bgcolor="#'+this.Bg+'" width="'+this.widthB+'" height="'+this.heightB+'">'+this.content+'<\/ilayer>');

        return;
    }

    if(this.scrollLayer){

      this.timer = setInterval(this.name+'.scrollLayer()','30');

    }

    }

    //-->


</SCRIPT>

<STYLE media="screen" type="text/css">

    <!-- 

    P{FONT-WEIGHT: normal; FONT-SIZE: xx-small; COLOR: #333333; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif; TEXT-DECORATION: none; margin:3px;}

    A{FONT COLOR=#ff00ff; FONT-SIZE: normal; SIZE: 3;}

    H1{FONT-WEIGHT: normal; FONT-SIZE: 17; COLOR: #6600ff; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif; TEXT-DECORATION: none; margin:30px; padding:0px;}

    -->

</STYLE>

<BODY style="MARGIN: 0px">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
    <TD vAlign=top align=middle width="100%">
      <TABLE width=50>
        <TBODY></TBODY></TABLE><IMG height=4 alt="" src="/ORS/Web/images/white-spacer.gif" width=732 align=top> 
        <jsp:include page="AdminHeader.jsp" flush="true"/>
      <TABLE cellSpacing=0 cellPadding=0 width=848 border=0>
        <TBODY>
        <TR>
          <TD vAlign=top align=left width=200 rowSpan=2>
            <jsp:include page="AdminPanel.jsp" flush="true"/>            
			<IMG height=40 alt="" src="/ORS/Web/images/spacer.gif" width=12> </TD>
          <TD vAlign=top align=right width=648 height=196>
            <DIV align=left>
            <TABLE height=94 cellSpacing=0 cellPadding=0 width=648 border=0>
              <TBODY>
              <TR>
                <TD vAlign=center align=left><IMG height=30 
                  src="/ORS/Web/images/spacer.gif" width=1> </TD></TR>
              <TR>
                <TD vAlign=center align=left>
                <DIV align="center">

                <SCRIPT type="text/javascript">

                 <!--

                   var a = 
                   '<h1>Dedicated To<\/h1><p><font color=#ff00ff size="2"><strong><a>My Mother : Manisha Arvind Patil<\/a></strong></font><\/p><br><br>' +
                   '<h1>Acknowledgements<\/h1><p><a href="mailto:www.horstmann.com/">Cay S. Horstmann<\/a> :  He teaches computer science at San Jose State University. He has written 6 books on C++, Java, and Oject oriented development.' + 
                   'He is the author of book <strong>Core Java</strong> and I owe him for teaching me JAVA.<\/p>' +
                   '<h1> <\/h1><p><a href="mailto:www.Person.com/">Gary Cornell<\/a> :  He is PhD from Brown University and has been a visiting scientist at IBM Watson Labs.' + 
                   'He is the author of book <strong>Core Java</strong> and I owe him too for teaching me JAVA.<\/p>' +
                   '<h1> <\/h1><p><a href="mailto:hall@coreservlets.com">Marty Hall<\/a> :  He is the president of coreservlets.com inc,a company that provides courses related to servlets and Jsp.' + 
                   'He is the author of book <strong>Core Servlets and Jsp</strong> and I thank him for his great work.<\/p>' +
                   '<h1> <\/h1><p><a href="mailto:brown@coreservlets.com">Larry Brown<\/a> :  He is a Senior Network Engineer and Oracle DBA in US Navy.' + 
                   'He is also the author of book <strong>Core Servlets and Jsp</strong> and I thank him for writing such a GREAT book.<\/p>' +
                   '<h1>Developed By<\/h1><p><a href="mailto:pranav_558@yahoo.com">Pranav Patil <\/a> :  Chief Project Designer, Database Administrator, Senior Software Developer, Senior Software Tester. He is a superb programmer and an expert in Application Design.' + 
                   'He is a is an expert in Java. Other skills include J2EE, C, C#, PL\SQL and Oracle.<\/p>';

                   var c = new scrollerObj('c','300','500','100','500',a,'#99CCCC','#99CCCC','2','center');

                 //-->

                 </SCRIPT>

                 </DIV>
                 <BR><BR><BR>
                </TD></TR>
                 </TD></TR>
                </TBODY></TABLE>
				 </TD></TR>
			</TBODY></TABLE></TD>
         </TR>
    </TBODY></TABLE>
	<jsp:include page="AdminFooter.jsp" flush="false"/>
	</TD></TR></TBODY></TABLE>
	</BODY></HTML>