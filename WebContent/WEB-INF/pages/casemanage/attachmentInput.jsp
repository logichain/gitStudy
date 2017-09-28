<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" enctype="multipart/form-data" onsubmit="return checkFormValidate();" >
	<html:errors />
	
	<input type="hidden" name="method" value="confirmAttachment">
	<input type="hidden" name="opType" value="<%=request.getParameter("opType")%>">				
	<table class="win" width="90%" border="0">		
		
		<tr>
			<td width="20%" align="right">
				<bean:message bundle="project" key="attachment_selectfile" />：
			</td>
			<td id="filetd" align="left">
				<html:file property="caseInfo.currentAttachment.attachmentFile" styleId="selectFile" size="60"  onchange="document.getElementById('selectFile').value=this.value"></html:file>	
				<html:hidden property="caseInfo.currentAttachment.caLocalUrl"/>				
			</td>				
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="attachment_filecode" />：
			</td>
			<td align="left">						
				<html:text property="caseInfo.currentAttachment.caCode" styleId="fileCode" size="12" maxlength="45"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="attachment_filename" />：
			</td>
			<td align="left">						
				<html:text property="caseInfo.currentAttachment.caName" styleId="fileName" size="45" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="attachment_description" />：
			</td>
			<td align="left">						
				<html:text property="caseInfo.currentAttachment.caDescription" size="60" maxlength="200"/>
			</td>
		</tr>
		
	</table>
	<table class="win" width="100%" border="0">
		
		<tr><td width="70%">&nbsp;</td>
			<td align="center" width="12%">
				<html:submit styleClass="button">
					<bean:message key="button.confirm" />
				</html:submit>						
			</td>
			<td align="center" width="12%">
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
	var file = document.getElementById("selectFile").value;
	if(file == "")
	{
		alert("选择文件不能为空");
		return false;
	} 
	
	var code = document.getElementById("fileCode").value;	
	if(code == "")
	{
		alert("文件编号不能为空");
		return false;
	} 
	
	var name = document.getElementById("fileName").value;	
	if(name == "")
	{
		alert("文件名称 不能为空");
		return false;
	} 
	 	
	return true;
}


</script>

