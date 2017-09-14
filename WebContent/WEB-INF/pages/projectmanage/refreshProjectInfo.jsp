<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<script language="JavaScript">
 	var url = window.opener.location.href;		
	var index = url.indexOf("method=");
	if(index == -1)
	{
		window.opener.location = url + "?method=refreshProjectInfo&tabpageId=<%=request.getAttribute("tabpageId")%>";
	}
	else
	{
		window.opener.location = url.substring(0,index) + "method=refreshProjectInfo&tabpageId=<%=request.getAttribute("tabpageId")%>";		
	}		
	
	window.close();	
</script>

