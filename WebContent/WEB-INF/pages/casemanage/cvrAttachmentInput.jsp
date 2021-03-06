<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
	
<html:form action="casemanage.do" enctype="multipart/form-data" onsubmit="return checkFormValidate();" >
	<html:errors />
	
	<input type="hidden" name="method" value="confirmCvrAttachment">			
	<table class="win" width="98%" border="0">		
		
		<tr>
			<td width="20%" align="right">
				<bean:message bundle="project" key="attachment_selectfile" />：
			</td>
			<td id="filetd" align="left">
				<html:file property="caseInfo.currentCaseVersionReference.currentAttachment.attachmentFile" accept="image/*" styleId="selectFile" size="60"  onchange="document.getElementById('selectFile').value=this.value"></html:file>	
				<html:hidden property="caseInfo.currentCaseVersionReference.currentAttachment.caLocalUrl"/>				
			</td>				
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="attachment_filecode" />：
			</td>
			<td align="left">						
				<html:text property="caseInfo.currentCaseVersionReference.currentAttachment.caCode" styleId="fileCode" size="12" maxlength="45"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="attachment_filename" />：
			</td>
			<td align="left">						
				<html:text property="caseInfo.currentCaseVersionReference.currentAttachment.caName" styleId="fileName" size="45" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="attachment_description" />：
			</td>
			<td align="left">						
				<html:text property="caseInfo.currentCaseVersionReference.currentAttachment.caDescription" size="60" maxlength="200"/>
			</td>
		</tr>
		
	</table>

	<table class="win" width="100%" border="0">
		
		<tr><td width="70%"></td>
			<td align="center" width="12%">
				<html:submit styleClass="confirmbutton">&nbsp;</html:submit>					
			</td>
			<td align="center" width="12%">
				<html:button property="" onclick="window.close();" styleClass="cancelbutton">&nbsp;</html:button>		
			</td>					
		</tr>
	</table>		
</html:form>


<script language="JavaScript">

function checkFormValidate()
{
	var file = document.getElementById("selectFile").value;
	if(file == "")
	{
		alert("选择文件不能为空");
		return false;
	} 
	
// 	var code = document.getElementById("fileCode").value;	
// 	if(code == "")
// 	{
// 		alert("文件编号不能为空");
// 		return false;
// 	} 
	
	var name = document.getElementById("fileName").value;	
	if(name == "")
	{
		alert("文件名称 不能为空");
		return false;
	} 
	 	
	return true;
}


</script>

