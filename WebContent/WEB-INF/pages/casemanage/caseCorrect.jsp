<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>

<html:form action="casemanage.do" onsubmit="return checkFormValidate();">
	<html:errors />
	
	<input type="hidden" name="method" value="saveTestCaseCorrect">

	<html:hidden property="caseInfo.tcModuleFunction"/>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">		
		<tr><td width="50%"></td>
			<td align="center"><html:submit styleClass="previousbutton" onclick="chgAction(document.all.method,'correctTestCasePrevious');">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="nextbutton" onclick="chgAction(document.all.method,'correctTestCaseNext');">&nbsp;</html:submit></td>
			
			<td align="center"><html:submit styleClass="savebutton">&nbsp;</html:submit></td>
			<td align="center"><html:submit styleClass="savenewbutton" onclick="chgAction(document.all.method,'saveTestCaseCorrectNext');">&nbsp;</html:submit></td>						
			<td align="center"><html:reset styleClass="resetbutton">&nbsp;</html:reset></td>				
		</tr>
		<tr>
			<td align="left" class="strong"><bean:message bundle="case" key="project"/>：<bean:write name="caseForm" property="projectInfo.PName"/></td>
		</tr>
	</table>	
	<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="0">	
		<tr>
			<td width="10%">&nbsp;</td><td width="20%"></td>
			<td width="10%"></td><td width="30%"></td>
			<td rowspan="3" align="center">
				<fieldset style="width:96%;">
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
			<td align="left" colspan="3">			
				<bean:write name="caseForm" property="caseInfo.moduleFunction.entireName"/>			
			</td>								
		</tr>	
		<tr><td>&nbsp;</td></tr>
		<tr>	
			<td align="right"><bean:message bundle="case" key="case_code"/>：</td>
			<td align="left"><bean:write name="caseForm" property="caseInfo.tcCode"/></td>		
			
			<td align="right"><bean:message bundle="case" key="case_type"/>：</td>
			<td align="left">
				<logic:notEmpty name="caseForm" property="caseInfo.caseType">
				<bean:write name="caseForm" property="caseInfo.caseType.ctName"/>	
				</logic:notEmpty>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_objective"/>：</td>
			<td colspan="4" align="left"><bean:write name="caseForm" property="caseInfo.tcTestObjective"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><bean:message bundle="case" key="test_content"/>：</td>
			<td colspan="4" align="left"><bean:write name="caseForm" property="caseInfo.tcTestContent"/></td>
		</tr>
		
		<tr>
			<td align="right">
				<bean:message bundle="project" key="project_attachment" />：
			</td>
			<td colspan="3" align="left">
				<div style="width:98%;height:20px;background:white;">
				<logic:iterate id="am" name="caseForm" property="caseInfo.attachmentList" indexId="i">
				<logic:notEqual name="am" property="caFlag" value="-1">											
					<a href="casemanage.do?method=downloadAttachment&id=<bean:write name="am" property="caId"/>"><bean:write name="am" property="caName"/></a>；
				</logic:notEqual>
				</logic:iterate>
				</div>				
			</td>			
			
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
						<bean:write name="cvr" property="testResult.trName"/>
					</td>				
					<td align="center">						
						<bean:write name="cvr" property="importantLevel.ilName"/>
					</td>				
					<td align="center">
						<bean:write name="cvr" property="bugType.btName"/>
					</td>
					<td align="left"><bean:write name="cvr" property="cvrCaseOutput"/></td>
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
				<html:textarea cols="60" rows="16" property="caseInfo.tcRemark"></html:textarea>					
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
	
	<bean:define id="c" name="caseForm" property="caseInfo"></bean:define>
	<c:set var="caseInfo" value="${c}" scope="request" />
	<c:import url="/WEB-INF/pages/casemanage/recordList.jsp"></c:import>
</table>
			 
</html:form>


<script language="JavaScript">	
 function chgAction(obj,str){
	obj.value=str;
 }
 
function checkFormValidate()
{
	var type = document.getElementsByName("caseInfo.tcBugType")[0].value	
	if(type == "")
	{
		alert("异常类型  不能为空");
		return false;
	}
}

</script>


