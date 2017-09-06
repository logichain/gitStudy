<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>


<html:form action="projectmanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveModuleFunction">	
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
			<tr><td>&nbsp;</td></tr>	
			<tr><td width="70%">&nbsp;</td>						
				<td align="center" width="12%">
					<html:submit styleClass="savebutton">
						&nbsp;
					</html:submit>						
				</td>				
			</tr>
		</table>	
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr>
			<td width="20%"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="function_name"/>：
			</td>
			<td align="left">
				<bean:write name="projectForm" property="moduleFunctionInfo.parentFunction.muName"/>
			</td>			
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="function_name" />：	
			</td>
			<td align="left">		
				<html:text property="moduleFunctionInfo.muName" size="30" maxlength="45"/>
			</td>						
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="remark" />：
			</td>
			<td align="left">			
				<html:text property="moduleFunctionInfo.muRemark" size="40" maxlength="200"/>
			</td>
			
		</tr>		
		
		<tr><td>&nbsp;</td></tr>					
		
	</table>
			
</html:form>
		

<script language="JavaScript">

 function checkFormValidate()
{
	//var pname = document.getElementsByName("moduleFunctionInfo.muName")[0].value
	var pname = document.getElementById("moduleFunctionInfo.muName").value;
	if(pname == "")
	{
		alert("画面名称  不能为空");
		return false;
	} 
	 	 	
	return true;
}


</script>

