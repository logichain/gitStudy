<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>


<html:form action="casemanage.do" enctype="multipart/form-data" onsubmit="return checkFormValidate();">
	<html:errors />		
	<input type="hidden" name="method" value="uploadTestCaseInfo">	

	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="70%" border="0" height="100">	
		<caption>用例导入</caption>
					
		<tr height="30">				
			<td>					
				<html:file styleId="importFile" property="caseInfo.importFile" size="80" accept="application/vnd.ms-excel;application/vnd.ms-word"></html:file>															
			</td>						
		</tr>
		
		<tr>
			<td align="right">				
				<html:submit styleClass="confirmbutton">&nbsp;</html:submit>
				<html:button property="" onclick="window.close();" styleClass="button">
					<bean:message key="button.cancel" />
				</html:button>				
			</td>
		</tr>			
	</table>
			
</html:form>
		

<script language="JavaScript">

function checkFormValidate()
{					
	var file = document.getElementById("importFile").value;	
	if(file == "")
	{
		alert("文件不能为空");
		return false;
	} 
			
	return true;
}

</script>

