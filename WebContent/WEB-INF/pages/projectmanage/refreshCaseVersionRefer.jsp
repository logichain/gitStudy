<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script language="JavaScript">
 	var url = window.opener.location.href;		
	var index = url.indexOf("method=");
	if(index == -1)
	{
		window.opener.location = url + "?method=caseVersionRefer&pid=<%=request.getAttribute("pid")%>";
	}
	else
	{
		window.opener.location = url.substring(0,index) + "method=caseVersionRefer&pid=<%=request.getAttribute("pid")%>";		
	}		
	
	window.close();	
</script>

