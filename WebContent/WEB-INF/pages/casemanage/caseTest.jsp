<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCaseTest">
	<html:hidden property="caseInfo.moduleId"/>
	<html:hidden property="caseInfo.tcModuleFunction"/>
	<html:hidden property="caseInfo.tcCode"/>
	<html:hidden property="caseInfo.tcTestObjective"/>
	<html:hidden property="caseInfo.tcTestContent"/>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgFormOnsubmit('return true;');chgAction(document.all.method,'testTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgFormOnsubmit('return true;');chgAction(document.all.method,'testTestCaseNext');">&nbsp;</html:submit></td>
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseTestNext');">&nbsp;</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>：<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="40%"></td>
			<td width="10%"></td><td width="10%"></td>
			<td rowspan="3" align="left">
				<fieldset style="width:96%;float:right;">
					<legend><bean:message bundle="project" key="version"/></legend>
					<logic:iterate name="caseForm" property="projectInfo.projectVersionList" id="ver" indexId="i">
						<html:checkbox disabled="true" name="caseForm" property='<%="projectInfo.projectVersionList[" + i + "].selected" %>'/>
						<bean:write name="ver" property="pvVersion"/>
	      				<input type="hidden" name='<%="projectInfo.projectVersionList[" + i + "].selected" %>' value="false">
					</logic:iterate>
				</fieldset>
			</td>
		</tr>	
		<tr>
			
			<td align="right"><bean:message bundle="case" key="module_function"/>：</td>
			<td align="left">			
				<bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/>			
			</td>			
			
			<td align="right"><bean:message bundle="case" key="case_code"/>：</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.tcCode"/></td>			
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>：</td>
			<td colspan="4" align="left"><bean:write name="caseForm" property="caseInfo.tcTestObjective"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>：</td>
			<td colspan="5" align="left"><bean:write name="caseForm" property="caseInfo.tcTestContent"/></td>
		</tr>
		
	</table>		
	
	<fieldset style="width:99%;float:left;">
	<legend><bean:message bundle="case" key="test_result"/></legend>
		<table class="sort-table" cellSpacing="1" cellPadding="1" width="100%" border="0">		
			<thead>
				<tr>
					<th width="10%"><bean:message bundle="project" key="project_version"/></th>
					<th width="10%"><bean:message bundle="case" key="test_result"/></th>
					<th width="10%"><bean:message bundle="case" key="important_level"/></th>
					<th width="10%"><bean:message bundle="case" key="bug_type"/></th>
					<th><bean:message bundle="case" key="test_output"/></th>
				</tr>
			</thead>
			<logic:iterate name="caseForm" property="caseInfo.caseVersionReferenceList" id="cvr" indexId="i">
				<logic:equal name="cvr" property="currentReference" value="true">
				<tr>	
					<td align="center"><bean:write name="cvr" property="projectVersion.pvVersion"/></td>				
					<td align="center">	
						<html:select styleId="testResult" name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrCaseResult"%>' style="width:120px">	
							<html:option value=""></html:option>									
							<html:optionsCollection name="testResultList" value="trId" label="trName"/>									
						</html:select>
					</td>				
					<td align="center">
						<html:select  styleId="importLevel" name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrImportantLevel"%>' style="width:120px">	
							<html:option value=""></html:option>									
							<html:optionsCollection name="importantLevelList" value="ilId" label="ilName"/>									
						</html:select>
					</td>				
					<td align="center">
						<html:select  styleId="bugType" name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrBugType"%>' style="width:120px">	
							<html:option value=""></html:option>									
							<html:optionsCollection name="bugTypeList" value="btId" label="btName"/>									
						</html:select>
					</td>
					<td align="left"><html:text styleId="output" name="caseForm" property='<%="caseInfo.caseVersionReferenceList[" + i + "].cvrCaseOutput"%>' size="93" maxlength="200"/></td>
				</tr>	
				</logic:equal>		
			</logic:iterate>		
		</table>
	</fieldset>
	
							
	<table width="100%">
		<tr>
			<td width="50%" rowspan="2" align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_step"/></legend>
					<html:textarea readonly="true" cols="60" rows="30" property="caseInfo.tcTestStep"></html:textarea>													
				</fieldset>	
			</td>
			<td align="left">
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="test_remark"/></legend>
					<html:textarea readonly="true" cols="60" rows="16" property="caseInfo.tcRemark"></html:textarea>					
				</fieldset>
			</td>
		</tr>
		<tr>
			<td align="left">				
				<fieldset style="width:98%;float:left;">
					<legend><bean:message bundle="case" key="intend_output"/></legend>
					<html:textarea readonly="true" cols="60" rows="10" property="caseInfo.tcIntendOutput"></html:textarea>							
				</fieldset>
			</td>
			
		</tr>
				
		<bean:define id="ci" name="caseForm" property="caseInfo"></bean:define>
		<c:set var="caseInfo" value="${ci}" scope="request" />
		<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
	</table>
			 
</html:form>

<script language="JavaScript">	

 function chgAction(obj,str){
	obj.value=str;
 }
 
 function chgFormOnsubmit(str){  	 	
	caseForm.onsubmit="function onsubmit(){" + str + "}";	
 }
 
 function checkFormValidate()
{	
	var pname = document.getElementById("testResult").value;
	if(pname == "")
	{
		alert("测试结果  不能为空");
		return false;
	} 
	
	if(pname==2)
	{
		var level = document.getElementById("importLevel").value;	
		if(level == "")
		{
			alert("重要等级  不能为空");
			return false;
		} 
		
		var type = document.getElementById("bugType").value;	
		if(type == "")
		{
			alert("异常类型  不能为空");
			return false;
		}
		
		var output = document.getElementById("output").value;	
		if(output == "")
		{
			alert("输出  不能为空");
			return false;
		}
		
	}
	 	 	
	return true;
}



</script>

