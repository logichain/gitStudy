<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>


	<html:form action="casemanage.do" enctype="multipart/form-data" onsubmit="return checkFormValidate();">
		<html:errors />		
		<input type="hidden" name="method" value="uploadTestCaseInfo">	
	
		<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="70%" border="0" height="200">	
			<caption>��������</caption>
						
			<tr height="30">				
				<td>					
					<html:file styleId="importFile" property="caseInfo.importFile" size="80" accept="application/vnd.ms-excel"></html:file>															
				</td>						
			</tr>
			
			<tr><td></td>
			<td align="right">				
				<html:submit styleClass="savebutton">&nbsp;</html:submit>				
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
		alert("�ļ�����Ϊ��");
		return false;
	} 
			
	return true;
}

</script>
