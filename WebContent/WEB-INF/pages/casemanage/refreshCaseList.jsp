<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script language="JavaScript">
 	var url = window.opener.location.href;		
	var index = url.indexOf("method=");
	if(index == -1)
	{
		window.opener.location = url + "?method=searchTestCase&pager.offset=0";
	}
	else
	{
		window.opener.location = url.substring(0,index) + "method=searchTestCase&pager.offset=0";		
	}		
	
	window.close();	
</script>

