<%@page pageEncoding="GBK"%>
<%@ include file="../common/include.jsp"%>
			
<table CELLPADDING="2" CELLSPACING="0" width="100%" border="0">		
	<tr><td width="10%" align="right" class="strong"><bean:message bundle="case" key="project"/>:</td>
		<td width="15%" align="left" class="strong"><bean:write name="caseForm" property="projectInfo.PName"/></td>
		<td width="10%"></td><td width="15%"></td><td width="10%"></td><td width="15%"></td>
		<td width="10%">
			<bean:message bundle="case" key="count"/>:<bean:write name="caseCount"/>
		</td>
		<td>
			<html:button styleClass="exportbutton" property="" onclick="excelExport();">
				&nbsp;
			</html:button>
		</td>
	</tr>					
</table>
			
<div id="exceltable"> 
<table CELLPADDING="2" CELLSPACING="0" width="100%" border="1">		
	<tr><td width="10%" align="right" class="strong"><bean:message bundle="case" key="project"/>:</td>
		<td width="15%" align="left" class="strong"><bean:write name="caseForm" property="projectInfo.PName"/></td>
		<td width="10%"></td><td width="15%"></td><td width="10%"></td><td width="15%"></td>
		<td width="10%" align="right"><bean:message bundle="case" key="count"/>:</td><td align="left"><bean:write name="caseCount"/></td>
	</tr>			
	<tr>
		<td align="right"><bean:message bundle="case" key="module"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="searchInfo.moduleFunction.muId">
			<bean:write name="caseForm" property="searchInfo.moduleFunction.projectModuleName"/>
		</logic:notEmpty>			
		</td>
		<td align="right"><bean:message bundle="case" key="module_function"/>:</td>
		<td align="left">			
		<logic:notEmpty name="caseForm" property="searchInfo.moduleFunction.muId">
			<bean:write name="caseForm" property="searchInfo.moduleFunction.entireModuleFunctionName"/>
		</logic:notEmpty>					
		</td>
		<td align="right"><bean:message bundle="case" key="case_code"/>:</td><td align="left"><bean:write name="caseForm" property="searchInfo.tcCode"/></td>
		<td align="right"><bean:message bundle="case" key="test_objective"/>:</td><td align="left"><bean:write name="caseForm" property="searchInfo.tcTestObjective"/></td>
	</tr>	
	<tr>
		<td align="right"><bean:message bundle="case" key="test_content"/>:</td><td align="left"><bean:write name="caseForm" property="searchInfo.tcTestContent"/></td>
		<td align="right"><bean:message bundle="case" key="intend_output"/>:</td><td align="left"><bean:write name="caseForm" property="searchInfo.tcIntendOutput"/></td>
		<td align="right"><bean:message bundle="case" key="test_output"/>:</td><td align="left"><bean:write name="caseForm" property="cvrSearchInfo.cvrCaseOutput"/></td>
		<td align="right"><bean:message bundle="case" key="test_result"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="cvrSearchInfo.testResult">
			<bean:write name="caseForm" property="cvrSearchInfo.testResult.trName"/>
		</logic:notEmpty>
		</td>
	</tr>
	<tr>
		<td align="right"><bean:message bundle="case" key="case_type"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="searchInfo.caseType">
			<bean:write name="caseForm" property="searchInfo.caseType.ctName"/>
		</logic:notEmpty>
		</td>
		<td align="right"><bean:message bundle="case" key="case_status"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="cvrSearchInfo.status">
			<bean:write name="caseForm" property="cvrSearchInfo.status.csName"/>	
		</logic:notEmpty>		
		</td>
		<td align="right"><bean:message bundle="case" key="create_time"/>:</td>
		<td align="left">
			<bean:write name="caseForm" property="searchInfo.tcCreateTimeStr"/>
		</td>
		<td align="right"><bean:message bundle="case" key="create_user"/>:</td><td align="left"><bean:write name="caseForm" property="searchInfo.tcCreateUserStr"/></td>			
	</tr>	
	<tr>
		<td align="right"><bean:message bundle="case" key="test_time"/>:</td>
		<td align="left">
			<bean:write name="caseForm" property="cvrSearchInfo.cvrTestTimeStr"/>
		</td>
		<td align="right"><bean:message bundle="case" key="test_user"/>:</td><td align="left"><bean:write name="caseForm" property="cvrSearchInfo.cvrTestUserStr"/></td>			
		<td align="right"><bean:message bundle="case" key="correct_time"/>:</td>
		<td align="left">
			<bean:write name="caseForm" property="cvrSearchInfo.cvrCorrectTimeStr"/>
		</td>
		<td align="right"><bean:message bundle="case" key="correct_user"/>:</td><td align="left"><bean:write name="caseForm" property="cvrSearchInfo.cvrCorrectUserStr"/></td>			
	</tr>		
	<tr>
		<td align="right"><bean:message bundle="case" key="important_level"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="cvrSearchInfo.importantLevel">
			<bean:write name="caseForm" property="cvrSearchInfo.importantLevel.ilName"/>
		</logic:notEmpty>
		</td>
		<td align="right"><bean:message bundle="case" key="bug_type"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="cvrSearchInfo.bugType">
			<bean:write name="caseForm" property="cvrSearchInfo.bugType.btName"/>
		</logic:notEmpty>
		</td>
		<td align="right"><bean:message bundle="project" key="version"/>:</td>
		<td align="left">
		<logic:notEmpty name="caseForm" property="cvrSearchInfo.projectVersion">
			<bean:write name="caseForm" property="cvrSearchInfo.projectVersion.pvName"/>
		</logic:notEmpty>
		</td>		
	</tr>	
