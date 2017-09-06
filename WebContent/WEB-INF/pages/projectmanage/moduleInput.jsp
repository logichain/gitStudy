<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>


<html:form action="projectmanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveProjectModule">	
	<table class="win" CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">
			<tr><td>&nbsp;</td></tr>	
			<tr>
				<td width="70%">
					
				</td>						
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
			<td align="right" class="strong">
				<bean:message bundle="case" key="project"/>:
			</td>
			<td align="left">
				<bean:write name="projectForm" property="moduleInfo.project.PName"/>
			</td>			
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="module_name" />：	
			</td>
			<td align="left">		
				<html:text property="moduleInfo.pmName" size="30" maxlength="45"/>
			</td>						
		</tr>
		<tr>
			<td align="right">
				<bean:message bundle="project" key="remark" />：
			</td>
			<td align="left">			
				<html:text property="moduleInfo.pmRemark" size="40" maxlength="200"/>
			</td>
			
		</tr>		
		
		<tr><td>&nbsp;</td></tr>					
		
	</table>
			
</html:form>
	
<script language="JavaScript">

 function checkFormValidate()
{
	var pname = document.getElementsByName("moduleInfo.pmName")[0].value
	//var pname = document.getElementById("caseInfo.tcTestResult").value;---与DOCTYPE不兼容
	if(pname == "")
	{
		alert("模块名称  不能为空");
		return false;
	} 
	 	 	
	return true;
}


</script>

