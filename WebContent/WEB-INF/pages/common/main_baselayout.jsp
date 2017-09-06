<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<%@ include file="./include.jsp"%>
<html:html locale="true">
<head>
<title><tiles:getAsString name="title"/></title>
 <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<script type="text/javascript" src="<c:url value='/pages/scripts/selectbox.js'/>" charset="gbk"></script>
<script type="text/javascript" src="<c:url value='/pages/scripts/global.js'/>" charset="gbk"></script>
<script type="text/javascript" src="<c:url value='/scripts/util.js'/>" charset="gbk"></script>
<link rel="icon" href="./pages/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="./pages/images/favicon.ico" type="image/x-icon" />
<link href="<c:url value="/pages/style/default/style.css"/>" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value='/pages/scripts/calendar.js'/>" charset="gbk"></script>
</head>

<body style="overflow-x:hidden;overflow-y:hidden;">
    <!---------------------- include header start --------------------------->
    <tiles:insert attribute="header"/>
    <!---------------------- include header end ----------------------------->
    
	<table border="1" width="100%" height="100%"><tr>
	<td width="20%" valign="top">
		<div id="scrolldivmenu" class="scroll">
			<tiles:insert attribute="menu"/>	
		</div>	
	</td>
	<td valign="top">
		<div id="scrolldivbody" class="scroll">		
	    <!---------------------- include body start ----------------------------->
	    	<tiles:insert attribute="body"/>
	    <!---------------------- include body end ------------------------------->
	    </div>			
	</td>	
	</tr></table>
	
    <!---------------------- include footer start --------------------------->
    <tiles:insert attribute="footer"/>
    <!---------------------- include footer end ----------------------------->   

</body>
</html:html>

<script language="javascript">
    <!--
    var clientheight = document.documentElement.clientHeight -60;
    clientheight = clientheight + "px";
    
    document.getElementById("scrolldivmenu").style.height = clientheight;
    document.getElementById("scrolldivbody").style.height = clientheight;
    
    function SetCookie(sName, sValue)
    {
        date = new Date();
        s = date.getDate();
        date.setDate(s+1);            //expire time is one month late!, and can't be current date!
        document.cookie = sName + "=" + escape(sValue) + "; expires=" + date.toGMTString();
    }
    function GetCookie(sName)
    {
        // cookies are separated by semicolons
        var aCookie = document.cookie.split("; ");
        for (var i=0; i < aCookie.length; i++)
        {
        // a name/value pair (a crumb) is separated by an equal sign
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            return unescape(aCrumb[1]);}
        }

        // a cookie with the requested name does not exist
        return null;
    }

    function fnLoad()
    {
    	window.onresize();
    	//document.documentElement.scrollLeft = GetCookie("scrollLeft");
        //document.documentElement.scrollTop = GetCookie("scrollTop");
        document.getElementById("scrolldivmenu").scrollLeft = GetCookie("menuscrollLeft");
        document.getElementById("scrolldivmenu").scrollTop = GetCookie("menuscrollTop");
        document.getElementById("scrolldivbody").scrollLeft = GetCookie("bodyscrollLeft");
        document.getElementById("scrolldivbody").scrollTop = GetCookie("bodyscrollTop");
    }

    function fnUnload()
    {
        //SetCookie("scrollLeft", document.documentElement.scrollLeft);
        //SetCookie("scrollTop", document.documentElement.scrollTop );
        SetCookie("menuscrollLeft", document.getElementById("scrolldivmenu").scrollLeft);
        SetCookie("menuscrollTop", document.getElementById("scrolldivmenu").scrollTop );
        SetCookie("bodyscrollLeft", document.getElementById("scrolldivbody").scrollLeft);
        SetCookie("bodyscrollTop", document.getElementById("scrolldivbody").scrollTop );
    }

    window.onload = fnLoad;
    window.onunload = fnUnload;
	window.onresize = function(){
		var clientheight = document.documentElement.clientHeight -60;
    	clientheight = clientheight + "px";    
    	document.getElementById("scrolldivmenu").style.height = clientheight;
    	document.getElementById("scrolldivbody").style.height = clientheight;
    	};
    // -->
</script>