</table>
<table><tr><td>&nbsp;</td></tr></table>

<logic:present name="caseList">
	<logic:iterate name="caseList" id="c" indexId="i">
		<table CELLPADDING="0" CELLSPACING="0" WIDTH="100%" border="1">				
			<tr>
				<td width="15%" align="center"><bean:message bundle="case" key="module"/></td>
				<td width="15%" align="left">
					<bean:write name="c" property="moduleFunction.projectModuleName"/>
				</td>
				<td width="15%" align="center"><bean:message bundle="case" key="module_function"/></td>
				<td width="15%" align="left">			
					<bean:write name="c" property="moduleFunction.entireModuleFunctionName"/>			
				</td>								
				<td width="15%" align="center"><bean:message bundle="case" key="case_code"/></td><td align="left"><bean:write name="c" property="tcCode"/></td>
			</tr>	
			<tr>
				<td align="center"><bean:message bundle="case" key="test_objective"/></td><td colspan="5" align="left"><bean:write name="c" property="tcTestObjective"/></td>
			</tr>
			<tr>
				<td align="center"><bean:message bundle="case" key="test_content"/></td><td colspan="5" align="left"><bean:write name="c" property="tcTestContent"/></td>
			</tr>
			
			<tr>			
				<td align="center"><bean:message bundle="case" key="test_step"/></td>
				<td align="left" colspan="5">
					<fieldset style="width:98%;float: left;">
						<bean:write name="c" property="tcTestStep" filter="false"/>
					</fieldset>
				</td>
			</tr>
		
			<tr>
				<td align="center"><bean:message bundle="case" key="intend_output"/></td>
				<td align="left" colspan="5">
					<fieldset style="width:98%;float: left;">
						<bean:write name="c" property="tcIntendOutput" filter="false"/>
					</fieldset>
				</td>				
			</tr>
			<tr>
				<td align="center"><bean:message bundle="case" key="test_output"/></td>
				<td align="left" colspan="5">
					
				</td>				
			</tr>
			<tr>			
				<td align="center"><bean:message bundle="case" key="test_result"/></td>
				<td align="left" colspan="5">
					
				</td>				
			</tr>					
		</table>
		<table><tr><td>&nbsp;</td></tr></table>
	</logic:iterate>
</logic:present>
</div>

<iframe name="printframe" style="height:0px;width:1000px;"></iframe>

<script language="JavaScript">
 
 function excelExport(){ 
	var tempStr = document.getElementById('exceltable').innerHTML;
   	var doc = document.frames["printframe"].document;
   	doc.write(tempStr);
  	
   	doc.close();
   	
   	doc.execCommand('saveas',true,'exportData.doc');
 }
	
 
</script>
